package weblogic.deploy.api.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.shared.factories.DeploymentFactoryManager;
import javax.enterprise.deploy.spi.DConfigBeanRoot;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import javax.enterprise.deploy.spi.factories.DeploymentFactory;
import weblogic.deploy.api.internal.Closable;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.deploy.api.internal.utils.LibrarySpec;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.model.WebLogicJ2eeApplicationObject;
import weblogic.deploy.api.model.internal.WebLogicDeployableObjectFactoryImpl;
import weblogic.deploy.api.model.sca.ScaApplicationObject;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.spi.WebLogicDConfigBeanRoot;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.WebLogicTargetModuleID;
import weblogic.deploy.api.spi.config.DescriptorSupportManager;
import weblogic.deploy.api.spi.factories.WebLogicDeploymentFactory;
import weblogic.deploy.api.spi.factories.internal.DeploymentFactoryImpl;
import weblogic.deploy.utils.JMSModuleDefaultingHelper;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.management.DomainDir;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.auth.callback.IdentityDomainNamesEncoder;
import weblogic.utils.FileUtils;

public class SessionHelper {
   private static final Class DEF_FACTORY_CLASS = DeploymentFactoryImpl.class;
   private boolean debug = Debug.isDebug("config");
   protected WebLogicDeploymentManager dm;
   protected WebLogicDeployableObject dObject = null;
   protected WebLogicDeploymentConfiguration dConfig = null;
   private ModuleType moduleType = null;
   private boolean explicitApplication = false;
   private boolean explicitPlan = false;
   private boolean explicitRoot = false;
   private File application = null;
   private String lightWeightAppName = null;
   private boolean beanScaffoldingEnabled = false;
   private File plan = null;
   private File root = null;
   private File plandir = null;
   private boolean fullInit = true;
   private static final String APP_DIR = "app";
   private static final String PLAN_DIR = "plan";
   private boolean updatePlanVersion = false;
   private DeploymentPlanBean planBean = null;
   private List libs = null;
   private String debugStack = null;
   private String partitionName = null;

   private boolean isExplicitApplication() {
      return this.explicitApplication;
   }

   private void setExplicitApplication(boolean explicitApplication) {
      this.explicitApplication = explicitApplication;
   }

   private boolean isExplicitPlan() {
      return this.explicitPlan;
   }

   private void setExplicitPlan(boolean explicitPlan) {
      this.explicitPlan = explicitPlan;
   }

   private boolean isExplicitRoot() {
      return this.explicitRoot;
   }

   private void setExplicitRoot(boolean explicitRoot) {
      this.explicitRoot = explicitRoot;
   }

   protected void finalize() {
      if (this.debug && this.debugStack != null) {
         Debug.say(this.debugStack);
      }

   }

   public void close() {
      this.close(this.dConfig);
      this.close(this.dObject);
      this.dConfig = null;
      this.dObject = null;
      if (this.debug && this.debugStack != null) {
         this.debugStack = null;
      }

   }

   private void close(Closable c) {
      try {
         if (c != null) {
            c.close();
         }
      } catch (Throwable var3) {
      }

   }

   public File getApplication() {
      return this.application;
   }

   public void setApplication(File application) {
      this.setApplication(application, true);
      this.setImplicitRoot(this.getApplication(), "app");
      this.setImplicitPlan();
   }

   protected void setLightWeightAppName(String appName) {
      this.lightWeightAppName = appName;
   }

   public void enableBeanScaffolding() {
      this.beanScaffoldingEnabled = true;
   }

   private void setImplicitRoot(File subdir, String parent) {
      if (!this.isExplicitRoot()) {
         if (subdir != null) {
            if (parent.equals(subdir.getAbsoluteFile().getParentFile().getName())) {
               this.setApplicationRoot(subdir.getAbsoluteFile().getParentFile().getParentFile(), false);
            }

         }
      }
   }

   private void setImplicitPlan() {
      if (!this.isExplicitPlan()) {
         File rt = this.getApplicationRoot();
         if (rt != null) {
            File plandir = new File(rt, "plan");
            if (plandir.exists() && plandir.isDirectory()) {
               String[] files = plandir.list();
               String pln = this.getOnlyXMLFile(files);
               if (pln != null) {
                  this.setPlan(new File(plandir, pln), false);
               }
            }

         }
      }
   }

   private String getOnlyXMLFile(String[] files) {
      String f = null;

      for(int i = 0; i < files.length; ++i) {
         String file = files[i];
         if (file.endsWith(".xml")) {
            if (f != null) {
               return null;
            }

            f = file;
         }
      }

      return f;
   }

   private void setImplicitApplication() {
      if (!this.isExplicitApplication()) {
         File rt = this.getApplicationRoot();
         if (rt != null) {
            File appdir = new File(rt, "app");
            if (appdir.exists() && appdir.isDirectory()) {
               String[] files = appdir.list();
               if (files.length == 1) {
                  this.setApplication(new File(appdir, files[0]), false);
               }
            }

         }
      }
   }

   private void setApplication(File application, boolean explicit) {
      this.application = application;
      this.setExplicitApplication(explicit);
   }

   public File getPlan() {
      return this.plan;
   }

   public void setPlan(File plan) {
      this.setPlan(plan, true);
      this.setImplicitRoot(plan, "plan");
   }

   private void setPlan(File plan, boolean b) {
      if (plan != null && plan.exists() && plan.isDirectory()) {
         throw new IllegalArgumentException(SPIDeployerLogger.planIsDir(plan.getPath()));
      } else {
         this.plan = plan;
         this.setExplicitPlan(b);
      }
   }

   public File getPlandir() {
      return this.plandir;
   }

   public void setPlandir(File plandir) {
      this.plandir = plandir;
   }

   public void setPlanBean(DeploymentPlanBean newPlan) {
      this.planBean = newPlan;
   }

   public File getApplicationRoot() {
      return this.root;
   }

   public void setApplicationRoot(File root) {
      this.setApplicationRoot(root, true);
      this.setImplicitApplication();
      this.setImplicitPlan();
   }

   private void setApplicationRoot(File root, boolean b) {
      if (root != null && root.exists() && !root.isDirectory()) {
         throw new IllegalArgumentException(SPIDeployerLogger.rootIsFile(root.getPath()));
      } else {
         this.root = root;
         this.setExplicitRoot(b);
      }
   }

   protected String[] getChangedDescriptors() {
      if (this.planBean == null) {
         return new String[0];
      } else {
         ArrayList descriptorList = new ArrayList();
         ModuleOverrideBean[] mobs = this.planBean.getModuleOverrides();
         if (mobs == null) {
            return new String[0];
         } else {
            String root = null;

            for(int i = 0; i < mobs.length; ++i) {
               if (this.planBean.rootModule(mobs[i].getModuleName())) {
                  root = mobs[i].getModuleName();
                  break;
               }
            }

            for(int i = 0; i < mobs.length; ++i) {
               boolean isEar = ModuleType.EAR.toString().equals(mobs[i].getModuleType());
               boolean isRoot = mobs[i].getModuleName().equals(root);
               ModuleDescriptorBean[] mds = mobs[i].getModuleDescriptors();
               if (mds != null) {
                  for(int j = 0; j < mds.length; ++j) {
                     String uri = mds[j].getUri();
                     if (!isRoot) {
                        uri = mobs[i].getModuleName() + "/" + uri;
                     }

                     if (mds[j].isChanged()) {
                        descriptorList.add(uri);
                     }
                  }
               }
            }

            return (String[])((String[])descriptorList.toArray(new String[descriptorList.size()]));
         }
      }
   }

   public void setDebug() {
      this.debug = true;
   }

   public boolean isFullInit() {
      return this.fullInit;
   }

   public void setFullInit(boolean fullInit) {
      this.fullInit = fullInit;
   }

   public boolean isUpdatePlanVersion() {
      return this.updatePlanVersion;
   }

   public void setUpdatePlanVersion(boolean updatePlanVersion) {
      this.updatePlanVersion = updatePlanVersion;
      if (updatePlanVersion && this.getConfiguration() != null) {
         this.bumpVersion();
      }

   }

   protected SessionHelper(WebLogicDeploymentManager dm) {
      this.dm = dm;
      if (this.debug) {
         Exception e = new Exception("SessionHelper.close() is not called. Created stack trace:");
         StringWriter writer = new StringWriter();
         PrintWriter pw = new PrintWriter(writer);
         e.printStackTrace(pw);
         pw.close();
         this.debugStack = writer.toString();
      }

   }

   public static SessionHelper getInstance(WebLogicDeploymentManager dm) {
      return new SessionHelper(dm);
   }

   public static WebLogicDeploymentManager getDisconnectedDeploymentManager() throws DeploymentManagerCreationException {
      WebLogicDeploymentFactory df = (WebLogicDeploymentFactory)registerDefaultFactory();
      return (WebLogicDeploymentManager)df.getDisconnectedDeploymentManager("deployer:WebLogic");
   }

   public static WebLogicDeploymentManager getDeploymentManager(String protocol, String host, String port, String userName, String password) throws DeploymentManagerCreationException {
      WebLogicDeploymentFactory df = (WebLogicDeploymentFactory)registerDefaultFactory();
      return (WebLogicDeploymentManager)df.getDeploymentManager(df.createUri(protocol, "deployer:WebLogic", host, port), userName, password);
   }

   public static WebLogicDeploymentManager getDeploymentManager(String host, String port, String userName, String password) throws DeploymentManagerCreationException {
      return getDeploymentManager("t3", host, port, userName, password);
   }

   public static WebLogicDeploymentManager getDeploymentManager(String protocol, String host, String port, String path, String userName, String password, String idd) throws DeploymentManagerCreationException {
      WebLogicDeploymentFactory df = (WebLogicDeploymentFactory)registerDefaultFactory();
      if (idd != null && !idd.isEmpty()) {
         userName = IdentityDomainNamesEncoder.encodeNames(userName, idd);
      }

      return (WebLogicDeploymentManager)df.getDeploymentManager(df.createUri(protocol, "deployer:WebLogic", host, port, path), userName, password);
   }

   public static WebLogicDeploymentManager getRemoteDeploymentManager(String host, String port, String userName, String password) throws DeploymentManagerCreationException {
      return getRemoteDeploymentManager("t3", host, port, userName, password);
   }

   public static WebLogicDeploymentManager getRemoteDeploymentManager(String protocol, String host, String port, String userName, String password) throws DeploymentManagerCreationException {
      WebLogicDeploymentFactory df = (WebLogicDeploymentFactory)registerDefaultFactory();
      return (WebLogicDeploymentManager)df.getDeploymentManager(df.createUri(protocol, "remote:deployer:WebLogic", host, port), userName, password);
   }

   public static WebLogicDeploymentManager getRemoteDeploymentManager(String protocol, String host, String port, String path, String userName, String password, String idd) throws DeploymentManagerCreationException {
      WebLogicDeploymentFactory df = (WebLogicDeploymentFactory)registerDefaultFactory();
      if (idd != null && !idd.isEmpty()) {
         userName = IdentityDomainNamesEncoder.encodeNames(userName, idd);
      }

      return (WebLogicDeploymentManager)df.getDeploymentManager(df.createUri(protocol, "remote:deployer:WebLogic", host, port, path), userName, password);
   }

   public static WebLogicDeploymentManager getDeploymentManager(String host, String port) throws DeploymentManagerCreationException {
      WebLogicDeploymentFactory df = (WebLogicDeploymentFactory)registerDefaultFactory();
      return (WebLogicDeploymentManager)df.getDeploymentManager(df.createUri("authenticated:deployer:WebLogic", host, port), (String)null, (String)null);
   }

   public WebLogicDeployableObject getDeployableObject() {
      return this.dObject;
   }

   public WebLogicDeploymentConfiguration getConfiguration() {
      return this.dConfig;
   }

   public void initializeConfiguration() throws ConfigurationException, IOException, InvalidModuleException {
      this.initializeConfiguration((AppDeploymentMBean)null);
   }

   public void initializeConfiguration(AppDeploymentMBean mbean) throws ConfigurationException, IOException, InvalidModuleException {
      if (this.dObject != null) {
         throw new AssertionError(SPIDeployerLogger.getReinitializeError());
      } else {
         this.normalizeProperties(mbean);
         this.initialize();
      }
   }

   private void normalizeProperties() throws IOException {
      this.normalizeProperties((AppDeploymentMBean)null);
   }

   private void normalizeProperties(AppDeploymentMBean mbean) throws IOException {
      if (this.getApplication() == null) {
         throw new IllegalArgumentException(SPIDeployerLogger.noAppProvided());
      } else {
         String appName = this.getApplication().getName();
         if (mbean != null) {
            appName = mbean.getName();
         }

         InstallDir installDir = new InstallDir(appName, this.getApplicationRoot());
         installDir.setArchive(this.getApplication());
         installDir.setPlan(this.getPlan());
         if (this.getPlandir() != null) {
            installDir.setConfigDir(this.getPlandir());
         }

         this.setApplication(installDir.getArchive(), true);
         this.setPlan(installDir.getPlan(), true);
         this.setApplicationRoot(installDir.getInstallDir(), true);
      }
   }

   /** @deprecated */
   @Deprecated
   public void initializeConfiguration(File app, File plan, ModuleType type) throws ConfigurationException, IOException, InvalidModuleException {
      this.initializeConfiguration(app, plan, (File)null);
   }

   /** @deprecated */
   @Deprecated
   public void initializeConfiguration(File app, File plan) throws ConfigurationException, IOException, InvalidModuleException {
      this.initializeConfiguration(app, plan, (File)null);
   }

   /** @deprecated */
   @Deprecated
   public void initializeConfiguration(File app, File plan, File root) throws ConfigurationException, IOException, InvalidModuleException {
      if (this.debug) {
         Debug.say("In deprecated method");
      }

      this.setApplication(app, true);
      this.setPlan(plan, true);
      this.setApplicationRoot(root, true);
      this.initializeConfiguration();
   }

   /** @deprecated */
   @Deprecated
   public void initializeConfiguration(File app, File plan, File root, ModuleType type) throws ConfigurationException, IOException, InvalidModuleException {
      this.initializeConfiguration(app, plan, root);
   }

   public void inspect() throws IOException, InvalidModuleException, ConfigurationException {
      if (this.dObject != null) {
         throw new AssertionError(SPIDeployerLogger.getReinitializeError());
      } else {
         this.normalizeProperties();
         WebLogicDeployableObjectFactoryImpl wdoFactory = new WebLogicDeployableObjectFactoryImpl();
         if (this.lightWeightAppName != null) {
            wdoFactory.setLightWeightAppName(this.lightWeightAppName);
         }

         if (this.beanScaffoldingEnabled) {
            wdoFactory.enableBeanScaffolding();
         }

         this.dObject = wdoFactory.createLazyDeployableObject(this.getApplication(), this.getApplicationRoot(), this.getPlan(), this.getPlandir(), this.getLibraries());
         this.moduleType = this.dObject.getType();
         if (this.debug) {
            Debug.say("derived module type: " + this.moduleType.toString());
         }

         this.dConfig = (WebLogicDeploymentConfiguration)this.dm.createConfiguration(this.dObject);
         if (this.dObject instanceof WebLogicJ2eeApplicationObject) {
            this.dConfig.getDConfigBeanRoot(this.dObject.getDDBeanRoot());
         }

         this.restorefromPlan();
      }
   }

   public LibrarySpec registerLibrary(File location, String name, String specVersion, String implVersion) throws IllegalArgumentException {
      if (this.libs == null) {
         this.enableLibraryMerge();
      }

      LibrarySpec lib = new LibrarySpec(name, specVersion, implVersion, location);
      this.libs.add(lib);
      return lib;
   }

   public ModuleInfo getModuleInfo() throws IOException, ConfigurationException {
      return ModuleInfo.createModuleInfo(this.getDeployableObject(), this.getConfiguration(), (String)null);
   }

   protected void initialize() throws ConfigurationException, IOException, InvalidModuleException {
      if (this.debug) {
         Debug.say("Initializing configuration using");
         Debug.say("   app: " + this.getApplication().getPath());
         if (this.getPlan() != null) {
            Debug.say("   plan: " + this.getPlan().getPath());
         }

         if (this.getApplicationRoot() != null) {
            Debug.say("   root: " + this.getApplicationRoot().getPath());
         }
      }

      WebLogicDeployableObjectFactoryImpl wdoFactory = new WebLogicDeployableObjectFactoryImpl();
      if (this.lightWeightAppName != null) {
         wdoFactory.setLightWeightAppName(this.lightWeightAppName);
      }

      if (this.beanScaffoldingEnabled) {
         wdoFactory.enableBeanScaffolding();
      }

      this.dObject = wdoFactory.createDeployableObject(this.getApplication(), this.getApplicationRoot(), this.getPlan(), this.getPlandir(), this.getLibraries());
      this.dObject.setPartitionName(this.getPartitionName());
      this.moduleType = this.dObject.getType();
      if (this.debug) {
         Debug.say("derived module type: " + this.moduleType.toString());
      }

      this.dConfig = (WebLogicDeploymentConfiguration)this.dm.createConfiguration(this.dObject);
      this.initializeWithConfig();
      if (this.updatePlanVersion) {
         this.bumpVersion();
      }

   }

   public LibrarySpec[] getLibraries() {
      return this.libs == null ? null : (LibrarySpec[])((LibrarySpec[])this.libs.toArray(new LibrarySpec[0]));
   }

   public void enableLibraryMerge() {
      if (this.libs == null) {
         this.libs = new ArrayList();
      }

   }

   private void initializeScaWithConfig() throws ConfigurationException {
      ScaApplicationObject scaObj = (ScaApplicationObject)this.dObject;
      DeployableObject[] dObjs = scaObj.getDeployableObjects();
      if (dObjs != null) {
         for(int i = 0; i < dObjs.length; ++i) {
            ModuleType type = dObjs[i].getType();
            if (type == ModuleType.WAR || type == ModuleType.EJB) {
               DDBeanRoot ddBeanRoot = dObjs[i].getDDBeanRoot();
               DConfigBeanRoot dConfigBean = this.dConfig.getDConfigBeanRoot(ddBeanRoot);
               if (this.fullInit) {
                  ConfigHelper.beanWalker(ddBeanRoot, dConfigBean);
               }
            }
         }

      }
   }

   protected void initializeWithConfig() throws ConfigurationException {
      if (this.debug) {
         Debug.say("initializing dconfig");
      }

      if (this.moduleType == WebLogicModuleType.SCA_COMPOSITE) {
         this.initializeScaWithConfig();
      } else {
         DDBeanRoot ddroot = this.dObject.getDDBeanRoot();
         this.restorefromPlan();
         DConfigBeanRoot rootConfig = this.dConfig.getDConfigBeanRoot(ddroot);
         if (this.fullInit) {
            ConfigHelper.beanWalker(ddroot, rootConfig);
         }

         if (rootConfig != null && this.moduleType == ModuleType.EAR) {
            DeployableObject[] dos = ((WebLogicJ2eeApplicationObject)this.dObject).getDeployableObjects();
            if (dos != null) {
               for(int i = 0; i < dos.length; ++i) {
                  DeployableObject mod = dos[i];
                  String uri = ((WebLogicDeployableObject)mod).getUri();
                  if (this.debug) {
                     Debug.say("Collecting beans for embedded module at, " + uri);
                  }

                  try {
                     if (DescriptorSupportManager.getForModuleType(mod.getDDBeanRoot().getType()).length > 0) {
                        DConfigBeanRoot modDcb = this.dConfig.getDConfigBeanRoot(mod.getDDBeanRoot());
                        if (this.fullInit) {
                           ConfigHelper.beanWalker(mod.getDDBeanRoot(), modDcb);
                        }
                     }
                  } catch (ConfigurationException var9) {
                     SPIDeployerLogger.logNoDCB(uri, var9.toString());
                     throw var9;
                  }
               }
            }
         }

      }
   }

   private void restorefromPlan() throws ConfigurationException {
      if (this.planBean != null) {
         this.dConfig.restore(this.planBean);
      } else if (this.getPlan() != null) {
         try {
            InputStream p = new FileInputStream(this.getPlan());

            try {
               this.dConfig.restore(p);
            } finally {
               p.close();
            }
         } catch (IOException var6) {
            SPIDeployerLogger.logNoPlan(this.getPlan().getPath());
            this.setPlan((File)null);
         }
      }

   }

   public void savePlan() throws IllegalStateException, ConfigurationException, FileNotFoundException {
      if (this.getConfiguration() == null) {
         throw new IllegalStateException(SPIDeployerLogger.mustInit());
      } else {
         if (this.debug) {
            Debug.say("Saving plan to " + this.getPlan());
         }

         this.getPlan().getAbsoluteFile().getParentFile().mkdirs();
         FileOutputStream fos = new FileOutputStream(this.getPlan());

         try {
            this.savePlan(fos);
         } finally {
            try {
               fos.close();
            } catch (IOException var8) {
            }

         }

      }
   }

   public void saveApplicationRoot() throws IOException, ConfigurationException, IllegalStateException {
      if (this.getConfiguration() == null) {
         throw new IllegalStateException(SPIDeployerLogger.mustInit());
      } else {
         File oldApp = this.getApplication().getCanonicalFile();
         InstallDir newRoot = new InstallDir(this.getApplicationRoot());
         newRoot.getAppDir().mkdirs();
         newRoot.setArchive(new File(newRoot.getAppDir(), this.getApplication().getName()));
         newRoot.getConfigDir().mkdirs();
         String p = this.getPlan() == null ? "plan.xml" : this.getPlan().getName();
         newRoot.setPlan(new File(newRoot.getConfigDir(), p));
         this.setApplication(newRoot.getArchive(), true);
         this.setPlan(newRoot.getPlan(), true);
         this.getConfiguration().getPlan().setConfigRoot(newRoot.getConfigDir().getCanonicalPath());
         if (!oldApp.equals(this.getApplication().getCanonicalFile())) {
            if (this.debug) {
               Debug.say("Copying app to " + this.getApplication());
            }

            FileUtils.copyPreservePermissions(oldApp, this.getApplication());
         }

         this.savePlan();
      }
   }

   private static DeploymentFactory getRegisteredDefaultFactory() {
      DeploymentFactory[] existingFactories = DeploymentFactoryManager.getInstance().getDeploymentFactories();
      if (existingFactories != null) {
         for(int i = 0; i < existingFactories.length; ++i) {
            if (DEF_FACTORY_CLASS.isInstance(existingFactories[i])) {
               return existingFactories[i];
            }
         }
      }

      return null;
   }

   private static DeploymentFactory registerDefaultFactory() throws IllegalArgumentException {
      DeploymentFactory existingOne = getRegisteredDefaultFactory();
      if (existingOne != null) {
         return existingOne;
      } else {
         try {
            if (DeploymentFactory.class.isAssignableFrom(DEF_FACTORY_CLASS)) {
               DeploymentFactory df = (DeploymentFactory)DEF_FACTORY_CLASS.newInstance();
               DeploymentFactoryManager.getInstance().registerDeploymentFactory(df);
               return df;
            } else {
               throw new IllegalArgumentException(SPIDeployerLogger.invalidFactory(DEF_FACTORY_CLASS.getName()));
            }
         } catch (InstantiationException var2) {
            throw new IllegalArgumentException(SPIDeployerLogger.invalidFactory(DEF_FACTORY_CLASS.getName()));
         } catch (IllegalAccessException var3) {
            throw new IllegalArgumentException(SPIDeployerLogger.invalidFactory(DEF_FACTORY_CLASS.getName()));
         }
      }
   }

   public File[] getPlanDirectories(String appName) throws IOException {
      ConfigHelper.checkParam("appName", appName);
      if (this.getConfiguration() == null) {
         throw new IllegalStateException(SPIDeployerLogger.mustInit());
      } else {
         Set files = new HashSet();
         if (this.getPlan() != null) {
            this.addFile(files, this.getPlan().getParentFile().getCanonicalFile(), false);
         }

         if (this.getConfiguration().getPlan().getConfigRoot() != null) {
            this.addFile(files, (new File(this.getConfiguration().getPlan().getConfigRoot())).getCanonicalFile(), false);
         }

         File f = new File(DomainDir.getDeploymentsDir(), appName);
         if (f.exists()) {
            this.addFile(files, (new InstallDir(appName, f)).getConfigDir().getCanonicalFile(), false);
         }

         return (File[])((File[])files.toArray(new File[0]));
      }
   }

   public File savePlan(File dir, String planName) throws IOException, ConfigurationException {
      ConfigHelper.checkParam("dir", dir);
      ConfigHelper.checkParam("planName", planName);
      if (this.getConfiguration() == null) {
         throw new IllegalStateException(SPIDeployerLogger.mustInit());
      } else {
         if (!dir.exists()) {
            dir.mkdirs();
         }

         File d = dir.getCanonicalFile();
         if (!d.isDirectory()) {
            throw new IllegalArgumentException(SPIDeployerLogger.notDir(d.getPath()));
         } else {
            return this.saveBackToSource(dir, planName);
         }
      }
   }

   private File saveBackToSource(File dir, String planName) throws FileNotFoundException, ConfigurationException {
      File planPath = new File(dir, planName);
      OutputStream os = new FileOutputStream(planPath);

      try {
         this.savePlan(os);
      } finally {
         try {
            os.close();
         } catch (IOException var11) {
         }

      }

      return planPath;
   }

   private void savePlan(OutputStream os) throws ConfigurationException {
      this.getConfiguration().save(os);
   }

   protected void bumpVersion() {
      DeploymentPlanBean planBean = this.getConfiguration().getPlan();
      if (planBean.getVersion() == null) {
         planBean.setVersion("1.0");
      } else {
         try {
            Float f1 = new Float(planBean.getVersion());
            Float f2 = new Float(1.0);
            planBean.setVersion((new Float(f1 + f2)).toString());
         } catch (NumberFormatException var4) {
         }
      }

   }

   public String getNewPlanName() {
      String pn = "plan.xml";
      if (this.getPlan() != null) {
         pn = this.getPlan().getName();
      }

      if (this.getConfiguration() != null) {
         String v = this.getConfiguration().getPlan().getVersion();
         if (v != null) {
            int x = pn.indexOf(".xml");
            if (x == -1) {
               pn = pn + "_" + v;
            } else {
               pn = pn.substring(0, x) + "_" + v + ".xml";
            }
         }
      }

      return pn;
   }

   public File[] findPlans() {
      Set plans = new HashSet();
      if (this.getPlan() != null) {
         this.addFile(plans, this.getPlan(), true);
      }

      File cd;
      if (this.getConfiguration() != null) {
         cd = new File(this.getConfiguration().getPlan().getConfigRoot());
         this.addPlansFromDirectory(cd, plans);
      }

      if (this.getApplicationRoot() != null) {
         cd = new File(this.getApplicationRoot().getPath() + File.separator + "plan");
         this.addPlansFromDirectory(cd, plans);
      }

      return (File[])((File[])plans.toArray(new File[0]));
   }

   private void addPlansFromDirectory(File cd, Set plans) {
      if (cd.exists() && cd.isDirectory()) {
         File[] f = cd.listFiles(new FileFilter() {
            public boolean accept(File f) {
               return f.isFile() && f.getName().endsWith(".xml");
            }
         });

         for(int i = 0; i < f.length; ++i) {
            this.addFile(plans, f[i], true);
         }
      }

   }

   private void addFile(Set fileSet, File f, boolean exists) {
      try {
         if (!exists || f.exists()) {
            fileSet.add(f.getCanonicalFile());
         }
      } catch (IOException var5) {
      }

   }

   public TargetModuleID[] getDefaultJMSTargetModuleIDs(DomainMBean domain, TargetMBean[] deploymentTargets, String appName, String name) throws ConfigurationException {
      if (this.getConfiguration() == null) {
         throw new IllegalStateException(SPIDeployerLogger.mustInit());
      } else {
         List tmids = new ArrayList();
         if (name == null) {
            return null;
         } else if (deploymentTargets != null && deploymentTargets.length > 0) {
            AppDeploymentMBean app = AppDeploymentHelper.lookupAppOrLib(appName, domain);
            if (app != null && this.checkAppTargetting(app, name)) {
               return null;
            } else {
               JMSBean module = null;

               try {
                  module = this.getJMSDescriptor(name);
               } catch (FileNotFoundException var16) {
               }

               if (module == null) {
                  return null;
               } else {
                  Map subDeploymentMap = JMSModuleDefaultingHelper.getJMSDefaultTargets(module, domain, deploymentTargets, (DeploymentData)null);
                  Iterator it = subDeploymentMap.keySet().iterator();

                  while(it.hasNext()) {
                     String subDeploymentName = (String)it.next();
                     TargetMBean[] targets = (TargetMBean[])((TargetMBean[])subDeploymentMap.get(subDeploymentName));

                     for(int i = 0; i < targets.length; ++i) {
                        TargetMBean target = targets[i];
                        if (target != null) {
                           TargetModuleID rootTmid = this.createParentTmids(appName, name, target);
                           if (rootTmid.getChildTargetModuleID() != null) {
                              rootTmid = rootTmid.getChildTargetModuleID()[0];
                           }

                           WebLogicTargetModuleID subTmid = this.dm.createTargetModuleID((TargetModuleID)rootTmid, (String)subDeploymentName, (ModuleType)WebLogicModuleType.SUBMODULE);
                           tmids.add(subTmid);
                        }
                     }
                  }

                  return (TargetModuleID[])((TargetModuleID[])tmids.toArray(new TargetModuleID[0]));
               }
            }
         } else {
            return null;
         }
      }
   }

   private boolean checkAppTargetting(AppDeploymentMBean app, String name) {
      SubDeploymentMBean[] mods;
      if (this.getDeployableObject().getType().getValue() == ModuleType.EAR.getValue()) {
         mods = app.getSubDeployments();

         for(int j = 0; j < mods.length; ++j) {
            SubDeploymentMBean mod = mods[j];
            if (mod.getName().equals(name)) {
               return this.checkIfTargeted(mod);
            }
         }
      } else {
         mods = app.getSubDeployments();
         if (mods != null && mods.length != 0) {
            return true;
         }
      }

      return false;
   }

   private boolean checkIfTargeted(SubDeploymentMBean mod) {
      SubDeploymentMBean[] subs = mod.getSubDeployments();
      return subs != null && subs.length != 0;
   }

   private TargetModuleID createParentTmids(String appName, String moduleName, TargetMBean target) {
      WebLogicTargetModuleID rootTmid;
      if (this.getDeployableObject().getType().getValue() == ModuleType.EAR.getValue()) {
         rootTmid = this.dm.createTargetModuleID(appName, ModuleType.EAR, this.getTarget(target));
         rootTmid = this.dm.createTargetModuleID((TargetModuleID)rootTmid, (String)moduleName, (ModuleType)WebLogicModuleType.JMS);
      } else {
         rootTmid = this.dm.createTargetModuleID((String)moduleName, (ModuleType)WebLogicModuleType.JMS, (Target)this.getTarget(target));
      }

      return rootTmid;
   }

   public Target getTarget(TargetMBean target) {
      return this.dm.getTarget(target.getName());
   }

   public JMSBean getJMSDescriptor(String name) throws ConfigurationException, FileNotFoundException {
      if (this.getConfiguration() == null) {
         throw new IllegalStateException(SPIDeployerLogger.mustInit());
      } else {
         WebLogicDConfigBeanRoot dcb;
         if (this.getDeployableObject().getType().getValue() == WebLogicModuleType.JMS.getValue() && this.getApplication().getName().equals(name)) {
            dcb = (WebLogicDConfigBeanRoot)this.getConfiguration().getDConfigBeanRoot(this.getDeployableObject().getDDBeanRoot());
            return (JMSBean)dcb.getDescriptorBean();
         } else {
            if (this.getDeployableObject() instanceof WebLogicJ2eeApplicationObject) {
               dcb = null;

               try {
                  dcb = (WebLogicDConfigBeanRoot)this.getConfiguration().getDConfigBeanRoot(this.getDeployableObject().getDDBeanRoot());
                  WeblogicApplicationBean app = (WeblogicApplicationBean)dcb.getDescriptorBean();
                  if (app.getModules() == null) {
                     return null;
                  }

                  Iterator modules = Arrays.asList((Object[])app.getModules()).iterator();
                  String uri = null;

                  while(modules.hasNext()) {
                     WeblogicModuleBean o = (WeblogicModuleBean)modules.next();
                     if (o.getName().equals(name)) {
                        uri = o.getPath();
                        break;
                     }
                  }

                  if (uri != null) {
                     dcb = (WebLogicDConfigBeanRoot)dcb.getDConfigBean(this.getDeployableObject().getDDBeanRoot(uri));
                     return (JMSBean)dcb.getDescriptorBean();
                  }
               } catch (DDBeanCreateException var7) {
                  throw new ConfigurationException(var7.toString());
               }
            }

            return null;
         }
      }
   }

   public String[] getDescriptorUris(ModuleType type) {
      if (this.getConfiguration() == null) {
         throw new IllegalStateException(SPIDeployerLogger.mustInit());
      } else if (this.getDeployableObject().getType().getValue() == type.getValue()) {
         return new String[]{this.getApplication().getName()};
      } else if (this.getDeployableObject().getType().getValue() == ModuleType.EAR.getValue()) {
         List uris = new ArrayList();
         WebLogicJ2eeApplicationObject ear = (WebLogicJ2eeApplicationObject)this.getDeployableObject();
         DDBeanRoot[] roots = ear.getDDBeanRoots();

         for(int i = 0; i < roots.length; ++i) {
            DDBeanRoot beanRoot = roots[i];
            if (beanRoot.getType().getValue() == type.getValue()) {
               uris.add(beanRoot.getFilename());
            }
         }

         return (String[])((String[])uris.toArray(new String[0]));
      } else {
         return new String[0];
      }
   }

   public void setPartitionName(String name) {
      this.partitionName = name;
   }

   public String getPartitionName() {
      return this.partitionName;
   }
}
