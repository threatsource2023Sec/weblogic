package weblogic.server;

import java.beans.PropertyChangeListener;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.management.InstanceNotFoundException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.ManagementException;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.PartitionRuntimeStateManagerContract;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGOperation;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.t3.srvr.PartitionLifecycleLogger;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManagerFactory;

public class ResourceGroupLifeCycleRuntime extends DomainRuntimeMBeanDelegate implements ResourceGroupLifeCycleRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final Properties DO_NOT_START_PROPS = prepDoNotStartProps();
   private static final int TASK_AFTERLIFE_TIME_MILLIS = 1800000;
   private static final boolean USE_OLD_STATE_NAMES;
   private static ComponentInvocationContext DOMAIN_CIC;
   private final Set tasks;
   private final ResourceGroupMBean resourceGroupMBean;
   private final String partitionName;
   private ComponentInvocationContext PARTITION_CIC;
   private static final String PTN_DELIMITER = "$";
   public static final String STATE_PROPERTY_NAME = "state";
   private final DomainRuntimeServiceMBean domainRuntimeServiceMBean;
   private final PartitionRuntimeStateManagerContract partitionRuntimeStateManager;
   private ResourceGroupLifecycleOperations.RGState state;
   private static final boolean DEBUG;

   private static Properties prepDoNotStartProps() {
      Properties appRuntimeOperationProps = new Properties();
      appRuntimeOperationProps.setProperty("startTask", "false");
      appRuntimeOperationProps.setProperty("appShutdownOnStop", "true");
      return appRuntimeOperationProps;
   }

   private static synchronized ComponentInvocationContext getDomainCIC() {
      if (DOMAIN_CIC == null) {
         DOMAIN_CIC = ComponentInvocationContextManager.getInstance().createComponentInvocationContext("DOMAIN");
      }

      return DOMAIN_CIC;
   }

   private synchronized ComponentInvocationContext getPartitionCIC() {
      if (this.PARTITION_CIC == null) {
         this.PARTITION_CIC = ComponentInvocationContextManager.getInstance().createComponentInvocationContext(this.partitionName);
      }

      return this.PARTITION_CIC;
   }

   public ResourceGroupLifeCycleRuntime(ResourceGroupMBean resourceGroupMBean, DomainRuntimeMBeanDelegate parentRuntime, PropertyChangeListener listener) throws ManagementException {
      super(resourceGroupMBean.getName(), parentRuntime);
      this.domainRuntimeServiceMBean = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
      this.partitionRuntimeStateManager = (PartitionRuntimeStateManagerContract)GlobalServiceLocator.getServiceLocator().getService(PartitionRuntimeStateManagerContract.class, new Annotation[0]);
      this.state = RGState.shutdownState();
      if (listener != null) {
         this.addPropertyChangeListener(listener);
      }

      this.tasks = Collections.synchronizedSet(new HashSet());
      this.resourceGroupMBean = resourceGroupMBean;
      this.partitionName = resourceGroupMBean.getParent() instanceof DomainMBean ? null : resourceGroupMBean.getParent().getName();
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean start() throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(this.resourceGroupMBean.findEffectiveTargets(), RGOperation.START, "Starting ", false);
      return taskMBean;
   }

   public String getState() {
      return RGState.chooseUserDesiredStateName(this.computeState());
   }

   public String getState(ServerMBean serverMBean) throws ResourceGroupLifecycleException {
      if (!PartitionUtils.getServers(this.resourceGroupMBean).contains(serverMBean.getName())) {
         throw new ResourceGroupLifecycleException(String.format("Server %s is not an effective target for resource group %s.", serverMBean.getName(), this.getName()));
      } else {
         return RGState.chooseUserDesiredStateName(this.stateForServer(serverMBean.getName()));
      }
   }

   public String getState(String serverName) throws ResourceGroupLifecycleException {
      if (!PartitionUtils.getServers(this.resourceGroupMBean).contains(serverName)) {
         throw new ResourceGroupLifecycleException(String.format("Server %s is not an effective target for resource group %s.", serverName, this.getName()));
      } else {
         return RGState.chooseUserDesiredStateName(this.stateForServer(serverName));
      }
   }

   public ResourceGroupLifecycleOperations.RGState getInternalState() {
      return this.computeState();
   }

   private ResourceGroupLifecycleOperations.RGState computeState() {
      ResourceGroupLifecycleOperations.RGState bestState = RGState.UNKNOWN;
      Set servers = PartitionUtils.getSpecificServerNames(this.resourceGroupMBean.findEffectiveTargets());
      String server;
      if (servers.size() > 0) {
         for(Iterator var3 = servers.iterator(); var3.hasNext(); bestState = RGState.max(bestState, this.stateForServer(server))) {
            server = (String)var3.next();
         }
      } else {
         String desiredState = this.partitionRuntimeStateManager.getResourceGroupState(this.partitionName, this.name, this.resourceGroupMBean.isAdministrative());
         if (desiredState == null) {
            desiredState = this.partitionRuntimeStateManager.getDefaultPartitionState();
         }

         desiredState = String.valueOf(PartitionUtils.filteredResourceGroupState(desiredState, this.resourceGroupMBean.isAdministrative()));
         bestState = RGState.valueOf(desiredState);
      }

      return bestState;
   }

   private ResourceGroupLifecycleOperations.RGState stateForServer(String server) {
      ResourceGroupLifecycleOperations.RGState result = RGState.SHUTDOWN;
      ServerRuntimeMBean svrRuntime = this.domainRuntimeServiceMBean.lookupServerRuntime(server);
      if (svrRuntime != null) {
         PartitionRuntimeMBean pRMbean = svrRuntime.lookupPartitionRuntime(this.partitionName);

         try {
            String rgStateString;
            if (pRMbean != null && (rgStateString = pRMbean.getRgState(this.getName())) != null) {
               result = RGState.normalize(rgStateString);
            } else if (this.resourceGroupMBean.getParent() instanceof DomainMBean) {
               String domainRGState = svrRuntime.getRgState(this.getName());
               if (domainRGState != null) {
                  result = RGState.normalize(domainRGState);
               }
            }
         } catch (ResourceGroupLifecycleException var7) {
         } catch (Throwable var8) {
            if (!(var8.getCause() instanceof InstanceNotFoundException)) {
               throw var8;
            }
         }
      }

      return result;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean[] getTasks() {
      return (ResourceGroupLifeCycleTaskRuntimeMBean[])((ResourceGroupLifeCycleTaskRuntimeMBean[])this.tasks.toArray(new ResourceGroupLifeCycleTaskRuntimeMBean[this.tasks.size()]));
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean lookupTask(String taskName) {
      Iterator it = this.tasks.iterator();

      ResourceGroupLifeCycleTaskRuntimeMBean task;
      do {
         if (!it.hasNext()) {
            return null;
         }

         task = (ResourceGroupLifeCycleTaskRuntimeMBean)it.next();
      } while(!task.getName().equals(taskName));

      return task;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean start(TargetMBean[] targets) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(targets, RGOperation.START, "Starting ", true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean start(String[] targetNames) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), RGOperation.START, "Starting ", true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean startInAdmin(TargetMBean[] targets) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(targets, RGOperation.ADMIN, "Starting ", true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean startInAdmin(String[] targetNames) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), RGOperation.ADMIN, "Starting ", true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean startInAdmin() throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(this.resourceGroupMBean.findEffectiveTargets(), RGOperation.ADMIN, "Starting ", false);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(this.resourceGroupMBean.findEffectiveTargets(), RGOperation.SHUTDOWN, "Shutting down ", timeout, ignoreSessions, waitForAllSessions, false);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions, TargetMBean[] targets) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(targets, RGOperation.SHUTDOWN, "Shutting down ", timeout, ignoreSessions, waitForAllSessions, true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions, String[] targetNames) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), RGOperation.SHUTDOWN, "Shutting down ", timeout, ignoreSessions, waitForAllSessions, true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, TargetMBean[] targets) throws ResourceGroupLifecycleException {
      return this.shutdown(timeout, ignoreSessions, false, targets);
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, String[] targetNames) throws ResourceGroupLifecycleException {
      return this.shutdown(timeout, ignoreSessions, false, PartitionUtils.lookupTargetMBeans(targetNames));
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions) throws ResourceGroupLifecycleException {
      return this.shutdown(timeout, ignoreSessions, false);
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean shutdown(TargetMBean[] targets) throws ResourceGroupLifecycleException {
      return this.shutdown(0, false, false, (TargetMBean[])targets);
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean shutdown(String[] targetNames) throws ResourceGroupLifecycleException {
      return this.shutdown(0, false, false, (TargetMBean[])PartitionUtils.lookupTargetMBeans(targetNames));
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean shutdown() throws ResourceGroupLifecycleException {
      return this.shutdown(0, false, false);
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean forceShutdown(TargetMBean[] targets) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(targets, RGOperation.FORCE_SHUTDOWN, "Force Shutting down ", true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean forceShutdown(String[] targetNames) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), RGOperation.FORCE_SHUTDOWN, "Force Shutting down ", true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean forceShutdown() throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(this.resourceGroupMBean.findEffectiveTargets(), RGOperation.FORCE_SHUTDOWN, "Force Shutting down ", false);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean suspend(int timeout, boolean ignoreSessions, TargetMBean[] targets) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(targets, RGOperation.SUSPEND, "Suspending  ", timeout, ignoreSessions, true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean suspend(int timeout, boolean ignoreSessions, String[] targetNames) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), RGOperation.SUSPEND, "Suspending  ", timeout, ignoreSessions, true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean suspend(int timeout, boolean ignoreSessions) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(this.resourceGroupMBean.findEffectiveTargets(), RGOperation.SUSPEND, "Suspending  ", timeout, ignoreSessions, false);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean suspend(TargetMBean[] targets) throws ResourceGroupLifecycleException {
      return this.suspend(0, false, (TargetMBean[])targets);
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean suspend(String[] targetNames) throws ResourceGroupLifecycleException {
      return this.suspend(0, false, (TargetMBean[])PartitionUtils.lookupTargetMBeans(targetNames));
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean suspend() throws ResourceGroupLifecycleException {
      return this.suspend(0, false);
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean forceSuspend(TargetMBean[] targets) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(targets, RGOperation.FORCE_SUSPEND, "Force suspending  ", true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean forceSuspend(String[] targetNames) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), RGOperation.FORCE_SUSPEND, "Force suspending  ", true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean forceSuspend() throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(this.resourceGroupMBean.findEffectiveTargets(), RGOperation.FORCE_SUSPEND, "Force suspending  ", false);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean resume(TargetMBean[] targets) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(targets, RGOperation.RESUME, "Resuming  ", true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean resume(String[] targetNames) throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), RGOperation.RESUME, "Resuming  ", true);
      return taskMBean;
   }

   public ResourceGroupLifeCycleTaskRuntimeMBean resume() throws ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskMBean = this.initiateRGLifecycleRequest(this.resourceGroupMBean.findEffectiveTargets(), RGOperation.RESUME, "Resuming  ", false);
      return taskMBean;
   }

   public void purgeTasks() {
   }

   public ResourceGroupLifecycleOperations.RGState state() {
      return this.state;
   }

   public void setState(ResourceGroupLifecycleOperations.RGState newState) {
      ResourceGroupLifecycleOperations.RGState oldState = this.state;
      this.state = newState;
      this._postSet("state", oldState, newState);
   }

   public void markStarted() {
      this.setState(RGState.runningState());
   }

   private ResourceGroupLifeCycleTaskRuntime createTaskMbean(ResourceGroupLifecycleOperations.RGOperation operation, String operationMsg, String serverName) throws ManagementException {
      return new ResourceGroupLifeCycleTaskRuntime(this, operationMsg, operation, serverName);
   }

   private ResourceGroupLifeCycleTaskRuntime initiateRGLifecycleRequest(TargetMBean[] targets, ResourceGroupLifecycleOperations.RGOperation operation, String msg, boolean targetsSpecified) throws ResourceGroupLifecycleException {
      return this.initiateRGLifecycleRequest(targets, operation, msg, 0, true, false, targetsSpecified);
   }

   private ResourceGroupLifeCycleTaskRuntime initiateRGLifecycleRequest(TargetMBean[] targets, ResourceGroupLifecycleOperations.RGOperation operation, String msg, int timeout, boolean ignoreSessions, boolean targetsSpecified) throws ResourceGroupLifecycleException {
      return this.initiateRGLifecycleRequest(targets, operation, msg, timeout, ignoreSessions, false, targetsSpecified);
   }

   private ResourceGroupLifeCycleTaskRuntime initiateRGLifecycleRequest(TargetMBean[] targets, ResourceGroupLifecycleOperations.RGOperation operation, String msg, int timeout, boolean ignoreSessions, boolean waitForAllSessions, boolean targetsSpecified) throws ResourceGroupLifecycleException {
      PartitionUtils.validateTargetsWithResourceGroup(this.resourceGroupMBean, targets);
      Set specificTargets = PartitionUtils.getSpecificServerNames(targets);
      List subTasksList = new LinkedList();
      Set requests = new HashSet();
      String[] serverList = this.getSvrsForSettingDesiredState(specificTargets, targetsSpecified);
      StringBuilder servers = new StringBuilder();

      try {
         Iterator var13 = specificTargets.iterator();

         while(var13.hasNext()) {
            String serverName = (String)var13.next();
            if (!this.isServerActionNeeded(serverName, this.getName(), operation.nextSuccessRGState)) {
               servers.append(serverName).append(",");
               ResourceGroupLifeCycleTaskRuntime subTaskMBean = this.createTaskMbean(operation, msg + this.getName() + " ... on the " + serverName + " server ...", serverName);
               subTasksList.add(subTaskMBean);
               ResourceGroupLifecycleRequest request = new ResourceGroupLifecycleRequest(timeout, ignoreSessions, waitForAllSessions, serverName, subTaskMBean, operation, serverList);
               requests.add(request);
            }
         }
      } catch (ManagementException var42) {
         throw new ResourceGroupLifecycleException(var42);
      }

      String serversSr = servers.length() > 0 ? servers.substring(0, servers.length() - 1) : "";

      ResourceGroupLifeCycleTaskRuntime var47;
      try {
         ManagedInvocationContext mic = ComponentInvocationContextManager.getInstance().setCurrentComponentInvocationContext(getDomainCIC());
         Throwable var45 = null;

         try {
            ResourceGroupLifeCycleTaskRuntime taskMBean;
            if (subTasksList.size() > 0) {
               subTasksList.removeAll(Collections.singleton((Object)null));
               TaskRuntimeMBean[] subTaskArray = (TaskRuntimeMBean[])subTasksList.toArray(new TaskRuntimeMBean[subTasksList.size()]);
               if (DEBUG) {
                  debug("The subtasks length :" + subTaskArray.length);
               }

               taskMBean = this.createTaskMbean(operation, msg + this.getName() + " on servers " + serversSr + " ... ", serversSr);
               taskMBean.registerSubTasks(subTaskArray);
               if (servers.length() > 0) {
                  PartitionLifecycleLogger.initiateRGOperationOnServers(operation.name(), this.getName(), serversSr);
               }

               Iterator var18 = requests.iterator();

               while(var18.hasNext()) {
                  ResourceGroupLifecycleRequest request = (ResourceGroupLifecycleRequest)var18.next();
                  WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
               }
            } else if (specificTargets.isEmpty()) {
               taskMBean = this.alreadyCompletedTask(operation);
            } else {
               taskMBean = this.alreadyCompletedTask(operation);
               this.logRGAlreadyExpectedState(operation.name());
            }

            this.tasks.add(taskMBean);
            this.updateStoreForDesiredState(operation.nextSuccessRGState, targetsSpecified ? PartitionUtils.getSpecificServerArray(targets) : new String[0]);
            var47 = taskMBean;
         } catch (Throwable var38) {
            var45 = var38;
            throw var38;
         } finally {
            if (mic != null) {
               if (var45 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var37) {
                     var45.addSuppressed(var37);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (ManagementException var40) {
         throw new ResourceGroupLifecycleException(var40);
      } finally {
         this.clearOldResourceGroupLifeCycleTaskRuntimes();
      }

      return var47;
   }

   private void logRGAlreadyExpectedState(String operationName) {
      ManagedInvocationContext mic = ComponentInvocationContextManager.getInstance(kernelId).setCurrentComponentInvocationContext(this.getPartitionCIC());
      Throwable var3 = null;

      try {
         PartitionLifecycleLogger.logRGAlreadyExpectedState(operationName, this.getName(), this.getState());
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private ResourceGroupLifeCycleTaskRuntime alreadyCompletedTask(ResourceGroupLifecycleOperations.RGOperation operation) throws ManagementException, ResourceGroupLifecycleException {
      ResourceGroupLifeCycleTaskRuntime taskRuntime = ResourceGroupLifeCycleTaskRuntime.preCompleted(this, operation.toString() + " " + this.getName() + " ...", operation);
      return taskRuntime;
   }

   private static void updateTaskMBeanOnCompletion(ResourceGroupLifeCycleTaskRuntime rlcTaskRuntime) {
      if (rlcTaskRuntime.getError() != null) {
         rlcTaskRuntime.setStatus(ResourceGroupLifeCycleTaskRuntime.Status.FAILED.toString());
      } else {
         rlcTaskRuntime.setStatus(ResourceGroupLifeCycleTaskRuntime.Status.SUCCEEDED.toString());
      }

      rlcTaskRuntime.setEndTime(System.currentTimeMillis());
      rlcTaskRuntime.setIsRunning(false);
   }

   public void clearOldResourceGroupLifeCycleTaskRuntimes() {
      synchronized(this.tasks) {
         Iterator iter = this.tasks.iterator();

         while(iter.hasNext()) {
            ResourceGroupLifeCycleTaskRuntime task = (ResourceGroupLifeCycleTaskRuntime)iter.next();
            if (task.getEndTime() > 0L && System.currentTimeMillis() - task.getEndTime() > 1800000L) {
               try {
                  task.unregister();
               } catch (ManagementException var6) {
               }

               iter.remove();
            }
         }

      }
   }

   private boolean isServerActionNeeded(String server, String resourceGroupName, ResourceGroupLifecycleOperations.RGState state) throws ResourceGroupLifecycleException {
      ServerRuntimeMBean svrRuntime = this.domainRuntimeServiceMBean.lookupServerRuntime(server);
      if (svrRuntime == null) {
         return true;
      } else {
         PartitionRuntimeMBean pRMbean = svrRuntime.lookupPartitionRuntime(this.partitionName);
         String pStateString = null;
         String rgStateString = null;

         try {
            if (pRMbean != null) {
               pStateString = pRMbean.getState();
               rgStateString = pRMbean.getRgState(resourceGroupName);
            }
         } catch (Throwable var9) {
            if (!(var9.getCause() instanceof InstanceNotFoundException)) {
               throw var9;
            }

            pRMbean = null;
         }

         if (DEBUG) {
            debug("<ResourceGroupLifeCycleRuntime> The partition runtime state is :" + (pRMbean == null ? "not found" : pStateString));
         }

         if (pRMbean != null) {
            ResourceGroupLifecycleOperations.RGState rgState = RGState.normalize(rgStateString);
            if (DEBUG) {
               debug("<ResourceGroupLifeCycleRuntime> The resource group state is :" + (rgState == null ? "not found" : rgState.name()));
            }

            if (rgState != null && rgState.name().equals(state.name())) {
               if (DEBUG) {
                  debug("<ResourceGroupLifeCycleRuntime> The resource group is already in expected state :" + state + "on the target " + server);
               }

               return true;
            }

            if (rgState == null && state.equals(RGState.SHUTDOWN)) {
               return true;
            }
         } else {
            if (this.resourceGroupMBean.getParent() instanceof DomainMBean) {
               return this.isDomainRGSameState(resourceGroupName, svrRuntime, state.name());
            }

            if (state.equals(RGState.SHUTDOWN)) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isDomainRGSameState(String resourceGroupName, ServerRuntimeMBean svrRuntime, String state) throws ResourceGroupLifecycleException {
      String domainRGState = svrRuntime.getRgState(resourceGroupName);
      return domainRGState != null && state.equals(domainRGState);
   }

   private void updateStoreForDesiredState(ResourceGroupLifecycleOperations.RGState state, String... servers) throws ResourceGroupLifecycleException {
      try {
         String newDesiredState = state.toString();
         this.partitionRuntimeStateManager.setResourceGroupState(this.partitionName, this.getName(), newDesiredState, servers);
      } catch (ManagementException var8) {
         throw new ResourceGroupLifecycleException(var8);
      }

      ServerRuntimeMBean[] var9 = this.domainRuntimeServiceMBean.getServerRuntimes();
      int var4 = var9.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ServerRuntimeMBean serverRuntime = var9[var5];
         PersistDesiredState request = new PersistResourceGroupDesiredState(state.name(), serverRuntime.getName(), servers);
         WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
      }

   }

   private String[] getSvrsForSettingDesiredState(Set targets, boolean targetsSpecified) {
      return targetsSpecified ? (String[])targets.toArray(new String[targets.size()]) : new String[0];
   }

   private static void debug(String debugMessage) {
      PartitionLifecycleDebugger.debug(debugMessage);
   }

   static {
      USE_OLD_STATE_NAMES = PartitionRuntimeMBean.USE_OLD_STATE_NAMES;
      DEBUG = PartitionLifecycleDebugger.isDebugEnabled();
   }

   private final class PersistResourceGroupDesiredState extends PersistDesiredState {
      PersistResourceGroupDesiredState(String desiredState, String serverName, String... servers) {
         super(desiredState, serverName, servers);
      }

      public void run() {
         ManagedInvocationContext mic = ComponentInvocationContextManager.getInstance(ResourceGroupLifeCycleRuntime.kernelId).setCurrentComponentInvocationContext(ResourceGroupLifeCycleRuntime.this.getPartitionCIC());
         Throwable var2 = null;

         try {
            super.run();
         } catch (Throwable var11) {
            var2 = var11;
            throw var11;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var10) {
                     var2.addSuppressed(var10);
                  }
               } else {
                  mic.close();
               }
            }

         }

      }

      protected void saveDesiredState() throws PartitionLifeCycleException, RemoteException, ResourceGroupLifecycleException {
         this.remote.setDesiredResourceGroupState(ResourceGroupLifeCycleRuntime.this.partitionName, ResourceGroupLifeCycleRuntime.this.resourceGroupMBean.getName(), this.desiredState, this.serversAffected);
      }
   }

   private final class ResourceGroupLifecycleRequest implements Runnable {
      private final ResourceGroupLifeCycleTaskRuntime taskMBean;
      private final String serverName;
      private final int timeout;
      private final boolean ignoreSessions;
      private final ResourceGroupLifecycleOperations.RGOperation operation;
      private final boolean waitForAllSessions;
      private final String[] serverList;

      ResourceGroupLifecycleRequest(int timeout, boolean ignoreSessions, boolean waitForAllSessions, String serverName, ResourceGroupLifeCycleTaskRuntime taskMBean, ResourceGroupLifecycleOperations.RGOperation operation, String[] serverList) {
         this.timeout = timeout;
         this.ignoreSessions = ignoreSessions;
         this.taskMBean = taskMBean;
         this.serverName = serverName;
         this.operation = operation;
         this.waitForAllSessions = waitForAllSessions;
         this.serverList = serverList;
      }

      public void run() {
         ManagedInvocationContext mic = ComponentInvocationContextManager.getInstance().setCurrentComponentInvocationContext(ResourceGroupLifeCycleRuntime.this.getPartitionCIC());
         Throwable var2 = null;

         try {
            long responseTime;
            if (ResourceGroupLifeCycleRuntime.this.resourceGroupMBean.getParent() instanceof PartitionMBean) {
               PartitionMBean partitionMBean = (PartitionMBean)ResourceGroupLifeCycleRuntime.this.resourceGroupMBean.getParent();
               responseTime = ServerLifeCycleRuntime.computeTimeOut(this.operation == RGOperation.SHUTDOWN, (long)this.timeout, (long)partitionMBean.getPartitionLifeCycleTimeoutVal(), (long)partitionMBean.getGracefulShutdownTimeout());
            } else {
               DomainMBean domainMBean = (DomainMBean)ResourceGroupLifeCycleRuntime.this.resourceGroupMBean.getParent();
               ServerMBean serverMBean = domainMBean.lookupServer(this.serverName);
               responseTime = ServerLifeCycleRuntime.computeTimeOut(this.operation == RGOperation.SHUTDOWN, (long)this.timeout, (long)serverMBean.getServerLifeCycleTimeoutVal(), (long)serverMBean.getGracefulShutdownTimeout());
            }

            try {
               RemoteLifeCycleOperations remote = ServerLifeCycleRuntime.getLifeCycleOperationsRemote(this.serverName, responseTime);
               if (remote == null) {
                  throw new ResourceGroupLifecycleException("Can not get to the relevant ServerRuntimeMBean");
               }

               switch (this.operation) {
                  case START:
                     remote.startResourceGroup(ResourceGroupLifeCycleRuntime.this.partitionName, ResourceGroupLifeCycleRuntime.this.getName(), false, this.serverList);
                     break;
                  case ADMIN:
                     remote.startResourceGroup(ResourceGroupLifeCycleRuntime.this.partitionName, ResourceGroupLifeCycleRuntime.this.getName(), true, this.serverList);
                     break;
                  case SUSPEND:
                     remote.suspendResourceGroup(ResourceGroupLifeCycleRuntime.this.partitionName, ResourceGroupLifeCycleRuntime.this.getName(), this.timeout, this.ignoreSessions, this.serverList);
                     break;
                  case FORCE_SUSPEND:
                     remote.forceSuspendResourceGroup(ResourceGroupLifeCycleRuntime.this.partitionName, ResourceGroupLifeCycleRuntime.this.getName(), this.serverList);
                     break;
                  case RESUME:
                     remote.resumeResourceGroup(ResourceGroupLifeCycleRuntime.this.partitionName, ResourceGroupLifeCycleRuntime.this.getName(), this.serverList);
                     break;
                  case SHUTDOWN:
                     remote.shutDownResourceGroup(ResourceGroupLifeCycleRuntime.this.partitionName, ResourceGroupLifeCycleRuntime.this.getName(), this.timeout, this.ignoreSessions, this.waitForAllSessions, this.serverList);
                     break;
                  case FORCE_SHUTDOWN:
                     remote.forceShutDownResourceGroup(ResourceGroupLifeCycleRuntime.this.partitionName, ResourceGroupLifeCycleRuntime.this.getName(), this.serverList);
               }
            } catch (Throwable var22) {
               if (ResourceGroupLifeCycleRuntime.DEBUG) {
                  ResourceGroupLifeCycleRuntime.debug("Exception while performing operation: " + this.operation.name() + " on the resource group " + ResourceGroupLifeCycleRuntime.this.getName() + " on the target " + this.serverName + " " + var22.getCause());
               }

               this.taskMBean.setError(new ResourceGroupLifecycleException(var22));
            } finally {
               ResourceGroupLifeCycleRuntime.updateTaskMBeanOnCompletion(this.taskMBean);
            }
         } catch (Throwable var24) {
            var2 = var24;
            throw var24;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var21) {
                     var2.addSuppressed(var21);
                  }
               } else {
                  mic.close();
               }
            }

         }

      }
   }
}
