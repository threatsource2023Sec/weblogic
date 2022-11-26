package org.python.netty.util.internal;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class DefaultPriorityQueue extends AbstractQueue implements PriorityQueue {
   private static final PriorityQueueNode[] EMPTY_ARRAY = new PriorityQueueNode[0];
   private final Comparator comparator;
   private PriorityQueueNode[] queue;
   private int size;

   public DefaultPriorityQueue(Comparator comparator, int initialSize) {
      this.comparator = (Comparator)ObjectUtil.checkNotNull(comparator, "comparator");
      this.queue = (PriorityQueueNode[])(initialSize != 0 ? new PriorityQueueNode[initialSize] : EMPTY_ARRAY);
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public boolean contains(Object o) {
      if (!(o instanceof PriorityQueueNode)) {
         return false;
      } else {
         PriorityQueueNode node = (PriorityQueueNode)o;
         return this.contains(node, node.priorityQueueIndex(this));
      }
   }

   public boolean containsTyped(PriorityQueueNode node) {
      return this.contains(node, node.priorityQueueIndex(this));
   }

   public void clear() {
      for(int i = 0; i < this.size; ++i) {
         PriorityQueueNode node = this.queue[i];
         if (node != null) {
            node.priorityQueueIndex(this, -1);
            this.queue[i] = null;
         }
      }

      this.size = 0;
   }

   public boolean offer(PriorityQueueNode e) {
      if (e.priorityQueueIndex(this) != -1) {
         throw new IllegalArgumentException("e.priorityQueueIndex(): " + e.priorityQueueIndex(this) + " (expected: " + -1 + ") + e: " + e);
      } else {
         if (this.size >= this.queue.length) {
            this.queue = (PriorityQueueNode[])Arrays.copyOf(this.queue, this.queue.length + (this.queue.length < 64 ? this.queue.length + 2 : this.queue.length >>> 1));
         }

         this.bubbleUp(this.size++, e);
         return true;
      }
   }

   public PriorityQueueNode poll() {
      if (this.size == 0) {
         return null;
      } else {
         PriorityQueueNode result = this.queue[0];
         result.priorityQueueIndex(this, -1);
         PriorityQueueNode last = this.queue[--this.size];
         this.queue[this.size] = null;
         if (this.size != 0) {
            this.bubbleDown(0, last);
         }

         return result;
      }
   }

   public PriorityQueueNode peek() {
      return this.size == 0 ? null : this.queue[0];
   }

   public boolean remove(Object o) {
      PriorityQueueNode node;
      try {
         node = (PriorityQueueNode)o;
      } catch (ClassCastException var4) {
         return false;
      }

      return this.removeTyped(node);
   }

   public boolean removeTyped(PriorityQueueNode node) {
      int i = node.priorityQueueIndex(this);
      if (!this.contains(node, i)) {
         return false;
      } else {
         node.priorityQueueIndex(this, -1);
         if (--this.size != 0 && this.size != i) {
            PriorityQueueNode moved = this.queue[i] = this.queue[this.size];
            this.queue[this.size] = null;
            if (this.comparator.compare(node, moved) < 0) {
               this.bubbleDown(i, moved);
            } else {
               this.bubbleUp(i, moved);
            }

            return true;
         } else {
            this.queue[i] = null;
            return true;
         }
      }
   }

   public void priorityChanged(PriorityQueueNode node) {
      int i = node.priorityQueueIndex(this);
      if (this.contains(node, i)) {
         if (i == 0) {
            this.bubbleDown(i, node);
         } else {
            int iParent = i - 1 >>> 1;
            PriorityQueueNode parent = this.queue[iParent];
            if (this.comparator.compare(node, parent) < 0) {
               this.bubbleUp(i, node);
            } else {
               this.bubbleDown(i, node);
            }
         }

      }
   }

   public Object[] toArray() {
      return Arrays.copyOf(this.queue, this.size);
   }

   public Object[] toArray(Object[] a) {
      if (a.length < this.size) {
         return (Object[])Arrays.copyOf(this.queue, this.size, a.getClass());
      } else {
         System.arraycopy(this.queue, 0, a, 0, this.size);
         if (a.length > this.size) {
            a[this.size] = null;
         }

         return a;
      }
   }

   public Iterator iterator() {
      return new PriorityQueueIterator();
   }

   private boolean contains(PriorityQueueNode node, int i) {
      return i >= 0 && i < this.size && node.equals(this.queue[i]);
   }

   private void bubbleDown(int k, PriorityQueueNode node) {
      int iChild;
      for(int half = this.size >>> 1; k < half; k = iChild) {
         iChild = (k << 1) + 1;
         PriorityQueueNode child = this.queue[iChild];
         int rightChild = iChild + 1;
         if (rightChild < this.size && this.comparator.compare(child, this.queue[rightChild]) > 0) {
            iChild = rightChild;
            child = this.queue[rightChild];
         }

         if (this.comparator.compare(node, child) <= 0) {
            break;
         }

         this.queue[k] = child;
         child.priorityQueueIndex(this, k);
      }

      this.queue[k] = node;
      node.priorityQueueIndex(this, k);
   }

   private void bubbleUp(int k, PriorityQueueNode node) {
      while(true) {
         if (k > 0) {
            int iParent = k - 1 >>> 1;
            PriorityQueueNode parent = this.queue[iParent];
            if (this.comparator.compare(node, parent) < 0) {
               this.queue[k] = parent;
               parent.priorityQueueIndex(this, k);
               k = iParent;
               continue;
            }
         }

         this.queue[k] = node;
         node.priorityQueueIndex(this, k);
         return;
      }
   }

   private final class PriorityQueueIterator implements Iterator {
      private int index;

      private PriorityQueueIterator() {
      }

      public boolean hasNext() {
         return this.index < DefaultPriorityQueue.this.size;
      }

      public PriorityQueueNode next() {
         if (this.index >= DefaultPriorityQueue.this.size) {
            throw new NoSuchElementException();
         } else {
            return DefaultPriorityQueue.this.queue[this.index++];
         }
      }

      public void remove() {
         throw new UnsupportedOperationException("remove");
      }

      // $FF: synthetic method
      PriorityQueueIterator(Object x1) {
         this();
      }
   }
}
