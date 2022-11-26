package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;
import org.glassfish.grizzly.ThreadCache;

public final class ByteBufferArray extends AbstractBufferArray {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(ByteBufferArray.class, Integer.getInteger(ByteBufferArray.class.getName() + "bba-cache-size", 4));

   public static ByteBufferArray create() {
      ByteBufferArray array = (ByteBufferArray)ThreadCache.takeFromCache(CACHE_IDX);
      return array != null ? array : new ByteBufferArray();
   }

   private ByteBufferArray() {
      super(ByteBuffer.class);
   }

   public void recycle() {
      super.recycle();
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   protected void setPositionLimit(ByteBuffer buffer, int position, int limit) {
      Buffers.setPositionLimit(buffer, position, limit);
   }

   protected int getPosition(ByteBuffer buffer) {
      return buffer.position();
   }

   protected int getLimit(ByteBuffer buffer) {
      return buffer.limit();
   }
}
