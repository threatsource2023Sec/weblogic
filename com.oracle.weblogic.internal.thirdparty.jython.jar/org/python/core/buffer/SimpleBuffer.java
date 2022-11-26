package org.python.core.buffer;

import org.python.core.BufferProtocol;
import org.python.core.PyBuffer;
import org.python.core.PyException;
import org.python.core.util.StringUtil;

public class SimpleBuffer extends BaseArrayBuffer {
   protected SimpleBuffer(BufferProtocol obj, byte[] storage, int index0, int size) throws PyException, ArrayIndexOutOfBoundsException {
      super(storage, 224, index0, size, 1);
      this.obj = obj;
   }

   public SimpleBuffer(int flags, BufferProtocol obj, byte[] storage, int index0, int size) throws PyException, ArrayIndexOutOfBoundsException, NullPointerException {
      this(obj, storage, index0, size);
      this.checkRequestFlags(flags);
      if ((index0 | size | storage.length - (index0 + size)) < 0) {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   protected SimpleBuffer(BufferProtocol obj, byte[] storage) throws NullPointerException {
      this(obj, storage, 0, storage.length);
   }

   public SimpleBuffer(int flags, BufferProtocol obj, byte[] storage) throws PyException, NullPointerException {
      this(obj, storage);
      this.checkRequestFlags(flags);
   }

   public int getLen() {
      return this.shape[0];
   }

   public int byteIndex(int index) throws IndexOutOfBoundsException {
      if (index >= 0 && index < this.shape[0]) {
         return this.index0 + index;
      } else {
         throw new IndexOutOfBoundsException();
      }
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
         return new Strided1DBuffer.SlicedView(this.getRoot(), flags, this.storage, compIndex0, count, stride);
      } else {
         return this.getBufferSlice(flags, start, count);
      }
   }

   public PyBuffer.Pointer getPointer(int index) throws IndexOutOfBoundsException {
      return new PyBuffer.Pointer(this.storage, this.index0 + index);
   }

   public PyBuffer.Pointer getPointer(int... indices) throws IndexOutOfBoundsException {
      this.checkDimension(indices.length);
      return this.getPointer(indices[0]);
   }

   public String toString() {
      return StringUtil.fromBytes(this.storage, this.index0, this.shape[0]);
   }

   static class SimpleView extends SimpleBuffer {
      PyBuffer root;

      public SimpleView(PyBuffer root, int flags, byte[] storage, int offset, int size) {
         super(flags, root.getObj(), storage, offset, size);
         this.root = root.getBuffer(284);
      }

      protected PyBuffer getRoot() {
         return this.root;
      }
   }
}
