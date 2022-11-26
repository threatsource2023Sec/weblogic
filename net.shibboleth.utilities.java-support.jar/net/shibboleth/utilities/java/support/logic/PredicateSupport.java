package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class PredicateSupport {
   private PredicateSupport() {
   }

   @Nonnull
   public static Predicate allMatch(@Nonnull Predicate target) {
      return new AllMatchPredicate(target);
   }

   @Nonnull
   public static Predicate anyMatch(@Nonnull Predicate target) {
      return new AnyMatchPredicate(target);
   }

   @Nonnull
   public static Predicate caseInsensitiveMatch(@Nonnull String target) {
      return new CaseInsensitiveStringMatchPredicate(target);
   }

   @Nonnull
   public static Predicate fromFunction(@Nonnull final Function function, @Nonnull final Predicate defValue) {
      return new Predicate() {
         public boolean apply(@Nullable Object input) {
            Boolean result = (Boolean)function.apply(input);
            return result != null ? result : defValue.apply(input);
         }
      };
   }
}
