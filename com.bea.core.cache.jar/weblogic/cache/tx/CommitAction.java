package weblogic.cache.tx;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import weblogic.cache.Action;
import weblogic.cache.CacheEntry;
import weblogic.cache.locks.LockFailureException;
import weblogic.cache.locks.LockKeyComparator;
import weblogic.cache.locks.LockManager;
import weblogic.cache.locks.LockMode;
import weblogic.cache.session.WorkspaceFlushAction;
import weblogic.cache.util.Preparable;

class CommitAction extends WorkspaceFlushAction implements Preparable {
   protected final Object id;
   protected final SortedSet lockedKeys;
   protected final long lockTimeout;
   private Map _rollbacks;
   private Set _rollbackAdds;
   private Set _rollbackRemoves;

   public CommitAction(TransactionalWorkspace workspace, long lockTimeout) {
      super(workspace);
      this.id = workspace.getId();
      this.lockedKeys = workspace.getLockedKeys();
      this.lockTimeout = lockTimeout;
   }

   protected CommitAction(Object id, SortedSet lockedKeys, long lockTimeout, Map adds, Map updates, Set removes, boolean clear) {
      super(adds, updates, removes, clear);
      this.id = id;
      this.lockedKeys = lockedKeys;
      this.lockTimeout = lockTimeout;
   }

   public long getLockTimeout() {
      return this.lockTimeout;
   }

   public void prepare() {
      this.lock();
      super.run();
   }

   protected void lock() {
      LockManager lm = this.cache.getLockManager();
      if (lm != null) {
         Set unlocked = new TreeSet(new LockKeyComparator());
         Iterator var3 = this.adds.keySet().iterator();

         Object key;
         while(var3.hasNext()) {
            key = var3.next();
            if (!this.lockedKeys.contains(key)) {
               unlocked.add(key);
            } else if (!lm.isLockOwner(key, this.id, LockMode.LOCK_EXCLUSIVE)) {
               throw new LockFailureException();
            }
         }

         var3 = this.updates.keySet().iterator();

         while(var3.hasNext()) {
            key = var3.next();
            if (!this.lockedKeys.contains(key)) {
               unlocked.add(key);
            } else if (!lm.isLockOwner(key, this.id, LockMode.LOCK_EXCLUSIVE)) {
               throw new LockFailureException();
            }
         }

         var3 = this.removes.iterator();

         while(var3.hasNext()) {
            key = var3.next();
            if (!this.lockedKeys.contains(key)) {
               unlocked.add(key);
            } else if (!lm.isLockOwner(key, this.id, LockMode.LOCK_EXCLUSIVE)) {
               throw new LockFailureException();
            }
         }

         if (!unlocked.isEmpty()) {
            if (!lm.tryLocks(unlocked, this.id, LockMode.LOCK_EXCLUSIVE, this.lockTimeout)) {
               throw new LockFailureException();
            }

            this.lockedKeys.addAll(unlocked);
         }

      }
   }

   public Object run() {
      Iterator var1;
      if (this._rollbacks != null) {
         var1 = this._rollbacks.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry rentry = (Map.Entry)var1.next();
            CacheEntry centry = this.cache.getEntry(rentry.getKey());
            if (centry == null) {
               this.cache.put(rentry.getKey(), rentry.getValue());
            } else {
               centry.setValue(rentry.getValue());
               centry.setVersion(centry.getVersion() - 2L);
            }
         }
      }

      if (this._rollbackAdds != null) {
         var1 = this._rollbackAdds.iterator();

         while(var1.hasNext()) {
            CacheEntry entry = (CacheEntry)var1.next();
            this.cache.restoreEntry(entry);
         }
      }

      if (this._rollbackRemoves != null) {
         var1 = this._rollbackRemoves.iterator();

         while(var1.hasNext()) {
            Object key = var1.next();
            this.cache.remove(key);
         }
      }

      return null;
   }

   public void close() {
      LockManager lm = this.cache.getLockManager();
      if (lm != null && !this.lockedKeys.isEmpty()) {
         lm.releaseLocks(this.lockedKeys, this.id, LockMode.LOCK_EXCLUSIVE);
      }

   }

   protected void clear() {
      Set entries = new HashSet();
      Iterator var2 = this.cache.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         entries.add((CacheEntry)entry);
      }

      super.clear();
      if (this._rollbackAdds == null) {
         this._rollbackAdds = entries;
      } else {
         this._rollbackAdds.addAll(entries);
      }

   }

   protected void add(Object key, Object value) {
      super.add(key, value);
      if (this._rollbackRemoves == null) {
         this._rollbackRemoves = new HashSet();
      }

      this._rollbackRemoves.add(key);
   }

   protected Object update(Object key, Object value) {
      Object prev = super.update(key, value);
      if (this._rollbacks == null) {
         this._rollbacks = new HashMap();
      }

      this._rollbacks.put(key, prev);
      return prev;
   }

   protected Object remove(Object key) {
      Object prev = super.remove(key);
      if (this._rollbacks == null) {
         this._rollbacks = new HashMap();
      }

      this._rollbacks.put(key, prev);
      return prev;
   }

   protected Action newDividedInstance(Set keys, Map adds, Map updates, Set removes, boolean clear) {
      SortedSet dlkeys = new TreeSet(new LockKeyComparator());
      Iterator var7 = keys.iterator();

      while(var7.hasNext()) {
         Object key = var7.next();
         if (this.lockedKeys.contains(key)) {
            dlkeys.add(key);
         }
      }

      return new CommitAction(this.id, dlkeys, this.lockTimeout, adds, updates, removes, clear);
   }
}
