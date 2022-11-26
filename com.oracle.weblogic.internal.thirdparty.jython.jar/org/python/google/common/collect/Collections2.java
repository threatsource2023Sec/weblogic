package org.python.google.common.collect;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.base.Predicates;
import org.python.google.common.math.IntMath;
import org.python.google.common.math.LongMath;

@GwtCompatible
public final class Collections2 {
   private Collections2() {
   }

   public static Collection filter(Collection unfiltered, Predicate predicate) {
      return unfiltered instanceof FilteredCollection ? ((FilteredCollection)unfiltered).createCombined(predicate) : new FilteredCollection((Collection)Preconditions.checkNotNull(unfiltered), (Predicate)Preconditions.checkNotNull(predicate));
   }

   static boolean safeContains(Collection collection, @Nullable Object object) {
      Preconditions.checkNotNull(collection);

      try {
         return collection.contains(object);
      } catch (ClassCastException var3) {
         return false;
      } catch (NullPointerException var4) {
         return false;
      }
   }

   static boolean safeRemove(Collection collection, @Nullable Object object) {
      Preconditions.checkNotNull(collection);

      try {
         return collection.remove(object);
      } catch (ClassCastException var3) {
         return false;
      } catch (NullPointerException var4) {
         return false;
      }
   }

   public static Collection transform(Collection fromCollection, Function function) {
      return new TransformedCollection(fromCollection, function);
   }

   static boolean containsAllImpl(Collection self, Collection c) {
      return Iterables.all(c, Predicates.in(self));
   }

   static String toStringImpl(Collection collection) {
      StringBuilder sb = newStringBuilderForCollection(collection.size()).append('[');
      boolean first = true;
      Iterator var3 = collection.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         if (!first) {
            sb.append(", ");
         }

         first = false;
         if (o == collection) {
            sb.append("(this Collection)");
         } else {
            sb.append(o);
         }
      }

      return sb.append(']').toString();
   }

   static StringBuilder newStringBuilderForCollection(int size) {
      CollectPreconditions.checkNonnegative(size, "size");
      return new StringBuilder((int)Math.min((long)size * 8L, 1073741824L));
   }

   static Collection cast(Iterable iterable) {
      return (Collection)iterable;
   }

   @Beta
   public static Collection orderedPermutations(Iterable elements) {
      return orderedPermutations(elements, Ordering.natural());
   }

   @Beta
   public static Collection orderedPermutations(Iterable elements, Comparator comparator) {
      return new OrderedPermutationCollection(elements, comparator);
   }

   @Beta
   public static Collection permutations(Collection elements) {
      return new PermutationCollection(ImmutableList.copyOf(elements));
   }

   private static boolean isPermutation(List first, List second) {
      if (first.size() != second.size()) {
         return false;
      } else {
         Multiset firstMultiset = HashMultiset.create(first);
         Multiset secondMultiset = HashMultiset.create(second);
         return firstMultiset.equals(secondMultiset);
      }
   }

   private static boolean isPositiveInt(long n) {
      return n >= 0L && n <= 2147483647L;
   }

   private static class PermutationIterator extends AbstractIterator {
      final List list;
      final int[] c;
      final int[] o;
      int j;

      PermutationIterator(List list) {
         this.list = new ArrayList(list);
         int n = list.size();
         this.c = new int[n];
         this.o = new int[n];
         Arrays.fill(this.c, 0);
         Arrays.fill(this.o, 1);
         this.j = Integer.MAX_VALUE;
      }

      protected List computeNext() {
         if (this.j <= 0) {
            return (List)this.endOfData();
         } else {
            ImmutableList next = ImmutableList.copyOf((Collection)this.list);
            this.calculateNextPermutation();
            return next;
         }
      }

      void calculateNextPermutation() {
         this.j = this.list.size() - 1;
         int s = 0;
         if (this.j != -1) {
            while(true) {
               while(true) {
                  int q = this.c[this.j] + this.o[this.j];
                  if (q >= 0) {
                     if (q != this.j + 1) {
                        Collections.swap(this.list, this.j - this.c[this.j] + s, this.j - q + s);
                        this.c[this.j] = q;
                        return;
                     }

                     if (this.j == 0) {
                        return;
                     }

                     ++s;
                     this.switchDirection();
                  } else {
                     this.switchDirection();
                  }
               }
            }
         }
      }

      void switchDirection() {
         this.o[this.j] = -this.o[this.j];
         --this.j;
      }
   }

   private static final class PermutationCollection extends AbstractCollection {
      final ImmutableList inputList;

      PermutationCollection(ImmutableList input) {
         this.inputList = input;
      }

      public int size() {
         return IntMath.factorial(this.inputList.size());
      }

      public boolean isEmpty() {
         return false;
      }

      public Iterator iterator() {
         return new PermutationIterator(this.inputList);
      }

      public boolean contains(@Nullable Object obj) {
         if (obj instanceof List) {
            List list = (List)obj;
            return Collections2.isPermutation(this.inputList, list);
         } else {
            return false;
         }
      }

      public String toString() {
         return "permutations(" + this.inputList + ")";
      }
   }

   private static final class OrderedPermutationIterator extends AbstractIterator {
      List nextPermutation;
      final Comparator comparator;

      OrderedPermutationIterator(List list, Comparator comparator) {
         this.nextPermutation = Lists.newArrayList((Iterable)list);
         this.comparator = comparator;
      }

      protected List computeNext() {
         if (this.nextPermutation == null) {
            return (List)this.endOfData();
         } else {
            ImmutableList next = ImmutableList.copyOf((Collection)this.nextPermutation);
            this.calculateNextPermutation();
            return next;
         }
      }

      void calculateNextPermutation() {
         int j = this.findNextJ();
         if (j == -1) {
            this.nextPermutation = null;
         } else {
            int l = this.findNextL(j);
            Collections.swap(this.nextPermutation, j, l);
            int n = this.nextPermutation.size();
            Collections.reverse(this.nextPermutation.subList(j + 1, n));
         }
      }

      int findNextJ() {
         for(int k = this.nextPermutation.size() - 2; k >= 0; --k) {
            if (this.comparator.compare(this.nextPermutation.get(k), this.nextPermutation.get(k + 1)) < 0) {
               return k;
            }
         }

         return -1;
      }

      int findNextL(int j) {
         Object ak = this.nextPermutation.get(j);

         for(int l = this.nextPermutation.size() - 1; l > j; --l) {
            if (this.comparator.compare(ak, this.nextPermutation.get(l)) < 0) {
               return l;
            }
         }

         throw new AssertionError("this statement should be unreachable");
      }
   }

   private static final class OrderedPermutationCollection extends AbstractCollection {
      final ImmutableList inputList;
      final Comparator comparator;
      final int size;

      OrderedPermutationCollection(Iterable input, Comparator comparator) {
         this.inputList = Ordering.from(comparator).immutableSortedCopy(input);
         this.comparator = comparator;
         this.size = calculateSize(this.inputList, comparator);
      }

      private static int calculateSize(List sortedInputList, Comparator comparator) {
         long permutations = 1L;
         int n = 1;

         int r;
         for(r = 1; n < sortedInputList.size(); ++r) {
            int comparison = comparator.compare(sortedInputList.get(n - 1), sortedInputList.get(n));
            if (comparison < 0) {
               permutations *= LongMath.binomial(n, r);
               r = 0;
               if (!Collections2.isPositiveInt(permutations)) {
                  return Integer.MAX_VALUE;
               }
            }

            ++n;
         }

         permutations *= LongMath.binomial(n, r);
         if (!Collections2.isPositiveInt(permutations)) {
            return Integer.MAX_VALUE;
         } else {
            return (int)permutations;
         }
      }

      public int size() {
         return this.size;
      }

      public boolean isEmpty() {
         return false;
      }

      public Iterator iterator() {
         return new OrderedPermutationIterator(this.inputList, this.comparator);
      }

      public boolean contains(@Nullable Object obj) {
         if (obj instanceof List) {
            List list = (List)obj;
            return Collections2.isPermutation(this.inputList, list);
         } else {
            return false;
         }
      }

      public String toString() {
         return "orderedPermutationCollection(" + this.inputList + ")";
      }
   }

   static class TransformedCollection extends AbstractCollection {
      final Collection fromCollection;
      final Function function;

      TransformedCollection(Collection fromCollection, Function function) {
         this.fromCollection = (Collection)Preconditions.checkNotNull(fromCollection);
         this.function = (Function)Preconditions.checkNotNull(function);
      }

      public void clear() {
         this.fromCollection.clear();
      }

      public boolean isEmpty() {
         return this.fromCollection.isEmpty();
      }

      public Iterator iterator() {
         return Iterators.transform(this.fromCollection.iterator(), this.function);
      }

      public int size() {
         return this.fromCollection.size();
      }
   }

   static class FilteredCollection extends AbstractCollection {
      final Collection unfiltered;
      final Predicate predicate;

      FilteredCollection(Collection unfiltered, Predicate predicate) {
         this.unfiltered = unfiltered;
         this.predicate = predicate;
      }

      FilteredCollection createCombined(Predicate newPredicate) {
         return new FilteredCollection(this.unfiltered, Predicates.and(this.predicate, newPredicate));
      }

      public boolean add(Object element) {
         Preconditions.checkArgument(this.predicate.apply(element));
         return this.unfiltered.add(element);
      }

      public boolean addAll(Collection collection) {
         Iterator var2 = collection.iterator();

         while(var2.hasNext()) {
            Object element = var2.next();
            Preconditions.checkArgument(this.predicate.apply(element));
         }

         return this.unfiltered.addAll(collection);
      }

      public void clear() {
         Iterables.removeIf(this.unfiltered, this.predicate);
      }

      public boolean contains(@Nullable Object element) {
         return Collections2.safeContains(this.unfiltered, element) ? this.predicate.apply(element) : false;
      }

      public boolean containsAll(Collection collection) {
         return Collections2.containsAllImpl(this, collection);
      }

      public boolean isEmpty() {
         return !Iterables.any(this.unfiltered, this.predicate);
      }

      public Iterator iterator() {
         return Iterators.filter(this.unfiltered.iterator(), this.predicate);
      }

      public boolean remove(Object element) {
         return this.contains(element) && this.unfiltered.remove(element);
      }

      public boolean removeAll(Collection collection) {
         return Iterables.removeIf(this.unfiltered, Predicates.and(this.predicate, Predicates.in(collection)));
      }

      public boolean retainAll(Collection collection) {
         return Iterables.removeIf(this.unfiltered, Predicates.and(this.predicate, Predicates.not(Predicates.in(collection))));
      }

      public int size() {
         return Iterators.size(this.iterator());
      }

      public Object[] toArray() {
         return Lists.newArrayList(this.iterator()).toArray();
      }

      public Object[] toArray(Object[] array) {
         return Lists.newArrayList(this.iterator()).toArray(array);
      }
   }
}
