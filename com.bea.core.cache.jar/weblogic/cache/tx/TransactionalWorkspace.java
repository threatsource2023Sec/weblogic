package weblogic.cache.tx;

import java.util.SortedSet;
import weblogic.cache.ActionTrigger;
import weblogic.cache.session.Workspace;

public interface TransactionalWorkspace extends Workspace {
   Object getId();

   boolean isCommitAttempt();

   void setCommitAttempt(boolean var1);

   SortedSet getLockedKeys();

   ActionTrigger getActionTrigger();

   void setActionTrigger(ActionTrigger var1);
}
