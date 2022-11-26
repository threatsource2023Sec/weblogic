package weblogic.ejb.container.timer;

import java.security.AccessController;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.logging.Loggable;
import weblogic.management.provider.ManagementService;
import weblogic.scheduler.ejb.ClusteredTimerManagerFactory;
import weblogic.scheduler.ejb.ConfigurationException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.PersistentStoreManager;

public final class EJBTimerManagerFactory {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private EJBTimerManagerFactory() {
   }

   public static void validateTimerConfig(BeanInfo bi) throws WLDeploymentException {
      if (bi.isClusteredTimers()) {
         try {
            ClusteredTimerManagerFactory.getInstance().ensureIsOperational();
         } catch (ConfigurationException var3) {
            throw new WLDeploymentException(var3.getMessage());
         }
      } else {
         String storeName = bi.getTimerStoreName();
         if (storeName != null && PersistentStoreManager.getManager().getStoreByLogicalName(storeName) == null) {
            Loggable l = EJBLogger.logUnableToFindPersistentStoreLoggable(bi.getDisplayName(), storeName, ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getName());
            throw new WLDeploymentException(l.getMessageText());
         }
      }

   }

   public static TimerManager createEJBTimerManager(BeanManager bm, boolean isClustered) {
      return (TimerManager)(isClustered ? new ClusteredEJBTimerManager(bm) : new EJBTimerManager(bm));
   }

   public static void removeAllTimers(BeanInfo bi) {
      if (bi.isClusteredTimers()) {
         ClusteredEJBTimerManager.removeAllTimers(bi);
      } else {
         EJBTimerManager.removeAllTimers(bi);
      }

   }
}
