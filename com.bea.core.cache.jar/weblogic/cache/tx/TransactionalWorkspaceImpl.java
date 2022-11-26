package weblogic.cache.tx;

import java.util.SortedSet;
import java.util.TreeSet;
import weblogic.cache.ActionTrigger;
import weblogic.cache.locks.LockKeyComparator;
import weblogic.cache.session.WorkspaceImpl;

class TransactionalWorkspaceImpl extends WorkspaceImpl implements TransactionalWorkspace {
   private final Object _id;
   private boolean _commitAttempt;
   private ActionTrigger _trigger;
   private final SortedSet _lockedKeys = new TreeSet(new LockKeyComparator());

   public TransactionalWorkspaceImpl(Object id) {
      this._id = id;
   }

   public Object getId() {
      return this._id;
   }

   public boolean isCommitAttempt() {
      return this._commitAttempt;
   }

   public void setCommitAttempt(boolean attempt) {
      this._commitAttempt = attempt;
   }

   public SortedSet getLockedKeys() {
      return this._lockedKeys;
   }

   public ActionTrigger getActionTrigger() {
      return this._trigger;
   }

   public void setActionTrigger(ActionTrigger trigger) {
      this._trigger = trigger;
   }

   public void reset() {
      super.reset();
      this._lockedKeys.clear();
      this._commitAttempt = false;
   }
}
