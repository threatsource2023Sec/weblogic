package weblogic.cache.tx;

import weblogic.cache.CacheEntry;
import weblogic.cache.CacheMap;
import weblogic.cache.configuration.CacheProperties;
import weblogic.cache.locks.LockMode;
import weblogic.cache.session.Workspace;

public class PessimisticMapAdapter extends AbstractTransactionalMapAdapter {
   public PessimisticMapAdapter(CacheMap delegate, JTAIntegration jta) {
      super(delegate, jta);
   }

   protected Object getReadValueMiss(Object key, Workspace workspace) {
      TransactionalWorkspace tspace = (TransactionalWorkspace)workspace;
      if (tspace.getLockedKeys().contains(key)) {
         return super.getReadValueMiss(key, tspace);
      } else if (this.getIsolation() != CacheProperties.TransactionIsolationValue.RepeatableRead) {
         return this.stdGet(key, tspace);
      } else {
         boolean locked = this.lock(key, tspace.getId(), LockMode.LOCK_EXCLUSIVE);

         try {
            if (locked) {
               tspace.getLockedKeys().add(key);
            }

            return super.getReadValueMiss(key, tspace);
         } catch (RuntimeException var6) {
            if (locked) {
               this.unlock(key, tspace.getId(), LockMode.LOCK_EXCLUSIVE);
               tspace.getLockedKeys().remove(key);
            }

            throw var6;
         }
      }
   }

   protected CacheEntry getReadMiss(Object key, Workspace workspace) {
      TransactionalWorkspace tspace = (TransactionalWorkspace)workspace;
      if (tspace.getLockedKeys().contains(key)) {
         return super.getReadMiss(key, tspace);
      } else if (this.getIsolation() != CacheProperties.TransactionIsolationValue.RepeatableRead) {
         return this.stdGetEntry(key, tspace);
      } else {
         boolean locked = this.lock(key, tspace.getId(), LockMode.LOCK_EXCLUSIVE);

         try {
            if (locked) {
               tspace.getLockedKeys().add(key);
            }

            return super.getReadMiss(key, tspace);
         } catch (RuntimeException var6) {
            if (locked) {
               this.unlock(key, tspace.getId(), LockMode.LOCK_EXCLUSIVE);
               tspace.getLockedKeys().remove(key);
            }

            throw var6;
         }
      }
   }

   protected CacheEntry getWriteMiss(Object key, Workspace workspace) {
      TransactionalWorkspace tspace = (TransactionalWorkspace)workspace;
      if (tspace.getLockedKeys().contains(key)) {
         return super.getWriteMiss(key, tspace);
      } else {
         boolean locked = this.lock(key, tspace.getId(), LockMode.LOCK_EXCLUSIVE);

         try {
            if (locked) {
               tspace.getLockedKeys().add(key);
            }

            return super.getWriteMiss(key, tspace);
         } catch (RuntimeException var6) {
            if (locked) {
               this.unlock(key, tspace.getId(), LockMode.LOCK_EXCLUSIVE);
               tspace.getLockedKeys().remove(key);
            }

            throw var6;
         }
      }
   }
}
