package org.jboss.weld.util.collections;

import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jboss.weld.util.Preconditions;

public final class Sets {
   private Sets() {
   }

   @SafeVarargs
   public static HashSet newHashSet(Object... elements) {
      HashSet set = new HashSet(elements.length);
      Collections.addAll(set, elements);
      return set;
   }

   public static Set union(final Set set1, Set set2) {
      Preconditions.checkArgumentNotNull(set1, "set1");
      Preconditions.checkArgumentNotNull(set2, "set2");
      final Set difference = new HashSet(set2);
      difference.removeAll(set1);
      return new AbstractSet() {
         public Iterator iterator() {
            final Iterator iterator = Iterators.concat(ImmutableList.of(set1.iterator(), difference.iterator()).iterator());
            return new Iterator() {
               public boolean hasNext() {
                  return iterator.hasNext();
               }

               public Object next() {
                  return iterator.next();
               }
            };
         }

         public int size() {
            return set1.size() + difference.size();
         }

         public boolean isEmpty() {
            return set1.isEmpty() && difference.isEmpty();
         }

         public boolean contains(Object o) {
            return set1.contains(o) || difference.contains(o);
         }
      };
   }
}
