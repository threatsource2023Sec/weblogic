package weblogic.cluster.singleton;

import java.util.List;
import weblogic.management.configuration.ServerMBean;

public class BasicServiceLocationSelector extends AbstractServiceLocationSelector {
   private String serviceName;
   private SingletonServicesStateManager stateManager;
   private ServerMBean upsServer = null;
   private int serverIndex = -1;
   private List serverList = null;
   private ServerMBean lastHost = null;
   private boolean indexSet = false;
   private boolean triedOnUPS = false;

   public BasicServiceLocationSelector(String svcName, SingletonServicesStateManager sm) {
      this.serviceName = svcName;
      this.stateManager = sm;
   }

   public void setUPS(ServerMBean ups) {
      this.upsServer = ups;
   }

   public ServerMBean chooseServer() {
      if (this.serverList != null && this.serverList.size() != 0) {
         if (this.upsServer != null && !this.triedOnUPS && this.isServerRunning(this.upsServer)) {
            this.triedOnUPS = true;
            return this.upsServer;
         } else {
            if (!this.indexSet) {
               this.setIndex();
            }

            ServerMBean target = null;
            int tmp = (this.serverIndex + 1) % this.serverList.size();
            int breakPoint = tmp;

            do {
               target = (ServerMBean)this.serverList.get(tmp);
               if (target != null) {
                  if (this.isServerRunning(target)) {
                     this.serverIndex = tmp;
                     break;
                  }

                  target = null;
               }

               tmp = (tmp + 1) % this.serverList.size();
            } while(tmp != breakPoint);

            return target;
         }
      } else {
         return null;
      }
   }

   public void setLastHost(ServerMBean server) {
      this.lastHost = server;
      this.indexSet = false;
   }

   public void setServerList(List servers) {
      this.serverList = this.getAcceptableServers(servers);
      if (this.serverList.size() > 1 && this.upsServer != null) {
         this.serverList.remove(this.upsServer);
      }

   }

   private void setIndex() {
      if (this.lastHost != null && this.serverList != null) {
         for(int i = 0; i < this.serverList.size(); ++i) {
            ServerMBean srvr = (ServerMBean)this.serverList.get(i);
            if (srvr.getName().equals(this.lastHost.getName())) {
               this.serverIndex = i;
               break;
            }
         }
      }

      this.indexSet = true;
   }

   public void migrationSuccessful(ServerMBean server, boolean automaticMigration) {
      this.stateManager.storeServiceState(this.serviceName, new SingletonServicesState(1));
   }
}
