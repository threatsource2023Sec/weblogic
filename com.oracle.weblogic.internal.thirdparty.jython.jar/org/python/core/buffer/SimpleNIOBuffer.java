package org.python.core.buffer;

import java.nio.ByteBuffer;
import org.python.core.BufferProtocol;
import org.python.core.PyBuffer;
import org.python.core.PyException;

public class SimpleNIOBuffer extends BaseNIOBuffer {
   protected SimpleNIOBuffer(BufferProtocol obj, ByteBuffer storage, int index0, int size) throws PyException, ArrayIndexOutOfBoundsException {
      super(storage, 224, index0, size, 1);
      this.obj = obj;
      if ((index0 | size | storage.capacity() - (index0 + size)) < 0) {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public SimpleNIOBuffer(int flags, BufferProtocol obj, ByteBuffer storage, int index0, int size) throws PyException, ArrayIndexOutOfBoundsException, NullPointerException {
      this(obj, storage.duplicate(), index0, size);
      this.checkRequestFlags(flags);
   }

   protected SimpleNIOBuffer(BufferProtocol obj, ByteBuffer storage) throws NullPointerException {
      this(obj, storage, 0, storage.capacity());
   }

   public SimpleNIOBuffer(int flags, BufferProtocol obj, ByteBuffer storage) throws PyException, NullPointerException {
      this(obj, storage.duplicate());
      this.checkRequestFlags(flags);
   }

   public int getLen() {
      return this.shape[0];
   }

   public final int byteIndex(int index) throws IndexOutOfBoundsException {
      return this.index0 + index;
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
         return new Strided1DNIOBuffer.SlicedView(this.getRoot(), flags, this.storage, compIndex0, count, stride);
      } else {
         return this.getBufferSlice(flags, start, count);
      }
   }

   static class SimpleView extends SimpleNIOBuffer {
      PyBuffer root;

      public SimpleView(PyBuffer root, int flags, ByteBuffer storage, int offset, int count) {
         super(flags, root.getObj(), storage, offset, count);
         this.root = root.getBuffer(284);
      }

      protected PyBuffer getRoot() {
         return this.root;
      }
   }
}
