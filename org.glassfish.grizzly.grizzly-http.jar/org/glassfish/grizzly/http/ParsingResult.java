package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;

public final class ParsingResult implements Cacheable {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(ParsingResult.class, 1);
   private HttpContent httpContent;
   private Buffer remainderBuffer;
   private boolean sendHeaderUpstream = true;

   public static ParsingResult create(HttpContent httpContent, Buffer remainderBuffer) {
      ParsingResult resultObject = (ParsingResult)ThreadCache.takeFromCache(CACHE_IDX);
      if (resultObject == null) {
         resultObject = new ParsingResult();
      }

      resultObject.httpContent = httpContent;
      resultObject.remainderBuffer = remainderBuffer;
      return resultObject;
   }

   public static ParsingResult create(HttpContent httpContent, Buffer remainderBuffer, boolean sendHeaderUpstream) {
      ParsingResult resultObject = create(httpContent, remainderBuffer);
      resultObject.sendHeaderUpstream = sendHeaderUpstream;
      return resultObject;
   }

   private ParsingResult() {
   }

   public Buffer getRemainderBuffer() {
      return this.remainderBuffer;
   }

   public HttpContent getHttpContent() {
      return this.httpContent;
   }

   public boolean isSendHeaderUpstream() {
      return this.sendHeaderUpstream;
   }

   public void recycle() {
      this.remainderBuffer = null;
      this.httpContent = null;
      this.sendHeaderUpstream = true;
      ThreadCache.putToCache(CACHE_IDX, this);
   }
}
