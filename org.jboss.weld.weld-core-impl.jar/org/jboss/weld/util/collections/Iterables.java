package org.jboss.weld.util.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

public final class Iterables {
   private Iterables() {
   }

   public static boolean addAll(Collection target, Iterable iterable) {
      return iterable instanceof Collection ? target.addAll((Collection)iterable) : Iterators.addAll(target, iterable.iterator());
   }

   public static Iterable concat(Iterable iterables) {
      return () -> {
         return Iterators.concat(iterators(iterables));
      };
   }

   public static Iterable concat(Iterable a, Iterable b) {
      return concat(Arrays.asList(a, b));
   }

   public static Iterator iterators(Iterable iterables) {
      return Iterators.transform(iterables.iterator(), Iterable::iterator);
   }

   public static Iterable transform(Iterable iterable, Function function) {
      return () -> {
         return new Iterators.TransformingIterator(iterable.iterator(), function);
      };
   }

   public static Iterable flatMap(Iterable iterable, Function function) {
      return concat(transform(iterable, function));
   }

   public static String toMultiRowString(Iterable iterable) {
      StringBuilder builder = new StringBuilder("\n  - ");
      Iterator iterator = iterable.iterator();

      while(iterator.hasNext()) {
         Object element = iterator.next();
         builder.append(element);
         if (iterator.hasNext()) {
            builder.append(",\n  - ");
         }
      }

      return builder.toString();
   }
}
