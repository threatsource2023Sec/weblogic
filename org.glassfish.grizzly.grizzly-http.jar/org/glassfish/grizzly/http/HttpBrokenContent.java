package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ThreadCache;

public class HttpBrokenContent extends HttpContent {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(HttpBrokenContent.class, 1);
   private Throwable exception;

   public static HttpBrokenContent create() {
      return create((HttpHeader)null);
   }

   public static HttpBrokenContent create(HttpHeader httpHeader) {
      HttpBrokenContent httpBrokenContent = (HttpBrokenContent)ThreadCache.takeFromCache(CACHE_IDX);
      if (httpBrokenContent != null) {
         httpBrokenContent.httpHeader = httpHeader;
         return httpBrokenContent;
      } else {
         return new HttpBrokenContent(httpHeader);
      }
   }

   public static Builder builder(HttpHeader httpHeader) {
      return (Builder)(new Builder()).httpHeader(httpHeader);
   }

   protected HttpBrokenContent(HttpHeader httpHeader) {
      super(httpHeader);
   }

   public Throwable getException() {
      return this.exception;
   }

   public Buffer getContent() {
      throw this.exception instanceof HttpBrokenContentException ? (HttpBrokenContentException)this.exception : new HttpBrokenContentException(this.exception);
   }

   public final boolean isLast() {
      return true;
   }

   protected void reset() {
      this.exception = null;
      super.reset();
   }

   public void recycle() {
      this.reset();
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public static final class Builder extends HttpContent.Builder {
      private Throwable cause;

      protected Builder() {
      }

      public final Builder error(Throwable cause) {
         this.cause = cause;
         return this;
      }

      public final HttpBrokenContent build() {
         HttpBrokenContent httpBrokenContent = (HttpBrokenContent)super.build();
         if (this.cause == null) {
            throw new IllegalStateException("No cause specified");
         } else {
            httpBrokenContent.exception = this.cause;
            return httpBrokenContent;
         }
      }

      protected HttpContent create() {
         return HttpBrokenContent.create();
      }
   }
}
