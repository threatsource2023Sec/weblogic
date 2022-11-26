package net.shibboleth.utilities.java.support.resolver;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ResolverSupport {
   private ResolverSupport() {
   }

   @Nonnull
   public static Set getPredicates(@Nullable CriteriaSet criteriaSet, @Nullable Class predicateCriterionType, @Nullable CriterionPredicateRegistry registry) throws ResolverException {
      if (criteriaSet == null) {
         return Collections.emptySet();
      } else {
         Set predicates = new HashSet(criteriaSet.size());
         Iterator i$ = criteriaSet.iterator();

         while(true) {
            while(i$.hasNext()) {
               Criterion criterion = (Criterion)i$.next();
               if (predicateCriterionType != null && predicateCriterionType.isInstance(criterion)) {
                  predicates.add(predicateCriterionType.cast(criterion));
               } else if (registry != null) {
                  Predicate predicate = registry.getPredicate(criterion);
                  if (predicate != null) {
                     predicates.add(predicate);
                  }
               }
            }

            return predicates;
         }
      }
   }

   @Nonnull
   public static Iterable getFilteredIterable(@Nullable Iterable candidates, @Nullable Set predicates, boolean satisfyAny, boolean onEmptyPredicatesReturnEmpty) {
      if (candidates != null && candidates.iterator().hasNext()) {
         if (predicates != null && !predicates.isEmpty()) {
            Predicate predicate;
            if (satisfyAny) {
               predicate = Predicates.or(predicates);
            } else {
               predicate = Predicates.and(predicates);
            }

            return Iterables.filter(candidates, predicate);
         } else {
            return (Iterable)(onEmptyPredicatesReturnEmpty ? Collections.emptySet() : candidates);
         }
      } else {
         return Collections.emptySet();
      }
   }
}
