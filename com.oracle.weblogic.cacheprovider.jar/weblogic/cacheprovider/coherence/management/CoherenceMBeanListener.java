package weblogic.cacheprovider.coherence.management;

import weblogic.cacheprovider.CacheProviderServerService;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.CoherenceManagementClusterMBean;
import weblogic.management.configuration.DomainMBean;

public class CoherenceMBeanListener implements BeanUpdateListener {
   private CacheProviderServerService m_svc;

   public CoherenceMBeanListener(CacheProviderServerService cpss) {
      this.m_svc = cpss;
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      if (event.getProposedBean() instanceof DomainMBean) {
         BeanUpdateEvent.PropertyUpdate[] changes = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var3 = changes;
         int var4 = changes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BeanUpdateEvent.PropertyUpdate change = var3[var5];
            CoherenceManagementClusterMBean cmcb;
            if (change.getAddedObject() instanceof CoherenceManagementClusterMBean) {
               cmcb = (CoherenceManagementClusterMBean)change.getAddedObject();

               try {
                  this.m_svc.registerCoherenceMgmtClusterMBean(cmcb);
               } catch (ManagementException var12) {
                  throw new BeanUpdateFailedException("Failed to create RuntimeMBean for CoherenceManagementClusterMBean - " + cmcb.getName(), var12);
               }
            } else {
               CoherenceClusterSystemResourceMBean ccsr;
               if (change.getAddedObject() instanceof CoherenceClusterSystemResourceMBean) {
                  ccsr = (CoherenceClusterSystemResourceMBean)change.getAddedObject();

                  try {
                     this.m_svc.registerCoherenceClusterSystemResourceMBean(ccsr);
                  } catch (ManagementException var11) {
                     throw new BeanUpdateFailedException("Failed to create RuntimeMBean for CoherenceClusterSystemResourceMBean - " + ccsr.getName(), var11);
                  }
               } else if (change.getRemovedObject() instanceof CoherenceManagementClusterMBean) {
                  cmcb = (CoherenceManagementClusterMBean)change.getRemovedObject();

                  try {
                     this.m_svc.unregisterCoherenceClusterMetricsRuntimeMBean(cmcb.getName());
                  } catch (ManagementException var10) {
                     throw new BeanUpdateFailedException("Failed to delete RuntimeMBean for CoherenceManagementClusterMBean - " + cmcb.getName(), var10);
                  }
               } else if (change.getRemovedObject() instanceof CoherenceClusterSystemResourceMBean) {
                  ccsr = (CoherenceClusterSystemResourceMBean)change.getRemovedObject();

                  try {
                     this.m_svc.unregisterCoherenceClusterMetricsRuntimeMBean(ccsr.getName());
                  } catch (ManagementException var9) {
                     throw new BeanUpdateFailedException("Failed to delete RuntimeMBean for CoherenceClusterSystemResourceMBean - " + ccsr.getName(), var9);
                  }
               }
            }
         }
      }

   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
