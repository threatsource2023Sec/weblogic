package org.apache.commons.io.output;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.input.ClosedInputStream;

public class ByteArrayOutputStream extends OutputStream {
   static final int DEFAULT_SIZE = 1024;
   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
   private final List buffers;
   private int currentBufferIndex;
   private int filledBufferSum;
   private byte[] currentBuffer;
   private int count;
   private boolean reuseBuffers;

   public ByteArrayOutputStream() {
      this(1024);
   }

   public ByteArrayOutputStream(int size) {
      this.buffers = new ArrayList();
      this.reuseBuffers = true;
      if (size < 0) {
         throw new IllegalArgumentException("Negative initial size: " + size);
      } else {
         synchronized(this) {
            this.needNewBuffer(size);
         }
      }
   }

   private void needNewBuffer(int newcount) {
      if (this.currentBufferIndex < this.buffers.size() - 1) {
         this.filledBufferSum += this.currentBuffer.length;
         ++this.currentBufferIndex;
         this.currentBuffer = (byte[])this.buffers.get(this.currentBufferIndex);
      } else {
         int newBufferSize;
         if (this.currentBuffer == null) {
            newBufferSize = newcount;
            this.filledBufferSum = 0;
         } else {
            newBufferSize = Math.max(this.currentBuffer.length << 1, newcount - this.filledBufferSum);
            this.filledBufferSum += this.currentBuffer.length;
         }

         ++this.currentBufferIndex;
         this.currentBuffer = new byte[newBufferSize];
         this.buffers.add(this.currentBuffer);
      }

   }

   public void write(byte[] b, int off, int len) {
      if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length && off + len >= 0) {
         if (len != 0) {
            synchronized(this) {
               int newcount = this.count + len;
               int remaining = len;
               int inBufferPos = this.count - this.filledBufferSum;

               while(remaining > 0) {
                  int part = Math.min(remaining, this.currentBuffer.length - inBufferPos);
                  System.arraycopy(b, off + len - remaining, this.currentBuffer, inBufferPos, part);
                  remaining -= part;
                  if (remaining > 0) {
                     this.needNewBuffer(newcount);
                     inBufferPos = 0;
                  }
               }

               this.count = newcount;
            }
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public synchronized void write(int b) {
      int inBufferPos = this.count - this.filledBufferSum;
      if (inBufferPos == this.currentBuffer.length) {
         this.needNewBuffer(this.count + 1);
         inBufferPos = 0;
      }

      this.currentBuffer[inBufferPos] = (byte)b;
      ++this.count;
   }

   public synchronized int write(InputStream in) throws IOException {
      int readCount = 0;
      int inBufferPos = this.count - this.filledBufferSum;

      for(int n = in.read(this.currentBuffer, inBufferPos, this.currentBuffer.length - inBufferPos); n != -1; n = in.read(this.currentBuffer, inBufferPos, this.currentBuffer.length - inBufferPos)) {
         readCount += n;
         inBufferPos += n;
         this.count += n;
         if (inBufferPos == this.currentBuffer.length) {
            this.needNewBuffer(this.currentBuffer.length);
            inBufferPos = 0;
         }
      }

      return readCount;
   }

   public synchronized int size() {
      return this.count;
   }

   public void close() throws IOException {
   }

   public synchronized void reset() {
      this.count = 0;
      this.filledBufferSum = 0;
      this.currentBufferIndex = 0;
      if (this.reuseBuffers) {
         this.currentBuffer = (byte[])this.buffers.get(this.currentBufferIndex);
      } else {
         this.currentBuffer = null;
         int size = ((byte[])this.buffers.get(0)).length;
         this.buffers.clear();
         this.needNewBuffer(size);
         this.reuseBuffers = true;
      }

   }

   public synchronized void writeTo(OutputStream out) throws IOException {
      int remaining = this.count;
      Iterator var3 = this.buffers.iterator();

      while(var3.hasNext()) {
         byte[] buf = (byte[])var3.next();
         int c = Math.min(buf.length, remaining);
         out.write(buf, 0, c);
         remaining -= c;
         if (remaining == 0) {
            break;
         }
      }

   }

   public static InputStream toBufferedInputStream(InputStream input) throws IOException {
      return toBufferedInputStream(input, 1024);
   }

   public static InputStream toBufferedInputStream(InputStream input, int size) throws IOException {
      ByteArrayOutputStream output = new ByteArrayOutputStream(size);
      output.write(input);
      return output.toInputStream();
   }

   public synchronized InputStream toInputStream() {
      int remaining = this.count;
      if (remaining == 0) {
         return new ClosedInputStream();
      } else {
         List list = new ArrayList(this.buffers.size());
         Iterator var3 = this.buffers.iterator();

         while(var3.hasNext()) {
            byte[] buf = (byte[])var3.next();
            int c = Math.min(buf.length, remaining);
            list.add(new ByteArrayInputStream(buf, 0, c));
            remaining -= c;
            if (remaining == 0) {
               break;
            }
         }

         this.reuseBuffers = false;
         return new SequenceInputStream(Collections.enumeration(list));
      }
   }

   public synchronized byte[] toByteArray() {
      int remaining = this.count;
      if (remaining == 0) {
         return EMPTY_BYTE_ARRAY;
      } else {
         byte[] newbuf = new byte[remaining];
         int pos = 0;
         Iterator var4 = this.buffers.iterator();

         while(var4.hasNext()) {
            byte[] buf = (byte[])var4.next();
            int c = Math.min(buf.length, remaining);
            System.arraycopy(buf, 0, newbuf, pos, c);
            pos += c;
            remaining -= c;
            if (remaining == 0) {
               break;
            }
         }

         return newbuf;
      }
   }

   /** @deprecated */
   @Deprecated
   public String toString() {
      return new String(this.toByteArray(), Charset.defaultCharset());
   }

   public String toString(String enc) throws UnsupportedEncodingException {
      return new String(this.toByteArray(), enc);
   }

   public String toString(Charset charset) {
      return new String(this.toByteArray(), charset);
   }
}
