package javax.security.enterprise;

import java.security.GeneralSecurityException;

public class AuthenticationException extends GeneralSecurityException {
   private static final long serialVersionUID = 1L;

   public AuthenticationException() {
   }

   public AuthenticationException(String message) {
      super(message);
   }

   public AuthenticationException(String message, Throwable cause) {
      super(message, cause);
   }

   public AuthenticationException(Throwable cause) {
      super(cause);
   }
}
