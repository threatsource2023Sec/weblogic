package weblogic.management.deploy.internal;

import java.io.File;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.management.InstanceNotFoundException;
import javax.management.ObjectName;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.adminserver.EditAccessHelper;
import weblogic.deploy.internal.adminserver.operations.AbstractOperation;
import weblogic.deploy.internal.adminserver.operations.ActivateOperation;
import weblogic.deploy.internal.adminserver.operations.DeactivateOperation;
import weblogic.deploy.internal.adminserver.operations.DeployOperation;
import weblogic.deploy.internal.adminserver.operations.DistributeOperation;
import weblogic.deploy.internal.adminserver.operations.ExtendLoaderOperation;
import weblogic.deploy.internal.adminserver.operations.RedeployOperation;
import weblogic.deploy.internal.adminserver.operations.RemoveOperation;
import weblogic.deploy.internal.adminserver.operations.RetireOperation;
import weblogic.deploy.internal.adminserver.operations.StartOperation;
import weblogic.deploy.internal.adminserver.operations.StopForGracefulRetireOperation;
import weblogic.deploy.internal.adminserver.operations.StopOperation;
import weblogic.deploy.internal.adminserver.operations.UnprepareOperation;
import weblogic.deploy.internal.adminserver.operations.UpdateOperation;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.TargetAvailabilityStatus;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.WebDeploymentMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.deploy.status.StatusFactory;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RegistrationHandler;
import weblogic.management.provider.Service;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.AssertionError;
import weblogic.utils.cmm.MemoryPressureListener;
import weblogic.utils.cmm.MemoryPressureService;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class DeployerRuntimeImpl extends DomainRuntimeMBeanDelegate implements DeployerRuntimeMBean, MemoryPressureListener {
   private static final int DEFAULT_MAX_RETIRED_TASKS = 20;
   private static final int MAX_VALIDATION_RETRY_LIMIT = 100;
   private static final int RETIRE_TIME = Integer.parseInt(System.getProperty("weblogic.management.deploy.taskRetireTime", "300"));
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final LinkedHashMap taskMap = new LinkedHashMap();
   private Timer tasksRetirePoller = null;
   private int cmmLevel = 0;
   private String partitionName = null;
   private static AtomicInteger nextId = new AtomicInteger();

   public DeployerRuntimeImpl() throws ManagementException {
      throw new AssertionError("Constructor not valid on singleton MBean");
   }

   DeployerRuntimeImpl(String name) throws ManagementException {
      super(name);
      DomainAccess runtime = ManagementService.getDomainAccess(kernelId);
      runtime.addRegistrationHandler(new RegistrationHandler() {
         public void registered(RuntimeMBean runtime, DescriptorBean config) {
         }

         public void unregistered(RuntimeMBean runtime) {
            if (runtime instanceof DeploymentTaskRuntimeMBean) {
               String id = ((DeploymentTaskRuntimeMBean)runtime).getId();
               synchronized(DeployerRuntimeImpl.taskMap) {
                  DeployerRuntimeImpl.taskMap.remove(id);
               }
            }

         }

         public void registeredCustom(ObjectName oname, Object custom) {
         }

         public void unregisteredCustom(ObjectName custom) {
         }

         public void registered(Service bean) {
         }

         public void unregistered(Service bean) {
         }
      });
      this.tasksRetirePoller = this.startTasksRetirePoller();
      MemoryPressureService memoryPressureService = (MemoryPressureService)GlobalServiceLocator.getServiceLocator().getService(MemoryPressureService.class, new Annotation[0]);
      memoryPressureService.registerListener("DeployerRuntime", this);
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Created deployer: " + this);
      }

   }

   DeployerRuntimeImpl(RuntimeMBean parent, String partitionName) throws ManagementException {
      super(parent.getName(), parent);
      this.partitionName = partitionName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   private Timer startTasksRetirePoller() {
      long executionPeriod = 30000L;
      Timer timer = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.management.TasksRetirePoller", WorkManagerFactory.getInstance().getSystem()).schedule(new TasksRetirePoller(), 0L, executionPeriod);
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Started TasksRetirePoller to execute  repeatedly for every 30 seconds");
      }

      return timer;
   }

   private void checkAndPurgeRetiredTasks() {
      try {
         this.checkAndMarkRetiredTasks();
      } catch (Throwable var3) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("TaskRetirePoller failed to mark tasks as retired due to an exception : ", var3);
         }
      }

      try {
         this.purgeOldRetiredTasks();
      } catch (Throwable var2) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("TaskRetirePoller failed to remove old retired tasks as retired due to an exception : ", var2);
         }
      }

   }

   private void checkAndMarkRetiredTasks() {
      ArrayList values;
      synchronized(taskMap) {
         values = new ArrayList(taskMap.values());
      }

      Iterator var2 = values.iterator();

      while(var2.hasNext()) {
         DeploymentTaskRuntime dtrm = (DeploymentTaskRuntime)var2.next();
         if (!dtrm.isRetired() && !dtrm.isRunning()) {
            long taskEndTime = dtrm.getEndTime();
            long calcEndRetiredTime = taskEndTime + (long)(RETIRE_TIME * 1000);
            long currentTime = System.currentTimeMillis();
            if (currentTime >= calcEndRetiredTime) {
               dtrm.setRetired();
            }
         }
      }

   }

   public DeploymentTaskRuntimeMBean activate(String source, String name, String stagingMode, DeploymentData info, String id) throws ManagementException {
      return this.activate(source, name, stagingMode, info, id, true);
   }

   public DeploymentTaskRuntimeMBean activate(String source, String name, String stagingMode, DeploymentData info, String id, boolean startIt) throws ManagementException {
      this.checkDeployerActions(id, name, 1, info);
      return this.performDeployerActions(source, name, stagingMode, info, id, startIt, new ActivateOperation());
   }

   public DeploymentTaskRuntimeMBean deactivate(String name, DeploymentData info, String id) throws ManagementException {
      return this.deactivate(name, info, id, true);
   }

   public DeploymentTaskRuntimeMBean deactivate(String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      this.checkDeployerActions(id, name, 3, info);
      return this.performDeployerActions((String)null, name, (String)null, info, id, startTask, new DeactivateOperation());
   }

   public DeploymentTaskRuntimeMBean remove(String name, DeploymentData info, String id) throws ManagementException {
      return this.remove(name, info, id, true);
   }

   public DeploymentTaskRuntimeMBean remove(String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      int taskType = 4;
      this.checkDeployerActions(id, name, taskType, info);
      return this.performDeployerActions((String)null, name, (String)null, info, id, startTask, new RemoveOperation(taskType));
   }

   public DeploymentTaskRuntimeMBean unprepare(String name, DeploymentData info, String id) throws ManagementException {
      return this.unprepare(name, info, id, true);
   }

   public DeploymentTaskRuntimeMBean unprepare(String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      this.checkDeployerActions(id, name, 5, info);
      return this.performDeployerActions((String)null, name, (String)null, info, id, startTask, new UnprepareOperation());
   }

   public DeploymentTaskRuntimeMBean distribute(String source, String name, DeploymentData info, String id) throws ManagementException {
      return this.distribute(source, name, info, id, true);
   }

   public DeploymentTaskRuntimeMBean distribute(String source, String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      name = this.ensureAppName(name);
      this.assertNameIsNonNull(name, weblogic.deploy.internal.targetserver.DeployHelper.getTaskName(6));
      this.checkDeployerActions(id, name, 6, info);
      String stagingMode = weblogic.deploy.internal.targetserver.DeployHelper.getStagingModeFromOptions(info);
      if (this.callConfirmApplicationName(name, source, info)) {
         name = confirmApplicationName(false, source, name, info);
      }

      return this.performDeployerActions(source, name, stagingMode, info, id, startTask, new DistributeOperation());
   }

   public DeploymentTaskRuntimeMBean appendToExtensionLoader(String source, DeploymentData info, String id, boolean startTask) throws ManagementException {
      String stagingMode = weblogic.deploy.internal.targetserver.DeployHelper.getStagingModeFromOptions(info);
      String name = "extend-loader-" + System.currentTimeMillis();
      return this.performDeployerActions(source, name, stagingMode, info, id, startTask, new ExtendLoaderOperation());
   }

   public DeploymentTaskRuntimeMBean deploy(String source, String name, String stagingMode, DeploymentData info, String id) throws ManagementException {
      return this.deploy(source, name, stagingMode, info, id, true);
   }

   public DeploymentTaskRuntimeMBean deploy(String source, String name, String stagingMode, DeploymentData info, String id, boolean startIt) throws ManagementException {
      this.checkDeployerActions(id, name, 11, info);
      if (this.callConfirmApplicationName(name, source, info)) {
         name = confirmApplicationName(false, source, name, info);
      }

      return this.performDeployerActions(source, name, stagingMode, info, id, startIt, new DeployOperation());
   }

   public DeploymentTaskRuntimeMBean undeploy(String name, DeploymentData info, String id) throws ManagementException {
      return this.undeploy(name, info, id, true);
   }

   public DeploymentTaskRuntimeMBean undeploy(String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      int taskType = 12;
      this.checkDeployerActions(id, name, taskType, info);
      return this.performDeployerActions((String)null, name, (String)null, info, id, startTask, new RemoveOperation(taskType));
   }

   public DeploymentTaskRuntimeMBean start(String name, DeploymentData info, String id) throws ManagementException {
      return this.start(name, info, id, true);
   }

   public DeploymentTaskRuntimeMBean start(String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      String versId = ApplicationVersionUtils.getVersionId(name);
      String appVersionId = info.getDeploymentOptions().getArchiveVersion();
      if (versId != null && versId.length() > 0 && (appVersionId == null || appVersionId.length() == 0)) {
         info.getDeploymentOptions().setArchiveVersion(versId);
      }

      name = this.ensureAppName(name);
      info.getDeploymentOptions().setName(name);
      this.assertNameIsNonNull(name, weblogic.deploy.internal.targetserver.DeployHelper.getTaskName(7));
      this.checkDeployerActions(id, name, 7, info);
      return this.performDeployerActions((String)null, name, (String)null, info, id, startTask, new StartOperation());
   }

   public DeploymentTaskRuntimeMBean stop(String name, DeploymentData info, String id) throws ManagementException {
      return this.stop(name, info, id, true);
   }

   public DeploymentTaskRuntimeMBean stop(String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      this.checkDeployerActions(id, name, 8, info);
      return this.performDeployerActions((String)null, name, (String)null, info, id, startTask, new StopOperation());
   }

   public DeploymentTaskRuntimeMBean redeploy(String name, DeploymentData info, String id) throws ManagementException {
      return this.redeploy(name, info, id, true);
   }

   public DeploymentTaskRuntimeMBean redeploy(String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      this.checkDeployerActions(id, name, 9, info);
      return this.performDeployerActions((String)null, name, info.getDeploymentOptions().getStageMode(), info, id, startTask, new RedeployOperation());
   }

   public DeploymentTaskRuntimeMBean redeploy(String source, String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      this.checkDeployerActions(id, name, 9, info);
      name = confirmApplicationName(true, source, name, info);
      return this.performDeployerActions(source, name, info.getDeploymentOptions().getStageMode(), info, id, startTask, new DeployOperation(true));
   }

   public DeploymentTaskRuntimeMBean update(String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      this.checkDeployerActions(id, name, 10, info);
      return this.performDeployerActions((String)null, name, (String)null, info, id, startTask, new UpdateOperation());
   }

   public DeploymentTaskRuntimeMBean stopForGracefulRetire(String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      this.checkDeployerActions(id, name, 8, info);
      return this.performDeployerActions((String)null, name, (String)null, info, id, startTask, new StopForGracefulRetireOperation());
   }

   public DeploymentTaskRuntimeMBean retire(String name, DeploymentData info, String id, boolean startTask) throws ManagementException {
      this.checkDeployerActions(id, name, 13, info);
      return this.performDeployerActions((String)null, name, (String)null, info, id, startTask, new RetireOperation());
   }

   public DeploymentTaskRuntimeMBean[] list() {
      synchronized(taskMap) {
         return (DeploymentTaskRuntimeMBean[])((DeploymentTaskRuntimeMBean[])taskMap.values().toArray(new DeploymentTaskRuntimeMBean[taskMap.size()]));
      }
   }

   public DeploymentTaskRuntimeMBean[] getDeploymentTaskRuntimes() {
      return this.list();
   }

   public DeploymentTaskRuntimeMBean query(String id) {
      DeploymentTaskRuntimeMBean dtrm = this.getTaskRuntime(id);
      return dtrm;
   }

   public boolean removeTask(String taskId) {
      if (taskId != null && taskId.length() != 0) {
         try {
            DeploymentTaskRuntimeMBean dtrm = this.getTaskRuntime(taskId);
            if (dtrm != null && !ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(dtrm.getDeploymentData())) {
               ((DomainRuntimeMBeanDelegate)dtrm).unregister();
               synchronized(taskMap) {
                  taskMap.remove(taskId);
               }

               return true;
            }
         } catch (Exception var8) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Remove DeploymentTaskRuntimeMBean failed for id: " + taskId + ", ex:" + var8.getMessage());
            }
         }

         synchronized(taskMap) {
            taskMap.remove(taskId);
            return false;
         }
      } else {
         return false;
      }
   }

   public synchronized String[] purgeRetiredTasks() {
      List taskIds = new ArrayList();
      List collectRetiredTasks = this.getRetiredTasks();
      Iterator taskIter = collectRetiredTasks.iterator();

      while(taskIter.hasNext()) {
         DeploymentTaskRuntimeMBean dtrm = (DeploymentTaskRuntimeMBean)taskIter.next();
         String taskId = dtrm.getId();
         this.removeTask(taskId);
         taskIds.add(taskId);
      }

      if (taskIds.size() == 0) {
         return new String[0];
      } else {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug(" +++ taskIds removed : " + taskIds);
         }

         String[] returnIds = new String[taskIds.size()];
         returnIds = (String[])((String[])taskIds.toArray(returnIds));
         return returnIds;
      }
   }

   private List getRetiredTasks() {
      LinkedList retiredTasks;
      synchronized(taskMap) {
         retiredTasks = new LinkedList(taskMap.values());
      }

      Iterator taskIter = retiredTasks.iterator();

      while(taskIter.hasNext()) {
         DeploymentTaskRuntime dtrm = (DeploymentTaskRuntime)taskIter.next();
         if (!dtrm.isRetired()) {
            taskIter.remove();
         }
      }

      return retiredTasks;
   }

   private void purgeOldRetiredTasks() {
      int maxRetiredTasks = 20;
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (domain != null) {
         DeploymentConfigurationMBean conf = domain.getDeploymentConfiguration();
         if (conf != null) {
            maxRetiredTasks = conf.getMaxRetiredTasks();
         }
      }

      if (this.cmmLevel >= 1 && this.cmmLevel <= 9) {
         Float flt = new Float((double)(10 - this.cmmLevel) * 0.1);
         maxRetiredTasks = (new Float((float)maxRetiredTasks * flt)).intValue();
      }

      List retiredTasks = this.getRetiredTasks();
      int removedRetiredTasks = retiredTasks.size() - maxRetiredTasks;
      if (removedRetiredTasks > 0) {
         Iterator var5 = retiredTasks.iterator();

         while(var5.hasNext()) {
            DeploymentTaskRuntime dt = (DeploymentTaskRuntime)var5.next();
            this.removeTask(dt.getId());
            --removedRetiredTasks;
            if (removedRetiredTasks <= 0) {
               break;
            }
         }
      }

   }

   private void assertNameIsNonNull(String appName, String operation) throws ManagementException {
      if (appName == null) {
         Loggable l = DeployerRuntimeLogger.logNullAppLoggable("null", operation);
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   private static int getNextTaskId() {
      return nextId.getAndIncrement();
   }

   public DeploymentTaskRuntime getTaskRuntime(String id) {
      synchronized(taskMap) {
         return (DeploymentTaskRuntime)taskMap.get(id);
      }
   }

   public void registerTaskRuntime(String taskId, DeploymentTaskRuntimeMBean taskRuntime) {
      synchronized(taskMap) {
         taskMap.put(taskId, (DeploymentTaskRuntime)taskRuntime);
      }
   }

   public void registerTaskRuntime(String taskId, DeploymentTaskRuntime taskRuntime, TaskRuntimeValidator validator) throws ManagementException {
      if (validator == null) {
         this.registerTaskRuntime(taskId, taskRuntime);
      } else {
         Set validatedTaskIdSet = null;
         Set currentTaskIdSet = null;
         Map currentTaskMap = null;
         boolean removeOldRetiredTasks = false;

         for(int i = 0; i < 100; ++i) {
            synchronized(taskMap) {
               if (validatedTaskIdSet != null && validatedTaskIdSet.containsAll(taskMap.keySet())) {
                  this.registerTaskRuntime(taskId, taskRuntime);
                  return;
               }

               currentTaskMap = (Map)taskMap.clone();
            }

            currentTaskIdSet = currentTaskMap.keySet();
            if (validatedTaskIdSet != null) {
               currentTaskIdSet.removeAll(validatedTaskIdSet);
            }

            Iterator var9 = currentTaskMap.values().iterator();

            while(var9.hasNext()) {
               DeploymentTaskRuntime dt = (DeploymentTaskRuntime)var9.next();
               if (dt.isRunning()) {
                  validator.validate(dt, taskRuntime);
               }
            }

            if (validatedTaskIdSet != null) {
               validatedTaskIdSet.addAll(currentTaskIdSet);
            } else {
               validatedTaskIdSet = new HashSet(currentTaskIdSet);
            }
         }

         throw new ManagementException("Many tasks are added at the same time.");
      }
   }

   private void ensureTaskIsNotAlreadyRunning(String id, int deployerAction) throws ManagementException {
      if (id != null) {
         DeploymentTaskRuntime task = this.getTaskRuntime(id);
         if (task != null) {
            Loggable l = DeployerRuntimeLogger.logTaskInUseLoggable(id, weblogic.deploy.internal.targetserver.DeployHelper.getTaskName(deployerAction), this.name);
            l.log();
            throw new ManagementException(l.getMessage());
         }
      }
   }

   private void ensureDeploymentServiceStarted(String appName, int action) throws ManagementException {
      if (!DeploymentServerService.isStarted()) {
         Loggable l = DeployerRuntimeLogger.logDeploymentServiceNotStartedLoggable(appName, weblogic.deploy.internal.targetserver.DeployHelper.getTaskName(action));
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   private void ensureNoTaskConflict(String appName, int action) throws ManagementException {
      if (7 == action) {
         DeploymentTaskRuntimeMBean[] taskMBeans = this.list();
         if (taskMBeans.length > 0) {
            for(int n = 0; n < taskMBeans.length; ++n) {
               if (taskMBeans[n].getState() == 1 && taskMBeans[n].getTask() == 8 && taskMBeans[n].getApplicationName().equals(appName)) {
                  Loggable l = DeployerRuntimeLogger.logTaskConflictLoggable(appName, weblogic.deploy.internal.targetserver.DeployHelper.getTaskName(action), weblogic.deploy.internal.targetserver.DeployHelper.getTaskName(taskMBeans[n].getTask()));
                  l.log();
                  throw new ManagementException(l.getMessage());
               }
            }
         }
      }

   }

   private void checkDeployerActions(String taskId, String appName, int deployerAction, DeploymentData info) throws ManagementException {
      this.ensureTaskIsNotAlreadyRunning(taskId, deployerAction);
      this.ensureDeploymentServiceStarted(appName, deployerAction);
      this.ensureNoTaskConflict(appName, deployerAction);
      this.validateAndSetResourceGroupOrTemplateOptions(appName, info, deployerAction);
   }

   private DeploymentTaskRuntimeMBean performDeployerActions(final String source, final String name, final String stagingMode, DeploymentData info, final String taskId, final boolean startIt, final AbstractOperation deploymentOperation) throws ManagementException {
      if (taskId == null || taskId.equals(" ")) {
         taskId = Integer.toString(getNextTaskId());
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Creating DeploymentTaskRuntime with id: " + taskId);
         }
      }

      final AuthenticatedSubject authenticatedSubject = SecurityServiceManager.getCurrentSubject(kernelId);
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Deployment operation subject: " + authenticatedSubject);
      }

      final DeploymentData infoToBePassed = info == null ? new DeploymentData() : info;
      infoToBePassed.setPartition(this.partitionName);
      Object obj = SecurityServiceManager.runAs(kernelId, authenticatedSubject, new PrivilegedAction() {
         public Object run() {
            Object toReturn;
            try {
               toReturn = deploymentOperation.execute(source, name, stagingMode, infoToBePassed, taskId, startIt, authenticatedSubject);
            } catch (ManagementException var3) {
               toReturn = var3;
            }

            return toReturn;
         }
      });
      if (obj instanceof ManagementException) {
         ((ManagementException)obj).printStackTrace();
         throw (ManagementException)obj;
      } else {
         return (DeploymentTaskRuntimeMBean)obj;
      }
   }

   public DeploymentMBean[] getDeployments(TargetMBean target) {
      return AppMBeanUsages.getDeployments(target);
   }

   private String ensureAppName(String name) {
      return ApplicationVersionUtils.getApplicationName(name);
   }

   public Map getAvailabilityStatusForApplication(String appName, boolean refreshCache) throws InstanceNotFoundException {
      HashMap appMap = new HashMap();
      ApplicationMBean appMBean = ApplicationVersionUtils.getActiveAppDeployment(appName).getAppMBean();
      ComponentMBean[] comps = appMBean.getComponents();
      if (comps != null) {
         for(int i = 0; i < comps.length; ++i) {
            Map compMap = this.getAvailabilityStatusForComponent(comps[i], false);
            appMap.put(comps[i].getName(), compMap);
         }
      }

      return appMap;
   }

   public Map getAvailabilityStatusForComponent(ComponentMBean compMBean, boolean refreshCache) throws InstanceNotFoundException {
      HashMap statusMap = new HashMap();
      ApplicationMBean appMBean = ApplicationVersionUtils.getActiveAppDeployment(compMBean.getApplication().getName()).getAppMBean();
      TargetMBean[] targets = compMBean.getTargets();
      if (targets != null) {
         for(int i = 0; i < targets.length; ++i) {
            TargetAvailabilityStatus status = this.getAvailabilityStatusForComponentTarget(appMBean, compMBean, targets[i]);
            statusMap.put(targets[i].getName(), status);
         }
      }

      if (compMBean instanceof WebDeploymentMBean) {
         VirtualHostMBean[] vh = ((WebDeploymentMBean)compMBean).getVirtualHosts();
         if (vh != null) {
            for(int j = 0; j < vh.length; ++j) {
               TargetAvailabilityStatus status = this.getAvailabilityStatusForComponentTarget(appMBean, compMBean, vh[j]);
               statusMap.put(vh[j].getName(), status);
            }
         }
      }

      ArrayList vhList = new ArrayList();
      TargetMBean[] activatedTargets = compMBean.getActivatedTargets();
      if (activatedTargets != null) {
         for(int i = 0; i < activatedTargets.length; ++i) {
            String componentTarget = activatedTargets[i].getName();
            if (!statusMap.containsKey(componentTarget) && !vhList.contains(componentTarget)) {
               ConfigurationMBean cfgMBean = null;
               DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
               ServerMBean server = domain.lookupServer(componentTarget);
               ClusterMBean cluster = null;
               VirtualHostMBean virtualHost = null;
               if (server != null) {
                  cfgMBean = server;
               } else if ((cluster = domain.lookupCluster(componentTarget)) != null) {
                  cfgMBean = cluster;
               } else if ((virtualHost = domain.lookupVirtualHost(componentTarget)) != null) {
                  cfgMBean = virtualHost;
               }

               if (cfgMBean != null) {
                  TargetAvailabilityStatus status = this.getAvailabilityStatusForComponentTarget(appMBean, compMBean, (ConfigurationMBean)cfgMBean);
                  if (status != null) {
                     statusMap.put(componentTarget, status);
                  }
               }
            }
         }
      }

      return statusMap;
   }

   private TargetAvailabilityStatus getAvailabilityStatusForComponentTarget(ApplicationMBean appMBean, ComponentMBean compMBean, ConfigurationMBean compTarget) throws InstanceNotFoundException {
      TargetAvailabilityStatus status = StatusFactory.getInstance().createStatus(appMBean, compTarget);
      boolean isStaged = appMBean.getStagingMode().equals("stage");
      String targetName = compTarget.getName();
      TargetMBean[] activatedTargets = compMBean.getActivatedTargets();
      if (compTarget instanceof VirtualHostMBean) {
         TargetMBean[] compTargetsForVH = ((VirtualHostMBean)compTarget).getTargets();

         for(int l = 0; l < compTargetsForVH.length; ++l) {
            this.updateAvailabilityStatusFromActivatedTargets(activatedTargets, compTarget, compTargetsForVH[l].getName(), status);
         }
      } else {
         this.updateAvailabilityStatusFromActivatedTargets(activatedTargets, compTarget, targetName, status);
      }

      if (isStaged) {
         String[] stagedTargets = compMBean.getApplication().getStagedTargets();
         status.updateUnavailabilityStatus(Arrays.asList(stagedTargets));
      }

      if (Debug.isDeploymentDebugEnabled()) {
         this.printStatus(status);
      }

      return status;
   }

   private void updateAvailabilityStatusFromActivatedTargets(TargetMBean[] activatedTargets, ConfigurationMBean configMBean, String targetName, TargetAvailabilityStatus status) {
      for(int i = 0; i < activatedTargets.length; ++i) {
         if (activatedTargets[i].getName().equals(targetName)) {
            Set componentTargetSet = createComponentTarget(activatedTargets[i], configMBean);
            Iterator itr = componentTargetSet.iterator();

            while(itr.hasNext()) {
               ComponentTarget componentTarget = (ComponentTarget)itr.next();
               status.updateAvailabilityStatus(componentTarget);
            }

            return;
         }
      }

   }

   private static Set createComponentTarget(TargetMBean target, ConfigurationMBean configMBean) {
      HashSet compTargetSet = new HashSet();
      ServerMBean[] clusterServers;
      int i;
      if (configMBean != null && configMBean instanceof VirtualHostMBean) {
         if (target instanceof ServerMBean) {
            compTargetSet.add(new ComponentTarget(configMBean.getName(), target.getName(), 3));
         } else if (target instanceof ClusterMBean) {
            clusterServers = ((ClusterMBean)target).getServers();

            for(i = 0; i < clusterServers.length; ++i) {
               compTargetSet.add(new ComponentTarget(configMBean.getName(), target.getName(), clusterServers[i].getName(), 3));
            }
         }
      } else if (target instanceof ServerMBean) {
         compTargetSet.add(new ComponentTarget(target.getName(), target.getName(), 1));
      } else if (target instanceof ClusterMBean) {
         clusterServers = ((ClusterMBean)target).getServers();

         for(i = 0; i < clusterServers.length; ++i) {
            compTargetSet.add(new ComponentTarget(target.getName(), clusterServers[i].getName(), 2));
         }
      }

      return compTargetSet;
   }

   private void printStatus(TargetAvailabilityStatus status) {
      Set clusters;
      Iterator itr;
      switch (status.getTargetType()) {
         case 1:
            Debug.deploymentDebug("Target Name:" + status.getTargetName() + " Target Type: Server");
            Debug.deploymentDebug("Deployment Status:" + status.getDeploymentStatus());
            Debug.deploymentDebug("Availability Status: " + status.getAvailabilityStatus());
            break;
         case 2:
            Debug.deploymentDebug("Target Name:" + status.getTargetName() + " Target Type: Cluster");
            Debug.deploymentDebug("Deployment Status:" + status.getDeploymentStatus());
            clusters = status.getServersAvailabilityStatus();
            itr = clusters.iterator();

            while(itr.hasNext()) {
               this.printStatus((TargetAvailabilityStatus)itr.next());
            }

            return;
         case 3:
            Debug.deploymentDebug("Target Name:" + status.getTargetName() + " Target Type: VirtualHost");
            Debug.deploymentDebug("Deployment Status:" + status.getDeploymentStatus());
            clusters = status.getClustersAvailabilityStatus();
            itr = clusters.iterator();

            while(itr.hasNext()) {
               this.printStatus((TargetAvailabilityStatus)itr.next());
            }

            Set servers = status.getServersAvailabilityStatus();
            Iterator itr1 = servers.iterator();

            while(itr1.hasNext()) {
               this.printStatus((TargetAvailabilityStatus)itr1.next());
            }
      }

   }

   public static String confirmApplicationName(boolean redeploy, String source, String name, DeploymentData info) throws ManagementException {
      if (info == null || info.getDeploymentOptions().isLibrary() || name != null && info.getDeploymentOptions().isSucceedIfNameUsed()) {
         return name;
      } else {
         try {
            String versionId = "";
            if (info != null) {
               versionId = info.getDeploymentOptions().getArchiveVersion();
            }

            if (versionId == null) {
               versionId = "";
            }

            DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
            AuthenticatedSubject authenticatedSubject = SecurityServiceManager.getCurrentSubject(kernelId);
            DomainMBean editDomain = EditAccessHelper.getInstance(kernelId, info.getDeploymentOptions().getEditSessionName()).getEditDomainBean(authenticatedSubject);
            String newName = ApplicationUtils.confirmApplicationName(redeploy, new File(source), info.getDeploymentOptions().getAltDDFile(), name, versionId, domain, editDomain, info.getDeploymentOptions());
            return newName;
         } catch (Exception var9) {
            throw new ManagementException(var9.getMessage());
         }
      }
   }

   private void validateAndSetResourceGroupOrTemplateOptions(String appName, DeploymentData info, int taskType) throws ManagementException {
      if (info != null && info.getDeploymentOptions() != null) {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         DeploymentOptions options = info.getDeploymentOptions();
         AuthenticatedSubject authenticatedSubject = SecurityServiceManager.getCurrentSubject(kernelId);
         String editSessionName = null;
         if (options != null) {
            editSessionName = options.getEditSessionName();
         }

         EditAccessHelper helper = EditAccessHelper.getInstance(kernelId, editSessionName);
         DomainMBean editDomain = null;
         if (helper.isCurrentEditor(authenticatedSubject)) {
            editDomain = helper.getEditDomainBean(authenticatedSubject);
         } else {
            editDomain = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService().getDomainConfiguration();
         }

         if (options.getResourceGroup() != null && (taskType == 12 || taskType == 9 || taskType == 10 || taskType == 7 || taskType == 8)) {
            Loggable l = DeployerRuntimeExtendedLogger.logCannotSpecifyResourceGroupOptionLoggable();
            l.log();
            options.setResourceGroup((String)null);
         }

         ApplicationUtils.validateAndSetResourceGroupOrTemplateOptions(options, domain, editDomain);
         String resourceGroupTemplate = info.getResourceGroupTemplate();
         String resourceGroup = info.getResourceGroup();
         if (resourceGroupTemplate == null && resourceGroup == null) {
            this.deriveAndSetResourceGroupName(appName, options, domain, taskType);
         }

         Loggable l;
         if (options.getPartition() != null && options.getResourceGroup() == null) {
            l = DeployerRuntimeExtendedLogger.logCannotSpecifyPartitionWithoutResourceGroupLoggable(options.getPartition());
            l.log();
            throw new ManagementException(l.getMessage());
         } else if ((taskType == 7 || taskType == 8) && resourceGroupTemplate != null) {
            l = DeployerRuntimeExtendedLogger.logStartStopOnTemplateNotSupportedLoggable();
            l.log();
            throw new ManagementException(l.getMessage());
         }
      }
   }

   private void deriveAndSetResourceGroupName(String appName, DeploymentOptions options, DomainMBean domain, int taskType) throws ManagementException {
      if (taskType == 7 || taskType == 8 || taskType == 9 || taskType == 10 || taskType == 12) {
         String partition = options.getPartition();
         String resourceGroupName = AppDeploymentHelper.deriveResourceGroupNameForDeployedApp(appName, partition, domain);
         if (resourceGroupName != null) {
            options.setResourceGroup(resourceGroupName);
         }
      }

   }

   private boolean callConfirmApplicationName(String name, String source, DeploymentData info) {
      if (name != null) {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         AppDeploymentMBean appDeployment = ApplicationUtils.lookupAppDeploymentInGivenScope(name, info, domain);
         if (appDeployment != null) {
            String appSource = appDeployment.getSourcePath();
            if (source != null && appSource != null) {
               File appSourceFile = new File(appSource);
               File sourceFile = new File(source);

               try {
                  if (appSourceFile.getCanonicalPath().equals(sourceFile.getCanonicalPath())) {
                     return false;
                  }
               } catch (Exception var10) {
               }
            }
         }
      }

      return true;
   }

   static int getPositiveIntProperty(String key, int defaultValue) {
      try {
         String prop = System.getProperty(key);
         if (prop != null) {
            int value = Integer.parseInt(prop);
            if (value >= 0) {
               return value;
            }
         }
      } catch (NumberFormatException var4) {
      }

      return defaultValue;
   }

   public void handleCMMLevel(int newLevel) {
      this.cmmLevel = newLevel;
      if (this.cmmLevel > 5) {
         WorkManager wm = WorkManagerFactory.getInstance().getDefault();
         wm.schedule(new MemoryPressureRunnable());
      }

   }

   class MemoryPressureRunnable implements Runnable {
      public void run() {
         DeployerRuntimeImpl.this.purgeRetiredTasks();
      }
   }

   private final class TasksRetirePoller implements TimerListener {
      private TasksRetirePoller() {
      }

      public final void timerExpired(Timer timer) {
         DeployerRuntimeImpl.this.checkAndPurgeRetiredTasks();
      }

      // $FF: synthetic method
      TasksRetirePoller(Object x1) {
         this();
      }
   }
}
