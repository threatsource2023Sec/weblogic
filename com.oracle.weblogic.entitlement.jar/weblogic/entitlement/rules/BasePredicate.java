package weblogic.entitlement.rules;

import java.util.Locale;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.Predicate;
import weblogic.security.providers.authorization.PredicateArgument;

public abstract class BasePredicate implements Predicate {
   private String displayNameId;
   private String descriptionId;

   public BasePredicate(String displayNameId, String descriptionId) {
      this.displayNameId = displayNameId;
      this.descriptionId = descriptionId;
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length > 0) {
         throw new IllegalPredicateArgumentException("No arguments expected");
      }
   }

   public boolean isSupportedResource(String resourceId) {
      return true;
   }

   public String getDisplayName(Locale locale) {
      return Localizer.getText(this.displayNameId, locale);
   }

   public String getDescription(Locale locale) {
      return Localizer.getText(this.descriptionId, locale);
   }

   public int getArgumentCount() {
      return 0;
   }

   public PredicateArgument getArgument(int index) {
      throw new IndexOutOfBoundsException("This predicate takes no arguments");
   }

   public boolean isDeprecated() {
      return false;
   }
}
