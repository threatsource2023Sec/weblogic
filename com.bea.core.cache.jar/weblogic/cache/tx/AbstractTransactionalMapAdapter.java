package weblogic.cache.tx;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.transaction.Synchronization;
import weblogic.cache.Action;
import weblogic.cache.ActionTrigger;
import weblogic.cache.CacheEntry;
import weblogic.cache.CacheMap;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.configuration.CacheProperties;
import weblogic.cache.locks.LockFailureException;
import weblogic.cache.locks.LockManager;
import weblogic.cache.locks.LockMode;
import weblogic.cache.session.AbstractWorkspaceMapAdapter;
import weblogic.cache.session.Workspace;
import weblogic.cache.util.DelegatingLockManager;

abstract class AbstractTransactionalMapAdapter extends AbstractWorkspaceMapAdapter implements TransactionalMapAdapter, Synchronization {
   private ThreadLocal _workspace = new ThreadLocal();
   private final JTAIntegration _jta;
   private final LockManager _lockManager;
   private long _lockTimeout = 10000L;
   private CacheProperties.TransactionIsolationValue _iso;
   private static long _idgen = System.currentTimeMillis();

   public AbstractTransactionalMapAdapter(CacheMap delegate, JTAIntegration jta) {
      super(delegate);
      this._iso = CacheProperties.TransactionIsolationValue.RepeatableRead;
      this._jta = jta;
      LockManager lm = delegate.getLockManager();
      if (lm == null) {
         this._lockManager = null;
      } else {
         this._lockManager = new DelegatingLockManager(lm) {
            public boolean tryLock(Object key, long timeout) {
               return this.delegate.tryLock(key, AbstractTransactionalMapAdapter.this.getLockOwner(AbstractTransactionalMapAdapter.this.getTransactionalWorkspace()), LockMode.LOCK_EXCLUSIVE, timeout);
            }

            public boolean tryLocks(Set keys, long timeout) {
               return this.delegate.tryLocks(keys, AbstractTransactionalMapAdapter.this.getLockOwner(AbstractTransactionalMapAdapter.this.getTransactionalWorkspace()), LockMode.LOCK_EXCLUSIVE, timeout);
            }

            public boolean tryGuardLock(long timeout) {
               return this.delegate.tryGuardLock(AbstractTransactionalMapAdapter.this.getLockOwner(AbstractTransactionalMapAdapter.this.getTransactionalWorkspace()), LockMode.LOCK_EXCLUSIVE, timeout);
            }

            public void releaseLock(Object key) {
               this.delegate.releaseLock(key, AbstractTransactionalMapAdapter.this.getLockOwner(AbstractTransactionalMapAdapter.this.getTransactionalWorkspace()), LockMode.LOCK_EXCLUSIVE);
            }

            public void releaseLocks(Set keys) {
               this.delegate.releaseLocks(keys, AbstractTransactionalMapAdapter.this.getLockOwner(AbstractTransactionalMapAdapter.this.getTransactionalWorkspace()), LockMode.LOCK_EXCLUSIVE);
            }

            public void releaseGuardLock() {
               this.delegate.releaseGuardLock(AbstractTransactionalMapAdapter.this.getLockOwner(AbstractTransactionalMapAdapter.this.getTransactionalWorkspace()), LockMode.LOCK_EXCLUSIVE);
            }

            public boolean isLockOwner(Object key) {
               return this.delegate.isLockOwner(key, AbstractTransactionalMapAdapter.this.getLockOwner(AbstractTransactionalMapAdapter.this.getTransactionalWorkspace()), LockMode.LOCK_EXCLUSIVE);
            }

            public boolean isGuardLockOwner() {
               return this.delegate.isGuardLockOwner(AbstractTransactionalMapAdapter.this.getLockOwner(AbstractTransactionalMapAdapter.this.getTransactionalWorkspace()), LockMode.LOCK_EXCLUSIVE);
            }
         };
      }

   }

   protected Workspace getWorkspace() {
      return this.getTransactionalWorkspace();
   }

   public TransactionalWorkspace getTransactionalWorkspace() {
      TransactionalWorkspace space = (TransactionalWorkspace)this._workspace.get();
      if (space == null && this.syncTransaction()) {
         space = this.newWorkspace(this.newUniqueId());
         this._workspace.set(space);
      }

      return space;
   }

   protected TransactionalWorkspace newWorkspace(Object id) {
      return new TransactionalWorkspaceImpl(id);
   }

   private Object newUniqueId() {
      Class var1 = AbstractTransactionalMapAdapter.class;
      synchronized(AbstractTransactionalMapAdapter.class) {
         return Long.valueOf((long)(_idgen++));
      }
   }

   public JTAIntegration getJTAIntegration() {
      return this._jta;
   }

   public CacheProperties.TransactionIsolationValue getIsolation() {
      return this._iso;
   }

   public void setIsolation(CacheProperties.TransactionIsolationValue iso) {
      this._iso = iso;
   }

   public long getLockTimeout() {
      return this._lockTimeout;
   }

   public void setLockTimeout(long lockTimeout) {
      this._lockTimeout = lockTimeout;
   }

   private boolean syncTransaction() {
      try {
         if (this._jta.getTransactionStatus() == 6) {
            return false;
         } else {
            this._jta.registerSynchronization(this);
            return true;
         }
      } catch (RuntimeException var2) {
         throw var2;
      } catch (Exception var3) {
         throw new CacheRuntimeException(var3);
      }
   }

   public void beforeCompletion() {
      TransactionalWorkspace space = (TransactionalWorkspace)this._workspace.get();
      this.lockGuard(space.getId(), LockMode.LOCK_EXCLUSIVE);
      space.setCommitAttempt(true);
      Action action = this.newCommitAction(space);
      space.setActionTrigger(super.prepare(action));
   }

   public void afterCompletion(int status) {
      TransactionalWorkspace space = (TransactionalWorkspace)this._workspace.get();
      this._workspace.remove();
      ActionTrigger trigger = space.getActionTrigger();
      if (trigger != null) {
         if (status == 4) {
            trigger.run();
         } else {
            trigger.close();
         }
      } else if (!space.isCommitAttempt() && !space.getLockedKeys().isEmpty()) {
         this.unlockAll(space.getLockedKeys(), space.getId(), LockMode.LOCK_EXCLUSIVE);
      }

      if (space.isCommitAttempt()) {
         this.unlockGuard(space.getId(), LockMode.LOCK_EXCLUSIVE);
      }

   }

   protected Action newCommitAction(TransactionalWorkspace workspace) {
      return new CommitAction(workspace, this._lockTimeout);
   }

   protected CacheEntry stdGetEntry(Object key, Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lock(key, owner, LockMode.LOCK_SHARED);

      CacheEntry var4;
      try {
         var4 = super.stdGetEntry(key, space);
      } finally {
         this.unlock(key, owner, LockMode.LOCK_SHARED);
      }

      return var4;
   }

   protected Object stdPutIfAbsent(Object key, Object value, Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lock(key, owner, LockMode.LOCK_EXCLUSIVE);

      Object var5;
      try {
         var5 = super.stdPutIfAbsent(key, value, space);
      } finally {
         this.unlock(key, owner, LockMode.LOCK_EXCLUSIVE);
      }

      return var5;
   }

   protected boolean stdRemove(Object key, Object value, Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lock(key, owner, LockMode.LOCK_EXCLUSIVE);

      boolean var5;
      try {
         var5 = super.stdRemove(key, value, space);
      } finally {
         this.unlock(key, owner, LockMode.LOCK_EXCLUSIVE);
      }

      return var5;
   }

   protected boolean stdReplace(Object key, Object oldValue, Object newValue, Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lock(key, owner, LockMode.LOCK_EXCLUSIVE);

      boolean var6;
      try {
         var6 = super.stdReplace(key, oldValue, newValue, space);
      } finally {
         this.unlock(key, owner, LockMode.LOCK_EXCLUSIVE);
      }

      return var6;
   }

   protected Object stdReplace(Object key, Object newValue, Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lock(key, owner, LockMode.LOCK_EXCLUSIVE);

      Object var5;
      try {
         var5 = super.stdReplace(key, newValue, space);
      } finally {
         this.unlock(key, owner, LockMode.LOCK_EXCLUSIVE);
      }

      return var5;
   }

   protected int stdSize(Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lockGuard(owner, LockMode.LOCK_SHARED);

      int var3;
      try {
         var3 = super.stdSize(space);
      } finally {
         this.unlockGuard(owner, LockMode.LOCK_SHARED);
      }

      return var3;
   }

   protected boolean stdContainsKey(Object key, Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lock(key, owner, LockMode.LOCK_SHARED);

      boolean var4;
      try {
         var4 = super.stdContainsKey(key, space);
      } finally {
         this.unlock(key, owner, LockMode.LOCK_SHARED);
      }

      return var4;
   }

   protected boolean stdContainsValue(Object value, Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lockGuard(owner, LockMode.LOCK_SHARED);

      boolean var4;
      try {
         var4 = super.stdContainsValue(value, space);
      } finally {
         this.unlockGuard(owner, LockMode.LOCK_SHARED);
      }

      return var4;
   }

   protected Object stdGet(Object key, Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lock(key, owner, LockMode.LOCK_SHARED);

      Object var4;
      try {
         var4 = super.stdGet(key, space);
      } finally {
         this.unlock(key, owner, LockMode.LOCK_SHARED);
      }

      return var4;
   }

   protected Object stdPut(Object key, Object value, Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lock(key, owner, LockMode.LOCK_EXCLUSIVE);

      Object var5;
      try {
         var5 = super.stdPut(key, value, space);
      } finally {
         this.unlock(key, owner, LockMode.LOCK_EXCLUSIVE);
      }

      return var5;
   }

   protected Object stdRemove(Object key, Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lock(key, owner, LockMode.LOCK_EXCLUSIVE);

      Object var4;
      try {
         var4 = super.stdRemove(key, space);
      } finally {
         this.unlock(key, owner, LockMode.LOCK_EXCLUSIVE);
      }

      return var4;
   }

   protected void stdPutAll(Map map, Workspace space) {
      Set keys = map.keySet();
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lockAll(keys, owner, LockMode.LOCK_EXCLUSIVE);

      try {
         super.stdPutAll(map, space);
      } finally {
         this.unlockAll(keys, owner, LockMode.LOCK_EXCLUSIVE);
      }

   }

   protected void stdClear(Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lockGuard(owner, LockMode.LOCK_EXCLUSIVE);

      try {
         super.stdClear(space);
      } finally {
         this.unlockGuard(owner, LockMode.LOCK_EXCLUSIVE);
      }

   }

   protected Set stdEntrySet(Workspace space) {
      Object owner = this.getLockOwner((TransactionalWorkspace)space);
      this.lockGuard(owner, LockMode.LOCK_SHARED);

      AbstractSet var4;
      try {
         final Set entries = new HashSet(super.stdEntrySet(space));
         var4 = new AbstractSet() {
            public int size() {
               return entries.size();
            }

            public boolean contains(Object entry) {
               return entries.contains(entry);
            }

            public boolean remove(Object entry) {
               if (!entries.remove(entry)) {
                  return false;
               } else {
                  AbstractTransactionalMapAdapter.this.stdRemove(((Map.Entry)entry).getKey(), AbstractTransactionalMapAdapter.this.getTransactionalWorkspace());
                  return true;
               }
            }

            public boolean add(Map.Entry entry) {
               if (!entries.add(entry)) {
                  return false;
               } else {
                  AbstractTransactionalMapAdapter.this.stdPut(entry.getKey(), entry.getValue(), AbstractTransactionalMapAdapter.this.getTransactionalWorkspace());
                  return true;
               }
            }

            public Iterator iterator() {
               final Iterator itr = entries.iterator();
               return new Iterator() {
                  private Object _last;

                  public boolean hasNext() {
                     return itr.hasNext();
                  }

                  public Map.Entry next() {
                     Map.Entry ret = (Map.Entry)itr.next();
                     this._last = ret.getKey();
                     return ret;
                  }

                  public void remove() {
                     itr.remove();
                     AbstractTransactionalMapAdapter.this.stdRemove(this._last, AbstractTransactionalMapAdapter.this.getTransactionalWorkspace());
                  }
               };
            }
         };
      } finally {
         this.unlockGuard(owner, LockMode.LOCK_SHARED);
      }

      return var4;
   }

   public LockManager getLockManager() {
      return this._lockManager;
   }

   protected Object getLockOwner(TransactionalWorkspace space) {
      return space != null ? space.getId() : Thread.currentThread();
   }

   protected boolean lock(Object key, Object owner, LockMode mode) {
      if (this._iso == CacheProperties.TransactionIsolationValue.ReadUncommitted) {
         return false;
      } else {
         LockManager lm = super.getLockManager();
         if (lm == null) {
            return false;
         } else if (!lm.tryLock(key, owner, mode, this._lockTimeout)) {
            throw new LockFailureException();
         } else {
            return true;
         }
      }
   }

   protected boolean unlock(Object key, Object owner, LockMode mode) {
      if (this._iso == CacheProperties.TransactionIsolationValue.ReadUncommitted) {
         return false;
      } else {
         LockManager lm = super.getLockManager();
         if (lm == null) {
            return false;
         } else {
            lm.releaseLock(key, owner, mode);
            return true;
         }
      }
   }

   protected boolean lockAll(Set keys, Object owner, LockMode mode) {
      if (this._iso == CacheProperties.TransactionIsolationValue.ReadUncommitted) {
         return false;
      } else {
         LockManager lm = super.getLockManager();
         if (lm == null) {
            return false;
         } else if (!lm.tryLocks(keys, owner, mode, this._lockTimeout)) {
            throw new LockFailureException();
         } else {
            return true;
         }
      }
   }

   protected boolean unlockAll(Set keys, Object owner, LockMode mode) {
      if (this._iso == CacheProperties.TransactionIsolationValue.ReadUncommitted) {
         return false;
      } else {
         LockManager lm = super.getLockManager();
         if (lm == null) {
            return false;
         } else {
            lm.releaseLocks(keys, owner, mode);
            return true;
         }
      }
   }

   protected boolean lockGuard(Object owner, LockMode mode) {
      if (this._iso == CacheProperties.TransactionIsolationValue.ReadUncommitted) {
         return false;
      } else {
         LockManager lm = super.getLockManager();
         if (lm == null) {
            return false;
         } else if (!lm.tryGuardLock(owner, mode, this._lockTimeout)) {
            throw new LockFailureException();
         } else {
            return true;
         }
      }
   }

   protected boolean unlockGuard(Object owner, LockMode mode) {
      if (this._iso == CacheProperties.TransactionIsolationValue.ReadUncommitted) {
         return false;
      } else {
         LockManager lm = this.getLockManager();
         if (lm == null) {
            return false;
         } else {
            lm.releaseGuardLock(owner, mode);
            return true;
         }
      }
   }
}
