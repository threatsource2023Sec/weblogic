package org.glassfish.grizzly.memory;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ThreadCache;

public final class BufferArray extends AbstractBufferArray {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(BufferArray.class, Integer.getInteger(BufferArray.class.getName() + "ba-cache-size", 4));

   public static BufferArray create() {
      BufferArray array = (BufferArray)ThreadCache.takeFromCache(CACHE_IDX);
      return array != null ? array : new BufferArray();
   }

   private BufferArray() {
      super(Buffer.class);
   }

   public void recycle() {
      super.recycle();
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   protected void setPositionLimit(Buffer buffer, int position, int limit) {
      Buffers.setPositionLimit(buffer, position, limit);
   }

   protected int getPosition(Buffer buffer) {
      return buffer.position();
   }

   protected int getLimit(Buffer buffer) {
      return buffer.limit();
   }
}
