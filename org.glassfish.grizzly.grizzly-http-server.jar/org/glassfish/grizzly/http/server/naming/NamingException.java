package org.glassfish.grizzly.http.server.naming;

public class NamingException extends Exception {
   public NamingException(String message) {
      super(message);
   }

   public NamingException(String message, Throwable cause) {
      super(message, cause);
   }

   public NamingException(Throwable cause) {
      super(cause);
   }
}
