package org.python.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.python.netty.util.internal.shaded.org.jctools.util.Pow2;

abstract class AtomicReferenceArrayQueue extends AbstractQueue {
   protected final AtomicReferenceArray buffer;
   protected final int mask;

   public AtomicReferenceArrayQueue(int capacity) {
      int actualCapacity = Pow2.roundToPowerOfTwo(capacity);
      this.mask = actualCapacity - 1;
      this.buffer = new AtomicReferenceArray(actualCapacity);
   }

   public Iterator iterator() {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      return this.getClass().getName();
   }

   public void clear() {
      while(this.poll() != null || !this.isEmpty()) {
      }

   }

   protected final int calcElementOffset(long index, int mask) {
      return (int)index & mask;
   }

   protected final int calcElementOffset(long index) {
      return (int)index & this.mask;
   }

   protected final Object lvElement(AtomicReferenceArray buffer, int offset) {
      return buffer.get(offset);
   }

   protected final Object lpElement(AtomicReferenceArray buffer, int offset) {
      return buffer.get(offset);
   }

   protected final Object lpElement(int offset) {
      return this.buffer.get(offset);
   }

   protected final void spElement(AtomicReferenceArray buffer, int offset, Object value) {
      buffer.lazySet(offset, value);
   }

   protected final void spElement(int offset, Object value) {
      this.buffer.lazySet(offset, value);
   }

   protected final void soElement(AtomicReferenceArray buffer, int offset, Object value) {
      buffer.lazySet(offset, value);
   }

   protected final void soElement(int offset, Object value) {
      this.buffer.lazySet(offset, value);
   }

   protected final void svElement(AtomicReferenceArray buffer, int offset, Object value) {
      buffer.set(offset, value);
   }

   protected final Object lvElement(int offset) {
      return this.lvElement(this.buffer, offset);
   }
}
