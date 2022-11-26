package weblogic.deploy.api.tools.deployer;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.shared.StateType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.status.DeploymentStatus;
import javax.enterprise.deploy.spi.status.ProgressObject;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.status.ProgressObjectImpl;
import weblogic.deploy.api.spi.status.WebLogicDeploymentStatus;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.deploy.utils.MBeanHomeTool;
import weblogic.deploy.utils.MBeanHomeToolException;
import weblogic.deploy.utils.TaskCompletionNotificationListener;
import weblogic.management.ManagementException;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.rmi.extensions.RemoteRuntimeException;

public abstract class Jsr88Operation extends Operation implements Serializable {
   private static final long serialVersionUID = 1L;
   private transient String id;
   protected transient WebLogicDeploymentManager dm;
   protected transient ProgressObject po;
   protected transient Target[] targets;
   protected transient File src = null;
   protected transient File plan = null;
   protected transient DeploymentOptions dOpts;
   protected transient ModuleTargetInfo[] targetInfos;
   protected transient List tmids;

   protected Jsr88Operation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   protected Jsr88Operation(Options options) {
      super(options);
   }

   protected Jsr88Operation() {
   }

   protected void init() {
      super.init();
      this.tmids = new ArrayList();
      this.dOpts = new DeploymentOptions();
      this.targets = new Target[0];
   }

   public void setAllowedOptions() throws IllegalArgumentException {
      this.allowedOptions.add("name");
      this.allowedOptions.add("id");
      this.allowedOptions.add("targets");
      this.allowedOptions.add("submoduletargets");
      this.allowedOptions.add("appversion");
      this.allowedOptions.add("planversion");
      this.allowedOptions.add("libspecver");
      this.allowedOptions.add("libimplver");
      this.allowedOptions.add("library");
      this.allowedOptions.add("specifiedTargetsOnly");
      this.allowedOptions.add("partition");
      this.allowedOptions.add("resourceGroupTemplate");
      this.allowedOptions.add("specifiedModules");
      this.allowedOptions.add("editsession");
   }

   public void validate() throws IllegalArgumentException, DeployerException {
      super.validate();
      this.validateSource();
      this.validateName();
      this.validateDelta();
      this.validateGraceful();
      this.validateRetireTimeout();
      this.validateAllVersions();
      this.validateAltDDs();
      this.validateDeleteFiles();
      this.validateVersions();
      this.validateLibVersions();
      this.validatePlan();
   }

   private void validateTargets() throws IllegalArgumentException {
      if (!this.options.specifiedTargetsOnly) {
         if ((this.options.resourceGroup != null || this.options.resourceGroupTemplate != null || this.options.partition != null) && this.options.moduleTargets != null && this.options.moduleTargets.length > 0) {
            String[] var1 = this.options.moduleTargets;
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               String target = var1[var3];
               if (target.indexOf(64) == -1) {
                  throw new IllegalArgumentException(cat.noTargetsAllowedForRGRGT());
               }
            }
         }

      }
   }

   protected void validatePlan() throws IllegalArgumentException {
      if (this.isPlanRequired() && this.options.plan == null) {
         throw new IllegalArgumentException(cat.errorNoPlan(this.getOperation()));
      } else {
         if (!this.options.remote && this.options.plan != null) {
            File f = new File(this.options.plan);
            if (!f.exists()) {
               throw new IllegalArgumentException(cat.errorNoSuchFile(this.options.plan));
            }
         }

      }
   }

   protected void validateLibVersions() throws IllegalArgumentException {
      this.validateLibVersion(this.options.libSpecVersion);
   }

   private void validateLibVersion(String version) throws IllegalArgumentException {
      if (version != null && !version.matches("[0-9]+(\\.[0-9]+)*")) {
         throw new IllegalArgumentException(cat.invalidLibVersion(version));
      }
   }

   protected void validateVersions() throws IllegalArgumentException {
      this.validateFileVersion(this.options.appVersion);
      this.validateFileVersion(this.options.planVersion);
      this.validateVersionNoVersion();
   }

   protected void validateVersionNoVersion() throws IllegalArgumentException {
      if (this.options.noVersion && (this.options.appVersion != null || this.options.planVersion != null)) {
         throw new IllegalArgumentException(cat.invalidVersionNoVersion());
      }
   }

   private void validateFileVersion(String version) throws IllegalArgumentException {
      if (version != null && (version.length() >= 215 || !version.matches("[a-zA-Z0-9\\._-]*") || version.equals(".") || version.equals(".."))) {
         throw new IllegalArgumentException(cat.invalidFileVersion(this.options.name, version));
      }
   }

   protected void validateName() throws IllegalArgumentException {
   }

   protected void validateSource() throws IllegalArgumentException {
      if (this.isSourceRequired()) {
         if (this.options.source == null) {
            throw new IllegalArgumentException(cat.errorNoSourceSpecified());
         }

         if (!this.options.remote) {
            File f = new File(this.options.source);
            if (!f.exists()) {
               throw new IllegalArgumentException(cat.errorNoSuchFile(this.options.source));
            }

            if (f.isDirectory() && f.list().length == 0) {
               throw new IllegalArgumentException(cat.errorEmptyDirectory(this.options.source));
            }
         }
      }

   }

   protected boolean isSourceRequired() {
      return this.options.upload;
   }

   protected boolean isPlanRequired() {
      return false;
   }

   protected void validateDelta() throws IllegalArgumentException {
      if (this.options.delta != null) {
         if (this.options.delta.length > 1) {
            throw new IllegalArgumentException(cat.errorMultipleSourceSpecified());
         }

         if (this.options.sourceFromOpts && this.options.delta.length > 0) {
            throw new IllegalArgumentException(cat.errorFilesIllegalWithSource());
         }
      }

   }

   protected void validateDeleteFiles() throws IllegalArgumentException {
      if (this.options.deleteFiles) {
         if (this.options.delta == null) {
            throw new IllegalArgumentException(cat.errorMissingDelta());
         }

         if (this.options.upload) {
            throw new IllegalArgumentException(cat.errorUploadDelete(this.getOperation()));
         }
      }

   }

   protected void validateAltDDs() {
      this.options.altAppDD = this.validateAltDD(this.options.altAppDD);
      this.options.altWlsAppDD = this.validateAltDD(this.options.altWlsAppDD);
   }

   private String validateAltDD(String dd) {
      if (dd != null && !this.options.remote) {
         File checkFile = new File(dd);
         if (!checkFile.exists() || checkFile.isDirectory()) {
            throw new IllegalArgumentException(cat.noSourceAltAppDD(dd));
         }

         if (dd.length() > 1 && dd.charAt(1) != ':') {
            dd = checkFile.getAbsolutePath();
         }
      }

      return dd;
   }

   protected void validateRetireTimeout() {
      if (this.options.retireTimeout != -1 && this.options.libModule) {
         throw new IllegalArgumentException(cat.errorRetireTimeoutIllegal("retiretimeout"));
      }
   }

   protected void validateGraceful() throws IllegalArgumentException {
      if (!this.options.graceful && this.options.ignoreSessions) {
         throw new IllegalArgumentException(cat.errorIgnoreSessionsIllegal("graceful", "ignoresessions"));
      } else if (!this.options.redeployOp && !this.options.graceful && this.options.rmiGracePeriod != -1) {
         throw new IllegalArgumentException(cat.errorRMIGracePeriodIllegal("graceful", "rmigraceperiod"));
      } else if (this.options.graceful && this.options.retireTimeout != -1) {
         throw new IllegalArgumentException(cat.errorRetireTimeoutGracefulIllegal("retiretimeout", "graceful"));
      }
   }

   protected void validateAllVersions() throws IllegalArgumentException {
      if (this.options.allVersions) {
         if (!this.options.undeployOp) {
            throw new IllegalArgumentException(cat.errorAllVersionsIllegal1("undeploy", "allversions"));
         } else if (this.options.graceful) {
            throw new IllegalArgumentException(cat.errorAllVersionsIllegal2("graceful", "allversions"));
         } else if (this.options.appVersion != null) {
            throw new IllegalArgumentException(cat.errorAllVersionsIllegal2("appversion", "allversions"));
         }
      }
   }

   public void prepare() throws DeployerException {
      try {
         this.helper = this.dm.getHelper();
         this.src = this.prepareFile(this.options.source);
         this.plan = this.prepareFile(this.options.plan);
         this.prepareDeploymentOptions();
         this.prepareTargets();
         this.prepareTmids();
         if (this.options.id != null) {
            this.dm.setTaskId(this.options.id);
         }

      } catch (IllegalArgumentException var3) {
         DeployerException de = new DeployerException(var3.toString());
         de.initCause(var3);
         throw de;
      }
   }

   public void connect() throws DeployerException {
      URI url = this.getUri();

      DeployerException de;
      try {
         this.tool.processUsernameAndPassword();
         if (this.options.upload) {
            this.dm = SessionHelper.getRemoteDeploymentManager(url.getScheme(), url.getHost(), (new Integer(url.getPort())).toString(), url.getPath(), this.tool.getUser(), this.tool.getPassword(), this.tool.getIDD());
         } else {
            this.dm = SessionHelper.getDeploymentManager(url.getScheme(), url.getHost(), (new Integer(url.getPort())).toString(), url.getPath(), this.tool.getUser(), this.tool.getPassword(), this.tool.getIDD());
         }

      } catch (MBeanHomeToolException var4) {
         de = new DeployerException(var4.toString());
         de.initCause(var4);
         throw de;
      } catch (DeploymentManagerCreationException var5) {
         de = new DeployerException(cat.errorUnableToAccessDeployer(url.toString(), ManagementException.unWrapExceptions(var5).getMessage()));
         if (this.options.debug || this.options.verbose) {
            de.initCause(var5);
         }

         throw de;
      }
   }

   protected void prepareTmids() {
      String name = null;
      if (this.targetInfos.length > 0) {
         name = this.deriveName();
      }

      for(int i = 0; i < this.targetInfos.length; ++i) {
         ModuleTargetInfo mti = this.targetInfos[i];
         this.tmids.add(mti.createTmid(name, this.findTarget(mti, this.targets), this.dm));
      }

   }

   private String deriveName() {
      String name = this.options.name;
      if ((name == null || name.length() == 0) && this.options.source != null) {
         name = this.getNameFromSource(this.options.source);
         this.dOpts.setNameFromSource(true);
      }

      return name;
   }

   private String getNameFromSource(String s) {
      if (s == null) {
         return null;
      } else {
         File f = new File(ConfigHelper.normalize(s));
         if (s.equals(".")) {
            f = (new File(f.getAbsolutePath())).getParentFile();
         }

         return f.getName();
      }
   }

   private void prepareDeploymentOptions() throws IllegalArgumentException {
      if (this.options.stageMode != null) {
         this.dOpts.setStageMode(this.options.stageMode);
      }

      if (this.options.planStageMode != null) {
         this.dOpts.setPlanStageMode(this.options.planStageMode);
      }

      this.dOpts.setTestMode(this.options.adminMode);
      this.dOpts.setGracefulProductionToAdmin(this.options.graceful);
      this.dOpts.setGracefulIgnoreSessions(this.options.ignoreSessions);
      this.dOpts.setRMIGracePeriodSecs(this.options.rmiGracePeriod);
      this.dOpts.setUndeployAllVersions(this.options.allVersions);
      this.dOpts.setSecurityValidationEnabled(this.options.securityValidation);
      if (this.options.securityModel != null) {
         this.dOpts.setSecurityModel(this.options.securityModel);
      }

      this.dOpts.setLibrary(this.options.libModule);
      if (this.options.appVersion != null) {
         this.dOpts.setArchiveVersion(this.options.appVersion);
      }

      if (this.options.planVersion != null) {
         this.dOpts.setPlanVersion(this.options.planVersion);
      }

      this.dOpts.setNoVersion(this.options.noVersion);
      this.dOpts.setRetireTime(this.options.retireTimeout);
      if (this.options.libSpecVersion != null) {
         this.dOpts.setLibSpecVersion(this.options.libSpecVersion);
      }

      if (this.options.libImplVersion != null) {
         this.dOpts.setLibImplVersion(this.options.libImplVersion);
      }

      if (this.options.name != null) {
         this.dOpts.setName(this.options.name);
      }

      this.dOpts.setAltDD(this.options.altAppDD);
      this.dOpts.setAltWlsDD(this.options.altWlsAppDD);
      this.dOpts.setForceUndeployTimeout(this.options.timeout);
      this.dOpts.setDefaultSubmoduleTargets(this.options.isDefaultSubmoduleTargets);
      this.dOpts.setUseNonexclusiveLock(this.options.useNonExclusiveLock);
      this.dOpts.setTimeout(this.options.timeout * 1000L);
      this.dOpts.setDeploymentOrder(this.options.deploymentOrder);
      this.dOpts.setRemote(this.options.remote);
      this.dOpts.setSucceedIfNameUsed(this.options.succeedIfNameUsed);
      this.dOpts.setSpecifiedTargetsOnly(this.options.specifiedTargetsOnly);
      this.dOpts.setResourceGroup(this.options.resourceGroup);
      this.dOpts.setPartition(this.options.partition);
      this.dOpts.setEditSessionName(this.options.editSession);
      this.dOpts.setResourceGroupTemplate(this.options.resourceGroupTemplate);
      if (this.options.specifiedModules != null) {
         this.dOpts.setSpecifiedModules(this.options.specifiedModules);
      }

      this.dOpts.setRemovePlanOverride(this.options.removePlanOverride);
      ApplicationUtils.validateAndSetPartitionParam(this.helper, this.dOpts);
      this.validateTargets();
   }

   private File prepareFile(String s) {
      if (s == null) {
         return null;
      } else {
         File f = new File(ConfigHelper.normalize(s));
         if (!this.options.remote && s != null) {
            if (s.equals(".")) {
               f = (new File(f.getAbsolutePath())).getParentFile();
            }

            if (s.length() > 1 && s.charAt(1) != ':') {
               f = f.getAbsoluteFile();
            }
         }

         return f;
      }
   }

   private void prepareTargets() throws DeployerException {
      if (this.options.moduleTargets == null) {
         this.options.moduleTargets = new String[0];
      }

      List tl = new ArrayList();
      this.targetInfos = this.createTargetInfos();

      for(int i = 0; i < this.targetInfos.length; ++i) {
         ModuleTargetInfo mti = this.targetInfos[i];
         Target targ = this.findTarget(mti);
         if (targ == null) {
            Target[] tgts = this.getDm().getTargets(this.dOpts, true);
            targ = this.findTarget(mti, tgts);
            if (targ == null) {
               throw new DeployerException(cat.errorNoSuchTarget(mti.getTarget()));
            }
         }

         tl.add(targ);
      }

      this.targets = (Target[])((Target[])tl.toArray(new Target[0]));
   }

   private Target findTarget(ModuleTargetInfo mti, Target[] allt) {
      for(int i = 0; i < allt.length; ++i) {
         Target target = allt[i];
         if (target.getName().equals(mti.getTarget())) {
            return target;
         }
      }

      return null;
   }

   private Target findTarget(ModuleTargetInfo mti) {
      return this.getDm().getTarget(mti.getTarget());
   }

   private ModuleTargetInfo[] createTargetInfos() {
      Set targs = new HashSet();
      String[] var2 = this.options.moduleTargets;
      int var3 = var2.length;

      int var4;
      String subModuleTarget;
      for(var4 = 0; var4 < var3; ++var4) {
         subModuleTarget = var2[var4];
         targs.add(new ModuleTargetInfo(subModuleTarget));
      }

      var2 = this.options.submoduleTargets;
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         subModuleTarget = var2[var4];
         targs.add(new SubModuleTargetInfo(subModuleTarget));
      }

      boolean isTargetedToRGT = false;
      boolean isTargetedToRG = false;
      String targetForRGOrRGT = null;
      Options var10000;
      if (this.options.partition == null && this.options.resourceGroup == null) {
         if (this.options.resourceGroupTemplate != null) {
            isTargetedToRGT = true;
            var10000 = this.options;
            targetForRGOrRGT = "resourceGroupTemplate";
         }
      } else {
         isTargetedToRG = true;
         var10000 = this.options;
         targetForRGOrRGT = "resourceGroup";
      }

      if (isTargetedToRG || isTargetedToRGT) {
         String[] var12 = this.options.specifiedModules;
         int var6 = var12.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String targetedModule = var12[var7];
            targs.add(new ModuleTargetInfo(targetedModule, targetForRGOrRGT));
         }
      }

      return (ModuleTargetInfo[])((ModuleTargetInfo[])targs.toArray(new ModuleTargetInfo[0]));
   }

   protected URI getUri() throws DeployerException {
      String uRLString = this.tool.getOpts().getOption("adminurl", "t3://localhost:7001");
      int x = uRLString.indexOf("://");
      if (x == -1) {
         uRLString = "t3://" + uRLString;
      }

      try {
         return new URI(uRLString);
      } catch (URISyntaxException var4) {
         throw new DeployerException(var4.toString());
      }
   }

   public void cleanUp() {
      if (this.dm != null) {
         this.dm.release();
      }

      super.cleanUp();
   }

   public int report() throws DeployerException {
      if (!this.dm.isConnected()) {
         this.println(cat.errorLostConnection());
         DeploymentStatus ds = this.po.getDeploymentStatus();
         if (ds.getMessage() != null) {
            this.println(ds.getMessage());
         }

         this.println(cat.lastKnownStatus(ds.getCommand().toString(), ds.getState().toString()));
      }

      this.println(cat.messageStartedTask(this.task.getId(), this.task.getDescription()));
      if (this.options.nowait) {
         return 0;
      } else if (this.dOpts.usesNonExclusiveLock() && this.po.getDeploymentStatus().isRunning()) {
         this.println(cat.pendingOperation());
         return 0;
      } else {
         this.id = this.options.id;
         if (this.id == null) {
            this.id = this.task.getId();
         }

         long endTime = 0L;
         if (this.options.timeout != 0L) {
            endTime = System.currentTimeMillis() + 1000L * this.options.timeout;
         }

         TaskCompletionNotificationListener listener = ((ProgressObjectImpl)this.po).getTaskCompletionListener();
         if (listener != null) {
            listener.waitForTaskCompletion(1000L * this.options.timeout);
            if (this.task.isRunning()) {
               this.inform(cat.timeOut(this.task.getId()));
            }

            if (this.options.formatted) {
               this.showTaskInformationHeader();
            }

            this.failures = this.showTaskInformation(this.task);
            this.reportState(this.task);
            if (this.options.noexit && this.failures != 0) {
               throw new DeployerException(this.outstr == null ? cat.deploymentFailed() : this.outstr.toString());
            } else {
               return this.failures;
            }
         } else {
            try {
               this.waitForTaskCompletion(this.task, endTime);
               if (this.task.isRunning()) {
                  this.inform(cat.timeOut(this.task.getId()));
               }

               if (this.options.formatted) {
                  this.showTaskInformationHeader();
               }

               this.failures = this.showTaskInformation(this.task);
               this.reportState(this.task);
               if (this.options.noexit && this.failures != 0) {
                  throw new DeployerException(this.outstr == null ? cat.deploymentFailed() : this.outstr.toString());
               } else {
                  return this.failures;
               }
            } catch (RemoteRuntimeException var5) {
               throw new DeployerException(cat.errorLostConnection());
            }
         }
      }
   }

   protected void postExecute() throws Exception {
      if (((ProgressObjectImpl)this.po).getTask() == null) {
         WebLogicDeploymentStatus ds = (WebLogicDeploymentStatus)this.po.getDeploymentStatus();
         Iterator es = ds.getRootException();
         Throwable t = (Throwable)es.next();
         if (t == null) {
            throw new DeployerException(cat.errorFailedOp());
         } else if (t instanceof Exception) {
            throw (Exception)t;
         } else {
            DeployerException de = new DeployerException(t.getMessage());
            de.initCause(t);
            throw de;
         }
      } else {
         this.task = this.helper.getTaskMBean(((ProgressObjectImpl)this.po).getTask());
         if (this.task == null) {
            throw new DeployerException(cat.noTask(((ProgressObjectImpl)this.po).getTask().toString()));
         }
      }
   }

   protected TargetModuleID[] getTmids() {
      return (TargetModuleID[])((TargetModuleID[])this.tmids.toArray(new TargetModuleID[0]));
   }

   public WebLogicDeploymentManager getDm() {
      return this.dm;
   }

   public void setDm(WebLogicDeploymentManager dm) {
      this.dm = dm;
   }

   private void reportState(DeploymentTaskRuntimeMBean task) {
      int taskCurrentState = task.getState();
      switch (taskCurrentState) {
         case 2:
         case 4:
            ((ProgressObjectImpl)this.po).setState(StateType.COMPLETED);
            break;
         case 3:
            ((ProgressObjectImpl)this.po).setState(StateType.FAILED);
      }

   }

   private void waitForTaskCompletion(DeploymentTaskRuntimeMBean task, long endTime) throws DeployerException {
      try {
         while(task.isRunning() && (endTime <= 0L || endTime > System.currentTimeMillis())) {
            try {
               Thread.sleep(100L);
            } catch (InterruptedException var5) {
            }
         }

      } catch (RemoteRuntimeException var6) {
         throw new DeployerException(cat.errorLostConnection());
      }
   }

   public int showTaskInformation(DeploymentTaskRuntimeMBean task) {
      int failures = super.showTaskInformation(task);
      TaskRuntimeMBean[] subTasks = task.getSubTasks();
      if (subTasks != null) {
         for(int n = 0; n < subTasks.length; ++n) {
            failures += super.showTaskInformation((DeploymentTaskRuntimeMBean)subTasks[n]);
         }
      }

      if (!this.options.verbose) {
         return failures;
      } else {
         String op = this.getOperation();
         if (!"deploy".equals(op) && !"distribute".equals(op) && !"extendloader".equals(op)) {
            return failures;
         } else {
            DeploymentData data = task.getDeploymentData();
            this.println(cat.showTargetAssignmentsHeader());
            this.printTargetAssignment(task.getApplicationName(), data.getGlobalTargets(), 0);
            Map allSubModuleTargets = data.getAllSubModuleTargets();
            Iterator it = allSubModuleTargets.keySet().iterator();

            while(true) {
               Map subModuleTargets;
               do {
                  if (!it.hasNext()) {
                     Map moduleTargets = data.getAllModuleTargets();
                     Iterator it = moduleTargets.keySet().iterator();

                     while(it.hasNext()) {
                        String moduleName = (String)it.next();
                        if (!allSubModuleTargets.containsKey(moduleName)) {
                           this.printTargetAssignment(moduleName, data.getModuleTargets(moduleName), 1);
                        }
                     }

                     return failures;
                  }

                  String moduleName = (String)it.next();
                  this.printTargetAssignment(moduleName, data.getModuleTargets(moduleName), 1);
                  subModuleTargets = (Map)allSubModuleTargets.get(moduleName);
               } while(subModuleTargets == null);

               Iterator it2 = subModuleTargets.keySet().iterator();

               while(it2.hasNext()) {
                  String subModule = (String)it2.next();
                  this.printTargetAssignment(subModule, (String[])((String[])subModuleTargets.get(subModule)), 2);
               }
            }
         }
      }
   }

   private void printTargetAssignment(String component, String[] targets, int level) {
      StringBuffer buf = new StringBuffer();

      int i;
      for(i = 0; i < level; ++i) {
         buf.append("   ");
      }

      buf.append("+ ");
      buf.append(component);
      buf.append("  ");
      if (targets != null && targets.length != 0) {
         for(i = 0; i < targets.length; ++i) {
            if (i != 0) {
               buf.append(",");
            }

            buf.append(targets[i]);
         }
      } else {
         buf.append(cat.noneSpecified());
      }

      this.println(buf.toString());
   }
}
