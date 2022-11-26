package weblogic.management.provider;

import java.util.Collection;

public interface ResolveTask extends ActivateTask {
   Collection getConflicts();

   String getPatchDescription();
}
