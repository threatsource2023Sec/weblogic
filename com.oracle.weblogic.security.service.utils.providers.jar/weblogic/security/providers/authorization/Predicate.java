package weblogic.security.providers.authorization;

import java.util.Locale;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public interface Predicate {
   void init(String[] var1) throws IllegalPredicateArgumentException;

   boolean evaluate(Subject var1, Resource var2, ContextHandler var3);

   boolean isSupportedResource(String var1);

   String getDisplayName(Locale var1);

   String getDescription(Locale var1);

   String getVersion();

   int getArgumentCount();

   PredicateArgument getArgument(int var1);

   boolean isDeprecated();
}
