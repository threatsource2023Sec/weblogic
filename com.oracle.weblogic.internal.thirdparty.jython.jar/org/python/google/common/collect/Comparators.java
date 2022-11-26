package org.python.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@Beta
@GwtCompatible
public final class Comparators {
   private Comparators() {
   }

   public static Comparator lexicographical(Comparator comparator) {
      return new LexicographicalOrdering((Comparator)Preconditions.checkNotNull(comparator));
   }

   public static boolean isInOrder(Iterable iterable, Comparator comparator) {
      Preconditions.checkNotNull(comparator);
      Iterator it = iterable.iterator();
      Object next;
      if (it.hasNext()) {
         for(Object prev = it.next(); it.hasNext(); prev = next) {
            next = it.next();
            if (comparator.compare(prev, next) > 0) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean isInStrictOrder(Iterable iterable, Comparator comparator) {
      Preconditions.checkNotNull(comparator);
      Iterator it = iterable.iterator();
      Object next;
      if (it.hasNext()) {
         for(Object prev = it.next(); it.hasNext(); prev = next) {
            next = it.next();
            if (comparator.compare(prev, next) >= 0) {
               return false;
            }
         }
      }

      return true;
   }
}
