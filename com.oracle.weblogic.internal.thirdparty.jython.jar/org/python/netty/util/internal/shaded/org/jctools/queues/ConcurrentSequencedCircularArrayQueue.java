package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.JvmInfo;
import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

public abstract class ConcurrentSequencedCircularArrayQueue extends ConcurrentCircularArrayQueue {
   private static final long ARRAY_BASE;
   private static final int ELEMENT_SHIFT;
   protected static final int SEQ_BUFFER_PAD;
   protected final long[] sequenceBuffer;

   public ConcurrentSequencedCircularArrayQueue(int capacity) {
      super(capacity);
      int actualCapacity = (int)(this.mask + 1L);
      this.sequenceBuffer = new long[actualCapacity + SEQ_BUFFER_PAD * 2];

      for(long i = 0L; i < (long)actualCapacity; ++i) {
         this.soSequence(this.sequenceBuffer, this.calcSequenceOffset(i), i);
      }

   }

   protected final long calcSequenceOffset(long index) {
      return calcSequenceOffset(index, this.mask);
   }

   protected static long calcSequenceOffset(long index, long mask) {
      return ARRAY_BASE + ((index & mask) << ELEMENT_SHIFT);
   }

   protected final void soSequence(long[] buffer, long offset, long e) {
      UnsafeAccess.UNSAFE.putOrderedLong(buffer, offset, e);
   }

   protected final long lvSequence(long[] buffer, long offset) {
      return UnsafeAccess.UNSAFE.getLongVolatile(buffer, offset);
   }

   static {
      int scale = UnsafeAccess.UNSAFE.arrayIndexScale(long[].class);
      if (8 == scale) {
         ELEMENT_SHIFT = 3;
         SEQ_BUFFER_PAD = JvmInfo.CACHE_LINE_SIZE * 2 / scale;
         ARRAY_BASE = (long)(UnsafeAccess.UNSAFE.arrayBaseOffset(long[].class) + SEQ_BUFFER_PAD * scale);
      } else {
         throw new IllegalStateException("Unexpected long[] element size");
      }
   }
}
