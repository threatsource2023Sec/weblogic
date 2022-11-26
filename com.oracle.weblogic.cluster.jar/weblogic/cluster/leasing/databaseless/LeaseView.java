package weblogic.cluster.leasing.databaseless;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import weblogic.cluster.messaging.internal.ClusterMessageProcessingException;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.singleton.SimpleLeasingBasis;
import weblogic.cluster.singleton.Leasing.LeaseOwnerIdentity;
import weblogic.protocol.LocalServerIdentity;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class LeaseView implements Serializable {
   static final long serialVersionUID = 7210518548000338390L;
   private static final DebugCategory debugLeaseView = Debug.getCategory("weblogic.cluster.leasing.LeaseView");
   private static final boolean DEBUG = debugEnabled();
   private static final long GUARD_TIME = 5000L;
   private long versionNumber;
   private HashMap leaseTableReplica;
   private HashMap localLeases;
   private String serverName;

   LeaseView(String serverName, HashMap entries) {
      this(serverName, entries, 0L);
   }

   LeaseView(String serverName, HashMap entries, long version) {
      this.versionNumber = version;
      this.serverName = serverName;
      if (entries == null) {
         this.leaseTableReplica = new HashMap();
      } else {
         this.leaseTableReplica = entries;
      }

      this.localLeases = new HashMap();
      if (DEBUG) {
         debug("created lease view for " + serverName + " with entries " + this.leaseTableReplica);
      }

   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[LeaseView] " + s);
   }

   synchronized void process(LeaseTableUpdateMessage update) throws ClusterMessageProcessingException {
      if (update.getVersion() != this.versionNumber + 1L) {
         throw new ClusterMessageProcessingException("unacceptable lease view update. local version " + this.versionNumber + " and received version is " + update.getVersion());
      } else {
         ++this.versionNumber;
         if (DEBUG) {
            debug("executing " + update);
         }

         if (update.getOperation() == 1) {
            this.leaseTableReplica.put(update.getKey(), update.getValue());
            Object localValue = this.localLeases.get(update.getKey());
            if (localValue != null && !localValue.equals(update.getValue())) {
               if (DEBUG) {
                  debug("removing " + update.getKey() + " as it has expired. local server no longer owns this lease !");
               }

               this.localLeases.remove(update.getKey());
            }
         } else if (update.getOperation() == 2) {
            this.leaseTableReplica.remove(update.getKey());
         } else {
            if (update.getOperation() != 3) {
               throw new AssertionError("unsupported lease update operation " + update);
            }

            this.leaseTableReplica.putAll(update.getMap());
         }

      }
   }

   synchronized void leaseAcquiredByLocalServer(String leaseName, int leaseTimeout) {
      String localIdentity = LeaseOwnerIdentity.getIdentity(LocalServerIdentity.getIdentity());
      SimpleLeasingBasis.LeaseEntry entry = new SimpleLeasingBasis.LeaseEntry(localIdentity, leaseName, leaseTimeout);
      this.localLeases.put(entry.getLeaseName(), entry);
      if (DEBUG) {
         debug("added " + entry + " to list of leases owned by this server");
      }

   }

   synchronized void leaseReleasedByLocalServer(String leaseName) {
      this.localLeases.remove(leaseName);
      if (DEBUG) {
         debug("removed " + leaseName + " from the list of leases owned by this server");
      }

   }

   synchronized int leasesOwnedByLocalServer() {
      return this.localLeases.size();
   }

   synchronized void merge(LeaseView entries) {
      if (entries != null) {
         this.leaseTableReplica.putAll(entries.localLeases);
      }
   }

   synchronized void prepareToBecomeLeader() {
      this.leaseTableReplica.putAll(this.localLeases);
      Iterator iter = this.leaseTableReplica.values().iterator();
      long timestamp = System.currentTimeMillis() + 5000L;

      while(iter.hasNext()) {
         SimpleLeasingBasis.LeaseEntry entry = (SimpleLeasingBasis.LeaseEntry)iter.next();
         entry.setTimestamp(timestamp);
      }

   }

   public HashMap getLeaseTableReplica() {
      return this.leaseTableReplica;
   }

   long getVersionNumber() {
      return this.versionNumber;
   }

   public synchronized void processStateDump(LeaseView leaseView) {
      if (leaseView != null && this.versionNumber != leaseView.getVersionNumber()) {
         if (DEBUG) {
            debug("resetting lease view with " + leaseView.getLeaseTableReplica());
         }

         this.leaseTableReplica = leaseView.getLeaseTableReplica();
         this.versionNumber = leaseView.getVersionNumber();
      }
   }

   void incrementVersionNumber() {
      ++this.versionNumber;
   }

   public String toString() {
      return "LeaseView for " + this.serverName + " with version " + this.versionNumber + "\nLeaseTableReplica contents: " + this.leaseTableReplica + "\nleases owned: " + this.localLeases;
   }

   private static boolean debugEnabled() {
      return debugLeaseView.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }
}
