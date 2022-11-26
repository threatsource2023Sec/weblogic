package org.python.core.io;

import java.nio.ByteBuffer;

public abstract class BufferedIOBase extends IOBase {
   public ByteBuffer read(int size) {
      if (size < 0) {
         return this.readall();
      } else {
         ByteBuffer bytes = ByteBuffer.allocate(size);
         this.readinto(bytes, false);
         bytes.flip();
         return bytes;
      }
   }

   public ByteBuffer readall() {
      this.unsupported("readall");
      return null;
   }

   public int readinto(ByteBuffer bytes) {
      this.unsupported("readinto");
      return -1;
   }

   protected int readinto(ByteBuffer bytes, boolean buffered) {
      this.unsupported("readinto");
      return -1;
   }

   public int write(ByteBuffer bytes) {
      this.unsupported("write");
      return -1;
   }

   public ByteBuffer peek(int size) {
      this.unsupported("peek");
      return null;
   }

   public int read1(ByteBuffer bytes) {
      this.unsupported("read1");
      return -1;
   }

   public boolean buffered() {
      this.unsupported("buffered");
      return false;
   }

   public void clear() {
      this.unsupported("clear");
   }
}
