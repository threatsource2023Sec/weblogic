package weblogic.entitlement.rules;

import java.util.Locale;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;

public class GroupnamePredicateArgument extends BasePredicateArgument {
   public GroupnamePredicateArgument() {
      this("GroupnamePredicateArgumentName", "GroupnamePredicateArgumentDescription", (String)null);
   }

   public GroupnamePredicateArgument(String displayNameId, String descriptionId, String defaultValue) {
      super(displayNameId, descriptionId, String.class, defaultValue);
   }

   public Object parseValue(String value, Locale locale) throws IllegalPredicateArgumentException {
      this.validateValue(value, locale);
      return value;
   }

   public String formatValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
      this.validateValue(value, locale);
      return (String)value;
   }
}
