package org.python.core.io;

import java.nio.ByteBuffer;

public class BufferedReader extends BufferedIOMixin {
   protected ByteBuffer buffer;

   public BufferedReader(RawIOBase rawIO, int bufferSize) {
      super(rawIO, bufferSize);
      rawIO.checkReadable();
      this.buffer = ByteBuffer.allocate(this.bufferSize);
      this.clear();
   }

   public int readinto(ByteBuffer bytes) {
      return this.readinto(bytes, true);
   }

   protected int readinto(ByteBuffer bytes, boolean buffered) {
      int size = bytes.remaining();
      if (size == 0) {
         return 0;
      } else if (this.buffer.remaining() >= size) {
         int bufferLimit = this.buffer.limit();
         this.buffer.limit(this.buffer.position() + size);
         bytes.put(this.buffer);
         this.buffer.limit(bufferLimit);
         return size;
      } else {
         bytes.put(this.buffer);
         long read;
         if (buffered) {
            this.buffer.clear();
            read = this.rawIO.readinto(new ByteBuffer[]{bytes, this.buffer});
            read -= (long)this.buffer.flip().limit();
         } else {
            this.clear();
            read = (long)this.rawIO.readinto(bytes);
         }

         return (int)read;
      }
   }

   public ByteBuffer readall() {
      ByteBuffer remaining = this.rawIO.readall();
      if (!this.buffer.hasRemaining()) {
         return remaining;
      } else {
         ByteBuffer all = ByteBuffer.allocate(this.buffer.remaining() + remaining.remaining());
         all.put(this.buffer);
         this.clear();
         all.put(remaining);
         all.flip();
         return all;
      }
   }

   public ByteBuffer peek(int size) {
      if (this.buffer.remaining() < Math.min(size, this.bufferSize)) {
         if (this.buffer.position() == 0) {
            this.buffer.limit(this.buffer.capacity());
         } else {
            this.buffer.compact();
         }

         this.rawIO.readinto(this.buffer);
         this.buffer.flip();
      }

      return this.buffer;
   }

   public int read1(ByteBuffer bytes) {
      int size = bytes.remaining();
      if (size == 0) {
         return 0;
      } else {
         if (this.bufferSize > 0) {
            this.peek(1);
            int bufferedSize = this.buffer.remaining();
            if (bufferedSize < size) {
               bytes.limit(bytes.position() + bufferedSize);
            }
         }

         return this.readinto(bytes);
      }
   }

   public long tell() {
      return this.rawIO.tell() - (long)this.buffer.remaining();
   }

   public long seek(long pos, int whence) {
      if (whence == 1) {
         pos -= (long)this.buffer.remaining();
      }

      pos = this.rawIO.seek(pos, whence);
      this.clear();
      return pos;
   }

   public boolean buffered() {
      return this.buffer.hasRemaining();
   }

   public void clear() {
      this.buffer.clear().limit(0);
   }

   public int write(ByteBuffer bytes) {
      this.checkClosed();
      this.checkWritable();
      return -1;
   }

   public boolean writable() {
      return false;
   }
}
