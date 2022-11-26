package org.python.core.buffer;

import org.python.core.BufferProtocol;
import org.python.core.PyBuffer;
import org.python.core.PyException;

public class SimpleWritableBuffer extends SimpleBuffer {
   public SimpleWritableBuffer(int flags, BufferProtocol obj, byte[] storage, int index0, int size) throws PyException, NullPointerException {
      super(obj, storage, index0, size);
      this.addFeatureFlags(1);
      this.checkRequestFlags(flags);
   }

   public SimpleWritableBuffer(int flags, BufferProtocol obj, byte[] storage) throws PyException, NullPointerException {
      this(flags, obj, storage, 0, storage.length);
   }

   public final boolean isReadonly() {
      return false;
   }

   protected final void checkWritable() {
   }

   protected void storeAtImpl(byte value, int byteIndex) {
      this.storage[byteIndex] = value;
   }

   public PyBuffer getBufferSlice(int flags, int start, int count) {
      if (count > 0) {
         int compIndex0 = this.index0 + start;
         return new SimpleView(this.getRoot(), flags, this.storage, compIndex0, count);
      } else {
         return new ZeroByteBuffer.View(this.getRoot(), flags);
      }
   }

   public PyBuffer getBufferSlice(int flags, int start, int count, int stride) {
      if (stride != 1 && count >= 2) {
         int compIndex0 = this.index0 + start;
         return new Strided1DWritableBuffer.SlicedView(this.getRoot(), flags, this.storage, compIndex0, count, stride);
      } else {
         return this.getBufferSlice(flags, start, count);
      }
   }

   static class SimpleView extends SimpleWritableBuffer {
      PyBuffer root;

      public SimpleView(PyBuffer root, int flags, byte[] storage, int index0, int size) {
         super(flags, root.getObj(), storage, index0, size);
         this.root = root.getBuffer(284);
      }

      protected PyBuffer getRoot() {
         return this.root;
      }
   }
}
