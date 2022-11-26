package weblogic.security.providers.authorization;

import java.util.Locale;

public interface PredicateArgument {
   String getType();

   String getDisplayName(Locale var1);

   String getDescription(Locale var1);

   Object getDefaultValue();

   boolean isOptional();

   void validateValue(Object var1, Locale var2) throws IllegalPredicateArgumentException;

   Object parseValue(String var1, Locale var2) throws IllegalPredicateArgumentException;

   String formatValue(Object var1, Locale var2) throws IllegalPredicateArgumentException;

   Object parseExprValue(String var1) throws IllegalPredicateArgumentException;

   String formatExprValue(Object var1) throws IllegalPredicateArgumentException;
}
