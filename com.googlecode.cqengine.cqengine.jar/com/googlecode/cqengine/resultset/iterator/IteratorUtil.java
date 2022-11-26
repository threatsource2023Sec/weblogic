package com.googlecode.cqengine.resultset.iterator;

import com.googlecode.concurrenttrees.common.LazyIterator;
import com.googlecode.cqengine.index.support.KeyValue;
import com.googlecode.cqengine.index.support.KeyValueMaterialized;
import com.googlecode.cqengine.resultset.filter.MaterializedDeduplicatedIterator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class IteratorUtil {
   public static boolean iterableContains(Iterable iterable, Object element) {
      Iterator var2 = iterable.iterator();

      Object contained;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         contained = var2.next();
      } while(!contained.equals(element));

      return true;
   }

   public static int countElements(Iterable iterable) {
      int count = 0;

      for(Iterator var2 = iterable.iterator(); var2.hasNext(); ++count) {
         Object object = var2.next();
      }

      return count;
   }

   public static Iterator removeNulls(final Iterator unfiltered) {
      return new LazyIterator() {
         protected Object computeNext() {
            while(true) {
               if (unfiltered.hasNext()) {
                  Object element = unfiltered.next();
                  if (element == null) {
                     continue;
                  }

                  return element;
               }

               return this.endOfData();
            }
         }
      };
   }

   public static UnmodifiableIterator wrapAsUnmodifiable(final Iterator iterator) {
      return new UnmodifiableIterator() {
         public boolean hasNext() {
            return iterator.hasNext();
         }

         public Object next() {
            return iterator.next();
         }
      };
   }

   public static Iterable flatten(final Map map) {
      return new Iterable() {
         public Iterator iterator() {
            return new LazyIterator() {
               Iterator entriesIterator = map.entrySet().iterator();
               Iterator valuesIterator = Collections.emptySet().iterator();

               protected KeyValue computeNext() {
                  while(!this.valuesIterator.hasNext()) {
                     if (!this.entriesIterator.hasNext()) {
                        return (KeyValue)this.endOfData();
                     }

                     Map.Entry entry = (Map.Entry)this.entriesIterator.next();
                     this.valuesIterator = IteratorUtil.flatten(entry.getKey(), (Iterable)entry.getValue()).iterator();
                  }

                  return (KeyValue)this.valuesIterator.next();
               }
            };
         }
      };
   }

   public static Iterable flatten(final Object key, final Iterable values) {
      return new Iterable() {
         public Iterator iterator() {
            return new LazyIterator() {
               final Iterator valuesIterator = values.iterator();

               protected KeyValue computeNext() {
                  return (KeyValue)(this.valuesIterator.hasNext() ? new KeyValueMaterialized(key, this.valuesIterator.next()) : (KeyValue)this.endOfData());
               }
            };
         }
      };
   }

   public static Iterator concatenate(final Iterator iterables) {
      return new ConcatenatingIterator() {
         public Iterator getNextIterator() {
            return iterables.hasNext() ? ((Iterable)iterables.next()).iterator() : null;
         }
      };
   }

   public static Iterator groupAndSort(final Iterator values, final Comparator comparator) {
      return new LazyIterator() {
         final Iterator valuesIterator = values;
         Set currentGroup = new TreeSet(comparator);
         Object currentKey = null;

         protected Set computeNext() {
            while(this.valuesIterator.hasNext()) {
               KeyValue next = (KeyValue)this.valuesIterator.next();
               if (!next.getKey().equals(this.currentKey)) {
                  Set resultx = this.currentGroup;
                  this.currentKey = next.getKey();
                  this.currentGroup = new TreeSet(comparator);
                  this.currentGroup.add(next.getValue());
                  return resultx;
               }

               this.currentGroup.add(next.getValue());
            }

            if (this.currentGroup.isEmpty()) {
               return (Set)this.endOfData();
            } else {
               Set result = this.currentGroup;
               this.currentGroup = new TreeSet(comparator);
               return result;
            }
         }
      };
   }

   public static Iterator materializedSort(Iterator unsortedIterator, Comparator comparator) {
      Set materializedSet = new TreeSet(comparator);

      while(unsortedIterator.hasNext()) {
         materializedSet.add(unsortedIterator.next());
      }

      return materializedSet.iterator();
   }

   public static Iterator materializedDeuplicate(Iterator iterator) {
      return new MaterializedDeduplicatedIterator(iterator);
   }
}
