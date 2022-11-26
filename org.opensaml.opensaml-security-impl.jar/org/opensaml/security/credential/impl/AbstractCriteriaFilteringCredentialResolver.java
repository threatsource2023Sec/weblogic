package org.opensaml.security.credential.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriteriaRegistry;
import org.opensaml.security.credential.criteria.impl.EvaluableCredentialCriterion;

public abstract class AbstractCriteriaFilteringCredentialResolver extends AbstractCredentialResolver {
   private boolean satisfyAllPredicates = true;

   @Nonnull
   public Iterable resolve(@Nullable CriteriaSet criteriaSet) throws ResolverException {
      Iterable storeCandidates = this.resolveFromSource(criteriaSet);
      Set predicates = this.getPredicates(criteriaSet);
      if (predicates.isEmpty()) {
         return storeCandidates;
      } else {
         Predicate aggregatePredicate = null;
         if (this.isSatisfyAllPredicates()) {
            aggregatePredicate = Predicates.and(predicates);
         } else {
            aggregatePredicate = Predicates.or(predicates);
         }

         return Iterables.filter(storeCandidates, aggregatePredicate);
      }
   }

   public boolean isSatisfyAllPredicates() {
      return this.satisfyAllPredicates;
   }

   public void setSatisfyAllPredicates(boolean flag) {
      this.satisfyAllPredicates = flag;
   }

   @Nonnull
   protected abstract Iterable resolveFromSource(@Nullable CriteriaSet var1) throws ResolverException;

   private Set getPredicates(@Nullable CriteriaSet criteriaSet) throws ResolverException {
      if (criteriaSet == null) {
         return Collections.emptySet();
      } else {
         Set predicates = new HashSet(criteriaSet.size());
         Iterator var3 = criteriaSet.iterator();

         while(var3.hasNext()) {
            Criterion criteria = (Criterion)var3.next();
            if (criteria instanceof EvaluableCredentialCriterion) {
               predicates.add((EvaluableCredentialCriterion)criteria);
            } else {
               EvaluableCredentialCriterion evaluableCriteria;
               try {
                  evaluableCriteria = EvaluableCredentialCriteriaRegistry.getEvaluator(criteria);
               } catch (SecurityException var7) {
                  throw new ResolverException("Exception obtaining EvaluableCredentialCriterion", var7);
               }

               if (evaluableCriteria != null) {
                  predicates.add(evaluableCriteria);
               }
            }
         }

         return predicates;
      }
   }
}
