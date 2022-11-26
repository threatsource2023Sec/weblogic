package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;
import org.python.google.common.math.IntMath;
import org.python.google.common.primitives.Ints;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtIncompatible
public final class ConcurrentHashMultiset extends AbstractMultiset implements Serializable {
   private final transient ConcurrentMap countMap;
   private static final long serialVersionUID = 1L;

   public static ConcurrentHashMultiset create() {
      return new ConcurrentHashMultiset(new ConcurrentHashMap());
   }

   public static ConcurrentHashMultiset create(Iterable elements) {
      ConcurrentHashMultiset multiset = create();
      Iterables.addAll(multiset, elements);
      return multiset;
   }

   @Beta
   public static ConcurrentHashMultiset create(ConcurrentMap countMap) {
      return new ConcurrentHashMultiset(countMap);
   }

   @VisibleForTesting
   ConcurrentHashMultiset(ConcurrentMap countMap) {
      Preconditions.checkArgument(countMap.isEmpty(), "the backing map (%s) must be empty", (Object)countMap);
      this.countMap = countMap;
   }

   public int count(@Nullable Object element) {
      AtomicInteger existingCounter = (AtomicInteger)Maps.safeGet(this.countMap, element);
      return existingCounter == null ? 0 : existingCounter.get();
   }

   public int size() {
      long sum = 0L;

      AtomicInteger value;
      for(Iterator var3 = this.countMap.values().iterator(); var3.hasNext(); sum += (long)value.get()) {
         value = (AtomicInteger)var3.next();
      }

      return Ints.saturatedCast(sum);
   }

   public Object[] toArray() {
      return this.snapshot().toArray();
   }

   public Object[] toArray(Object[] array) {
      return this.snapshot().toArray(array);
   }

   private List snapshot() {
      List list = Lists.newArrayListWithExpectedSize(this.size());
      Iterator var2 = this.entrySet().iterator();

      while(var2.hasNext()) {
         Multiset.Entry entry = (Multiset.Entry)var2.next();
         Object element = entry.getElement();

         for(int i = entry.getCount(); i > 0; --i) {
            list.add(element);
         }
      }

      return list;
   }

   @CanIgnoreReturnValue
   public int add(Object element, int occurrences) {
      Preconditions.checkNotNull(element);
      if (occurrences == 0) {
         return this.count(element);
      } else {
         CollectPreconditions.checkPositive(occurrences, "occurences");

         AtomicInteger existingCounter;
         AtomicInteger newCounter;
         do {
            existingCounter = (AtomicInteger)Maps.safeGet(this.countMap, element);
            if (existingCounter == null) {
               existingCounter = (AtomicInteger)this.countMap.putIfAbsent(element, new AtomicInteger(occurrences));
               if (existingCounter == null) {
                  return 0;
               }
            }

            while(true) {
               int oldValue = existingCounter.get();
               if (oldValue == 0) {
                  newCounter = new AtomicInteger(occurrences);
                  break;
               }

               try {
                  int newValue = IntMath.checkedAdd(oldValue, occurrences);
                  if (existingCounter.compareAndSet(oldValue, newValue)) {
                     return oldValue;
                  }
               } catch (ArithmeticException var6) {
                  throw new IllegalArgumentException("Overflow adding " + occurrences + " occurrences to a count of " + oldValue);
               }
            }
         } while(this.countMap.putIfAbsent(element, newCounter) != null && !this.countMap.replace(element, existingCounter, newCounter));

         return 0;
      }
   }

   @CanIgnoreReturnValue
   public int remove(@Nullable Object element, int occurrences) {
      if (occurrences == 0) {
         return this.count(element);
      } else {
         CollectPreconditions.checkPositive(occurrences, "occurences");
         AtomicInteger existingCounter = (AtomicInteger)Maps.safeGet(this.countMap, element);
         if (existingCounter == null) {
            return 0;
         } else {
            int oldValue;
            int newValue;
            do {
               oldValue = existingCounter.get();
               if (oldValue == 0) {
                  return 0;
               }

               newValue = Math.max(0, oldValue - occurrences);
            } while(!existingCounter.compareAndSet(oldValue, newValue));

            if (newValue == 0) {
               this.countMap.remove(element, existingCounter);
            }

            return oldValue;
         }
      }
   }

   @CanIgnoreReturnValue
   public boolean removeExactly(@Nullable Object element, int occurrences) {
      if (occurrences == 0) {
         return true;
      } else {
         CollectPreconditions.checkPositive(occurrences, "occurences");
         AtomicInteger existingCounter = (AtomicInteger)Maps.safeGet(this.countMap, element);
         if (existingCounter == null) {
            return false;
         } else {
            int oldValue;
            int newValue;
            do {
               oldValue = existingCounter.get();
               if (oldValue < occurrences) {
                  return false;
               }

               newValue = oldValue - occurrences;
            } while(!existingCounter.compareAndSet(oldValue, newValue));

            if (newValue == 0) {
               this.countMap.remove(element, existingCounter);
            }

            return true;
         }
      }
   }

   @CanIgnoreReturnValue
   public int setCount(Object element, int count) {
      Preconditions.checkNotNull(element);
      CollectPreconditions.checkNonnegative(count, "count");

      AtomicInteger existingCounter;
      AtomicInteger newCounter;
      label40:
      do {
         existingCounter = (AtomicInteger)Maps.safeGet(this.countMap, element);
         if (existingCounter == null) {
            if (count == 0) {
               return 0;
            }

            existingCounter = (AtomicInteger)this.countMap.putIfAbsent(element, new AtomicInteger(count));
            if (existingCounter == null) {
               return 0;
            }
         }

         int oldValue;
         do {
            oldValue = existingCounter.get();
            if (oldValue == 0) {
               if (count == 0) {
                  return 0;
               }

               newCounter = new AtomicInteger(count);
               continue label40;
            }
         } while(!existingCounter.compareAndSet(oldValue, count));

         if (count == 0) {
            this.countMap.remove(element, existingCounter);
         }

         return oldValue;
      } while(this.countMap.putIfAbsent(element, newCounter) != null && !this.countMap.replace(element, existingCounter, newCounter));

      return 0;
   }

   @CanIgnoreReturnValue
   public boolean setCount(Object element, int expectedOldCount, int newCount) {
      Preconditions.checkNotNull(element);
      CollectPreconditions.checkNonnegative(expectedOldCount, "oldCount");
      CollectPreconditions.checkNonnegative(newCount, "newCount");
      AtomicInteger existingCounter = (AtomicInteger)Maps.safeGet(this.countMap, element);
      if (existingCounter == null) {
         if (expectedOldCount != 0) {
            return false;
         } else if (newCount == 0) {
            return true;
         } else {
            return this.countMap.putIfAbsent(element, new AtomicInteger(newCount)) == null;
         }
      } else {
         int oldValue = existingCounter.get();
         if (oldValue == expectedOldCount) {
            if (oldValue == 0) {
               if (newCount == 0) {
                  this.countMap.remove(element, existingCounter);
                  return true;
               }

               AtomicInteger newCounter = new AtomicInteger(newCount);
               return this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter);
            }

            if (existingCounter.compareAndSet(oldValue, newCount)) {
               if (newCount == 0) {
                  this.countMap.remove(element, existingCounter);
               }

               return true;
            }
         }

         return false;
      }
   }

   Set createElementSet() {
      final Set delegate = this.countMap.keySet();
      return new ForwardingSet() {
         protected Set delegate() {
            return delegate;
         }

         public boolean contains(@Nullable Object object) {
            return object != null && Collections2.safeContains(delegate, object);
         }

         public boolean containsAll(Collection collection) {
            return this.standardContainsAll(collection);
         }

         public boolean remove(Object object) {
            return object != null && Collections2.safeRemove(delegate, object);
         }

         public boolean removeAll(Collection c) {
            return this.standardRemoveAll(c);
         }
      };
   }

   public Set createEntrySet() {
      return new EntrySet();
   }

   int distinctElements() {
      return this.countMap.size();
   }

   public boolean isEmpty() {
      return this.countMap.isEmpty();
   }

   Iterator entryIterator() {
      final Iterator readOnlyIterator = new AbstractIterator() {
         private final Iterator mapEntries;

         {
            this.mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();
         }

         protected Multiset.Entry computeNext() {
            Map.Entry mapEntry;
            int count;
            do {
               if (!this.mapEntries.hasNext()) {
                  return (Multiset.Entry)this.endOfData();
               }

               mapEntry = (Map.Entry)this.mapEntries.next();
               count = ((AtomicInteger)mapEntry.getValue()).get();
            } while(count == 0);

            return Multisets.immutableEntry(mapEntry.getKey(), count);
         }
      };
      return new ForwardingIterator() {
         private Multiset.Entry last;

         protected Iterator delegate() {
            return readOnlyIterator;
         }

         public Multiset.Entry next() {
            this.last = (Multiset.Entry)super.next();
            return this.last;
         }

         public void remove() {
            CollectPreconditions.checkRemove(this.last != null);
            ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
            this.last = null;
         }
      };
   }

   public void clear() {
      this.countMap.clear();
   }

   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeObject(this.countMap);
   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      ConcurrentMap deserializedCountMap = (ConcurrentMap)stream.readObject();
      ConcurrentHashMultiset.FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, deserializedCountMap);
   }

   private class EntrySet extends AbstractMultiset.EntrySet {
      private EntrySet() {
         super();
      }

      ConcurrentHashMultiset multiset() {
         return ConcurrentHashMultiset.this;
      }

      public Object[] toArray() {
         return this.snapshot().toArray();
      }

      public Object[] toArray(Object[] array) {
         return this.snapshot().toArray(array);
      }

      private List snapshot() {
         List list = Lists.newArrayListWithExpectedSize(this.size());
         Iterators.addAll(list, this.iterator());
         return list;
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }

   private static class FieldSettersHolder {
      static final Serialization.FieldSetter COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");
   }
}
