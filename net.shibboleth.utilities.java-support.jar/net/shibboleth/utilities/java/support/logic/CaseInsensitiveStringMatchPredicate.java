package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Predicate;
import javax.annotation.Nonnull;

public class CaseInsensitiveStringMatchPredicate implements Predicate {
   private final String target;

   public CaseInsensitiveStringMatchPredicate(@Nonnull String matchString) {
      this.target = (String)Constraint.isNotNull(matchString, "Target string can not be null");
   }

   public boolean apply(CharSequence input) {
      return input == null ? false : this.target.equalsIgnoreCase(input.toString());
   }
}
