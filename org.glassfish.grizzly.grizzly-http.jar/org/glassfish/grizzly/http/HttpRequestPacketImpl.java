package org.glassfish.grizzly.http;

import org.glassfish.grizzly.ThreadCache;

class HttpRequestPacketImpl extends HttpRequestPacket {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(HttpRequestPacketImpl.class, 16);
   private final ProcessingState processingState = new ProcessingState();

   public static HttpRequestPacketImpl create() {
      HttpRequestPacketImpl httpRequestImpl = (HttpRequestPacketImpl)ThreadCache.takeFromCache(CACHE_IDX);
      return httpRequestImpl != null ? httpRequestImpl : new HttpRequestPacketImpl() {
         public void recycle() {
            super.recycle();
            ThreadCache.putToCache(HttpRequestPacketImpl.CACHE_IDX, this);
         }
      };
   }

   protected HttpRequestPacketImpl() {
      this.isExpectContent = true;
   }

   public ProcessingState getProcessingState() {
      return this.processingState;
   }

   protected void reset() {
      this.processingState.recycle();
      this.isExpectContent = true;
      super.reset();
   }

   public void recycle() {
      if (!this.isExpectContent()) {
         this.reset();
      }
   }
}
