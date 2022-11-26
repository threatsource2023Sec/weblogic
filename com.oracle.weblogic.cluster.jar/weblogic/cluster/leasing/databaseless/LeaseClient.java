package weblogic.cluster.leasing.databaseless;

import java.io.IOException;
import java.util.Set;
import weblogic.cluster.messaging.internal.ClusterMessageFactory;
import weblogic.cluster.messaging.internal.ClusterMessageSender;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.messaging.internal.ServerInformation;
import weblogic.cluster.singleton.ClusterReformationInProgressException;
import weblogic.cluster.singleton.LeasingBasis;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class LeaseClient implements LeasingBasis, ClusterStateChangeListener {
   private static final DebugCategory debugLeaseClient = Debug.getCategory("weblogic.cluster.leasing.LeaseClient");
   private boolean clusterFormationInProgress = true;

   public LeaseClient() {
      ClusterState.getInstance().addStateChangeListener(this);
   }

   public boolean acquire(String leaseName, String owner, int leaseTimeout) throws IOException {
      ServerInformation leaderInformation = ClusterLeaderService.getLeader();
      if (leaderInformation == null) {
         if (debugEnabled()) {
            debug("Cluster leader is not present ! refuse lease acquisition for " + leaseName);
         }

         return false;
      } else {
         ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
         LeaseMessage message = new LeaseMessage(leaseName, owner, leaseTimeout);
         if (debugEnabled()) {
            debug("requesting new lease " + message + " from LeaseServer " + leaderInformation);
         }

         LeaseResponse response = (LeaseResponse)messageSender.send(message, leaderInformation);
         if (debugEnabled()) {
            debug("received response to the lease message " + response);
         }

         boolean result = (Boolean)response.getResult();
         if (result && EnvironmentFactory.getClusterMember().getLeaseView() != null) {
            EnvironmentFactory.getClusterMember().getLeaseView().leaseAcquiredByLocalServer(leaseName, leaseTimeout);
         }

         return result;
      }
   }

   public void release(String leaseName, String owner) throws IOException {
      ServerInformation leaderInformation = ClusterLeaderService.getLeader();
      if (leaderInformation == null) {
         if (debugEnabled()) {
            debug("Cluster leader is not present ! refuse lease release for " + leaseName);
         }

      } else {
         ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
         LeaseMessage message = new LeaseMessage(leaseName, owner);
         if (debugEnabled()) {
            debug("requesting lease release " + message + " from LeaseServer " + leaderInformation);
         }

         try {
            LeaseResponse response = (LeaseResponse)messageSender.send(message, leaderInformation);
            if (debugEnabled()) {
               debug("received response to the lease message " + response);
            }

            if (EnvironmentFactory.getClusterMember().getLeaseView() != null) {
               EnvironmentFactory.getClusterMember().getLeaseView().leaseReleasedByLocalServer(leaseName);
            }

         } catch (LeaseTableUpdateException var7) {
            throw new IOException(var7.getMessage());
         }
      }
   }

   public String findOwner(String leaseName) throws IOException {
      ServerInformation leaderInformation = ClusterLeaderService.getLeader();
      if (leaderInformation == null) {
         leaderInformation = PrimordialClusterLeaderService.getInstance().getLeaderInformationInternal(true);
         if (leaderInformation == null) {
            if (debugEnabled()) {
               debug("No server knows about the Cluster leader yet ! refuse find owner for " + leaseName);
            }

            throw this.createIOException("unable to find owner for lease " + leaseName + " as the cluster leader is unavailable");
         }
      }

      return this.findOwner(leaseName, leaderInformation);
   }

   public String findOwner(String leaseName, ServerInformation leaderInformation) throws IOException {
      ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
      LeaseMessage message = new LeaseMessage(leaseName);
      if (debugEnabled()) {
         debug("requesting find owner " + message + " from LeaseServer " + leaderInformation);
      }

      LeaseResponse response = (LeaseResponse)messageSender.send(message, leaderInformation);
      if (debugEnabled()) {
         debug("received response to the lease message " + response);
      }

      return (String)response.getResult();
   }

   public String findPreviousOwner(String leaseName) throws IOException {
      ServerInformation leaderInformation = ClusterLeaderService.getLeader();
      if (leaderInformation == null) {
         if (debugEnabled()) {
            debug("Cluster leader is not present ! refuse find previous owner for " + leaseName);
         }

         throw this.createIOException("unable to find previous owner for lease " + leaseName + " as the cluster leader is unavailable");
      } else {
         ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
         LeaseMessage message = LeaseMessage.createFindPreviousOwnerMessage(leaseName);
         if (debugEnabled()) {
            debug("requesting find previous owner " + message + " from LeaseServer " + leaderInformation);
         }

         LeaseResponse response = (LeaseResponse)messageSender.send(message, leaderInformation);
         if (debugEnabled()) {
            debug("received response to the lease message " + response);
         }

         return (String)response.getResult();
      }
   }

   public int renewLeases(String owner, Set leases, int healthCheckPeriod) throws IOException {
      ServerInformation leaderInformation = ClusterLeaderService.getLeader();
      if (leaderInformation == null) {
         if (debugEnabled()) {
            debug("Cluster leader is not present ! refuse renew leases for " + owner);
         }

         throw this.createIOException("Cluster leader is not present ! refuse renew leases for " + owner);
      } else {
         ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
         LeaseMessage message = new LeaseMessage(owner, leases, healthCheckPeriod);
         if (debugEnabled()) {
            debug("requesting renew leases " + message + " from LeaseServer " + leaderInformation);
         }

         LeaseResponse response = (LeaseResponse)messageSender.send(message, leaderInformation);
         if (debugEnabled()) {
            debug("received response to the lease message " + response);
         }

         return (Integer)response.getResult();
      }
   }

   public int renewAllLeases(int healthCheckPeriod, String owner) throws IOException {
      ServerInformation leaderInformation = ClusterLeaderService.getLeader();
      if (leaderInformation == null) {
         if (debugEnabled()) {
            debug("Cluster leader is not present ! refuse renew all leases for " + owner);
         }

         throw this.createIOException("Cluster leader is not present ! refuse renew all leases for " + owner);
      } else {
         ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
         LeaseMessage message = new LeaseMessage(healthCheckPeriod, owner);
         if (debugEnabled()) {
            debug("requesting renew all leases " + message + " from LeaseServer " + leaderInformation);
         }

         LeaseResponse response = (LeaseResponse)messageSender.send(message, leaderInformation);
         if (debugEnabled()) {
            debug("received response to the lease message " + response);
         }

         return (Integer)response.getResult();
      }
   }

   public String[] findExpiredLeases(int gracePeriod) throws IOException {
      ServerInformation leaderInformation = ClusterLeaderService.getLeader();
      if (leaderInformation == null) {
         if (debugEnabled()) {
            debug("Cluster leader is not present ! refuse find expired leases");
         }

         throw this.createIOException("unable to find expired leases  as the cluster leader is unavailable");
      } else {
         ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
         LeaseMessage message = LeaseMessage.findExpiredLeasesMessage(gracePeriod);
         if (debugEnabled()) {
            debug("requesting expired leases " + message + " from LeaseServer " + leaderInformation);
         }

         LeaseResponse response = (LeaseResponse)messageSender.send(message, leaderInformation);
         if (debugEnabled()) {
            debug("received response to the lease message " + response);
         }

         return (String[])((String[])response.getResult());
      }
   }

   public synchronized void stateChanged(String oldState, String newState) {
      String interned = newState.intern();
      if (interned != "discovery" && interned != "formation" && interned != "formation_leader") {
         this.clusterFormationInProgress = false;
      } else {
         this.clusterFormationInProgress = true;
      }

   }

   private synchronized IOException createIOException(String message) {
      return (IOException)(this.clusterFormationInProgress ? new ClusterReformationInProgressException(message) : new IOException(message));
   }

   private static boolean debugEnabled() {
      return debugLeaseClient.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[LeaseClient] " + s);
   }
}
