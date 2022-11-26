package weblogic.cache.session;

import java.util.Set;
import weblogic.cache.Action;

public interface DivisibleAction extends Action {
   boolean includesClear();

   Set getAffectedKeys();

   Action divide(Set var1);
}
