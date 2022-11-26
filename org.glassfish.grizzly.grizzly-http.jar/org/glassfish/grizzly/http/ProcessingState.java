package org.glassfish.grizzly.http;

public final class ProcessingState {
   boolean keepAlive = false;
   boolean error;
   HttpContext httpContext;

   public boolean isError() {
      return this.error;
   }

   public void setError(boolean error) {
      this.error = error;
   }

   public boolean isStayAlive() {
      return this.keepAlive && !this.error;
   }

   public boolean isKeepAlive() {
      return this.keepAlive;
   }

   public void setKeepAlive(boolean keepAlive) {
      this.keepAlive = keepAlive;
   }

   public HttpContext getHttpContext() {
      return this.httpContext;
   }

   public void setHttpContext(HttpContext httpContext) {
      this.httpContext = httpContext;
   }

   public void recycle() {
      this.keepAlive = false;
      this.error = false;
      this.httpContext = null;
   }
}
