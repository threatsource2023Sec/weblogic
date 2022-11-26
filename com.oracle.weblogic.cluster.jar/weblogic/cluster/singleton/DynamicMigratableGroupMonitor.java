package weblogic.cluster.singleton;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import weblogic.cluster.migration.DynamicLoadbalancer;
import weblogic.cluster.migration.MigratableGroupConfig;
import weblogic.cluster.migration.PartitionAwareDynamicLoadbalancer;
import weblogic.cluster.migration.PartitionAwareObject;
import weblogic.cluster.migration.DynamicLoadbalancer.CRITICAL;
import weblogic.cluster.migration.DynamicLoadbalancer.State;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.work.WorkManagerFactory;

public abstract class DynamicMigratableGroupMonitor {
   protected static final DebugLogger dynLogger = DebugLogger.getDebugLogger("DebugDynamicSingletonServices");
   protected static final Map EMPTY_ACTIONS = new HashMap();
   protected final ServerMBean server;
   protected final ClusterMBean cluster;
   protected Map inProgressDLBs = Collections.synchronizedMap(new IdentityHashMap());

   protected DynamicMigratableGroupMonitor(ServerMBean server) {
      this.server = server;
      this.cluster = server.getCluster();
   }

   Set processAllDynamicServices(Map dynamicServices) {
      if (isDebug()) {
         p("processDynamicServices start ...");
      }

      Set allDynamicServices = new HashSet();
      if (!this.isActive()) {
         if (isDebug()) {
            p("processDynamicServices: skip run since SingletonMonitor is not active.");
         }

         return allDynamicServices;
      } else {
         Map runningServers = this.getRuningServersInCluster();
         Iterator var4 = dynamicServices.keySet().iterator();

         while(var4.hasNext()) {
            PartitionAwareDynamicLoadbalancer padlb = (PartitionAwareDynamicLoadbalancer)var4.next();
            DynamicLoadbalancer dlb = (DynamicLoadbalancer)padlb.getDelegate();
            if (this.inProgressDLBs.put(dlb, dlb) != null) {
               if (isDebug()) {
                  p("processAllDynamicServices: processing DynamicLoadbalancer " + dlb.getName() + " - its last processing has not finished yet, skip this run. ");
               }
            } else {
               Map configMap = (Map)dynamicServices.get(padlb);
               Set configNames = configMap.keySet();
               if (isDebug()) {
                  p("processAllDynamicServices: processing DynamicLoadbalancer " + dlb.getName() + " with configs " + configNames);
               }

               allDynamicServices.addAll(configNames);
               if (isDebug()) {
                  p("processAllDynamicServices: will schedule a thread to process DynamicLoadbalancer " + dlb.getName() + ": with registered services:[" + configMap.keySet() + "], runningServers:[" + runningServers + "]");
               }

               this.schedule(new DLBRunnable(padlb, configMap, runningServers));
            }
         }

         if (isDebug()) {
            p("processAllDynamicServices: end.");
         }

         return allDynamicServices;
      }
   }

   private Map getCurrentServiceStatus(Map configMap) {
      Map currentServiceStatus = new HashMap(configMap.size());
      Iterator var3 = configMap.keySet().iterator();

      while(var3.hasNext()) {
         String targetName = (String)var3.next();
         MigratableGroupConfig config = (MigratableGroupConfig)configMap.get(targetName);
         DynamicLoadbalancer.ServiceStatus status = this.getCurrentServiceStatus(targetName);
         currentServiceStatus.put(config.getName(), status);
      }

      return currentServiceStatus;
   }

   protected DynamicLoadbalancer.ServiceStatus getCurrentServiceStatus(String targetName) {
      String currentLocation = this.findServiceLocation(targetName);
      SingletonServicesState state = this.findServiceState(targetName);
      if (isDebug()) {
         p("targetName=" + targetName + "; singletonServicesState=" + state);
      }

      boolean isSerivceActive = currentLocation != null;
      DynamicLoadbalancer.ServiceStatus status;
      if (isSerivceActive) {
         status = new DynamicLoadbalancer.ServiceStatus(State.ACTIVE, currentLocation);
         if (state != null && currentLocation.equals(state.getStateData())) {
            if (isDebug()) {
               p("getCurrentServiceStatus: " + targetName + "; found current location - " + currentLocation);
            }
         } else {
            if (isDebug()) {
               p("getCurrentServiceStatus:" + targetName + "; inconsistent current location - from lease is " + currentLocation + ", but from stateManager is " + (state == null ? " [unable to get state]" : state.getStateData()) + "; will update stateManager with " + currentLocation);
            }

            this.saveServiceState(targetName, 1, currentLocation);
         }
      } else if (state == null) {
         status = new DynamicLoadbalancer.ServiceStatus(State.INACTIVE_OTHERS, (String)null);
      } else {
         String lastHostingServer = (String)state.getStateData();
         switch (state.getState()) {
            case 0:
               status = new DynamicLoadbalancer.ServiceStatus(State.INACTIVE_FAILED_UNHEALTHY, lastHostingServer);
               break;
            case 1:
            case 2:
            default:
               status = new DynamicLoadbalancer.ServiceStatus(State.INACTIVE_OTHERS, lastHostingServer);
               break;
            case 3:
               status = new DynamicLoadbalancer.ServiceStatus(State.INACTIVE_SERVER_SHUTDOWN, lastHostingServer);
               break;
            case 4:
               status = new DynamicLoadbalancer.ServiceStatus(State.INACTIVE_FAILED_CRITICAL, lastHostingServer);
               break;
            case 5:
               status = new DynamicLoadbalancer.ServiceStatus(State.INACTIVE_MANAGED, lastHostingServer);
         }
      }

      return status;
   }

   protected void saveServiceState(String service, int newState, String source) {
      SingletonServicesState state = new SingletonServicesState(newState);
      state.setStateData(source);
      this.storeServiceState(service, state);
   }

   protected static boolean isDebug() {
      return dynLogger.isDebugEnabled();
   }

   protected static void p(Object msg) {
      dynLogger.debug("[DynamicMigratableGroupMonitor] " + msg);
   }

   protected static void p(Object msg, Throwable th) {
      dynLogger.debug("[DynamicMigratableGroupMonitor] " + msg, th);
   }

   abstract String findServiceLocation(String var1);

   abstract SingletonServicesState findServiceState(String var1);

   protected abstract void storeServiceState(String var1, SingletonServicesState var2);

   protected abstract Map getRuningServersInCluster();

   protected abstract boolean deactivate(String var1, String var2, String var3);

   protected abstract boolean migrate(String var1, String var2, String var3, String var4);

   protected abstract void schedule(Runnable var1);

   protected abstract boolean isActive();

   protected abstract void manualMigrate(String var1, String var2, String var3) throws RemoteException, IllegalArgumentException;

   protected abstract DynamicLoadbalancer.ServiceStatus getServiceStatus(String var1, String var2) throws RemoteException, IllegalArgumentException;

   private class ActionPlanRunnable implements Runnable {
      private final String dlbName;
      private final String configName;
      private final DynamicLoadbalancer.ActionItem action;
      private final String partitionName;
      private final Map completedActions;
      private final Map actionFailures;
      private final CountDownLatch latch;

      private ActionPlanRunnable(String dlbName, String configName, DynamicLoadbalancer.ActionItem action, String partitionName, Map completedActions, Map actionFailures, CountDownLatch latch) {
         this.dlbName = dlbName;
         this.configName = configName;
         this.action = action;
         this.partitionName = partitionName;
         this.completedActions = completedActions;
         this.actionFailures = actionFailures;
         this.latch = latch;
      }

      public void run() {
         try {
            if (DynamicMigratableGroupMonitor.this.isActive()) {
               String targetName = PartitionAwareObject.qualifyPartitionInfo(this.partitionName, this.configName);
               DynamicLoadbalancer.ServiceStatus status = DynamicMigratableGroupMonitor.this.getCurrentServiceStatus(targetName);
               if (status == null) {
                  this.actionFailures.put(this.configName, CRITICAL.NO);
                  if (DynamicMigratableGroupMonitor.isDebug()) {
                     DynamicMigratableGroupMonitor.p("DLBRunnable.run(): execte action plan for service " + this.configName + " of DynamicLoadbalancer " + this.dlbName + " failed: invalid ActionItem - unknown service");
                     return;
                  }
               } else {
                  if (DynamicMigratableGroupMonitor.isDebug()) {
                     DynamicMigratableGroupMonitor.p("DLBRunnable.run(): will execte action plan for service " + this.configName + " of DynamicLoadbalancer " + this.dlbName + ": " + this.action);
                  }

                  String source = status.getHostingServer();
                  DynamicLoadbalancer.State currentState = status.getState();
                  String target = this.action.getTargetServer();
                  switch (this.action.getAction()) {
                     case MIGRATE:
                        this.doMigrate(this.completedActions, this.actionFailures, this.partitionName, this.configName, this.action, currentState, source, target);
                        return;
                     case ACTIVATE:
                        this.doActivate(this.completedActions, this.actionFailures, this.partitionName, this.configName, this.action, currentState, source, target);
                        return;
                     case DEACTIVATE:
                        this.doDeactivate(this.completedActions, this.actionFailures, this.partitionName, this.configName, this.action, currentState, source);
                        return;
                     case NO_OP:
                        this.completedActions.put(this.configName, this.action);
                        if (DynamicMigratableGroupMonitor.isDebug()) {
                           DynamicMigratableGroupMonitor.p("DLBRunnable.run(): execte action plan for service " + this.configName + " of DynamicLoadbalancer " + this.dlbName + " succeeded: NO_OP");
                           return;
                        }
                  }
               }

               return;
            }

            if (DynamicMigratableGroupMonitor.isDebug()) {
               DynamicMigratableGroupMonitor.p("DLBRunnable.run(): will return and skip remaining action items since system is not active.");
            }
         } finally {
            this.latch.countDown();
         }

      }

      private void doDeactivate(Map completedActions, Map actionFailures, String partitionName, String configName, DynamicLoadbalancer.ActionItem action, DynamicLoadbalancer.State currentState, String source) {
         String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, configName);
         if (!State.ACTIVE.equals(currentState)) {
            actionFailures.put(configName, CRITICAL.NO);
            if (DynamicMigratableGroupMonitor.isDebug()) {
               DynamicMigratableGroupMonitor.p("doDeactivate: execte action plan for service " + configName + " of DynamicLoadbalancer " + this.dlbName + " failed: invalid ActionItem - DEACTIVATE action is only allowed for current active service! but its state now is " + currentState);
            }
         } else if (action.getTargetServer() != null) {
            actionFailures.put(configName, CRITICAL.NO);
            if (DynamicMigratableGroupMonitor.isDebug()) {
               DynamicMigratableGroupMonitor.p("doDeactivate: execte action plan for service " + configName + " of DynamicLoadbalancer " + this.dlbName + " failed: invalid ActionItem - DEACTIVATE action must not set target server! but its target server is " + action.getTargetServer());
            }
         } else {
            boolean result = false;

            try {
               result = DynamicMigratableGroupMonitor.this.deactivate(partitionName, configName, source);
            } catch (Throwable var12) {
               if (DynamicMigratableGroupMonitor.isDebug()) {
                  DynamicMigratableGroupMonitor.p("doDeactivate: - Exception while deactivating service " + configName + " from " + source + " : " + var12, var12);
               }
            }

            if (result) {
               completedActions.put(configName, action);
               DynamicMigratableGroupMonitor.this.saveServiceState(targetName, 5, source);
               if (DynamicMigratableGroupMonitor.isDebug()) {
                  DynamicMigratableGroupMonitor.p("doDeactivate: execte action plan for service " + configName + " of DynamicLoadbalancer " + this.dlbName + " succeeded: DEACTIVATED from " + source);
               }
            } else {
               SingletonServicesState state = DynamicMigratableGroupMonitor.this.findServiceState(targetName);
               boolean critical = state != null && state.getState() == 4;
               if (critical) {
                  actionFailures.put(configName, CRITICAL.YES);
               } else {
                  actionFailures.put(configName, CRITICAL.NO);
               }

               if (DynamicMigratableGroupMonitor.isDebug()) {
                  DynamicMigratableGroupMonitor.p("doDeactivate: execte action plan for service " + configName + " of DynamicLoadbalancer " + this.dlbName + " failed: DEACTIVATE action failed, critical: " + critical);
               }
            }
         }

      }

      private void doActivate(Map completedActions, Map actionFailures, String partitionName, String configName, DynamicLoadbalancer.ActionItem action, DynamicLoadbalancer.State currentState, String source, String target) {
         if (State.ACTIVE.equals(currentState)) {
            actionFailures.put(configName, CRITICAL.NO);
            if (DynamicMigratableGroupMonitor.isDebug()) {
               DynamicMigratableGroupMonitor.p("doActivate: execte action plan for service " + configName + " of DynamicLoadbalancer " + this.dlbName + " failed: invalid ActionItem - ACTIVATE action is only allowed for current inactive service! but its state now is " + currentState);
            }
         } else {
            this.migrateOrActivateDynamicService(partitionName, configName, action, source, target, completedActions, actionFailures);
         }

      }

      private void doMigrate(Map completedActions, Map actionFailures, String partitionName, String configName, DynamicLoadbalancer.ActionItem action, DynamicLoadbalancer.State currentState, String source, String target) {
         if (!State.ACTIVE.equals(currentState)) {
            actionFailures.put(configName, CRITICAL.NO);
            if (DynamicMigratableGroupMonitor.isDebug()) {
               DynamicMigratableGroupMonitor.p("doMigrate: execte action plan for service " + configName + " of DynamicLoadbalancer " + this.dlbName + " failed: invalid ActionItem - MIGRATE action is only allowed for current active service! but its state now is " + currentState);
            }
         } else {
            this.migrateOrActivateDynamicService(partitionName, configName, action, source, target, completedActions, actionFailures);
         }

      }

      private void migrateOrActivateDynamicService(String partitionName, String configName, DynamicLoadbalancer.ActionItem action, String source, String target, Map completedActions, Map actionFailures) {
         String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, configName);
         if (target == null) {
            actionFailures.put(configName, CRITICAL.NO);
            if (DynamicMigratableGroupMonitor.isDebug()) {
               DynamicMigratableGroupMonitor.p("migrateOrActivateDynamicService: execte action plan for service " + configName + " of DynamicLoadbalancer " + this.dlbName + " failed: " + action.getAction() + " must have valid target server.");
            }
         } else {
            boolean succeed = false;
            boolean isNotReady = false;

            try {
               succeed = DynamicMigratableGroupMonitor.this.migrate(partitionName, configName, source, target);
               DynamicMigratableGroupMonitor.this.saveServiceState(targetName, 1, target);
            } catch (Throwable var14) {
               if (DynamicMigratableGroupMonitor.isDebug()) {
                  DynamicMigratableGroupMonitor.p("migrateOrActivateDynamicService: " + configName + " - Exception while migrating service it  from " + source + " to " + target + " : " + var14);
               }

               if (var14.toString().indexOf("MigratableGroupNotReadyException") >= 0) {
                  isNotReady = true;
               }
            }

            DynamicLoadbalancer.CRITICAL result;
            if (succeed) {
               result = null;
               completedActions.put(configName, action);
               if (DynamicMigratableGroupMonitor.isDebug()) {
                  DynamicMigratableGroupMonitor.p("migrateOrActivateDynamicService: execte action plan for service " + configName + " of DynamicLoadbalancer " + this.dlbName + " succeeded: " + action.getAction() + " from " + source + " to " + target);
               }
            } else {
               SingletonServicesState state = DynamicMigratableGroupMonitor.this.findServiceState(targetName);
               boolean critical = state != null && state.getState() == 4;
               if (!isNotReady) {
                  if (critical) {
                     result = CRITICAL.YES;
                     actionFailures.put(configName, result);
                  } else {
                     result = CRITICAL.NO;
                     actionFailures.put(configName, result);
                  }
               } else {
                  result = CRITICAL.NOTREADY;
                  actionFailures.put(configName, result);
               }

               if (DynamicMigratableGroupMonitor.isDebug()) {
                  DynamicMigratableGroupMonitor.p("migrateOrActivateDynamicService: execte action plan for service " + configName + " of DynamicLoadbalancer " + this.dlbName + " failed: " + action.getAction() + " action failed, critical:" + result);
               }
            }
         }

      }

      // $FF: synthetic method
      ActionPlanRunnable(String x1, String x2, DynamicLoadbalancer.ActionItem x3, String x4, Map x5, Map x6, CountDownLatch x7, Object x8) {
         this(x1, x2, x3, x4, x5, x6, x7);
      }
   }

   private class DLBRunnable implements Runnable {
      private final DynamicLoadbalancer dlb;
      private final PartitionAwareDynamicLoadbalancer padlb;
      private final String partitionName;
      private final Map configMap;
      private Map runningServers;

      DLBRunnable(PartitionAwareDynamicLoadbalancer padlb, Map configMap, Map runningServers) {
         this.padlb = padlb;
         this.dlb = (DynamicLoadbalancer)padlb.getDelegate();
         this.partitionName = padlb.getPartitionName();
         this.configMap = configMap;
         this.runningServers = runningServers;
      }

      public void run() {
         if (!DynamicMigratableGroupMonitor.this.isActive()) {
            if (DynamicMigratableGroupMonitor.isDebug()) {
               DynamicMigratableGroupMonitor.p("DLBRunnable.run(): skip run since system is not active.");
            }

         } else {
            try {
               final Map currentServiceStatus = DynamicMigratableGroupMonitor.this.getCurrentServiceStatus(this.configMap);
               if (DynamicMigratableGroupMonitor.isDebug()) {
                  DynamicMigratableGroupMonitor.p("DLBRunnable.run(): start to process DynamicLoadbalancer " + this.dlb.getName() + ": will call adjustServices() with currentServiceStatus:[" + currentServiceStatus + "], cluster:" + DynamicMigratableGroupMonitor.this.cluster.getName() + ", server:" + DynamicMigratableGroupMonitor.this.server.getName() + ", runningServers:[" + this.runningServers + "]");
               }

               Map actionPlan;
               try {
                  final Map finalRunningServersx = this.runningServers;
                  actionPlan = (Map)this.padlb.runUnderPartition(new Callable() {
                     public Map call() {
                        return DLBRunnable.this.dlb.adjustServices(currentServiceStatus, DynamicMigratableGroupMonitor.this.cluster, DynamicMigratableGroupMonitor.this.server, finalRunningServersx);
                     }
                  });
               } catch (Exception var18) {
                  if (DynamicMigratableGroupMonitor.isDebug()) {
                     DynamicMigratableGroupMonitor.p("DLBRunnable.run(): DynamicLoadbalancer " + this.dlb.getName() + " threw out unexpected exception during calling adjustServices(), will ignore and treat as empty action plan: " + var18);
                  }

                  actionPlan = null;
               }

               if (DynamicMigratableGroupMonitor.isDebug()) {
                  DynamicMigratableGroupMonitor.p("DLBRunnable.run(): start to process DynamicLoadbalancer " + this.dlb.getName() + ": adjustServices() returns action plan " + actionPlan);
               }

               while(true) {
                  if (actionPlan == null) {
                     actionPlan = DynamicMigratableGroupMonitor.EMPTY_ACTIONS;
                  }

                  final Map completedActions = new ConcurrentHashMap();
                  final Map actionFailures = new ConcurrentHashMap();
                  int actionPalnSize = actionPlan.size();
                  if (actionPalnSize > 0) {
                     CountDownLatch latch = new CountDownLatch(actionPalnSize);
                     Iterator var7 = actionPlan.keySet().iterator();

                     while(true) {
                        if (!var7.hasNext()) {
                           try {
                              latch.await();
                              if (DynamicMigratableGroupMonitor.isDebug()) {
                                 DynamicMigratableGroupMonitor.p("DLBRunnable.run(): execution of action plan for all services of DynamicLoadbalancer " + this.dlb.getName() + " completed!");
                              }
                           } catch (InterruptedException var19) {
                              if (DynamicMigratableGroupMonitor.isDebug()) {
                                 DynamicMigratableGroupMonitor.p("DLBRunnable.run(): DynamicLoadbalancer " + this.dlb.getName() + " threw out unexpected exception during waiting for completing actionPaln: " + var19);
                              }
                           }
                           break;
                        }

                        String configName = (String)var7.next();
                        DynamicLoadbalancer.ActionItem actionItem = (DynamicLoadbalancer.ActionItem)actionPlan.get(configName);
                        ActionPlanRunnable apr = DynamicMigratableGroupMonitor.this.new ActionPlanRunnable(this.dlb.getName(), configName, actionItem, this.partitionName, completedActions, actionFailures, latch);
                        WorkManagerFactory.getInstance().getSystem().schedule(apr);
                     }
                  }

                  if (actionFailures.isEmpty()) {
                     if (DynamicMigratableGroupMonitor.isDebug()) {
                        DynamicMigratableGroupMonitor.p("DLBRunnable.run(): execution of action plan for all services of DynamicLoadbalancer " + this.dlb.getName() + " succeeded: will call onSuccess()");
                     }

                     try {
                        this.padlb.runUnderPartition(new Callable() {
                           public Void call() {
                              DLBRunnable.this.dlb.onSuccess();
                              return null;
                           }
                        });
                     } catch (Exception var17) {
                        if (DynamicMigratableGroupMonitor.isDebug()) {
                           DynamicMigratableGroupMonitor.p("DLBRunnable.run(): DynamicLoadbalancer " + this.dlb.getName() + " threw out unexpected exception during calling onSuccess(), will ignore: " + var17);
                           return;
                        }
                     }

                     return;
                  }

                  currentServiceStatus = DynamicMigratableGroupMonitor.this.getCurrentServiceStatus(this.configMap);
                  this.runningServers = DynamicMigratableGroupMonitor.this.getRuningServersInCluster();
                  if (DynamicMigratableGroupMonitor.isDebug()) {
                     DynamicMigratableGroupMonitor.p("DLBRunnable.run(): execution of action plan for DynamicLoadbalancer " + this.dlb.getName() + " partially failed: will call onFailure() with:  actionFailures:" + actionFailures + "; completedActions:" + completedActions + "; currentServiceStatus:" + currentServiceStatus + "; runningServers:" + this.runningServers);
                  }

                  if (!DynamicMigratableGroupMonitor.this.isActive()) {
                     if (DynamicMigratableGroupMonitor.isDebug()) {
                        DynamicMigratableGroupMonitor.p("DLBRunnable.run(): will return and skip dlb.onFailure() since system is not active.");
                     }
                     break;
                  }

                  try {
                     final Map finalRunningServers = this.runningServers;
                     actionPlan = (Map)this.padlb.runUnderPartition(new Callable() {
                        public Map call() {
                           return DLBRunnable.this.dlb.onFailure(completedActions, actionFailures, currentServiceStatus, finalRunningServers);
                        }
                     });
                  } catch (Exception var20) {
                     if (DynamicMigratableGroupMonitor.isDebug()) {
                        DynamicMigratableGroupMonitor.p("DLBRunnable.run(): DynamicLoadbalancer " + this.dlb.getName() + " threw out unexpected exception during calling onFailure(), will ignore and treat as empty action plan: " + var20);
                     }

                     actionPlan = null;
                  }

                  if (DynamicMigratableGroupMonitor.isDebug()) {
                     DynamicMigratableGroupMonitor.p("DLBRunnable.run(): execution of action plan for DynamicLoadbalancer " + this.dlb.getName() + " partially failed: onFailure() returns new action plan " + actionPlan);
                  }
               }
            } finally {
               DynamicMigratableGroupMonitor.this.inProgressDLBs.remove(this.dlb);
               if (DynamicMigratableGroupMonitor.isDebug()) {
                  DynamicMigratableGroupMonitor.p("DLBRunnable.run(): finished process DynamicLoadbalancer " + this.dlb.getName());
               }

            }

         }
      }
   }
}
