package org.python.core.buffer;

import org.python.core.BufferProtocol;
import org.python.core.PyBuffer;
import org.python.core.PyException;

public class Strided1DBuffer extends BaseArrayBuffer {
   protected int stride;

   protected Strided1DBuffer(BufferProtocol obj, byte[] storage, int index0, int count, int stride) throws ArrayIndexOutOfBoundsException, NullPointerException {
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

         if ((count | lo | storage.length - lo | hi | storage.length - hi) < 0) {
            throw new ArrayIndexOutOfBoundsException();
         }
      }

   }

   public Strided1DBuffer(int flags, BufferProtocol obj, byte[] storage, int index0, int count, int stride) throws ArrayIndexOutOfBoundsException, NullPointerException, PyException {
      this(obj, storage, index0, count, stride);
      this.checkRequestFlags(flags);
   }

   public boolean isReadonly() {
      return true;
   }

   public final int byteIndex(int index) throws IndexOutOfBoundsException {
      if (index >= 0 && index < this.shape[0]) {
         return this.index0 + index * this.stride;
      } else {
         throw new IndexOutOfBoundsException();
      }
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

   public PyBuffer.Pointer getPointer(int index) {
      return new PyBuffer.Pointer(this.storage, this.index0 + index * this.stride);
   }

   public PyBuffer.Pointer getPointer(int... indices) {
      this.checkDimension(indices.length);
      return this.getPointer(indices[0]);
   }

   static class SlicedView extends Strided1DBuffer {
      PyBuffer root;

      public SlicedView(PyBuffer root, int flags, byte[] storage, int index0, int count, int stride) throws PyException {
         super(flags, root.getObj(), storage, index0, count, stride);
         this.root = root.getBuffer(284);
      }

      protected PyBuffer getRoot() {
         return this.root;
      }
   }
}
