package com.bea.common.security.internal.utils.collections;

import com.bea.common.security.internal.service.ServiceLogger;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class CircularQueue extends AbstractCollection {
   private static final int MAX_CAPACITY = 1073741824;
   private static final int DEFAULT_CAPACITY = 256;
   private int size;
   private int producerIndex;
   private int consumerIndex;
   private int capacity;
   private int maxCapacity;
   private int bitmask;
   private Object[] q;

   public CircularQueue() {
      this(256);
   }

   public CircularQueue(int c) {
      this(c, 1073741824);
   }

   public CircularQueue(int c, int mc) {
      this.size = 0;
      this.producerIndex = 0;
      this.consumerIndex = 0;
      if (c > mc) {
         throw new IllegalArgumentException(ServiceLogger.getCapacityGreaterThanMax(c, mc));
      } else if (mc > 1073741824) {
         throw new IllegalArgumentException(ServiceLogger.getMaxCapacityTooLarge(mc, 1073741824));
      } else {
         for(this.capacity = 1; this.capacity < c; this.capacity <<= 1) {
         }

         for(this.maxCapacity = 1; this.maxCapacity < mc; this.maxCapacity <<= 1) {
         }

         this.bitmask = this.capacity - 1;
         this.q = new Object[this.capacity];
      }
   }

   private CircularQueue(CircularQueue oldQueue) {
      this.size = 0;
      this.producerIndex = 0;
      this.consumerIndex = 0;
      this.size = oldQueue.size;
      this.producerIndex = oldQueue.producerIndex;
      this.consumerIndex = oldQueue.consumerIndex;
      this.capacity = oldQueue.capacity;
      this.maxCapacity = oldQueue.maxCapacity;
      this.bitmask = oldQueue.bitmask;
      this.q = new Object[oldQueue.q.length];
      System.arraycopy(oldQueue.q, 0, this.q, 0, this.q.length);
   }

   private boolean expandQueue() {
      if (this.capacity == this.maxCapacity) {
         return false;
      } else {
         int old_capacity = this.capacity;
         Object[] old_q = this.q;
         this.capacity += this.capacity;
         this.bitmask = this.capacity - 1;
         this.q = new Object[this.capacity];
         System.arraycopy(old_q, this.consumerIndex, this.q, 0, old_capacity - this.consumerIndex);
         if (this.consumerIndex != 0) {
            System.arraycopy(old_q, 0, this.q, old_capacity - this.consumerIndex, this.consumerIndex);
         }

         this.consumerIndex = 0;
         this.producerIndex = this.size;
         return true;
      }
   }

   public boolean add(Object obj) {
      if (this.size == this.capacity && !this.expandQueue()) {
         return false;
      } else {
         ++this.size;
         this.q[this.producerIndex] = obj;
         this.producerIndex = this.producerIndex + 1 & this.bitmask;
         return true;
      }
   }

   public Object remove() {
      if (this.size == 0) {
         return null;
      } else {
         --this.size;
         Object obj = this.q[this.consumerIndex];
         this.q[this.consumerIndex] = null;
         this.consumerIndex = this.consumerIndex + 1 & this.bitmask;
         return obj;
      }
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public int size() {
      return this.size;
   }

   public int capacity() {
      return this.capacity;
   }

   public Object peek() {
      return this.size == 0 ? null : this.q[this.consumerIndex];
   }

   public void clear() {
      Arrays.fill(this.q, (Object)null);
      this.size = 0;
      this.producerIndex = 0;
      this.consumerIndex = 0;
   }

   public Object clone() {
      return new CircularQueue(this);
   }

   public String toString() {
      StringBuffer s = new StringBuffer(super.toString() + " - capacity: '" + this.capacity() + "' size: '" + this.size() + "'");
      if (this.size > 0) {
         s.append(" elements:");

         for(int i = 0; i < this.size; ++i) {
            s.append('\n');
            s.append('\t');
            s.append(this.q[this.consumerIndex + i & this.bitmask].toString());
         }
      }

      return s.toString();
   }

   public Iterator iterator() {
      return new Iterator() {
         private final int ci;
         private final int pi;
         private int s;
         private int i;

         {
            this.ci = CircularQueue.this.consumerIndex;
            this.pi = CircularQueue.this.producerIndex;
            this.s = CircularQueue.this.size;
            this.i = this.ci;
         }

         public boolean hasNext() {
            this.checkForModification();
            return this.s > 0;
         }

         public Object next() {
            this.checkForModification();
            if (this.s == 0) {
               throw new NoSuchElementException();
            } else {
               --this.s;
               Object r = CircularQueue.this.q[this.i];
               this.i = this.i + 1 & CircularQueue.this.bitmask;
               return r;
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }

         private void checkForModification() {
            if (this.ci != CircularQueue.this.consumerIndex) {
               throw new ConcurrentModificationException();
            } else if (this.pi != CircularQueue.this.producerIndex) {
               throw new ConcurrentModificationException();
            }
         }
      };
   }
}
