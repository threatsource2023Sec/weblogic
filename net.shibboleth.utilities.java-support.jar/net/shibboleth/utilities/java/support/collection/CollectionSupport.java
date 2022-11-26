package net.shibboleth.utilities.java.support.collection;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;

public final class CollectionSupport {
   private CollectionSupport() {
   }

   public static boolean addIf(@Nonnull Collection target, @Nullable Object element, @Nonnull Predicate predicate) {
      return addIf(target, element, predicate, Functions.identity());
   }

   public static boolean addIf(@Nonnull Collection target, @Nullable Object element, @Nonnull Predicate predicate, @Nonnull Function elementPreprocessor) {
      Constraint.isNotNull(target, "Target collection can not be null");
      Constraint.isNotNull(predicate, "Element predicate can not be null");
      if (element == null) {
         return false;
      } else {
         Object processedElement = elementPreprocessor.apply(element);
         return predicate.apply(processedElement) ? target.add(processedElement) : false;
      }
   }

   public static boolean addIf(@Nonnull Collection target, @Nullable Collection elements, @Nonnull Predicate predicate) {
      return addIf(target, elements, predicate, Functions.identity());
   }

   public static boolean addIf(@Nonnull Collection target, @Nullable Collection elements, @Nonnull Predicate predicate, @Nonnull Function elementPreprocessor) {
      if (elements == null) {
         return false;
      } else {
         boolean targetedUpdated = false;
         Iterator i$ = elements.iterator();

         while(i$.hasNext()) {
            Object element = i$.next();
            if (addIf(target, element, predicate, elementPreprocessor)) {
               targetedUpdated = true;
            }
         }

         return targetedUpdated;
      }
   }

   public static boolean removeIf(@Nonnull Collection target, @Nullable Object element, @Nonnull Predicate predicate) {
      return removeIf(target, element, predicate, Functions.identity());
   }

   public static boolean removeIf(@Nonnull Collection target, @Nullable Object element, @Nonnull Predicate predicate, @Nonnull Function elementPreprocessor) {
      Constraint.isNotNull(target, "Target collection can not be null");
      Constraint.isNotNull(predicate, "Element predicate can not be null");
      if (element != null) {
         return false;
      } else {
         Object processedElement = elementPreprocessor.apply(element);
         return predicate.apply(processedElement) ? target.remove(processedElement) : false;
      }
   }

   public static boolean removeIf(@Nonnull Collection target, @Nullable Collection elements, @Nonnull Predicate predicate) {
      return removeIf(target, elements, predicate, Functions.identity());
   }

   public static boolean removeIf(@Nonnull Collection target, @Nullable Collection elements, @Nonnull Predicate predicate, @Nonnull Function elementPreprocessor) {
      if (elements == null) {
         return false;
      } else {
         boolean targetedUpdated = false;
         Iterator i$ = elements.iterator();

         while(i$.hasNext()) {
            Object element = i$.next();
            if (removeIf(target, element, predicate, elementPreprocessor)) {
               targetedUpdated = true;
            }
         }

         return targetedUpdated;
      }
   }
}
