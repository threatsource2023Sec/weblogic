package weblogic.management.partition.admin;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import weblogic.management.runtime.PartitionRuntimeMBean;

public class ResourceGroupLifecycleOperations {
   public static boolean isStartUpTransitionState(RGState rgState) {
      return rgState == ResourceGroupLifecycleOperations.RGState.STARTING_IN_ADMIN || rgState == ResourceGroupLifecycleOperations.RGState.STARTING || rgState == ResourceGroupLifecycleOperations.RGState.RESUMING;
   }

   public static boolean isTearDownTransitionState(RGState rgState) {
      return rgState == ResourceGroupLifecycleOperations.RGState.SHUTTING_DOWN || rgState == ResourceGroupLifecycleOperations.RGState.FORCE_SHUTTING_DOWN || rgState == ResourceGroupLifecycleOperations.RGState.FORCE_SUSPENDING || rgState == ResourceGroupLifecycleOperations.RGState.SUSPENDING;
   }

   public static boolean isStateChangeAllowed(PartitionRuntimeMBean.State prevState, PartitionRuntimeMBean.State prevSubState, PartitionRuntimeMBean.State state, PartitionRuntimeMBean.State subState, RGState currentState, RGState proposedNewState, RGState desiredOnServer) {
      if (currentState == ResourceGroupLifecycleOperations.RGState.UNKNOWN) {
         return true;
      } else {
         boolean isTeardownPartitionOperation = isPartitionTeardown(prevState, prevSubState, state, subState);
         if (!isTeardownPartitionOperation && proposedNewState.compareTo(desiredOnServer) > 0) {
            return false;
         } else {
            return isTeardownPartitionOperation ? currentState.compareTo(proposedNewState) > 0 : currentState.compareTo(proposedNewState) < 0;
         }
      }
   }

   private static boolean isPartitionTeardown(PartitionRuntimeMBean.State prevState, PartitionRuntimeMBean.State prevSubState, PartitionRuntimeMBean.State state, PartitionRuntimeMBean.State subState) {
      if (prevState.compareTo(state) > 0) {
         return true;
      } else if (prevState.compareTo(state) < 0) {
         return false;
      } else if (prevSubState != null && subState != null) {
         return prevSubState.compareTo(subState) > 0;
      } else {
         return false;
      }
   }

   public static Set shutdownTransitionStates() {
      return new HashSet() {
         {
            this.add(ResourceGroupLifecycleOperations.RGState.FORCE_SHUTTING_DOWN);
            this.add(ResourceGroupLifecycleOperations.RGState.SHUTTING_DOWN);
         }
      };
   }

   public static Set suspendTransitionStates() {
      return new HashSet() {
         {
            this.add(ResourceGroupLifecycleOperations.RGState.SUSPENDING);
            this.add(ResourceGroupLifecycleOperations.RGState.FORCE_SUSPENDING);
         }
      };
   }

   public static Set resumeTransitionStates() {
      return new HashSet() {
         {
            this.add(ResourceGroupLifecycleOperations.RGState.RESUMING);
         }
      };
   }

   public static Set startTransitionStates() {
      return new HashSet() {
         {
            this.add(ResourceGroupLifecycleOperations.RGState.STARTING);
         }
      };
   }

   public static enum RGOperation {
      START(ResourceGroupLifecycleOperations.RGState.STARTING, ResourceGroupLifecycleOperations.RGState.runningState(), ResourceGroupLifecycleOperations.RGState.UNKNOWN, Collections.EMPTY_SET),
      ADMIN(ResourceGroupLifecycleOperations.RGState.STARTING_IN_ADMIN, ResourceGroupLifecycleOperations.RGState.ADMIN, ResourceGroupLifecycleOperations.RGState.SHUTDOWN, Collections.EMPTY_SET),
      /** @deprecated */
      @Deprecated
      STOP(ResourceGroupLifecycleOperations.RGState.SHUTTING_DOWN, ResourceGroupLifecycleOperations.RGState.SHUTDOWN, ResourceGroupLifecycleOperations.RGState.UNKNOWN, Collections.EMPTY_SET),
      FORCE_SHUTDOWN(ResourceGroupLifecycleOperations.RGState.FORCE_SHUTTING_DOWN, ResourceGroupLifecycleOperations.RGState.SHUTDOWN, ResourceGroupLifecycleOperations.RGState.UNKNOWN, Collections.EMPTY_SET),
      FORCE_SUSPEND(ResourceGroupLifecycleOperations.RGState.FORCE_SUSPENDING, ResourceGroupLifecycleOperations.RGState.ADMIN, ResourceGroupLifecycleOperations.RGState.UNKNOWN, new HashSet() {
         {
            this.add(ResourceGroupLifecycleOperations.RGState.SUSPENDING);
            this.add(ResourceGroupLifecycleOperations.RGState.RUNNING);
         }
      }),
      RESUME(ResourceGroupLifecycleOperations.RGState.RESUMING, ResourceGroupLifecycleOperations.RGState.runningState(), ResourceGroupLifecycleOperations.RGState.UNKNOWN, new HashSet() {
         {
            this.add(ResourceGroupLifecycleOperations.RGState.ADMIN);
            this.add(ResourceGroupLifecycleOperations.RGState.STARTING);
         }
      }),
      SHUTDOWN(ResourceGroupLifecycleOperations.RGState.SHUTTING_DOWN, ResourceGroupLifecycleOperations.RGState.SHUTDOWN, ResourceGroupLifecycleOperations.RGState.UNKNOWN, Collections.EMPTY_SET, new HashSet() {
         {
            this.add(ResourceGroupLifecycleOperations.RGOperation.FORCE_SHUTDOWN);
         }
      }),
      SUSPEND(ResourceGroupLifecycleOperations.RGState.SUSPENDING, ResourceGroupLifecycleOperations.RGState.ADMIN, ResourceGroupLifecycleOperations.RGState.UNKNOWN, new HashSet() {
         {
            this.add(ResourceGroupLifecycleOperations.RGState.RUNNING);
         }
      }, new HashSet() {
         {
            this.add(ResourceGroupLifecycleOperations.RGOperation.FORCE_SHUTDOWN);
            this.add(ResourceGroupLifecycleOperations.RGOperation.FORCE_SUSPEND);
         }
      });

      public final RGState nextRGState;
      public final RGState nextSuccessRGState;
      public final RGState nextFailureRGState;
      public final Set allowOps;
      public final Set allowablePreviousState;

      private RGOperation(RGState nextRGState, RGState nextSuccessRGState, RGState nextFailureRGState, Set allowablePreviousState) {
         this(nextRGState, nextSuccessRGState, nextFailureRGState, allowablePreviousState, Collections.EMPTY_SET);
      }

      private RGOperation(RGState nextRGState, RGState nextSuccessRGState, RGState nextFailureRGState, Set allowablePreviousState, Set allowOps) {
         this.nextRGState = nextRGState;
         this.nextSuccessRGState = nextSuccessRGState;
         this.nextFailureRGState = nextFailureRGState;
         this.allowOps = allowOps;
         this.allowablePreviousState = allowablePreviousState;
      }

      public boolean isAllowedOp(RGOperation proposedOperation) {
         return this.allowOps.contains(proposedOperation);
      }

      public boolean isValidForState(RGState allowablePreviousState) {
         return this.allowablePreviousState.isEmpty() ? true : this.allowablePreviousState.contains(allowablePreviousState);
      }
   }

   public static enum RGState {
      UNKNOWN,
      SHUTDOWN,
      FORCE_SHUTTING_DOWN,
      SHUTTING_DOWN,
      STARTING_IN_ADMIN,
      ADMIN,
      RESUMING,
      FORCE_SUSPENDING,
      SUSPENDING,
      STARTING,
      RUNNING;

      private static final boolean USE_OLD_STATE_NAMES = PartitionRuntimeMBean.USE_OLD_STATE_NAMES;

      public static RGState max(RGState s1, RGState s2) {
         if (s1 == null) {
            return s2 == null ? UNKNOWN : s2;
         } else if (s2 == null) {
            return s1;
         } else {
            return s1.compareTo(s2) >= 0 ? s1 : s2;
         }
      }

      public static RGState min(RGState... states) {
         RGState result = null;
         RGState[] var2 = states;
         int var3 = states.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            RGState s = var2[var4];
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

      public static boolean isRunning(RGState s) {
         return s == RUNNING;
      }

      public static boolean isShutdown(RGState s) {
         return s == SHUTDOWN;
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

      public static RGState runningState() {
         return RUNNING;
      }

      public static RGState shutdownState() {
         return SHUTDOWN;
      }

      public static RGState shuttingDownState() {
         return SHUTTING_DOWN;
      }

      public static String chooseUserDesiredStateName(RGState s) {
         return s == RUNNING && USE_OLD_STATE_NAMES ? "STARTED" : s.name();
      }

      public static RGState normalize(String stateName) {
         return stateName.equals("STARTED") ? RUNNING : valueOf(stateName);
      }
   }
}
