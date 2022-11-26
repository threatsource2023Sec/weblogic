package org.python.google.common.collect;

import java.util.Comparator;
import java.util.SortedSet;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
final class SortedIterables {
   private SortedIterables() {
   }

   public static boolean hasSameComparator(Comparator comparator, Iterable elements) {
      Preconditions.checkNotNull(comparator);
      Preconditions.checkNotNull(elements);
      Comparator comparator2;
      if (elements instanceof SortedSet) {
         comparator2 = comparator((SortedSet)elements);
      } else {
         if (!(elements instanceof SortedIterable)) {
            return false;
         }

         comparator2 = ((SortedIterable)elements).comparator();
      }

      return comparator.equals(comparator2);
   }

   public static Comparator comparator(SortedSet sortedSet) {
      Comparator result = sortedSet.comparator();
      if (result == null) {
         result = Ordering.natural();
      }

      return (Comparator)result;
   }
}
