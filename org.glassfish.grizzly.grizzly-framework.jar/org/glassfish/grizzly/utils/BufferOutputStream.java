package org.glassfish.grizzly.utils;

import java.io.IOException;
import java.io.OutputStream;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;

public class BufferOutputStream extends OutputStream {
   private static final int BUFFER_SIZE = 8192;
   final MemoryManager mm;
   final boolean reallocate;
   private Buffer currentBuffer;
   private CompositeBuffer compositeBuffer;

   public BufferOutputStream(MemoryManager mm) {
      this(mm, (Buffer)null);
   }

   public BufferOutputStream(MemoryManager mm, Buffer buffer) {
      this(mm, buffer, false);
   }

   public BufferOutputStream(MemoryManager mm, Buffer buffer, boolean reallocate) {
      this.currentBuffer = buffer;
      this.mm = mm;
      this.reallocate = reallocate;
   }

   public void setInitialOutputBuffer(Buffer initialBuffer) {
      if (this.currentBuffer == null && this.compositeBuffer == null) {
         this.currentBuffer = initialBuffer;
      } else {
         throw new IllegalStateException("Can not set initial buffer on non-reset OutputStream");
      }
   }

   public Buffer getBuffer() {
      if (!this.reallocate && this.compositeBuffer != null) {
         if (this.currentBuffer != null && this.currentBuffer.position() > 0) {
            this.flushCurrent();
         }

         return this.compositeBuffer;
      } else {
         return this.currentBuffer != null ? this.currentBuffer : Buffers.EMPTY_BUFFER;
      }
   }

   public boolean isReallocate() {
      return this.reallocate;
   }

   public void write(int b) throws IOException {
      this.ensureCapacity(1);
      this.currentBuffer.put((byte)b);
   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.ensureCapacity(len);
      this.currentBuffer.put(b, off, len);
   }

   public void flush() throws IOException {
   }

   public void close() throws IOException {
   }

   public void reset() {
      this.currentBuffer = null;
      this.compositeBuffer = null;
   }

   protected Buffer allocateNewBuffer(MemoryManager memoryManager, int size) {
      return memoryManager.allocate(size);
   }

   private void ensureCapacity(int len) {
      if (this.currentBuffer == null) {
         this.currentBuffer = this.allocateNewBuffer(this.mm, Math.max(8192, len));
      } else if (this.currentBuffer.remaining() < len) {
         if (this.reallocate) {
            this.currentBuffer = this.mm.reallocate(this.currentBuffer, Math.max(this.currentBuffer.capacity() + len, this.currentBuffer.capacity() * 3 / 2 + 1));
         } else {
            this.flushCurrent();
            this.currentBuffer = this.allocateNewBuffer(this.mm, Math.max(8192, len));
         }
      }

   }

   private void flushCurrent() {
      this.currentBuffer.trim();
      if (this.compositeBuffer == null) {
         this.compositeBuffer = CompositeBuffer.newBuffer(this.mm);
      }

      this.compositeBuffer.append(this.currentBuffer);
      this.compositeBuffer.position(this.compositeBuffer.limit());
      this.currentBuffer = null;
   }
}
