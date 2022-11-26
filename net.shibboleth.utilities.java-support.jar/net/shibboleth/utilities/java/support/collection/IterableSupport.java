package net.shibboleth.utilities.java.support.collection;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;

public final class IterableSupport {
   private IterableSupport() {
   }

   public static boolean containsInstance(@Nonnull Iterable target, @Nonnull Class clazz) {
      Constraint.isNotNull(target, "Target collection can not be null");
      Constraint.isNotNull(clazz, "Class can not be null");
      Predicate instanceOf = Predicates.instanceOf(clazz);
      Optional result = Iterables.tryFind(target, instanceOf);
      return result.isPresent();
   }
}
