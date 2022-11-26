package org.glassfish.grizzly.http.server.http2;

import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.MimeHeaders;

public class PushEvent implements FilterChainEvent {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(PushEvent.class, 8);
   public static final Object TYPE = PushEvent.class.getName();
   private String method;
   private MimeHeaders headers = new MimeHeaders();
   private String path;
   private HttpRequestPacket httpRequest;

   private PushEvent() {
   }

   public Object type() {
      return TYPE;
   }

   public static PushEvent create(PushBuilder builder) {
      PushEvent pushEvent = (PushEvent)ThreadCache.takeFromCache(CACHE_IDX);
      if (pushEvent == null) {
         pushEvent = new PushEvent();
      }

      return pushEvent.init(builder);
   }

   public String getMethod() {
      return this.method;
   }

   public MimeHeaders getHeaders() {
      return this.headers;
   }

   public String getPath() {
      return this.path;
   }

   public HttpHeader getHttpRequest() {
      return this.httpRequest;
   }

   public void recycle() {
      this.method = null;
      this.headers.recycle();
      this.path = null;
      this.httpRequest = null;
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public static PushEventBuilder builder() {
      return new PushEventBuilder();
   }

   private static PushEvent create(PushEventBuilder builder) {
      PushEvent pushEvent = (PushEvent)ThreadCache.takeFromCache(CACHE_IDX);
      if (pushEvent == null) {
         pushEvent = new PushEvent();
      }

      return pushEvent.init(builder);
   }

   private PushEvent init(PushBuilder builder) {
      this.method = builder.method;
      this.headers.copyFrom(builder.headers);
      this.path = builder.path;
      this.httpRequest = builder.request.getRequest();
      return this;
   }

   private PushEvent init(PushEventBuilder builder) {
      this.method = builder.method;
      this.headers.copyFrom(builder.headers);
      this.path = builder.path;
      this.httpRequest = builder.httpRequest;
      return this;
   }

   public static final class PushEventBuilder {
      private String method;
      private MimeHeaders headers;
      private String path;
      private HttpRequestPacket httpRequest;

      private PushEventBuilder() {
         this.method = Method.GET.getMethodString();
         this.headers = new MimeHeaders();
      }

      public PushEventBuilder method(String val) {
         if (this.method == null) {
            throw new NullPointerException();
         } else if (!Method.POST.getMethodString().equals(this.method) && !Method.PUT.getMethodString().equals(this.method) && !Method.DELETE.getMethodString().equals(this.method) && !Method.CONNECT.getMethodString().equals(this.method) && !Method.OPTIONS.getMethodString().equals(this.method) && !Method.TRACE.getMethodString().equals(this.method)) {
            this.method = val;
            return this;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public PushEventBuilder headers(MimeHeaders val) {
         if (val == null) {
            throw new NullPointerException();
         } else {
            this.headers.copyFrom(val);
            return this;
         }
      }

      public PushEventBuilder path(String val) {
         this.path = validate(val);
         return this;
      }

      public PushEventBuilder httpRequest(HttpRequestPacket val) {
         if (val == null) {
            throw new NullPointerException();
         } else {
            this.httpRequest = val;
            return this;
         }
      }

      public PushEvent build() {
         if (this.path != null && this.httpRequest != null && this.headers != null) {
            return PushEvent.create(this);
         } else {
            throw new IllegalArgumentException();
         }
      }

      private static String validate(String val) {
         return val != null && !val.isEmpty() ? val : null;
      }

      // $FF: synthetic method
      PushEventBuilder(Object x0) {
         this();
      }
   }
}
