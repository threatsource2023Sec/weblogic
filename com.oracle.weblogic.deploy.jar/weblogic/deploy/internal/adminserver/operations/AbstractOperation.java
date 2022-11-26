package weblogic.deploy.internal.adminserver.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.application.ApplicationFileManager;
import weblogic.application.DeploymentManager;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.config.DescriptorParser;
import weblogic.deploy.api.tools.ModuleInfo;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.deploy.beans.factory.DeploymentBeanFactory;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.Deployment;
import weblogic.deploy.internal.adminserver.EditAccessHelper;
import weblogic.deploy.service.datatransferhandlers.SourceCache;
import weblogic.deploy.service.internal.DeploymentRequestTaskRuntimeMBeanImpl;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.deploy.utils.JMSModuleDefaultingHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.logging.Loggable;
import weblogic.management.ApplicationException;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.deploy.internal.DeployerRuntimeExtendedLogger;
import weblogic.management.deploy.internal.DeployerRuntimeImpl;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.DeploymentServerService;
import weblogic.management.deploy.internal.TaskRuntimeValidator;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.jars.VirtualJarFile;

public abstract class AbstractOperation {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DeploymentManager appDeploymentManager = DeploymentManager.getDeploymentManager();
   protected weblogic.deploy.internal.adminserver.DeploymentManager deploymentManager;
   protected final DeploymentBeanFactory beanFactory;
   protected int taskType;
   protected DeploymentTaskRuntimeMBean deploymentTask;
   protected DeploymentTaskRuntimeMBean templateDeploymentTask;
   protected Deployment deployment;
   protected EditAccessHelper editAccessHelper;
   protected boolean controlOperation;
   protected File moduleArchive;
   protected File plan;
   protected DeploymentPlanBean planBean;
   protected InstallDir paths;
   protected DeploymentOptions options;
   private AuthenticatedSubject authSubject;
   private static int nextDelegateId = 0;
   private static final String DELEGATE_PREFIX = "_DELEGATE_";
   private static int curTaskId = 0;

   AbstractOperation() {
      this.deploymentManager = weblogic.deploy.internal.adminserver.DeploymentManager.getInstance(kernelId);
      this.beanFactory = DeploymentServerService.getDeploymentBeanFactory();
      this.deploymentTask = null;
      this.templateDeploymentTask = null;
      this.deployment = null;
      this.controlOperation = false;
   }

   private static synchronized int getTaskId() {
      return curTaskId++;
   }

   protected boolean isRemote(DeploymentData info) {
      return info.isRemote();
   }

   protected boolean isThinClient(DeploymentData info) {
      return info.isThinClient();
   }

   public DeploymentTaskRuntimeMBean execute(String source, String name, String stagingMode, DeploymentData info, String taskId, boolean startIt, AuthenticatedSubject authenticatedSubject) throws ManagementException {
      this.authSubject = authenticatedSubject;
      Loggable l;
      if (name == null || !name.contains("../") && !name.contains("/..")) {
         this.options = info.getDeploymentOptions();
         if (this.taskType == 7 && ApplicationUtils.isPartitionOrRGShutdown(this.options.getPartition(), this.options.getResourceGroup())) {
            l = DeployerRuntimeExtendedLogger.logCannotStartAppWhenPartitionOrRGShutdownLoggable(this.options.getPartition(), this.options.getResourceGroup());
            l.log();
            throw new ManagementException(l.getMessage());
         } else {
            this.editAccessHelper = getEditAccessHelperForDeployment(kernelId, this.options);
            this.deploymentManager = getDeploymentManagerFromDeployment(kernelId, this.options, this.editAccessHelper);
            boolean callerOwnsEditLock;
            if (this.isRemote(info) || this.isThinClient(info)) {
               if (source != null) {
                  this.moduleArchive = new File(source);
               }

               if (info.getDeploymentPlan() != null) {
                  this.plan = new File(ConfigHelper.normalize(info.getDeploymentPlan()));
               }

               this.parsePlan();
               if (this.moduleArchive != null) {
                  name = ConfigHelper.getAppName(this.options, this.moduleArchive, this.planBean);
                  if (this.options != null && !this.options.isLibrary() && name == null) {
                     callerOwnsEditLock = this instanceof RedeployOperation;
                     String version = ConfigHelper.appendVersionToAppName("", this.options);
                     DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
                     name = appDeploymentManager.confirmApplicationName(callerOwnsEditLock, this.moduleArchive, this.options.getAltDDFile(), (String)null, version, domain, info.getResourceGroupTemplate(), info.getResourceGroup(), info.getPartition());
                  }
               }

               this.setupPaths(name);
               if (this.paths != null) {
                  if (this.paths.getPlan() != null) {
                     info.setDeploymentPlan(this.paths.getPlan().getPath());
                  }

                  info.setConfigDirectory(this.paths.getConfigDir().getPath());
                  info.setRootDirectory(this.paths.getInstallDir().getPath());
               }

               this.setVersionInfo(name);
               this.setupDDPaths(info);
            }

            if (this.isControlOperation()) {
               DomainMBean alternateEditableDomainMBean = this.editAccessHelper.getEditDomainBean(authenticatedSubject);
               this.beanFactory.setAlternateEditableDomain(alternateEditableDomainMBean);

               DeploymentTaskRuntimeMBean var24;
               try {
                  var24 = this.executeControlOperation(source, name, stagingMode, info, taskId, startIt, authenticatedSubject);
               } finally {
                  this.beanFactory.resetAlternateEditableDomain();
               }

               return var24;
            } else {
               callerOwnsEditLock = this.editAccessHelper.isCurrentEditor(authenticatedSubject);
               long expirationTime = this.editAccessHelper.getEditorExpirationTime();
               if (callerOwnsEditLock && expirationTime > 0L && expirationTime < System.currentTimeMillis() && (info == null || info.getDeploymentOptions() == null || !info.getDeploymentOptions().usesExpiredLock())) {
                  callerOwnsEditLock = false;
               }

               boolean useNonExclusiveLock = info != null && info.usesNonExclusiveLock();
               boolean isEditorExclusive = this.editAccessHelper.isEditorExclusive();
               boolean pendingActivate = false;
               if (isDebugEnabled()) {
                  this.debugSay("Deployment operation lock settings:  acquire lock in non-exclusive mode?: " + useNonExclusiveLock + ", caller owns lock?: " + callerOwnsEditLock + "', as exclusive editor:? " + isEditorExclusive);
               }

               boolean isTaskStarting = false;

               try {
                  DomainMBean editableDomainMBean;
                  if (callerOwnsEditLock) {
                     String msg;
                     if (isEditorExclusive) {
                        msg = DeployerRuntimeLogger.exclusiveModeLock();
                        throw new ManagementException(msg);
                     }

                     editableDomainMBean = this.editAccessHelper.getEditDomainBean(authenticatedSubject);
                     if (!useNonExclusiveLock) {
                        msg = DeployerRuntimeLogger.nonExclusiveModeLock();
                        throw new ManagementException(msg);
                     }

                     pendingActivate = true;
                  } else {
                     if (isDebugEnabled()) {
                        this.debugSay("Caller does not own edit lock - deployment subsystem acquiring edit lock in exlusive mode for " + name);
                     }

                     editableDomainMBean = this.editAccessHelper.startEditSession(!useNonExclusiveLock);
                  }

                  this.beanFactory.setEditableDomain(editableDomainMBean, callerOwnsEditLock);
                  AppDeploymentMBean deployable = this.updateConfigurationAndInitializeDeployment(source, name, stagingMode, info, taskId, editableDomainMBean, authenticatedSubject, this.editAccessHelper, callerOwnsEditLock);
                  if (deployable.isAutoDeployedApp() && info != null) {
                     DeploymentOptions opts = info.getDeploymentOptions();
                     if (!opts.isOperationInitiatedByAutoDeployPoller()) {
                        throw new DeploymentException(this.getAutoDeployErrorMsg(deployable.getName()));
                     }
                  }

                  this.postTaskCreationConfigurationUpdate(deployable, name, this.deploymentTask.getDeploymentData());
                  if (pendingActivate) {
                     ((DeploymentTaskRuntime)this.deploymentTask).setPendingActivation(true);
                  }

                  if (callerOwnsEditLock) {
                     this.mergeWithExistingOperationsOnSameApp(this.editAccessHelper);
                  }

                  if (!callerOwnsEditLock || pendingActivate) {
                     this.editAccessHelper.saveEditSessionChanges();
                  }

                  if (!callerOwnsEditLock) {
                     this.editAccessHelper.activateEditSessionChanges((long)info.getTimeOut());
                  }

                  this.beanFactory.resetEditableDomain();
                  DeploymentRequestTaskRuntimeMBeanImpl parentTask = (DeploymentRequestTaskRuntimeMBeanImpl)((DeploymentTaskRuntime)this.deploymentTask).getMyParent();
                  if (!this.handleTemplateTask(info, parentTask)) {
                     this.switchDeploymentTaskIfNeeded(info);
                  }

                  if (!callerOwnsEditLock && startIt) {
                     isTaskStarting = true;
                     this.deploymentTask.start();
                  }

                  return this.deploymentTask;
               } catch (Throwable var21) {
                  if (!isTaskStarting) {
                     this.deploymentManager.deploymentFailedBeforeStart(this.deployment, var21, callerOwnsEditLock, authenticatedSubject, this.editAccessHelper, this.controlOperation);
                  }

                  if (this.deployment == null) {
                     OperationHelper.logTaskFailed(name, this.taskType, var21);
                  }

                  if (var21 instanceof ManagementException) {
                     throw (ManagementException)var21;
                  } else {
                     throw new ManagementException(var21);
                  }
               }
            }
         }
      } else {
         l = DeploymentServiceLogger.logRequestWithInvalidAppNameLoggable(Integer.toString(this.taskType), name);
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   public DeploymentTaskRuntime getTaskRuntime() {
      return (DeploymentTaskRuntime)this.deploymentTask;
   }

   public void rollback(AuthenticatedSubject initiator) {
   }

   protected String getAutoDeployErrorMsg(String appName) {
      return appName;
   }

   protected DeploymentTaskRuntimeMBean executeControlOperation(String source, String name, String stagingMode, DeploymentData info, String taskId, boolean startIt, AuthenticatedSubject authenticatedSubject) throws ManagementException {
      DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      boolean isTaskStarting = false;

      try {
         this.updateConfigurationAndInitializeDeployment(source, name, stagingMode, info, taskId, domainMBean, authenticatedSubject, this.editAccessHelper, false);
         if (startIt) {
            isTaskStarting = true;
            this.deploymentTask.start();
         }

         return this.deploymentTask;
      } catch (Throwable var11) {
         if (!isTaskStarting) {
            this.deploymentManager.deploymentFailedBeforeStart(this.deployment, var11, false, authenticatedSubject, this.editAccessHelper, this.controlOperation);
         }

         if (this.deployment == null) {
            OperationHelper.logTaskFailed(name, this.taskType, var11);
         }

         if (var11 instanceof ManagementException) {
            throw (ManagementException)var11;
         } else {
            throw new ManagementException(var11);
         }
      }
   }

   private AppDeploymentMBean updateConfigurationAndInitializeDeployment(String source, String name, String stagingMode, DeploymentData info, String taskId, DomainMBean domainMBean, AuthenticatedSubject authenticatedSubject, EditAccessHelper editAccessHelper, boolean callerOwnsEditLock) throws ManagementException {
      source = OperationHelper.normalizePaths(source, info);
      if (info.getDeploymentOptions() != null && info.getDeploymentOptions().isDefaultSubmoduleTargets()) {
         this.defaultSubModuleTargets(name, source, info, domainMBean);
      }

      if (ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(info) && !info.getDeploymentOptions().getSpecifiedTargetsOnly()) {
         this.setTargetsForPartitionDeployment(info);
      }

      AppDeploymentMBean deployable = this.updateConfiguration(source, name, stagingMode, info, taskId, callerOwnsEditLock);
      doDeploymentTypeValidations(domainMBean, name, info, deployable);
      this.createRuntimeObjects(source, taskId, deployable, info, this.getCreateTaskType(), domainMBean, authenticatedSubject, callerOwnsEditLock);
      return deployable;
   }

   protected void defaultSubModuleTargets(String appName, String source, DeploymentData info, DomainMBean domain) throws ManagementException {
      SessionHelper helper = null;
      AppDeploymentMBean mbean = null;
      boolean lookup = false;

      try {
         File tmpArchive = null;
         if (source != null) {
            tmpArchive = new File(source);
         } else {
            mbean = ApplicationUtils.lookupAppDeploymentInGivenScope(appName, info, domain);
            lookup = true;
            if (mbean == null) {
               return;
            }

            tmpArchive = new File(mbean.getSourcePath());
         }

         if (this.hasJMSModules(tmpArchive)) {
            if (!lookup) {
               mbean = ApplicationUtils.lookupAppDeploymentInGivenScope(appName, info, domain);
            }

            if (isDebugEnabled()) {
               this.debugSay(tmpArchive + " has JMS modules");
            }

            helper = SessionHelper.getInstance(SessionHelper.getDisconnectedDeploymentManager());
            helper.setApplication(tmpArchive);
            helper.setPlan(this.plan);
            helper.inspect();
            boolean standalone = false;
            ModuleInfo minfo = helper.getModuleInfo();
            ArrayList sinfos = new ArrayList();
            if (minfo.getType().getValue() == WebLogicModuleType.JMS.getValue()) {
               sinfos.add(minfo);
               standalone = true;
            } else if (minfo.getType().getValue() == WebLogicModuleType.EAR.getValue()) {
               ModuleInfo[] minfos = minfo.getSubModules();

               for(int i = 0; i < minfos.length; ++i) {
                  ModuleInfo moduleInfo = minfos[i];
                  if (moduleInfo.getType().getValue() == WebLogicModuleType.JMS.getValue()) {
                     sinfos.add(moduleInfo);
                  }
               }
            }

            TargetMBean[] targs = this.getTargetMBeans(domain, info);
            Iterator var32 = sinfos.iterator();

            while(true) {
               ModuleInfo s;
               do {
                  if (!var32.hasNext()) {
                     return;
                  }

                  Object sinfo = var32.next();
                  s = (ModuleInfo)sinfo;
               } while(mbean != null && this.checkAppTargetting(mbean, s.getName(), helper));

               Map subDeploymentMap = this.getDefaultJMSTargets(domain, targs, s.getName(), helper, info);
               Iterator var17 = subDeploymentMap.keySet().iterator();

               while(var17.hasNext()) {
                  Object o = var17.next();
                  String subDeploymentName = (String)o;
                  TargetMBean[] targets = (TargetMBean[])((TargetMBean[])subDeploymentMap.get(subDeploymentName));
                  TargetMBean[] var21 = targets;
                  int var22 = targets.length;

                  for(int var23 = 0; var23 < var22; ++var23) {
                     TargetMBean target = var21[var23];
                     String modName = s.getName();
                     if (standalone) {
                        modName = null;
                     }

                     if (!info.isSubModuleTargeted(modName, subDeploymentName)) {
                        info.addSubModuleTarget(modName, subDeploymentName, new String[]{target.getName()});
                     }
                  }
               }
            }
         }
      } catch (Throwable var29) {
         throw new ManagementException(var29);
      } finally {
         if (helper != null) {
            helper.close();
         }

      }

   }

   private Map getDefaultJMSTargets(DomainMBean domain, TargetMBean[] deploymentTargets, String name, SessionHelper helper, DeploymentData info) throws ConfigurationException {
      JMSBean module = null;

      try {
         module = helper.getJMSDescriptor(name);
      } catch (FileNotFoundException var8) {
      }

      return module == null ? null : JMSModuleDefaultingHelper.getJMSDefaultTargets(module, domain, deploymentTargets, info);
   }

   private boolean checkAppTargetting(AppDeploymentMBean app, String name, SessionHelper helper) {
      SubDeploymentMBean[] mods;
      if (helper.getDeployableObject().getType().getValue() == ModuleType.EAR.getValue()) {
         mods = app.getSubDeployments();
         SubDeploymentMBean[] var5 = mods;
         int var6 = mods.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            SubDeploymentMBean mod = var5[var7];
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

   private boolean hasJMSModules(File tmpArchive) {
      if (tmpArchive.isFile() && tmpArchive.getPath().endsWith("-jms.xml")) {
         return true;
      } else {
         VirtualJarFile vjf = null;

         try {
            ApplicationFileManager afm = ApplicationFileManager.newInstance(tmpArchive);
            vjf = afm.getVirtualJarFile();
            Iterator entries = vjf.entries();

            JarEntry o;
            do {
               if (!entries.hasNext()) {
                  return false;
               }

               o = (JarEntry)entries.next();
            } while(!o.getName().endsWith("-jms.xml"));

            if (isDebugEnabled()) {
               this.debugSay(" found JMS module: " + o.getName());
            }

            boolean var6 = true;
            return var6;
         } catch (IOException var17) {
            return false;
         } finally {
            if (vjf != null) {
               try {
                  vjf.close();
               } catch (IOException var16) {
               }
            }

         }
      }
   }

   private TargetMBean[] getTargetMBeans(DomainMBean domain, DeploymentData info) {
      String[] targetList = info.getTargets();
      if (targetList == null || targetList.length < 1) {
         targetList = new String[]{domain.getAdminServerName()};
      }

      Set tset = new HashSet();
      TargetMBean[] mbeans = domain.getTargets();
      String[] var6 = targetList;
      int var7 = targetList.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String aTargetList = var6[var8];
         TargetMBean mbean = this.findTarget(aTargetList, mbeans);
         if (mbean != null) {
            tset.add(mbean);
         }
      }

      return (TargetMBean[])((TargetMBean[])tset.toArray(new TargetMBean[tset.size()]));
   }

   private TargetMBean findTarget(String target, TargetMBean[] mbeans) {
      TargetMBean[] var3 = mbeans;
      int var4 = mbeans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TargetMBean mbean = var3[var5];
         if (mbean.getName().equals(target)) {
            return mbean;
         }
      }

      return null;
   }

   private static void doDeploymentTypeValidations(DomainMBean editableDomainMBean, String name, DeploymentData info, AppDeploymentMBean deployable) throws ManagementException {
      OperationHelper.validateAutoDeployTarget(editableDomainMBean, name, info);
      OperationHelper.validateSplitDirTarget(editableDomainMBean, name, info);
      if (isInternalApp(name)) {
         Loggable l = DeployerRuntimeLogger.logNoOpOnInternalAppLoggable(deployable.getName());
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   private boolean isControlOperation() {
      return this.controlOperation;
   }

   private static boolean isInternalApp(String appName) {
      DomainMBean currentDomain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      AppDeploymentMBean app = currentDomain.lookupAppDeployment(appName);
      return null == app ? false : app.isInternalApp();
   }

   protected boolean removeBeans() {
      return false;
   }

   protected int getCreateTaskType() {
      return this.taskType;
   }

   protected abstract AppDeploymentMBean updateConfiguration(String var1, String var2, String var3, DeploymentData var4, String var5, boolean var6) throws ManagementException;

   protected void postTaskCreationConfigurationUpdate(AppDeploymentMBean deployable, String name, DeploymentData info) throws ManagementException {
   }

   protected static void addAdminServerAsDefaultTarget(DeploymentData info) {
      if (info != null) {
         if (info.isActionFromDeployer()) {
            RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
            if (!info.hasTargets()) {
               info.addGlobalTarget(runtime.getServerName());
            }
         }

      }
   }

   protected final AppDeploymentMBean createOrReconcileMBeans(String source, String appName, DeploymentData info, String versionId, AppDeploymentMBean deployable, String stagingMode) throws ManagementException {
      Loggable l;
      try {
         if (deployable == null) {
            deployable = this.createMBeans(source, appName, info, versionId);
            DomainMBean domain = this.beanFactory.getEditableDomain();
            boolean ignoreVersion = info.getDeploymentOptions().isNoVersion();
            boolean isOldDeploymentOrderSet = false;
            int olddeploymentOrder = 0;
            if (!ignoreVersion) {
               AppDeploymentMBean deployedApp = ApplicationUtils.getAppDeployment(domain, appName, (String)null, (DeploymentData)info);
               if (deployedApp != null) {
                  isOldDeploymentOrderSet = deployedApp.isSet("DeploymentOrder");
                  olddeploymentOrder = deployedApp.getDeploymentOrder();
                  if (isDebugEnabled()) {
                     this.debugSay("createOrReconcileMBeans Copying the old deployment order value of " + olddeploymentOrder + " from AppDeploymentMBean " + deployable.getApplicationName());
                  }
               }
            }

            DeploymentOptions deplOpts = info.getDeploymentOptions();
            this.setStagingMode(stagingMode, deplOpts, deployable);
            this.setDeploymentOrder(olddeploymentOrder, isOldDeploymentOrderSet, deplOpts, deployable);
         } else {
            this.reconcileMBeans(info, deployable);
         }

         if (isDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
            String debugMsg = "Deploying app " + deployable.getApplicationName() + (deployable.getVersionIdentifier() == null ? "" : " versionId: " + deployable.getVersionIdentifier()) + " with path: " + deployable.getAbsoluteSourcePath();
            if (isDebugEnabled()) {
               this.debugSay(debugMsg);
            } else {
               Debug.deploymentDebugConcise(debugMsg);
            }
         }

         return deployable;
      } catch (FileNotFoundException var12) {
         l = DeployerRuntimeLogger.logInvalidSourceLoggable(source, appName, var12.getMessage());
         l.log();
         if (info.isNewApplication() && deployable != null) {
            this.beanFactory.removeMBean(deployable);
         }

         throw new ManagementException(l.getMessage(), var12);
      } catch (ApplicationException var13) {
         l = DeployerRuntimeLogger.logInvalidAppLoggable(source, appName, var13.getMessage());
         l.log();
         throw new ManagementException(l.getMessage(), var13);
      }
   }

   protected final AppDeploymentMBean createMBeans(String filename, String appName, DeploymentData info, String versionId) throws FileNotFoundException, ManagementException {
      File file = new File(filename);
      if (isDebugEnabled()) {
         this.debugSay("Creating mbeans for " + filename);
      }

      info.setNewApp(true);
      String appId = ApplicationVersionUtils.getApplicationId(appName, versionId);

      try {
         AppDeploymentMBean du = this.beanFactory.createAppDeploymentMBean(appId, file, info);
         return du;
      } catch (InvalidTargetException var10) {
         String msg = DeployerRuntimeLogger.invalidTarget();
         throw new ManagementException(msg, var10);
      }
   }

   protected final void validateAllTargets(AppDeploymentMBean dep, DeploymentData info, String appName, String version, String taskType) throws DeploymentException {
      String[] targets = info.getGlobalTargets();
      if (targets != null && targets.length != 0) {
         String[] var7 = targets;
         int var8 = targets.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String target = var7[var9];
            if (!this.isValidTarget(target, dep)) {
               String appId = ApplicationVersionUtils.getApplicationId(appName, version);
               Loggable log = DeployerRuntimeLogger.logInvalidTargetForOperationLoggable(appId, target, taskType);
               throw new DeploymentException(log.getMessage());
            }
         }

      }
   }

   private boolean isValidTarget(String target, AppDeploymentMBean dep) {
      TargetMBean[] appTargets = dep.getTargets();
      if (appTargets != null && appTargets.length != 0) {
         TargetMBean[] var4 = appTargets;
         int var5 = appTargets.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            TargetMBean eachTarget = var4[var6];
            if (eachTarget.getName().equals(target)) {
               return true;
            }

            if (eachTarget instanceof ClusterMBean) {
               ClusterMBean cluster = (ClusterMBean)eachTarget;
               ServerMBean[] clusterServers = cluster.getServers();
               ServerMBean[] var10 = clusterServers;
               int var11 = clusterServers.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  ServerMBean clusterServer = var10[var12];
                  if (clusterServer.getName().equals(target)) {
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void deleteFailedApplication(AppDeploymentMBean dep) {
      appDeploymentManager.getMBeanFactory().cleanupMBeans(this.beanFactory.getEditableDomain(), dep.getAppMBean());
   }

   protected final void reconcileMBeans(DeploymentData info, AppDeploymentMBean deployment) throws ApplicationException, FileNotFoundException, ManagementException {
      this.reconcileMBeans(info, deployment, false);
   }

   protected void reconcileMBeans(DeploymentData info, AppDeploymentMBean deployment, boolean preservePlan) throws ApplicationException, FileNotFoundException, ManagementException {
      boolean requiresDescriptorInvalidation = false;
      String securityModel;
      if (!OperationHelper.hasFiles(info)) {
         if (info.getDeploymentPlan() != null) {
            securityModel = info.getConfigDirectory();
            if (securityModel != null) {
               File f = new File(securityModel);
               String[] s = f.list();
               if (s == null || s.length == 0) {
                  securityModel = null;
               }
            }

            deployment.setPlanDir(securityModel);
            deployment.setPlanPath(info.getDeploymentPlan());
            requiresDescriptorInvalidation = true;
         } else if (!preservePlan && deployment.getPlanPath() != null) {
            deployment.setPlanPath((String)null);
            deployment.setPlanDir((String)null);
            requiresDescriptorInvalidation = true;
         }
      }

      if (info.getAltDescriptorPath() != null) {
         deployment.setAltDescriptorPath(info.getAltDescriptorPath());
         requiresDescriptorInvalidation = true;
      }

      if (info.getAltWLSDescriptorPath() != null) {
         deployment.setAltWLSDescriptorPath(info.getAltWLSDescriptorPath());
         requiresDescriptorInvalidation = true;
      }

      if (info.getIsNameFromSource()) {
         info.setFile((String[])null);
      }

      securityModel = info.getSecurityModel();
      if (securityModel != null && securityModel.length() > 0 && !securityModel.equals(deployment.getSecurityDDModel())) {
         DeployerRuntimeLogger.logDiffSecurityModelIgnoredForRedeploy(ApplicationVersionUtils.getDisplayName(deployment), deployment.getSecurityDDModel(), securityModel);
      }

      if (info.getDeploymentPrincipalName() != null) {
         deployment.setDeploymentPrincipalName(info.getDeploymentPrincipalName());
         requiresDescriptorInvalidation = true;
      }

      if (isDebugEnabled()) {
         this.debugSay("Reloading mbeans for " + deployment.getAbsoluteSourcePath());
      }

      try {
         this.beanFactory.addTargetsInDeploymentData(info, deployment);
      } catch (InvalidTargetException var8) {
         String msg = DeployerRuntimeLogger.invalidTarget();
         throw new ManagementException(msg, var8);
      }

      if (requiresDescriptorInvalidation) {
         SourceCache.updateDescriptorsInCache(deployment);
      }

   }

   protected static void debugDeployer(String s) {
      Debug.deploymentDebug(s);
   }

   protected void setStagingMode(String stagingMode, DeploymentOptions deplOpts, AppDeploymentMBean deployable) throws ManagementException {
      if (stagingMode != null) {
         if (!stagingMode.equals("nostage") && !stagingMode.equals("stage") && !stagingMode.equals("external_stage")) {
            Loggable l = DeployerRuntimeLogger.logInvalidStagingModeLoggable(stagingMode);
            l.log();
            this.deleteFailedApplication(deployable);
            throw new ManagementException(l.getMessage());
         }

         if (isDebugEnabled()) {
            this.debugSay("Staging Mode is " + stagingMode);
         }
      }

      String planStagingMode = null;
      if (deplOpts != null) {
         planStagingMode = deplOpts.getPlanStageMode();
      }

      if (planStagingMode != null) {
         if (!planStagingMode.equals("nostage") && !planStagingMode.equals("stage") && !planStagingMode.equals("external_stage")) {
            Loggable l = DeployerRuntimeLogger.logInvalidStagingModeLoggable(stagingMode);
            l.log();
            this.deleteFailedApplication(deployable);
            throw new ManagementException(l.getMessage());
         }

         if (isDebugEnabled()) {
            this.debugSay("Plan Staging Mode is " + planStagingMode);
         }
      }

      deployable.setStagingMode(stagingMode);
      deployable.setPlanStagingMode(planStagingMode);
   }

   protected void setDeploymentOrder(int olddeploymentOrder, boolean isOldDeploymentOrderSet, DeploymentOptions deplOpts, AppDeploymentMBean deployable) throws ManagementException {
      if (deplOpts != null) {
         int deploymentOrder = deplOpts.getDeploymentOrder();
         if (isDebugEnabled()) {
            this.debugSay("In setDeploymentOrder  deploymentOrder = " + deploymentOrder + " olddeploymentOrder: " + olddeploymentOrder + " isOldDeploymentOrderSet = " + isOldDeploymentOrderSet);
         }

         if (deploymentOrder != 100) {
            deployable.setDeploymentOrder(deploymentOrder);
         } else if (olddeploymentOrder != 0 && isOldDeploymentOrderSet) {
            deployable.setDeploymentOrder(olddeploymentOrder);
         }

      }
   }

   protected void setDeploymentOrder(DeploymentOptions deplOpts, AppDeploymentMBean deployable) throws ManagementException {
      this.setDeploymentOrder(0, false, deplOpts, deployable);
   }

   protected final void printDebugStartMessage(String source, String appName, String version, DeploymentData info, String id, String task, String stagemode) {
      if (isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         sb.append(task).append(" invoked with:\n");
         if (source != null) {
            sb.append("  Path = <").append(source).append(">\n");
         }

         sb.append("  name = <").append(appName).append(">\n");
         if (version != null) {
            sb.append("  version = <").append(version).append(">\n");
         }

         if (stagemode != null) {
            sb.append("  StagingMode = <").append(stagemode).append(">\n");
         }

         if (info != null) {
            sb.append("  info = <").append(info.toString()).append(">\n");
         }

         sb.append("  id = <").append(id).append(">\n");
         this.debugSay(sb.toString());
      }

   }

   protected final void createRuntimeObjects(String source, String taskId, AppDeploymentMBean dep, DeploymentData info, int operation, DomainMBean domainMbean, AuthenticatedSubject authenticatedSubject, boolean callerIsEditLockOwner) throws ManagementException {
      this.deploymentTask = this.createAndRegisterDeploymentTaskRuntime(source, taskId, dep, info, operation, domainMbean);
      if (callerIsEditLockOwner) {
         ((DeploymentTaskRuntime)this.deploymentTask).setSubject(authenticatedSubject);
      }

      ((DeploymentTaskRuntime)this.deploymentTask).setAdminOperation(this);
      this.editAccessHelper = getEditAccessHelperForDeployment(kernelId, this.options);
      weblogic.deploy.internal.adminserver.DeploymentManager partitionDeploymentManager = getDeploymentManagerFromDeployment(kernelId, info.getDeploymentOptions(), this.editAccessHelper);
      this.deployment = partitionDeploymentManager.createDeployment(taskId, info, operation, (DeploymentTaskRuntime)this.deploymentTask, domainMbean, false, authenticatedSubject, callerIsEditLockOwner, this.controlOperation, false);
      this.deployment.setEditAccessHelper(this.editAccessHelper);
   }

   private DeploymentTaskRuntime createAndRegisterDeploymentTaskRuntime(String source, String taskId, AppDeploymentMBean dep, DeploymentData info, int operation, DomainMBean editableDomainMBean) throws ManagementException {
      DeployerRuntimeImpl deployerRuntime = this.deploymentManager.getDeployerRuntime();
      DeploymentTaskRuntime taskRuntime = new DeploymentTaskRuntime(source, dep, info, taskId, operation, editableDomainMBean, this.controlOperation, false);
      taskRuntime.setDepMgr(this.deploymentManager);

      try {
         deployerRuntime.registerTaskRuntime(taskId, taskRuntime, this.getTaskRuntimeValidator());
         return taskRuntime;
      } catch (ManagementException var10) {
         taskRuntime.setState(3);
         throw var10;
      }
   }

   protected static void invalidateCache(BasicDeploymentMBean bean) {
      SourceCache.invalidateCache(bean);
   }

   protected void parsePlan() throws ManagementException {
      if (this.planBean == null) {
         InputStream pis = null;

         try {
            if (this.plan != null) {
               pis = new FileInputStream(this.plan);
            }

            if (pis != null) {
               this.planBean = DescriptorParser.parseDeploymentPlan(pis);
            }
         } catch (IOException var10) {
            throw new ManagementException(var10);
         } finally {
            try {
               if (pis != null) {
                  pis.close();
               }
            } catch (IOException var9) {
            }

         }

      }
   }

   protected void setupPaths(String appName) throws ManagementException {
      try {
         if (this.moduleArchive != null) {
            this.moduleArchive = ConfigHelper.normalize(this.moduleArchive).getCanonicalFile();
            this.paths = new InstallDir(appName, ConfigHelper.getAppRootFromPlan(this.planBean));
            this.paths.setArchive(this.moduleArchive);
            if (this.plan != null) {
               this.paths.setPlan(this.plan.getCanonicalFile());
            }

            ConfigHelper.initPlanDirFromPlan(this.planBean, this.paths);
            if (this.planBean != null && this.planBean.getConfigRoot() != null) {
               this.paths.setConfigDir((new File(this.planBean.getConfigRoot())).getCanonicalFile());
            }

         }
      } catch (IOException var3) {
         throw new ManagementException(var3);
      }
   }

   protected boolean isSameOperationType(AbstractOperation theOp) {
      return this.getClass().equals(theOp.getClass());
   }

   protected final boolean areTargetsSame(AbstractOperation theOp) {
      DeploymentTaskRuntime theTask = this.getTaskRuntime();
      DeploymentData curData = theTask.getDeploymentData();
      DeploymentTaskRuntime otherTask = theOp.getTaskRuntime();
      DeploymentData otherData = otherTask.getDeploymentData();
      List curGlobalTargets = Arrays.asList(curData.getGlobalTargets());
      List otherGlobalTargets = Arrays.asList(otherData.getGlobalTargets());
      boolean targetsAreSame = curGlobalTargets.equals(otherGlobalTargets);
      if (isDebugEnabled()) {
         this.debugSay(" Global Targets are same : " + targetsAreSame);
      }

      if (!targetsAreSame) {
         return false;
      } else {
         targetsAreSame = curData.getAllModuleTargets().equals(otherData.getAllModuleTargets());
         if (isDebugEnabled()) {
            this.debugSay(" Module Targets are same : " + targetsAreSame);
         }

         if (!targetsAreSame) {
            return false;
         } else {
            targetsAreSame = curData.getAllSubModuleTargets().equals(otherData.getAllSubModuleTargets());
            if (isDebugEnabled()) {
               this.debugSay(" Submodule Targets are same : " + targetsAreSame);
               this.debugSay(" Targets are same : " + targetsAreSame);
            }

            return targetsAreSame;
         }
      }
   }

   protected final void setAsDelegatorTo(AbstractOperation otherOp) throws DeploymentException {
      DeploymentTaskRuntime theTask = this.getTaskRuntime();
      List removalTaskIds = new ArrayList();
      removalTaskIds.add(theTask.getId());
      this.deploymentManager.removeDeploymentsForTasks(removalTaskIds);
      DeploymentTaskRuntime otherTask = otherOp.getTaskRuntime();
      otherTask.addDelegator(theTask);
   }

   protected void removeSelf() throws DeploymentException {
      DeploymentTaskRuntime theTask = this.getTaskRuntime();
      List removalTaskIds = new ArrayList();
      removalTaskIds.add(theTask.getId());
      this.deploymentManager.removeDeploymentsForTasks(removalTaskIds);
      theTask.updateAllTargetsWithSuccessForMerging();
   }

   protected final void mergeWithSameOperationType(AbstractOperation otherOp) throws ManagementException {
      if (this.isSameOperationType(otherOp)) {
         if (this.areTargetsSame(otherOp)) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug(" Operation '" + this + "' is same type and has same targets as '" + otherOp + "'");
            }

            this.setAsDelegatorTo(otherOp);
         } else {
            AbstractOperation mergedOp = this.createCopy();
            DeploymentTaskRuntime theTask = this.getTaskRuntime();
            DeploymentData curData = theTask.getDeploymentData();
            DeploymentTaskRuntime otherTask = otherOp.getTaskRuntime();
            DeploymentData otherData = otherTask.getDeploymentData();
            DeploymentData mergedData = curData.copy();
            mergedData.addGlobalTargets(otherData.getGlobalTargets());
            if (otherData.hasModuleTargets()) {
               mergedData.addOrUpdateModuleTargets(otherData.getAllModuleTargets());
            }

            if (otherData.hasSubModuleTargets()) {
               mergedData.addOrUpdateSubModuleTargets(otherData.getAllSubModuleTargets());
            }

            if (otherData.hasFiles()) {
               mergedData.addFiles(otherData.getFiles());
            }

            String otherDeploymentPlan = otherData.getDeploymentPlan();
            if (otherDeploymentPlan != null) {
               mergedData.setDeploymentPlan(otherDeploymentPlan);
            }

            String theSource = theTask.getSource();
            String theTaskId = "_DELEGATE_" + nextDelegateId++;
            AuthenticatedSubject theSubject = this.authSubject;
            mergedOp.createRuntimeObjects(theSource, theTaskId, (AppDeploymentMBean)theTask.getDeploymentMBean(), mergedData, this.getCreateTaskType(), this.editAccessHelper.getEditDomainBean(theSubject), theSubject, true);
            List delegators = new ArrayList();
            delegators.add(this);
            delegators.add(otherOp);
            mergedOp.setAsDelegateTo(delegators);
            if (isDebugEnabled()) {
               this.debugSay(" Operations '" + this + "' and '" + otherOp + "' are merged to '" + mergedOp + "'");
            }
         }

      }
   }

   protected final void createCopyAndConstructNewRuntime() throws ManagementException {
      AbstractOperation copy = this.createCopy();
      DeploymentTaskRuntime currentTask = this.getTaskRuntime();
      DeploymentData currentData = currentTask.getDeploymentData();
      String theSource = currentTask.getSource();
      String theTaskId = "_DELEGATE_" + nextDelegateId++;
      AuthenticatedSubject theSubject = this.authSubject;
      copy.createRuntimeObjects(theSource, theTaskId, (AppDeploymentMBean)currentTask.getDeploymentMBean(), currentData, this.getCreateTaskType(), this.editAccessHelper.getEditDomainBean(theSubject), theSubject, true);
      List delegators = new ArrayList();
      delegators.add(this);
      copy.setAsDelegateTo(delegators);
   }

   protected void setAsDelegateTo(List operations) throws DeploymentException {
      if (operations != null && !operations.isEmpty()) {
         List tasksToBeRemoved = new ArrayList();
         DeploymentTaskRuntime currentRuntime = this.getTaskRuntime();
         Iterator var4 = operations.iterator();

         while(var4.hasNext()) {
            Object operation = var4.next();
            AbstractOperation theOp = (AbstractOperation)operation;
            DeploymentTaskRuntime theTask = theOp.getTaskRuntime();
            tasksToBeRemoved.add(theTask.getId());
            currentRuntime.addDelegator(theTask);
         }

         this.deploymentManager.removeDeploymentsForTasks(tasksToBeRemoved);
      }
   }

   protected abstract AbstractOperation createCopy();

   protected final void mergeUndeployWithDistributeOrDeployOrRedeploy(AbstractOperation otherOp) throws ManagementException {
      if (!(this instanceof RemoveOperation)) {
         throw new DeploymentException("current operation must be undeploy");
      } else if (!(otherOp instanceof DistributeOperation) && !(otherOp instanceof RedeployOperation) && !(otherOp instanceof ActivateOperation)) {
         throw new DeploymentException("other operation must be one of distribute or redeploy or deploy operations");
      } else {
         if (this.areTargetsSame(otherOp)) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Targets are same. So, cancelling operations");
            }

            this.removeSelf();
            otherOp.removeSelf();
         } else {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Targets are not same. So, making redeploy operation and keeing undeploy operation with some targets");
            }

            boolean haveCommonTargets = this.haveCommonTargets(otherOp);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("operations '" + this + "' and '" + otherOp + "' have common targets: " + haveCommonTargets);
            }

            if (haveCommonTargets) {
               DeploymentTaskRuntime theTask = this.getTaskRuntime();
               DeploymentData theData = theTask.getDeploymentData();
               DeploymentTaskRuntime otherTask = otherOp.getTaskRuntime();
               DeploymentData otherData = otherTask.getDeploymentData();
               theData.removeCommonTargets(otherData, true);
               if (theData.hasNoTargets()) {
                  otherOp.createCopyAndConstructNewRuntime();
                  this.removeSelf();
               } else if (otherData.hasNoTargets()) {
                  this.createCopyAndConstructNewRuntime();
                  otherOp.removeSelf();
               } else {
                  this.createCopyAndConstructNewRuntime();
                  otherOp.createCopyAndConstructNewRuntime();
               }
            }
         }

      }
   }

   protected void mergeWithUndeploy(AbstractOperation otherOp) throws ManagementException {
      if (isDebugEnabled()) {
         this.debugSay("Invoking mergeWithUndeploy(" + otherOp + ")...");
      }

      if (!(otherOp instanceof RemoveOperation)) {
         throw new DeploymentException("Other operation '" + otherOp + "' is not RemoveOperation");
      }
   }

   protected void mergeWithRedeploy(AbstractOperation otherOp) throws ManagementException {
      if (isDebugEnabled()) {
         this.debugSay("Invoking mergeWithRedeploy(" + otherOp + ")...");
      }

      if (!(otherOp instanceof RedeployOperation)) {
         throw new DeploymentException("Other operation '" + otherOp + "' is not RedeployOperation");
      }
   }

   protected void mergeWithDeploy(AbstractOperation otherOp) throws ManagementException {
      if (isDebugEnabled()) {
         this.debugSay("Invoking mergeWithDeploy(" + otherOp + ")...");
      }

      if (!(otherOp instanceof ActivateOperation)) {
         throw new DeploymentException("Other operation '" + otherOp + "' is not ActivateOperation");
      }
   }

   protected void mergeWithUpdate(AbstractOperation otherOp) throws ManagementException {
      if (isDebugEnabled()) {
         this.debugSay("Invoking mergeWithUpdate(" + otherOp + ")...");
      }

      if (!(otherOp instanceof UpdateOperation)) {
         throw new DeploymentException("Other operation '" + otherOp + "' is not UpdateOperation");
      }
   }

   protected void mergeWithDistribute(AbstractOperation otherOp) throws ManagementException {
      if (isDebugEnabled()) {
         this.debugSay("Invoking mergeWithDistribute(" + otherOp + ")...");
      }

      if (!(otherOp instanceof DistributeOperation)) {
         throw new DeploymentException("Other operation '" + otherOp + "' is not DistributeOperation");
      }
   }

   protected TaskRuntimeValidator getTaskRuntimeValidator() {
      return null;
   }

   private boolean haveCommonTargets(AbstractOperation otherOp) {
      DeploymentTaskRuntime curRuntime = this.getTaskRuntime();
      DeploymentData curData = curRuntime.getDeploymentData();
      DeploymentTaskRuntime otherRuntime = otherOp.getTaskRuntime();
      DeploymentData otherData = otherRuntime.getDeploymentData();
      return haveCommonTargets(curData, otherData);
   }

   private static boolean haveCommonTargets(DeploymentData data1, DeploymentData data2) {
      if (haveCommonGlobalTargets(data1, data2)) {
         return true;
      } else if (haveCommonModuleTargets(data1, data2)) {
         return true;
      } else {
         return haveCommonSubModuleTargets(data1, data2);
      }
   }

   private static boolean haveCommonGlobalTargets(DeploymentData data1, DeploymentData data2) {
      String[] data1GTs = data1.getGlobalTargets();
      if (data1GTs != null && data1GTs.length != 0) {
         ExtendedArrayList data1List = new ExtendedArrayList(data1GTs);
         String[] data2GTs = data2.getGlobalTargets();
         return data2GTs != null && data2GTs.length != 0 ? data1List.containsOne(data2GTs) : false;
      } else {
         return false;
      }
   }

   private static boolean haveCommonModuleTargets(DeploymentData data1, DeploymentData data2) {
      Map moduleTargets = data1.getAllModuleTargets();
      Map otherMTs = data2.getAllModuleTargets();
      if (!otherMTs.isEmpty() && !moduleTargets.isEmpty()) {
         Set otherModules = otherMTs.keySet();
         Iterator var5 = otherModules.iterator();

         while(var5.hasNext()) {
            Object otherModule1 = var5.next();
            String otherModule = (String)otherModule1;
            if (moduleTargets.containsKey(otherModule)) {
               String[] otherTargets = (String[])((String[])otherMTs.get(otherModule));
               ExtendedArrayList currentTargets = new ExtendedArrayList((String[])((String[])moduleTargets.get(otherModule)));
               if (currentTargets.containsOne(otherTargets)) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean haveCommonSubModuleTargets(DeploymentData data1, DeploymentData data2) {
      Map allSubModuleTargets = data1.getAllSubModuleTargets();
      Map otherSMTs = data2.getAllSubModuleTargets();
      if (!otherSMTs.isEmpty() && !allSubModuleTargets.isEmpty()) {
         Set otherModules = otherSMTs.keySet();
         Iterator var5 = otherModules.iterator();

         while(true) {
            Map otherSubs;
            Map currentSubs;
            do {
               do {
                  String otherModule;
                  do {
                     do {
                        do {
                           if (!var5.hasNext()) {
                              return false;
                           }

                           Object otherModule1 = var5.next();
                           otherModule = (String)otherModule1;
                        } while(!allSubModuleTargets.containsKey(otherModule));

                        otherSubs = (Map)otherSMTs.get(otherModule);
                     } while(otherSubs == null);
                  } while(otherSubs.isEmpty());

                  currentSubs = (Map)allSubModuleTargets.get(otherModule);
               } while(currentSubs == null);
            } while(currentSubs.isEmpty());

            Iterator var10 = otherSubs.keySet().iterator();

            while(var10.hasNext()) {
               Object o = var10.next();
               String otherSubModule = (String)o;
               if (currentSubs.containsKey(otherSubModule)) {
                  String[] otherTargets = (String[])((String[])otherSubs.get(otherSubModule));
                  ExtendedArrayList currentTargets = new ExtendedArrayList((String[])((String[])currentSubs.get(otherSubModule)));
                  if (currentTargets.containsOne(otherTargets)) {
                     return true;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   private void mergeOperationsOnAppAgain(String appId) throws ManagementException {
      List allOps = this.deploymentManager.getExistingOperationsOnApp(appId);
      if (isDebugEnabled()) {
         Debug.deploymentDebug(" <merge2> Found Same Operations : " + allOps);
      }

      if (allOps != null && allOps.size() >= 2) {
         if (allOps.size() > 2) {
            throw new ManagementException("Contains more than two operations after merge");
         } else {
            AbstractOperation operation1 = (AbstractOperation)allOps.get(0);
            AbstractOperation operation2 = (AbstractOperation)allOps.get(1);
            if (operation1.haveCommonTargets(operation2)) {
               operation1.mergeWithOperation(operation2);
            }

         }
      }
   }

   private void mergeWithExistingOperationsOnSameApp(EditAccessHelper editAccessHelper) throws ManagementException {
      List allOps = this.deploymentManager.getExistingOperationsOnSameApp(this);
      allOps.remove(this);
      if (isDebugEnabled()) {
         this.debugSay(" Found Same Operations : " + allOps);
      }

      if (!allOps.isEmpty()) {
         AbstractOperation otherOp = (AbstractOperation)allOps.get(0);
         DeploymentTaskRuntime curTask = this.getTaskRuntime();
         AppDeploymentMBean curMBean = curTask.getAppDeploymentMBean();
         String appId = curMBean == null ? null : curMBean.getName();
         this.mergeWithOperation(otherOp);
         if (appId != null) {
            this.mergeOperationsOnAppAgain(appId);
         }

      }
   }

   private void mergeWithOperation(AbstractOperation otherOp) throws ManagementException {
      if (this != otherOp) {
         if (otherOp instanceof ActivateOperation) {
            this.mergeWithDeploy(otherOp);
         } else if (otherOp instanceof DistributeOperation) {
            this.mergeWithDistribute(otherOp);
         } else if (otherOp instanceof UpdateOperation) {
            this.mergeWithUpdate(otherOp);
         } else if (otherOp instanceof RedeployOperation) {
            this.mergeWithRedeploy(otherOp);
         } else if (otherOp instanceof RemoveOperation) {
            this.mergeWithUndeploy(otherOp);
         }

      }
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
      if (v != null) {
         this.options.setPlanVersion(v);
      } else if (this.options.getPlanVersion() == null && this.planBean != null) {
         this.options.setPlanVersion(this.planBean.getVersion());
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

         if (!v1.equals(v2)) {
            throw new IllegalArgumentException(SPIDeployerLogger.versionMismatchPlan(v1, v2));
         }
      }

   }

   public void undoChangesTriggeredByUser() {
      if (this.deployment != null) {
         this.deploymentManager.undoChangesTriggeredByUser(this.deployment);
      }
   }

   protected static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   protected void debugSay(String msg) {
      StringBuilder sb = new StringBuilder();
      sb.append("(").append(this).append("): ").append(msg);
      Debug.deploymentDebug(sb.toString());
   }

   protected void setupDDPaths(DeploymentData info) {
   }

   private boolean handleTemplateTask(DeploymentData info, DeploymentRequestTaskRuntimeMBeanImpl parentTask) throws ManagementException {
      if (info.getDeploymentOptions().getResourceGroupTemplate() != null && parentTask != null && parentTask.getSubTasks() != null && parentTask.getSubTasks().length > 1) {
         if (this.templateDeploymentTask == null) {
            try {
               DeploymentTaskRuntime deplTask = (DeploymentTaskRuntime)this.deploymentTask;
               this.templateDeploymentTask = new DeploymentTaskRuntime(deplTask.getSource(), deplTask.getDeploymentMBean(), deplTask.getDeploymentData(), "weblogic.deploy.templateTask." + getTaskId(), deplTask.getTask(), deplTask.getDomain(), deplTask.isAControlOperation(), true);
               ((DeploymentTaskRuntime)this.templateDeploymentTask).setDescription();
            } catch (Exception var11) {
               throw new ManagementException(var11);
            }
         }

         TaskRuntimeMBean[] tasks = parentTask.getSubTasks();
         ((DeploymentTaskRuntime)this.templateDeploymentTask).setSubTasks(tasks);
         this.deploymentManager.getDeployerRuntime().removeTask(this.deploymentTask.getId());
         this.deploymentTask = this.templateDeploymentTask;

         try {
            this.deploymentManager.getDeployerRuntime().registerTaskRuntime(this.deploymentTask.getId(), (DeploymentTaskRuntime)this.deploymentTask, this.getTaskRuntimeValidator());
            if (tasks != null && tasks.length > 0) {
               TaskRuntimeMBean[] var4 = tasks;
               int var5 = tasks.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  TaskRuntimeMBean task = var4[var6];
                  DeploymentTaskRuntime newTask = (DeploymentTaskRuntime)task;
                  if (!this.deploymentTask.getId().equals(newTask.getId())) {
                     try {
                        this.deploymentManager.getDeployerRuntime().registerTaskRuntime(newTask.getId(), newTask, this.getTaskRuntimeValidator());
                     } catch (ManagementException var10) {
                        newTask.setState(3);
                        throw var10;
                     }
                  }
               }
            }
         } catch (ManagementException var12) {
            this.deploymentTask.setState(3);
            throw var12;
         }

         if (!parentTask.getDeploymentRequest().isEnqueued()) {
            parentTask.start();
         }

         return true;
      } else {
         return false;
      }
   }

   protected void switchDeploymentTaskIfNeeded(DeploymentData info) throws ManagementException {
      if (ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(info)) {
         DeploymentRequestTaskRuntimeMBeanImpl parentTask = (DeploymentRequestTaskRuntimeMBeanImpl)((DeploymentTaskRuntime)this.deploymentTask).getMyParent();
         if (parentTask != null) {
            TaskRuntimeMBean[] tasks = parentTask.getSubTasks();
            if (tasks != null && tasks.length > 0) {
               TaskRuntimeMBean[] var4 = tasks;
               int var5 = tasks.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  TaskRuntimeMBean task = var4[var6];
                  DeploymentTaskRuntime newTask = (DeploymentTaskRuntime)task;
                  DeployerRuntimeImpl deployerRuntime = this.deploymentManager.getDeployerRuntime();
                  if (!this.deploymentTask.getId().equals(newTask.getId())) {
                     newTask.setConfigChange(((DeploymentTaskRuntime)this.deploymentTask).isConfigChange());
                     newTask.setTask(((DeploymentTaskRuntime)this.deploymentTask).getTask());
                     newTask.setDescription();
                     deployerRuntime = this.deploymentManager.getDeployerRuntime();
                     deployerRuntime.removeTask(this.deploymentTask.getId());
                     this.deploymentTask.setState(2);
                     this.deploymentTask = newTask;

                     try {
                        deployerRuntime.registerTaskRuntime(this.deploymentTask.getId(), (DeploymentTaskRuntime)this.deploymentTask, this.getTaskRuntimeValidator());
                     } catch (ManagementException var11) {
                        this.deploymentTask.setState(3);
                        throw var11;
                     }
                  }

                  if (deployerRuntime.getTaskRuntime(newTask.getId()) == null) {
                     this.deploymentManager.getDeployerRuntime().registerTaskRuntime(newTask.getId(), newTask, this.getTaskRuntimeValidator());
                     newTask.start();
                  }
               }
            }
         }
      }

   }

   protected void setTargetsForPartitionDeployment(DeploymentData data) {
      String[] gTargets = new String[]{""};
      data.setGlobalTargets(gTargets);
   }

   protected AppDeploymentMBean lookupAppDeployment(DomainMBean domain, String appName, String partitionName, String resourceGroupName) throws ManagementException {
      PartitionMBean partition = domain.lookupPartition(partitionName);
      ResourceGroupMBean resourceGroup = partition.lookupResourceGroup(resourceGroupName);
      return resourceGroup.lookupAppDeployment(appName);
   }

   protected AppDeploymentMBean lookupAppDeployment(DomainMBean domain, String appName, String resourceGroupName) throws ManagementException {
      ResourceGroupMBean resourceGroup = domain.lookupResourceGroup(resourceGroupName);
      return resourceGroup.lookupAppDeployment(appName);
   }

   protected AppDeploymentMBean removePlan(String appName, String versionId, DomainMBean domain, AppDeploymentMBean deployment, AppDeploymentMBean existingDeployment, DeploymentData info) throws ManagementException, ApplicationException, FileNotFoundException {
      if (isDebugEnabled()) {
         this.debugSay("existing plan:  " + existingDeployment.getPlanPath());
      }

      String templatePlan = AppDeploymentHelper.lookupAppOrLibInReferencedResourceGroupTemplate(domain, deployment, appName, true).getPlanPath();
      if (isDebugEnabled()) {
         this.debugSay("template plan:  " + templatePlan);
      }

      if (templatePlan != null && templatePlan.equals(existingDeployment.getPlanPath())) {
         Loggable l = DeployerRuntimeExtendedLogger.logPlanAlreadyRevertedLoggable();
         l.log();
         throw new ManagementException(l.getMessage());
      } else {
         deployment = ApplicationVersionUtils.getAppDeployment(domain, appName, versionId, info.getResourceGroupTemplate(), info.getResourceGroup(), info.getPartition());
         if (isDebugEnabled()) {
            this.debugSay("removePlan returning:  " + deployment);
         }

         this.beanFactory.removeMBean(deployment);
         invalidateCache(deployment);
         return deployment;
      }
   }

   protected AppDeploymentMBean addPlan(String appName, String versionId, DomainMBean domain, AppDeploymentMBean deployment, AppDeploymentMBean existingDeployment, DeploymentData info) throws ManagementException, ApplicationException, FileNotFoundException {
      boolean usePartition = info.getPartition() != null;
      String appId = null;
      if (isDebugEnabled()) {
         this.debugSay("is transient:  " + ((AbstractDescriptorBean)existingDeployment)._isTransient());
      }

      if (((AbstractDescriptorBean)existingDeployment)._isTransient()) {
         deployment = this.createMBeans(existingDeployment.getSourcePath(), appName, info, versionId);
         if (isDebugEnabled()) {
            this.debugSay("createMBeans returned:  " + deployment);
         }

         if (usePartition) {
            existingDeployment = this.lookupAppDeployment(domain, appName, info.getPartition(), info.getResourceGroup());
            appId = ApplicationVersionUtils.getApplicationId(appName, versionId, info.getPartition());
         } else {
            existingDeployment = this.lookupAppDeployment(domain, appName, info.getResourceGroup());
            appId = ApplicationVersionUtils.getApplicationId(appName, versionId);
         }

         if (isDebugEnabled()) {
            this.debugSay("looked up again deployment:  " + existingDeployment);
            this.debugSay("appId:  " + appId);
         }

         deployment = existingDeployment;
      } else {
         if (usePartition) {
            appId = ApplicationVersionUtils.getApplicationId(appName, versionId, info.getPartition());
         } else {
            appId = ApplicationVersionUtils.getApplicationId(appName, versionId);
         }

         if (isDebugEnabled()) {
            this.debugSay("appId:  " + appId);
         }

         AppDeploymentMBean clonedDeployment = domain.lookupAppDeployment(appId);
         if (isDebugEnabled()) {
            this.debugSay("cloned deployment:  " + clonedDeployment);
         }

         deployment = existingDeployment;
      }

      this.reconcileMBeans(info, deployment);
      deployment = ApplicationVersionUtils.getAppDeployment(domain, appName, versionId, info.getResourceGroupTemplate(), info.getResourceGroup(), info.getPartition());
      if (isDebugEnabled()) {
         this.debugSay("addPlan returning:  " + deployment);
      }

      return deployment;
   }

   protected AppDeploymentMBean handlePartitionResourceGroup(DomainMBean domain, DeploymentData info, String appName, String versionId, String taskName, AppDeploymentMBean depl) throws ManagementException {
      Loggable l;
      try {
         AppDeploymentMBean existingDeployment = this.lookupAppDeployment(domain, appName, info.getPartition(), info.getResourceGroup());
         if (isDebugEnabled()) {
            this.debugSay("looked up deployment:  " + existingDeployment);
         }

         OperationHelper.assertAppIsNonNull(existingDeployment, appName, versionId, taskName);
         return info.getDeploymentOptions().isRemovePlanOverride() ? this.removePlan(appName, versionId, domain, depl, existingDeployment, info) : this.addPlan(appName, versionId, domain, depl, existingDeployment, info);
      } catch (ApplicationException var9) {
         l = DeployerRuntimeLogger.logInvalidAppLoggable(depl.getAbsoluteSourcePath(), appName, var9.getMessage());
         l.log();
         throw new ManagementException(l.getMessage(), var9);
      } catch (FileNotFoundException var10) {
         l = DeployerRuntimeLogger.logInvalidSourceLoggable(depl.getAbsoluteSourcePath(), appName, var10.getMessage());
         l.log();
         throw new ManagementException(l.getMessage(), var10);
      }
   }

   protected AppDeploymentMBean handleResourceGroup(DomainMBean domain, DeploymentData info, String appName, String versionId, String taskName, AppDeploymentMBean depl) throws ManagementException {
      Loggable l;
      try {
         AppDeploymentMBean existingDeployment = this.lookupAppDeployment(domain, appName, info.getResourceGroup());
         if (isDebugEnabled()) {
            this.debugSay("looked up deployment:  " + existingDeployment);
         }

         OperationHelper.assertAppIsNonNull(existingDeployment, appName, versionId, taskName);
         return info.getDeploymentOptions().isRemovePlanOverride() ? this.removePlan(appName, versionId, domain, depl, existingDeployment, info) : this.addPlan(appName, versionId, domain, depl, existingDeployment, info);
      } catch (ApplicationException var9) {
         l = DeployerRuntimeLogger.logInvalidAppLoggable(depl.getAbsoluteSourcePath(), appName, var9.getMessage());
         l.log();
         throw new ManagementException(l.getMessage(), var9);
      } catch (FileNotFoundException var10) {
         l = DeployerRuntimeLogger.logInvalidSourceLoggable(depl.getAbsoluteSourcePath(), appName, var10.getMessage());
         l.log();
         throw new ManagementException(l.getMessage(), var10);
      }
   }

   protected static EditAccessHelper getEditAccessHelperForDeployment(AuthenticatedSubject authenticatedSubject, DeploymentOptions depOptions) {
      String editSessionName = depOptions != null ? depOptions.getEditSessionName() : null;
      return EditAccessHelper.getInstance(authenticatedSubject, editSessionName);
   }

   private static weblogic.deploy.internal.adminserver.DeploymentManager getDeploymentManagerFromDeployment(AuthenticatedSubject authenticatedSubject, DeploymentOptions depOptions, EditAccessHelper eaHelper) {
      String editSessionName = eaHelper.getEditSessionName();
      String partitionName = eaHelper.getPartitionName();
      return editSessionName == null && partitionName == null ? weblogic.deploy.internal.adminserver.DeploymentManager.getInstance(authenticatedSubject) : weblogic.deploy.internal.adminserver.DeploymentManager.getInstance(authenticatedSubject, partitionName, editSessionName);
   }

   private static boolean deployPartitionMatchesCIC(String deployPartition) {
      String actualPartition = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      return actualPartition.equals(deployPartition);
   }

   public EditAccessHelper getEditAccessHelper() {
      return this.editAccessHelper;
   }

   private static class ExtendedArrayList extends ArrayList {
      ExtendedArrayList(Collection c) {
         super(c);
      }

      ExtendedArrayList(String[] given) {
         this((Collection)(given != null ? Arrays.asList(given) : Collections.EMPTY_LIST));
      }

      boolean containsOne(String[] objs) {
         if (this.isEmpty()) {
            return false;
         } else if (objs != null && objs.length != 0) {
            String[] var2 = objs;
            int var3 = objs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               String obj = var2[var4];
               if (this.contains(obj)) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }
   }
}
