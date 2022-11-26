package org.python.core.buffer;

import org.python.core.BufferProtocol;
import org.python.core.PyBuffer;
import org.python.core.PyException;

public class Strided1DWritableBuffer extends Strided1DBuffer {
   public Strided1DWritableBuffer(int flags, BufferProtocol obj, byte[] storage, int index0, int count, int stride) throws ArrayIndexOutOfBoundsException, NullPointerException, PyException {
      super(obj, storage, index0, count, stride);
      this.addFeatureFlags(1);
      this.checkRequestFlags(flags);
   }

   public final boolean isReadonly() {
      return false;
   }

   protected final void checkWritable() {
   }

   protected void storeAtImpl(byte value, int byteIndex) {
      this.storage[byteIndex] = value;
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

   static class SlicedView extends Strided1DWritableBuffer {
      PyBuffer root;

      public SlicedView(PyBuffer root, int flags, byte[] storage, int index0, int count, int stride) throws PyException {
         super(flags, root.getObj(), storage, index0, count, stride);
         this.root = root.getBuffer(285);
      }

      protected PyBuffer getRoot() {
         return this.root;
      }
   }
}
