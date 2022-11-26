package weblogic.cache.tx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import weblogic.cache.Action;
import weblogic.cache.CacheEntry;
import weblogic.cache.CacheMap;
import weblogic.cache.configuration.CacheProperties;
import weblogic.cache.locks.LockKeyComparator;
import weblogic.cache.session.Workspace;

public class OptimisticMapAdapter extends AbstractTransactionalMapAdapter {
   private static final Long VERSION_NONE = -1L;

   public OptimisticMapAdapter(CacheMap delegate, JTAIntegration jta) {
      super(delegate, jta);
   }

   protected Object getReadValueMiss(Object key, Workspace workspace) {
      if (this.getIsolation() == CacheProperties.TransactionIsolationValue.RepeatableRead && !((OptimisticWorkspace)workspace).getVersions().containsKey(key)) {
         CacheEntry entry = this.getWriteMiss(key, workspace);
         return entry == null ? null : entry.getValue();
      } else {
         return this.stdGet(key, workspace);
      }
   }

   protected CacheEntry getReadMiss(Object key, Workspace workspace) {
      return this.getIsolation() == CacheProperties.TransactionIsolationValue.RepeatableRead && !((OptimisticWorkspace)workspace).getVersions().containsKey(key) ? this.getWriteMiss(key, workspace) : this.stdGetEntry(key, workspace);
   }

   protected CacheEntry getWriteMiss(Object key, Workspace workspace) {
      CacheEntry entry = this.stdGetEntry(key, workspace);
      Long version = entry == null ? VERSION_NONE : entry.getVersion();
      ((OptimisticWorkspace)workspace).getVersions().put(key, version);
      return entry;
   }

   protected TransactionalWorkspace newWorkspace(Object id) {
      return new OptimisticWorkspace(id);
   }

   protected Action newCommitAction(TransactionalWorkspace workspace) {
      return new OptimisticCommitAction((OptimisticWorkspace)workspace, this.getLockTimeout());
   }

   private static class OptimisticCommitAction extends CommitAction {
      private final Map _versions;

      public OptimisticCommitAction(OptimisticWorkspace workspace, long lockTimeout) {
         super(workspace, lockTimeout);
         this._versions = workspace.getVersions();
      }

      public OptimisticCommitAction(Object id, SortedSet lockedKeys, long lockTimeout, Map versions, Map adds, Map updates, Set removes, boolean clear) {
         super(id, lockedKeys, lockTimeout, adds, updates, removes, clear);
         this._versions = versions;
      }

      public void lock() {
         super.lock();
         Collection failed = null;
         Iterator var2 = this._versions.entrySet().iterator();

         while(true) {
            Map.Entry ventry;
            CacheEntry centry;
            do {
               do {
                  if (!var2.hasNext()) {
                     if (failed != null) {
                        throw new OptimisticIsolationException(failed);
                     }

                     return;
                  }

                  ventry = (Map.Entry)var2.next();
                  centry = this.cache.getEntry(ventry.getKey());
               } while(centry == null && (Long)ventry.getValue() == OptimisticMapAdapter.VERSION_NONE);
            } while(centry != null && (Long)ventry.getValue() == centry.getVersion());

            if (failed == null) {
               failed = new ArrayList();
            }

            failed.add(ventry.getKey());
         }
      }

      protected Action newDividedInstance(Set keys, Map adds, Map updates, Set removes, boolean clear) {
         SortedSet dlkeys = new TreeSet(new LockKeyComparator());
         Map dversions = new HashMap();
         Iterator var8 = keys.iterator();

         while(var8.hasNext()) {
            Object key = var8.next();
            if (this.lockedKeys.contains(key)) {
               dlkeys.add(key);
            }

            Long version = (Long)this._versions.get(key);
            if (version != null) {
               dversions.put(key, version);
            }
         }

         return new OptimisticCommitAction(this.id, dlkeys, this.lockTimeout, dversions, adds, updates, removes, clear);
      }
   }

   private static class OptimisticWorkspace extends TransactionalWorkspaceImpl {
      private final Map _versions = new HashMap();

      public OptimisticWorkspace(Object id) {
         super(id);
      }

      public Map getVersions() {
         return this._versions;
      }

      public void reset() {
         super.reset();
         this._versions.clear();
      }
   }
}
