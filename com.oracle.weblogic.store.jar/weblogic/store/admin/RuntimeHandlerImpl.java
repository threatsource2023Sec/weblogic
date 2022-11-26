package weblogic.store.admin;

import java.security.AccessController;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.RuntimeHandler;
import weblogic.store.RuntimeUpdater;
import weblogic.store.internal.PersistentStoreImpl;

public class RuntimeHandlerImpl implements RuntimeHandler {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public RuntimeUpdater createStoreMBean(PersistentStoreImpl store) throws PersistentStoreException {
      return JMXUtils.createStoreMBean(store);
   }

   public void registerConnectionMBean(RuntimeUpdater mbean, PersistentStoreConnection conn) throws PersistentStoreException {
      JMXUtils.registerConnectionMBean((PersistentStoreRuntimeMBeanImpl)mbean, conn);
   }

   public void unregisterConnectionMBean(RuntimeUpdater mbean, PersistentStoreConnection conn) throws PersistentStoreException {
      JMXUtils.unregisterConnectionMBean((PersistentStoreRuntimeMBeanImpl)mbean, conn);
   }

   public void unregisterStoreMBean(RuntimeUpdater mbean) throws PersistentStoreException {
      JMXUtils.unregisterStoreMBean((PersistentStoreRuntimeMBeanImpl)mbean);
   }

   public long getJTAAbandonTimeoutMillis() {
      long retVal = 0L;
      if (KernelStatus.isServer()) {
         DomainMBean dom = ManagementService.getRuntimeAccess(kernelId).getDomain();
         retVal = (long)dom.getJTA().getAbandonTimeoutSeconds() * 1000L;
         retVal += 86400000L;
         retVal = Math.max(345600000L, retVal);
      } else {
         retVal = 86400000L;
      }

      return retVal;
   }

   public String getDomainName() {
      String retVal = ManagementService.getRuntimeAccess(kernelId).getDomain().getName();
      return retVal == null ? "" : retVal;
   }
}
