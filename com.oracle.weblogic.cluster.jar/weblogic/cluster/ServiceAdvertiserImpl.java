package weblogic.cluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.internal.PeerInfo;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.DomainMBean;
import weblogic.utils.Debug;

@Service
public final class ServiceAdvertiserImpl implements ServiceAdvertiser {
   private int nextServiceID = 0;
   private Map serviceMap = new HashMap();
   private DomainMBean domain;

   public void offerService(String name, String appId, Object service) throws NamingException {
      this.offerService(name, appId, service, (AdvertisementStatusListener)null);
   }

   public void createSubcontext(String name) throws NamingException {
      this.offerService(name, (String)null, (Object)null, (AdvertisementStatusListener)null);
   }

   public void offerService(String name, String appId, Object service, AdvertisementStatusListener statusListener) throws NamingException {
      String partitionName = getPartitionName();
      this.announceOffer(name, appId, partitionName, service);
   }

   public void replaceService(String name, String appId, Object oldService, Object newService) throws NamingException {
      String partitionName = getPartitionName();
      this.announceReplacement(name, appId, partitionName, oldService, newService);
   }

   public void retractService(String name, String appId, Object service) throws NamingException {
      String partitionName = getPartitionName();
      this.announceReplacement(name, appId, partitionName, service, (Object)null);
   }

   private synchronized void announceOffer(String name, String appId, String partitionName, Object service) throws NamingException {
      int id = this.getNextServiceID();
      ServiceOffer offer = new BasicServiceOffer(id, name, appId, partitionName, service);
      String partitionId = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionId();
      String appName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getApplicationName();
      String applicationId = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getApplicationId();
      String resourceGroupName = PartitionAwareSenderManager.theOne().getCurrentResourceGroupName(partitionId, appName);
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("ServiceAdvertiserImpl.announceOffer partitionId: " + partitionId + ", partitionName: " + partitionName + ", appName: " + appName + ", applicationId: " + applicationId + ", resourceGroupName: " + resourceGroupName + ", name: " + name);
      }

      MulticastSessionId multicastSessionId = new MulticastSessionId(partitionId, resourceGroupName);
      PartitionAwareSenderManager.theOne().findOrCreateAnnouncementManager(multicastSessionId).announce((ServiceRetract)null, offer);
      this.addToMap(new CompositeKey(name, appId, partitionName), service, id);
   }

   private final synchronized void announceReplacement(String name, String appId, String partitionName, Object oldService, Object newService) throws NamingException {
      ServiceRetract retraction = null;
      ServiceOffer offer = null;
      CompositeKey key = new CompositeKey(name, appId, partitionName);
      ServiceRec oldServiceRec = this.getRecordFromMap(key, oldService);
      int oldID = -1;
      int newId;
      if (oldServiceRec != null) {
         newId = oldServiceRec.id;
         oldID = newId;
         if (newService != null) {
            retraction = new ServiceRetract(newId, true);
         } else {
            retraction = new ServiceRetract(newId, false);
         }
      }

      if (newService != null) {
         newId = this.getNextServiceID();
         this.addToMap(key, newService, newId);
         offer = new BasicServiceOffer(newId, name, appId, partitionName, newService, oldID);
      }

      if (retraction != null || offer != null) {
         String partitionId = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionId();
         String appName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getApplicationName();
         String applicationId = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getApplicationId();
         String resourceGroupName = PartitionAwareSenderManager.theOne().getCurrentResourceGroupName(partitionId, appName);
         MulticastSessionId multicastSessionId = new MulticastSessionId(partitionId, resourceGroupName);
         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterAnnouncementsDebugLogger.debug("ServiceAdvertiserImpl.announceReplacement partitionId: " + partitionId + ", partitionName: " + partitionName + ", appName: " + appName + ", applicationId: " + applicationId + ", resourceGroupName: " + resourceGroupName + ", name: " + name);
         }

         PartitionAwareSenderManager.theOne().findOrCreateAnnouncementManager(multicastSessionId).announce(retraction, offer);
         if (retraction != null) {
            this.removeFromMap(key, oldServiceRec);
         }
      }

   }

   private int getNextServiceID() {
      synchronized(this.serviceMap) {
         return this.nextServiceID++;
      }
   }

   private final void addToMap(CompositeKey key, Object service, int id) {
      synchronized(this.serviceMap) {
         Object o = this.serviceMap.get(key);
         List instances = (List)o;
         if (o == null) {
            instances = new ArrayList(1);
            this.serviceMap.put(key, instances);
         }

         ((List)instances).add(new ServiceRec(id, service));
      }
   }

   private final ServiceRec getRecordFromMap(CompositeKey key, Object service) {
      synchronized(this.serviceMap) {
         List instances = (List)this.serviceMap.get(key);
         if (instances == null) {
            return null;
         } else {
            Debug.assertion(instances.size() != 0);
            if (service == null) {
               return (ServiceRec)instances.get(0);
            } else {
               ServiceRec searchRec = new ServiceRec(-1, service);
               int i = instances.indexOf(searchRec);
               return i != -1 ? (ServiceRec)instances.get(i) : null;
            }
         }
      }
   }

   private final void removeFromMap(CompositeKey key, ServiceRec rec) {
      synchronized(this.serviceMap) {
         List instances = (List)this.serviceMap.get(key);
         if (instances != null) {
            int i = instances.indexOf(rec);
            if (i != -1) {
               instances.remove(i);
               if (instances.size() == 0) {
                  this.serviceMap.remove(key);
               }
            }
         }

      }
   }

   synchronized void rewriteServicesAtNewVersion(PeerInfo oldVersion, PeerInfo newVersion) {
      Iterator entries = this.serviceMap.entrySet().iterator();

      while(entries.hasNext()) {
         Map.Entry entry = (Map.Entry)entries.next();
         CompositeKey key = (CompositeKey)entry.getKey();
         List records = (List)entry.getValue();
         if (records != null && records.size() != 0) {
            ServiceRec record = (ServiceRec)records.get(0);

            try {
               if (UpgradeUtils.needsRewrite(record.service, oldVersion, newVersion)) {
                  try {
                     this.announceReplacement(key.name, key.appId, key.partitionName, record.service, record.service);
                  } catch (NamingException var9) {
                  }
               }
            } catch (IOException var10) {
            }
         }
      }

   }

   private static final String getPartitionName() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      return cic != null && !cic.isGlobalRuntime() ? cic.getPartitionName() : null;
   }
}
