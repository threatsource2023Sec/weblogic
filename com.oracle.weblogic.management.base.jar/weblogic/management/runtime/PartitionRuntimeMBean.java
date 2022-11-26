package weblogic.management.runtime;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.health.HealthState;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;

public interface PartitionRuntimeMBean extends RuntimeMBean {
   boolean USE_OLD_STATE_NAMES = Boolean.valueOf(System.getProperty("partitionlifecycle.useOldStateNames", "false"));

   String getName();

   String getServerName();

   String getState();

   String getSubState();

   State getInternalState();

   State getInternalSubState();

   State getPrevInternalState();

   State getPrevInternalSubState();

   String getPartitionID();

   String getSystemFileSystemRoot();

   String getUserFileSystemRoot();

   JMSRuntimeMBean getJMSRuntime();

   void setJMSRuntime(JMSRuntimeMBean var1);

   JDBCPartitionRuntimeMBean getJDBCPartitionRuntime();

   void setJDBCPartitionRuntime(JDBCPartitionRuntimeMBean var1);

   ApplicationRuntimeMBean[] getApplicationRuntimes();

   ApplicationRuntimeMBean lookupApplicationRuntime(String var1);

   LibraryRuntimeMBean[] getLibraryRuntimes();

   LibraryRuntimeMBean lookupLibraryRuntime(String var1);

   WorkManagerRuntimeMBean[] getWorkManagerRuntimes();

   boolean addWorkManagerRuntime(WorkManagerRuntimeMBean var1);

   boolean removeWorkManagerRuntime(WorkManagerRuntimeMBean var1);

   PartitionWorkManagerRuntimeMBean getPartitionWorkManagerRuntime();

   void setPartitionWorkManagerRuntime(PartitionWorkManagerRuntimeMBean var1);

   MinThreadsConstraintRuntimeMBean lookupMinThreadsConstraintRuntime(String var1);

   RequestClassRuntimeMBean lookupRequestClassRuntime(String var1);

   MaxThreadsConstraintRuntimeMBean lookupMaxThreadsConstraintRuntime(String var1);

   boolean addMaxThreadsConstraintRuntime(MaxThreadsConstraintRuntimeMBean var1);

   boolean addMinThreadsConstraintRuntime(MinThreadsConstraintRuntimeMBean var1);

   boolean addRequestClassRuntime(RequestClassRuntimeMBean var1);

   MaxThreadsConstraintRuntimeMBean[] getMaxThreadsConstraintRuntimes();

   MinThreadsConstraintRuntimeMBean[] getMinThreadsConstraintRuntimes();

   RequestClassRuntimeMBean[] getRequestClassRuntimes();

   MailSessionRuntimeMBean[] getMailSessionRuntimes();

   boolean addMailSessionRuntime(MailSessionRuntimeMBean var1);

   boolean removeMailSessionRuntime(MailSessionRuntimeMBean var1);

   MessagingBridgeRuntimeMBean[] getMessagingBridgeRuntimes();

   boolean addMessagingBridgeRuntime(MessagingBridgeRuntimeMBean var1);

   boolean removeMessagingBridgeRuntime(MessagingBridgeRuntimeMBean var1);

   MessagingBridgeRuntimeMBean lookupMessagingBridgeRuntime(String var1);

   PersistentStoreRuntimeMBean[] getPersistentStoreRuntimes();

   PersistentStoreRuntimeMBean lookupPersistentStoreRuntime(String var1);

   void addPersistentStoreRuntime(PersistentStoreRuntimeMBean var1);

   void removePersistentStoreRuntime(PersistentStoreRuntimeMBean var1);

   ConnectorServiceRuntimeMBean getConnectorServiceRuntime();

   void setConnectorServiceRuntime(ConnectorServiceRuntimeMBean var1);

   /** @deprecated */
   @Deprecated
   PathServiceRuntimeMBean getPathServiceRuntime();

   /** @deprecated */
   @Deprecated
   void setPathServiceRuntime(PathServiceRuntimeMBean var1);

   PathServiceRuntimeMBean[] getPathServiceRuntimes();

   boolean addPathServiceRuntime(PathServiceRuntimeMBean var1, boolean var2);

   boolean removePathServiceRuntime(PathServiceRuntimeMBean var1, boolean var2);

   SAFRuntimeMBean getSAFRuntime();

   void setSAFRuntime(SAFRuntimeMBean var1);

   WLDFPartitionRuntimeMBean getWLDFPartitionRuntime();

   void setWLDFPartitionRuntime(WLDFPartitionRuntimeMBean var1);

   ConcurrentManagedObjectsRuntimeMBean getConcurrentManagedObjectsRuntime();

   void setConcurrentManagedObjectsRuntime(ConcurrentManagedObjectsRuntimeMBean var1);

   JTAPartitionRuntimeMBean getJTAPartitionRuntime();

   void setJTAPartitionRuntime(JTAPartitionRuntimeMBean var1);

   PartitionResourceMetricsRuntimeMBean getPartitionResourceMetricsRuntime();

   void setPartitionResourceMetricsRuntime(PartitionResourceMetricsRuntimeMBean var1);

   BatchJobRepositoryRuntimeMBean getBatchJobRepositoryRuntime();

   void setBatchJobRepositoryRuntime(BatchJobRepositoryRuntimeMBean var1);

   void suspend(int var1, boolean var2) throws PartitionLifeCycleException;

   void suspend() throws PartitionLifeCycleException;

   void forceSuspend() throws PartitionLifeCycleException;

   void resume() throws PartitionLifeCycleException;

   void shutdown(int var1, boolean var2, boolean var3) throws PartitionLifeCycleException;

   void shutdown(int var1, boolean var2) throws PartitionLifeCycleException;

   void shutdown() throws PartitionLifeCycleException;

   void halt() throws PartitionLifeCycleException;

   void forceShutdown() throws PartitionLifeCycleException;

   void startResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void startResourceGroupInAdmin(String var1) throws ResourceGroupLifecycleException;

   void suspendResourceGroup(String var1, int var2, boolean var3) throws ResourceGroupLifecycleException;

   void suspendResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void forceSuspendResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void resumeResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void forceShutdownResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void shutdownResourceGroup(String var1, int var2, boolean var3, boolean var4) throws ResourceGroupLifecycleException;

   void shutdownResourceGroup(String var1, int var2, boolean var3) throws ResourceGroupLifecycleException;

   void shutdownResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void setState(State var1);

   void setSubState(State var1);

   String getRgState(String var1) throws ResourceGroupLifecycleException;

   void setRgState(String var1, ResourceGroupLifecycleOperations.RGState var2) throws ResourceGroupLifecycleException;

   ResourceGroupLifecycleOperations.RGState getInternalRgState(String var1) throws ResourceGroupLifecycleException;

   void setWseeClusterFrontEndRuntime(WseeClusterFrontEndRuntimeMBean var1);

   WseeClusterFrontEndRuntimeMBean getWseeClusterFrontEndRuntime();

   HealthState getOverallHealthState();

   CompositeData getOverallHealthStateJMX() throws OpenDataException;

   void setRestartRequired(boolean var1);

   boolean isRestartRequired();

   boolean addPendingRestartResourceMBean(ConfigurationMBean var1);

   boolean removePendingRestartResourceMBean(ConfigurationMBean var1);

   boolean isRestartPendingForResourceMBean(ConfigurationMBean var1);

   ConfigurationMBean[] getPendingRestartResourceMBeans();

   boolean removePendingRestartResource(String var1);

   boolean isRestartPendingForResource(String var1);

   String[] getPendingRestartResources();

   boolean addPendingRestartSystemResource(String var1);

   boolean removePendingRestartSystemResource(String var1);

   boolean isRestartPendingForSystemResource(String var1);

   String[] getPendingRestartSystemResources();

   HealthState[] getSubsystemHealthStates();

   CompositeData[] getSubsystemHealthStatesJMX() throws OpenDataException;

   String[] urlMappingForVT(String var1, String var2);

   public static enum Operation {
      BOOT(PartitionRuntimeMBean.State.BOOTING, PartitionRuntimeMBean.State.SHUTDOWN, PartitionRuntimeMBean.State.BOOTED, PartitionRuntimeMBean.State.UNKNOWN, Collections.EMPTY_SET),
      START(PartitionRuntimeMBean.State.STARTING, PartitionRuntimeMBean.State.RUNNING, (State)null, PartitionRuntimeMBean.State.UNKNOWN, Collections.EMPTY_SET),
      ADMIN(PartitionRuntimeMBean.State.STARTING_IN_ADMIN, PartitionRuntimeMBean.State.ADMIN, (State)null, PartitionRuntimeMBean.State.UNKNOWN, Collections.EMPTY_SET),
      /** @deprecated */
      @Deprecated
      STOP(PartitionRuntimeMBean.State.SHUTTING_DOWN, PartitionRuntimeMBean.State.SHUTDOWN, PartitionRuntimeMBean.State.BOOTED, PartitionRuntimeMBean.State.UNKNOWN, Collections.EMPTY_SET),
      FORCE_SHUTDOWN(PartitionRuntimeMBean.State.FORCE_SHUTTING_DOWN, PartitionRuntimeMBean.State.SHUTDOWN, PartitionRuntimeMBean.State.BOOTED, PartitionRuntimeMBean.State.UNKNOWN, Collections.EMPTY_SET),
      FORCE_SUSPEND(PartitionRuntimeMBean.State.FORCE_SUSPENDING, PartitionRuntimeMBean.State.ADMIN, (State)null, PartitionRuntimeMBean.State.UNKNOWN, new HashSet() {
         {
            this.add(PartitionRuntimeMBean.State.RUNNING);
            this.add(PartitionRuntimeMBean.State.SUSPENDING);
         }
      }),
      RESUME(PartitionRuntimeMBean.State.RESUMING, PartitionRuntimeMBean.State.RUNNING, (State)null, PartitionRuntimeMBean.State.UNKNOWN, new HashSet() {
         {
            this.add(PartitionRuntimeMBean.State.ADMIN);
         }
      }),
      SHUTDOWN(PartitionRuntimeMBean.State.SHUTTING_DOWN, PartitionRuntimeMBean.State.SHUTDOWN, PartitionRuntimeMBean.State.BOOTED, PartitionRuntimeMBean.State.UNKNOWN, Collections.EMPTY_SET, new HashSet() {
         {
            this.add(PartitionRuntimeMBean.Operation.FORCE_SHUTDOWN);
         }
      }),
      HALT(PartitionRuntimeMBean.State.HALTING, PartitionRuntimeMBean.State.SHUTDOWN, PartitionRuntimeMBean.State.HALTED, PartitionRuntimeMBean.State.UNKNOWN, Collections.EMPTY_SET, Collections.EMPTY_SET),
      SUSPEND(PartitionRuntimeMBean.State.SUSPENDING, PartitionRuntimeMBean.State.ADMIN, (State)null, PartitionRuntimeMBean.State.UNKNOWN, new HashSet() {
         {
            this.add(PartitionRuntimeMBean.State.RUNNING);
         }
      }, new HashSet() {
         {
            this.add(PartitionRuntimeMBean.Operation.FORCE_SHUTDOWN);
            this.add(PartitionRuntimeMBean.Operation.FORCE_SUSPEND);
         }
      }),
      FORCE_RESTART(PartitionRuntimeMBean.State.FORCE_RESTARTING, (State)null, (State)null, PartitionRuntimeMBean.State.UNKNOWN, Collections.EMPTY_SET, Collections.EMPTY_SET);

      public final State nextState;
      public final State nextSuccessState;
      public final State nextFailureState;
      public final Set allowOps;
      public final Set allowablePreviousState;
      public final State successSubState;

      private Operation(State nextState, State nextSuccessState, State successSubState, State nextFailureState, Set allowablePreviousState) {
         this(nextState, nextSuccessState, successSubState, nextFailureState, allowablePreviousState, Collections.EMPTY_SET);
      }

      private Operation(State nextState, State nextSuccessState, State successSubState, State nextFailureState, Set allowablePreviousState, Set allowedOps) {
         this.nextState = nextState;
         this.nextSuccessState = nextSuccessState;
         this.nextFailureState = nextFailureState;
         this.allowOps = allowedOps;
         this.allowablePreviousState = allowablePreviousState;
         this.successSubState = successSubState;
      }

      public boolean isAllowedOp(Operation proposedOperation) {
         return this.allowOps.contains(proposedOperation);
      }

      public boolean isValidForState(State allowablePreviousState) {
         return this.allowablePreviousState.isEmpty() ? true : this.allowablePreviousState.contains(allowablePreviousState);
      }

      public static boolean isTeardownOperation(Operation operation) {
         return operation == SHUTDOWN || operation == FORCE_SHUTDOWN || operation == SUSPEND || operation == FORCE_SUSPEND || operation == HALT;
      }

      public static boolean isSetupOperation(Operation operation) {
         return operation == START || operation == BOOT || operation == ADMIN || operation == RESUME;
      }
   }

   public static enum State {
      UNKNOWN,
      HALTED,
      HALTING,
      BOOTING,
      SHUTDOWN,
      BOOTED,
      SHUTTING_DOWN,
      FORCE_SHUTTING_DOWN,
      STARTING_IN_ADMIN,
      ADMIN,
      RESUMING,
      SUSPENDING,
      FORCE_SUSPENDING,
      STARTING,
      FORCE_RESTARTING,
      RUNNING;

      private static final String USE_OLD_STATE_NAMES_PROPERTY_NAME = "partitionlifecycle.useOldStateNames";
      private static final String DEFAULT_USE_OLD_STATE_NAMES = "false";

      public static State max(State s1, State s2) {
         if (s1 == null) {
            return s2 == null ? null : s2;
         } else if (s2 == null) {
            return s1;
         } else {
            return s1.compareTo(s2) >= 0 ? s1 : s2;
         }
      }

      public static State min(State... states) {
         State result = null;
         State[] var2 = states;
         int var3 = states.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            State s = var2[var4];
            if (s != null) {
               if (result == null) {
                  result = s;
               } else if (s.compareTo(result) < 0) {
                  result = s;
               }
            }
         }

         return result;
      }

      public static boolean isRunning(State s) {
         return s == RUNNING;
      }

      public static boolean isShutdown(State s) {
         return s == SHUTDOWN;
      }

      public static boolean isShutdownBooted(State s) {
         return s == BOOTED;
      }

      public static boolean isShutdownBooted(String stateName) {
         return BOOTED == normalize(stateName);
      }

      public static boolean isShutdownHalted(State s) {
         return s == HALTED;
      }

      public static boolean isShutdownHalted(String stateName) {
         return HALTED == normalize(stateName);
      }

      public static State getLowestState(Operation op) {
         return op.successSubState != null ? op.successSubState : op.nextSuccessState;
      }

      public static boolean isRunning(String stateName) {
         return isRunning(normalize(stateName));
      }

      public static boolean isShutdown(String stateName) {
         return isShutdown(normalize(stateName));
      }

      public static boolean isAdmin(String stateName) {
         return ADMIN == normalize(stateName);
      }

      public static boolean isAdmin(State state) {
         return ADMIN == state;
      }

      public static State runningState() {
         return RUNNING;
      }

      public static State shuttingDownState() {
         return SHUTTING_DOWN;
      }

      public static State shutdownState() {
         return SHUTDOWN;
      }

      public static State shutdownInBootedState() {
         return BOOTED;
      }

      public static String chooseUserDesiredStateName(State s) {
         if (s == null) {
            return null;
         } else {
            return s == RUNNING && PartitionRuntimeMBean.USE_OLD_STATE_NAMES ? "STARTED" : s.name();
         }
      }

      public static State normalize(String stateName) {
         if (stateName == null) {
            return null;
         } else {
            return stateName.equals("STARTED") ? RUNNING : valueOf(stateName);
         }
      }

      public static boolean isAnyShutdownState(State state) {
         return state == BOOTED || state == HALTED || state == BOOTING || state == HALTING;
      }

      public static State chooseState(State state, boolean isSubState) {
         if (!isAnyShutdownState(state)) {
            return isSubState ? null : state;
         } else {
            return isSubState ? state : SHUTDOWN;
         }
      }
   }
}
