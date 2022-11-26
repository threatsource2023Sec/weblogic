package weblogic.utils.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import weblogic.utils.concurrent.locks.Mutex;

public final class DoubleBufferedOutputStream extends FilterOutputStream {
   private final OutputStream out;
   private final int size;
   private byte[] writeBuf;
   private byte[] flushBuf;
   private int writeCount;
   private int flushCount;
   private final Object writeLock;
   private final Mutex flushLock;

   public DoubleBufferedOutputStream(OutputStream out) {
      this(out, 512);
   }

   public DoubleBufferedOutputStream(OutputStream out, int size) {
      super(out);
      this.writeCount = 0;
      this.flushCount = 0;
      this.writeLock = new Object() {
      };
      this.flushLock = new Mutex();
      this.writeBuf = new byte[size];
      this.flushBuf = new byte[size];
      this.size = size;
      this.out = out;
   }

   private void flushBuffer() throws IOException {
      if (this.flushCount > 0) {
         this.out.write(this.flushBuf, 0, this.flushCount);
         this.flushCount = 0;
      }

   }

   private void swapBuffer() {
      byte[] tempBuf = this.writeBuf;
      this.writeBuf = this.flushBuf;
      this.flushBuf = tempBuf;
      this.flushCount = this.writeCount;
      this.writeCount = 0;
   }

   public void write(int b) throws IOException {
      synchronized(this.writeLock) {
         if (this.writeCount < this.size) {
            this.writeBuf[this.writeCount++] = (byte)b;
            return;
         }

         this.flushLock.lock();
         this.swapBuffer();
         this.writeBuf[this.writeCount++] = (byte)b;
      }

      try {
         this.flushBuffer();
      } finally {
         this.flushLock.unlock();
      }

   }

   public void write(byte[] b, int off, int len) throws IOException {
      boolean tooBig = false;
      synchronized(this.writeLock) {
         int bufAvail = this.size - this.writeCount;
         if (len <= bufAvail) {
            System.arraycopy(b, off, this.writeBuf, this.writeCount, len);
            this.writeCount += len;
            return;
         }

         System.arraycopy(b, off, this.writeBuf, this.writeCount, bufAvail);
         this.writeCount += bufAvail;
         off += bufAvail;
         len -= bufAvail;
         this.flushLock.lock();
         this.swapBuffer();
         if (len <= this.size) {
            System.arraycopy(b, off, this.writeBuf, this.writeCount, len);
            this.writeCount += len;
         } else {
            tooBig = true;
         }
      }

      try {
         this.flushBuffer();
         if (tooBig) {
            this.out.write(b, off, len);
         }
      } finally {
         this.flushLock.unlock();
      }

   }

   public void flush() throws IOException {
      synchronized(this.writeLock) {
         this.flushLock.lock();
         this.swapBuffer();
      }

      try {
         this.flushBuffer();
         this.out.flush();
      } finally {
         this.flushLock.unlock();
      }

   }
}
