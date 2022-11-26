package weblogic.security.service;

import java.io.IOException;

public class HttpResponseErrorException extends IOException {
   int statusCode;

   public HttpResponseErrorException(int statusCode) {
      this.statusCode = statusCode;
   }

   public HttpResponseErrorException(String message, int statusCode) {
      super(message);
      this.statusCode = statusCode;
   }

   public HttpResponseErrorException(String message, Throwable cause, int statusCode) {
      super(message, cause);
      this.statusCode = statusCode;
   }

   public HttpResponseErrorException(Throwable cause, int statusCode) {
      super(cause);
      this.statusCode = statusCode;
   }

   public int getStatusCode() {
      return this.statusCode;
   }

   public void setStatusCode(int statusCode) {
      this.statusCode = statusCode;
   }
}
