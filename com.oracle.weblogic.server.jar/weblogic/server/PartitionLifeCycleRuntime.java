package weblogic.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.management.InstanceNotFoundException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.ManagementException;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.PartitionRuntimeStateManagerContract;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.PartitionLifeCycleRuntimeMBean;
import weblogic.management.runtime.PartitionLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.Operation;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.t3.srvr.PartitionLifecycleLogger;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManagerFactory;

public class PartitionLifeCycleRuntime extends DomainRuntimeMBeanDelegate implements PartitionLifeCycleRuntimeMBean {
   private final Set tasks;
   private final PartitionMBean partitionMBean;
   private final DomainMBean domain;
   private static final int TASK_AFTERLIFE_TIME_MILLIS = Integer.parseInt(System.getProperty("partitionlifecycle.task.afterlife.time", "1800000"));
   public static final String STATE_PROPERTY_NAME = "State";
   private final Set resourceGroups = new HashSet();
   private final boolean USE_OLD_STATE_NAMES;
   public static final boolean OLD_LIFECYCLE_MODEL = false;
   private static ComponentInvocationContext DOMAIN_CIC;
   private ComponentInvocationContext PARTITION_CIC;
   private final PropertyChangeListener rgListener;
   private final PropertyChangeListener configListener;
   private PartitionRuntimeMBean.State state;
   private final PartitionRuntimeStateManagerContract partitionRuntimeStateManager;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final DomainRuntimeServiceMBean domainRuntimeServiceMBean;
   private final ServerRuntimeMBean serverRuntime;
   private static final boolean DEBUG = PartitionLifecycleDebugger.isDebugEnabled();

   private static synchronized ComponentInvocationContext getDomainCIC() {
      if (DOMAIN_CIC == null) {
         DOMAIN_CIC = ComponentInvocationContextManager.getInstance().createComponentInvocationContext("DOMAIN");
      }

      return DOMAIN_CIC;
   }

   private synchronized ComponentInvocationContext getPartitionCIC() {
      if (this.PARTITION_CIC == null) {
         this.PARTITION_CIC = ComponentInvocationContextManager.getInstance(kernelId).createComponentInvocationContext(this.partitionMBean.getName());
      }

      return this.PARTITION_CIC;
   }

   public PartitionLifeCycleRuntime(PartitionMBean partitionMBean) throws ManagementException {
      super(partitionMBean.getName());
      this.USE_OLD_STATE_NAMES = PartitionRuntimeMBean.USE_OLD_STATE_NAMES;
      this.rgListener = new ResourceGroupLifeCycleChangeListener();
      this.configListener = new ConfigChangeListener();
      this.state = State.shutdownState();
      this.partitionRuntimeStateManager = (PartitionRuntimeStateManagerContract)GlobalServiceLocator.getServiceLocator().getService(PartitionRuntimeStateManagerContract.class, new Annotation[0]);
      this.domainRuntimeServiceMBean = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
      this.serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      this.tasks = Collections.synchronizedSet(new HashSet());
      this.partitionMBean = partitionMBean;
      this.domain = findDomain(partitionMBean);
      partitionMBean.addPropertyChangeListener(this.configListener);
      this.loadResourceGroups(partitionMBean);
   }

   public PartitionLifeCycleRuntime(PartitionMBean partitionMBean, DomainPartitionRuntimeMBean parentBean) throws ManagementException {
      super(partitionMBean.getName(), parentBean);
      this.USE_OLD_STATE_NAMES = PartitionRuntimeMBean.USE_OLD_STATE_NAMES;
      this.rgListener = new ResourceGroupLifeCycleChangeListener();
      this.configListener = new ConfigChangeListener();
      this.state = State.shutdownState();
      this.partitionRuntimeStateManager = (PartitionRuntimeStateManagerContract)GlobalServiceLocator.getServiceLocator().getService(PartitionRuntimeStateManagerContract.class, new Annotation[0]);
      this.domainRuntimeServiceMBean = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
      this.serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      parentBean.setPartitionLifeCycleRuntime(this);
      this.tasks = Collections.synchronizedSet(new HashSet());
      this.partitionMBean = partitionMBean;
      this.domain = findDomain(partitionMBean);
      partitionMBean.addPropertyChangeListener(this.configListener);
      this.loadResourceGroups(partitionMBean);
   }

   private static DomainMBean findDomain(PartitionMBean p) {
      return (DomainMBean)DomainMBean.class.cast(p.getParent());
   }

   private void loadResourceGroups(PartitionMBean partitionMBean) throws ManagementException {
   }

   public ResourceGroupLifeCycleRuntime createAndRegisterResourceGroupLifeCycle(ResourceGroupMBean rg) throws ManagementException {
      if (this.lookupResourceGroupLifeCycleRuntime(this.name) != null) {
         return null;
      } else {
         ResourceGroupLifeCycleRuntime rgLC = new ResourceGroupLifeCycleRuntime(rg, this, this.rgListener);
         this.resourceGroups.add(rgLC);
         return rgLC;
      }
   }

   public void unregister() throws ManagementException {
      super.unregister();
      if (this.partitionMBean != null) {
         this.partitionMBean.removePropertyChangeListener(this.configListener);
      }

   }

   public void unregisterResourceGroupLifeCycle(ResourceGroupMBean rg) throws ManagementException {
      Iterator it = this.resourceGroups.iterator();

      while(it.hasNext()) {
         ResourceGroupLifeCycleRuntime rgLC = (ResourceGroupLifeCycleRuntime)it.next();
         if (rgLC.getName().equals(rg.getName())) {
            rgLC.removePropertyChangeListener(this.rgListener);
            rgLC.unregister();
            it.remove();
            break;
         }
      }

   }

   public PartitionLifeCycleTaskRuntimeMBean start() throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.START, "Starting ", false);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean start(String initialState, int timeOut) throws PartitionLifeCycleException, InterruptedException {
      PartitionLifeCycleTaskRuntimeMBean retTask = null;
      TargetMBean[] targets = this.getAllTargetsForPartition();
      switch (State.valueOf(initialState)) {
         case ADMIN:
            retTask = this.initiateLifecycleRequest(targets, Operation.ADMIN, "Starting...", timeOut, true, false);
            break;
         case RUNNING:
            retTask = this.initiateLifecycleRequest(targets, Operation.START, "Starting...", timeOut, true, false);
            break;
         default:
            throw new PartitionLifeCycleException(String.format("Partition can only be started in \"%s\" or \"%s\" mode", State.ADMIN, State.RUNNING));
      }

      return retTask;
   }

   public PartitionLifeCycleTaskRuntime forceRestart() throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.FORCE_RESTART, "Force restarting ", false);
      return taskMBean;
   }

   public String getState() {
      return State.chooseUserDesiredStateName(this.getInternalState());
   }

   public String getSubState() {
      return State.chooseUserDesiredStateName(this.getInternalSubState());
   }

   public String getState(ServerMBean serverMBean) {
      if (!PartitionUtils.getServers(this.partitionMBean).contains(serverMBean.getName())) {
         throw new IllegalArgumentException(String.format("Server %s is not an effective target for partition %s.", serverMBean.getName(), this.getName()));
      } else {
         return State.chooseUserDesiredStateName(this.stateForServer(serverMBean.getName(), false));
      }
   }

   public String getSubState(ServerMBean serverMBean) {
      if (!PartitionUtils.getServers(this.partitionMBean).contains(serverMBean.getName())) {
         throw new IllegalArgumentException(String.format("Server %s is not an effective target for partition %s.", serverMBean.getName(), this.getName()));
      } else {
         return State.chooseUserDesiredStateName(this.stateForServer(serverMBean.getName(), true));
      }
   }

   public String getState(String serverName) {
      if (!PartitionUtils.getServers(this.partitionMBean).contains(serverName)) {
         throw new IllegalArgumentException(String.format("Server %s is not an effective target for partition %s.", serverName, this.getName()));
      } else {
         return State.chooseUserDesiredStateName(this.stateForServer(serverName, false));
      }
   }

   public String getSubState(String serverName) {
      if (!PartitionUtils.getServers(this.partitionMBean).contains(serverName)) {
         throw new IllegalArgumentException(String.format("Server %s is not an effective target for partition %s.", serverName, this.getName()));
      } else {
         return State.chooseUserDesiredStateName(this.stateForServer(serverName, true));
      }
   }

   public PartitionRuntimeMBean.State getInternalState() {
      return this.computeState(false);
   }

   public PartitionRuntimeMBean.State getInternalSubState() {
      return this.computeState(true);
   }

   private PartitionRuntimeMBean.State computeState(boolean isSubState) {
      StatePair bestStatePair = new StatePair(State.UNKNOWN, (PartitionRuntimeMBean.State)null);
      boolean isExplicitlyTargetedToAdminServer = this.isExplicitlyTargetedToAdminServer();
      Set servers = this.chooseServersForComputingState(isExplicitlyTargetedToAdminServer);
      if (servers.size() > 0) {
         Iterator var5 = servers.iterator();

         while(var5.hasNext()) {
            String server = (String)var5.next();
            ServerRuntimeMBean serverRuntimeMBean = this.domainRuntimeServiceMBean.lookupServerRuntime(server);
            PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntimeMBean(serverRuntimeMBean);
            StatePair statePair = new StatePair(this.stateForServer(server, partitionRuntimeMBean, false), this.stateForServer(server, partitionRuntimeMBean, true));
            if (DEBUG) {
               debug("Partition lifecycle state is : " + statePair + " on the server ... " + server);
            }

            if (statePair.compareTo(bestStatePair) > 0) {
               bestStatePair = statePair;
            }
         }

         if (!isExplicitlyTargetedToAdminServer) {
            bestStatePair = statePairMax(bestStatePair, this.statePairForImplicitlyTargetedAdminServer(bestStatePair));
            if (DEBUG) {
               debug("Partition lifecycle state for !isExplicitlyTargetedToAdminServer: " + bestStatePair + " on the adminServer");
            }
         }
      } else if (this.isAdminServerOnlyTarget()) {
         StatePair statePairForAdminServer = this.statePairForAdminServer();
         bestStatePair = statePairMax(bestStatePair, statePairForAdminServer);
         if (DEBUG) {
            debug("Partition lifecycle state for isAdminServerOnlyTarget : " + bestStatePair + " on the adminServer");
         }
      } else {
         String desiredState = this.partitionRuntimeStateManager.getPartitionState(this.partitionMBean.getName());
         if (desiredState == null) {
            desiredState = this.partitionRuntimeStateManager.getDefaultPartitionState();
         }

         bestStatePair = this.statePairFromPersistedState(desiredState);
      }

      if (DEBUG) {
         debug("Final Partition lifecycle state is: " + bestStatePair);
      }

      return isSubState ? bestStatePair.subState : bestStatePair.state;
   }

   private boolean isAdminServerOnlyTarget() {
      Set servers = PartitionUtils.getServers(this.partitionMBean);
      return servers.size() == 1 && ((String)servers.iterator().next()).equals(this.domain.getAdminServerName());
   }

   private Set chooseServersForComputingState(boolean includeAdminServer) {
      Set servers = PartitionUtils.getServers(this.partitionMBean);
      if (!includeAdminServer) {
         Iterator it = servers.iterator();

         while(it.hasNext()) {
            String serverName = (String)it.next();
            if (serverName.equals(this.domain.getAdminServerName())) {
               it.remove();
               break;
            }
         }
      }

      return servers;
   }

   private boolean isExplicitlyTargetedToAdminServer() {
      TargetMBean adminVT = this.partitionMBean.getAdminVirtualTarget();
      TargetMBean[] var2 = this.partitionMBean.findEffectiveTargets();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean target = var2[var4];
         if (!target.equals(adminVT) && target.getServerNames().contains(this.domain.getAdminServerName())) {
            return true;
         }
      }

      return false;
   }

   private StatePair statePairForAdminServer() {
      StatePair statePairForAdminServer = new StatePair(this.stateForServer(this.domain.getAdminServerName(), this.serverRuntime, false), this.stateForServer(this.domain.getAdminServerName(), this.serverRuntime, true));
      return statePairForAdminServer;
   }

   private StatePair statePairForImplicitlyTargetedAdminServer(StatePair bestStatePair) {
      return this.statePairForAdminServer().compareTo(bestStatePair) > 0 ? new StatePair(State.SHUTDOWN, State.BOOTED) : bestStatePair;
   }

   private PartitionRuntimeMBean.State stateForServer(String serverName, boolean isSubState) {
      return this.stateForServer(serverName, this.domainRuntimeServiceMBean.lookupServerRuntime(serverName), isSubState);
   }

   private PartitionRuntimeMBean.State stateForServer(String server, ServerRuntimeMBean svrRuntime, boolean isSubState) {
      if (DEBUG && svrRuntime == null) {
         debug(" Looks like JMX connectivity could not be established with the server or server is not up........ : " + server + " the serverRuntime is " + svrRuntime);
      }

      PartitionRuntimeMBean pRMbean = this.getPartitionRuntimeMBean(svrRuntime);
      return this.stateForServer(server, pRMbean, isSubState);
   }

   private PartitionRuntimeMBean.State stateForServer(String server, PartitionRuntimeMBean pRMbean, boolean isSubState) {
      PartitionRuntimeMBean.State result = !isSubState ? State.SHUTDOWN : State.HALTED;
      if (DEBUG) {
         debug(" The partition runtime bean on the server ........ : " + server + "   is " + pRMbean);
      }

      if (pRMbean != null) {
         try {
            result = State.normalize(pRMbean.getState());
            if (DEBUG) {
               debug(" The partition state on the server ........ : " + server + "   is " + result);
            }

            if (isSubState) {
               result = State.normalize(pRMbean.getSubState());
            }
         } catch (Throwable var6) {
            if (!(var6.getCause() instanceof InstanceNotFoundException)) {
               throw var6;
            }
         }
      }

      return result;
   }

   public void setState(PartitionRuntimeMBean.State newState) {
      PartitionRuntimeMBean.State oldState = this.state;
      this.state = newState;
      this._postSet("State", oldState, newState);
   }

   public void markStarted() {
      Iterator var1 = this.resourceGroups.iterator();

      while(var1.hasNext()) {
         ResourceGroupLifeCycleRuntime rgLC = (ResourceGroupLifeCycleRuntime)var1.next();
         rgLC.markStarted();
      }

      this.setState(State.RUNNING);
   }

   public PartitionLifeCycleTaskRuntimeMBean[] getTasks() {
      return (PartitionLifeCycleTaskRuntimeMBean[])this.tasks.toArray(new PartitionLifeCycleTaskRuntimeMBean[this.tasks.size()]);
   }

   public PartitionLifeCycleTaskRuntimeMBean lookupTask(String taskName) {
      Iterator it = this.tasks.iterator();

      PartitionLifeCycleTaskRuntimeMBean task;
      do {
         if (!it.hasNext()) {
            return null;
         }

         task = (PartitionLifeCycleTaskRuntimeMBean)it.next();
      } while(!task.getName().equals(taskName));

      return task;
   }

   public ResourceGroupLifeCycleRuntimeMBean[] getResourceGroupLifeCycleRuntimes() {
      return (ResourceGroupLifeCycleRuntimeMBean[])this.resourceGroups.toArray(new ResourceGroupLifeCycleRuntimeMBean[this.resourceGroups.size()]);
   }

   public Set resourceGroupLifeCycleRuntimes() {
      return this.resourceGroups;
   }

   public ResourceGroupLifeCycleRuntimeMBean lookupResourceGroupLifeCycleRuntime(String name) {
      Iterator var2 = this.resourceGroups.iterator();

      ResourceGroupLifeCycleRuntimeMBean rg;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         rg = (ResourceGroupLifeCycleRuntimeMBean)var2.next();
      } while(!rg.getName().equals(name));

      return rg;
   }

   public PartitionLifeCycleTaskRuntimeMBean start(TargetMBean[] targets) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(targets, Operation.START, "Starting ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean start(String[] targetNames) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), Operation.START, "Starting ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean startInAdmin(TargetMBean[] targets) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(targets, Operation.ADMIN, "Starting ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean startInAdmin(String[] targetNames) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), Operation.ADMIN, "Starting ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean startInAdmin() throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.ADMIN, "Starting ", false);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.SHUTDOWN, "Shutting down ", timeout, ignoreSessions, waitForAllSessions, false);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions, TargetMBean[] targets) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(targets, Operation.SHUTDOWN, "Shutting down ", timeout, ignoreSessions, waitForAllSessions, true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions, String[] targetNames) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), Operation.SHUTDOWN, "Shutting down ", timeout, ignoreSessions, waitForAllSessions, true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions) throws PartitionLifeCycleException {
      return this.shutdown(timeout, ignoreSessions, false);
   }

   public PartitionLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, TargetMBean[] targets) throws PartitionLifeCycleException {
      return this.shutdown(timeout, ignoreSessions, false, targets);
   }

   public PartitionLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, String[] targetNames) throws PartitionLifeCycleException {
      return this.shutdown(timeout, ignoreSessions, false, PartitionUtils.lookupTargetMBeans(targetNames));
   }

   public PartitionLifeCycleTaskRuntimeMBean shutdown(TargetMBean[] targets) throws PartitionLifeCycleException {
      return this.shutdown(0, false, (TargetMBean[])targets);
   }

   public PartitionLifeCycleTaskRuntimeMBean shutdown(String[] targetNames) throws PartitionLifeCycleException {
      return this.shutdown(0, false, (TargetMBean[])PartitionUtils.lookupTargetMBeans(targetNames));
   }

   public PartitionLifeCycleTaskRuntimeMBean shutdown() throws PartitionLifeCycleException {
      return this.shutdown(0, false);
   }

   public PartitionLifeCycleTaskRuntimeMBean forceShutdown(TargetMBean[] targets) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(targets, Operation.FORCE_SHUTDOWN, "Force Shutting down ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean forceShutdown(String[] targetNames) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), Operation.FORCE_SHUTDOWN, "Force Shutting down ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean forceShutdown() throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.FORCE_SHUTDOWN, "Force Shutting down ", false);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean forceShutdown(int timeout) throws PartitionLifeCycleException, InterruptedException {
      PartitionLifeCycleTaskRuntimeMBean retTask = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.FORCE_SHUTDOWN, "Force Shutting down ", timeout, true, false, false);
      return retTask;
   }

   public PartitionLifeCycleTaskRuntimeMBean suspend(int timeout, boolean ignoreSessions, TargetMBean[] targets) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(targets, Operation.SUSPEND, "Suspending  ", timeout, ignoreSessions, true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean suspend(int timeout, boolean ignoreSessions, String[] targetNames) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), Operation.SUSPEND, "Suspending  ", timeout, ignoreSessions, true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean suspend(int timeout, boolean ignoreSessions) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.SUSPEND, "Suspending  ", timeout, ignoreSessions, false);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean suspend(TargetMBean[] targets) throws PartitionLifeCycleException {
      return this.suspend(0, false, (TargetMBean[])targets);
   }

   public PartitionLifeCycleTaskRuntimeMBean suspend(String[] targetNames) throws PartitionLifeCycleException {
      return this.suspend(0, false, (TargetMBean[])PartitionUtils.lookupTargetMBeans(targetNames));
   }

   public PartitionLifeCycleTaskRuntimeMBean suspend() throws PartitionLifeCycleException {
      return this.suspend(0, false);
   }

   public PartitionLifeCycleTaskRuntimeMBean forceSuspend(TargetMBean[] targets) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(targets, Operation.FORCE_SUSPEND, "Force suspending  ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean forceSuspend(String[] targetNames) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), Operation.FORCE_SUSPEND, "Force suspending  ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean forceSuspend() throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.FORCE_SUSPEND, "Force suspending  ", false);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean resume(TargetMBean[] targets) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(targets, Operation.RESUME, "Resuming  ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean resume(String[] targetNames) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), Operation.RESUME, "Resuming  ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean resume() throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.RESUME, "Resuming  ", false);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean halt(TargetMBean[] targets) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(targets, Operation.HALT, "Halting  ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean halt(String[] targetNames) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), Operation.HALT, "Halting  ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean halt() throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.HALT, "Halting  ", false);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean boot(TargetMBean[] targets) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(targets, Operation.BOOT, "Booting  ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean boot(String[] targetNames) throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(PartitionUtils.lookupTargetMBeans(targetNames), Operation.BOOT, "Booting  ", true);
      return taskMBean;
   }

   public PartitionLifeCycleTaskRuntimeMBean boot() throws PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskMBean = this.initiateLifecycleRequest(this.getAllTargetsForPartition(), Operation.BOOT, "Booting  ", false);
      return taskMBean;
   }

   private TargetMBean[] getAllTargetsForPartition() {
      return this.partitionMBean.findEffectiveTargets();
   }

   public void purgeTasks() {
   }

   public PartitionMBean getPartitionMBean() {
      return this.partitionMBean;
   }

   void findAddedAndRemoved(Object[] originalArr, Object[] updatedArr, Set added, Set removed) {
      Set original = new HashSet(Arrays.asList(originalArr));
      Set updated = new HashSet(Arrays.asList(updatedArr));
      removed.clear();
      removed.addAll(original);
      removed.removeAll(updated);
      added.clear();
      added.addAll(updated);
      added.removeAll(original);
   }

   private PartitionLifeCycleTaskRuntime createTaskMbean(PartitionRuntimeMBean.Operation operation, String operationMsg, String serverName) throws ManagementException {
      if (DEBUG) {
         debug("Partition lifecycle task " + operation);
      }

      return new PartitionLifeCycleTaskRuntime(this, operationMsg, operation, serverName);
   }

   private PartitionLifeCycleTaskRuntime initiateLifecycleRequest(TargetMBean[] targets, PartitionRuntimeMBean.Operation operation, String msg, boolean targetsSpecified) throws PartitionLifeCycleException {
      return this.initiateLifecycleRequest(targets, operation, msg, 0, true, false, targetsSpecified);
   }

   private PartitionLifeCycleTaskRuntime initiateLifecycleRequest(TargetMBean[] targets, PartitionRuntimeMBean.Operation operation, String msg, int timeout, boolean ignoreSessions, boolean targetsSpecified) throws PartitionLifeCycleException {
      return this.initiateLifecycleRequest(targets, operation, msg, timeout, ignoreSessions, false, targetsSpecified);
   }

   private PartitionLifeCycleTaskRuntime initiateLifecycleRequest(TargetMBean[] targets, PartitionRuntimeMBean.Operation operation, String msg, int timeout, boolean ignoreSessions, boolean waitForAllSessions, boolean targetsSpecified) throws PartitionLifeCycleException {
      PartitionUtils.validateTargetsWithPartition(this.partitionMBean, targets);
      Set specificTargets = PartitionUtils.getSpecificServerNames(targets);
      List subTasksList = new LinkedList();
      Set requests = new HashSet();
      String[] serverList = this.getSvrsForSettingDesiredState(specificTargets, targetsSpecified);
      StringBuilder servers = new StringBuilder();
      this.clearOldPartitionLifeCycleTaskRuntimes();

      try {
         Iterator var13 = specificTargets.iterator();

         while(var13.hasNext()) {
            String serverName = (String)var13.next();
            if (this.isServerActionNeeded(operation, serverName, operation.nextSuccessState)) {
               servers.append(serverName).append(",");
               PartitionLifeCycleTaskRuntime subTaskMBean = this.createTaskMbean(operation, msg + this.getName() + " ... on the " + serverName + " server ...", serverName);
               if (DEBUG) {
                  debug("Created subtask :" + subTaskMBean);
               }

               subTasksList.add(subTaskMBean);
               PartitionLifecycleRequest request = new PartitionLifecycleRequest(timeout, ignoreSessions, waitForAllSessions, serverName, subTaskMBean, operation, serverList);
               requests.add(request);
            } else if (DEBUG) {
               debug("No action needed on server " + serverName + " for operation " + operation.name());
            }
         }
      } catch (ManagementException var34) {
         throw new PartitionLifeCycleException(var34);
      }

      PartitionRuntimeMBean.State state = this.getInternalState();
      PartitionRuntimeMBean.State subState = this.getInternalSubState();
      String serversSr = servers.length() > 0 ? servers.substring(0, servers.length() - 1) : "";

      try {
         ManagedInvocationContext mic = ComponentInvocationContextManager.getInstance(kernelId).setCurrentComponentInvocationContext(getDomainCIC());
         Throwable var17 = null;

         PartitionLifeCycleTaskRuntime var39;
         try {
            PartitionLifeCycleTaskRuntime taskMBean;
            if (subTasksList.size() > 0) {
               TaskRuntimeMBean[] subTaskArray = (TaskRuntimeMBean[])subTasksList.toArray(new TaskRuntimeMBean[subTasksList.size()]);
               if (DEBUG) {
                  debug("The subtasks length :" + subTaskArray.length);
               }

               taskMBean = this.createTaskMbean(operation, msg + this.getName() + " on servers " + serversSr + " ... ", serversSr);
               taskMBean.registerSubTasks(subTaskArray);
               if (servers.length() > 0) {
                  PartitionLifecycleLogger.initiateOperationOnServers(operation.name(), this.getName(), serversSr);
               }

               Iterator var20 = requests.iterator();

               while(var20.hasNext()) {
                  PartitionLifecycleRequest request = (PartitionLifecycleRequest)var20.next();
                  WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
               }
            } else if (specificTargets.isEmpty()) {
               taskMBean = this.alreadyCompletedTask(operation);
               PartitionLifecycleLogger.logPartitionOperationNoTarget(operation.name(), this.getName());
            } else {
               taskMBean = this.alreadyCompletedTask(operation);
               PartitionLifecycleLogger.logPartitionAlreadyExpectedState(operation.name(), this.getName(), this.getState());
            }

            this.tasks.add(taskMBean);
            this.updateStoreForDesiredState(operation, targets, targetsSpecified, state, subState);
            var39 = taskMBean;
         } catch (Throwable var31) {
            var17 = var31;
            throw var31;
         } finally {
            if (mic != null) {
               if (var17 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var30) {
                     var17.addSuppressed(var30);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var39;
      } catch (ManagementException var33) {
         throw new PartitionLifeCycleException(var33);
      }
   }

   static boolean shouldUpdatePersistedState(PartitionRuntimeMBean.Operation operation, PartitionRuntimeMBean.State currentState, PartitionRuntimeMBean.State currentSubstate) {
      if (operation == Operation.FORCE_RESTART) {
         return false;
      } else {
         StatePair current = new StatePair(currentState, currentSubstate);
         StatePair opResult = new StatePair(operation.nextSuccessState, operation.successSubState);
         boolean result = (!Operation.isTeardownOperation(operation) || current.compareTo(opResult) > 0) && (!Operation.isSetupOperation(operation) || current.compareTo(opResult) < 0);
         if (DEBUG) {
            debug("shouldUpdatePersistedState: The partition is in state " + current.toString() + " so with operation " + operation.name() + " the persisted state will " + (result ? "" : "not") + " be updated");
         }

         return result;
      }
   }

   private static boolean isHalted(PartitionRuntimeMBean.State state, PartitionRuntimeMBean.State substate) {
      return State.isShutdown(state) && State.isShutdownHalted(substate);
   }

   private PartitionLifeCycleTaskRuntime alreadyCompletedTask(PartitionRuntimeMBean.Operation operation) throws ManagementException, PartitionLifeCycleException {
      PartitionLifeCycleTaskRuntime taskRuntime = PartitionLifeCycleTaskRuntime.preCompleted(this, operation.toString() + " " + this.getName() + " ...", operation);
      return taskRuntime;
   }

   private static void updateTaskMBeanOnCompletion(PartitionLifeCycleTaskRuntime plcTaskRuntime, String serverName) {
      if (DEBUG) {
         debug("setting task status" + plcTaskRuntime.getName());
      }

      if (plcTaskRuntime.getError() != null) {
         plcTaskRuntime.setStatus(PartitionLifeCycleTaskRuntime.Status.FAILED.toString());
      } else {
         plcTaskRuntime.setStatus(PartitionLifeCycleTaskRuntime.Status.SUCCEEDED.toString());
         if (DEBUG) {
            debug("Task succeeded");
         }
      }

      plcTaskRuntime.setEndTime(System.currentTimeMillis());
      plcTaskRuntime.setIsRunning(false);
   }

   public void clearOldPartitionLifeCycleTaskRuntimes() {
      Iterator iter = this.tasks.iterator();

      while(true) {
         PartitionLifeCycleTaskRuntime task;
         do {
            do {
               if (!iter.hasNext()) {
                  return;
               }

               task = (PartitionLifeCycleTaskRuntime)iter.next();
            } while(task.getEndTime() <= 0L);
         } while(System.currentTimeMillis() - task.getEndTime() <= (long)TASK_AFTERLIFE_TIME_MILLIS);

         try {
            if (DEBUG) {
               debug("Unregistering task: " + task.getName());
            }

            task.unregister();
         } catch (ManagementException var7) {
            if (DEBUG) {
               debug("An exception occured while unregistering task: " + var7.getMessage());
            }

            var7.printStackTrace();
         } finally {
            iter.remove();
         }
      }
   }

   private PartitionRuntimeMBean getPartitionRuntimeMBean(ServerRuntimeMBean svrRuntime) {
      return svrRuntime == null ? null : svrRuntime.lookupPartitionRuntime(this.getName());
   }

   private boolean isServerActionNeeded(PartitionRuntimeMBean.Operation operation, String server, PartitionRuntimeMBean.State state) throws PartitionLifeCycleException {
      ServerRuntimeMBean svrRuntime = this.domainRuntimeServiceMBean.lookupServerRuntime(server);
      PartitionRuntimeMBean pRMbean = this.getPartitionRuntimeMBean(svrRuntime);
      PartitionRuntimeMBean.State pState = this.stateForServer(server, pRMbean, false);
      PartitionRuntimeMBean.State pSubState = this.stateForServer(server, pRMbean, true);
      if (DEBUG) {
         debug("The partition runtime state is :" + (pRMbean == null ? "not found" : pState.name()));
      }

      if (pRMbean != null) {
         if (operation == Operation.FORCE_RESTART) {
            if (DEBUG) {
               debug("The partition is being forcefully restarted on every target including " + server);
            }

            return true;
         }

         if (pState == operation.nextSuccessState && (operation.successSubState == null || pSubState == operation.successSubState)) {
            if (DEBUG) {
               debug("The partition is already in expected state : " + state + " on the target " + server);
            }

            return false;
         }

         if (operation == Operation.BOOT) {
            if (DEBUG) {
               debug("The partition is already active in state " + state + " on the target " + server + " so no action needed for the BOOT operation");
            }

            return false;
         }
      } else {
         if (operation != Operation.BOOT && State.isShutdown(state)) {
            if (DEBUG) {
               debug("The next state is SHUTDOWN, the partition runtime MBean does not exist, and the operation is not BOOT, so no action needed");
            }

            return false;
         }

         if (operation == Operation.FORCE_RESTART) {
            if (DEBUG) {
               debug("The partition is not active on " + server + " so no action is needed for a FORCE_RESTART");
            }

            return false;
         }

         if (Operation.isTeardownOperation(operation)) {
            if (DEBUG) {
               debug("The partition is already fully rundown on server " + server + " so the operation " + operation + " is ignored");
            }

            return false;
         }
      }

      return operation != Operation.BOOT || PartitionUtils.isAdminRelatedActionNeeded(this.partitionMBean.getName(), svrRuntime);
   }

   private void updateStoreForDesiredState(PartitionRuntimeMBean.Operation operation, TargetMBean[] targets, boolean targetsSpecified, PartitionRuntimeMBean.State originalState, PartitionRuntimeMBean.State originalSubState) throws PartitionLifeCycleException {
      if (operation == Operation.FORCE_RESTART) {
         if (DEBUG) {
            debug("Skipping update of desired state for operation " + operation.name() + ": there is no net change in state");
         }

      } else if (!targetsSpecified && !shouldUpdatePersistedState(operation, originalState, originalSubState)) {
         if (DEBUG) {
            debug("Skipping update of desired state for operation " + operation.name() + ": ");
         }

      } else {
         PartitionRuntimeMBean.State state = State.getLowestState(operation);
         Set serverNames = PartitionUtils.getSpecificServerNames(targets);
         String[] servers = targetsSpecified ? this.filterServersForRecordingStateChange(operation, serverNames) : new String[0];
         if (servers.length == 0 && targetsSpecified) {
            if (DEBUG) {
               debug("Skipping update of desired state for operation " + operation.name() + " and caller-specified servers " + Arrays.toString(servers) + ": no servers' states needs to be updated");
            }

         } else {
            if (DEBUG) {
               debug("Updating stored desired state for partition " + this.getName() + " to " + state.name() + "; candidate servers: " + serverNames.toString() + " (specified by caller = " + targetsSpecified + "); servers for which persisted state being updated: " + Arrays.toString(servers));
            }

            try {
               String newDesiredState = state.toString();
               this.partitionRuntimeStateManager.setPartitionState(this.getName(), newDesiredState, servers);
            } catch (ManagementException var14) {
               throw new PartitionLifeCycleException(var14);
            }

            ServerRuntimeMBean[] var15 = this.domainRuntimeServiceMBean.getServerRuntimes();
            int var10 = var15.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               ServerRuntimeMBean serverRuntime = var15[var11];
               PersistDesiredState request = new PersistPartitionDesiredState(state.name(), serverRuntime.getName(), servers);
               WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
            }

         }
      }
   }

   private String[] filterServersForRecordingStateChange(PartitionRuntimeMBean.Operation operation, Set serverNames) {
      Iterator it = serverNames.iterator();

      while(it.hasNext()) {
         String serverName = (String)it.next();
         PartitionRuntimeMBean.State state = State.valueOf(this.partitionRuntimeStateManager.getPartitionState(this.getName(), serverName));
         PartitionRuntimeMBean.State mainState = State.chooseState(state, false);
         PartitionRuntimeMBean.State subState = State.chooseState(state, true);
         if (!shouldUpdatePersistedState(operation, mainState, subState)) {
            it.remove();
         }
      }

      return (String[])serverNames.toArray(new String[serverNames.size()]);
   }

   private static StatePair statePairMax(StatePair a, StatePair b) {
      return a.compareTo(b) >= 0 ? a : b;
   }

   private StatePair statePairFromPersistedState(String desiredStateName) {
      PartitionRuntimeMBean.State desiredState = State.valueOf(desiredStateName);
      return new StatePair(State.chooseState(desiredState, false), State.chooseState(desiredState, true));
   }

   private String[] getSvrsForSettingDesiredState(Set targets, boolean targetsSpecified) {
      return targetsSpecified ? (String[])targets.toArray(new String[targets.size()]) : new String[0];
   }

   private static void debug(String debugMessage) {
      PartitionLifecycleDebugger.debug("<PartitionLifecycleRuntime> " + debugMessage);
   }

   private static class StatePair {
      private PartitionRuntimeMBean.State state;
      private PartitionRuntimeMBean.State subState;

      StatePair(PartitionRuntimeMBean.State state, PartitionRuntimeMBean.State subState) {
         this.state = state;
         this.subState = this.validSubState(state, subState);
      }

      private PartitionRuntimeMBean.State validSubState(PartitionRuntimeMBean.State state, PartitionRuntimeMBean.State subState) {
         PartitionRuntimeMBean.State validSubState = null;
         if (State.isShutdown(state)) {
            if (State.isAnyShutdownState(subState)) {
               validSubState = subState;
            } else {
               PartitionLifecycleLogger.fixingInconsistentStateSubState(state.name(), subState == null ? "null" : subState.name(), validSubState == null ? "null" : validSubState.name());
               validSubState = State.HALTED;
            }
         } else if (state == subState) {
            validSubState = null;
         } else if (subState != null) {
            PartitionLifecycleLogger.fixingInconsistentStateSubState(state.name(), subState.name(), "null");
         }

         return validSubState;
      }

      int compareTo(StatePair other) {
         int stateComparison = this.state.compareTo(other.state);
         if (stateComparison != 0) {
            return stateComparison;
         } else if (this.subState != null) {
            return this.subState.compareTo(other.subState);
         } else {
            return other.subState != null ? -1 : 0;
         }
      }

      public String toString() {
         return this.state.name() + (this.subState == null ? "" : "/" + this.subState.name());
      }

      String toString(boolean isSubState) {
         return isSubState ? this.subState.name() : this.state.name();
      }
   }

   private final class PersistPartitionDesiredState extends PersistDesiredState {
      PersistPartitionDesiredState(String desiredState, String serverName, String... serversAffected) {
         super(desiredState, serverName, serversAffected);
      }

      public void run() {
         ManagedInvocationContext mic = ComponentInvocationContextManager.getInstance(PartitionLifeCycleRuntime.kernelId).setCurrentComponentInvocationContext(PartitionLifeCycleRuntime.this.getPartitionCIC());
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

      protected void saveDesiredState() throws PartitionLifeCycleException, RemoteException {
         this.remote.setDesiredPartitionState(PartitionLifeCycleRuntime.this.partitionMBean.getName(), this.desiredState, this.serversAffected);
      }
   }

   private final class PartitionLifecycleRequest implements Runnable {
      private final PartitionLifeCycleTaskRuntime taskMBean;
      private final String serverName;
      private final int timeout;
      private final boolean ignoreSessions;
      private final PartitionRuntimeMBean.Operation operation;
      private final boolean waitForAllSessions;
      private final String[] serverList;

      PartitionLifecycleRequest(int timeout2, boolean ignoreSessions, boolean waitForAllSessions, String serverName, PartitionLifeCycleTaskRuntime taskMBean, PartitionRuntimeMBean.Operation operation, String[] serverList) {
         this.timeout = timeout2;
         this.ignoreSessions = ignoreSessions;
         this.taskMBean = taskMBean;
         this.serverName = serverName;
         this.operation = operation;
         this.waitForAllSessions = waitForAllSessions;
         this.serverList = serverList;
      }

      public void run() {
         ManagedInvocationContext mic = ComponentInvocationContextManager.getInstance(PartitionLifeCycleRuntime.kernelId).setCurrentComponentInvocationContext(PartitionLifeCycleRuntime.this.getPartitionCIC());
         Throwable var2 = null;

         try {
            long responseTime = ServerLifeCycleRuntime.computeTimeOut(this.operation == Operation.SHUTDOWN, (long)this.timeout, (long)PartitionLifeCycleRuntime.this.partitionMBean.getPartitionLifeCycleTimeoutVal(), (long)PartitionLifeCycleRuntime.this.partitionMBean.getGracefulShutdownTimeout());

            try {
               RemoteLifeCycleOperations remote = ServerLifeCycleRuntime.getLifeCycleOperationsRemote(this.serverName, responseTime);
               if (remote == null) {
                  throw new PartitionLifeCycleException("Can not get to the relevant ServerRuntimeMBean");
               }

               switch (this.operation) {
                  case START:
                     remote.startPartition(PartitionLifeCycleRuntime.this.getName(), PartitionLifeCycleRuntime.this.partitionMBean.getPartitionID(), false, this.serverList);
                     break;
                  case ADMIN:
                     remote.startPartition(PartitionLifeCycleRuntime.this.getName(), PartitionLifeCycleRuntime.this.partitionMBean.getPartitionID(), true, this.serverList);
                     break;
                  case SUSPEND:
                     remote.suspendPartition(PartitionLifeCycleRuntime.this.getName(), this.timeout, this.ignoreSessions, this.serverList);
                     break;
                  case FORCE_SUSPEND:
                     remote.forceSuspendPartition(PartitionLifeCycleRuntime.this.getName(), this.serverList);
                     break;
                  case RESUME:
                     remote.resumePartition(PartitionLifeCycleRuntime.this.getName(), this.serverList);
                     break;
                  case SHUTDOWN:
                     remote.shutDownPartition(PartitionLifeCycleRuntime.this.getName(), this.timeout, this.ignoreSessions, this.waitForAllSessions, this.serverList);
                     break;
                  case FORCE_SHUTDOWN:
                     remote.forceShutDownPartition(PartitionLifeCycleRuntime.this.getName(), this.serverList);
                     break;
                  case BOOT:
                     remote.bootPartition(PartitionLifeCycleRuntime.this.getName(), PartitionLifeCycleRuntime.this.partitionMBean.getPartitionID(), this.serverList);
                     break;
                  case HALT:
                     remote.haltPartition(PartitionLifeCycleRuntime.this.getName(), PartitionLifeCycleRuntime.this.partitionMBean.getPartitionID(), this.serverList);
                     break;
                  case FORCE_RESTART:
                     remote.forceRestartPartition(PartitionLifeCycleRuntime.this.getName(), PartitionLifeCycleRuntime.this.partitionMBean.getPartitionID(), this.serverList);
               }
            } catch (Throwable var21) {
               if (PartitionLifeCycleRuntime.DEBUG) {
                  PartitionLifeCycleRuntime.debug("Exception while performing operation: " + this.operation.name() + " on the partition " + PartitionLifeCycleRuntime.this.getName() + " on the target " + this.serverName + " " + var21.getCause());
               }

               this.taskMBean.setError(new PartitionLifeCycleException(var21));
            } finally {
               PartitionLifeCycleRuntime.updateTaskMBeanOnCompletion(this.taskMBean, this.serverName);
            }
         } catch (Throwable var23) {
            var2 = var23;
            throw var23;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var20) {
                     var2.addSuppressed(var20);
                  }
               } else {
                  mic.close();
               }
            }

         }

      }
   }

   private class ResourceGroupLifeCycleChangeListener implements PropertyChangeListener {
      private ResourceGroupLifeCycleChangeListener() {
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if (evt.getPropertyName().equals("state") && evt.getSource() instanceof ResourceGroupLifeCycleRuntime) {
            PartitionRuntimeMBean.State originalState = PartitionLifeCycleRuntime.this.state;
            PartitionRuntimeMBean.State provisionalNewState = originalState;
            Set rgStates = new HashSet();
            Iterator var5 = PartitionLifeCycleRuntime.this.resourceGroups.iterator();

            while(var5.hasNext()) {
               ResourceGroupLifeCycleRuntime rg = (ResourceGroupLifeCycleRuntime)var5.next();
               rgStates.add(rg.state());
            }

            if (originalState == State.STARTING) {
               if (rgStates.contains(RGState.STARTING) || rgStates.contains(RGState.shutdownState())) {
                  return;
               }

               if (rgStates.contains(RGState.shuttingDownState())) {
                  provisionalNewState = State.UNKNOWN;
               } else {
                  provisionalNewState = State.runningState();
               }
            } else if (originalState == State.shuttingDownState()) {
               if (rgStates.contains(RGState.shuttingDownState()) || rgStates.contains(RGState.runningState())) {
                  return;
               }

               if (rgStates.contains(RGState.STARTING)) {
                  provisionalNewState = State.UNKNOWN;
               } else {
                  provisionalNewState = State.shutdownState();
               }
            }

            if (provisionalNewState != originalState) {
               PartitionLifeCycleRuntime.this.setState(provisionalNewState);
            }

         }
      }

      // $FF: synthetic method
      ResourceGroupLifeCycleChangeListener(Object x1) {
         this();
      }
   }

   private class ConfigChangeListener implements PropertyChangeListener {
      private ConfigChangeListener() {
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if (evt.getSource() == PartitionLifeCycleRuntime.this.partitionMBean && evt.getPropertyName().equals("ResourceGroups")) {
            Set removedRGs = new HashSet();
            Set addedRGs = new HashSet();
            PartitionLifeCycleRuntime.this.findAddedAndRemoved((ResourceGroupMBean[])((ResourceGroupMBean[])evt.getOldValue()), (ResourceGroupMBean[])((ResourceGroupMBean[])evt.getNewValue()), addedRGs, removedRGs);

            try {
               Iterator var4 = addedRGs.iterator();

               ResourceGroupMBean rg;
               while(var4.hasNext()) {
                  rg = (ResourceGroupMBean)var4.next();
                  ResourceGroupLifeCycleRuntime rgLC = PartitionLifeCycleRuntime.this.createAndRegisterResourceGroupLifeCycle(rg);
                  if (rgLC != null) {
                     rgLC.markStarted();
                  }
               }

               var4 = removedRGs.iterator();

               while(var4.hasNext()) {
                  rg = (ResourceGroupMBean)var4.next();
                  PartitionLifeCycleRuntime.this.unregisterResourceGroupLifeCycle(rg);
               }
            } catch (ManagementException var7) {
               throw new RuntimeException(var7);
            }
         }

      }

      // $FF: synthetic method
      ConfigChangeListener(Object x1) {
         this();
      }
   }
}
