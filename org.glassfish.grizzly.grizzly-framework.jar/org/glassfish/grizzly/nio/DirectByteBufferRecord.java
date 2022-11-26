package org.glassfish.grizzly.nio;

import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.memory.Buffers;

public final class DirectByteBufferRecord {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex("direct-buffer-cache", DirectByteBufferRecord.class, 1);
   private ByteBuffer directBuffer;
   private int sliceOffset;
   private ByteBuffer directBufferSlice;
   private SoftReference softRef;
   private ByteBuffer[] array = new ByteBuffer[8];
   private int arraySize;

   public static DirectByteBufferRecord get() {
      DirectByteBufferRecord record = (DirectByteBufferRecord)ThreadCache.getFromCache(CACHE_IDX);
      if (record != null) {
         return record;
      } else {
         DirectByteBufferRecord recordLocal = new DirectByteBufferRecord();
         ThreadCache.putToCache(CACHE_IDX, recordLocal);
         return recordLocal;
      }
   }

   DirectByteBufferRecord() {
   }

   public ByteBuffer getDirectBuffer() {
      return this.directBuffer;
   }

   public ByteBuffer getDirectBufferSlice() {
      return this.directBufferSlice;
   }

   public ByteBuffer allocate(int size) {
      ByteBuffer byteBuffer;
      if ((byteBuffer = this.switchToStrong()) != null && byteBuffer.remaining() >= size) {
         return byteBuffer;
      } else {
         byteBuffer = ByteBuffer.allocateDirect(size);
         this.reset(byteBuffer);
         return byteBuffer;
      }
   }

   public ByteBuffer sliceBuffer() {
      int oldLim = this.directBuffer.limit();
      Buffers.setPositionLimit(this.directBuffer, this.sliceOffset, this.directBuffer.capacity());
      this.directBufferSlice = this.directBuffer.slice();
      Buffers.setPositionLimit((ByteBuffer)this.directBuffer, 0, oldLim);
      return this.directBufferSlice;
   }

   public void finishBufferSlice() {
      if (this.directBufferSlice != null) {
         this.directBufferSlice.flip();
         int sliceSz = this.directBufferSlice.remaining();
         this.sliceOffset += sliceSz;
         if (sliceSz > 0) {
            this.putToArray(this.directBufferSlice);
         }

         this.directBufferSlice = null;
      }

   }

   public ByteBuffer[] getArray() {
      return this.array;
   }

   public int getArraySize() {
      return this.arraySize;
   }

   public void putToArray(ByteBuffer byteBuffer) {
      this.ensureArraySize();
      this.array[this.arraySize++] = byteBuffer;
   }

   public void release() {
      if (this.directBuffer != null) {
         this.directBuffer.clear();
         this.switchToSoft();
      }

      Arrays.fill(this.array, 0, this.arraySize, (Object)null);
      this.arraySize = 0;
      this.directBufferSlice = null;
      this.sliceOffset = 0;
   }

   private ByteBuffer switchToStrong() {
      if (this.directBuffer == null && this.softRef != null) {
         this.directBuffer = this.directBufferSlice = (ByteBuffer)this.softRef.get();
      }

      return this.directBuffer;
   }

   private void switchToSoft() {
      if (this.directBuffer != null && this.softRef == null) {
         this.softRef = new SoftReference(this.directBuffer);
      }

      this.directBuffer = null;
   }

   private void reset(ByteBuffer byteBuffer) {
      this.directBuffer = this.directBufferSlice = byteBuffer;
      this.softRef = null;
   }

   private void ensureArraySize() {
      if (this.arraySize == this.array.length) {
         this.array = (ByteBuffer[])Arrays.copyOf(this.array, this.arraySize * 3 / 2 + 1);
      }

   }
}
