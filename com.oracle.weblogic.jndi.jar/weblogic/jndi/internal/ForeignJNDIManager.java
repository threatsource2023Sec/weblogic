package weblogic.jndi.internal;

import java.security.AccessController;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ForeignJNDIProviderMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ForeignJNDIManager implements BeanUpdateListener {
   private ConcurrentHashMap jndiLinkMngrs;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private ForeignJNDIManager() {
      this.jndiLinkMngrs = new ConcurrentHashMap();
   }

   static ForeignJNDIManager getInstance() {
      return ForeignJNDIManager.ForeignJNDIManagerSingletonHolder.singleton;
   }

   static void initialize() {
      getInstance().processForeignJNDIProviderLinks("DOMAIN");
      ManagementService.getRuntimeAccess(kernelId).getDomain().addBeanUpdateListener(getInstance());
   }

   void processForeignJNDIProviderLinks(String partitionName) {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
      String server = runtime.getServer().getName();
      ForeignJNDIProviderMBean[] providerBeans = runtime.getDomain().getForeignJNDIProviders();
      if (providerBeans != null) {
         ForeignJNDIProviderMBean[] var5 = providerBeans;
         int var6 = providerBeans.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ForeignJNDIProviderMBean bean = var5[var7];
            if (this.isForeignJNDILinkTargettedTo(server, bean) && partitionName.equals(ForeignJNDILinkManager.getPartitionName(bean))) {
               this.jndiLinkMngrs.put(bean, new ForeignJNDILinkManager(bean, bean.getForeignJNDILinks()));
            }
         }
      }

   }

   private boolean isForeignJNDILinkTargettedTo(String server, ForeignJNDIProviderMBean bean) {
      TargetMBean[] targets = bean.getTargets();
      if (targets != null && targets.length != 0) {
         TargetMBean[] var4 = targets;
         int var5 = targets.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            TargetMBean target = var4[var6];
            if (target.getServerNames().contains(server)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      if (event.getSourceBean() instanceof DomainMBean) {
         String server = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
         BeanUpdateEvent.PropertyUpdate[] var5 = event.getUpdateList();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            BeanUpdateEvent.PropertyUpdate change = var5[var7];
            Object obj;
            ForeignJNDIProviderMBean bean;
            switch (change.getUpdateType()) {
               case 2:
                  obj = change.getAddedObject();
                  if (obj instanceof ForeignJNDIProviderMBean) {
                     bean = (ForeignJNDIProviderMBean)obj;
                     if (this.isForeignJNDILinkTargettedTo(server, bean)) {
                        this.jndiLinkMngrs.put(bean, new ForeignJNDILinkManager(bean, bean.getForeignJNDILinks()));
                     }
                  }
                  break;
               case 3:
                  obj = change.getRemovedObject();
                  if (obj instanceof ForeignJNDIProviderMBean) {
                     bean = (ForeignJNDIProviderMBean)obj;
                     if (this.isForeignJNDILinkTargettedTo(server, bean)) {
                        ForeignJNDILinkManager linkManager = (ForeignJNDILinkManager)this.jndiLinkMngrs.remove(bean);
                        if (linkManager != null) {
                           linkManager.unbindAll();
                        }
                     }
                  }
            }
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   // $FF: synthetic method
   ForeignJNDIManager(Object x0) {
      this();
   }

   private static class ForeignJNDIManagerSingletonHolder {
      private static final ForeignJNDIManager singleton = new ForeignJNDIManager();
   }
}
