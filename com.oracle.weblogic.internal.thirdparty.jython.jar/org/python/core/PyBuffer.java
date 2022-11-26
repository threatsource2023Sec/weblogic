package org.python.core;

import java.nio.ByteBuffer;

public interface PyBuffer extends PyBUF, BufferProtocol, AutoCloseable {
   BufferProtocol getObj();

   byte byteAt(int var1) throws IndexOutOfBoundsException;

   int intAt(int var1) throws IndexOutOfBoundsException;

   void storeAt(byte var1, int var2) throws IndexOutOfBoundsException;

   byte byteAt(int... var1) throws IndexOutOfBoundsException;

   int intAt(int... var1) throws IndexOutOfBoundsException;

   void storeAt(byte var1, int... var2) throws IndexOutOfBoundsException;

   void copyTo(byte[] var1, int var2) throws IndexOutOfBoundsException, PyException;

   void copyTo(int var1, byte[] var2, int var3, int var4) throws IndexOutOfBoundsException, PyException;

   void copyFrom(byte[] var1, int var2, int var3, int var4) throws IndexOutOfBoundsException, PyException;

   void copyFrom(PyBuffer var1) throws IndexOutOfBoundsException, PyException;

   PyBuffer getBuffer(int var1) throws PyException;

   void release();

   void close();

   boolean isReleased();

   PyBuffer getBufferSlice(int var1, int var2, int var3);

   PyBuffer getBufferSlice(int var1, int var2, int var3, int var4);

   int byteIndex(int var1) throws IndexOutOfBoundsException;

   int byteIndex(int... var1);

   ByteBuffer getNIOByteBuffer();

   boolean hasArray();

   Pointer getBuf();

   Pointer getPointer(int var1);

   Pointer getPointer(int... var1);

   String getFormat();

   String toString();

   /** @deprecated */
   @Deprecated
   public static class Pointer {
      public byte[] storage;
      public int offset;

      public Pointer(byte[] storage, int offset) {
         this.storage = storage;
         this.offset = offset;
      }
   }
}
