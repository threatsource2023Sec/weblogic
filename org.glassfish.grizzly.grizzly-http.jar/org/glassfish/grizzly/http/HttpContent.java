package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Appendable;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;

public class HttpContent extends HttpPacket implements Appendable {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(HttpContent.class, 16);
   protected boolean isLast;
   protected Buffer content;
   protected HttpHeader httpHeader;

   public static boolean isContent(HttpPacket httpPacket) {
      return httpPacket instanceof HttpContent;
   }

   public static boolean isBroken(HttpContent httpContent) {
      return httpContent instanceof HttpBrokenContent;
   }

   public static HttpContent create() {
      return create((HttpHeader)null);
   }

   public static HttpContent create(HttpHeader httpHeader) {
      return create(httpHeader, false);
   }

   public static HttpContent create(HttpHeader httpHeader, boolean isLast) {
      return create(httpHeader, isLast, Buffers.EMPTY_BUFFER);
   }

   public static HttpContent create(HttpHeader httpHeader, boolean isLast, Buffer content) {
      content = content != null ? content : Buffers.EMPTY_BUFFER;
      HttpContent httpContent = (HttpContent)ThreadCache.takeFromCache(CACHE_IDX);
      if (httpContent != null) {
         httpContent.httpHeader = httpHeader;
         httpContent.isLast = isLast;
         httpContent.content = content;
         return httpContent;
      } else {
         return new HttpContent(httpHeader, isLast, content);
      }
   }

   public static Builder builder(HttpHeader httpHeader) {
      return (new Builder()).httpHeader(httpHeader);
   }

   protected HttpContent() {
      this((HttpHeader)null);
   }

   protected HttpContent(HttpHeader httpHeader) {
      this.content = Buffers.EMPTY_BUFFER;
      this.httpHeader = httpHeader;
   }

   protected HttpContent(HttpHeader httpHeader, boolean isLast, Buffer content) {
      this.content = Buffers.EMPTY_BUFFER;
      this.httpHeader = httpHeader;
      this.isLast = isLast;
      this.content = content;
   }

   public Buffer getContent() {
      return this.content;
   }

   protected final void setContent(Buffer content) {
      this.content = content;
   }

   public final HttpHeader getHttpHeader() {
      return this.httpHeader;
   }

   public boolean isLast() {
      return this.isLast;
   }

   public void setLast(boolean isLast) {
      this.isLast = isLast;
   }

   public final boolean isHeader() {
      return false;
   }

   public HttpContent append(HttpContent element) {
      if (this.isLast) {
         throw new IllegalStateException("Can not append to a last chunk");
      } else if (isBroken(element)) {
         return element;
      } else {
         Buffer content2 = element.getContent();
         if (content2 != null && content2.hasRemaining()) {
            this.content = Buffers.appendBuffers((MemoryManager)null, this.content, content2);
         }

         if (element.isLast()) {
            element.setContent(this.content);
            return element;
         } else {
            return this;
         }
      }
   }

   protected void reset() {
      this.isLast = false;
      this.content = Buffers.EMPTY_BUFFER;
      this.httpHeader = null;
   }

   public void recycle() {
      this.reset();
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public static class Builder {
      protected boolean last;
      protected Buffer content;
      protected HttpHeader httpHeader;

      protected Builder() {
      }

      public final Builder httpHeader(HttpHeader httpHeader) {
         this.httpHeader = httpHeader;
         return this;
      }

      public final Builder last(boolean last) {
         this.last = last;
         return this;
      }

      public final Builder content(Buffer content) {
         this.content = content;
         return this;
      }

      public HttpContent build() {
         if (this.httpHeader == null) {
            throw new IllegalStateException("No HttpHeader specified to associate with this HttpContent.");
         } else {
            HttpContent httpContent = this.create();
            httpContent.httpHeader = this.httpHeader;
            httpContent.setLast(this.last);
            if (this.content != null) {
               httpContent.setContent(this.content);
            }

            return httpContent;
         }
      }

      public void reset() {
         this.last = false;
         this.content = null;
         this.httpHeader = null;
      }

      protected HttpContent create() {
         return HttpContent.create();
      }
   }
}
