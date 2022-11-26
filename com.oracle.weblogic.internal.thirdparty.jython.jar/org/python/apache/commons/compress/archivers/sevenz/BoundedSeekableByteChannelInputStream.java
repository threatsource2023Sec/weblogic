package org.python.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

class BoundedSeekableByteChannelInputStream extends InputStream {
   private static final int MAX_BUF_LEN = 8192;
   private final ByteBuffer buffer;
   private final SeekableByteChannel channel;
   private long bytesRemaining;

   public BoundedSeekableByteChannelInputStream(SeekableByteChannel channel, long size) {
      this.channel = channel;
      this.bytesRemaining = size;
      if (size < 8192L && size > 0L) {
         this.buffer = ByteBuffer.allocate((int)size);
      } else {
         this.buffer = ByteBuffer.allocate(8192);
      }

   }

   public int read() throws IOException {
      if (this.bytesRemaining > 0L) {
         --this.bytesRemaining;
         int read = this.read(1);
         return read < 0 ? read : this.buffer.get() & 255;
      } else {
         return -1;
      }
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (this.bytesRemaining == 0L) {
         return -1;
      } else {
         int bytesToRead = len;
         if ((long)len > this.bytesRemaining) {
            bytesToRead = (int)this.bytesRemaining;
         }

         ByteBuffer buf;
         int bytesRead;
         if (bytesToRead <= this.buffer.capacity()) {
            buf = this.buffer;
            bytesRead = this.read(bytesToRead);
         } else {
            buf = ByteBuffer.allocate(bytesToRead);
            bytesRead = this.channel.read(buf);
            buf.flip();
         }

         if (bytesRead >= 0) {
            buf.get(b, off, bytesRead);
            this.bytesRemaining -= (long)bytesRead;
         }

         return bytesRead;
      }
   }

   private int read(int len) throws IOException {
      this.buffer.rewind().limit(len);
      int read = this.channel.read(this.buffer);
      this.buffer.flip();
      return read;
   }

   public void close() {
   }
}
