package org.glassfish.grizzly.http.io;

import java.util.Arrays;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.HeapBuffer;
import org.glassfish.grizzly.memory.MemoryManager;

final class TemporaryHeapBuffer extends HeapBuffer {
   boolean isDisposed;
   boolean hasClonedArray;

   void reset(byte[] heap, int offset, int len) {
      this.heap = heap;
      this.offset = offset;
      this.cap = len;
      this.lim = len;
      this.pos = 0;
      this.byteBuffer = null;
      this.isDisposed = false;
      this.hasClonedArray = false;
   }

   Buffer cloneContent(MemoryManager memoryManager) {
      int length = this.remaining();
      Buffer buffer;
      if (!this.hasClonedArray) {
         buffer = memoryManager.allocate(length);
         buffer.put(this.heap, this.offset + this.pos, length);
         buffer.flip();
      } else {
         buffer = Buffers.wrap(memoryManager, this.heap, this.offset + this.pos, length);
      }

      buffer.allowBufferDispose(true);
      this.dispose();
      return buffer;
   }

   protected void onShareHeap() {
      if (!this.hasClonedArray) {
         this.heap = Arrays.copyOfRange(this.heap, this.offset, this.offset + this.cap);
         this.offset = 0;
         this.hasClonedArray = true;
      }

      super.onShareHeap();
   }

   public void dispose() {
      this.isDisposed = true;
      super.dispose();
   }

   boolean isDisposed() {
      return this.isDisposed;
   }

   public void recycle() {
      this.reset((byte[])null, 0, 0);
   }
}
