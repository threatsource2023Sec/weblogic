package weblogic.deploy.api.spi.config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.model.J2eeApplicationObject;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.DConfigBeanRoot;
import javax.enterprise.deploy.spi.exceptions.BeanNotFoundException;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ClassLoaderControl;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.deploy.api.model.WebLogicDDBeanRoot;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.model.internal.DDBeanRootImpl;
import weblogic.deploy.api.model.sca.ScaApplicationObject;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.spi.WebLogicDConfigBeanRoot;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.descriptor.internal.DescriptorHelper;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.EntityDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.PersistenceBean;
import weblogic.j2ee.descriptor.wl.PersistenceUseBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;

public class DeploymentConfigurationImpl implements WebLogicDeploymentConfiguration, PropertyChangeListener {
   private static final boolean debug = Debug.isDebug("config");
   private DeployableObject dObject;
   private ArrayList dObjects = new ArrayList();
   private Map rootMap = new HashMap();
   private Map ddBeanRoots = new HashMap();
   private DeploymentPlanBean plan = null;
   private String appName;
   private InstallDir installDir = null;
   private boolean planRestored = false;
   private boolean extDDWrite = false;
   private DescriptorHelper ddhelper = null;

   public void close() {
      this.unregisterListener(this.plan);
      this.dObject = null;
      this.dObjects.clear();
      this.rootMap.clear();
      Iterator valIter = this.ddBeanRoots.values().iterator();

      while(valIter.hasNext()) {
         WebLogicDConfigBeanRoot dcb = (WebLogicDConfigBeanRoot)valIter.next();
         if (dcb != null) {
            dcb.close();
         }
      }

      this.ddBeanRoots.clear();
      this.plan = null;
      this.appName = null;
      this.installDir.resetInstallDir((File)null);
      this.installDir = null;
   }

   public DeploymentConfigurationImpl(DeployableObject deployableObject) throws InvalidModuleException, IOException {
      ConfigHelper.checkParam("DeployableObject", deployableObject);
      DescriptorSupportManager.flush();
      this.dObject = deployableObject;
      this.initialize();
      this.plan = this.buildPlan();
   }

   public DeployableObject getDeployableObject() {
      return this.dObject;
   }

   private WebLogicDConfigBeanRoot findRoot(DDBeanRoot ddb) {
      Iterator roots = this.ddBeanRoots.entrySet().iterator();

      Map.Entry entry;
      DDBeanRoot root;
      do {
         if (!roots.hasNext()) {
            return null;
         }

         entry = (Map.Entry)roots.next();
         root = (DDBeanRoot)entry.getKey();
      } while(!root.equals(ddb));

      return (WebLogicDConfigBeanRoot)entry.getValue();
   }

   public DConfigBeanRoot getDConfigBeanRoot(DDBeanRoot ddbeanroot) throws ConfigurationException {
      ConfigHelper.checkParam("DDBeanRoot", ddbeanroot);
      WebLogicDConfigBeanRoot dcb = this.findRoot(ddbeanroot);
      if (dcb == null) {
         DescriptorSupport ds = null;
         Iterator names = this.rootMap.entrySet().iterator();
         String rtag = this.getRootTag(ddbeanroot);

         while(names.hasNext()) {
            Map.Entry entry = (Map.Entry)names.next();
            DeployableObjectKey tmpKey = (DeployableObjectKey)entry.getKey();
            String name = tmpKey.getName();
            DDBeanRoot root = (DDBeanRoot)entry.getValue();
            if (root != null && root.equals(ddbeanroot)) {
               ConfigurationException ce;
               try {
                  ds = DescriptorSupportManager.getForTag(rtag);
                  if (ds == null) {
                     throw new AssertionError("Inconsistent results from DescriptorSupport");
                  }

                  dcb = this.initDConfig(ddbeanroot, name, ds);
                  if (dcb != null) {
                     DeployableObjectKey key = new DeployableObjectKey(name, ddbeanroot.getDeployableObject().getType(), tmpKey.getContextRoot());
                     this.rootMap.put(key, ddbeanroot);
                     this.ddBeanRoots.put(ddbeanroot, dcb);
                     this.addToPlan(dcb);
                     this.collectSecondaryConfig(dcb);
                  }
                  break;
               } catch (InvalidModuleException var12) {
                  ce = new ConfigurationException(var12.toString());
                  ce.initCause(var12);
                  throw ce;
               } catch (IOException var13) {
                  ce = new ConfigurationException(SPIDeployerLogger.parseError(name, ds.getConfigURI(), var13.getMessage()));
                  ce.initCause(var13);
                  throw ce;
               }
            }
         }

         if (dcb == null) {
            throw new AssertionError("DDBeanRoot not part of any DeployableObject");
         }
      }

      return dcb;
   }

   public String getModuleName(DDBeanRoot ddr) {
      ConfigHelper.checkParam("DDBeanRoot", ddr);
      Iterator names = this.rootMap.entrySet().iterator();

      DDBeanRoot root;
      String name;
      do {
         if (!names.hasNext()) {
            return null;
         }

         Map.Entry entry = (Map.Entry)names.next();
         DeployableObjectKey key = (DeployableObjectKey)entry.getKey();
         name = key.getName();
         root = (DDBeanRoot)entry.getValue();
      } while(!ddr.equals(root));

      return name;
   }

   public void removeDConfigBean(DConfigBeanRoot dcb) throws BeanNotFoundException {
      if (dcb == null) {
         throw new BeanNotFoundException(SPIDeployerLogger.nullDCB());
      } else {
         Iterator entries = this.ddBeanRoots.entrySet().iterator();

         Map.Entry entry;
         for(entry = null; entries.hasNext(); entry = null) {
            entry = (Map.Entry)entries.next();
            if (dcb.equals(entry.getValue())) {
               break;
            }
         }

         if (entry == null) {
            this.removeSecondaryDConfigBean(dcb);
         }

         ((BasicDConfigBeanRoot)dcb).removeAllChildren();
         if (entry != null) {
            entry.setValue((Object)null);
         }

         this.removeFromPlan((BasicDConfigBeanRoot)dcb);
         if (entry != null) {
            SPIDeployerLogger.logRemoveDCB(this.getModuleName((DDBeanRoot)entry.getKey()));
         }

      }
   }

   public DConfigBeanRoot restoreDConfigBean(InputStream inputstream, DDBeanRoot ddbeanroot) throws ConfigurationException {
      ConfigHelper.checkParam("InputStream", inputstream);
      ConfigHelper.checkParam("DDBeanRoot", ddbeanroot);
      WebLogicDConfigBeanRoot newDCB = null;
      WebLogicDConfigBeanRoot oldDCB = this.findRoot(ddbeanroot);
      DeploymentPlanBean oldPlan = this.plan;

      try {
         String rtag = null;
         DescriptorSupport ds;
         if (oldDCB != null) {
            ds = oldDCB.getDescriptorSupport();
         } else {
            rtag = this.getRootTag(ddbeanroot);
            ds = DescriptorSupportManager.getForTag(rtag);
         }

         if (ds == null) {
            throw new ConfigurationException(SPIDeployerLogger.badDDBean(rtag));
         } else {
            newDCB = this.constructDCB(ddbeanroot, inputstream, ds.getDConfigClassName(), this.getModuleName(ddbeanroot), ds);
            this.ddBeanRoots.put(ddbeanroot, newDCB);
            this.removeFromPlan((BasicDConfigBeanRoot)oldDCB);
            this.addToPlan(newDCB);
            SPIDeployerLogger.logRestoreDCB(this.getModuleName(ddbeanroot));
            return newDCB;
         }
      } catch (Throwable var8) {
         ConfigurationException ce;
         if (var8 instanceof ConfigurationException) {
            ce = (ConfigurationException)var8;
         } else {
            ce = new ConfigurationException(var8.toString());
            ce.initCause(var8);
         }

         this.ddBeanRoots.put(ddbeanroot, oldDCB);
         this.plan = oldPlan;
         throw ce;
      }
   }

   public void saveDConfigBean(OutputStream outputstream, DConfigBeanRoot dconfigbeanroot) throws ConfigurationException {
      ConfigHelper.checkParam("OuputStream", outputstream);
      ConfigHelper.checkParam("DConfigBeanRoot", dconfigbeanroot);

      try {
         this.toXML(((BasicDConfigBean)dconfigbeanroot).getDescriptorBean(), outputstream);
         SPIDeployerLogger.logSaveDCB(this.getModuleName((DDBeanRoot)dconfigbeanroot.getDDBean()));
      } catch (IOException var5) {
         ConfigurationException ce = new ConfigurationException(SPIDeployerLogger.marshalError(((BasicDConfigBeanRoot)dconfigbeanroot).getName(), var5.toString()));
         ce.initCause(var5);
         throw ce;
      }
   }

   public void restore(InputStream inputstream) throws ConfigurationException {
      ConfigHelper.checkParam("InputStream", inputstream);
      this.restore(this.parsePlan(inputstream));
   }

   public void restore(DeploymentPlanBean newPlan) throws ConfigurationException {
      ConfigHelper.checkParam("DeploymentPlanBean", newPlan);
      if (debug) {
         Debug.say("start restore new plan");
      }

      Map saveDCBRoots = this.ddBeanRoots;
      DeploymentPlanBean savePlan = this.plan;
      String saveAppName = this.appName;
      InstallDir saveInstallDir = this.installDir;
      ArrayList saveDObjects = this.dObjects;

      try {
         this.ddBeanRoots = new HashMap();
         this.dObjects = new ArrayList();
         this.plan = newPlan;
         this.planRestored = true;
         this.initialize();
         SPIDeployerLogger.logRestore(this.plan.getApplicationName());
      } catch (Throwable var12) {
         ConfigurationException ce;
         if (var12 instanceof ConfigurationException) {
            ce = (ConfigurationException)var12;
         } else {
            ce = new ConfigurationException(SPIDeployerLogger.restoreError(var12.getMessage()));
            ce.initCause(var12);
         }

         this.dObjects = saveDObjects;
         this.ddBeanRoots = saveDCBRoots;
         this.plan = savePlan;
         this.appName = saveAppName;
         this.installDir = saveInstallDir;
         throw ce;
      } finally {
         this.unregisterListener(savePlan);
      }

   }

   private void unregisterListener(DeploymentPlanBean plan) {
      if (plan != null) {
         this.getDescriptorHelper().removePropertyChangeListener((DescriptorBean)plan, this);
      }

   }

   public void save(OutputStream outputstream) throws ConfigurationException {
      ConfigHelper.checkParam("OutputStream", outputstream);

      try {
         this.writeConfig();
         if (debug) {
            Debug.say("Saving following plan: ");
            this.toXML((DescriptorBean)this.plan, System.out);
         }

         this.toXML((DescriptorBean)this.plan, outputstream);
         SPIDeployerLogger.logSave(this.appName);
      } catch (IOException var3) {
         throw new ConfigurationException(SPIDeployerLogger.marshalPlanError(var3.toString()));
      }
   }

   private boolean hasExternalDDs() {
      ModuleOverrideBean[] mos = this.plan.getModuleOverrides();

      for(int i = 0; i < mos.length; ++i) {
         ModuleOverrideBean mo = mos[i];
         ModuleDescriptorBean[] mds = mo.getModuleDescriptors();

         for(int j = 0; j < mds.length; ++j) {
            ModuleDescriptorBean md = mds[j];
            if (md.isExternal()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean isPlanRestored() {
      return this.planRestored;
   }

   public InstallDir getInstallDir() {
      return this.installDir;
   }

   public void export(int type) throws IllegalArgumentException {
      this.export(type, false, (String)null);
   }

   public void export(int type, boolean std) throws IllegalArgumentException {
      this.export(type, std, (String)null);
   }

   public void export(int type, boolean std, String module_uri) throws IllegalArgumentException {
      if (module_uri != null) {
         if (this.dObject instanceof J2eeApplicationObject) {
            this.buildPlan(module_uri);
            J2eeApplicationObject jao = (J2eeApplicationObject)this.dObject;
            DeployableObject dobj = jao.getDeployableObject(module_uri);
            if (dobj != null) {
               DDBeanRoot root = dobj.getDDBeanRoot();

               try {
                  DConfigBeanRoot cbr = this.getDConfigBeanRoot(root);
                  WebLogicDConfigBeanRoot o = (WebLogicDConfigBeanRoot)cbr;
                  this.exportDConfigBeanRoot(type, o);
                  if (std) {
                     this.exportDDBeanRoot(type, root);
                  }
               } catch (ConfigurationException var9) {
                  throw new IllegalArgumentException(var9);
               }
            }
         }
      } else {
         Iterator dcbs = this.ddBeanRoots.values().iterator();

         while(dcbs.hasNext()) {
            WebLogicDConfigBeanRoot o = (WebLogicDConfigBeanRoot)dcbs.next();
            this.exportDConfigBeanRoot(type, o);
         }

         if (std) {
            Iterator names = this.rootMap.entrySet().iterator();

            while(names.hasNext()) {
               Map.Entry entry = (Map.Entry)names.next();
               DDBeanRoot root = (DDBeanRoot)entry.getValue();
               this.exportDDBeanRoot(type, root);
            }
         }
      }

   }

   private void exportDDBeanRoot(int type, DDBeanRoot root) {
      if (root != null) {
         if (root instanceof WebLogicDDBeanRoot) {
            WebLogicDDBeanRoot impl = (WebLogicDDBeanRoot)root;
            impl.export(type);
         }

      }
   }

   private void exportDConfigBeanRoot(int type, WebLogicDConfigBeanRoot root) {
      if (root != null) {
         root.export(type);
         DConfigBean[] dcbs2 = (DConfigBean[])root.getSecondaryDescriptors();

         for(int i = 0; i < dcbs2.length; ++i) {
            WebLogicDConfigBeanRoot root2 = (WebLogicDConfigBeanRoot)dcbs2[i];
            if (root2 != null) {
               root2.export(type);
            }
         }

      }
   }

   public DeploymentPlanBean getPlan() {
      return this.plan;
   }

   public void addToPlan(WebLogicDConfigBeanRoot root) {
      String rootName = root.getDConfigName();
      ModuleOverrideBean mo = this.findModuleOverride(rootName);
      if (mo == null) {
         if (debug) {
            Debug.say("creating module override for " + rootName);
         }

         mo = this.plan.createModuleOverride();
         mo.setModuleName(rootName);
         mo.setModuleType(root.getDDBean().getRoot().getType().toString());
      }

      String dcbUri = root.getDescriptorSupport().getConfigURI();
      if (debug) {
         Debug.say("creating module override descriptor for " + root + " at " + dcbUri);
      }

      this.addDescriptor(mo, dcbUri, root.getDescriptorSupport().getConfigTag(), root.isExternal());
      dcbUri = root.getDescriptorSupport().getBaseURI();
      if (debug) {
         Debug.say("creating module override descriptor for " + root + " at " + dcbUri);
      }

      this.addDescriptor(mo, dcbUri, root.getDescriptorSupport().getBaseTag(), false);
   }

   private void addDescriptor(ModuleOverrideBean mo, String uri, String tag, boolean external) {
      ModuleDescriptorBean md = this.findModuleDescriptor(mo, uri);
      if (md == null) {
         md = mo.createModuleDescriptor();
         md.setRootElement(tag);
         md.setUri(uri);
         md.setExternal(external);
      }

   }

   private ModuleOverrideBean findModuleOverride(String name) {
      ModuleOverrideBean[] mobs = this.plan.getModuleOverrides();
      if (mobs == null) {
         return null;
      } else {
         for(int i = 0; i < mobs.length; ++i) {
            ModuleOverrideBean mob = mobs[i];
            if (name.equals(mob.getModuleName())) {
               return mob;
            }
         }

         return null;
      }
   }

   private ModuleDescriptorBean findModuleDescriptor(ModuleOverrideBean mo, String uri) {
      if (mo == null) {
         return null;
      } else {
         ModuleDescriptorBean[] mdbs = mo.getModuleDescriptors();
         if (mdbs == null) {
            return null;
         } else {
            for(int i = 0; i < mdbs.length; ++i) {
               ModuleDescriptorBean mdb = mdbs[i];
               if (uri.equals(mdb.getUri())) {
                  return mdb;
               }
            }

            return null;
         }
      }
   }

   private void removeFromPlan(BasicDConfigBeanRoot root) {
      String rootName = root.getDConfigName();
      ModuleOverrideBean mo = this.findModuleOverride(rootName);
      if (mo != null) {
         String dcbUri = root.getDescriptorSupport().getConfigURI();
         ModuleDescriptorBean md = this.findModuleDescriptor(mo, dcbUri);
         if (md != null) {
            mo.destroyModuleDescriptor(md);
         }

         dcbUri = root.getDescriptorSupport().getBaseURI();
         md = this.findModuleDescriptor(mo, dcbUri);
         if (md != null) {
            mo.destroyModuleDescriptor(md);
         }

         Iterator dcbs = Arrays.asList((Object[])root.getSecondaryDescriptors()).iterator();

         while(dcbs.hasNext()) {
            this.removeFromPlan((BasicDConfigBeanRoot)dcbs.next());
         }

      }
   }

   private boolean writeConfig() throws ConfigurationException {
      boolean ext = false;
      Iterator dcbs = this.ddBeanRoots.values().iterator();

      while(dcbs.hasNext()) {
         try {
            WebLogicDConfigBeanRoot dcb = (WebLogicDConfigBeanRoot)dcbs.next();
            if (dcb != null) {
               if (this.writeConfigToFile(dcb)) {
                  ext = true;
               }

               if (this.writeSecondaryConfig(dcb)) {
                  ext = true;
               }
            }
         } catch (IOException var5) {
            throw new SPIConfigurationException(var5.toString(), var5);
         }
      }

      return ext;
   }

   private boolean writeSecondaryConfig(WebLogicDConfigBeanRoot dcb) throws IOException {
      boolean ext = false;
      Iterator dcbs = Arrays.asList((Object[])dcb.getSecondaryDescriptors()).iterator();

      while(dcbs.hasNext()) {
         if (this.writeConfigToFile((WebLogicDConfigBeanRoot)dcbs.next())) {
            ext = true;
         }
      }

      return ext;
   }

   private boolean writeConfigToFile(WebLogicDConfigBeanRoot dcb) throws IOException {
      if (dcb == null) {
         return false;
      } else {
         if (debug) {
            Debug.say("hasdd: " + dcb.hasDD() + ", is external:" + dcb.isExternal() + ", is modified:" + dcb.isModified());
         }

         if ((!dcb.hasDD() || dcb.isExternal()) && dcb.isModified()) {
            String dcbUri = dcb.getDescriptorSupport().getConfigURI();
            if (debug) {
               Debug.say("writing dd for module, " + dcb.getDConfigName() + ", with uri, " + dcbUri);
            }

            try {
               ((BasicDConfigBeanRoot)dcb).clearUnmodifiedElementsFromDescriptor();
               File ddFile = this.writeDDToPlanDir(dcb, dcbUri, dcb.getDescriptorBean());
               ((BasicDConfigBeanRoot)dcb).restoreUnmodifiedElementsToDescriptor();
               ModuleDescriptorBean md = this.findModuleDescriptor(this.findModuleOverride(dcb.getDConfigName()), dcbUri);
               if (md != null) {
                  md.setHashCode(Long.toString(System.currentTimeMillis()));
               }

               SPIDeployerLogger.logSaveDD(ddFile.getPath());
            } catch (IOException var6) {
               IOException ioe2 = new IOException(SPIDeployerLogger.marshalError(((BasicDConfigBeanRoot)dcb).getName(), var6.toString()));
               ioe2.initCause(var6);
               throw ioe2;
            }
         } else if (debug) {
            SPIDeployerLogger.logNoSave(dcb.getDConfigName(), dcb.hasDD(), !dcb.isModified());
         }

         return dcb.isExternal();
      }
   }

   private WebLogicDConfigBeanRoot constructDCB(DDBeanRoot root, Object sourceObject, String dcbClassName, String name, DescriptorSupport ds) throws Throwable {
      ClassLoaderControl clf = this.pushClassLoader(root.getDeployableObject());

      WebLogicDConfigBeanRoot var17;
      try {
         Class clazz = Class.forName(dcbClassName, false, Thread.currentThread().getContextClassLoader());
         if (debug) {
            Debug.say("name : " + name);
            Debug.say("Loaded class: " + clazz.getName());
            Debug.say("this class loader: " + clazz.getClassLoader());

            for(ClassLoader cl = clazz.getClassLoader().getParent(); cl != null; cl = cl.getParent()) {
               Debug.say("parent context loader: " + cl);
            }

            Object o = Thread.currentThread().getContextClassLoader().getResource("binding.jar");
            if (o != null) {
               Debug.say("binding url: " + o.toString());
            }
         }

         Constructor cons;
         if (sourceObject != null) {
            if (sourceObject instanceof DescriptorBean) {
               cons = clazz.getDeclaredConstructor(DDBeanRoot.class, WebLogicDeploymentConfiguration.class, DescriptorBean.class, String.class, DescriptorSupport.class);
            } else {
               cons = clazz.getDeclaredConstructor(DDBeanRoot.class, WebLogicDeploymentConfiguration.class, Object.class, String.class, DescriptorSupport.class);
            }

            var17 = (WebLogicDConfigBeanRoot)cons.newInstance(root, this, sourceObject, name, ds);
            return var17;
         }

         cons = clazz.getDeclaredConstructor(DDBeanRoot.class, WebLogicDeploymentConfiguration.class, String.class, DescriptorSupport.class);
         var17 = (WebLogicDConfigBeanRoot)cons.newInstance(root, this, name, ds);
      } catch (Throwable var13) {
         Throwable c = var13.getCause();
         if (c == null) {
            throw var13;
         }

         throw c;
      } finally {
         this.popClassLoader(clf);
      }

      return var17;
   }

   private void popClassLoader(ClassLoaderControl clf) {
      if (clf != null) {
         clf.restoreClassloader();
      }

   }

   private ClassLoaderControl pushClassLoader(DeployableObject dobj) throws IOException {
      if (!(dobj instanceof WebLogicDeployableObject)) {
         return null;
      } else {
         Thread th = Thread.currentThread();
         ClassLoaderControl clf = new ClassLoaderControl((WebLogicDeployableObject)dobj);
         th.setContextClassLoader(clf.getClassLoader());
         return clf;
      }
   }

   private DeploymentPlanBean buildPlan() {
      if (debug) {
         Debug.say("building deployment plan");
      }

      this.plan = (DeploymentPlanBean)DescriptorParser.getWLSEditableDescriptorBean(DeploymentPlanBean.class);
      this.plan.setApplicationName(this.appName);
      this.plan.setConfigRoot(this.installDir.getConfigDir().getPath());
      Iterator roots = this.rootMap.entrySet().iterator();

      while(roots.hasNext()) {
         Map.Entry entry = (Map.Entry)roots.next();
         DeployableObjectKey key = (DeployableObjectKey)entry.getKey();
         String rootName = key.getName();
         DDBeanRoot ddroot = (DDBeanRoot)this.rootMap.get(key);
         WebLogicDConfigBeanRoot root = (WebLogicDConfigBeanRoot)this.ddBeanRoots.get(ddroot);
         if (root != null) {
            this.addToPlan(root);
         }
      }

      return this.plan;
   }

   private DeploymentPlanBean buildPlan(String module_uri) {
      if (debug) {
         Debug.say("building deployment plan");
      }

      this.plan = (DeploymentPlanBean)DescriptorParser.getWLSEditableDescriptorBean(DeploymentPlanBean.class);
      this.plan.setApplicationName(this.appName);
      this.plan.setConfigRoot(this.installDir.getConfigDir().getPath());
      Iterator roots = this.rootMap.entrySet().iterator();

      while(roots.hasNext()) {
         Map.Entry entry = (Map.Entry)roots.next();
         DeployableObjectKey key = (DeployableObjectKey)entry.getKey();
         String rootName = key.getName();
         if (rootName.equals(module_uri)) {
            DDBeanRoot ddroot = (DDBeanRoot)this.rootMap.get(key);
            WebLogicDConfigBeanRoot root = (WebLogicDConfigBeanRoot)this.ddBeanRoots.get(ddroot);
            if (root != null) {
               this.addToPlan(root);
            }
         }
      }

      return this.plan;
   }

   private void toXML(DescriptorBean root, OutputStream os) throws IOException {
      (new EditableDescriptorManager()).writeDescriptorAsXML(root.getDescriptor(), os);
   }

   public WebLogicDConfigBeanRoot initDConfig(DDBeanRoot ddroot, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      WebLogicDConfigBeanRoot dcb = null;
      String dcbClassName = null;

      try {
         String uri = ds.getConfigURI();
         DeployableObject dObj = ddroot.getDeployableObject();
         if (debug) {
            Debug.say("looking for " + name + " on dobj: " + dObj);
         }

         if (dObj instanceof WebLogicDeployableObject) {
            boolean isExt = false;
            WebLogicDeployableObject wdo = (WebLogicDeployableObject)dObj;
            if (wdo.hasDDBean(uri)) {
               DDBeanRootImpl wddr = (DDBeanRootImpl)wdo.getDDBeanRoot(uri);
               if (debug) {
                  Debug.say("Getting DConfig information from ddroot :\n" + wddr + " of type: " + wddr.getType());
               }

               DescriptorBean dbean = wddr.getDescriptorBean();
               DDBeanRootImpl std = (DDBeanRootImpl)wdo.getDDBeanRoot();
               std.setAppName(name);
               if (std.getDescriptorBean() == null) {
                  throw new BeanNotFoundException(std.toString());
               }

               std.registerAsListener(this, std.getDescriptorBean());
               dcbClassName = this.convertImplToDConfig(dbean);
               if (debug) {
                  Debug.say("will create new " + wddr.getType() + " dcb, " + name + ", of class, " + dcbClassName + " with existing bean");
               }

               dcb = this.constructDCB(ddroot, dbean, dcbClassName, name, ds);
               InputStream is = null;

               try {
                  is = dObj.getEntry(uri);
                  if (is == null) {
                     isExt = true;
                  }
               } finally {
                  if (is != null) {
                     try {
                        is.close();
                     } catch (IOException var21) {
                     }
                  }

               }
            } else {
               dcbClassName = ds.getDConfigClassName();
               if (debug) {
                  Debug.say("will create new dcb, " + name + ", of class, " + dcbClassName);
               }

               dcb = this.constructDCB(ddroot, (Object)null, dcbClassName, name, ds);
               isExt = true;
            }

            dcb.setExternal(isExt);
            if (dcb.getDescriptorBean() != null) {
               ((BasicDConfigBeanRoot)dcb).registerAsListener(dcb.getDescriptorBean());
            }
         }

         return dcb;
      } catch (Throwable var23) {
         if (var23 instanceof InvalidModuleException) {
            throw (InvalidModuleException)var23;
         } else if (var23 instanceof IOException) {
            throw (IOException)var23;
         } else {
            if (debug) {
               var23.printStackTrace();
            }

            InvalidModuleException e2 = new InvalidModuleException(SPIDeployerLogger.invalidClass(dcbClassName, var23.toString()));
            e2.initCause(var23);
            throw e2;
         }
      }
   }

   private String convertImplToDConfig(DescriptorBean dbean) {
      String classname = dbean.toString();
      if (classname.indexOf("Impl") != -1) {
         classname = classname.substring(0, classname.indexOf("Impl"));
      }

      return classname + "DConfig";
   }

   private String getNameFromUri(String uri) {
      return uri;
   }

   private void initEAR() {
      DeployableObject[] dobjs = ((J2eeApplicationObject)this.dObject).getDeployableObjects();
      String[] uris = ((J2eeApplicationObject)this.dObject).getModuleUris();
      if (dobjs != null && uris != null) {
         for(int i = 0; i < dobjs.length; ++i) {
            this.dObjects.add(dobjs[i]);
            if (debug) {
               Debug.say("initing roots for " + this.getNameFromUri(uris[i]) + " with uri " + uris[i]);
            }

            DDBeanRoot ddbeanroot = dobjs[i].getDDBeanRoot();
            ModuleType type = ddbeanroot.getDeployableObject().getType();
            DeployableObjectKey key = new DeployableObjectKey(this.getNameFromUri(uris[i]), type);
            if (type == ModuleType.WAR) {
               DeployableObject dobj = ddbeanroot.getDeployableObject();
               if (dobj instanceof WebLogicDeployableObject) {
                  WebLogicDeployableObject wlObject = (WebLogicDeployableObject)dobj;
                  key.setContextRoot(wlObject.getContextRoot());
               }
            }

            this.rootMap.put(key, ddbeanroot);
            this.ddBeanRoots.put(ddbeanroot, (Object)null);
         }

      }
   }

   private void initSCA() {
      ScaApplicationObject scaObj = (ScaApplicationObject)this.dObject;
      DeployableObject[] dObjs = scaObj.getDeployableObjects();
      if (dObjs != null) {
         for(int i = 0; i < dObjs.length; ++i) {
            if (dObjs[i] instanceof WebLogicDeployableObject) {
               WebLogicDeployableObject wdo = (WebLogicDeployableObject)dObjs[i];
               ModuleType type = wdo.getType();
               if (type == ModuleType.WAR || type == ModuleType.EJB) {
                  this.dObjects.add(wdo);
                  String uri = wdo.getUri();
                  DeployableObjectKey key = new DeployableObjectKey(this.getNameFromUri(uri), type);
                  if (type == ModuleType.WAR) {
                     key.setContextRoot(wdo.getContextRoot());
                  }

                  DDBeanRoot ddbeanroot = wdo.getDDBeanRoot();
                  this.rootMap.put(key, ddbeanroot);
                  this.ddBeanRoots.put(ddbeanroot, (Object)null);
               }
            }
         }

      }
   }

   private void initialize() throws IOException, InvalidModuleException {
      File inst = null;
      this.appName = null;
      String partitionName = null;
      WebLogicDeployableObject wldObject;
      if (this.plan != null) {
         ModuleOverrideBean rm = this.plan.findRootModule();
         if (rm != null) {
            this.appName = rm.getModuleName();
         } else if (this.dObject instanceof WebLogicDeployableObject) {
            wldObject = (WebLogicDeployableObject)this.dObject;
            this.appName = wldObject.getArchive().getName();
         } else {
            this.appName = this.plan.getApplicationName();
         }

         inst = ConfigHelper.getAppRootFromPlan(this.plan);
      }

      if (inst == null && this.dObject instanceof WebLogicDeployableObject) {
         WebLogicDeployableObject wldObject = (WebLogicDeployableObject)this.dObject;
         this.appName = wldObject.getArchive().getName();
         inst = wldObject.getInstallDirPath();
         partitionName = wldObject.getPartitionName();
      }

      if (this.appName == null) {
         this.appName = "MyApp";
      }

      if (debug) {
         Debug.say("initing roots with " + this.appName);
      }

      if (this.dObject instanceof ScaApplicationObject) {
         this.initSCA();
      } else {
         this.dObjects.add(this.dObject);
         DeployableObjectKey key = new DeployableObjectKey(this.appName, this.dObject.getType());
         if (this.dObject.getType() == ModuleType.WAR && this.dObject instanceof WebLogicDeployableObject) {
            wldObject = (WebLogicDeployableObject)this.dObject;
            key.setContextRoot(wldObject.getContextRoot());
         }

         this.rootMap.put(key, this.dObject.getDDBeanRoot());
         this.ddBeanRoots.put(this.dObject.getDDBeanRoot(), (Object)null);
         if (this.dObject instanceof J2eeApplicationObject) {
            this.initEAR();
         }
      }

      this.installDir = new InstallDir(this.appName, inst);
      if (partitionName != null) {
         this.installDir.setConfigDir(new File(inst + File.separator + partitionName + File.separator + this.appName + File.separator + "app", "plan"));
      }

      this.initPlanArtifacts();
      if (this.plan != null) {
         try {
            this.genDConfigBeans();
         } catch (ConfigurationException var5) {
            if (debug) {
               var5.printStackTrace();
            }

            InvalidModuleException e2 = new InvalidModuleException(var5.getMessage());
            e2.initCause(var5);
            throw e2;
         }
      }

   }

   private void initPlanArtifacts() {
      if (this.plan != null) {
         ConfigHelper.initPlanDirFromPlan(this.plan, this.installDir);
         this.registerListener(this.plan);
      }

   }

   private void registerListener(DeploymentPlanBean plan) {
      if (plan != null) {
         this.getDescriptorHelper().addPropertyChangeListener((DescriptorBean)plan, this);
      }

   }

   private DescriptorHelper getDescriptorHelper() {
      if (this.ddhelper == null) {
         this.ddhelper = DescriptorHelper.getInstance();
      }

      return this.ddhelper;
   }

   private DeploymentPlanBean parsePlan(InputStream is) throws ConfigurationException {
      try {
         return DescriptorParser.parseDeploymentPlan(is);
      } catch (IOException var5) {
         Throwable t = ManagementException.unWrapExceptions(var5);
         ConfigurationException ce = new ConfigurationException(SPIDeployerLogger.badPlan(t.getMessage()));
         ce.initCause(t);
         throw ce;
      }
   }

   private void genDConfigBeans() throws ConfigurationException {
      Iterator roots = this.ddBeanRoots.keySet().iterator();

      while(roots.hasNext()) {
         DDBeanRoot root = (DDBeanRoot)roots.next();
         DConfigBeanRoot dcb = this.getDConfigBeanRoot(root);
         this.collectSecondaryConfig((WebLogicDConfigBeanRoot)dcb);
      }

   }

   private void collectSecondaryConfig(WebLogicDConfigBeanRoot dcb) throws ConfigurationException {
      DeployableObject dobj = dcb.getDDBean().getRoot().getDeployableObject();
      if (debug) {
         Debug.say("checking for secondary dd's for " + dobj.getType().toString() + ":" + dcb.getDConfigName());
      }

      DescriptorSupport ds = null;
      DescriptorSupport[] dss = DescriptorSupportManager.getForModuleType(dobj.getType());

      int i;
      for(i = 0; i < dss.length; ++i) {
         ds = dss[i];
         if (!ds.isPrimary()) {
            if (debug) {
               Debug.say("found secondary dd candidate: " + ds.toString());
            }

            if (ds.getBaseTag().equals("weblogic-rdbms-jar")) {
               this.collectSecondaryEjbConfig(dcb, dobj);
            } else if (this.filterByType(ds)) {
               this.addSecondaryModule(dobj, dcb, ds, false);
            }
         }
      }

      this.collectWlsModules(dcb, dobj);
      if (ModuleType.EJB.equals(dobj.getType()) || ModuleType.WAR.equals(dobj.getType())) {
         dss = DescriptorSupportManager.getForModuleType(WebLogicModuleType.WSEE);

         for(i = 0; i < dss.length; ++i) {
            ds = dss[i];
            if ((ModuleType.EJB.equals(dobj.getType()) && ds.getConfigURI().startsWith("META-INF") || ModuleType.WAR.equals(dobj.getType()) && ds.getConfigURI().startsWith("WEB-INF")) && this.checkForDepenedentDD(dobj, ds)) {
               this.addSecondaryModule(dobj, dcb, ds, false);
            }
         }
      }

      if (ModuleType.EAR.equals(dobj.getType()) || ModuleType.WAR.equals(dobj.getType())) {
         dss = DescriptorSupportManager.getForModuleType(WebLogicModuleType.CONFIG);

         for(i = 0; i < dss.length; ++i) {
            ds = dss[i];
            if (debug) {
               Debug.say("Adding secondary module to wdo : " + dobj + " at " + ds.getBaseURI());
            }

            this.addSecondaryModule(dobj, dcb, ds, false);
         }
      }

      this.collectPersistenceDescriptors(dcb);
   }

   private void collectPersistenceDescriptors(WebLogicDConfigBeanRoot dcb) throws ConfigurationException {
      WebLogicDeployableObject deployableObject = (WebLogicDeployableObject)dcb.getDDBean().getRoot().getDeployableObject();
      List persistence = Collections.list(deployableObject.getDDResourceEntries("META-INF/persistence.xml"));
      List persistenceConfig = Collections.list(deployableObject.getDDResourceEntries("META-INF/persistence-configuration.xml"));
      if (!persistence.isEmpty()) {
         Iterator listIt = persistence.iterator();

         while(listIt.hasNext()) {
            String persistenceURI = (String)listIt.next();
            String persistenceConfigURI = this.getPersistenceConfigURI(persistenceURI);
            DescriptorSupport ds = this.getDescriptorSupportforPersistenceArchive(deployableObject.getType(), persistenceURI, persistenceConfigURI);
            this.addSecondaryModule(deployableObject, dcb, ds, false);
         }

      }
   }

   private DescriptorSupport getDescriptorSupportforPersistenceArchive(ModuleType mt, String baseURI, String configURI) {
      return new DescriptorSupport(mt, "persistence", "persistence-configuration", "http://java.sun.com/xml/ns/persistence", "http://bea.com/ns/weblogic/950/persistence", baseURI, configURI, "weblogic.j2ee.descriptor.PersistenceBean", "kodo.jdbc.conf.descriptor.PersistenceConfigurationBean", "kodo.jdbc.conf.descriptor.PersistenceConfigurationBeanDConfig", false);
   }

   private String getPersistenceConfigURI(String persistenceURI) {
      String subString = persistenceURI.substring(0, persistenceURI.length() - "META-INF/persistence.xml".length());
      return subString + "META-INF/persistence-configuration.xml";
   }

   private boolean checkForDepenedentDD(DeployableObject dobj, DescriptorSupport ds) {
      if (dobj instanceof WebLogicDeployableObject) {
         WebLogicDeployableObject wdo = (WebLogicDeployableObject)dobj;

         try {
            if (ds.getConfigURI().equals("META-INF/weblogic-webservices-policy.xml")) {
               return wdo.hasDDBean("META-INF/weblogic-webservices.xml");
            }

            if (ds.getConfigURI().equals("WEB-INF/weblogic-webservices-policy.xml")) {
               return wdo.hasDDBean("WEB-INF/weblogic-webservices.xml");
            }
         } catch (FileNotFoundException var5) {
         }
      }

      return true;
   }

   private void safeClose(InputStream is) {
      if (is != null) {
         try {
            is.close();
         } catch (IOException var3) {
         }
      }

   }

   private void addSecondaryModule(DeployableObject dobj, WebLogicDConfigBeanRoot dcb, DescriptorSupport ds, boolean expected) throws ConfigurationException {
      String uri = ds.getBaseURI();

      try {
         if (debug) {
            Debug.say("linking secondary dd at " + uri + " on " + dobj);
         }

         DDBeanRoot dd = this.getOrCreateDDBeanRoot(dobj, dcb, uri);
         DConfigBean dcb2 = dcb.getDConfigBean(dd, ds);
         if (dcb2 != null) {
            this.addToPlan((WebLogicDConfigBeanRoot)dcb2);
         }
      } catch (FileNotFoundException var10) {
         if (expected) {
            Loggable l = SPIDeployerLogger.logMissingDDLoggable(uri, ds.getModuleType().toString());
            throw new ConfigurationException(l.getMessage());
         }

         if (debug) {
            Debug.say("no dd found or created for " + uri + ": " + var10.toString());
         }
      } catch (DDBeanCreateException var11) {
         throw new ConfigurationException(SPIDeployerLogger.getDDBeanCreateError(uri, var11.getMessage()));
      }

   }

   private DDBeanRoot getOrCreateDDBeanRoot(DeployableObject dobj, WebLogicDConfigBeanRoot dcb, String uri) throws FileNotFoundException, DDBeanCreateException {
      DDBeanRoot dd = null;

      try {
         dd = dobj.getDDBeanRoot(uri);
      } catch (FileNotFoundException var11) {
         if (debug) {
            Debug.say("Could not get the DDBean root for " + dobj + " at uri: " + uri);
         }

         DescriptorSupport ds = DescriptorSupportManager.getForceWriteDS(uri);
         if (ds != null) {
            if (debug) {
               Debug.say("Creating dd for " + uri);
            }

            try {
               String c = ds.getStandardClassName();
               c = c.substring(0, c.length() - 4);
               DescriptorBean bean = DescriptorParser.getWLSEditableDescriptorBean(Class.forName(c));
               this.writeDDToPlanDir(dcb, uri, bean);
               dd = dobj.getDDBeanRoot(uri);
            } catch (ClassNotFoundException var9) {
               throw new FileNotFoundException(uri);
            } catch (IOException var10) {
               if (var10 instanceof FileNotFoundException) {
                  throw (FileNotFoundException)var10;
               }

               throw new FileNotFoundException(uri);
            }
         }
      }

      if (dd == null) {
         throw new FileNotFoundException(uri);
      } else {
         return dd;
      }
   }

   private File writeDDToPlanDir(WebLogicDConfigBeanRoot dcb, String uri, DescriptorBean bean) throws IOException {
      FileOutputStream out = null;

      File f;
      try {
         if (this.dObject == dcb.getDDBean().getRoot().getDeployableObject()) {
            f = this.installDir.getAppDDFile(uri);
         } else {
            f = this.installDir.getDDFile(dcb.getDConfigName(), uri);
         }

         out = this.installDir.getOutputStream(f);
         this.toXML(bean, out);
         this.extDDWrite = true;
      } finally {
         if (out != null) {
            out.close();
         }

      }

      return f;
   }

   private void collectWlsModules(WebLogicDConfigBeanRoot dcb, DeployableObject dobj) throws ConfigurationException {
      DescriptorSupport ds = DescriptorSupportManager.getForModuleType(WebLogicModuleType.WLDF)[0];
      this.addSecondaryModule(dobj, dcb, ds, false);
      if (dobj instanceof J2eeApplicationObject) {
         WeblogicModuleBean[] modules = ((WeblogicApplicationBean)dcb.getDescriptorBean()).getModules();
         ds = null;
         if (modules == null) {
            return;
         }

         for(int i = 0; i < modules.length; ++i) {
            WeblogicModuleBean module = modules[i];
            String type = module.getType();
            String uri = module.getPath();
            if ("JDBC".equals(type)) {
               ds = DescriptorSupportManager.getForTag("jdbc-data-source");
            } else if ("JMS".equals(type)) {
               ds = DescriptorSupportManager.getForTag("weblogic-jms");
            } else if ("Interception".equals(type)) {
               ds = DescriptorSupportManager.getForTag("weblogic-interception");
            } else {
               if (!"GAR".equals(type)) {
                  throw new AssertionError("ERROR: A module of unknown type " + type + " was found");
               }

               ds = DescriptorSupportManager.getForTag("coherence-application");
            }

            ds.setBaseURI(uri);
            ds.setConfigURI(uri);
            if (uri != null) {
               this.addSecondaryModule(dobj, dcb, ds, true);
            }
         }
      }

   }

   private void collectSecondaryEjbConfig(WebLogicDConfigBeanRoot dcb, DeployableObject dobj) throws ConfigurationException {
      WeblogicEjbJarBean ejb = (WeblogicEjbJarBean)dcb.getDescriptorBean();
      WeblogicEnterpriseBeanBean[] webbs = ejb.getWeblogicEnterpriseBeans();
      if (webbs != null) {
         for(int i = 0; i < webbs.length; ++i) {
            DescriptorSupport ds = null;
            if (webbs[i].isEntityDescriptorSet()) {
               EntityDescriptorBean ed = webbs[i].getEntityDescriptor();
               if (ed.isPersistenceSet()) {
                  PersistenceBean pb = ed.getPersistence();
                  if (pb.isPersistenceUseSet()) {
                     PersistenceUseBean pu = pb.getPersistenceUse();
                     String typeId = pu.getTypeIdentifier();
                     if (debug) {
                        Debug.say("checking pu: " + typeId);
                     }

                     if ("WebLogic_CMP_RDBMS".equals(typeId)) {
                        ds = DescriptorSupportManager.getForSecondaryTag("weblogic-rdbms-jar")[0];
                     }

                     if (ds != null && pu.getTypeStorage() != null) {
                        if (debug) {
                           Debug.say("changing TypeStorage to " + pu.getTypeStorage());
                        }

                        ds.setBaseURI(pu.getTypeStorage());
                        ds.setConfigURI(pu.getTypeStorage());
                        if ("5.1.0".equals(pu.getTypeVersion())) {
                           ds.setBaseNameSpace("http://www.bea.com/ns/weblogic/60");
                           ds.setConfigNameSpace("http://www.bea.com/ns/weblogic/60");
                           ds.setStandardClassName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanImpl");
                           ds.setConfigClassName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanImpl");
                           ds.setDConfigClassName("weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanDConfig");
                        }

                        DescriptorSupportManager.add(ds);
                        this.addSecondaryModule(dobj, dcb, ds, true);
                     }
                  }
               }
            }
         }

      }
   }

   private boolean filterByType(DescriptorSupport ds) {
      return ds.getModuleType().getValue() != WebLogicModuleType.CONFIG.getValue();
   }

   private void removeSecondaryDConfigBean(DConfigBean dcb) throws BeanNotFoundException {
   }

   public String getRootTag(DDBeanRoot ddb) throws ConfigurationException {
      String xp = ddb.getXpath();
      if (!"/".equals(xp)) {
         throw new IllegalArgumentException(SPIDeployerLogger.badRootBean(ddb.toString()));
      } else {
         DescriptorSupport[] ds = DescriptorSupportManager.getForModuleType(ddb.getType());
         if (ds != null && ds.length != 0) {
            if (ds.length == 1) {
               return ds[0].getBaseTag();
            } else {
               for(int i = 0; i < ds.length; ++i) {
                  String ns = ConfigHelper.getNSPrefix(ddb, ds[i].getBaseNameSpace());
                  DDBean[] dd = ddb.getChildBean(ConfigHelper.applyNamespace(ns, ds[i].getBaseTag()));
                  if (dd != null) {
                     return ds[i].getBaseTag();
                  }
               }

               if (ddb.getType() == ModuleType.EJB) {
                  return DescriptorSupportManager.EJB_DESC_SUPPORT.getBaseTag();
               } else {
                  WebLogicDeployableObject wdobj = (WebLogicDeployableObject)ddb.getDeployableObject();
                  throw new ConfigurationException(SPIDeployerLogger.noTagFound(ddb.getType().toString(), wdobj.getUri(), ddb.getFilename()));
               }
            }
         } else {
            throw new ConfigurationException(SPIDeployerLogger.noTagRegistered(ddb.getType().toString()));
         }
      }
   }

   public void propertyChange(PropertyChangeEvent evt) {
      String p = evt.getPropertyName();
      if ("ConfigRoot".equals(p)) {
         File f = null;
         if (this.plan.getConfigRoot() != null) {
            f = (new File(this.plan.getConfigRoot())).getAbsoluteFile();
            f.mkdirs();
         }

         if (f != null && (!f.exists() || !f.isDirectory())) {
            if (debug) {
               Debug.say("Ignoring invalid change to config root: " + this.plan.getConfigRoot());
            }
         } else {
            this.installDir.setConfigDir(f);
         }
      }

   }
}
