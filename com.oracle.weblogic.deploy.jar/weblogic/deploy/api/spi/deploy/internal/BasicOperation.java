package weblogic.deploy.api.spi.deploy.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.deploy.shared.CommandType;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.status.ProgressObject;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.shared.WebLogicModuleTypeUtil;
import weblogic.deploy.api.shared.WebLogicTargetType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.WebLogicTargetModuleID;
import weblogic.deploy.api.spi.config.DescriptorParser;
import weblogic.deploy.api.spi.deploy.TargetImpl;
import weblogic.deploy.api.spi.deploy.TargetModuleIDImpl;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.deploy.api.spi.status.ProgressObjectImpl;
import weblogic.deploy.internal.DeployerTextFormatter;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.deploy.utils.TaskCompletionNotificationFilter;
import weblogic.deploy.utils.TaskCompletionNotificationListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.utils.FileUtils;

public abstract class BasicOperation {
   protected static final boolean debug = Debug.isDebug("deploy");
   protected static final DeployerTextFormatter cat = new DeployerTextFormatter();
   private static final String PREFIX = "app";
   protected WebLogicDeploymentManager dm;
   protected Target[] targetList;
   protected File moduleArchive;
   protected File plan;
   protected DeploymentOptions options;
   protected ProgressObject po;
   protected CommandType cmd;
   protected DeploymentPlanBean planBean;
   protected InstallDir paths;
   protected DeploymentTaskRuntimeMBean task;
   protected String appName;
   protected DeploymentData info;
   protected TargetModuleID[] tmids;
   protected InputStream moduleStream;
   protected InputStream planStream;
   protected boolean initWithTmids;
   protected ModuleType distributeStreamModuleType;
   private TaskCompletionNotificationListener taskCompletionListener;
   protected boolean isWLFullClient;

   protected BasicOperation() {
      this.po = null;
      this.initWithTmids = true;
      this.distributeStreamModuleType = null;
      this.taskCompletionListener = null;
      this.isWLFullClient = false;
   }

   protected BasicOperation(WebLogicDeploymentManager dm, DeploymentOptions options) {
      this.po = null;
      this.initWithTmids = true;
      this.distributeStreamModuleType = null;
      this.taskCompletionListener = null;
      this.isWLFullClient = false;
      this.dm = dm;
      this.options = options;
      if (this.options == null) {
         this.options = new DeploymentOptions();
      }

   }

   protected BasicOperation(WebLogicDeploymentManager dm, File moduleArchive, File plan, DeploymentOptions options) {
      this(dm, options);
      if (moduleArchive != null && moduleArchive.getPath().length() == 0) {
         moduleArchive = null;
      }

      if (plan != null && plan.getPath().length() == 0) {
         plan = null;
      }

      this.moduleArchive = moduleArchive;
      this.plan = plan;
   }

   protected BasicOperation(WebLogicDeploymentManager dm, InputStream moduleArchive, InputStream plan, DeploymentOptions options) {
      this(dm, options);
      this.moduleStream = moduleArchive;
      this.planStream = plan;
   }

   protected TargetModuleID[] createTmidsFromTargets() {
      this.initWithTmids = false;
      Set tl = new HashSet();
      this.deriveAppName();

      for(int i = 0; i < this.targetList.length; ++i) {
         Target target = this.targetList[i];
         tl.add(this.dm.createTargetModuleID((String)this.appName, (ModuleType)WebLogicModuleType.UNKNOWN, (Target)target));
      }

      return (TargetModuleID[])((TargetModuleID[])tl.toArray(new TargetModuleID[0]));
   }

   protected void deriveAppName() {
      if (this.initWithTmids && (this.options == null || !this.getNameFromAppFiles())) {
         try {
            this.appName = ConfigHelper.getAppName(this.tmids, this.options);
         } catch (Exception var2) {
         }
      }

      if (this.appName == null) {
         this.appName = this.confirmAppName(this.options, this.moduleArchive, this.planBean);
      }

      if (debug) {
         Debug.say("appname established as: " + this.appName);
      }

      if (this.appName == null) {
         throw new IllegalArgumentException(SPIDeployerLogger.nullAppName(this.cmd.toString()));
      }
   }

   private boolean getNameFromAppFiles() {
      return this.options.isLibrary() && this.options.isNameFromLibrary() || this.options.isNameFromSource();
   }

   public synchronized ProgressObject run() throws IllegalStateException {
      if (this.po != null) {
         return this.po;
      } else {
         try {
            this.checkConnection();
            this.validateParams();

            try {
               Class var1 = Class.forName("weblogic.j2ee.descriptor.wl.DeploymentPlanBeanImpl");
            } catch (ClassNotFoundException var2) {
               this.isWLFullClient = true;
            }

            if (!this.isWLFullClient && !this.options.isRemote()) {
               this.parsePlan();
            }

            this.initTmids();
            this.setupPaths();
            this.execute();
            this.po = this.createResults();
         } catch (FailedOperationException var3) {
            this.po = var3.getProgressObject();
         }

         return this.po;
      }
   }

   protected void logRequest() {
      String targs;
      if (this.tmids != null) {
         targs = this.getTargetsFromTmids();
      } else {
         targs = this.getTargetsFromTargets();
      }

      if (targs.length() == 0) {
         targs = SPIDeployerLogger.getConfiguredTargetsLoggable().getMessageText();
      }

      String arc = null;
      if (this.moduleStream != null) {
         SPIDeployerLogger.logInitStreamOperation(this.cmd.toString(), this.appName, targs);
      } else {
         if (this.moduleArchive != null) {
            arc = this.moduleArchive.getPath();
         }

         SPIDeployerLogger.logInitOperation(this.cmd.toString(), this.appName, arc, targs);
      }

   }

   private String getTargetsFromTargets() {
      String t = "";

      for(int i = 0; i < this.targetList.length; ++i) {
         t = t + this.targetList[i].getName() + " ";
      }

      return t;
   }

   private String getTargetsFromTmids() {
      String t = "";

      for(int i = 0; i < this.tmids.length; ++i) {
         t = t + this.tmids[i].getTarget().getName() + " ";
      }

      return t;
   }

   protected void checkConnection() {
      if (!this.dm.isConnected()) {
         throw new IllegalStateException(SPIDeployerLogger.notConnected());
      }
   }

   protected void initTmids() {
      if (this.tmids == null) {
         this.tmids = this.createTmidsFromTargets();
      } else {
         this.targetList = this.createTargetsFromTmids();
      }

      this.dumpTmids(this.tmids);
   }

   protected void validateParams() throws FailedOperationException {
      try {
         if (this.tmids == null && this.targetList == null) {
            throw new IllegalArgumentException(SPIDeployerLogger.nullTMID(this.cmd.toString()));
         }
      } catch (IllegalArgumentException var2) {
         throw new FailedOperationException(this.failOperation(var2));
      }

      ApplicationUtils.validateAndSetPartitionParam(this.dm.getHelper(), this.options);
      this.checkIfMTSupportIsNeeded(this.options);
   }

   private void checkIfMTSupportIsNeeded(DeploymentOptions options) {
      if (!this.dm.getHelper().supportsMT() && options.isRGOrRGTOperation()) {
         String msg = DeployerTextFormatter.getInstance().mtOptionWithNonMTServer();
         throw new UnsupportedOperationException(msg, (Throwable)null);
      }
   }

   protected void parsePlan() throws FailedOperationException {
      if (this.planBean == null) {
         InputStream pis = null;

         try {
            if (this.planStream != null) {
               pis = this.planStream;
            } else if (this.plan != null) {
               pis = new FileInputStream(this.plan);
            }

            if (pis != null) {
               this.planBean = DescriptorParser.parseDeploymentPlan((InputStream)pis);
            }
         } catch (IOException var10) {
            throw new FailedOperationException(this.failOperation(var10));
         } finally {
            try {
               if (pis != null && this.planStream == null) {
                  ((InputStream)pis).close();
               }
            } catch (IOException var9) {
            }

         }

      }
   }

   protected void setupPaths() throws FailedOperationException {
      try {
         if (this.moduleArchive != null) {
            if (!this.options.isRemote()) {
               this.moduleArchive = ConfigHelper.normalize(this.moduleArchive).getAbsoluteFile();
            }

            this.paths = new InstallDir(this.confirmAppName(this.options, this.moduleArchive, this.planBean), ConfigHelper.getAppRootFromPlan(this.planBean), false);
            this.paths.setArchive(this.moduleArchive);
            if (this.plan != null) {
               this.paths.setPlan(this.plan.getAbsoluteFile());
            }

            if (this.options.getAltDD() != null) {
               this.paths.setAltAppDD(new File(ConfigHelper.normalize(this.options.getAltDD())));
            }

            ConfigHelper.initPlanDirFromPlan(this.planBean, this.paths);
            if (this.planBean != null && this.planBean.getConfigRoot() != null) {
               File rootFile = (new File(this.planBean.getConfigRoot())).getAbsoluteFile();
               rootFile.mkdir();
               this.paths.setConfigDir(rootFile);
            }

         }
      } catch (IOException var2) {
         throw new FailedOperationException(this.failOperation(var2));
      }
   }

   protected ProgressObject createResults() {
      try {
         ProgressObjectImpl po = this.newProgressObject();
         return po;
      } catch (ServerConnectionException var2) {
         return this.failOperation(var2);
      }
   }

   protected final void startTask() throws ManagementException {
      if (!this.task.isPendingActivation()) {
         this.task.start();
      }

   }

   protected abstract void initializeTask() throws Throwable;

   protected void buildDeploymentData() {
      this.info = this.createDeploymentData();
   }

   protected String getAppIdForUpload() {
      String versionId = this.options.getVersionIdentifier();
      if (versionId == null) {
         versionId = this.info != null && this.info.getDeploymentOptions() != null && this.info.getDeploymentOptions().isLibrary() ? ApplicationVersionUtils.getLibVersionId(this.paths.getArchive().getPath()) : ApplicationVersionUtils.getManifestVersion(this.paths.getArchive().getPath());
      }

      return ApplicationVersionUtils.getApplicationId(this.appName, versionId);
   }

   protected void uploadFiles() throws IOException {
      if (this.paths != null) {
         String appId = this.getAppIdForUpload();
         this.paths = this.dm.getServerConnection().upload(this.paths, appId, (String[])null, this.options);
      }
   }

   protected ProgressObject failOperation(Throwable error) {
      ProgressObjectImpl po;
      if (error instanceof ServerConnectionException) {
         po = new ProgressObjectImpl(this.cmd, ((ServerConnectionException)error).getRootCause(), this.dm);
         po.setMessage(error.toString());
      } else {
         po = new ProgressObjectImpl(this.cmd, error, this.dm);
      }

      return po;
   }

   protected DeploymentData getDeploymentData() {
      if (this.options == null) {
         this.options = new DeploymentOptions();
      }

      DeploymentData dd = new DeploymentData();

      for(int i = 0; i < this.targetList.length; ++i) {
         if (debug) {
            Debug.say("adding global target: " + this.targetList[i].getName());
         }

         dd.addGlobalTarget(this.targetList[i].getName());
      }

      this.loadGeneralOptions(dd, this.options.getName());
      return dd;
   }

   protected void validateForNoTMIDs() {
      if (this.tmids == null || this.tmids.length == 0) {
         throw new IllegalArgumentException(SPIDeployerLogger.noTargetInfo(this.appName));
      }
   }

   ProgressObjectImpl newProgressObject() throws ServerConnectionException {
      if (this.defaultTmidsWithOnlySubModules()) {
         this.createTmidForAdminServer();
      }

      if (this.tmids.length == 0) {
         this.createTmidsFromApp();
      }

      this.updateTmids();
      ProgressObjectImpl po = new ProgressObjectImpl(this.cmd, this.task.getId(), this.tmids, this.dm);
      po.setTaskCompletionListener(this.taskCompletionListener);
      this.dm.getServerConnection().registerListener(po);
      List messages = this.task.getTaskMessages();
      StringBuffer buff = new StringBuffer();
      Iterator var4 = messages.iterator();

      while(var4.hasNext()) {
         Object message = var4.next();
         buff.append(message).append("\n");
      }

      if (buff.length() > 0) {
         po.setMessage(buff.toString());
      }

      return po;
   }

   private void updateTmids() {
      if (this.task != null) {
         String newName = this.task.getApplicationName();
         String versionId = this.task.getApplicationVersionIdentifier();
         newName = ApplicationVersionUtils.getApplicationId(newName, versionId);
         if (this.tmids != null) {
            TargetModuleID[] newTmids = new TargetModuleIDImpl[this.tmids.length];

            for(int n = 0; n < this.tmids.length; ++n) {
               TargetModuleIDImpl tmidImpl = (TargetModuleIDImpl)this.tmids[n];
               newTmids[n] = new TargetModuleIDImpl(newName, tmidImpl.getTarget(), tmidImpl.getParentTargetModuleID(), tmidImpl.getValue(), tmidImpl.getManager());
            }

            this.tmids = newTmids;
         }
      }

   }

   private void createTmidsFromApp() {
      String verID = null;
      if (this.options.getArchiveVersion() != null) {
         verID = this.options.getArchiveVersion();
      }

      String rgt = this.options.getResourceGroupTemplate();
      String rg = this.options.getResourceGroup();
      String partition = this.options.getPartition();
      AppDeploymentMBean mbean = ApplicationVersionUtils.getAppDeployment(this.dm.getHelper().getDomain(), this.appName, verID, rgt, rg, partition);
      if (mbean == null) {
         mbean = ApplicationVersionUtils.getAppDeployment(this.dm.getHelper().getDomain(), this.appName, (String)null, rgt, rg, partition);
      }

      if (mbean != null) {
         List l = this.dm.getServerConnection().getModules(mbean, this.options);
         this.tmids = (TargetModuleID[])((TargetModuleID[])l.toArray(new TargetModuleID[0]));
         this.options.setTmidsFromConfig(true);
         if (rgt != null || rg != null || partition != null) {
            this.createTmidForAdminServer();
         }
      } else {
         this.createTmidForAdminServer();
      }

   }

   private void createTmidForAdminServer() throws IllegalArgumentException {
      TargetModuleID[] newTmids = new TargetModuleID[this.tmids.length + 1];
      if (0 != this.tmids.length) {
         System.arraycopy(this.tmids, 0, newTmids, 1, this.tmids.length);
      }

      String admin = this.dm.getHelper().getAdminServerName();
      Target adminTarget = null;
      if (this.options.getPartition() == null) {
         adminTarget = this.dm.getTarget(admin, this.options);
      } else {
         adminTarget = new TargetImpl(admin, WebLogicTargetType.SERVER, this.dm);
      }

      if (adminTarget == null) {
         throw new IllegalArgumentException(cat.errorNoSuchTarget(admin));
      } else {
         newTmids[0] = this.dm.createTargetModuleID((String)this.appName, (ModuleType)WebLogicModuleType.UNKNOWN, (Target)adminTarget);
         this.tmids = newTmids;
      }
   }

   protected void execute() throws FailedOperationException {
      try {
         this.deriveAppName();
         this.logRequest();
         if (this.defaultTmidsWithOnlySubModules()) {
            this.createTmidForAdminServer();
         }

         if (this.tmids.length == 0) {
            this.createTmidsFromApp();
         }

         this.validateForNoTMIDs();
         if (debug) {
            this.dumpTmids(this.tmids);
         }

         this.updateOptions();
         this.buildDeploymentData();
         this.uploadFiles();
         if (this.paths != null) {
            if (this.paths.getPlan() != null) {
               this.info.setDeploymentPlan(this.paths.getPlan().getPath());
            }

            if (this.paths.getAltAppDD() != null) {
               this.info.setAltDescriptorPath(this.paths.getAltAppDD().getPath());
            }

            this.info.setConfigDirectory(this.paths.getConfigDir().getPath());
            this.info.setRootDirectory(this.paths.getInstallDir().getPath());
         }

         if (debug) {
            Debug.say("Initiating " + this.cmd.toString() + " operation for app, " + this.appName + ", on targets:");

            for(int i = 0; i < this.targetList.length; ++i) {
               Debug.say("   " + this.targetList[i].getName());
            }
         }

         this.initializeTask();
         this.initializeNotifiers();
         this.startTask();
      } catch (ManagementException var2) {
         throw new FailedOperationException(this.failOperation(ManagementException.unWrapExceptions(var2)));
      } catch (FailedOperationException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new FailedOperationException(this.failOperation(var4));
      }
   }

   protected void updateOptions() {
      if (this.options.usesNonExclusiveLock()) {
         this.options.setUseNonexclusiveLock(this.dm.getHelper().needsNonExclusiveLock(this.options.getEditSessionName()));
      }

   }

   private TargetMBean[] getTargetMBeans() {
      this.targetList = this.createTargetsFromTmids();
      Set tset = new HashSet();
      TargetMBean[] mbeans = null;
      DomainMBean domain = this.dm.getHelper().getDomain();
      if (this.options.getPartition() != null) {
         PartitionMBean pm = domain.lookupPartition(this.options.getPartition());
         mbeans = pm.findEffectiveAvailableTargets();
      } else {
         mbeans = domain.getTargets();
      }

      for(int i = 0; i < this.targetList.length; ++i) {
         Target target = this.targetList[i];
         TargetMBean mbean = this.findTarget(target, mbeans);
         if (mbean == null) {
            throw new IllegalArgumentException(SPIDeployerLogger.noSuchTarget(target.getName(), target.getDescription()));
         }

         tset.add(mbean);
      }

      return (TargetMBean[])((TargetMBean[])tset.toArray(new TargetMBean[0]));
   }

   private TargetMBean findTarget(Target target, TargetMBean[] mbeans) {
      for(int i = 0; i < mbeans.length; ++i) {
         TargetMBean mbean = mbeans[i];
         if (mbean.getName().equals(target.getName())) {
            return mbean;
         }
      }

      return null;
   }

   private boolean isTargetedSubmodule(WebLogicTargetModuleID tmid) {
      if (tmid.getValue() == WebLogicModuleType.SUBMODULE.getValue()) {
         return tmid.isTargeted();
      } else {
         TargetModuleID[] ctmids = tmid.getChildTargetModuleID();
         if (ctmids != null) {
            for(int i = 0; i < ctmids.length; ++i) {
               TargetModuleID ctmid = ctmids[i];
               if (this.isTargetedSubmodule((WebLogicTargetModuleID)ctmid)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected boolean defaultTmidsWithOnlySubModules() {
      return false;
   }

   protected boolean tmidsHaveOnlySubModules() {
      boolean hasSubModule = false;

      for(int i = 0; i < this.tmids.length; ++i) {
         if (((WebLogicTargetModuleID)this.tmids[i]).isTargeted()) {
            if (((WebLogicTargetModuleID)this.tmids[i]).getValue() != WebLogicModuleType.SUBMODULE.getValue()) {
               return false;
            }

            hasSubModule = true;
         }

         TargetModuleID[] childTmids = this.tmids[i].getChildTargetModuleID();
         if (null != childTmids) {
            for(int j = 0; j < childTmids.length; ++j) {
               if (((WebLogicTargetModuleID)childTmids[j]).isTargeted()) {
                  if (((WebLogicTargetModuleID)childTmids[j]).getValue() != WebLogicModuleType.SUBMODULE.getValue()) {
                     return false;
                  }

                  hasSubModule = true;
               }

               TargetModuleID[] gChildTmids = childTmids[j].getChildTargetModuleID();
               if (null != gChildTmids) {
                  for(int k = 0; k < gChildTmids.length; ++k) {
                     if (((WebLogicTargetModuleID)gChildTmids[k]).isTargeted()) {
                        if (((WebLogicTargetModuleID)childTmids[j]).getValue() != WebLogicModuleType.SUBMODULE.getValue()) {
                           return false;
                        }

                        hasSubModule = true;
                     }
                  }
               }
            }
         }
      }

      return hasSubModule;
   }

   protected Target[] createTargetsFromTmids() {
      if (this.tmids == null) {
         return null;
      } else {
         Set t = new HashSet();

         for(int i = 0; i < this.tmids.length; ++i) {
            t.add(this.tmids[i].getTarget());
         }

         return (Target[])((Target[])t.toArray(new Target[0]));
      }
   }

   protected DeploymentData createDeploymentData() {
      String appId = null;
      if (this.options == null) {
         this.options = new DeploymentOptions();
      }

      DeploymentData dInfo = new DeploymentData();

      TargetModuleIDImpl tmid;
      for(int i = 0; i < this.tmids.length; ++i) {
         tmid = (TargetModuleIDImpl)this.tmids[i];
         if (tmid.isTargeted()) {
            appId = tmid.getModuleID();
            dInfo.addGlobalTarget(tmid.getTarget().getName());
         }
      }

      int k;
      for(int i = 0; i < this.tmids.length; ++i) {
         tmid = (TargetModuleIDImpl)this.tmids[i];
         if (!tmid.isTargeted()) {
            TargetModuleID[] childTmids = tmid.getChildTargetModuleID();
            if (childTmids != null) {
               for(int j = 0; j < childTmids.length; ++j) {
                  TargetModuleIDImpl ct = (TargetModuleIDImpl)childTmids[j];
                  if (ct.isTargeted()) {
                     if (ct.getValue() == WebLogicModuleType.SUBMODULE.getValue()) {
                        dInfo.addSubModuleTarget((String)null, ct.getModuleID(), new String[]{ct.getTarget().getName()});
                     } else {
                        dInfo.addModuleTarget(ct.getModuleID(), ct.getTarget().getName());
                     }
                  } else {
                     TargetModuleID[] gchildTmids = ct.getChildTargetModuleID();
                     String modName = ct.getModuleID();
                     if (gchildTmids != null) {
                        for(k = 0; k < gchildTmids.length; ++k) {
                           ct = (TargetModuleIDImpl)gchildTmids[k];
                           if (ct.isTargeted()) {
                              dInfo.addSubModuleTarget(modName, ct.getModuleID(), new String[]{ct.getTarget().getName()});
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (this.options.isRGOrRGTOperation() && this.options.getSpecifiedModules() != null) {
         String targetToUse;
         if (this.options.getResourceGroupTemplate() != null) {
            targetToUse = "resourceGroupTemplate";
         } else {
            targetToUse = "resourceGroup";
         }

         String[] var15 = this.options.getSpecifiedModules();
         k = var15.length;

         for(int var11 = 0; var11 < k; ++var11) {
            String specifiedModule = var15[var11];
            dInfo.addModuleTarget(specifiedModule, targetToUse);
         }
      }

      this.loadGeneralOptions(dInfo, appId);
      return dInfo;
   }

   private void loadGeneralOptions(DeploymentData dInfo, String appId) {
      if (this.paths != null) {
         if (this.paths.getPlan() != null) {
            dInfo.setDeploymentPlan(this.paths.getPlan().getPath());
         }

         dInfo.setConfigDirectory(this.paths.getConfigDir().getPath());
         dInfo.setRootDirectory(this.paths.getInstallDir().getPath());
      }

      this.setVersionInfo(appId);
      dInfo.setDeploymentOptions(this.options);
      if (debug) {
         Debug.say(dInfo.toString());
      }

      dInfo.setTargetsFromConfig(this.options.isTmidsFromConfig());
      if (this.options.getTimeout() != 0L) {
         dInfo.setTimeOut((int)this.options.getTimeout());
      }

      dInfo.setRemote(this.options.isRemote());
      dInfo.setThinClient(this.isWLFullClient);
   }

   private void setVersionInfo(String appId) {
      if (this.options.getPlanVersion() == null) {
         String planv = null;
         String vid = ApplicationVersionUtils.getVersionId(appId);
         if (vid != null) {
            planv = ApplicationVersionUtils.getPlanVersion(vid);
         }

         this.setPlanVersion(planv);
      }
   }

   private void setPlanVersion(String v) {
      if (this.planBean != null) {
         this.options.setPlanVersion(this.planBean.getVersion());
      }

      if (this.options.getPlanVersion() == null && v != null) {
         this.options.setPlanVersion(v);
      }

      this.assertPlanVersionValid();
   }

   private void assertPlanVersionValid() {
      if (this.planBean != null) {
         String v1 = this.planBean.getVersion();
         String v2 = this.options.getPlanVersion();
         if (v1 == null || v2 == null) {
            return;
         }

         if (!v2.equals(v1)) {
            throw new IllegalArgumentException(SPIDeployerLogger.versionMismatchPlan(v1, v2));
         }
      }

   }

   protected List validateTmids() throws IllegalArgumentException, ServerConnectionException {
      String localAppName = null;
      List resultTMIDs = new ArrayList();

      for(int i = 0; i < this.tmids.length; ++i) {
         TargetModuleIDImpl tmid = (TargetModuleIDImpl)this.tmids[i];
         if (tmid.getParentTargetModuleID() != null) {
            throw new IllegalArgumentException(SPIDeployerLogger.notRootTMID(this.cmd.toString(), tmid.toString()));
         }

         if (localAppName == null) {
            localAppName = ConfigHelper.getAppName(tmid);
            if (debug) {
               Debug.say("Using app name " + localAppName);
            }

            if (localAppName == null) {
               throw new IllegalArgumentException(SPIDeployerLogger.noAppForTMID(tmid.toString()));
            }
         } else if (!localAppName.equals(ConfigHelper.getAppName(tmid))) {
            throw new IllegalArgumentException(SPIDeployerLogger.diffTMID(this.cmd.toString(), tmid.toString(), localAppName));
         }

         resultTMIDs.add(tmid);
      }

      return resultTMIDs;
   }

   private void dumpTmids(TargetModuleID[] tmids) {
      if (debug && tmids != null) {
         Debug.say("Incoming tmids:");

         for(int i = 0; i < tmids.length; ++i) {
            Debug.say("  " + tmids[i].toString() + ", targeted=" + ((TargetModuleIDImpl)tmids[i]).isTargeted());
            this.dumpChildTmids(tmids[i].getChildTargetModuleID(), "  ");
         }
      }

   }

   private void dumpChildTmids(TargetModuleID[] id, String indent) {
      if (id != null) {
         for(int i = 0; i < id.length; ++i) {
            Debug.say("   " + indent + id[i].toString() + ", targeted=" + ((TargetModuleIDImpl)id[i]).isTargeted());
            this.dumpChildTmids(id[i].getChildTargetModuleID(), "    ");
         }

      }
   }

   protected InstallDir createRootFromStreams(InputStream arc, DeploymentPlanBean plan, DeploymentOptions options) throws IOException {
      File f = FileUtils.createTempDir("app");
      String localAppName = ConfigHelper.getAppName(options, f, plan);
      if (localAppName == null) {
         localAppName = this.confirmAppName(options, f, plan);
      }

      InstallDir paths = new InstallDir(localAppName, f);
      f = paths.getAppDir();
      f.mkdir();
      paths.setAppDir(f.getAbsoluteFile());
      if (debug) {
         Debug.say("App dir set to " + paths.getAppDir().toString());
      }

      localAppName = this.getArchiveName(plan, localAppName);
      f = new File(f, localAppName);
      this.copy(arc, f);
      ModuleType mt = this.distributeStreamModuleType == null ? WebLogicModuleTypeUtil.getFileModuleType(f) : this.distributeStreamModuleType;
      if (mt != null) {
         File p;
         if (!localAppName.endsWith(mt.getModuleExtension())) {
            localAppName = localAppName.concat(mt.getModuleExtension());
            if (debug) {
               Debug.say("renaming " + f.getName() + " to " + localAppName);
            }

            p = new File(paths.getAppDir(), localAppName);
            if (!f.renameTo(p)) {
               if (debug) {
                  Debug.say("rename failed for file, " + f.getAbsolutePath() + "/" + localAppName);
               }

               throw new IOException(SPIDeployerLogger.getRenameError(localAppName));
            }

            f = p;
         }

         paths.setArchive(f.getAbsoluteFile());
         if (debug) {
            Debug.say("Archive set to " + paths.getArchive().toString());
         }

         f = paths.getConfigDir();
         f.mkdir();
         p = new File(f, "plan.xml");
         if (debug) {
            Debug.say("using for plan  " + p.toString());
         }

         if (plan != null) {
            (new EditableDescriptorManager()).writeDescriptorAsXML(((DescriptorBean)plan).getDescriptor(), new FileOutputStream(p));
            paths.setPlan(p.getAbsoluteFile());
            File f1 = ConfigHelper.getConfigRootFile(plan);
            if (f1 != null) {
               f1.mkdir();
               paths.setConfigDir(f1.getAbsoluteFile());
            } else {
               paths.setConfigDir(f.getAbsoluteFile());
            }
         } else {
            if (debug) {
               Debug.say("no plan");
            }

            paths.setConfigDir(f.getAbsoluteFile());
            paths.setPlan(p.getAbsoluteFile());
         }

         if (debug) {
            Debug.say("Plan dir to " + paths.getPlan().toString());
         }

         if (debug) {
            Debug.say("Config dir to " + paths.getConfigDir().toString());
         }

         return paths;
      } else {
         throw new IOException(SPIDeployerLogger.getUnknownType(localAppName));
      }
   }

   private String getArchiveName(DeploymentPlanBean plan, String arcName) {
      if (plan != null) {
         ModuleOverrideBean[] mods = plan.getModuleOverrides();

         for(int i = 0; i < mods.length; ++i) {
            ModuleOverrideBean mod = mods[i];
            if (plan.rootModule(mod.getModuleName())) {
               arcName = mod.getModuleName();
               break;
            }
         }
      }

      return arcName;
   }

   private void copy(InputStream is, File f) throws IOException {
      if (debug) {
         Debug.say("copying stream to " + f.getName());
      }

      byte[] b = new byte[1024];
      FileOutputStream out = new FileOutputStream(f);

      int in;
      try {
         while((in = is.read(b)) > 0) {
            out.write(b, 0, in);
         }
      } finally {
         out.close();
      }

   }

   private void initializeNotifiers() {
      this.task.setNotificationLevel(1);
   }

   private void registerTaskCompletionListener() {
      if (this.task != null) {
         try {
            TaskCompletionNotificationListener tempListener = new TaskCompletionNotificationListener(this.task);
            MBeanServerConnection jmxConn = this.dm.getServerConnection().getMBeanServerConnection();
            if (jmxConn != null) {
               MBeanServerConnection runtimeCon = this.dm.getServerConnection().getRuntimeServerConnection();
               if (runtimeCon == null) {
                  if (debug) {
                     Debug.say("Not adding TaskCompletionNotification since we do not have runtime server mbean connection");
                  }

                  return;
               }

               if (debug) {
                  Debug.say("Adding TaskCompletionNotification Listener : " + tempListener);
               }

               runtimeCon.addNotificationListener(this.task.getObjectName(), tempListener, new TaskCompletionNotificationFilter(), (Object)null);
               if (debug) {
                  Debug.say("Added TaskCompletionNotification Listener : " + tempListener);
               }
            }

            this.taskCompletionListener = tempListener;
         } catch (InstanceNotFoundException var4) {
            if (debug) {
               var4.printStackTrace();
            }
         } catch (IOException var5) {
            if (debug) {
               var5.printStackTrace();
            }
         }
      }

   }

   private String confirmAppName(DeploymentOptions options, File moduleArchive, DeploymentPlanBean planBean) {
      String name = ConfigHelper.getAppName(options, moduleArchive, planBean);
      boolean isRedeploy = this instanceof RedeployOperation;
      if (options != null && !options.isLibrary() && name == null) {
         String version = ConfigHelper.appendVersionToAppName("", options);
         name = this.dm.confirmApplicationName(isRedeploy, (File)moduleArchive, (File)options.getAltDDFile(), (String)null, version, options);
      }

      return name;
   }
}
