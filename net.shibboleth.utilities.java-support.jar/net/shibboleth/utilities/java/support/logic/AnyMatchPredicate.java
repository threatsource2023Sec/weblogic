package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Predicate;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AnyMatchPredicate implements Predicate {
   private final Predicate predicate;

   public AnyMatchPredicate(@Nonnull Predicate target) {
      this.predicate = (Predicate)Constraint.isNotNull(target, "Target predicate can not be null");
   }

   public boolean apply(@Nullable Iterable inputs) {
      if (inputs == null) {
         return false;
      } else {
         Iterator i$ = inputs.iterator();

         Object input;
         do {
            if (!i$.hasNext()) {
               return false;
            }

            input = i$.next();
         } while(!this.predicate.apply(input));

         return true;
      }
   }
}
