package weblogic.cache.util;

import java.util.Set;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.locks.LockManager;
import weblogic.cache.locks.LockMode;

public class DelegatingLockManager implements LockManager {
   protected LockManager delegate;
   protected ExceptionTranslator extrans;

   public DelegatingLockManager(LockManager delegate) {
      this(delegate, (ExceptionTranslator)null);
   }

   public DelegatingLockManager(LockManager delegate, ExceptionTranslator extrans) {
      this.delegate = delegate;
      this.extrans = extrans;
   }

   public LockManager getDelegate() {
      return this.delegate;
   }

   public ExceptionTranslator getExceptionTranslator() {
      return this.extrans;
   }

   private RuntimeException translate(CacheRuntimeException ce) {
      return (RuntimeException)(this.extrans == null ? ce : this.extrans.fromInternal(ce));
   }

   public LockManager getInnermostDelegate() {
      return this.getDelegate() instanceof DelegatingLockManager ? ((DelegatingLockManager)this.getDelegate()).getInnermostDelegate() : this.getDelegate();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingLockManager) {
            other = ((DelegatingLockManager)other).getDelegate();
         }

         return this.getDelegate().equals(other);
      }
   }

   public int hashCode() {
      return this.getDelegate().hashCode();
   }

   public boolean tryLock(Object key, long timeout) {
      try {
         return this.delegate.tryLock(key, timeout);
      } catch (CacheRuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public boolean tryLocks(Set keys, long timeout) {
      try {
         return this.delegate.tryLocks(keys, timeout);
      } catch (CacheRuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public boolean tryGuardLock(long timeout) {
      try {
         return this.delegate.tryGuardLock(timeout);
      } catch (CacheRuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public boolean tryLock(Object key, Object owner, LockMode mode, long timeout) {
      try {
         return this.delegate.tryLock(key, owner, mode, timeout);
      } catch (CacheRuntimeException var7) {
         throw this.translate(var7);
      }
   }

   public boolean tryLocks(Set keys, Object owner, LockMode mode, long timeout) {
      try {
         return this.delegate.tryLocks(keys, owner, mode, timeout);
      } catch (CacheRuntimeException var7) {
         throw this.translate(var7);
      }
   }

   public boolean tryGuardLock(Object owner, LockMode mode, long timeout) {
      try {
         return this.delegate.tryGuardLock(owner, mode, timeout);
      } catch (CacheRuntimeException var6) {
         throw this.translate(var6);
      }
   }

   public void releaseLock(Object key) {
      try {
         this.delegate.releaseLock(key);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void releaseLocks(Set keys) {
      try {
         this.delegate.releaseLocks(keys);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void releaseGuardLock() {
      try {
         this.delegate.releaseGuardLock();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void releaseLock(Object key, Object owner, LockMode mode) {
      try {
         this.delegate.releaseLock(key, owner, mode);
      } catch (CacheRuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public void releaseLocks(Set keys, Object owner, LockMode mode) {
      try {
         this.delegate.releaseLocks(keys, owner, mode);
      } catch (CacheRuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public void releaseGuardLock(Object owner, LockMode mode) {
      try {
         this.delegate.releaseGuardLock(owner, mode);
      } catch (CacheRuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public boolean isLockOwner(Object key) {
      try {
         return this.delegate.isLockOwner(key);
      } catch (CacheRuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean isLockOwner(Object key, Object owner, LockMode mode) {
      try {
         return this.delegate.isLockOwner(key, owner, mode);
      } catch (CacheRuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public boolean isGuardLockOwner() {
      try {
         return this.delegate.isGuardLockOwner();
      } catch (CacheRuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean isGuardLockOwner(Object owner, LockMode mode) {
      try {
         return this.delegate.isGuardLockOwner(owner, mode);
      } catch (CacheRuntimeException var4) {
         throw this.translate(var4);
      }
   }
}
