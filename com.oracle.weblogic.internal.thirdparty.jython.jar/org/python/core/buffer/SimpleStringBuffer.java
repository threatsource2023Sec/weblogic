package org.python.core.buffer;

import java.nio.ByteBuffer;
import org.python.core.BufferProtocol;
import org.python.core.PyBuffer;
import org.python.core.util.StringUtil;

public class SimpleStringBuffer extends SimpleBuffer {
   private String bufString;

   public SimpleStringBuffer(int flags, BufferProtocol obj, String bufString) {
      super(obj, (byte[])null, 0, bufString.length());
      this.bufString = bufString;
      this.checkRequestFlags(flags);
   }

   public int getLen() {
      return this.bufString.length();
   }

   public final byte byteAtImpl(int index) {
      return (byte)this.bufString.charAt(index);
   }

   public final int byteIndex(int index) {
      return index;
   }

   public void copyTo(int srcIndex, byte[] dest, int destPos, int count) throws IndexOutOfBoundsException {
      int endIndex = srcIndex + count;
      int p = destPos;

      for(int i = srcIndex; i < endIndex; ++i) {
         dest[p++] = (byte)this.bufString.charAt(i);
      }

   }

   public PyBuffer getBufferSlice(int flags, int start, int count) {
      return (PyBuffer)(count > 0 ? new SimpleStringView(this.getRoot(), flags, this.bufString.substring(start, start + count)) : new ZeroByteBuffer.View(this.getRoot(), flags));
   }

   public PyBuffer getBufferSlice(int flags, int start, int count, int stride) {
      if (stride == 1) {
         return this.getBufferSlice(flags, start, count);
      } else {
         this.ensureHaveBytes();
         return super.getBufferSlice(flags, start, count, stride);
      }
   }

   protected ByteBuffer getNIOByteBufferImpl() {
      this.ensureHaveBytes();
      ByteBuffer b = ByteBuffer.wrap(this.storage);
      return b.asReadOnlyBuffer();
   }

   private void ensureHaveBytes() {
      if (this.storage == null) {
         this.storage = StringUtil.toBytes(this.bufString);
      }

   }

   public PyBuffer.Pointer getBuf() {
      this.ensureHaveBytes();
      return super.getBuf();
   }

   public PyBuffer.Pointer getPointer(int index) {
      this.ensureHaveBytes();
      return super.getPointer(index);
   }

   public PyBuffer.Pointer getPointer(int... indices) {
      this.ensureHaveBytes();
      return super.getPointer(indices);
   }

   public String toString() {
      return this.bufString;
   }

   static class SimpleStringView extends SimpleStringBuffer {
      PyBuffer root;

      public SimpleStringView(PyBuffer root, int flags, String bufString) {
         super(flags, root.getObj(), bufString);
         this.root = root.getBuffer(284);
      }

      protected PyBuffer getRoot() {
         return this.root;
      }
   }
}
