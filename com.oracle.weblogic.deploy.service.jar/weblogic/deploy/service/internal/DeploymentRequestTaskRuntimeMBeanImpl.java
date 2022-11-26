package weblogic.deploy.service.internal;

import java.io.PrintWriter;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.DeploymentRequestSubTask;
import weblogic.deploy.service.internal.adminserver.AdminDeploymentRequestCancellerService;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;

public final class DeploymentRequestTaskRuntimeMBeanImpl extends DomainRuntimeMBeanDelegate implements DeploymentRequestTaskRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final DeploymentRequest deploymentRequest;
   private static final String TASK_NAME_PREFIX = "DREQ-";
   private final String description;
   private final long taskId;
   private int state;
   private HashMap failedTargetsMap = new HashMap();
   private Map deploymentSubTasks = new HashMap();
   private Set serversToBeRestarted;
   private final long beginTime = System.currentTimeMillis();
   private Exception lastException;
   private long endTime;
   private boolean failedWhilePreparingToStart = false;
   private boolean allSubTasksCompleted = false;

   public DeploymentRequestTaskRuntimeMBeanImpl(String description, DeploymentRequest req) throws ManagementException {
      super("DREQ-" + req.getId(), false);
      this.description = description;
      this.deploymentRequest = req;
      this.taskId = req.getId();
      this.state = 0;
   }

   private static void debug(String message) {
      Debug.serviceDebug(message);
   }

   private static boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   public final String getDescription() {
      return this.description;
   }

   public final String getStatus() {
      switch (this.state) {
         case 0:
            return "STATE_INITIALIZING";
         case 1:
            return "STATE_INPROGRESS";
         case 2:
            return "STATE_SUCCESS";
         case 3:
            return "STATE_FAILED";
         case 4:
            return "STATE_CANCEL_SCHEDULED";
         case 5:
            return "STATE_CANCEL_INPROGRESS";
         case 6:
            return "STATE_CANCEL_COMPLETED";
         case 7:
            return "STATE_CANCEL_FAILED";
         case 8:
            return "STATE_COMMIT_FAILED";
         default:
            return "STATE_UNKNOWN";
      }
   }

   public String getProgress() {
      switch (this.state) {
         case 0:
            return "processing";
         case 1:
            return "processing";
         case 2:
            return "success";
         case 3:
            return "failed";
         case 4:
            return "processing";
         case 5:
            return "processing";
         case 6:
            return "failed";
         case 7:
            return "failed";
         case 8:
            return "failed";
         default:
            return "failed";
      }
   }

   public final boolean isRunning() {
      if (this.getDeploymentRequestSubTasks().size() <= 0) {
         return true;
      } else {
         Iterator iterator = this.deploymentSubTasks.entrySet().iterator();

         DeploymentRequestSubTask subTask;
         do {
            if (!iterator.hasNext()) {
               return false;
            }

            Map.Entry eachEntry = (Map.Entry)iterator.next();
            subTask = (DeploymentRequestSubTask)eachEntry.getValue();
         } while(!subTask.isRunning());

         return true;
      }
   }

   public final boolean isComplete() {
      if (this.getDeploymentRequestSubTasks().size() <= 0) {
         return true;
      } else {
         Iterator iterator = this.deploymentSubTasks.entrySet().iterator();

         DeploymentRequestSubTask subTask;
         do {
            if (!iterator.hasNext()) {
               return true;
            }

            Map.Entry eachEntry = (Map.Entry)iterator.next();
            subTask = (DeploymentRequestSubTask)eachEntry.getValue();
         } while(subTask.isComplete());

         return false;
      }
   }

   public final long getBeginTime() {
      return this.beginTime;
   }

   public final long getEndTime() {
      return this.endTime;
   }

   public final void setEndTime() {
      this.endTime = System.currentTimeMillis();
   }

   public final TaskRuntimeMBean[] getSubTasks() {
      Collection tasks = this.getDeploymentRequestSubTasks().values();
      return (TaskRuntimeMBean[])((TaskRuntimeMBean[])tasks.toArray(new TaskRuntimeMBean[tasks.size()]));
   }

   public final TaskRuntimeMBean getParentTask() {
      return this;
   }

   public final void cancel() throws Exception {
      if (this.deploymentSubTasks != null && this.deploymentSubTasks.size() > 0) {
         boolean isInitializing = this.getState() == 0;
         this.setState(4);
         this.prepareSubTasksForCancel();
         if (isInitializing) {
            AdminDeploymentRequestCancellerService canceller = (AdminDeploymentRequestCancellerService)LocatorUtilities.getService(AdminDeploymentRequestCancellerService.class);
            canceller.deploymentRequestCancelledBeforeStart(this.deploymentRequest);
         } else {
            DeploymentService.getDeploymentService().cancel(this.deploymentRequest);
         }

      } else {
         throw new ManagementException(DeploymentServiceLogger.logNoTaskToCancelLoggable().getMessage());
      }
   }

   public final void printLog(PrintWriter out) {
   }

   public final synchronized Exception getError() {
      if (this.lastException != null) {
         return this.lastException;
      } else if (this.getDeploymentRequestSubTasks().size() <= 0) {
         return this.lastException;
      } else {
         Iterator iterator = this.deploymentSubTasks.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry eachEntry = (Map.Entry)iterator.next();
            DeploymentRequestSubTask subTask = (DeploymentRequestSubTask)eachEntry.getValue();
            if (subTask.getError() != null) {
               this.lastException = subTask.getError();
               break;
            }
         }

         return this.lastException;
      }
   }

   public final boolean isSystemTask() {
      return false;
   }

   public final void setSystemTask(boolean sys) {
   }

   public final long getTaskId() {
      return this.taskId;
   }

   public final int getState() {
      return this.state;
   }

   public final void setState(int newState) {
      this.state = newState;
   }

   public final synchronized Map getFailedTargets() {
      if (this.failedTargetsMap.size() <= 0 && this.getDeploymentRequestSubTasks().size() > 0) {
         Iterator iterator = this.getDeploymentRequestSubTasks().entrySet().iterator();

         while(true) {
            Map subTaskFailedTargets;
            do {
               do {
                  if (!iterator.hasNext()) {
                     if (isDebugEnabled()) {
                        debug("getFailedTargets returning: " + this.failedTargetsMap);
                     }

                     return this.failedTargetsMap;
                  }

                  Map.Entry eachTaskEntry = (Map.Entry)iterator.next();
                  DeploymentRequestSubTask subTask = (DeploymentRequestSubTask)eachTaskEntry.getValue();
                  subTaskFailedTargets = subTask.getFailedTargets();
               } while(subTaskFailedTargets == null);
            } while(subTaskFailedTargets.size() <= 0);

            Iterator subTaskFailedTargetsIterator = subTaskFailedTargets.entrySet().iterator();

            while(subTaskFailedTargetsIterator.hasNext()) {
               Map.Entry failEntry = (Map.Entry)subTaskFailedTargetsIterator.next();
               String server = (String)failEntry.getKey();
               if (!this.failedTargetsMap.containsKey(server)) {
                  Exception failure = (Exception)failEntry.getValue();
                  if (isDebugEnabled()) {
                     debug("getFailedTargets adding target '" + server + "' and failure: " + failure + " to failed targets map");
                  }

                  this.failedTargetsMap.put(server, failure);
               }
            }
         }
      } else {
         if (isDebugEnabled()) {
            debug("getFailedTargets returning: " + this.failedTargetsMap);
         }

         return (HashMap)this.failedTargetsMap.clone();
      }
   }

   public final synchronized void addFailedTarget(String target, Exception reason) {
      this.failedTargetsMap.put(target, reason);
      if (isDebugEnabled()) {
         debug("addFailedTargets adding target '" + target + "' and failure: " + reason + " to failed targets map");
      }

      this.lastException = reason;
   }

   private Set getRestartSet() {
      if (this.serversToBeRestarted == null) {
         this.serversToBeRestarted = new HashSet();
      }

      return this.serversToBeRestarted;
   }

   public final synchronized String[] getServersToBeRestarted() {
      String[] dummy = new String[0];
      return (String[])((String[])this.getRestartSet().toArray(dummy));
   }

   public final synchronized void addServerToRestartSet(String server) {
      this.getRestartSet().add(server);
   }

   public final DeploymentRequest getDeploymentRequest() {
      return this.deploymentRequest;
   }

   private Map getDeploymentRequestSubTasks() {
      return this.deploymentSubTasks;
   }

   public final void addDeploymentRequestSubTask(DeploymentRequestSubTask subTask, String id) {
      this.getDeploymentRequestSubTasks().put(id, subTask);
      subTask.setMyParent(this);
   }

   public final void start() throws ManagementException {
      this.prepareSubTasksForStart();
      this.startTaskIfNecessary();
   }

   public final void unregisterIfNoSubTasks() {
      if (this.getDeploymentRequestSubTasks().isEmpty()) {
         try {
            if (isDebugEnabled()) {
               debug("Unregistering DeploymentRequestTaskRuntimeMBean : " + this);
            }

            this.unregister();
         } catch (Throwable var2) {
            var2.printStackTrace();
         }
      }

   }

   private void prepareSubTasksForStart() throws ManagementException {
      try {
         Iterator var1 = this.getDeploymentRequestSubTasks().entrySet().iterator();

         while(var1.hasNext()) {
            Object o = var1.next();
            Map.Entry each = (Map.Entry)o;
            DeploymentRequestSubTask subTask = (DeploymentRequestSubTask)each.getValue();
            subTask.prepareToStart();
         }

      } catch (ManagementException var6) {
         this.failedWhilePreparingToStart = true;

         try {
            this.cancel();
         } catch (Exception var5) {
            var5.printStackTrace();
         }

         throw var6;
      }
   }

   private void startTaskIfNecessary() {
      if (this.getDeploymentRequestSubTasks().size() > 0) {
         this.allSubTasksCompleted = true;
      }

      this.lastException = null;
      this.checkForPreStartErrors();
      if (this.lastException != null) {
         this.setState(3);
      } else if (this.allSubTasksCompleted) {
         if (isDebugEnabled()) {
            debug("All sub tasks complete for task '" + this.taskId + "' - transitioning task to STATE_SUCCESS");
         }

         this.setState(2);
      } else {
         this.setState(1);
         DeploymentService.getDeploymentService().startDeploy(this.deploymentRequest);
      }

   }

   private void checkForPreStartErrors() {
      Iterator var1 = this.deploymentSubTasks.entrySet().iterator();

      while(var1.hasNext()) {
         Object o = var1.next();
         Map.Entry each = (Map.Entry)o;
         DeploymentRequestSubTask subTask = (DeploymentRequestSubTask)each.getValue();
         if (subTask.isRunning()) {
            this.allSubTasksCompleted = false;
         } else {
            this.lastException = subTask.getError();
            if (this.lastException != null) {
               String localServerName = ManagementService.getRuntimeAccess(kernelId).getServerName();
               this.addFailedTarget(localServerName, this.lastException);
               return;
            }
         }
      }

   }

   private void prepareSubTasksForCancel() throws Exception {
      Iterator var1 = this.deploymentSubTasks.entrySet().iterator();

      while(var1.hasNext()) {
         Object o = var1.next();
         Map.Entry eachEntry = (Map.Entry)o;
         DeploymentRequestSubTask subTask = (DeploymentRequestSubTask)eachEntry.getValue();
         boolean needToPrepareForCancel = this.failedWhilePreparingToStart ? !subTask.isComplete() : true;
         if (needToPrepareForCancel) {
            subTask.prepareToCancel();
         }
      }

   }
}
