package weblogic.security.principal;

import java.security.AccessController;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceRuntimeException;

public class WLSPrincipalConfigurationDelegateImpl extends PrincipalConfigurationDelegate {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean equalsCaseInsensitive = false;
   private boolean equalsCompareDnAndGuid = false;
   private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
   private final Lock readlock;
   private final Lock writelock;

   public WLSPrincipalConfigurationDelegateImpl() {
      this.readlock = this.readWriteLock.readLock();
      this.writelock = this.readWriteLock.writeLock();
      this.initialize();
   }

   private void initialize() {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
      if (runtime == null) {
         throw new SecurityServiceRuntimeException("The WLS ManagementService has not been initialized.");
      } else {
         SecurityConfigurationMBean configMBean = runtime.getDomain().getSecurityConfiguration();
         configMBean.addBeanUpdateListener(this.createBeanUpdateListener());

         try {
            this.writelock.lock();
            this.equalsCaseInsensitive = configMBean.isPrincipalEqualsCaseInsensitive();
            this.equalsCompareDnAndGuid = configMBean.isPrincipalEqualsCompareDnAndGuid();
         } finally {
            this.writelock.unlock();
         }

      }
   }

   public boolean isEqualsCaseInsensitive() {
      boolean var1;
      try {
         this.readlock.lock();
         var1 = this.equalsCaseInsensitive;
      } finally {
         this.readlock.unlock();
      }

      return var1;
   }

   public boolean isEqualsCompareDnAndGuid() {
      boolean var1;
      try {
         this.readlock.lock();
         var1 = this.equalsCompareDnAndGuid;
      } finally {
         this.readlock.unlock();
      }

      return var1;
   }

   private BeanUpdateListener createBeanUpdateListener() {
      return new BeanUpdateListener() {
         public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         }

         public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
            if (event.getSourceBean() instanceof SecurityConfigurationMBean) {
               BeanUpdateEvent.PropertyUpdate[] propertyUpdates = event.getUpdateList();

               for(int i = 0; i < propertyUpdates.length; ++i) {
                  if (propertyUpdates[i].getPropertyName().equalsIgnoreCase("PrincipalEqualsCaseInsensitive") || propertyUpdates[i].getPropertyName().equalsIgnoreCase("PrincipalEqualsCompareDnAndGuid")) {
                     try {
                        FlagsHolder fh = WLSPrincipalConfigurationDelegateImpl.this.new FlagsHolder(propertyUpdates, event);
                        WLSPrincipalConfigurationDelegateImpl.this.writelock.lock();
                        if (fh.caseInsensitive != null) {
                           WLSPrincipalConfigurationDelegateImpl.this.equalsCaseInsensitive = fh.caseInsensitive;
                        }

                        if (fh.compareDnAndGuid != null) {
                           WLSPrincipalConfigurationDelegateImpl.this.equalsCompareDnAndGuid = fh.compareDnAndGuid;
                        }
                     } finally {
                        WLSPrincipalConfigurationDelegateImpl.this.writelock.unlock();
                     }

                     return;
                  }
               }
            }

         }

         public void rollbackUpdate(BeanUpdateEvent event) {
         }
      };
   }

   private class FlagsHolder {
      Boolean caseInsensitive = null;
      Boolean compareDnAndGuid = null;

      FlagsHolder(BeanUpdateEvent.PropertyUpdate[] properties, BeanUpdateEvent event) {
         for(int i = 0; i < properties.length; ++i) {
            if (properties[i].getPropertyName().equalsIgnoreCase("PrincipalEqualsCaseInsensitive")) {
               this.caseInsensitive = new Boolean(((SecurityConfigurationMBean)event.getSourceBean()).isPrincipalEqualsCaseInsensitive());
            } else if (properties[i].getPropertyName().equalsIgnoreCase("PrincipalEqualsCompareDnAndGuid")) {
               this.compareDnAndGuid = new Boolean(((SecurityConfigurationMBean)event.getSourceBean()).isPrincipalEqualsCompareDnAndGuid());
            }
         }

      }
   }
}
