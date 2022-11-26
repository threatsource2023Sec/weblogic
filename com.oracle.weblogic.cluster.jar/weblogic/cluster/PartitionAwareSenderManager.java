package weblogic.cluster;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import weblogic.cluster.migration.MigrationManagerService;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class PartitionAwareSenderManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static PartitionAwareSenderManager _me = new PartitionAwareSenderManager();
   private long ageThreshold;
   private boolean onBulkUpdate;
   private AtomicBoolean blocked = new AtomicBoolean(true);
   private ConcurrentHashMap knownAnnouncementManagers = new ConcurrentHashMap();
   private Object lock = new Object();
   private ConcurrentHashMap activeMulticastSessionIds = new ConcurrentHashMap();
   private ConcurrentHashMap inactiveMulticastSessionIds = new ConcurrentHashMap();
   private AtomicReference session = new AtomicReference((Object)null);
   private static AtomicInteger _nextChangeNumber = new AtomicInteger(0);
   private static final ResourceGroupLifecycleOperations.RGState EXPECTED_STATE;
   private ConcurrentHashMap appId2AppInfo = new ConcurrentHashMap();

   public static PartitionAwareSenderManager theOne() {
      return _me;
   }

   private PartitionAwareSenderManager() {
   }

   void setAgeThreshold(long ageThreshold) {
      this.ageThreshold = ageThreshold;
   }

   void handlePartitionStart(String partitionName) {
      ResourceGroupMBean[] resourceGroupMBeans = getResourceGroupsForThisServer(partitionName);
      PartitionRuntimeMBean partitionRuntimeMBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupPartitionRuntime(partitionName);
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManger.handlePartitionStart Partition " + partitionName + " state: " + partitionRuntimeMBean.getState() + "  RG size: " + resourceGroupMBeans.length);
      }

      ResourceGroupMBean[] var4 = resourceGroupMBeans;
      int var5 = resourceGroupMBeans.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ResourceGroupMBean rg = var4[var6];
         ResourceGroupLifecycleOperations.RGState rgState = null;

         try {
            rgState = RGState.valueOf(partitionRuntimeMBean.getRgState(rg.getName()));
            if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager.handlePartitionStart Partition " + partitionName + "; rgState: " + rgState);
            }

            switch (rgState) {
               case STARTING_IN_ADMIN:
               case STARTING:
               case RESUMING:
               case ADMIN:
               case RUNNING:
                  this.activateSource(new MulticastSessionId(PartitionStateTracker.toPartitionId(partitionName), rg.getName()));
            }
         } catch (ResourceGroupLifecycleException var10) {
            if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager.handlePartitionStart ResourceGroupLifeCycleException: " + var10);
            }
         }
      }

      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager.handlePartitionStart Activating Partition level AnnouncementManager for Partition " + partitionName);
      }

      this.activateSource(new MulticastSessionId(PartitionStateTracker.toPartitionId(partitionName), "NO_RESOURCE_GROUP"));
   }

   boolean isKnownToBeActive(String partitionId) {
      HashSet ids;
      synchronized(this.lock) {
         ids = new HashSet(this.activeMulticastSessionIds.keySet());
      }

      Iterator var3 = ids.iterator();

      MulticastSessionId id;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         id = (MulticastSessionId)var3.next();
      } while(!id.getPartitionID().equals(partitionId));

      return true;
   }

   void handlePartitionStop(String partitionName) {
      ResourceGroupMBean[] resourceGroupMBeans = getResourceGroupsForThisServer(partitionName);
      PartitionRuntimeMBean partitionRuntimeMBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupPartitionRuntime(partitionName);
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManger.handlePartitionStop Partition " + partitionName + " state: " + partitionRuntimeMBean.getState() + "  RG size: " + resourceGroupMBeans.length);
      }

      ResourceGroupMBean[] var4 = resourceGroupMBeans;
      int var5 = resourceGroupMBeans.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ResourceGroupMBean rg = var4[var6];
         ResourceGroupLifecycleOperations.RGState rgState = null;

         try {
            rgState = RGState.valueOf(partitionRuntimeMBean.getRgState(rg.getName()));
            if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager.handlePartitionStop rgState: " + rgState);
            }

            switch (rgState) {
               case SUSPENDING:
               case FORCE_SUSPENDING:
               case SHUTTING_DOWN:
               case FORCE_SHUTTING_DOWN:
               case SHUTDOWN:
                  this.deactivateSource(new MulticastSessionId(PartitionPostStateTracker.toPartitionId(partitionName), rg.getName()));
            }
         } catch (ResourceGroupLifecycleException var10) {
            if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager.handlePartitionStop ResourceGroupLifecycleException: " + var10);
            }
         }
      }

      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager.handlePartitionStop DeActivating Partition level AnnouncementManager for Partition " + partitionName);
      }

      this.deactivateSource(new MulticastSessionId(PartitionPostStateTracker.toPartitionId(partitionName), "NO_RESOURCE_GROUP"));
      this.shutdownPartitionScopedMigratableGroups(partitionName);
   }

   private void shutdownPartitionScopedMigratableGroups(String partitionName) {
      MigrationManagerService mms = (MigrationManagerService)GlobalServiceLocator.getServiceLocator().getService(MigrationManagerService.class, new Annotation[0]);
      mms.handlePriorityShutDownTasks(partitionName);
   }

   void activateSource(MulticastSessionId multicastSessionId) {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("*** PartitionAwareSenderManger.activateSource() ACTIVATING MulticastSession: " + multicastSessionId + " ***");
      }

      MulticastSessionsStatusMessage pscm;
      synchronized(this.lock) {
         pscm = new MulticastSessionsStatusMessage(_nextChangeNumber.incrementAndGet());
         this.inactiveMulticastSessionIds.remove(multicastSessionId);
         this.activeMulticastSessionIds.put(multicastSessionId, multicastSessionId);
         Iterator var4 = this.activeMulticastSessionIds.keySet().iterator();

         MulticastSessionId key;
         while(var4.hasNext()) {
            key = (MulticastSessionId)var4.next();
            pscm.markAsActive(key);
         }

         var4 = this.inactiveMulticastSessionIds.keySet().iterator();

         while(true) {
            if (!var4.hasNext()) {
               ClusterMessagesManager.theOne().replaceItem(pscm);
               if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
                  ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager.activateSource() Updated MulticastSessionStatus: " + pscm + " for multicastSessionId: " + multicastSessionId);
               }
               break;
            }

            key = (MulticastSessionId)var4.next();
            pscm.markAsInactive(key);
         }
      }

      try {
         this.getOrCreateMulticastSession().send(new MulticastSessionStartedMessage(pscm));
      } catch (IOException var7) {
         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager.activateSource() for multicastSessionId: " + multicastSessionId + " got IOException: " + var7);
         }
      }

      AnnouncementManager am = this.findOrCreateAnnouncementManager(multicastSessionId);
      if (!this.blocked.get()) {
         am.unblockAnnouncements();
         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager UNBLOCKED for multicastSessionId: " + multicastSessionId);
         }
      } else if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager BLOCKED for multicastSessionId: " + multicastSessionId);
      }

   }

   public boolean isBlocked() {
      return this.blocked.get();
   }

   public void startBulkUpdate() {
      this.setOnBulkUpdate(true);
   }

   public void endBulkUpdate() {
      this.setOnBulkUpdate(false);
   }

   public synchronized void setOnBulkUpdate(boolean value) {
      this.onBulkUpdate = value;
   }

   public synchronized boolean getOnBulkUpdate() {
      return this.onBulkUpdate;
   }

   void deactivateSource(MulticastSessionId multicastSessionId) {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("*** PartitionAwareSenderManger.deactivateSource() DE-ACTIVATING MULTICAST_SESSION: " + multicastSessionId + " ***");
      }

      MulticastSessionsStatusMessage pscm;
      synchronized(this.lock) {
         pscm = new MulticastSessionsStatusMessage(_nextChangeNumber.incrementAndGet());
         this.inactiveMulticastSessionIds.put(multicastSessionId, multicastSessionId);
         this.activeMulticastSessionIds.remove(multicastSessionId);
         Iterator var4 = this.activeMulticastSessionIds.keySet().iterator();

         while(true) {
            MulticastSessionId key;
            if (!var4.hasNext()) {
               var4 = this.inactiveMulticastSessionIds.keySet().iterator();

               while(var4.hasNext()) {
                  key = (MulticastSessionId)var4.next();
                  pscm.markAsInactive(key);
               }

               ClusterMessagesManager.theOne().replaceItem(pscm);
               if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
                  ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager.deactivateSource() Updated MulticastSessionStatus: " + pscm + " for multicastSessionId: " + multicastSessionId);
               }
               break;
            }

            key = (MulticastSessionId)var4.next();
            pscm.markAsActive(key);
         }
      }

      try {
         this.getOrCreateMulticastSession().send(new MulticastSessionStoppedMessage(pscm));
      } catch (IOException var7) {
         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager.deactivateSource() for multicastSessionId: " + multicastSessionId + " got IOException: " + var7);
         }
      }

      AnnouncementManager am = (AnnouncementManager)this.knownAnnouncementManagers.remove(multicastSessionId);
      if (am != null) {
         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterAnnouncementsDebugLogger.debug("PartitionAwareSenderManager shutting down AnnouncementManager for multicastSessionId: " + multicastSessionId);
         }

         am.shutdown();
      }

   }

   void register(MulticastSessionId multicastSessionId) {
      synchronized(this.lock) {
         this.inactiveMulticastSessionIds.put(multicastSessionId, multicastSessionId);
         this.activeMulticastSessionIds.remove(multicastSessionId);
      }
   }

   boolean isMulticastSessionInactive(MulticastSessionId multicastSessionId) {
      if (!multicastSessionId.equals(MulticastSessionIDConstants.ANNOUNCEMENT_MANAGER_ID) && !multicastSessionId.equals(MulticastSessionIDConstants.ATTRIBUTE_MANAGER_ID) && !multicastSessionId.equals(MulticastSessionIDConstants.HEARTBEAT_SENDER_ID) && !multicastSessionId.equals(MulticastSessionIDConstants.MEMBER_MANAGER_ID) && !multicastSessionId.isCustomMulticastSession()) {
         return !this.activeMulticastSessionIds.contains(multicastSessionId) && !this.inactiveMulticastSessionIds.contains(multicastSessionId) ? true : this.inactiveMulticastSessionIds.contains(multicastSessionId);
      } else {
         return false;
      }
   }

   public AnnouncementManager findOrCreateAnnouncementManager(MulticastSessionId multicastSessionId) {
      AnnouncementManager am = (AnnouncementManager)this.knownAnnouncementManagers.get(multicastSessionId);
      if (am == null) {
         synchronized(this.knownAnnouncementManagers) {
            am = (AnnouncementManager)this.knownAnnouncementManagers.get(multicastSessionId);
            if (am == null) {
               am = new AnnouncementManager(this.ageThreshold, multicastSessionId);
               this.knownAnnouncementManagers.put(multicastSessionId, am);
            }
         }
      }

      return am;
   }

   void blockAllAnnouncements() {
      if (this.blocked.compareAndSet(false, true)) {
         synchronized(this.knownAnnouncementManagers) {
            Iterator var2 = this.knownAnnouncementManagers.values().iterator();

            while(var2.hasNext()) {
               AnnouncementManager am = (AnnouncementManager)var2.next();
               am.blockAnnouncements();
            }
         }
      }

   }

   void unblockAllAnnouncements() {
      if (this.blocked.compareAndSet(true, false)) {
         synchronized(this.knownAnnouncementManagers) {
            Iterator var2 = this.knownAnnouncementManagers.values().iterator();

            while(var2.hasNext()) {
               AnnouncementManager am = (AnnouncementManager)var2.next();
               am.unblockAnnouncements();
            }
         }
      }

   }

   void shutdown() {
      synchronized(this.knownAnnouncementManagers) {
         Iterator var2 = this.knownAnnouncementManagers.values().iterator();

         while(var2.hasNext()) {
            AnnouncementManager am = (AnnouncementManager)var2.next();
            am.shutdown();
         }

      }
   }

   private MulticastSession getOrCreateMulticastSession() {
      if (this.session.get() == null) {
         synchronized(this.session) {
            if (this.session.get() == null) {
               this.session.set(ClusterService.getClusterServiceInternal().createMulticastSession((RecoverListener)null, -1));
            }
         }
      }

      return (MulticastSession)this.session.get();
   }

   private static String makeKey(String partitionId, String appName) {
      if (partitionId == null) {
         partitionId = "0";
      }

      return partitionId + ":" + appName;
   }

   public String getCurrentResourceGroupName(String partitionId, String appName) {
      if (appName == null) {
         return "NO_RESOURCE_GROUP";
      } else {
         AppInfo appInfo = (AppInfo)this.appId2AppInfo.get(makeKey(partitionId, appName));
         return appInfo == null ? "NO_RESOURCE_GROUP" : appInfo.getResourceGroupName();
      }
   }

   public String getPartitionNameFromPartitionId(String partitionId) {
      return "DOMAIN";
   }

   private static ResourceGroupMBean[] getResourceGroupsForThisServer(String partitionName) {
      return new ResourceGroupMBean[0];
   }

   private static DomainMBean getDomain() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   static {
      EXPECTED_STATE = RGState.STARTING;
   }

   public static class AppInfo {
      private String partitionId;
      private String resourceGroupName;
      private String appName;

      public AppInfo(String partitionId, String resourceGroupName, String appName) {
         this.partitionId = partitionId;
         this.resourceGroupName = resourceGroupName;
         this.appName = appName;
      }

      public String getPartitionId() {
         return this.partitionId;
      }

      public String getResourceGroupName() {
         return this.resourceGroupName;
      }

      public String getAppName() {
         return this.appName;
      }
   }
}
