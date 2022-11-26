package weblogic.xml.util.cache.entitycache;

import java.io.Serializable;
import java.security.AccessController;
import java.util.Enumeration;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.EntityCacheCurrentStateRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class EntityCacheCurrentStats extends EntityCacheStats implements EntityCacheCurrentStateRuntimeMBean {
   private static final long serialVersionUID = 3556632666101847510L;

   Statistics getStats() {
      this.cache.currentStats.clear();
      Enumeration i = this.cache.entries.keys();

      while(i.hasMoreElements()) {
         Serializable key = (Serializable)i.nextElement();
         CacheEntry ce = (CacheEntry)this.cache.entries.get(key);
         this.cache.currentStats.addEntry(ce);
         if (ce.isPersisted()) {
            this.cache.currentStats.writeEntry(ce);
         }
      }

      return this.cache.currentStats;
   }

   boolean changesMade() {
      return this.cache.statsCurrentModification;
   }

   public EntityCacheCurrentStats(EntityCache cache) throws ManagementException {
      super("EntityCacheCurrentState_" + ManagementService.getRuntimeAccess((AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction())).getServerName(), (RuntimeMBean)null, cache);
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setEntityCacheCurrentStateRuntime(this);
   }

   public synchronized long getMemoryUsage() {
      return this.getStats().getTotalEntries();
   }

   public synchronized long getDiskUsage() {
      return this.getStats().getTotalEntries();
   }
}
