package weblogic.deploy.api.spi.config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.DConfigBeanRoot;
import javax.enterprise.deploy.spi.DeploymentConfiguration;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelperLowLevel;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.model.WebLogicDDBeanRoot;
import weblogic.deploy.api.model.WebLogicDeployableObjectInterface;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.spi.WebLogicDConfigBeanRoot;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.ConfigurationSupportBean;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.j2ee.descriptor.wl.VariableBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.logging.Loggable;
import weblogic.utils.LocatorUtilities;

public abstract class BasicDConfigBeanRoot extends BasicDConfigBean implements WebLogicDConfigBeanRoot, PropertyChangeListener, VetoableChangeListener {
   private static final boolean debug = Debug.isDebug("config");
   private static final boolean allowNonConfigurableChanges = Boolean.getBoolean("weblogic.deploy.api.AllowNonConfigurableChanges");
   protected DeployableObject dObject;
   private DescriptorParser dom = null;
   private boolean ddProvided = true;
   private boolean isSchema = true;
   Map secondaryRoots = Collections.synchronizedMap(new HashMap());
   private boolean external = false;
   private String namespace = null;
   private static DescriptorSupportManagerInterface descriptorSupportManager = (DescriptorSupportManagerInterface)LocatorUtilities.getService(DescriptorSupportManagerInterface.class);

   public BasicDConfigBeanRoot(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws IOException, InvalidModuleException {
      super(root);
      ConfigHelperLowLevel.checkParam("WebLogicDeploymentConfiguration", dc);
      ConfigHelperLowLevel.checkParam("name", name);
      ConfigHelperLowLevel.checkParam("DescriptorSupport", ds);
      this.setDescriptorSupport(ds);
      this.setNamespace();
      this.dObject = root.getDeployableObject();
      this.dc = dc;
      this.setAppName(name);
      if (ds.supportsConfigModules() || ds.isPrimary() && this.isAppRoot()) {
         this.addWebLogicExtensions();
      }

   }

   public void close() {
      Iterator iter = this.secondaryRoots.values().iterator();

      while(iter.hasNext()) {
         WebLogicDConfigBeanRoot secondary = (WebLogicDConfigBeanRoot)iter.next();
         if (secondary != null) {
            secondary.close();
         }
      }

      this.deregisterAsListener(this.getDescriptorBean());
      this.secondaryRoots.clear();
   }

   private boolean isAppRoot() {
      return this.dObject.equals(this.dc.getDeployableObject());
   }

   public BasicDConfigBeanRoot(DDBean ddbean) {
      super(ddbean);
   }

   public DConfigBean getDConfigBean(DDBeanRoot ddBeanRoot) {
      ConfigHelperLowLevel.checkParam("DDBeanRoot", ddBeanRoot);
      if (debug) {
         Debug.say("looking up secondary dcb for " + ddBeanRoot.toString());
      }

      DescriptorSupport ds = null;
      DConfigBeanRoot dcb = (DConfigBeanRoot)this.secondaryRoots.get(ddBeanRoot);
      if (dcb != null) {
         return dcb;
      } else {
         try {
            label57: {
               String rtag = this.dc.getRootTag(ddBeanRoot);
               if (debug) {
                  Debug.say("Looking up #2 ds for tag " + rtag);
               }

               DescriptorSupport[] dss = descriptorSupportManager.getForSecondaryTag(rtag);
               if (dss != null && dss.length != 0) {
                  int i = 0;

                  while(true) {
                     if (i >= dss.length) {
                        break label57;
                     }

                     if (debug) {
                        Debug.say("Checking " + dss[i].getBaseURI() + " against " + ddBeanRoot.getFilename());
                     }

                     if (dss[i].getBaseURI().equals(ddBeanRoot.getFilename())) {
                        ds = dss[i];
                        break label57;
                     }

                     ++i;
                  }
               }

               return null;
            }
         } catch (ConfigurationException var8) {
            SPIDeployerLogger.logNoDCB(this.getAppName(), var8.toString());
            return null;
         }

         if (ds == null) {
            return null;
         } else {
            try {
               return this.getDConfigBean(ddBeanRoot, ds);
            } catch (ConfigurationException var7) {
               SPIDeployerLogger.logDDCreateError(ds.getConfigURI());
               return null;
            }
         }
      }
   }

   private WebLogicDConfigBeanRoot findRoot(DDBeanRoot ddb) {
      Iterator roots = this.secondaryRoots.keySet().iterator();

      DDBeanRoot root;
      do {
         if (!roots.hasNext()) {
            return null;
         }

         root = (DDBeanRoot)roots.next();
      } while(!root.equals(ddb));

      return (WebLogicDConfigBeanRoot)this.secondaryRoots.get(root);
   }

   public DConfigBean getDConfigBean(DDBeanRoot ddBeanRoot, DescriptorSupport ds) throws ConfigurationException {
      DConfigBeanRoot dcb = null;

      try {
         ConfigHelperLowLevel.checkParam("DDBeanRoot", ddBeanRoot);
         ConfigHelperLowLevel.checkParam("DescriptorSupport", ds);
         dcb = this.findRoot(ddBeanRoot);
         if (dcb != null) {
            return dcb;
         } else {
            if (debug) {
               Debug.say("Creating secondary DCB for " + this.getAppName() + " at " + ds.getConfigTag() + " on ddbeanroot: \n" + ddBeanRoot);
            }

            dcb = this.dc.initDConfig(ddBeanRoot, this.getAppName(), ds);
            this.secondaryRoots.put(ddBeanRoot, dcb);
            if (dcb != null) {
               ConfigHelperLowLevel.beanWalker((DDBeanRoot)ddBeanRoot, (DConfigBeanRoot)dcb);
               this.dc.addToPlan((WebLogicDConfigBeanRoot)dcb);
            }

            return dcb;
         }
      } catch (IOException var5) {
         throw (ConfigurationException)(new ConfigurationException(var5.getMessage())).initCause(var5);
      } catch (InvalidModuleException var6) {
         throw (ConfigurationException)(new ConfigurationException(var6.getMessage())).initCause(var6);
      }
   }

   public String getDConfigName() {
      return this.getAppName();
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return (DConfigBean[])((DConfigBean[])this.secondaryRoots.values().toArray(new DConfigBean[0]));
   }

   public boolean isSchemaBased() {
      if (this.isSchema) {
         return true;
      } else {
         return this.dom == null ? false : this.dom.isSchemaBased();
      }
   }

   public boolean hasDD() {
      return this.ddProvided;
   }

   public boolean isExternal() {
      return this.external;
   }

   public void setExternal(boolean b) {
      this.external = b;
   }

   void setSchemaBased() {
      this.isSchema = true;
   }

   private void setDescriptorSupport(DescriptorSupport ds) {
      this.descriptorSupport = ds;
   }

   public DeploymentConfiguration getDeploymentConfiguration() {
      return this.dc;
   }

   public void vetoableChange(PropertyChangeEvent pce) throws PropertyVetoException {
      if (this.hasDD() && !this.isSchemaBased()) {
         Loggable l = SPIDeployerLogger.logDTDDDUpdateLoggable(pce.getPropertyName(), this.getDescriptorSupport().getConfigURI(), this.getName());
         throw new PropertyVetoException(l.getMessage(), pce);
      } else {
         this.propertyChange(pce);
      }
   }

   public void propertyChange(PropertyChangeEvent pce) {
      this.setModified(true);
      this.setPlanBasedDBean(false);
      DescriptorBean bean = (DescriptorBean)pce.getSource();
      if (debug) {
         Debug.say("PropertyChangeEvent : " + pce);
         Debug.say("PropertyChangeEvent.source : " + bean);
         Debug.say("PropertyChangeEvent.prop : " + pce.getPropertyName());
         Debug.say("PropertyChangeEvent.old : " + pce.getOldValue());
         Debug.say("PropertyChangeEvent.new : " + pce.getNewValue());
      }

      if (this.hasDD() && !this.isExternal()) {
         this.handleChange(bean, pce.getPropertyName(), pce.getOldValue(), pce.getNewValue());
      } else if (this.isExternal()) {
         BasicDConfigBean dconfig = this.findDConfigBean(this, bean);
         if (dconfig != null) {
            dconfig.setModified(true);
         }
      }

      this.reregister(bean);
   }

   public String getUri() {
      return this.getDescriptorSupport().getConfigURI();
   }

   private void setNamespace() {
      this.namespace = ConfigHelperLowLevel.getNSPrefix(this.getDDBean(), this.getDescriptorSupport().getBaseNameSpace());
   }

   protected String getNamespace() {
      return this.namespace;
   }

   public void registerChildAsListener(DescriptorBean bean) {
      if (!this.findDescriptorBean(bean) && this.bl.add(bean)) {
         if (debug) {
            Debug.say("Registering listener for " + bean.toString());
         }

         this.getDescriptorHelper().addPropertyChangeListener(bean, this);
      }

      Iterator beans = this.getDescriptorHelper().getChildren(bean);

      while(beans.hasNext()) {
         DescriptorBean o = (DescriptorBean)beans.next();
         this.registerChildAsListener(o);
      }

   }

   protected void registerWebservices(String uri) {
      if (this.dObject instanceof WebLogicDeployableObjectInterface) {
         WebLogicDeployableObjectInterface dobj = (WebLogicDeployableObjectInterface)this.dObject;
         WebLogicDDBeanRoot ddRoot = null;

         try {
            if (!dobj.hasDDBean(uri)) {
               return;
            }

            ddRoot = (WebLogicDDBeanRoot)dobj.getDDBeanRoot(uri);
         } catch (FileNotFoundException var5) {
            return;
         } catch (Exception var6) {
            throw new RuntimeException(var6);
         }

         WebLogicDConfigBeanRoot configRoot = this.findRoot(ddRoot);
         if (configRoot != null && configRoot instanceof BasicDConfigBeanRoot) {
            ((BasicDConfigBeanRoot)configRoot).registerChildAsListener(configRoot.getDescriptorBean());
         }
      }

   }

   protected void deregisterWebservices(String uri) {
      if (this.dObject instanceof WebLogicDeployableObjectInterface) {
         WebLogicDeployableObjectInterface dobj = (WebLogicDeployableObjectInterface)this.dObject;
         WebLogicDDBeanRoot ddRoot = null;

         try {
            if (!dobj.hasDDBean(uri)) {
               return;
            }

            ddRoot = (WebLogicDDBeanRoot)dobj.getDDBeanRoot(uri);
         } catch (FileNotFoundException var5) {
            return;
         } catch (Exception var6) {
            throw new RuntimeException(var6);
         }

         WebLogicDConfigBeanRoot configRoot = this.findRoot(ddRoot);
         if (configRoot != null && configRoot instanceof BasicDConfigBeanRoot) {
            ((BasicDConfigBeanRoot)configRoot).deregisterChildAsListener(configRoot.getDescriptorBean());
         }
      }

   }

   private String handleRemoveOperation(List removed, VariableBean var, Object[] newValue) {
      List values = this.extractValues(var);
      if (values.isEmpty()) {
         this.loadVariable(removed, var);
         return "remove";
      } else {
         for(int i = 0; i < removed.size(); ++i) {
            Object o = removed.get(i);
            if (values.contains(o)) {
               removed.remove(o);
               int v = values.indexOf(o);
               values.remove(v);
            }
         }

         if (removed.isEmpty()) {
            this.loadVariable(values, var);
            return null;
         } else {
            this.loadVariable(Arrays.asList(newValue), var);
            return "replace";
         }
      }
   }

   private String handleAddOperation(List added, VariableBean var) {
      List values = this.extractValues(var);
      values.addAll(added);
      this.loadVariable(values, var);
      return "add";
   }

   private List extractValues(VariableBean var) {
      if (var.getValue() == null) {
         return new ArrayList(0);
      } else {
         StringTokenizer st = new StringTokenizer(var.getValue(), ",");
         int cnt = st.countTokens();
         List vals = new ArrayList(cnt);

         for(int i = 0; i < cnt; ++i) {
            String v = st.nextToken();
            if (v.startsWith("\"")) {
               v = v.substring(1, v.length() - 1);
            }

            vals.add(v);
         }

         return vals;
      }
   }

   private void addWebLogicExtensions() throws InvalidModuleException, IOException {
      String urix;
      DescriptorSupportManagerInterface var10000;
      if (this.dObject.getType().getValue() == ModuleType.EAR.getValue()) {
         var10000 = descriptorSupportManager;
         urix = "META-INF/weblogic-extension.xml";
      } else {
         if (this.dObject.getType().getValue() != ModuleType.WAR.getValue()) {
            return;
         }

         var10000 = descriptorSupportManager;
         urix = "WEB-INF/weblogic-extension.xml";
      }

      WeblogicExtensionBean web = null;
      if (this.dObject instanceof WebLogicDeployableObjectInterface) {
         WebLogicDeployableObjectInterface wdo = (WebLogicDeployableObjectInterface)this.dObject;
         if (wdo.hasDDBean(urix)) {
            try {
               WebLogicDDBeanRoot wddr = (WebLogicDDBeanRoot)wdo.getDDBeanRoot(urix);
               if (debug) {
                  Debug.say("Getting descriptorbean from ddroot :\n" + wddr + " of type: " + wddr.getType());
               }

               web = (WeblogicExtensionBean)wddr.getDescriptorBean();
            } catch (DDBeanCreateException var16) {
               if (debug) {
                  var16.printStackTrace();
               }
            }
         }
      }

      if (web != null) {
         try {
            CustomModuleBean[] mods = web.getCustomModules();
            if (mods != null) {
               for(int i = 0; i < mods.length; ++i) {
                  CustomModuleBean mod = mods[i];
                  ConfigurationSupportBean cfg = mod.getConfigurationSupport();
                  if (cfg != null) {
                     String baseTag = cfg.getBaseRootElement();
                     if (baseTag == null) {
                        throw new InvalidModuleException(SPIDeployerLogger.getMissingExt(urix, "base-root-element", mod.getUri(), mod.getProviderName()));
                     }

                     String configTag = cfg.getConfigRootElement();
                     if (configTag == null) {
                        configTag = baseTag;
                     }

                     String baseNameSpace = cfg.getBaseNamespace();
                     if (baseNameSpace == null) {
                        throw new InvalidModuleException(SPIDeployerLogger.getMissingExt(urix, "base-namespace", mod.getUri(), mod.getProviderName()));
                     }

                     String configNameSpace = cfg.getConfigNamespace();
                     if (configNameSpace == null) {
                        configNameSpace = baseNameSpace;
                     }

                     String baseUri = cfg.getBaseUri();
                     if (baseUri == null) {
                        throw new InvalidModuleException(SPIDeployerLogger.getMissingExt(urix, "base-uri", mod.getUri(), mod.getProviderName()));
                     }

                     String configUri = cfg.getConfigUri();
                     if (configUri == null) {
                        configUri = baseUri;
                     }

                     String basePackageName = cfg.getBasePackageName();
                     if (basePackageName == null) {
                        throw new InvalidModuleException(SPIDeployerLogger.getMissingExt(urix, "base-package-name", mod.getUri(), mod.getProviderName()));
                     }

                     String configPackageName = cfg.getConfigPackageName();
                     if (configPackageName == null) {
                        configPackageName = basePackageName;
                     }

                     SPIDeployerLogger.logAddDS(baseUri, configUri);
                     descriptorSupportManager.add(WebLogicModuleType.CONFIG, baseTag, configTag, baseNameSpace, configNameSpace, baseUri, configUri, basePackageName, configPackageName);
                  }
               }

            }
         } catch (IllegalArgumentException var15) {
            throw new InvalidModuleException(var15.toString());
         }
      }
   }

   private BasicDConfigBean findDConfigBean(BasicDConfigBean dcb, DescriptorBean bean) {
      if (bean != null && dcb != null) {
         if (dcb.getDescriptorBean() == bean) {
            return dcb;
         } else {
            Iterator dcbs = dcb.getChildBeans().iterator();

            BasicDConfigBean dconfigBean;
            do {
               if (!dcbs.hasNext()) {
                  return null;
               }

               BasicDConfigBean childDCB = (BasicDConfigBean)dcbs.next();
               dconfigBean = this.findDConfigBean(childDCB, bean);
            } while(dconfigBean == null);

            return dconfigBean;
         }
      } else {
         return null;
      }
   }
}
