package org.apache.xml.security.stax.impl.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TrimmerOutputStream extends FilterOutputStream {
   private byte[] buffer;
   private int bufferedCount;
   private int preTrimmed;
   private int startTrimLength;
   private int endTrimLength;

   public TrimmerOutputStream(OutputStream out, int bufferSize, int startTrimLength, int endTrimLength) {
      super(out);
      if (bufferSize <= 0) {
         throw new IllegalArgumentException("bufferSize <= 0");
      } else if (bufferSize < endTrimLength) {
         throw new IllegalArgumentException("bufferSize < endTrimLength");
      } else {
         this.buffer = new byte[bufferSize];
         this.startTrimLength = startTrimLength;
         this.endTrimLength = endTrimLength;
      }
   }

   private void flushBuffer() throws IOException {
      if (this.bufferedCount >= this.endTrimLength) {
         this.out.write(this.buffer, 0, this.bufferedCount - this.endTrimLength);
         System.arraycopy(this.buffer, this.bufferedCount - this.endTrimLength, this.buffer, 0, this.endTrimLength);
         this.bufferedCount = this.endTrimLength;
      }

   }

   public void write(int b) throws IOException {
      if (this.preTrimmed < this.startTrimLength) {
         ++this.preTrimmed;
      } else {
         if (this.bufferedCount >= this.buffer.length) {
            this.flushBuffer();
         }

         this.buffer[this.bufferedCount++] = (byte)b;
      }
   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (this.preTrimmed < this.startTrimLength) {
         int missingBytes = this.startTrimLength - this.preTrimmed;
         if (missingBytes >= len) {
            this.preTrimmed += len;
            return;
         }

         len -= missingBytes;
         off += missingBytes;
         this.preTrimmed += missingBytes;
      }

      if (len >= this.buffer.length - this.bufferedCount) {
         this.out.write(this.buffer, 0, this.bufferedCount);
         this.out.write(b, off, len - this.endTrimLength);
         System.arraycopy(b, off + len - this.endTrimLength, this.buffer, 0, this.endTrimLength);
         this.bufferedCount = this.endTrimLength;
      } else {
         System.arraycopy(b, off, this.buffer, this.bufferedCount, len);
         this.bufferedCount += len;
      }
   }

   public void flush() throws IOException {
      this.flushBuffer();
      this.out.flush();
   }
}
