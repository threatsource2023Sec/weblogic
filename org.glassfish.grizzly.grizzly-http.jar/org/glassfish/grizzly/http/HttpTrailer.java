package org.glassfish.grizzly.http;

import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HeaderValue;
import org.glassfish.grizzly.http.util.MimeHeaders;

public class HttpTrailer extends HttpContent implements MimeHeadersPacket {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(HttpTrailer.class, 16);
   private MimeHeaders trailers = new MimeHeaders();

   public static boolean isTrailer(HttpContent httpContent) {
      return HttpTrailer.class.isAssignableFrom(httpContent.getClass());
   }

   public static HttpTrailer create() {
      return create((HttpHeader)null);
   }

   public static HttpTrailer create(HttpHeader httpHeader) {
      HttpTrailer httpTrailer = (HttpTrailer)ThreadCache.takeFromCache(CACHE_IDX);
      if (httpTrailer != null) {
         httpTrailer.httpHeader = httpHeader;
         return httpTrailer;
      } else {
         return new HttpTrailer(httpHeader);
      }
   }

   public static Builder builder(HttpHeader httpHeader) {
      return (Builder)(new Builder()).httpHeader(httpHeader);
   }

   protected HttpTrailer(HttpHeader httpHeader) {
      super(httpHeader);
      this.trailers.mark();
   }

   public final boolean isLast() {
      return true;
   }

   public MimeHeaders getHeaders() {
      return this.trailers;
   }

   public String getHeader(String name) {
      return this.trailers.getHeader(name);
   }

   public String getHeader(Header header) {
      return this.trailers.getHeader(header);
   }

   public void setHeader(String name, String value) {
      if (name != null && value != null) {
         this.trailers.setValue(name).setString(value);
      }
   }

   public void setHeader(String name, HeaderValue value) {
      if (name != null && value != null && value.isSet()) {
         value.serializeToDataChunk(this.trailers.setValue(name));
      }
   }

   public void setHeader(Header header, String value) {
      if (header != null && value != null) {
         this.trailers.setValue(header).setString(value);
      }
   }

   public void setHeader(Header header, HeaderValue value) {
      if (header != null && value != null && value.isSet()) {
         value.serializeToDataChunk(this.trailers.setValue(header));
      }
   }

   public void addHeader(String name, String value) {
      if (name != null && value != null) {
         this.trailers.addValue(name).setString(value);
      }
   }

   public void addHeader(String name, HeaderValue value) {
      if (name != null && value != null && value.isSet()) {
         value.serializeToDataChunk(this.trailers.setValue(name));
      }
   }

   public void addHeader(Header header, String value) {
      if (header != null && value != null) {
         DataChunk c = this.trailers.addValue(header);
         if (c != null) {
            c.setString(value);
         }

      }
   }

   public void addHeader(Header header, HeaderValue value) {
      if (header != null && value != null && value.isSet()) {
         value.serializeToDataChunk(this.trailers.setValue(header));
      }
   }

   public boolean containsHeader(String name) {
      return this.trailers.contains(name);
   }

   public boolean containsHeader(Header header) {
      return this.trailers.contains(header);
   }

   protected void setTrailers(MimeHeaders trailers) {
      this.trailers = trailers;
      this.trailers.mark();
   }

   protected void reset() {
      this.trailers.recycle();
      this.trailers.mark();
      super.reset();
   }

   public void recycle() {
      this.reset();
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public static final class Builder extends HttpContent.Builder {
      private MimeHeaders mimeTrailers;

      protected Builder() {
      }

      public final Builder headers(MimeHeaders mimeTrailers) {
         this.mimeTrailers = mimeTrailers;
         mimeTrailers.mark();
         return this;
      }

      public final Builder header(String name, String value) {
         if (this.mimeTrailers == null) {
            this.mimeTrailers = new MimeHeaders();
            this.mimeTrailers.mark();
         }

         DataChunk c = this.mimeTrailers.addValue(name);
         if (c != null) {
            c.setString(value);
         }

         return this;
      }

      public final HttpTrailer build() {
         HttpTrailer trailer = (HttpTrailer)super.build();
         if (this.mimeTrailers != null) {
            trailer.trailers = this.mimeTrailers;
         }

         return trailer;
      }

      protected HttpContent create() {
         return HttpTrailer.create();
      }
   }
}
