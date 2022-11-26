package weblogic.management.utils.jmsdlb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.cluster.migration.DynamicLoadbalancer;
import weblogic.cluster.migration.DynamicLoadbalancer.State;

public class DLCoreInfo {
   private transient Map lastCoreServiceStatus;
   private final DLUtil.DLLogger logger;
   private final DLContext context;

   public DLCoreInfo(DLUtil.DLLogger logger, DLContext _context) {
      this.logger = logger;
      this.context = _context;
   }

   private static boolean statusChanged(Map oldstatus, Map newstatus) {
      if (oldstatus.size() != newstatus.size()) {
         return true;
      } else if (!oldstatus.keySet().containsAll(newstatus.keySet())) {
         return true;
      } else {
         Iterator var2 = oldstatus.entrySet().iterator();

         DynamicLoadbalancer.ServiceStatus newvalue;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            Map.Entry entry = (Map.Entry)var2.next();
            newvalue = (DynamicLoadbalancer.ServiceStatus)newstatus.get(entry.getKey());
            if (!((DynamicLoadbalancer.ServiceStatus)entry.getValue()).equals(newvalue)) {
               return true;
            }
         } while(newvalue.getState() == State.ACTIVE);

         return true;
      }
   }

   public boolean updateServiceStatus(Map managedServers, Map managedGroups, Map currentServiceStatus) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("DLCoreClusterInfo: In updateServiceStatus " + currentServiceStatus);
      }

      if (this.lastCoreServiceStatus == null) {
         this.lastCoreServiceStatus = new HashMap(currentServiceStatus);
      } else {
         if (!statusChanged(currentServiceStatus, this.lastCoreServiceStatus)) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("DLCluster: Complete ChangeServiceStatus nothing to do");
            }

            return false;
         }

         this.lastCoreServiceStatus = new HashMap(currentServiceStatus);
      }

      if (this.logger.isDebugEnabled()) {
         this.logger.debug("DLCluster: Processing ChangeServiceStatus state changes");
      }

      Iterator var4 = currentServiceStatus.entrySet().iterator();

      while(true) {
         while(var4.hasNext()) {
            Map.Entry e = (Map.Entry)var4.next();
            String mgName = (String)e.getKey();
            DynamicLoadbalancer.ServiceStatus ss = (DynamicLoadbalancer.ServiceStatus)e.getValue();
            String host = ss.getHostingServer();
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Processing: " + mgName + " " + ss);
            }

            DLMigratableGroup mg = (DLMigratableGroup)managedGroups.get(mgName);
            if (mg == null) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Internal Error, we never registered the migration instance for " + mgName);
               }
            } else {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Setting state for " + mg);
               }

               DLServerStatus server = (DLServerStatus)managedServers.get(host);
               boolean serverRemoved;
               if (ss.getState() == State.ACTIVE) {
                  serverRemoved = mg.getStatus() != DLMigratableGroup.STATUS.RUNNING;
                  boolean notInDoubt = server != null && server.getState() != DLServerStatus.State.SUSPECT;
                  if (server != null && (serverRemoved || !notInDoubt)) {
                     server.setState(DLServerStatus.State.RUNNING);
                  }

                  mg.setStatus(DLMigratableGroup.STATUS.RUNNING);
                  mg.setCurrentServer(server);
               } else {
                  serverRemoved = false;
                  switch (ss.getState()) {
                     case INACTIVE_FAILED_CRITICAL:
                        mg.setStatus(DLMigratableGroup.STATUS.FAILED_CRITICAL);
                        break;
                     case INACTIVE_FAILED_UNHEALTHY:
                        mg.setStatus(DLMigratableGroup.STATUS.FAILED);
                        break;
                     case INACTIVE_SERVER_SHUTDOWN:
                        serverRemoved = true;
                        mg.setStatus(DLMigratableGroup.STATUS.ADMIN_DOWN);
                        if (server == null) {
                           server = new DLServerStatus(host, this.context);
                           server.setState(DLServerStatus.State.REMOVED);
                           if (this.logger.isDebugEnabled()) {
                              this.logger.debug("Reconciling the missing " + server + " by putting it in managedServers");
                           }

                           managedServers.put(host, server);
                        }

                        mg.setCurrentServer(server);
                        break;
                     case INACTIVE_MANAGED:
                     case INACTIVE_OTHERS:
                        serverRemoved = true;
                        mg.setStatus(DLMigratableGroup.STATUS.DOWN);
                  }

                  if (server != null && server.getState() == DLServerStatus.State.DOWN_UNKNOWN) {
                     server.setState(serverRemoved ? DLServerStatus.State.REMOVED : DLServerStatus.State.DOWN);
                  }
               }
            }
         }

         return true;
      }
   }

   public Map getLastStatus() {
      return new HashMap(this.lastCoreServiceStatus);
   }
}
