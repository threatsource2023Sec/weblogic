package weblogic.management.provider.internal;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.deploy.service.FailureDescription;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorDiff;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.ActivateTask;
import weblogic.management.provider.EditFailedException;
import weblogic.management.provider.EditWaitTimedOutException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtils;

public final class ActivateTaskImpl implements ActivateTask {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private Map serversStateMap;
   private final Map systemComponentToRestartMap = new HashMap();
   private ArrayList changes;
   private AuthenticatedSubject user;
   private Map failedServersMap;
   private Set activatingServers = Collections.synchronizedSet(new HashSet(20));
   private DeploymentRequestTaskRuntimeMBean deploymentReqTask;
   private final String description;
   private volatile int state;
   private long taskId;
   private final long activateTimeoutTime;
   private final long beginTime = System.currentTimeMillis();
   private long endTime;
   private Exception error;
   private boolean subTaskErrorsAdded;
   private boolean waitingForEndFailureCallback;
   private boolean deploySucceededCalled;
   private boolean commitSucceededCalled;
   private boolean commitFailureOccurred;
   private boolean haveConfigDeployments;
   private EditLockManager lockMgr;
   private boolean editLockReleased;
   private volatile CompletionListener completionListener;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private volatile EditAccessImpl editAccessImpl;
   private final String editSessionName;
   private final String partitionName;
   private DescriptorDiff currentTreeConcurrentAppPrepareRevertDiff;
   private DescriptorDiff editTreeConcurrentAppPrepareRevertDiff;
   private boolean deploymentStarted;

   public boolean isDeploymentStarted() {
      return this.deploymentStarted;
   }

   public void setDeploymentStarted(boolean deploymentStarted) {
      this.deploymentStarted = deploymentStarted;
   }

   public ActivateTaskImpl(String description, EditLockManager lockMgr, boolean haveConfigDeployments, ArrayList activateChanges, AuthenticatedSubject user, long taskId, long timeout, String[] targets, EditAccessImpl editAccessImpl) throws ManagementException {
      this.description = description;
      this.taskId = taskId;
      this.haveConfigDeployments = haveConfigDeployments;
      if (timeout == Long.MAX_VALUE) {
         this.activateTimeoutTime = Long.MAX_VALUE;
      } else {
         this.activateTimeoutTime = System.currentTimeMillis() + timeout;
      }

      if (!haveConfigDeployments) {
         this.setEndTime();
         this.setState(4);
         this.editLockReleased = true;
      } else {
         this.state = 1;

         for(int i = 0; targets != null && i < targets.length; ++i) {
            this.activatingServers.add(targets[i]);
         }
      }

      this.changes = activateChanges;
      this.user = user;
      this.lockMgr = lockMgr;
      if (this.changes != null) {
         Iterator var20 = this.changes.iterator();

         while(var20.hasNext()) {
            Object change = var20.next();
            BeanUpdateEvent evt = (BeanUpdateEvent)change;
            BeanUpdateEvent.PropertyUpdate[] updates = evt.getUpdateList();
            BeanUpdateEvent.PropertyUpdate[] var16 = updates;
            int var17 = updates.length;

            for(int var18 = 0; var18 < var17; ++var18) {
               BeanUpdateEvent.PropertyUpdate update = var16[var18];
               if (!update.isDynamic() && ChangeUtils.isRealmRestartEnabled(evt.getProposedBean())) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Reset dynamic bit for property " + update.getPropertyName());
                  }

                  update.resetDynamic();
               }
            }
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created activate task runtime with task " + taskId + " timeout " + new Date(this.activateTimeoutTime) + " state " + this.getStatus() + " user " + this.getUser());
      }

      this.editAccessImpl = editAccessImpl;
      this.editSessionName = editAccessImpl.getEditSessionName();
      this.partitionName = editAccessImpl.getPartitionName();
   }

   public final int getState() {
      return this.state == 4 && !this.commitSucceededCalled && this.deploymentReqTask != null && !this.deploymentReqTask.isComplete() ? 2 : this.state;
   }

   public final void setState(int newState) {
      synchronized(this) {
         this.state = newState;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Setting state for activate task " + this.taskId + " to " + this.getStatus());
         }

         this.notifyAll();
      }

      if (this.state == 5 && !this.deploymentStarted) {
         this.getEditAccessImpl().failedBeforeDeploymentStart(this.getTaskId());
      }

      if (this.state != 0 && !this.isRunning() && this.completionListener != null) {
         this.completionListener.onCompleted();
      }

   }

   public Map getStateOnServers() {
      if (this.serversStateMap == null) {
         this.serversStateMap = new HashMap();
      }

      this.checkDeploymentSubTasksStatus();
      return this.serversStateMap;
   }

   public long getTaskId() {
      return this.taskId;
   }

   public final synchronized boolean updateServerState(String server, int state) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Updating state for activate task " + this.taskId + " server " + server + " to " + this.getStatusForState(state));
      }

      Integer serverState = (Integer)this.getStateOnServers().get(server);
      if (state == 2) {
         if (serverState == null) {
            this.getStateOnServers().put(server, state);
         }
      } else if (state == 5) {
         this.getStateOnServers().put(server, state);
      } else if (state == 4) {
         this.getStateOnServers().put(server, state);
         this.activatingServers.remove(server);
      } else if (state == 3) {
         this.getStateOnServers().put(server, state);
         this.activatingServers.remove(server);
      } else if (state == 6) {
         this.activatingServers.remove(server);
         this.getStateOnServers().put(server, 5);
      } else if (state == 7) {
         this.commitFailureOccurred = true;
         this.activatingServers.remove(server);
         if (serverState == null || serverState != 5) {
            this.getStateOnServers().put(server, state);
         }
      } else if (state == 8) {
         this.getStateOnServers().put(server, state);
      }

      if (state != 4 && state != 3 && state != 7) {
         if (state == 6) {
            try {
               String[] servers = (String[])this.activatingServers.toArray(new String[this.activatingServers.size()]);
               String[] var5 = servers;
               int var6 = servers.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  String svrName = var5[var7];
                  if (this.getStateOnServers().get(svrName) == null || this.getFailedServers().get(svrName) != null) {
                     this.activatingServers.remove(svrName);
                  }
               }
            } catch (Exception var9) {
            }

            if (this.activatingServers.size() == 0) {
               if (!this.isWaitingForEndFailureCallback()) {
                  this.setState(6);
               } else {
                  this.setEndTime();
                  this.setState(5);
               }
            }
         } else if (state == 2 && this.activatingServers.size() == 0 && serverState != null && serverState == 3) {
            return true;
         }
      } else if (this.activatingServers.size() == 0) {
         if (this.commitFailureOccurred) {
            if (!this.isWaitingForEndFailureCallback()) {
               this.setState(7);
            } else {
               this.setEndTime();
               this.setState(5);
            }
         } else {
            if (!this.deploySucceededCalled) {
               if (state == 3) {
                  return false;
               }

               return true;
            }

            this.setEndTime();
            this.setState(4);
         }
      }

      return false;
   }

   public Iterator getChanges() {
      return this.changes != null ? this.changes.iterator() : null;
   }

   public String getUser() {
      return SubjectUtils.getUsername(this.user);
   }

   public final Map getFailedServers() {
      if (this.failedServersMap == null) {
         this.failedServersMap = new HashMap();
      }

      this.checkDeploymentSubTasksStatus();
      return this.failedServersMap;
   }

   public final void addFailedServer(String server, Exception reason) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Adding failed server for activate task " + this.taskId + " server " + server + " reason " + reason);
      }

      this.getFailedServers().put(server, reason);
      this.getStateOnServers().put(server, 5);
      this.setError(reason);
   }

   public void deploySucceeded(FailureDescription[] deferredDeployments) {
      this.deploySucceededCalled = true;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Deploy succeeded for activate task " + this.taskId + " releasing lock");
      }

      if (this.getState() != 4) {
         this.releaseEditLock();

         for(int i = 0; deferredDeployments != null && i < deferredDeployments.length; ++i) {
            this.activatingServers.remove(deferredDeployments[i].getServer());
         }

         if (this.activatingServers.size() == 0 && !this.commitFailureOccurred) {
            this.setEndTime();
            this.setState(4);
         } else if (this.getState() == 1) {
            this.setState(2);
         }
      }

   }

   public void commitSucceeded() {
      this.commitSucceededCalled = true;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Commit succeeded for activate task " + this.taskId);
      }

      this.releaseAndSetCommitted();
   }

   public void releaseAndSetCommitted() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Set state to committed " + this.taskId + " releasing lock");
      }

      if (this.getState() != 4) {
         this.releaseEditLock();
         this.setEndTime();
         this.setState(4);
      }

   }

   public void waitForTaskCompletion() {
      this.waitForCompletion(this.activateTimeoutTime);
   }

   public void waitForTaskCompletion(long timeout) {
      this.waitForCompletion(System.currentTimeMillis() + timeout);
   }

   private void waitForCompletion(long timeoutTime) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Waiting for completion for activate task " + this.taskId + " timeoutTime is " + new Date(timeoutTime));
      }

      while(this.isRunning()) {
         try {
            long remainingWaitTime = timeoutTime - System.currentTimeMillis();
            if (remainingWaitTime > 0L) {
               synchronized(this) {
                  this.wait(remainingWaitTime);
               }
            } else {
               if (System.currentTimeMillis() < this.activateTimeoutTime) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("JMX invocation timed out before activate timeout for activate task " + this.taskId + ". It means activate is still running in the background, which can either succeed or fail.");
                  }

                  this.outputActivateTaskInfo(true, timeoutTime);
                  return;
               }

               this.setEndTime();
               this.setError(new RuntimeException("Timed out waiting for completion: " + this.getCurrentErrorState()));
               if (this.getState() != 5) {
                  this.setState(5);
               }

               this.outputActivateTaskInfo(false, timeoutTime);
            }
         } catch (InterruptedException var9) {
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Waited for completion of config for activate task " + this.taskId);
      }

      if (this.state != 5) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Waiting for completion of subtasks for activate task " + this.taskId + " timeoutTime is " + new Date(timeoutTime));
         }

         while(this.deploymentReqTask != null && !this.deploymentReqTask.isComplete() && this.getState() != 5) {
            if (System.currentTimeMillis() >= this.activateTimeoutTime) {
               this.setState(5);
               this.setError(new RuntimeException("Timed out waiting for completion of deployment: " + this.getCurrentErrorState()));
               this.outputActivateTaskInfo(false, timeoutTime);
            } else {
               try {
                  Thread.sleep(100L);
               } catch (InterruptedException var7) {
               }
            }
         }

         this.checkDeploymentSubTasksStatus();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Waited for completion of subtasks for activate task " + this.taskId);
         }

         if (!this.haveConfigDeployments && this.getState() == 4) {
            try {
               this.releaseEditLock();
            } catch (IllegalStateException var10) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Edit lock taken away", var10);
               }
            }
         }

      }
   }

   void reLock() {
      try {
         this.editAccessImpl.reLock();
      } catch (EditWaitTimedOutException var2) {
         var2.printStackTrace();
      } catch (EditFailedException var3) {
         var3.printStackTrace();
      }

      this.editLockReleased = false;
   }

   void releaseEditLock() {
      if (!this.editLockReleased) {
         this.lockMgr.releaseEditLock(this.user);
         this.editLockReleased = true;
      }

      if (this.completionListener != null) {
         this.completionListener.onLockReleased();
      }

   }

   public DeploymentRequestTaskRuntimeMBean getDeploymentRequestTaskRuntimeMBean() {
      return this.deploymentReqTask;
   }

   public final boolean isWaitingForEndFailureCallback() {
      return this.waitingForEndFailureCallback;
   }

   public final void setWaitingForEndFailureCallback(boolean waiting) {
      this.waitingForEndFailureCallback = waiting;
   }

   void setDeploymentRequestTaskRuntimeMBean(DeploymentRequestTaskRuntimeMBean task) {
      this.deploymentReqTask = task;
   }

   private synchronized void checkDeploymentSubTasksStatus() {
      if (this.deploymentReqTask != null && this.deploymentReqTask.isComplete() && !this.subTaskErrorsAdded) {
         this.subTaskErrorsAdded = true;
         Map failedTargets = this.deploymentReqTask.getFailedTargets();
         Set values = failedTargets.entrySet();
         Iterator var3 = values.iterator();

         while(var3.hasNext()) {
            Object value = var3.next();
            Map.Entry entry = (Map.Entry)value;
            Exception failure = (Exception)entry.getValue();
            String target = (String)entry.getKey();
            this.addFailedServer(target, failure);
         }

      }
   }

   public final String getDescription() {
      return this.description;
   }

   public final String getStatus() {
      return this.getStatusForState(this.state);
   }

   private String getStatusForState(int statusState) {
      switch (statusState) {
         case 0:
            return "STATE_NEW";
         case 1:
            return "STATE_DISTRIBUTING";
         case 2:
            return "STATE_DISTRIBUTED";
         case 3:
            return "STATE_PENDING";
         case 4:
            return "STATE_COMMITTED";
         case 5:
            return "STATE_FAILED";
         case 6:
            return "STATE_CANCELING";
         case 7:
            return "STATE_COMMIT_FAILING";
         case 8:
            return "STATE_DEFERRED";
         default:
            return "STATE_UNKNOWN";
      }
   }

   public final void cancel() throws Exception {
      if (this.deploymentReqTask != null) {
         DeploymentService.getDeploymentService().cancel(this.deploymentReqTask.getDeploymentRequest());
      }
   }

   public final boolean isRunning() {
      return this.state == 1 || this.state == 2 || this.state == 3 || this.state == 6 || this.state == 7;
   }

   public final long getBeginTime() {
      return this.beginTime;
   }

   public final long getEndTime() {
      return this.endTime;
   }

   private void setEndTime() {
      this.endTime = System.currentTimeMillis();
   }

   public void setCompletionListener(CompletionListener completionListener) {
      this.completionListener = completionListener;
   }

   public final Exception getError() {
      if (this.error == null) {
         this.checkDeploymentSubTasksStatus();
      }

      return this.error;
   }

   public final void setError(Exception ex) {
      this.error = ex;
   }

   public final void addDeferredServer(String server, Exception reason) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Adding deferred server for activate task " + this.taskId + " server " + server + " reason " + reason);
      }

      if (reason != null) {
         this.getFailedServers().put(server, reason);
      }

      this.getStateOnServers().put(server, 8);
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String getEditSessionName() {
      return this.editSessionName;
   }

   public Map getSystemComponentRestartRequired() {
      return this.systemComponentToRestartMap;
   }

   void releaseEditAccess() {
      this.editAccessImpl = null;
   }

   private String getCurrentErrorState() {
      StringBuilder sb = new StringBuilder();
      sb.append(" Activate State: ").append(this.getStatus());
      Map serverStates = this.getStateOnServers();
      if (serverStates.size() > 0) {
         sb.append(" Target Servers States: ");
      }

      Iterator var3 = serverStates.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         sb.append((String)entry.getKey()).append(" ").append(this.getStatusForState((Integer)entry.getValue())).append(" ");
      }

      return sb.toString();
   }

   void outputActivateTaskInfo(boolean isJMXInvocationTimedOut, long jmxInvocationTimeoutTime) {
      try {
         StringBuilder info = new StringBuilder();
         info.append("\n").append(this.getCurrentErrorState()).append("\n");
         info.append("Begin-time: ").append(this.getBeginTime());
         if (isJMXInvocationTimedOut) {
            info.append(", Current Time: ").append(System.currentTimeMillis()).append(", JMX-Invocation-Timeout: ").append(jmxInvocationTimeoutTime).append("\n");
         } else {
            info.append(", End-time: ").append(this.getEndTime()).append(", Activate-Timeout: ").append(this.activateTimeoutTime).append("\n");
         }

         info.append("User: ").append(this.user).append(", Description: ").append(this.description).append("\n");
         if (this.error != null) {
            info.append("Activate-Exception: ").append(this.error).append("\n");
         }

         Iterator var5 = this.getFailedServers().entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry entry = (Map.Entry)var5.next();
            info.append("Failed-server: ").append((String)entry.getKey()).append(", Reason: ").append(entry.getValue()).append("\n");
         }

         if (this.lockMgr != null) {
            info.append("Edit-owner: ").append(this.lockMgr.getLockOwner()).append(", Exclusive: ").append(this.lockMgr.isLockExclusive()).append(", Acquired: ").append(this.lockMgr.getLockAcquisitionTime()).append(", Expire: ").append(this.lockMgr.getLockExpirationTime()).append("\n");
         }

         if (this.changes != null && this.changes.size() > 0) {
            info.append("Activate-changes:");
            var5 = this.changes.iterator();

            while(var5.hasNext()) {
               Object change = var5.next();
               info.append(" ").append(change.toString()).append("\n");
            }
         }

         info.append("Sub-Task: ").append(this.subTaskErrorsAdded).append(", Waiting-fail: ").append(this.isWaitingForEndFailureCallback()).append(", Deploy-succeeded: ").append(this.deploySucceededCalled).append(", Commit-succeeded: ").append(this.commitSucceededCalled).append(", Commit-failed: ").append(this.commitFailureOccurred).append(", Have-config: ").append(this.haveConfigDeployments).append("\n");
         String threadDumps = getThreadDumpsOnServers(this.activatingServers);
         info.append(threadDumps);
         if (isJMXInvocationTimedOut) {
            ManagementLogger.logJMXInvocationTimedOutOnActivate(this.getTaskId(), info.toString());
         } else {
            ManagementLogger.logActivateTimedOut(this.getTaskId(), info.toString());
         }
      } catch (Exception var7) {
      }

   }

   private static String getThreadDumpsOnServers(Set servers) {
      if (servers == null) {
         return null;
      } else {
         Iterator theIter = servers.iterator();
         StringBuilder sBuffer = new StringBuilder();
         sBuffer.append("Thread Dumps on servers ").append(servers).append(" {\n");

         while(theIter.hasNext()) {
            String eachTarget = (String)theIter.next();

            try {
               DomainRuntimeServiceMBean drsm = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
               ServerRuntimeMBean srm = drsm.lookupServerRuntime(eachTarget);
               String dump = "";
               if (srm != null && srm.getJVMRuntime() != null) {
                  dump = srm.getJVMRuntime().getThreadStackDump();
               }

               sBuffer.append("\nThreadDump for ").append(eachTarget).append(":\n");
               sBuffer.append(dump);
               sBuffer.append("\n\n\n");
            } catch (Throwable var7) {
               sBuffer.append(" Exception while getting ThreadDump for ");
               sBuffer.append(eachTarget).append(" :: ");
               sBuffer.append(StackTraceUtils.throwable2StackTrace(var7));
               sBuffer.append("\n\n");
            }
         }

         sBuffer.append("\n}\n");
         return sBuffer.toString();
      }
   }

   EditAccessImpl getEditAccessImpl() {
      return this.editAccessImpl;
   }

   void setCurrentTreeConcurrentAppPrepareRevertDiff(DescriptorDiff currentTreeConcurrentAppPrepareRevertDiff) {
      this.currentTreeConcurrentAppPrepareRevertDiff = currentTreeConcurrentAppPrepareRevertDiff;
   }

   DescriptorDiff getCurrentTreeConcurrentAppPrepareRevertDiff() {
      return this.currentTreeConcurrentAppPrepareRevertDiff;
   }

   void setEditTreeConcurrentAppPrepareRevertDiff(DescriptorDiff editTreeConcurrentAppPrepareRevertDiff) {
      this.editTreeConcurrentAppPrepareRevertDiff = editTreeConcurrentAppPrepareRevertDiff;
   }

   DescriptorDiff getEditTreeConcurrentAppPrepareRevertDiff() {
      return this.editTreeConcurrentAppPrepareRevertDiff;
   }

   boolean isLockExclusive() {
      return this.lockMgr.isLockExclusive();
   }

   boolean isHaveConfigDeployments() {
      return this.haveConfigDeployments;
   }

   interface StartListener {
      void onStarted();
   }

   interface CompletionListener {
      void onCompleted();

      void onLockReleased();
   }
}
