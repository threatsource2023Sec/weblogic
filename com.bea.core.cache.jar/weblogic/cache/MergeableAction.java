package weblogic.cache;

import java.util.Map;
import weblogic.cache.session.DivisibleAction;

public interface MergeableAction extends DivisibleAction {
   Object mergeResult(Map var1);
}
