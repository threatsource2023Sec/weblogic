package org.python.google.common.collect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class Ordering implements Comparator {
   static final int LEFT_IS_GREATER = 1;
   static final int RIGHT_IS_GREATER = -1;

   @GwtCompatible(
      serializable = true
   )
   public static Ordering natural() {
      return NaturalOrdering.INSTANCE;
   }

   @GwtCompatible(
      serializable = true
   )
   public static Ordering from(Comparator comparator) {
      return (Ordering)(comparator instanceof Ordering ? (Ordering)comparator : new ComparatorOrdering(comparator));
   }

   /** @deprecated */
   @Deprecated
   @GwtCompatible(
      serializable = true
   )
   public static Ordering from(Ordering ordering) {
      return (Ordering)Preconditions.checkNotNull(ordering);
   }

   @GwtCompatible(
      serializable = true
   )
   public static Ordering explicit(List valuesInOrder) {
      return new ExplicitOrdering(valuesInOrder);
   }

   @GwtCompatible(
      serializable = true
   )
   public static Ordering explicit(Object leastValue, Object... remainingValuesInOrder) {
      return explicit(Lists.asList(leastValue, remainingValuesInOrder));
   }

   @GwtCompatible(
      serializable = true
   )
   public static Ordering allEqual() {
      return AllEqualOrdering.INSTANCE;
   }

   @GwtCompatible(
      serializable = true
   )
   public static Ordering usingToString() {
      return UsingToStringOrdering.INSTANCE;
   }

   public static Ordering arbitrary() {
      return Ordering.ArbitraryOrderingHolder.ARBITRARY_ORDERING;
   }

   protected Ordering() {
   }

   @GwtCompatible(
      serializable = true
   )
   public Ordering reverse() {
      return new ReverseOrdering(this);
   }

   @GwtCompatible(
      serializable = true
   )
   public Ordering nullsFirst() {
      return new NullsFirstOrdering(this);
   }

   @GwtCompatible(
      serializable = true
   )
   public Ordering nullsLast() {
      return new NullsLastOrdering(this);
   }

   @GwtCompatible(
      serializable = true
   )
   public Ordering onResultOf(Function function) {
      return new ByFunctionOrdering(function, this);
   }

   Ordering onKeys() {
      return this.onResultOf(Maps.keyFunction());
   }

   @GwtCompatible(
      serializable = true
   )
   public Ordering compound(Comparator secondaryComparator) {
      return new CompoundOrdering(this, (Comparator)Preconditions.checkNotNull(secondaryComparator));
   }

   @GwtCompatible(
      serializable = true
   )
   public static Ordering compound(Iterable comparators) {
      return new CompoundOrdering(comparators);
   }

   @GwtCompatible(
      serializable = true
   )
   public Ordering lexicographical() {
      return new LexicographicalOrdering(this);
   }

   @CanIgnoreReturnValue
   public abstract int compare(@Nullable Object var1, @Nullable Object var2);

   @CanIgnoreReturnValue
   public Object min(Iterator iterator) {
      Object minSoFar;
      for(minSoFar = iterator.next(); iterator.hasNext(); minSoFar = this.min(minSoFar, iterator.next())) {
      }

      return minSoFar;
   }

   @CanIgnoreReturnValue
   public Object min(Iterable iterable) {
      return this.min(iterable.iterator());
   }

   @CanIgnoreReturnValue
   public Object min(@Nullable Object a, @Nullable Object b) {
      return this.compare(a, b) <= 0 ? a : b;
   }

   @CanIgnoreReturnValue
   public Object min(@Nullable Object a, @Nullable Object b, @Nullable Object c, Object... rest) {
      Object minSoFar = this.min(this.min(a, b), c);
      Object[] var6 = rest;
      int var7 = rest.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Object r = var6[var8];
         minSoFar = this.min(minSoFar, r);
      }

      return minSoFar;
   }

   @CanIgnoreReturnValue
   public Object max(Iterator iterator) {
      Object maxSoFar;
      for(maxSoFar = iterator.next(); iterator.hasNext(); maxSoFar = this.max(maxSoFar, iterator.next())) {
      }

      return maxSoFar;
   }

   @CanIgnoreReturnValue
   public Object max(Iterable iterable) {
      return this.max(iterable.iterator());
   }

   @CanIgnoreReturnValue
   public Object max(@Nullable Object a, @Nullable Object b) {
      return this.compare(a, b) >= 0 ? a : b;
   }

   @CanIgnoreReturnValue
   public Object max(@Nullable Object a, @Nullable Object b, @Nullable Object c, Object... rest) {
      Object maxSoFar = this.max(this.max(a, b), c);
      Object[] var6 = rest;
      int var7 = rest.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Object r = var6[var8];
         maxSoFar = this.max(maxSoFar, r);
      }

      return maxSoFar;
   }

   public List leastOf(Iterable iterable, int k) {
      if (iterable instanceof Collection) {
         Collection collection = (Collection)iterable;
         if ((long)collection.size() <= 2L * (long)k) {
            Object[] array = (Object[])collection.toArray();
            Arrays.sort(array, this);
            if (array.length > k) {
               array = Arrays.copyOf(array, k);
            }

            return Collections.unmodifiableList(Arrays.asList(array));
         }
      }

      return this.leastOf(iterable.iterator(), k);
   }

   public List leastOf(Iterator iterator, int k) {
      Preconditions.checkNotNull(iterator);
      CollectPreconditions.checkNonnegative(k, "k");
      if (k != 0 && iterator.hasNext()) {
         if (k >= 1073741823) {
            ArrayList list = Lists.newArrayList(iterator);
            Collections.sort(list, this);
            if (list.size() > k) {
               list.subList(k, list.size()).clear();
            }

            list.trimToSize();
            return Collections.unmodifiableList(list);
         } else {
            TopKSelector selector = TopKSelector.least(k, this);
            selector.offerAll(iterator);
            return selector.topK();
         }
      } else {
         return ImmutableList.of();
      }
   }

   public List greatestOf(Iterable iterable, int k) {
      return this.reverse().leastOf(iterable, k);
   }

   public List greatestOf(Iterator iterator, int k) {
      return this.reverse().leastOf(iterator, k);
   }

   @CanIgnoreReturnValue
   public List sortedCopy(Iterable elements) {
      Object[] array = (Object[])Iterables.toArray(elements);
      Arrays.sort(array, this);
      return Lists.newArrayList((Iterable)Arrays.asList(array));
   }

   @CanIgnoreReturnValue
   public ImmutableList immutableSortedCopy(Iterable elements) {
      return ImmutableList.sortedCopyOf(this, elements);
   }

   public boolean isOrdered(Iterable iterable) {
      Iterator it = iterable.iterator();
      Object next;
      if (it.hasNext()) {
         for(Object prev = it.next(); it.hasNext(); prev = next) {
            next = it.next();
            if (this.compare(prev, next) > 0) {
               return false;
            }
         }
      }

      return true;
   }

   public boolean isStrictlyOrdered(Iterable iterable) {
      Iterator it = iterable.iterator();
      Object next;
      if (it.hasNext()) {
         for(Object prev = it.next(); it.hasNext(); prev = next) {
            next = it.next();
            if (this.compare(prev, next) >= 0) {
               return false;
            }
         }
      }

      return true;
   }

   /** @deprecated */
   @Deprecated
   public int binarySearch(List sortedList, @Nullable Object key) {
      return Collections.binarySearch(sortedList, key, this);
   }

   @VisibleForTesting
   static class IncomparableValueException extends ClassCastException {
      final Object value;
      private static final long serialVersionUID = 0L;

      IncomparableValueException(Object value) {
         super("Cannot compare value: " + value);
         this.value = value;
      }
   }

   @VisibleForTesting
   static class ArbitraryOrdering extends Ordering {
      private final AtomicInteger counter = new AtomicInteger(0);
      private final ConcurrentMap uids = Platform.tryWeakKeys(new MapMaker()).makeMap();

      private Integer getUid(Object obj) {
         Integer uid = (Integer)this.uids.get(obj);
         if (uid == null) {
            uid = this.counter.getAndIncrement();
            Integer alreadySet = (Integer)this.uids.putIfAbsent(obj, uid);
            if (alreadySet != null) {
               uid = alreadySet;
            }
         }

         return uid;
      }

      public int compare(Object left, Object right) {
         if (left == right) {
            return 0;
         } else if (left == null) {
            return -1;
         } else if (right == null) {
            return 1;
         } else {
            int leftCode = this.identityHashCode(left);
            int rightCode = this.identityHashCode(right);
            if (leftCode != rightCode) {
               return leftCode < rightCode ? -1 : 1;
            } else {
               int result = this.getUid(left).compareTo(this.getUid(right));
               if (result == 0) {
                  throw new AssertionError();
               } else {
                  return result;
               }
            }
         }
      }

      public String toString() {
         return "Ordering.arbitrary()";
      }

      int identityHashCode(Object object) {
         return System.identityHashCode(object);
      }
   }

   private static class ArbitraryOrderingHolder {
      static final Ordering ARBITRARY_ORDERING = new ArbitraryOrdering();
   }
}
