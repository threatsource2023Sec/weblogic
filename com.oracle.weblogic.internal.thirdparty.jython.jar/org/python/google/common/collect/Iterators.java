package org.python.google.common.collect;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Function;
import org.python.google.common.base.Objects;
import org.python.google.common.base.Optional;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Predicate;
import org.python.google.common.base.Predicates;
import org.python.google.common.primitives.Ints;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
public final class Iterators {
   private Iterators() {
   }

   static UnmodifiableIterator emptyIterator() {
      return emptyListIterator();
   }

   static UnmodifiableListIterator emptyListIterator() {
      return Iterators.ArrayItr.EMPTY;
   }

   static Iterator emptyModifiableIterator() {
      return Iterators.EmptyModifiableIterator.INSTANCE;
   }

   public static UnmodifiableIterator unmodifiableIterator(final Iterator iterator) {
      Preconditions.checkNotNull(iterator);
      if (iterator instanceof UnmodifiableIterator) {
         UnmodifiableIterator result = (UnmodifiableIterator)iterator;
         return result;
      } else {
         return new UnmodifiableIterator() {
            public boolean hasNext() {
               return iterator.hasNext();
            }

            public Object next() {
               return iterator.next();
            }
         };
      }
   }

   /** @deprecated */
   @Deprecated
   public static UnmodifiableIterator unmodifiableIterator(UnmodifiableIterator iterator) {
      return (UnmodifiableIterator)Preconditions.checkNotNull(iterator);
   }

   public static int size(Iterator iterator) {
      long count;
      for(count = 0L; iterator.hasNext(); ++count) {
         iterator.next();
      }

      return Ints.saturatedCast(count);
   }

   public static boolean contains(Iterator iterator, @Nullable Object element) {
      if (element == null) {
         while(iterator.hasNext()) {
            if (iterator.next() == null) {
               return true;
            }
         }
      } else {
         while(iterator.hasNext()) {
            if (element.equals(iterator.next())) {
               return true;
            }
         }
      }

      return false;
   }

   @CanIgnoreReturnValue
   public static boolean removeAll(Iterator removeFrom, Collection elementsToRemove) {
      Preconditions.checkNotNull(elementsToRemove);
      boolean result = false;

      while(removeFrom.hasNext()) {
         if (elementsToRemove.contains(removeFrom.next())) {
            removeFrom.remove();
            result = true;
         }
      }

      return result;
   }

   @CanIgnoreReturnValue
   public static boolean removeIf(Iterator removeFrom, Predicate predicate) {
      Preconditions.checkNotNull(predicate);
      boolean modified = false;

      while(removeFrom.hasNext()) {
         if (predicate.apply(removeFrom.next())) {
            removeFrom.remove();
            modified = true;
         }
      }

      return modified;
   }

   @CanIgnoreReturnValue
   public static boolean retainAll(Iterator removeFrom, Collection elementsToRetain) {
      Preconditions.checkNotNull(elementsToRetain);
      boolean result = false;

      while(removeFrom.hasNext()) {
         if (!elementsToRetain.contains(removeFrom.next())) {
            removeFrom.remove();
            result = true;
         }
      }

      return result;
   }

   public static boolean elementsEqual(Iterator iterator1, Iterator iterator2) {
      while(true) {
         if (iterator1.hasNext()) {
            if (!iterator2.hasNext()) {
               return false;
            }

            Object o1 = iterator1.next();
            Object o2 = iterator2.next();
            if (Objects.equal(o1, o2)) {
               continue;
            }

            return false;
         }

         return !iterator2.hasNext();
      }
   }

   public static String toString(Iterator iterator) {
      StringBuilder sb = (new StringBuilder()).append('[');
      boolean first = true;

      while(iterator.hasNext()) {
         if (!first) {
            sb.append(", ");
         }

         first = false;
         sb.append(iterator.next());
      }

      return sb.append(']').toString();
   }

   @CanIgnoreReturnValue
   public static Object getOnlyElement(Iterator iterator) {
      Object first = iterator.next();
      if (!iterator.hasNext()) {
         return first;
      } else {
         StringBuilder sb = (new StringBuilder()).append("expected one element but was: <").append(first);

         for(int i = 0; i < 4 && iterator.hasNext(); ++i) {
            sb.append(", ").append(iterator.next());
         }

         if (iterator.hasNext()) {
            sb.append(", ...");
         }

         sb.append('>');
         throw new IllegalArgumentException(sb.toString());
      }
   }

   @Nullable
   @CanIgnoreReturnValue
   public static Object getOnlyElement(Iterator iterator, @Nullable Object defaultValue) {
      return iterator.hasNext() ? getOnlyElement(iterator) : defaultValue;
   }

   @GwtIncompatible
   public static Object[] toArray(Iterator iterator, Class type) {
      List list = Lists.newArrayList(iterator);
      return Iterables.toArray(list, (Class)type);
   }

   @CanIgnoreReturnValue
   public static boolean addAll(Collection addTo, Iterator iterator) {
      Preconditions.checkNotNull(addTo);
      Preconditions.checkNotNull(iterator);

      boolean wasModified;
      for(wasModified = false; iterator.hasNext(); wasModified |= addTo.add(iterator.next())) {
      }

      return wasModified;
   }

   public static int frequency(Iterator iterator, @Nullable Object element) {
      return size(filter(iterator, Predicates.equalTo(element)));
   }

   public static Iterator cycle(final Iterable iterable) {
      Preconditions.checkNotNull(iterable);
      return new Iterator() {
         Iterator iterator = Iterators.emptyModifiableIterator();

         public boolean hasNext() {
            return this.iterator.hasNext() || iterable.iterator().hasNext();
         }

         public Object next() {
            if (!this.iterator.hasNext()) {
               this.iterator = iterable.iterator();
               if (!this.iterator.hasNext()) {
                  throw new NoSuchElementException();
               }
            }

            return this.iterator.next();
         }

         public void remove() {
            this.iterator.remove();
         }
      };
   }

   @SafeVarargs
   public static Iterator cycle(Object... elements) {
      return cycle((Iterable)Lists.newArrayList(elements));
   }

   public static Iterator concat(Iterator a, Iterator b) {
      Preconditions.checkNotNull(a);
      Preconditions.checkNotNull(b);
      return concat((Iterator)(new ConsumingQueueIterator(new Iterator[]{a, b})));
   }

   public static Iterator concat(Iterator a, Iterator b, Iterator c) {
      Preconditions.checkNotNull(a);
      Preconditions.checkNotNull(b);
      Preconditions.checkNotNull(c);
      return concat((Iterator)(new ConsumingQueueIterator(new Iterator[]{a, b, c})));
   }

   public static Iterator concat(Iterator a, Iterator b, Iterator c, Iterator d) {
      Preconditions.checkNotNull(a);
      Preconditions.checkNotNull(b);
      Preconditions.checkNotNull(c);
      Preconditions.checkNotNull(d);
      return concat((Iterator)(new ConsumingQueueIterator(new Iterator[]{a, b, c, d})));
   }

   public static Iterator concat(Iterator... inputs) {
      Iterator[] var1 = (Iterator[])Preconditions.checkNotNull(inputs);
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Iterator input = var1[var3];
         Preconditions.checkNotNull(input);
      }

      return concat((Iterator)(new ConsumingQueueIterator(inputs)));
   }

   public static Iterator concat(Iterator inputs) {
      return new ConcatenatedIterator(inputs);
   }

   public static UnmodifiableIterator partition(Iterator iterator, int size) {
      return partitionImpl(iterator, size, false);
   }

   public static UnmodifiableIterator paddedPartition(Iterator iterator, int size) {
      return partitionImpl(iterator, size, true);
   }

   private static UnmodifiableIterator partitionImpl(final Iterator iterator, final int size, final boolean pad) {
      Preconditions.checkNotNull(iterator);
      Preconditions.checkArgument(size > 0);
      return new UnmodifiableIterator() {
         public boolean hasNext() {
            return iterator.hasNext();
         }

         public List next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               Object[] array = new Object[size];

               int count;
               for(count = 0; count < size && iterator.hasNext(); ++count) {
                  array[count] = iterator.next();
               }

               for(int i = count; i < size; ++i) {
                  array[i] = null;
               }

               List list = Collections.unmodifiableList(Arrays.asList(array));
               return !pad && count != size ? list.subList(0, count) : list;
            }
         }
      };
   }

   public static UnmodifiableIterator filter(final Iterator unfiltered, final Predicate retainIfTrue) {
      Preconditions.checkNotNull(unfiltered);
      Preconditions.checkNotNull(retainIfTrue);
      return new AbstractIterator() {
         protected Object computeNext() {
            while(true) {
               if (unfiltered.hasNext()) {
                  Object element = unfiltered.next();
                  if (!retainIfTrue.apply(element)) {
                     continue;
                  }

                  return element;
               }

               return this.endOfData();
            }
         }
      };
   }

   @GwtIncompatible
   public static UnmodifiableIterator filter(Iterator unfiltered, Class desiredType) {
      return filter(unfiltered, Predicates.instanceOf(desiredType));
   }

   public static boolean any(Iterator iterator, Predicate predicate) {
      return indexOf(iterator, predicate) != -1;
   }

   public static boolean all(Iterator iterator, Predicate predicate) {
      Preconditions.checkNotNull(predicate);

      Object element;
      do {
         if (!iterator.hasNext()) {
            return true;
         }

         element = iterator.next();
      } while(predicate.apply(element));

      return false;
   }

   public static Object find(Iterator iterator, Predicate predicate) {
      return filter(iterator, predicate).next();
   }

   @Nullable
   public static Object find(Iterator iterator, Predicate predicate, @Nullable Object defaultValue) {
      return getNext(filter(iterator, predicate), defaultValue);
   }

   public static Optional tryFind(Iterator iterator, Predicate predicate) {
      UnmodifiableIterator filteredIterator = filter(iterator, predicate);
      return filteredIterator.hasNext() ? Optional.of(filteredIterator.next()) : Optional.absent();
   }

   public static int indexOf(Iterator iterator, Predicate predicate) {
      Preconditions.checkNotNull(predicate, "predicate");

      for(int i = 0; iterator.hasNext(); ++i) {
         Object current = iterator.next();
         if (predicate.apply(current)) {
            return i;
         }
      }

      return -1;
   }

   public static Iterator transform(Iterator fromIterator, final Function function) {
      Preconditions.checkNotNull(function);
      return new TransformedIterator(fromIterator) {
         Object transform(Object from) {
            return function.apply(from);
         }
      };
   }

   public static Object get(Iterator iterator, int position) {
      checkNonnegative(position);
      int skipped = advance(iterator, position);
      if (!iterator.hasNext()) {
         throw new IndexOutOfBoundsException("position (" + position + ") must be less than the number of elements that remained (" + skipped + ")");
      } else {
         return iterator.next();
      }
   }

   static void checkNonnegative(int position) {
      if (position < 0) {
         throw new IndexOutOfBoundsException("position (" + position + ") must not be negative");
      }
   }

   @Nullable
   public static Object get(Iterator iterator, int position, @Nullable Object defaultValue) {
      checkNonnegative(position);
      advance(iterator, position);
      return getNext(iterator, defaultValue);
   }

   @Nullable
   public static Object getNext(Iterator iterator, @Nullable Object defaultValue) {
      return iterator.hasNext() ? iterator.next() : defaultValue;
   }

   public static Object getLast(Iterator iterator) {
      Object current;
      do {
         current = iterator.next();
      } while(iterator.hasNext());

      return current;
   }

   @Nullable
   public static Object getLast(Iterator iterator, @Nullable Object defaultValue) {
      return iterator.hasNext() ? getLast(iterator) : defaultValue;
   }

   @CanIgnoreReturnValue
   public static int advance(Iterator iterator, int numberToAdvance) {
      Preconditions.checkNotNull(iterator);
      Preconditions.checkArgument(numberToAdvance >= 0, "numberToAdvance must be nonnegative");

      int i;
      for(i = 0; i < numberToAdvance && iterator.hasNext(); ++i) {
         iterator.next();
      }

      return i;
   }

   public static Iterator limit(final Iterator iterator, final int limitSize) {
      Preconditions.checkNotNull(iterator);
      Preconditions.checkArgument(limitSize >= 0, "limit is negative");
      return new Iterator() {
         private int count;

         public boolean hasNext() {
            return this.count < limitSize && iterator.hasNext();
         }

         public Object next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               ++this.count;
               return iterator.next();
            }
         }

         public void remove() {
            iterator.remove();
         }
      };
   }

   public static Iterator consumingIterator(final Iterator iterator) {
      Preconditions.checkNotNull(iterator);
      return new UnmodifiableIterator() {
         public boolean hasNext() {
            return iterator.hasNext();
         }

         public Object next() {
            Object next = iterator.next();
            iterator.remove();
            return next;
         }

         public String toString() {
            return "Iterators.consumingIterator(...)";
         }
      };
   }

   @Nullable
   static Object pollNext(Iterator iterator) {
      if (iterator.hasNext()) {
         Object result = iterator.next();
         iterator.remove();
         return result;
      } else {
         return null;
      }
   }

   static void clear(Iterator iterator) {
      Preconditions.checkNotNull(iterator);

      while(iterator.hasNext()) {
         iterator.next();
         iterator.remove();
      }

   }

   @SafeVarargs
   public static UnmodifiableIterator forArray(Object... array) {
      return forArray(array, 0, array.length, 0);
   }

   static UnmodifiableListIterator forArray(Object[] array, int offset, int length, int index) {
      Preconditions.checkArgument(length >= 0);
      int end = offset + length;
      Preconditions.checkPositionIndexes(offset, end, array.length);
      Preconditions.checkPositionIndex(index, length);
      return (UnmodifiableListIterator)(length == 0 ? emptyListIterator() : new ArrayItr(array, offset, length, index));
   }

   public static UnmodifiableIterator singletonIterator(@Nullable final Object value) {
      return new UnmodifiableIterator() {
         boolean done;

         public boolean hasNext() {
            return !this.done;
         }

         public Object next() {
            if (this.done) {
               throw new NoSuchElementException();
            } else {
               this.done = true;
               return value;
            }
         }
      };
   }

   public static UnmodifiableIterator forEnumeration(final Enumeration enumeration) {
      Preconditions.checkNotNull(enumeration);
      return new UnmodifiableIterator() {
         public boolean hasNext() {
            return enumeration.hasMoreElements();
         }

         public Object next() {
            return enumeration.nextElement();
         }
      };
   }

   public static Enumeration asEnumeration(final Iterator iterator) {
      Preconditions.checkNotNull(iterator);
      return new Enumeration() {
         public boolean hasMoreElements() {
            return iterator.hasNext();
         }

         public Object nextElement() {
            return iterator.next();
         }
      };
   }

   public static PeekingIterator peekingIterator(Iterator iterator) {
      if (iterator instanceof PeekingImpl) {
         PeekingImpl peeking = (PeekingImpl)iterator;
         return peeking;
      } else {
         return new PeekingImpl(iterator);
      }
   }

   /** @deprecated */
   @Deprecated
   public static PeekingIterator peekingIterator(PeekingIterator iterator) {
      return (PeekingIterator)Preconditions.checkNotNull(iterator);
   }

   @Beta
   public static UnmodifiableIterator mergeSorted(Iterable iterators, Comparator comparator) {
      Preconditions.checkNotNull(iterators, "iterators");
      Preconditions.checkNotNull(comparator, "comparator");
      return new MergingIterator(iterators, comparator);
   }

   static ListIterator cast(Iterator iterator) {
      return (ListIterator)iterator;
   }

   private static class ConcatenatedIterator extends MultitransformedIterator {
      public ConcatenatedIterator(Iterator iterators) {
         super(getComponentIterators(iterators));
      }

      Iterator transform(Iterator iterator) {
         return iterator;
      }

      private static Iterator getComponentIterators(Iterator iterators) {
         return new MultitransformedIterator(iterators) {
            Iterator transform(Iterator iterator) {
               if (iterator instanceof ConcatenatedIterator) {
                  ConcatenatedIterator concatIterator = (ConcatenatedIterator)iterator;
                  if (!concatIterator.current.hasNext()) {
                     return Iterators.ConcatenatedIterator.getComponentIterators(concatIterator.backingIterator);
                  }
               }

               return Iterators.singletonIterator(iterator);
            }
         };
      }
   }

   private static class MergingIterator extends UnmodifiableIterator {
      final Queue queue;

      public MergingIterator(Iterable iterators, final Comparator itemComparator) {
         Comparator heapComparator = new Comparator() {
            public int compare(PeekingIterator o1, PeekingIterator o2) {
               return itemComparator.compare(o1.peek(), o2.peek());
            }
         };
         this.queue = new PriorityQueue(2, heapComparator);
         Iterator var4 = iterators.iterator();

         while(var4.hasNext()) {
            Iterator iterator = (Iterator)var4.next();
            if (iterator.hasNext()) {
               this.queue.add(Iterators.peekingIterator(iterator));
            }
         }

      }

      public boolean hasNext() {
         return !this.queue.isEmpty();
      }

      public Object next() {
         PeekingIterator nextIter = (PeekingIterator)this.queue.remove();
         Object next = nextIter.next();
         if (nextIter.hasNext()) {
            this.queue.add(nextIter);
         }

         return next;
      }
   }

   private static class PeekingImpl implements PeekingIterator {
      private final Iterator iterator;
      private boolean hasPeeked;
      private Object peekedElement;

      public PeekingImpl(Iterator iterator) {
         this.iterator = (Iterator)Preconditions.checkNotNull(iterator);
      }

      public boolean hasNext() {
         return this.hasPeeked || this.iterator.hasNext();
      }

      public Object next() {
         if (!this.hasPeeked) {
            return this.iterator.next();
         } else {
            Object result = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return result;
         }
      }

      public void remove() {
         Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next");
         this.iterator.remove();
      }

      public Object peek() {
         if (!this.hasPeeked) {
            this.peekedElement = this.iterator.next();
            this.hasPeeked = true;
         }

         return this.peekedElement;
      }
   }

   private static final class ArrayItr extends AbstractIndexedListIterator {
      static final UnmodifiableListIterator EMPTY = new ArrayItr(new Object[0], 0, 0, 0);
      private final Object[] array;
      private final int offset;

      ArrayItr(Object[] array, int offset, int length, int index) {
         super(length, index);
         this.array = array;
         this.offset = offset;
      }

      protected Object get(int index) {
         return this.array[this.offset + index];
      }
   }

   private static enum EmptyModifiableIterator implements Iterator {
      INSTANCE;

      public boolean hasNext() {
         return false;
      }

      public Object next() {
         throw new NoSuchElementException();
      }

      public void remove() {
         CollectPreconditions.checkRemove(false);
      }
   }
}
