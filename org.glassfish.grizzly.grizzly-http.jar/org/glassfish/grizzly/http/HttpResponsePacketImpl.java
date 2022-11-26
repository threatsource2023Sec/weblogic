package org.glassfish.grizzly.http;

import org.glassfish.grizzly.ThreadCache;

class HttpResponsePacketImpl extends HttpResponsePacket {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(HttpResponsePacketImpl.class, 16);

   public static HttpResponsePacketImpl create() {
      HttpResponsePacketImpl httpResponseImpl = (HttpResponsePacketImpl)ThreadCache.takeFromCache(CACHE_IDX);
      return httpResponseImpl != null ? httpResponseImpl : new HttpResponsePacketImpl() {
         public void recycle() {
            super.recycle();
            ThreadCache.putToCache(HttpResponsePacketImpl.CACHE_IDX, this);
         }
      };
   }

   protected HttpResponsePacketImpl() {
   }

   public ProcessingState getProcessingState() {
      return this.getRequest().getProcessingState();
   }

   protected void reset() {
      super.reset();
   }

   public void recycle() {
      if (!this.getRequest().isExpectContent()) {
         this.reset();
      }
   }
}
