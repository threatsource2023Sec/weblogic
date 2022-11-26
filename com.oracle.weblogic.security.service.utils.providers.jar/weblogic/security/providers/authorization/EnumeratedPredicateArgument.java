package weblogic.security.providers.authorization;

import java.util.List;

public interface EnumeratedPredicateArgument extends PredicateArgument {
   List getAllowedValues();
}
