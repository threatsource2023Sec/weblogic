package weblogic.cluster.migration;

import java.util.List;
import weblogic.cluster.singleton.AbstractServiceLocationSelector;
import weblogic.cluster.singleton.BasicServiceLocationSelector;
import weblogic.cluster.singleton.ServiceLocationSelector;
import weblogic.cluster.singleton.SingletonServicesDebugLogger;
import weblogic.cluster.singleton.SingletonServicesState;
import weblogic.cluster.singleton.SingletonServicesStateManager;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;

public class FailureRecoveryServiceLocationSelector extends AbstractServiceLocationSelector {
   private MigratableTargetMBean mtBean;
   private ServerMBean upsServer;
   private SingletonServicesStateManager stateManager;
   private List serverList;
   private ServerMBean lastHost;
   private boolean triedOnUPS = false;
   private ServiceLocationSelector sls;

   public FailureRecoveryServiceLocationSelector(MigratableTargetMBean mt, SingletonServicesStateManager sm) {
      this.mtBean = mt;
      this.upsServer = mt.getUserPreferredServer();
      this.stateManager = sm;
      this.sls = new BasicServiceLocationSelector(mt.getName(), sm);
   }

   public ServerMBean chooseServer() {
      return this.chooseServer(false);
   }

   public ServerMBean chooseServer(boolean isShutdownPolicy) {
      ServerMBean target = null;
      boolean isMigratable = this.stateManager.checkServiceState(this.mtBean.getName(), 1);
      boolean isNonMigratable = this.stateManager.checkServiceState(this.mtBean.getName(), 2);
      boolean shutDown = this.stateManager.checkServiceState(this.mtBean.getName(), 3);
      boolean failed = this.stateManager.checkServiceState(this.mtBean.getName(), 0);
      if (shutDown && !isShutdownPolicy) {
         if (DEBUG) {
            p(this.mtBean.getName() + " - is marked as shutdown. Trying to start it on its UPS Server");
         }

         return this.startOnUPServer(false);
      } else {
         if (!failed && !isShutdownPolicy) {
            if (!isNonMigratable && !isMigratable) {
               if (DEBUG) {
                  p(this.mtBean.getName() + " - is neither marked as NonMigratable nor migratable. Trying to start it on its UPS Server");
               }

               return this.startOnUPServer(false);
            }

            if (isNonMigratable) {
               if (this.lastHost == null) {
                  if (DEBUG) {
                     p(this.mtBean.getName() + " is marked as NonMigratable and its lasthost is null. Trying to start it on its UPS Server");
                  }

                  return this.startOnUPServer(false);
               }

               if (DEBUG) {
                  p(this.mtBean.getName() + " is marked as NonMigratable and its lasthost is not null. First trying to start it on its UPS Server");
               }

               if (!this.triedOnUPS) {
                  target = this.startOnUPServer(true);
               }

               if (target == null) {
                  if (DEBUG) {
                     p(this.mtBean.getName() + " -  Now trying to start it on its Non-UPS Server");
                  }

                  target = this.startOnNonUPServer(true);
               }
            } else if (isMigratable) {
               if (this.lastHost == null) {
                  if (DEBUG) {
                     p(this.mtBean.getName() + " is marked as Migratable and its lasthost is null. First trying to start it on its UPS Server.");
                  }

                  if (!this.triedOnUPS) {
                     target = this.startOnUPServer(false);
                  }

                  if (target == null) {
                     if (DEBUG) {
                        p(this.mtBean.getName() + " -  Now trying to start it on its Non-UPS Server");
                     }

                     target = this.startOnNonUPServer(false);
                  }
               } else {
                  if (DEBUG) {
                     p(this.mtBean.getName() + " is marked as Migratable and its lasthost is not null. First trying to start it on its UPS Server.");
                  }

                  if (!this.triedOnUPS) {
                     target = this.startOnUPServer(true);
                  }

                  if (target == null) {
                     if (DEBUG) {
                        p(this.mtBean.getName() + " -  Now trying to start it on its Non-UPS Server");
                     }

                     target = this.startOnNonUPServer(true);
                  }
               }
            }
         } else {
            if (this.lastHost == null) {
               if (DEBUG) {
                  p(this.mtBean.getName() + " - is marked as failed and its last host is null. First trying to start it on its UPS Server");
               }

               target = this.startOnUPServer(false);
            } else {
               if (DEBUG) {
                  p(this.mtBean.getName() + " - is marked as failed and its last host is not null. First trying to start it on its UPS Server");
               }

               if (!this.lastHost.getName().equals(this.upsServer.getName())) {
                  target = this.startOnUPServer(false);
               }
            }

            if (target == null) {
               if (DEBUG) {
                  p(this.mtBean.getName() + " -  Now trying to start it on its Non-UPS Server");
               }

               target = this.startOnNonUPServer(false);
            }
         }

         return target;
      }
   }

   private ServerMBean startOnUPServer(boolean doPostScriptExecution) {
      ServerMBean target = null;
      if (this.isServerRunning(this.upsServer) && !this.triedOnUPS) {
         target = this.chooseUPSServer();
      }

      if (target != null) {
         if (DEBUG) {
            p(this.mtBean.getName() + " - Going to select (UPS) " + target);
         }

         if (doPostScriptExecution && !this.executePostScript(this.mtBean, target, this.lastHost)) {
            this.stateManager.storeServiceState(this.mtBean.getName(), new SingletonServicesState(4));
            target = null;
         }
      } else if (DEBUG) {
         p(this.mtBean.getName() + " - UPS Server " + this.upsServer + " not available");
      }

      return target;
   }

   private ServerMBean startOnNonUPServer(boolean doPostScriptExecution) {
      if (this.serverList.size() == 0) {
         return null;
      } else {
         ServerMBean target = this.sls.chooseServer();
         if (target != null) {
            if (DEBUG) {
               p(this.mtBean.getName() + " - Going to select (Non-UPS) " + target);
            }

            if (doPostScriptExecution && !this.executePostScript(this.mtBean, target, this.lastHost)) {
               this.stateManager.storeServiceState(this.mtBean.getName(), new SingletonServicesState(4));
               target = null;
            }
         }

         return target;
      }
   }

   public void setLastHost(ServerMBean server) {
      this.lastHost = server;
      this.sls.setLastHost(server);
   }

   public void setServerList(List servers) {
      this.serverList = this.getAcceptableServers(servers);
      if (this.serverList.size() > 1) {
         this.serverList.remove(this.upsServer);
      }

      this.sls.setServerList(this.serverList);
   }

   public void migrationSuccessful(ServerMBean server, boolean automaticMigration) {
      SingletonServicesState state = null;
      if (automaticMigration) {
         if (server.getName().equals(this.upsServer.getName())) {
            state = new SingletonServicesState(2);
         } else {
            state = new SingletonServicesState(1);
         }
      } else {
         this.upsServer = server;
         state = new SingletonServicesState(2);
      }

      this.stateManager.storeServiceState(this.mtBean.getName(), state);
   }

   private ServerMBean chooseUPSServer() {
      this.sls.setLastHost(this.upsServer);
      this.triedOnUPS = true;
      return this.upsServer;
   }

   protected static void p(Object o) {
      SingletonServicesDebugLogger.debug("FailureRecoveryServiceLocationSelector: " + o);
   }
}
