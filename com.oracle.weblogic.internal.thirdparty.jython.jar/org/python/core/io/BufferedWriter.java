package org.python.core.io;

import java.nio.ByteBuffer;

public class BufferedWriter extends BufferedIOMixin {
   protected ByteBuffer buffer;

   public BufferedWriter(RawIOBase rawIO, int bufferSize) {
      super(rawIO, bufferSize);
      rawIO.checkWritable();
      this.buffer = ByteBuffer.allocate(this.bufferSize);
   }

   public int write(ByteBuffer bytes) {
      if (this.bufferSize == 0) {
         return this.rawIO.write(bytes);
      } else {
         int bytesSize = bytes.remaining();
         int total = this.buffer.position() + bytesSize;
         if (total < this.bufferSize) {
            this.buffer.put(bytes);
            return bytesSize;
         } else {
            int toBuffer = total % this.bufferSize;
            int bytesToWrite = bytesSize - toBuffer;
            int origBytesLimit = bytes.limit();
            bytes.limit(bytes.position() + bytesToWrite);
            int totalToWrite = total - toBuffer;
            int count = totalToWrite;
            ByteBuffer[] bulk = new ByteBuffer[]{this.buffer, bytes};
            this.buffer.flip();

            while(count > 0) {
               count = (int)((long)count - this.rawIO.write(bulk));
            }

            this.buffer.clear();
            if (toBuffer > 0) {
               bytes.limit(origBytesLimit);
               this.buffer.put(bytes);
            }

            return totalToWrite;
         }
      }
   }

   public void flush() {
      if (this.buffer.position() > 0) {
         this.buffer.flip();

         while(this.buffer.hasRemaining()) {
            this.rawIO.write(this.buffer);
         }

         this.buffer.clear();
      }

      super.flush();
   }

   public long tell() {
      return this.rawIO.tell() + (long)this.buffer.position();
   }

   public long seek(long pos, int whence) {
      this.flush();
      return this.rawIO.seek(pos, whence);
   }

   public boolean buffered() {
      return this.buffer.position() > 0;
   }

   public ByteBuffer readall() {
      this.checkClosed();
      this.checkReadable();
      return null;
   }

   public int readinto(ByteBuffer bytes) {
      this.checkClosed();
      this.checkReadable();
      return -1;
   }

   public int read1(ByteBuffer bytes) {
      this.checkClosed();
      this.checkReadable();
      return -1;
   }

   public boolean readable() {
      return false;
   }
}
