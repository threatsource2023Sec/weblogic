package org.glassfish.grizzly.utils;

import java.io.IOException;
import java.io.InputStream;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.Buffers;

public class BufferInputStream extends InputStream {
   private final Buffer buffer;
   private final boolean isMovingPosition;
   private int position;
   private final int limit;

   public BufferInputStream(Buffer buffer) {
      this.isMovingPosition = true;
      this.buffer = buffer;
      this.position = buffer.position();
      this.limit = buffer.limit();
   }

   public BufferInputStream(Buffer buffer, int position, int limit) {
      this.isMovingPosition = false;
      this.buffer = buffer;
      this.position = position;
      this.limit = limit;
   }

   public int read() throws IOException {
      if (this.position >= this.limit) {
         return -1;
      } else {
         int result = this.buffer.get(this.position++) & 255;
         if (this.isMovingPosition) {
            this.buffer.position(this.position);
         }

         return result;
      }
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (this.position >= this.limit) {
         return -1;
      } else {
         int length = Math.min(len, this.available());
         int oldPos = this.buffer.position();
         int oldLim = this.buffer.limit();
         if (!this.isMovingPosition) {
            Buffers.setPositionLimit(this.buffer, this.position, this.limit);
         }

         try {
            this.buffer.get(b, off, length);
         } finally {
            if (!this.isMovingPosition) {
               Buffers.setPositionLimit(this.buffer, oldPos, oldLim);
            }

         }

         this.position += length;
         return length;
      }
   }

   public int available() throws IOException {
      return this.limit - this.position;
   }

   public long skip(long n) throws IOException {
      int skipped = (int)Math.min(n, (long)this.available());
      this.position += skipped;
      if (this.isMovingPosition) {
         this.buffer.position(this.position);
      }

      return (long)skipped;
   }
}
