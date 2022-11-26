package org.hibernate.validator.internal.constraintvalidators.hv;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.internal.util.CollectionHelper;

public class UniqueElementsValidator implements ConstraintValidator {
   public boolean isValid(Collection collection, ConstraintValidatorContext constraintValidatorContext) {
      if (collection != null && collection.size() >= 2) {
         List duplicates = this.findDuplicates(collection);
         if (duplicates.isEmpty()) {
            return true;
         } else {
            if (constraintValidatorContext instanceof HibernateConstraintValidatorContext) {
               ((HibernateConstraintValidatorContext)constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class)).addMessageParameter("duplicates", duplicates.stream().map(String::valueOf).collect(Collectors.joining(", "))).withDynamicPayload(CollectionHelper.toImmutableList(duplicates));
            }

            return false;
         }
      } else {
         return true;
      }
   }

   private List findDuplicates(Collection collection) {
      Set uniqueElements = CollectionHelper.newHashSet(collection.size());
      return (List)collection.stream().filter((o) -> {
         return !uniqueElements.add(o);
      }).collect(Collectors.toList());
   }
}
