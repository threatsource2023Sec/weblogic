package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StrategyIndirectedPredicate implements Predicate {
   @Nonnull
   private final Function objectLookupStrategy;
   @Nonnull
   private final Predicate predicate;

   public StrategyIndirectedPredicate(@Nonnull Function objectStrategy, @Nonnull Predicate pred) {
      this.objectLookupStrategy = (Function)Constraint.isNotNull(objectStrategy, "Object lookup strategy cannot be null");
      this.predicate = (Predicate)Constraint.isNotNull(pred, "Predicate cannot be null");
   }

   public StrategyIndirectedPredicate(@Nonnull Function objectStrategy, @Nonnull Collection collection) {
      this.objectLookupStrategy = (Function)Constraint.isNotNull(objectStrategy, "Object lookup strategy cannot be null");
      this.predicate = Predicates.in(collection);
   }

   public boolean apply(@Nullable Object input) {
      return this.predicate.apply(this.objectLookupStrategy.apply(input));
   }

   @Nonnull
   public static StrategyIndirectedPredicate forPredicate(@Nonnull Function objectStrategy, @Nonnull Predicate pred) {
      return new StrategyIndirectedPredicate(objectStrategy, pred);
   }

   @Nonnull
   public static StrategyIndirectedPredicate forCollection(@Nonnull Function objectStrategy, @Nonnull Collection collection) {
      return new StrategyIndirectedPredicate(objectStrategy, collection);
   }
}
