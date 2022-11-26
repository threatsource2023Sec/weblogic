package org.python.core.buffer;

import java.nio.ByteBuffer;
import org.python.core.BufferProtocol;
import org.python.core.PyBuffer;
import org.python.core.PyException;

public class Strided1DNIOBuffer extends BaseNIOBuffer {
   protected int stride;

   protected Strided1DNIOBuffer(BufferProtocol obj, ByteBuffer storage, int index0, int count, int stride) throws ArrayIndexOutOfBoundsException, NullPointerException {
      super(storage, 24, index0, count, stride);
      this.obj = obj;
      this.stride = stride;
      if (count == 0) {
         this.addFeatureFlags(224);
      } else {
         int lo;
         int hi;
         if (stride == 1) {
            lo = index0;
            hi = index0 + count;
            this.addFeatureFlags(224);
         } else if (stride > 1) {
            lo = index0;
            hi = index0 + (count - 1) * stride + 1;
         } else {
            hi = index0 + 1;
            lo = index0 + (count - 1) * stride;
         }

         int cap = storage.capacity();
         if ((count | lo | cap - lo | hi | cap - hi) < 0) {
            throw new ArrayIndexOutOfBoundsException();
         }
      }

      if (!storage.isReadOnly()) {
         this.addFeatureFlags(1);
      }

      if (storage.hasArray()) {
         this.addFeatureFlags(268435456);
      }

   }

   public Strided1DNIOBuffer(int flags, BufferProtocol obj, ByteBuffer storage, int index0, int count, int stride) throws ArrayIndexOutOfBoundsException, NullPointerException, PyException {
      this(obj, storage.duplicate(), index0, count, stride);
      this.checkRequestFlags(flags);
   }

   public final int byteIndex(int index) throws IndexOutOfBoundsException {
      return this.index0 + index * this.stride;
   }

   public PyBuffer getBufferSlice(int flags, int start, int count, int stride) {
      if (count > 0) {
         int compStride = this.stride * stride;
         int compIndex0 = this.index0 + start * this.stride;
         return new SlicedView(this.getRoot(), flags, this.storage, compIndex0, count, compStride);
      } else {
         return new ZeroByteBuffer.View(this.getRoot(), flags);
      }
   }

   static class SlicedView extends Strided1DNIOBuffer {
      PyBuffer root;

      public SlicedView(PyBuffer root, int flags, ByteBuffer storage, int index0, int count, int stride) throws PyException {
         super(flags, root.getObj(), storage, index0, count, stride);
         this.root = root.getBuffer(284);
      }

      protected PyBuffer getRoot() {
         return this.root;
      }
   }
}
