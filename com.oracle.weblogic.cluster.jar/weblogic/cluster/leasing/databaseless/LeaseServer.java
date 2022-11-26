package weblogic.cluster.leasing.databaseless;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import weblogic.cluster.messaging.internal.ClusterMessageProcessingException;
import weblogic.cluster.messaging.internal.ClusterResponse;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.singleton.LeasingBasis;
import weblogic.cluster.singleton.MissedHeartbeatException;
import weblogic.cluster.singleton.SimpleLeasingBasis;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class LeaseServer implements LeasingBasis {
   private static final DebugCategory debugLeaseServer = Debug.getCategory("weblogic.cluster.leasing.LeaseServer");
   private static final boolean DEBUG = debugEnabled();
   private final SimpleLeasingBasis simpleBasis;

   LeaseServer(ClusterLeader leader, LeaseView leaseView) {
      Map replicatedMap = new ReplicatedLeaseTable(leader, leaseView);
      this.simpleBasis = new SimpleLeasingBasis(replicatedMap);
      if (DEBUG) {
         debug("created basis with entries " + leaseView.getLeaseTableReplica());
      }

   }

   public boolean acquire(String leaseName, String owner, int leaseTimeout) {
      synchronized(this.simpleBasis) {
         boolean var10000;
         try {
            boolean result = this.simpleBasis.acquire(leaseName, owner, leaseTimeout);
            if (result) {
               if (DEBUG) {
                  debug("acquire: successfully updated other servers for " + leaseName + " requested by " + owner + ". Updated master lease table and granted lease to " + owner + " for lease timeout of " + leaseTimeout);
               }

               var10000 = true;
               return var10000;
            }

            if (DEBUG) {
               String entry = this.simpleBasis.findOwner(leaseName);
               debug("acquire: " + entry + " owns the lease " + leaseName + " requested by " + owner + ". lease acquire is denied");
            }

            var10000 = false;
         } catch (LeaseTableUpdateException var8) {
            if (DEBUG) {
               debug("acquire: failed to update other servers for " + leaseName + " requested by " + owner + ". Lease is denied!");
            }

            return false;
         }

         return var10000;
      }
   }

   public void release(String leaseName, String owner) throws IOException {
      synchronized(this.simpleBasis) {
         String entry = this.simpleBasis.findPreviousOwner(leaseName);
         if (DEBUG) {
            debug("release: got previous owner " + entry + " for lease name " + leaseName);
         }

         if (entry != null) {
            if (entry == null || entry.equalsIgnoreCase(owner)) {
               if (DEBUG) {
                  debug("release: removing " + leaseName + " from master after updating other servers");
               }

               this.simpleBasis.release(leaseName, owner);
            }
         }
      }
   }

   public String findOwner(String leaseName) {
      synchronized(this.simpleBasis) {
         String entry = this.simpleBasis.findOwner(leaseName);
         if (DEBUG) {
            debug("findOwner: got owner " + entry + " for lease name " + leaseName);
         }

         return entry;
      }
   }

   public String findPreviousOwner(String leaseName) {
      synchronized(this.simpleBasis) {
         String entry = this.simpleBasis.findPreviousOwner(leaseName);
         if (DEBUG) {
            debug("findPreviousOwner: got owner " + entry + " for lease name " + leaseName);
         }

         return entry;
      }
   }

   public int renewAllLeases(int healthCheckPeriod, String owner) throws MissedHeartbeatException {
      synchronized(this.simpleBasis) {
         int var10000;
         try {
            int count = this.simpleBasis.renewAllLeases(healthCheckPeriod, owner);
            if (DEBUG) {
               debug("renewAllLeases: successfully renewed " + count + " leases for " + owner);
            }

            var10000 = count;
         } catch (LeaseTableUpdateException var6) {
            if (DEBUG) {
               debug("renewAllLeases: update failed on other servers. not renewing leases owned by " + owner);
            }

            return 0;
         }

         return var10000;
      }
   }

   public int renewLeases(String owner, Set leases, int healthCheckPeriod) throws MissedHeartbeatException {
      synchronized(this.simpleBasis) {
         int var10000;
         try {
            int count = this.simpleBasis.renewLeases(owner, leases, healthCheckPeriod);
            if (DEBUG) {
               debug("renewLeases: successfully renewed " + count + " leases for " + owner);
            }

            var10000 = count;
         } catch (LeaseTableUpdateException var7) {
            if (DEBUG) {
               debug("renewLeases: update failed on other servers. not renewing leases owned by " + owner);
            }

            return 0;
         }

         return var10000;
      }
   }

   public String[] findExpiredLeases(int gracePeriod) {
      synchronized(this.simpleBasis) {
         return this.simpleBasis.findExpiredLeases(gracePeriod);
      }
   }

   public ClusterResponse process(LeaseMessage leaseMessage) throws ClusterMessageProcessingException {
      if ("acquire".equals(leaseMessage.getRequestType())) {
         boolean result = this.acquire(leaseMessage.getLeaseName(), leaseMessage.getOwner(), leaseMessage.getLeaseTimeout());
         return new LeaseResponse(result, leaseMessage);
      } else if ("release".equals(leaseMessage.getRequestType())) {
         try {
            this.release(leaseMessage.getLeaseName(), leaseMessage.getOwner());
         } catch (IOException var3) {
            throw new ClusterMessageProcessingException(var3);
         }

         return new LeaseResponse(Boolean.TRUE, leaseMessage);
      } else {
         String lease;
         if ("find_owner".equals(leaseMessage.getRequestType())) {
            lease = this.findOwner(leaseMessage.getLeaseName());
            return new LeaseResponse(lease, leaseMessage);
         } else {
            int count;
            if ("renew_all".equals(leaseMessage.getRequestType())) {
               try {
                  count = this.renewAllLeases(leaseMessage.getHealthCheckPeriod(), leaseMessage.getOwner());
                  return new LeaseResponse(count, leaseMessage);
               } catch (MissedHeartbeatException var4) {
                  throw new ClusterMessageProcessingException(var4);
               }
            } else if ("renew_leases".equals(leaseMessage.getRequestType())) {
               try {
                  count = this.renewLeases(leaseMessage.getOwner(), leaseMessage.getLeasesToRenew(), leaseMessage.getHealthCheckPeriod());
                  return new LeaseResponse(count, leaseMessage);
               } catch (MissedHeartbeatException var5) {
                  throw new ClusterMessageProcessingException(var5);
               }
            } else if ("expired".equals(leaseMessage.getRequestType())) {
               String[] leases = this.findExpiredLeases(leaseMessage.getGracePeriod());
               return new LeaseResponse(leases, leaseMessage);
            } else if ("find_previous_owner".equals(leaseMessage.getRequestType())) {
               lease = this.findPreviousOwner(leaseMessage.getLeaseName());
               return new LeaseResponse(lease, leaseMessage);
            } else {
               throw new AssertionError(leaseMessage + " is unsupported !");
            }
         }
      }
   }

   private static boolean debugEnabled() {
      return debugLeaseServer.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[LeaseServer] " + s);
   }
}
