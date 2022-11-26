package org.python.google.common.collect;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Function;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Preconditions;
import org.python.google.common.math.IntMath;
import org.python.google.common.primitives.Ints;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
public final class Lists {
   private Lists() {
   }

   @GwtCompatible(
      serializable = true
   )
   public static ArrayList newArrayList() {
      return new ArrayList();
   }

   @SafeVarargs
   @CanIgnoreReturnValue
   @GwtCompatible(
      serializable = true
   )
   public static ArrayList newArrayList(Object... elements) {
      Preconditions.checkNotNull(elements);
      int capacity = computeArrayListCapacity(elements.length);
      ArrayList list = new ArrayList(capacity);
      Collections.addAll(list, elements);
      return list;
   }

   @VisibleForTesting
   static int computeArrayListCapacity(int arraySize) {
      CollectPreconditions.checkNonnegative(arraySize, "arraySize");
      return Ints.saturatedCast(5L + (long)arraySize + (long)(arraySize / 10));
   }

   @CanIgnoreReturnValue
   @GwtCompatible(
      serializable = true
   )
   public static ArrayList newArrayList(Iterable elements) {
      Preconditions.checkNotNull(elements);
      return elements instanceof Collection ? new ArrayList(Collections2.cast(elements)) : newArrayList(elements.iterator());
   }

   @CanIgnoreReturnValue
   @GwtCompatible(
      serializable = true
   )
   public static ArrayList newArrayList(Iterator elements) {
      ArrayList list = newArrayList();
      Iterators.addAll(list, elements);
      return list;
   }

   @GwtCompatible(
      serializable = true
   )
   public static ArrayList newArrayListWithCapacity(int initialArraySize) {
      CollectPreconditions.checkNonnegative(initialArraySize, "initialArraySize");
      return new ArrayList(initialArraySize);
   }

   @GwtCompatible(
      serializable = true
   )
   public static ArrayList newArrayListWithExpectedSize(int estimatedSize) {
      return new ArrayList(computeArrayListCapacity(estimatedSize));
   }

   @GwtCompatible(
      serializable = true
   )
   public static LinkedList newLinkedList() {
      return new LinkedList();
   }

   @GwtCompatible(
      serializable = true
   )
   public static LinkedList newLinkedList(Iterable elements) {
      LinkedList list = newLinkedList();
      Iterables.addAll(list, elements);
      return list;
   }

   @GwtIncompatible
   public static CopyOnWriteArrayList newCopyOnWriteArrayList() {
      return new CopyOnWriteArrayList();
   }

   @GwtIncompatible
   public static CopyOnWriteArrayList newCopyOnWriteArrayList(Iterable elements) {
      Collection elementsCollection = elements instanceof Collection ? Collections2.cast(elements) : newArrayList(elements);
      return new CopyOnWriteArrayList((Collection)elementsCollection);
   }

   public static List asList(@Nullable Object first, Object[] rest) {
      return new OnePlusArrayList(first, rest);
   }

   public static List asList(@Nullable Object first, @Nullable Object second, Object[] rest) {
      return new TwoPlusArrayList(first, second, rest);
   }

   public static List cartesianProduct(List lists) {
      return CartesianList.create(lists);
   }

   @SafeVarargs
   public static List cartesianProduct(List... lists) {
      return cartesianProduct(Arrays.asList(lists));
   }

   public static List transform(List fromList, Function function) {
      return (List)(fromList instanceof RandomAccess ? new TransformingRandomAccessList(fromList, function) : new TransformingSequentialList(fromList, function));
   }

   public static List partition(List list, int size) {
      Preconditions.checkNotNull(list);
      Preconditions.checkArgument(size > 0);
      return (List)(list instanceof RandomAccess ? new RandomAccessPartition(list, size) : new Partition(list, size));
   }

   public static ImmutableList charactersOf(String string) {
      return new StringAsImmutableList((String)Preconditions.checkNotNull(string));
   }

   @Beta
   public static List charactersOf(CharSequence sequence) {
      return new CharSequenceAsList((CharSequence)Preconditions.checkNotNull(sequence));
   }

   public static List reverse(List list) {
      if (list instanceof ImmutableList) {
         return ((ImmutableList)list).reverse();
      } else if (list instanceof ReverseList) {
         return ((ReverseList)list).getForwardList();
      } else {
         return (List)(list instanceof RandomAccess ? new RandomAccessReverseList(list) : new ReverseList(list));
      }
   }

   static int hashCodeImpl(List list) {
      int hashCode = 1;

      for(Iterator var2 = list.iterator(); var2.hasNext(); hashCode = ~(~hashCode)) {
         Object o = var2.next();
         hashCode = 31 * hashCode + (o == null ? 0 : o.hashCode());
      }

      return hashCode;
   }

   static boolean equalsImpl(List thisList, @Nullable Object other) {
      if (other == Preconditions.checkNotNull(thisList)) {
         return true;
      } else if (!(other instanceof List)) {
         return false;
      } else {
         List otherList = (List)other;
         int size = thisList.size();
         if (size != otherList.size()) {
            return false;
         } else if (thisList instanceof RandomAccess && otherList instanceof RandomAccess) {
            for(int i = 0; i < size; ++i) {
               if (!Objects.equal(thisList.get(i), otherList.get(i))) {
                  return false;
               }
            }

            return true;
         } else {
            return Iterators.elementsEqual(thisList.iterator(), otherList.iterator());
         }
      }
   }

   static boolean addAllImpl(List list, int index, Iterable elements) {
      boolean changed = false;
      ListIterator listIterator = list.listIterator(index);

      for(Iterator var5 = elements.iterator(); var5.hasNext(); changed = true) {
         Object e = var5.next();
         listIterator.add(e);
      }

      return changed;
   }

   static int indexOfImpl(List list, @Nullable Object element) {
      if (list instanceof RandomAccess) {
         return indexOfRandomAccess(list, element);
      } else {
         ListIterator listIterator = list.listIterator();

         do {
            if (!listIterator.hasNext()) {
               return -1;
            }
         } while(!Objects.equal(element, listIterator.next()));

         return listIterator.previousIndex();
      }
   }

   private static int indexOfRandomAccess(List list, @Nullable Object element) {
      int size = list.size();
      int i;
      if (element == null) {
         for(i = 0; i < size; ++i) {
            if (list.get(i) == null) {
               return i;
            }
         }
      } else {
         for(i = 0; i < size; ++i) {
            if (element.equals(list.get(i))) {
               return i;
            }
         }
      }

      return -1;
   }

   static int lastIndexOfImpl(List list, @Nullable Object element) {
      if (list instanceof RandomAccess) {
         return lastIndexOfRandomAccess(list, element);
      } else {
         ListIterator listIterator = list.listIterator(list.size());

         do {
            if (!listIterator.hasPrevious()) {
               return -1;
            }
         } while(!Objects.equal(element, listIterator.previous()));

         return listIterator.nextIndex();
      }
   }

   private static int lastIndexOfRandomAccess(List list, @Nullable Object element) {
      int i;
      if (element == null) {
         for(i = list.size() - 1; i >= 0; --i) {
            if (list.get(i) == null) {
               return i;
            }
         }
      } else {
         for(i = list.size() - 1; i >= 0; --i) {
            if (element.equals(list.get(i))) {
               return i;
            }
         }
      }

      return -1;
   }

   static ListIterator listIteratorImpl(List list, int index) {
      return (new AbstractListWrapper(list)).listIterator(index);
   }

   static List subListImpl(List list, int fromIndex, int toIndex) {
      Object wrapper;
      if (list instanceof RandomAccess) {
         wrapper = new RandomAccessListWrapper(list) {
            private static final long serialVersionUID = 0L;

            public ListIterator listIterator(int index) {
               return this.backingList.listIterator(index);
            }
         };
      } else {
         wrapper = new AbstractListWrapper(list) {
            private static final long serialVersionUID = 0L;

            public ListIterator listIterator(int index) {
               return this.backingList.listIterator(index);
            }
         };
      }

      return ((List)wrapper).subList(fromIndex, toIndex);
   }

   static List cast(Iterable iterable) {
      return (List)iterable;
   }

   private static class RandomAccessListWrapper extends AbstractListWrapper implements RandomAccess {
      RandomAccessListWrapper(List backingList) {
         super(backingList);
      }
   }

   private static class AbstractListWrapper extends AbstractList {
      final List backingList;

      AbstractListWrapper(List backingList) {
         this.backingList = (List)Preconditions.checkNotNull(backingList);
      }

      public void add(int index, Object element) {
         this.backingList.add(index, element);
      }

      public boolean addAll(int index, Collection c) {
         return this.backingList.addAll(index, c);
      }

      public Object get(int index) {
         return this.backingList.get(index);
      }

      public Object remove(int index) {
         return this.backingList.remove(index);
      }

      public Object set(int index, Object element) {
         return this.backingList.set(index, element);
      }

      public boolean contains(Object o) {
         return this.backingList.contains(o);
      }

      public int size() {
         return this.backingList.size();
      }
   }

   private static class RandomAccessReverseList extends ReverseList implements RandomAccess {
      RandomAccessReverseList(List forwardList) {
         super(forwardList);
      }
   }

   private static class ReverseList extends AbstractList {
      private final List forwardList;

      ReverseList(List forwardList) {
         this.forwardList = (List)Preconditions.checkNotNull(forwardList);
      }

      List getForwardList() {
         return this.forwardList;
      }

      private int reverseIndex(int index) {
         int size = this.size();
         Preconditions.checkElementIndex(index, size);
         return size - 1 - index;
      }

      private int reversePosition(int index) {
         int size = this.size();
         Preconditions.checkPositionIndex(index, size);
         return size - index;
      }

      public void add(int index, @Nullable Object element) {
         this.forwardList.add(this.reversePosition(index), element);
      }

      public void clear() {
         this.forwardList.clear();
      }

      public Object remove(int index) {
         return this.forwardList.remove(this.reverseIndex(index));
      }

      protected void removeRange(int fromIndex, int toIndex) {
         this.subList(fromIndex, toIndex).clear();
      }

      public Object set(int index, @Nullable Object element) {
         return this.forwardList.set(this.reverseIndex(index), element);
      }

      public Object get(int index) {
         return this.forwardList.get(this.reverseIndex(index));
      }

      public int size() {
         return this.forwardList.size();
      }

      public List subList(int fromIndex, int toIndex) {
         Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size());
         return Lists.reverse(this.forwardList.subList(this.reversePosition(toIndex), this.reversePosition(fromIndex)));
      }

      public Iterator iterator() {
         return this.listIterator();
      }

      public ListIterator listIterator(int index) {
         int start = this.reversePosition(index);
         final ListIterator forwardIterator = this.forwardList.listIterator(start);
         return new ListIterator() {
            boolean canRemoveOrSet;

            public void add(Object e) {
               forwardIterator.add(e);
               forwardIterator.previous();
               this.canRemoveOrSet = false;
            }

            public boolean hasNext() {
               return forwardIterator.hasPrevious();
            }

            public boolean hasPrevious() {
               return forwardIterator.hasNext();
            }

            public Object next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  this.canRemoveOrSet = true;
                  return forwardIterator.previous();
               }
            }

            public int nextIndex() {
               return ReverseList.this.reversePosition(forwardIterator.nextIndex());
            }

            public Object previous() {
               if (!this.hasPrevious()) {
                  throw new NoSuchElementException();
               } else {
                  this.canRemoveOrSet = true;
                  return forwardIterator.next();
               }
            }

            public int previousIndex() {
               return this.nextIndex() - 1;
            }

            public void remove() {
               CollectPreconditions.checkRemove(this.canRemoveOrSet);
               forwardIterator.remove();
               this.canRemoveOrSet = false;
            }

            public void set(Object e) {
               Preconditions.checkState(this.canRemoveOrSet);
               forwardIterator.set(e);
            }
         };
      }
   }

   private static final class CharSequenceAsList extends AbstractList {
      private final CharSequence sequence;

      CharSequenceAsList(CharSequence sequence) {
         this.sequence = sequence;
      }

      public Character get(int index) {
         Preconditions.checkElementIndex(index, this.size());
         return this.sequence.charAt(index);
      }

      public int size() {
         return this.sequence.length();
      }
   }

   private static final class StringAsImmutableList extends ImmutableList {
      private final String string;

      StringAsImmutableList(String string) {
         this.string = string;
      }

      public int indexOf(@Nullable Object object) {
         return object instanceof Character ? this.string.indexOf((Character)object) : -1;
      }

      public int lastIndexOf(@Nullable Object object) {
         return object instanceof Character ? this.string.lastIndexOf((Character)object) : -1;
      }

      public ImmutableList subList(int fromIndex, int toIndex) {
         Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size());
         return Lists.charactersOf(this.string.substring(fromIndex, toIndex));
      }

      boolean isPartialView() {
         return false;
      }

      public Character get(int index) {
         Preconditions.checkElementIndex(index, this.size());
         return this.string.charAt(index);
      }

      public int size() {
         return this.string.length();
      }
   }

   private static class RandomAccessPartition extends Partition implements RandomAccess {
      RandomAccessPartition(List list, int size) {
         super(list, size);
      }
   }

   private static class Partition extends AbstractList {
      final List list;
      final int size;

      Partition(List list, int size) {
         this.list = list;
         this.size = size;
      }

      public List get(int index) {
         Preconditions.checkElementIndex(index, this.size());
         int start = index * this.size;
         int end = Math.min(start + this.size, this.list.size());
         return this.list.subList(start, end);
      }

      public int size() {
         return IntMath.divide(this.list.size(), this.size, RoundingMode.CEILING);
      }

      public boolean isEmpty() {
         return this.list.isEmpty();
      }
   }

   private static class TransformingRandomAccessList extends AbstractList implements RandomAccess, Serializable {
      final List fromList;
      final Function function;
      private static final long serialVersionUID = 0L;

      TransformingRandomAccessList(List fromList, Function function) {
         this.fromList = (List)Preconditions.checkNotNull(fromList);
         this.function = (Function)Preconditions.checkNotNull(function);
      }

      public void clear() {
         this.fromList.clear();
      }

      public Object get(int index) {
         return this.function.apply(this.fromList.get(index));
      }

      public Iterator iterator() {
         return this.listIterator();
      }

      public ListIterator listIterator(int index) {
         return new TransformedListIterator(this.fromList.listIterator(index)) {
            Object transform(Object from) {
               return TransformingRandomAccessList.this.function.apply(from);
            }
         };
      }

      public boolean isEmpty() {
         return this.fromList.isEmpty();
      }

      public Object remove(int index) {
         return this.function.apply(this.fromList.remove(index));
      }

      public int size() {
         return this.fromList.size();
      }
   }

   private static class TransformingSequentialList extends AbstractSequentialList implements Serializable {
      final List fromList;
      final Function function;
      private static final long serialVersionUID = 0L;

      TransformingSequentialList(List fromList, Function function) {
         this.fromList = (List)Preconditions.checkNotNull(fromList);
         this.function = (Function)Preconditions.checkNotNull(function);
      }

      public void clear() {
         this.fromList.clear();
      }

      public int size() {
         return this.fromList.size();
      }

      public ListIterator listIterator(int index) {
         return new TransformedListIterator(this.fromList.listIterator(index)) {
            Object transform(Object from) {
               return TransformingSequentialList.this.function.apply(from);
            }
         };
      }
   }

   private static class TwoPlusArrayList extends AbstractList implements Serializable, RandomAccess {
      final Object first;
      final Object second;
      final Object[] rest;
      private static final long serialVersionUID = 0L;

      TwoPlusArrayList(@Nullable Object first, @Nullable Object second, Object[] rest) {
         this.first = first;
         this.second = second;
         this.rest = (Object[])Preconditions.checkNotNull(rest);
      }

      public int size() {
         return IntMath.saturatedAdd(this.rest.length, 2);
      }

      public Object get(int index) {
         switch (index) {
            case 0:
               return this.first;
            case 1:
               return this.second;
            default:
               Preconditions.checkElementIndex(index, this.size());
               return this.rest[index - 2];
         }
      }
   }

   private static class OnePlusArrayList extends AbstractList implements Serializable, RandomAccess {
      final Object first;
      final Object[] rest;
      private static final long serialVersionUID = 0L;

      OnePlusArrayList(@Nullable Object first, Object[] rest) {
         this.first = first;
         this.rest = (Object[])Preconditions.checkNotNull(rest);
      }

      public int size() {
         return IntMath.saturatedAdd(this.rest.length, 1);
      }

      public Object get(int index) {
         Preconditions.checkElementIndex(index, this.size());
         return index == 0 ? this.first : this.rest[index - 1];
      }
   }
}
