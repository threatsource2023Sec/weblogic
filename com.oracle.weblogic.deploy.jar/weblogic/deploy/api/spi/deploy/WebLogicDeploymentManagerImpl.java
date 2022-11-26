package weblogic.deploy.api.spi.deploy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.shared.DConfigBeanVersionType;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.DeploymentConfiguration;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.DConfigBeanVersionUnsupportedException;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import javax.enterprise.deploy.spi.exceptions.TargetException;
import javax.enterprise.deploy.spi.status.ProgressObject;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.internal.utils.JMXDeployerHelper;
import weblogic.deploy.api.internal.utils.LocaleManager;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.shared.WebLogicTargetType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.WebLogicTargetModuleID;
import weblogic.deploy.api.spi.config.DeploymentConfigurationImpl;
import weblogic.deploy.api.spi.deploy.internal.ActivateOperation;
import weblogic.deploy.api.spi.deploy.internal.DeactivateOperation;
import weblogic.deploy.api.spi.deploy.internal.DeployOperation;
import weblogic.deploy.api.spi.deploy.internal.DistributeOperation;
import weblogic.deploy.api.spi.deploy.internal.DistributeStreamsOperation;
import weblogic.deploy.api.spi.deploy.internal.ExtendLoaderOperation;
import weblogic.deploy.api.spi.deploy.internal.RedeployDeltaOperation;
import weblogic.deploy.api.spi.deploy.internal.RedeployOperation;
import weblogic.deploy.api.spi.deploy.internal.RedeployStreamsOperation;
import weblogic.deploy.api.spi.deploy.internal.RemoveOperation;
import weblogic.deploy.api.spi.deploy.internal.StartOperation;
import weblogic.deploy.api.spi.deploy.internal.StopOperation;
import weblogic.deploy.api.spi.deploy.internal.UndeployOperation;
import weblogic.deploy.api.spi.deploy.internal.UnprepareOperation;
import weblogic.deploy.api.spi.deploy.internal.UpdateOperation;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;

public class WebLogicDeploymentManagerImpl implements WebLogicDeploymentManager {
   private static final boolean debug = Debug.isDebug("deploy");
   private static final List DCONFIG_VERSIONS;
   private DConfigBeanVersionType dconfigVersion;
   private String domain;
   private ServerConnection serverConnection;
   private boolean local;
   private boolean auth;
   private String taskId;
   public static final String PREFIX = "app";
   public static final String SUFFIX = ".zip";
   private URI saveUri;
   private String saveUser;
   private String saveClass;
   private String savePword;
   private Thread hook;
   private boolean releasing;

   public WebLogicDeploymentManagerImpl(String deployerUri) throws DeploymentManagerCreationException {
      this.domain = "weblogic";
      this.taskId = null;
      this.hook = null;
      this.releasing = false;
      String msg = SPIDeployerLogger.noURI();
      if (deployerUri == null) {
         throw new DeploymentManagerCreationException(msg);
      } else {
         this.setCharacteristics(deployerUri);
         this.dconfigVersion = (DConfigBeanVersionType)DCONFIG_VERSIONS.get(DCONFIG_VERSIONS.size() - 1);
         if (debug) {
            Debug.say("Constructing DeploymentManager for Java EE version " + this.dconfigVersion.toString() + " deployments");
         }

      }
   }

   public WebLogicDeploymentManagerImpl(String serverClass, String deployerUri, URI uri, String user, String pword) throws DeploymentManagerCreationException {
      this(deployerUri);

      DeploymentManagerCreationException dmce;
      try {
         this.saveConnectionArgs(uri, user, serverClass, pword);
         this.getNewConnection();
      } catch (ServerConnectionException var8) {
         this.release();
         dmce = new DeploymentManagerCreationException(var8.getMessage());
         dmce.initCause(var8);
         throw dmce;
      } catch (Throwable var9) {
         this.serverConnection = null;
         if (debug) {
            if (var9.getCause() != null) {
               var9.getCause().printStackTrace();
            } else {
               var9.printStackTrace();
            }
         }

         dmce = new DeploymentManagerCreationException(SPIDeployerLogger.noClass(serverClass, var9.toString()));
         dmce.initCause(var9);
         throw dmce;
      }
   }

   private void saveConnectionArgs(URI uri, String user, String aClass, String pword) {
      this.saveUri = uri;
      this.saveUser = user;
      this.saveClass = aClass;
      this.savePword = pword;
   }

   private void getNewConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      if (debug) {
         Debug.say("Connecting to admin server at " + this.saveUri.getHost() + ":" + this.saveUri.getPort() + ", as user " + this.saveUser);
      }

      this.serverConnection = (ServerConnection)Class.forName(this.saveClass).newInstance();
      this.serverConnection.init(this.saveUri, this.saveUser, this.savePword, this);
      this.hook = new Thread() {
         public void run() {
            if (WebLogicDeploymentManagerImpl.debug) {
               Debug.say("Releasing connection due to user signal");
            }

            WebLogicDeploymentManagerImpl.this.shutdown();
         }
      };
      Runtime.getRuntime().addShutdownHook(this.hook);
   }

   private void shutdown() {
      try {
         this._release(true);
      } catch (Throwable var2) {
      }

   }

   private void removeHook() {
      try {
         if (this.hook != null) {
            Runtime.getRuntime().removeShutdownHook(this.hook);
         }

         this.hook = null;
      } catch (Throwable var2) {
      }

   }

   public Target getTarget(String name) throws IllegalStateException {
      return this.serverConnection.getTarget(name);
   }

   public Target getTarget(String name, DeploymentOptions options) throws IllegalStateException {
      return this.serverConnection.getTarget(options, name);
   }

   public final WebLogicTargetModuleID createTargetModuleID(String app, ModuleType type, Target target) {
      ConfigHelper.checkParam("type", type);
      return this.createTargetModuleID(app, type.getValue(), target);
   }

   private final WebLogicTargetModuleID createTargetModuleID(String app, int type, Target target) {
      return new TargetModuleIDImpl(app, target, (TargetModuleID)null, type, this);
   }

   public final WebLogicTargetModuleID createTargetModuleID(TargetModuleID base, String module, ModuleType type) {
      ConfigHelper.checkParam("base", base);
      ConfigHelper.checkParam("module", module);
      ConfigHelper.checkParam("type", type);

      WebLogicTargetModuleID root;
      for(root = (WebLogicTargetModuleID)base; root.getParentTargetModuleID() != null; root = (WebLogicTargetModuleID)root.getParentTargetModuleID()) {
      }

      boolean isRoot = root == base;
      root = this.createTargetModuleID(root.getModuleID(), ((TargetModuleIDImpl)root).getValue(), root.getTarget());
      root.setTargeted(false);
      Object wtm;
      if (!isRoot) {
         wtm = new TargetModuleIDImpl(base.getModuleID(), base.getTarget(), root, ((TargetModuleIDImpl)base).getValue(), this);
         ((WebLogicTargetModuleID)wtm).setTargeted(false);
      } else {
         wtm = root;
      }

      new TargetModuleIDImpl(module, ((WebLogicTargetModuleID)wtm).getTarget(), (TargetModuleID)wtm, type, this);
      return root;
   }

   public boolean isLocal() {
      return this.local;
   }

   public String getDomain() {
      return this.domain;
   }

   public void setDomain(String d) {
      this.domain = d;
   }

   public boolean isConnected() {
      try {
         this.testConnection();
      } catch (IllegalStateException var2) {
         return false;
      }

      return this.serverConnection != null && !this.releasing;
   }

   public void enableFileUploads() throws IllegalStateException {
      this.checkConnection();
      if (this.local) {
         this.serverConnection.setRemote();
         this.local = false;
      }

   }

   private void checkConnection() throws IllegalStateException {
      if (this.serverConnection == null) {
         throw new IllegalStateException(SPIDeployerLogger.notConnected());
      } else {
         this.testConnection();
      }
   }

   private void testConnection() throws IllegalStateException {
      if (this.serverConnection != null && !this.releasing) {
         try {
            this.serverConnection.test();
         } catch (Throwable var6) {
            this.release();

            try {
               if (debug) {
                  Debug.say("Attempting to recover lost connection!!!");
               }

               this.getNewConnection();
            } catch (IllegalStateException var4) {
               this.release();
               throw var4;
            } catch (Throwable var5) {
               this.release();
               IllegalStateException ise = new IllegalStateException(SPIDeployerLogger.connectionError());
               ise.initCause(var5);
               throw ise;
            }
         }

      }
   }

   public String getTaskId() {
      String id = this.taskId;
      this.taskId = null;
      return id;
   }

   public boolean isAuthenticated() {
      return this.auth;
   }

   public void setTaskId(String id) throws IllegalStateException {
      ConfigHelper.checkParam("id", id);
      this.checkConnection();
      this.taskId = id;
   }

   public TargetModuleID[] filter(TargetModuleID[] modules, String appName, String moduleName, String versionId) {
      ConfigHelper.checkParam("modules", modules);
      ConfigHelper.checkParam("appName", appName);
      if (modules == null) {
         return null;
      } else {
         Set filterSet = new HashSet();

         TargetModuleID tmid;
         for(int i = 0; i < modules.length; ++i) {
            tmid = modules[i];
            TargetModuleID parent = tmid.getParentTargetModuleID();
            if (parent == null) {
               if (moduleName == null && tmid.getModuleID().equals(appName)) {
                  filterSet.add(tmid);
               }
            } else if (tmid.getModuleID().equals(moduleName) && appName.equals(parent.getModuleID())) {
               filterSet.add(tmid);
            }
         }

         if (versionId != null) {
            Iterator fs = filterSet.iterator();

            while(fs.hasNext()) {
               tmid = (TargetModuleID)fs.next();
               if (!versionId.equals(((WebLogicTargetModuleID)tmid).getVersion())) {
                  fs.remove();
               }
            }
         }

         return (TargetModuleID[])((TargetModuleID[])filterSet.toArray(new TargetModuleID[0]));
      }
   }

   public TargetModuleID[] getModules(ConfigurationMBean mbean) throws IllegalStateException, IllegalArgumentException {
      ConfigHelper.checkParam("mbean", mbean);
      this.checkConnection();

      try {
         List mods = this.serverConnection.getModules(mbean);
         return (TargetModuleID[])mods.toArray(new TargetModuleID[mods.size()]);
      } catch (Exception var3) {
         throw new IllegalArgumentException(var3.getMessage());
      }
   }

   public JMXDeployerHelper getHelper() {
      return this.serverConnection.getHelper();
   }

   public ServerConnection getServerConnection() {
      return this.serverConnection;
   }

   public ProgressObject redeploy(TargetModuleID[] modules, File app, String[] delta, DeploymentOptions options) throws IllegalStateException {
      return (new RedeployDeltaOperation(this, modules, app, delta, false, options)).run();
   }

   public ProgressObject undeploy(TargetModuleID[] modules, File app, String[] delta, DeploymentOptions options) throws IllegalStateException {
      return (new RedeployDeltaOperation(this, modules, app, delta, true, options)).run();
   }

   public ProgressObject deploy(Target[] targets, File application, File plan, DeploymentOptions options) throws TargetException, IllegalStateException {
      return (new DeployOperation(this, targets, application, plan, options)).run();
   }

   public ProgressObject deploy(TargetModuleID[] tmids, File application, File plan, DeploymentOptions options) throws TargetException, IllegalStateException {
      return (new DeployOperation(this, tmids, application, plan, options)).run();
   }

   public ProgressObject update(TargetModuleID[] modules, File plan, DeploymentOptions options) throws IllegalStateException {
      return (new UpdateOperation(this, modules, plan, options)).run();
   }

   public ProgressObject update(TargetModuleID[] modules, File plan, String[] delta, DeploymentOptions options) throws IllegalStateException {
      return (new UpdateOperation(this, modules, plan, delta, options)).run();
   }

   public ProgressObject start(TargetModuleID[] modules, DeploymentOptions options) throws IllegalStateException {
      return (new StartOperation(this, modules, options)).run();
   }

   public ProgressObject stop(TargetModuleID[] modules, DeploymentOptions options) throws IllegalStateException {
      return (new StopOperation(this, modules, options)).run();
   }

   public ProgressObject unprepare(TargetModuleID[] modules, DeploymentOptions options) {
      return (new UnprepareOperation(this, modules, options)).run();
   }

   public ProgressObject deactivate(TargetModuleID[] modules, DeploymentOptions options) {
      return (new DeactivateOperation(this, modules, options)).run();
   }

   public ProgressObject remove(TargetModuleID[] tmids, DeploymentOptions opts) {
      return (new RemoveOperation(this, tmids, opts)).run();
   }

   public ProgressObject activate(TargetModuleID[] tmids, File application, File plan, DeploymentOptions options) throws TargetException, IllegalStateException {
      return (new ActivateOperation(this, tmids, application, plan, options)).run();
   }

   public ProgressObject undeploy(TargetModuleID[] modules, DeploymentOptions options) throws IllegalStateException {
      return (new UndeployOperation(this, modules, options)).run();
   }

   public ProgressObject distribute(Target[] targets, File application, File plan, DeploymentOptions options) throws IllegalStateException {
      return (new DistributeOperation(this, targets, application, plan, options)).run();
   }

   public ProgressObject distribute(TargetModuleID[] targets, File application, File plan, DeploymentOptions options) throws IllegalStateException, TargetException {
      return (new DistributeOperation(this, targets, application, plan, options)).run();
   }

   public ProgressObject distribute(Target[] targets, InputStream application, InputStream plan, DeploymentOptions options) throws IllegalStateException {
      return (new DistributeStreamsOperation(this, targets, (ModuleType)null, application, plan, options)).run();
   }

   private ProgressObject distribute(Target[] targets, ModuleType moduleType, InputStream application, InputStream plan, DeploymentOptions options) throws IllegalStateException {
      return (new DistributeStreamsOperation(this, targets, moduleType, application, plan, options)).run();
   }

   public ProgressObject appendToExtensionLoader(TargetModuleID[] targets, File codeSource, DeploymentOptions options) throws IllegalStateException, TargetException {
      return (new ExtendLoaderOperation(this, targets, codeSource, options)).run();
   }

   public ProgressObject redeploy(TargetModuleID[] modules, File application, File plan, DeploymentOptions options) throws UnsupportedOperationException, IllegalStateException {
      return (new RedeployOperation(this, modules, application, plan, options)).run();
   }

   public ProgressObject redeploy(TargetModuleID[] modules, InputStream application, InputStream plan, DeploymentOptions options) throws UnsupportedOperationException, IllegalStateException {
      return (new RedeployStreamsOperation(this, modules, application, plan, options)).run();
   }

   public ProgressObject distribute(Target[] targets, File application, File plan) throws IllegalStateException {
      return this.distribute(targets, application, plan, new DeploymentOptions());
   }

   public ProgressObject distribute(Target[] targets, InputStream application, InputStream plan) throws IllegalStateException {
      return this.distribute(targets, application, plan, new DeploymentOptions());
   }

   public ProgressObject distribute(Target[] targets, ModuleType moduleType, InputStream application, InputStream plan) throws IllegalStateException {
      ConfigHelper.checkParam("moduleType", moduleType);
      return this.distribute(targets, moduleType, application, plan, new DeploymentOptions());
   }

   public ProgressObject start(TargetModuleID[] modules) throws IllegalStateException {
      return this.start(modules, new DeploymentOptions());
   }

   public ProgressObject stop(TargetModuleID[] modules) throws IllegalStateException {
      return this.stop(modules, new DeploymentOptions());
   }

   public ProgressObject undeploy(TargetModuleID[] modules) throws IllegalStateException {
      return this.undeploy(modules, new DeploymentOptions());
   }

   public boolean isRedeploySupported() {
      return false;
   }

   public ProgressObject redeploy(TargetModuleID[] modules, File application, File plan) throws UnsupportedOperationException, IllegalStateException {
      return this.redeploy(modules, application, plan, new DeploymentOptions());
   }

   public ProgressObject redeploy(TargetModuleID[] modules, InputStream application, InputStream plan) throws UnsupportedOperationException, IllegalStateException {
      return this.redeploy(modules, application, plan, new DeploymentOptions());
   }

   public Target[] getTargets(DeploymentOptions options) throws IllegalStateException {
      return this.getTargets(options, false);
   }

   public Target[] getTargets(DeploymentOptions options, boolean internalUse) throws IllegalStateException {
      this.checkConnection();
      Target[] targets = null;
      List targs;
      if (options == null) {
         targs = this.serverConnection.getTargets((DeploymentOptions)null, internalUse);
         targets = (Target[])targs.toArray(new Target[targs.size()]);
      } else {
         targs = this.serverConnection.getTargets(options, internalUse);
         targets = (Target[])targs.toArray(new Target[targs.size()]);
      }

      if (targets.length == 0) {
         targets = null;
      }

      if (debug) {
         if (targets == null) {
            Debug.say("Return no targets");
         } else {
            for(int i = 0; i < targets.length; ++i) {
               Target target = targets[i];
               Debug.say("Return target " + target.getName());
            }
         }
      }

      return targets;
   }

   public Target[] getTargets() throws IllegalStateException {
      return this.getTargets((DeploymentOptions)null);
   }

   public TargetModuleID[] getRunningModules(ModuleType moduleType, Target[] targetList) throws TargetException, IllegalStateException {
      return this.getRunningModules(moduleType, targetList, (DeploymentOptions)null);
   }

   public TargetModuleID[] getRunningModules(ModuleType moduleType, Target[] targetList, DeploymentOptions options) throws TargetException, IllegalStateException {
      if (debug) {
         Debug.say("getting all running modules of type " + moduleType);
      }

      return this.getModules(moduleType, targetList, true, options);
   }

   public TargetModuleID[] getNonRunningModules(ModuleType moduleType, Target[] targetList) throws TargetException, IllegalStateException {
      return this.getNonRunningModules(moduleType, targetList, (DeploymentOptions)null);
   }

   public TargetModuleID[] getNonRunningModules(ModuleType moduleType, Target[] targetList, DeploymentOptions options) throws TargetException, IllegalStateException {
      if (debug) {
         Debug.say("getting all nonrunning modules of type " + moduleType);
      }

      return this.getModules(moduleType, targetList, false, options);
   }

   public TargetModuleID[] getModules(ModuleType moduleType, Target[] targetList, boolean running) throws TargetException, IllegalStateException {
      return this.getModules(moduleType, targetList, running, (DeploymentOptions)null);
   }

   public TargetModuleID[] getModules(ModuleType moduleType, Target[] targetList, boolean running, DeploymentOptions options) throws TargetException, IllegalStateException {
      ConfigHelper.checkParam("moduleType", moduleType);
      ConfigHelper.checkParam("targetList", targetList);
      this.checkConnection();
      TargetModuleID[] tmids = this.getAvailableModules(moduleType, targetList, options);
      if (tmids != null) {
         Set modules = new HashSet(Arrays.asList(tmids));
         Iterator mods = modules.iterator();

         while(mods.hasNext()) {
            TargetModuleID tmid = (TargetModuleID)mods.next();
            if (running != this.serverConnection.isRunning(options, tmid)) {
               mods.remove();
            }
         }

         if (modules.isEmpty()) {
            return null;
         } else {
            this.dumpTmidSet(modules, running);
            return (TargetModuleID[])((TargetModuleID[])modules.toArray(new TargetModuleID[0]));
         }
      } else {
         return null;
      }
   }

   private void dumpTmidSet(Set modules, boolean running) {
      if (debug) {
         Debug.say(running ? "Running Modules" : "Nonrunning Modules");
         Iterator var3 = modules.iterator();

         while(var3.hasNext()) {
            TargetModuleID tmid = (TargetModuleID)var3.next();
            Debug.say("   " + tmid);
         }
      }

   }

   public TargetModuleID[] getAvailableModules(ModuleType moduleType, Target[] targetList) throws TargetException, IllegalStateException {
      return this.getAvailableModules(moduleType, targetList, (DeploymentOptions)null);
   }

   public TargetModuleID[] getAvailableModules(ModuleType moduleType, Target[] targetList, DeploymentOptions options) throws TargetException, IllegalStateException {
      ConfigHelper.checkParam("moduleType", moduleType);
      ConfigHelper.checkParam("targetList", targetList);
      if (debug) {
         Debug.say("getting all available modules of type " + moduleType);
      }

      this.checkConnection();
      List modules = this.serverConnection.getModulesForTargets(moduleType, targetList, options);
      return modules.size() == 0 ? null : (TargetModuleID[])((TargetModuleID[])modules.toArray(new TargetModuleID[0]));
   }

   public DeploymentConfiguration createConfiguration(DeployableObject dObj) throws InvalidModuleException {
      ConfigHelper.checkParam("DeployableObject", dObj);

      try {
         return new DeploymentConfigurationImpl(dObj);
      } catch (IOException var4) {
         InvalidModuleException ime = new InvalidModuleException(SPIDeployerLogger.createError(dObj.toString()));
         ime.initCause(var4);
         throw ime;
      }
   }

   public void release() {
      this._release(false);
   }

   private synchronized void _release(boolean force) {
      if (!this.releasing) {
         if (this.serverConnection != null) {
            this.releasing = true;
            this.serverConnection.close(force);
         }

         this.releasing = false;
         this.serverConnection = null;
         this.removeHook();
      }
   }

   public Locale getDefaultLocale() {
      return LocaleManager.getDefaultLocale();
   }

   public Locale getCurrentLocale() {
      return LocaleManager.getCurrentLocale();
   }

   public void setLocale(Locale locale) throws UnsupportedOperationException {
      ConfigHelper.checkParam("Locale", locale);
      LocaleManager.setLocale(locale);
      if (this.serverConnection != null) {
         try {
            this.serverConnection.setLocale(locale);
         } catch (IOException var3) {
            throw new IllegalArgumentException(var3);
         }
      }

      if (debug) {
         Debug.say("Changed locale to : " + locale.toString());
      }

   }

   public Locale[] getSupportedLocales() {
      return LocaleManager.getSupportedLocales();
   }

   public boolean isLocaleSupported(Locale locale) {
      ConfigHelper.checkParam("Locale", locale);
      return LocaleManager.isLocaleSupported(locale);
   }

   public DConfigBeanVersionType getDConfigBeanVersion() {
      return this.dconfigVersion;
   }

   public boolean isDConfigBeanVersionSupported(DConfigBeanVersionType version) {
      ConfigHelper.checkParam("DConfigBeanVersionType", version);
      return DCONFIG_VERSIONS.contains(version);
   }

   public void setDConfigBeanVersion(DConfigBeanVersionType version) throws DConfigBeanVersionUnsupportedException {
      ConfigHelper.checkParam("DConfigBeanVersionType", version);
      if (this.isDConfigBeanVersionSupported(version)) {
         if (!this.dconfigVersion.equals(version)) {
            this.dconfigVersion = version;
         }

      } else {
         throw new DConfigBeanVersionUnsupportedException(SPIDeployerLogger.unsupportedVersion(version.toString()));
      }
   }

   private void setCharacteristics(String uri) throws DeploymentManagerCreationException {
      if (uri.equals("authenticated:deployer:WebLogic")) {
         this.local = true;
         this.auth = true;
      } else if (uri.equals("deployer:WebLogic")) {
         this.local = true;
         this.auth = false;
      } else {
         if (!uri.equals("remote:deployer:WebLogic")) {
            throw new DeploymentManagerCreationException(SPIDeployerLogger.getInvalidURI(uri.toString()));
         }

         this.local = false;
         this.auth = false;
      }

   }

   public TargetModuleID[] getAvailableModules(ModuleType moduleType, Target[] targetList, String applicationName) {
      ConfigHelper.checkParam("targetList", targetList);
      ConfigHelper.checkParam("applicationName", applicationName);
      if (this.serverConnection == null) {
         return null;
      } else {
         List ret = new ArrayList();
         AppRuntimeStateRuntimeMBean appRuntime = this.serverConnection.getAppRuntimeStateRuntimeMBean();
         DomainMBean domain = this.serverConnection.getDomainMBean();
         if (appRuntime != null && domain != null) {
            AppDeploymentMBean appDeployment = domain.lookupAppDeployment(applicationName);
            if (appDeployment == null) {
               appDeployment = domain.lookupLibrary(applicationName);
            }

            if (appDeployment == null) {
               return null;
            } else {
               String appType = ((AppDeploymentMBean)appDeployment).getModuleType();
               ModuleType appModuleType = WebLogicModuleType.getTypeFromString(appType);
               if (moduleType == null || appModuleType.getValue() == moduleType.getValue()) {
                  TargetMBean[] appTargets = ((AppDeploymentMBean)appDeployment).getTargets();
                  if (appTargets != null && appTargets.length > 0) {
                     for(int n = 0; n < appTargets.length; ++n) {
                        boolean fnd = false;
                        if (targetList.length > 0) {
                           for(int i = 0; i < targetList.length; ++i) {
                              if (appTargets[n].getName().equals(targetList[i].getName())) {
                                 fnd = true;
                              }
                           }
                        }

                        if (fnd) {
                           String serverName = appTargets[n].getName();
                           TargetImpl targetImpl = new TargetImpl(serverName, this.getTypeForTarget(appTargets[n]), this);
                           TargetModuleID appTMID = this.createTargetModuleID((String)applicationName, (ModuleType)appModuleType, (Target)targetImpl);
                           ret.add(appTMID);
                           String[] childIDs = appRuntime.getModuleIds(applicationName);
                           if (childIDs != null && childIDs.length > 0) {
                              for(int ch = 0; ch < childIDs.length; ++ch) {
                                 String childType = appRuntime.getModuleType(applicationName, childIDs[ch]);
                                 ModuleType childModuleType = WebLogicModuleType.getTypeFromString(childType);
                                 new TargetModuleIDImpl(childIDs[ch], targetImpl, appTMID, childModuleType.getValue(), this);
                              }
                           }
                        }
                     }
                  }
               }

               return (TargetModuleID[])ret.toArray(new TargetModuleID[ret.size()]);
            }
         } else {
            return null;
         }
      }
   }

   private WebLogicTargetType getTypeForTarget(TargetMBean bean) {
      if (bean instanceof ServerMBean) {
         return WebLogicTargetType.SERVER;
      } else if (bean instanceof ClusterMBean) {
         return WebLogicTargetType.CLUSTER;
      } else if (bean instanceof VirtualHostMBean) {
         return WebLogicTargetType.VIRTUALHOST;
      } else if (bean instanceof VirtualTargetMBean) {
         return WebLogicTargetType.VIRTUALTARGET;
      } else if (bean instanceof JMSServerMBean) {
         return WebLogicTargetType.JMSSERVER;
      } else {
         return bean instanceof SAFAgentMBean ? WebLogicTargetType.SAFAGENT : null;
      }
   }

   public String confirmApplicationName(boolean isRedeployment, String appSource, String altAppDescriptor, String tentativeName, String tentativeApplicationId) throws RuntimeException {
      return this.confirmApplicationName(isRedeployment, new File(appSource), altAppDescriptor == null ? null : new File(altAppDescriptor), tentativeName, tentativeApplicationId);
   }

   public String confirmApplicationName(boolean isRedeployment, File appSource, File altAppDescriptor, String tentativeName, String tentativeApplicationId) throws RuntimeException {
      return this.confirmApplicationName(isRedeployment, appSource, altAppDescriptor, tentativeName, tentativeApplicationId, new DeploymentOptions());
   }

   public String confirmApplicationName(boolean isRedeployment, String appSource, String altAppDescriptor, String tentativeName, String tentativeApplicationId, DeploymentOptions options) throws RuntimeException {
      return this.confirmApplicationName(isRedeployment, new File(appSource), altAppDescriptor == null ? null : new File(altAppDescriptor), tentativeName, tentativeApplicationId, options);
   }

   public String confirmApplicationName(boolean isRedeployment, File appSource, File altDescriptorFile, String tentativeName, String tentativeApplicationId, DeploymentOptions options) throws RuntimeException {
      try {
         if (tentativeApplicationId == null) {
            tentativeApplicationId = "";
         }

         String newName = ApplicationUtils.confirmApplicationName(isRedeployment, appSource, altDescriptorFile, tentativeName, tentativeApplicationId, this.serverConnection.getDomainMBean(), (DomainMBean)null, options);
         return newName;
      } catch (Exception var9) {
         throw new RuntimeException(var9);
      }
   }

   /** @deprecated */
   @Deprecated
   public String confirmApplicationName(boolean isRedeployment, String appSource, String tentativeName, String tentativeApplicationId) throws RuntimeException {
      this.checkIfTemporarilyAllowed();
      return this.confirmApplicationName(isRedeployment, (String)appSource, (String)null, tentativeName, (String)tentativeApplicationId);
   }

   private void checkIfTemporarilyAllowed() {
      if (!this.isTemporarilyAllowed()) {
         throw new AssertionError("Disalowed use of this method.");
      }
   }

   private boolean isTemporarilyAllowed() {
      StackTraceElement[] elems = (new Throwable()).getStackTrace();
      StackTraceElement[] var2 = elems;
      int var3 = elems.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         StackTraceElement elem = var2[var4];
         if (elem.getClassName().contains("console")) {
            return true;
         }
      }

      return false;
   }

   /** @deprecated */
   @Deprecated
   public String confirmApplicationName(boolean isRedeployment, File appSource, String tentativeName, String tentativeApplicationId) throws RuntimeException {
      this.checkIfTemporarilyAllowed();
      return this.confirmApplicationName(isRedeployment, (File)appSource, (File)null, tentativeName, (String)tentativeApplicationId);
   }

   /** @deprecated */
   @Deprecated
   public String confirmApplicationName(boolean isRedeployment, String appSource, String tentativeName, String tentativeApplicationId, DeploymentOptions options) throws RuntimeException {
      this.checkIfTemporarilyAllowed();
      return this.confirmApplicationName(isRedeployment, (String)appSource, (String)null, tentativeName, tentativeApplicationId, options);
   }

   /** @deprecated */
   @Deprecated
   public String confirmApplicationName(boolean isRedeployment, File appSource, String tentativeName, String tentativeApplicationId, DeploymentOptions options) throws RuntimeException {
      this.checkIfTemporarilyAllowed();
      return this.confirmApplicationName(isRedeployment, (File)appSource, (File)null, tentativeName, tentativeApplicationId, options);
   }

   static {
      DCONFIG_VERSIONS = Arrays.asList(DConfigBeanVersionType.V1_4);
   }
}
