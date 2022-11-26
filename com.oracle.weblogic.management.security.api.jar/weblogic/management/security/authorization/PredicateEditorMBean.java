package weblogic.management.security.authorization;

import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.InvalidPredicateException;
import weblogic.management.utils.NotFoundException;

public interface PredicateEditorMBean extends PredicateReaderMBean {
   void registerPredicate(String var1) throws InvalidPredicateException, AlreadyExistsException;

   void unregisterPredicate(String var1) throws NotFoundException;
}
