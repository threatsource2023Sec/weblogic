package weblogic.net.http;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class HttpRetryException extends IOException {
   private static final long serialVersionUID = 2681042980966739659L;
   private int responseCode;
   private String location;
   private static Constructor javaCtor;

   public HttpRetryException(String detail, int code) {
      super(detail);
      this.responseCode = code;
   }

   public HttpRetryException(String detail, int code, String location) {
      super(detail);
      this.responseCode = code;
      this.location = location;
   }

   static IOException newInstance(String detail, int code, String location) {
      if (javaCtor != null) {
         try {
            return (IOException)javaCtor.newInstance(detail, new Integer(code), location);
         } catch (Exception var4) {
         }
      }

      return new HttpRetryException(detail, code, location);
   }

   public int responseCode() {
      return this.responseCode;
   }

   public String getReason() {
      return super.getMessage();
   }

   public String getLocation() {
      return this.location;
   }

   static {
      try {
         Class e = Class.forName("java.net.HttpRetryException");
         javaCtor = e.getConstructor(String.class, Integer.TYPE, String.class);
      } catch (Exception var1) {
      }

   }
}
