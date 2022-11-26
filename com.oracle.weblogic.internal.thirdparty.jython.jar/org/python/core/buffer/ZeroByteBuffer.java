package org.python.core.buffer;

import org.python.core.BufferProtocol;
import org.python.core.PyBuffer;
import org.python.core.PyException;

public class ZeroByteBuffer extends BaseArrayBuffer {
   private static final byte[] EMPTY = new byte[0];

   public ZeroByteBuffer(int flags, BufferProtocol obj, boolean readonly, boolean hasArray) throws PyException {
      super(EMPTY, 224 | (readonly ? 0 : 1), 0, 0, 1);
      this.obj = obj;
      if (!hasArray) {
         this.removeFeatureFlags(268435456);
      }

      this.checkRequestFlags(flags);
   }

   public int getLen() {
      return 0;
   }

   public int byteIndex(int index) throws IndexOutOfBoundsException {
      throw new IndexOutOfBoundsException();
   }

   public int byteIndex(int... indices) throws IndexOutOfBoundsException {
      this.checkDimension(indices);
      throw new IndexOutOfBoundsException();
   }

   public void copyTo(byte[] dest, int destPos) throws IndexOutOfBoundsException {
   }

   public void copyTo(int srcIndex, byte[] dest, int destPos, int count) throws IndexOutOfBoundsException, PyException {
   }

   public void copyFrom(byte[] src, int srcPos, int destIndex, int count) throws IndexOutOfBoundsException, PyException {
      if (this.isReadonly()) {
         throw notWritable();
      } else if (count > 0) {
         throw new IndexOutOfBoundsException();
      }
   }

   public void copyFrom(PyBuffer src) throws IndexOutOfBoundsException, PyException {
      if (this.isReadonly()) {
         throw notWritable();
      } else if (src.getLen() > 0) {
         throw new IndexOutOfBoundsException();
      }
   }

   public PyBuffer getBufferSlice(int flags, int start, int count) {
      if (start == 0 && count <= 0) {
         return this.getBuffer(flags);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public PyBuffer getBufferSlice(int flags, int start, int count, int stride) {
      return this.getBufferSlice(flags, start, count);
   }

   public PyBuffer.Pointer getBuf() {
      return new PyBuffer.Pointer(EMPTY, 0);
   }

   public String toString() {
      return "";
   }

   static class View extends ZeroByteBuffer {
      PyBuffer root;

      public View(PyBuffer root, int flags) {
         super(flags, root.getObj(), root.isReadonly(), root.hasArray());
         this.root = root.getBuffer(284);
      }

      protected PyBuffer getRoot() {
         return this.root;
      }
   }
}
