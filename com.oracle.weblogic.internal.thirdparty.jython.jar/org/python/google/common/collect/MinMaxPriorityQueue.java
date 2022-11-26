package org.python.google.common.collect;

import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;
import org.python.google.common.math.IntMath;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;
import org.python.google.j2objc.annotations.Weak;

@Beta
@GwtCompatible
public final class MinMaxPriorityQueue extends AbstractQueue {
   private final Heap minHeap;
   private final Heap maxHeap;
   @VisibleForTesting
   final int maximumSize;
   private Object[] queue;
   private int size;
   private int modCount;
   private static final int EVEN_POWERS_OF_TWO = 1431655765;
   private static final int ODD_POWERS_OF_TWO = -1431655766;
   private static final int DEFAULT_CAPACITY = 11;

   public static MinMaxPriorityQueue create() {
      return (new Builder(Ordering.natural())).create();
   }

   public static MinMaxPriorityQueue create(Iterable initialContents) {
      return (new Builder(Ordering.natural())).create(initialContents);
   }

   public static Builder orderedBy(Comparator comparator) {
      return new Builder(comparator);
   }

   public static Builder expectedSize(int expectedSize) {
      return (new Builder(Ordering.natural())).expectedSize(expectedSize);
   }

   public static Builder maximumSize(int maximumSize) {
      return (new Builder(Ordering.natural())).maximumSize(maximumSize);
   }

   private MinMaxPriorityQueue(Builder builder, int queueSize) {
      Ordering ordering = builder.ordering();
      this.minHeap = new Heap(ordering);
      this.maxHeap = new Heap(ordering.reverse());
      this.minHeap.otherHeap = this.maxHeap;
      this.maxHeap.otherHeap = this.minHeap;
      this.maximumSize = builder.maximumSize;
      this.queue = new Object[queueSize];
   }

   public int size() {
      return this.size;
   }

   @CanIgnoreReturnValue
   public boolean add(Object element) {
      this.offer(element);
      return true;
   }

   @CanIgnoreReturnValue
   public boolean addAll(Collection newElements) {
      boolean modified = false;

      for(Iterator var3 = newElements.iterator(); var3.hasNext(); modified = true) {
         Object element = var3.next();
         this.offer(element);
      }

      return modified;
   }

   @CanIgnoreReturnValue
   public boolean offer(Object element) {
      Preconditions.checkNotNull(element);
      ++this.modCount;
      int insertIndex = this.size++;
      this.growIfNeeded();
      this.heapForIndex(insertIndex).bubbleUp(insertIndex, element);
      return this.size <= this.maximumSize || this.pollLast() != element;
   }

   @CanIgnoreReturnValue
   public Object poll() {
      return this.isEmpty() ? null : this.removeAndGet(0);
   }

   Object elementData(int index) {
      return this.queue[index];
   }

   public Object peek() {
      return this.isEmpty() ? null : this.elementData(0);
   }

   private int getMaxElementIndex() {
      switch (this.size) {
         case 1:
            return 0;
         case 2:
            return 1;
         default:
            return this.maxHeap.compareElements(1, 2) <= 0 ? 1 : 2;
      }
   }

   @CanIgnoreReturnValue
   public Object pollFirst() {
      return this.poll();
   }

   @CanIgnoreReturnValue
   public Object removeFirst() {
      return this.remove();
   }

   public Object peekFirst() {
      return this.peek();
   }

   @CanIgnoreReturnValue
   public Object pollLast() {
      return this.isEmpty() ? null : this.removeAndGet(this.getMaxElementIndex());
   }

   @CanIgnoreReturnValue
   public Object removeLast() {
      if (this.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         return this.removeAndGet(this.getMaxElementIndex());
      }
   }

   public Object peekLast() {
      return this.isEmpty() ? null : this.elementData(this.getMaxElementIndex());
   }

   @VisibleForTesting
   @CanIgnoreReturnValue
   MoveDesc removeAt(int index) {
      Preconditions.checkPositionIndex(index, this.size);
      ++this.modCount;
      --this.size;
      if (this.size == index) {
         this.queue[this.size] = null;
         return null;
      } else {
         Object actualLastElement = this.elementData(this.size);
         int lastElementAt = this.heapForIndex(this.size).swapWithConceptuallyLastElement(actualLastElement);
         if (lastElementAt == index) {
            this.queue[this.size] = null;
            return null;
         } else {
            Object toTrickle = this.elementData(this.size);
            this.queue[this.size] = null;
            MoveDesc changes = this.fillHole(index, toTrickle);
            if (lastElementAt < index) {
               return changes == null ? new MoveDesc(actualLastElement, toTrickle) : new MoveDesc(actualLastElement, changes.replaced);
            } else {
               return changes;
            }
         }
      }
   }

   private MoveDesc fillHole(int index, Object toTrickle) {
      Heap heap = this.heapForIndex(index);
      int vacated = heap.fillHoleAt(index);
      int bubbledTo = heap.bubbleUpAlternatingLevels(vacated, toTrickle);
      if (bubbledTo == vacated) {
         return heap.tryCrossOverAndBubbleUp(index, vacated, toTrickle);
      } else {
         return bubbledTo < index ? new MoveDesc(toTrickle, this.elementData(index)) : null;
      }
   }

   private Object removeAndGet(int index) {
      Object value = this.elementData(index);
      this.removeAt(index);
      return value;
   }

   private Heap heapForIndex(int i) {
      return isEvenLevel(i) ? this.minHeap : this.maxHeap;
   }

   @VisibleForTesting
   static boolean isEvenLevel(int index) {
      int oneBased = ~(~(index + 1));
      Preconditions.checkState(oneBased > 0, "negative index");
      return (oneBased & 1431655765) > (oneBased & -1431655766);
   }

   @VisibleForTesting
   boolean isIntact() {
      for(int i = 1; i < this.size; ++i) {
         if (!this.heapForIndex(i).verifyIndex(i)) {
            return false;
         }
      }

      return true;
   }

   public Iterator iterator() {
      return new QueueIterator();
   }

   public void clear() {
      for(int i = 0; i < this.size; ++i) {
         this.queue[i] = null;
      }

      this.size = 0;
   }

   public Object[] toArray() {
      Object[] copyTo = new Object[this.size];
      System.arraycopy(this.queue, 0, copyTo, 0, this.size);
      return copyTo;
   }

   public Comparator comparator() {
      return this.minHeap.ordering;
   }

   @VisibleForTesting
   int capacity() {
      return this.queue.length;
   }

   @VisibleForTesting
   static int initialQueueSize(int configuredExpectedSize, int maximumSize, Iterable initialContents) {
      int result = configuredExpectedSize == -1 ? 11 : configuredExpectedSize;
      if (initialContents instanceof Collection) {
         int initialSize = ((Collection)initialContents).size();
         result = Math.max(result, initialSize);
      }

      return capAtMaximumSize(result, maximumSize);
   }

   private void growIfNeeded() {
      if (this.size > this.queue.length) {
         int newCapacity = this.calculateNewCapacity();
         Object[] newQueue = new Object[newCapacity];
         System.arraycopy(this.queue, 0, newQueue, 0, this.queue.length);
         this.queue = newQueue;
      }

   }

   private int calculateNewCapacity() {
      int oldCapacity = this.queue.length;
      int newCapacity = oldCapacity < 64 ? (oldCapacity + 1) * 2 : IntMath.checkedMultiply(oldCapacity / 2, 3);
      return capAtMaximumSize(newCapacity, this.maximumSize);
   }

   private static int capAtMaximumSize(int queueSize, int maximumSize) {
      return Math.min(queueSize - 1, maximumSize) + 1;
   }

   // $FF: synthetic method
   MinMaxPriorityQueue(Builder x0, int x1, Object x2) {
      this(x0, x1);
   }

   private class QueueIterator implements Iterator {
      private int cursor;
      private int nextCursor;
      private int expectedModCount;
      private Queue forgetMeNot;
      private List skipMe;
      private Object lastFromForgetMeNot;
      private boolean canRemove;

      private QueueIterator() {
         this.cursor = -1;
         this.nextCursor = -1;
         this.expectedModCount = MinMaxPriorityQueue.this.modCount;
      }

      public boolean hasNext() {
         this.checkModCount();
         this.nextNotInSkipMe(this.cursor + 1);
         return this.nextCursor < MinMaxPriorityQueue.this.size() || this.forgetMeNot != null && !this.forgetMeNot.isEmpty();
      }

      public Object next() {
         this.checkModCount();
         this.nextNotInSkipMe(this.cursor + 1);
         if (this.nextCursor < MinMaxPriorityQueue.this.size()) {
            this.cursor = this.nextCursor;
            this.canRemove = true;
            return MinMaxPriorityQueue.this.elementData(this.cursor);
         } else {
            if (this.forgetMeNot != null) {
               this.cursor = MinMaxPriorityQueue.this.size();
               this.lastFromForgetMeNot = this.forgetMeNot.poll();
               if (this.lastFromForgetMeNot != null) {
                  this.canRemove = true;
                  return this.lastFromForgetMeNot;
               }
            }

            throw new NoSuchElementException("iterator moved past last element in queue.");
         }
      }

      public void remove() {
         CollectPreconditions.checkRemove(this.canRemove);
         this.checkModCount();
         this.canRemove = false;
         ++this.expectedModCount;
         if (this.cursor < MinMaxPriorityQueue.this.size()) {
            MoveDesc moved = MinMaxPriorityQueue.this.removeAt(this.cursor);
            if (moved != null) {
               if (this.forgetMeNot == null) {
                  this.forgetMeNot = new ArrayDeque();
                  this.skipMe = new ArrayList(3);
               }

               if (!this.foundAndRemovedExactReference(this.skipMe, moved.toTrickle)) {
                  this.forgetMeNot.add(moved.toTrickle);
               }

               if (!this.foundAndRemovedExactReference(this.forgetMeNot, moved.replaced)) {
                  this.skipMe.add(moved.replaced);
               }
            }

            --this.cursor;
            --this.nextCursor;
         } else {
            Preconditions.checkState(this.removeExact(this.lastFromForgetMeNot));
            this.lastFromForgetMeNot = null;
         }

      }

      private boolean foundAndRemovedExactReference(Iterable elements, Object target) {
         Iterator it = elements.iterator();

         Object element;
         do {
            if (!it.hasNext()) {
               return false;
            }

            element = it.next();
         } while(element != target);

         it.remove();
         return true;
      }

      private boolean removeExact(Object target) {
         for(int i = 0; i < MinMaxPriorityQueue.this.size; ++i) {
            if (MinMaxPriorityQueue.this.queue[i] == target) {
               MinMaxPriorityQueue.this.removeAt(i);
               return true;
            }
         }

         return false;
      }

      private void checkModCount() {
         if (MinMaxPriorityQueue.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      private void nextNotInSkipMe(int c) {
         if (this.nextCursor < c) {
            if (this.skipMe != null) {
               while(c < MinMaxPriorityQueue.this.size() && this.foundAndRemovedExactReference(this.skipMe, MinMaxPriorityQueue.this.elementData(c))) {
                  ++c;
               }
            }

            this.nextCursor = c;
         }

      }

      // $FF: synthetic method
      QueueIterator(Object x1) {
         this();
      }
   }

   private class Heap {
      final Ordering ordering;
      @Weak
      Heap otherHeap;

      Heap(Ordering ordering) {
         this.ordering = ordering;
      }

      int compareElements(int a, int b) {
         return this.ordering.compare(MinMaxPriorityQueue.this.elementData(a), MinMaxPriorityQueue.this.elementData(b));
      }

      MoveDesc tryCrossOverAndBubbleUp(int removeIndex, int vacated, Object toTrickle) {
         int crossOver = this.crossOver(vacated, toTrickle);
         if (crossOver == vacated) {
            return null;
         } else {
            Object parent;
            if (crossOver < removeIndex) {
               parent = MinMaxPriorityQueue.this.elementData(removeIndex);
            } else {
               parent = MinMaxPriorityQueue.this.elementData(this.getParentIndex(removeIndex));
            }

            return this.otherHeap.bubbleUpAlternatingLevels(crossOver, toTrickle) < removeIndex ? new MoveDesc(toTrickle, parent) : null;
         }
      }

      void bubbleUp(int index, Object x) {
         int crossOver = this.crossOverUp(index, x);
         Heap heap;
         if (crossOver == index) {
            heap = this;
         } else {
            index = crossOver;
            heap = this.otherHeap;
         }

         heap.bubbleUpAlternatingLevels(index, x);
      }

      @CanIgnoreReturnValue
      int bubbleUpAlternatingLevels(int index, Object x) {
         while(true) {
            if (index > 2) {
               int grandParentIndex = this.getGrandparentIndex(index);
               Object e = MinMaxPriorityQueue.this.elementData(grandParentIndex);
               if (this.ordering.compare(e, x) > 0) {
                  MinMaxPriorityQueue.this.queue[index] = e;
                  index = grandParentIndex;
                  continue;
               }
            }

            MinMaxPriorityQueue.this.queue[index] = x;
            return index;
         }
      }

      int findMin(int index, int len) {
         if (index >= MinMaxPriorityQueue.this.size) {
            return -1;
         } else {
            Preconditions.checkState(index > 0);
            int limit = Math.min(index, MinMaxPriorityQueue.this.size - len) + len;
            int minIndex = index;

            for(int i = index + 1; i < limit; ++i) {
               if (this.compareElements(i, minIndex) < 0) {
                  minIndex = i;
               }
            }

            return minIndex;
         }
      }

      int findMinChild(int index) {
         return this.findMin(this.getLeftChildIndex(index), 2);
      }

      int findMinGrandChild(int index) {
         int leftChildIndex = this.getLeftChildIndex(index);
         return leftChildIndex < 0 ? -1 : this.findMin(this.getLeftChildIndex(leftChildIndex), 4);
      }

      int crossOverUp(int index, Object x) {
         if (index == 0) {
            MinMaxPriorityQueue.this.queue[0] = x;
            return 0;
         } else {
            int parentIndex = this.getParentIndex(index);
            Object parentElement = MinMaxPriorityQueue.this.elementData(parentIndex);
            if (parentIndex != 0) {
               int grandparentIndex = this.getParentIndex(parentIndex);
               int uncleIndex = this.getRightChildIndex(grandparentIndex);
               if (uncleIndex != parentIndex && this.getLeftChildIndex(uncleIndex) >= MinMaxPriorityQueue.this.size) {
                  Object uncleElement = MinMaxPriorityQueue.this.elementData(uncleIndex);
                  if (this.ordering.compare(uncleElement, parentElement) < 0) {
                     parentIndex = uncleIndex;
                     parentElement = uncleElement;
                  }
               }
            }

            if (this.ordering.compare(parentElement, x) < 0) {
               MinMaxPriorityQueue.this.queue[index] = parentElement;
               MinMaxPriorityQueue.this.queue[parentIndex] = x;
               return parentIndex;
            } else {
               MinMaxPriorityQueue.this.queue[index] = x;
               return index;
            }
         }
      }

      int swapWithConceptuallyLastElement(Object actualLastElement) {
         int parentIndex = this.getParentIndex(MinMaxPriorityQueue.this.size);
         if (parentIndex != 0) {
            int grandparentIndex = this.getParentIndex(parentIndex);
            int uncleIndex = this.getRightChildIndex(grandparentIndex);
            if (uncleIndex != parentIndex && this.getLeftChildIndex(uncleIndex) >= MinMaxPriorityQueue.this.size) {
               Object uncleElement = MinMaxPriorityQueue.this.elementData(uncleIndex);
               if (this.ordering.compare(uncleElement, actualLastElement) < 0) {
                  MinMaxPriorityQueue.this.queue[uncleIndex] = actualLastElement;
                  MinMaxPriorityQueue.this.queue[MinMaxPriorityQueue.this.size] = uncleElement;
                  return uncleIndex;
               }
            }
         }

         return MinMaxPriorityQueue.this.size;
      }

      int crossOver(int index, Object x) {
         int minChildIndex = this.findMinChild(index);
         if (minChildIndex > 0 && this.ordering.compare(MinMaxPriorityQueue.this.elementData(minChildIndex), x) < 0) {
            MinMaxPriorityQueue.this.queue[index] = MinMaxPriorityQueue.this.elementData(minChildIndex);
            MinMaxPriorityQueue.this.queue[minChildIndex] = x;
            return minChildIndex;
         } else {
            return this.crossOverUp(index, x);
         }
      }

      int fillHoleAt(int index) {
         int minGrandchildIndex;
         while((minGrandchildIndex = this.findMinGrandChild(index)) > 0) {
            MinMaxPriorityQueue.this.queue[index] = MinMaxPriorityQueue.this.elementData(minGrandchildIndex);
            index = minGrandchildIndex;
         }

         return index;
      }

      private boolean verifyIndex(int i) {
         if (this.getLeftChildIndex(i) < MinMaxPriorityQueue.this.size && this.compareElements(i, this.getLeftChildIndex(i)) > 0) {
            return false;
         } else if (this.getRightChildIndex(i) < MinMaxPriorityQueue.this.size && this.compareElements(i, this.getRightChildIndex(i)) > 0) {
            return false;
         } else if (i > 0 && this.compareElements(i, this.getParentIndex(i)) > 0) {
            return false;
         } else {
            return i <= 2 || this.compareElements(this.getGrandparentIndex(i), i) <= 0;
         }
      }

      private int getLeftChildIndex(int i) {
         return i * 2 + 1;
      }

      private int getRightChildIndex(int i) {
         return i * 2 + 2;
      }

      private int getParentIndex(int i) {
         return (i - 1) / 2;
      }

      private int getGrandparentIndex(int i) {
         return this.getParentIndex(this.getParentIndex(i));
      }
   }

   static class MoveDesc {
      final Object toTrickle;
      final Object replaced;

      MoveDesc(Object toTrickle, Object replaced) {
         this.toTrickle = toTrickle;
         this.replaced = replaced;
      }
   }

   @Beta
   public static final class Builder {
      private static final int UNSET_EXPECTED_SIZE = -1;
      private final Comparator comparator;
      private int expectedSize;
      private int maximumSize;

      private Builder(Comparator comparator) {
         this.expectedSize = -1;
         this.maximumSize = Integer.MAX_VALUE;
         this.comparator = (Comparator)Preconditions.checkNotNull(comparator);
      }

      @CanIgnoreReturnValue
      public Builder expectedSize(int expectedSize) {
         Preconditions.checkArgument(expectedSize >= 0);
         this.expectedSize = expectedSize;
         return this;
      }

      @CanIgnoreReturnValue
      public Builder maximumSize(int maximumSize) {
         Preconditions.checkArgument(maximumSize > 0);
         this.maximumSize = maximumSize;
         return this;
      }

      public MinMaxPriorityQueue create() {
         return this.create(Collections.emptySet());
      }

      public MinMaxPriorityQueue create(Iterable initialContents) {
         MinMaxPriorityQueue queue = new MinMaxPriorityQueue(this, MinMaxPriorityQueue.initialQueueSize(this.expectedSize, this.maximumSize, initialContents));
         Iterator var3 = initialContents.iterator();

         while(var3.hasNext()) {
            Object element = var3.next();
            queue.offer(element);
         }

         return queue;
      }

      private Ordering ordering() {
         return Ordering.from(this.comparator);
      }

      // $FF: synthetic method
      Builder(Comparator x0, Object x1) {
         this(x0);
      }
   }
}
