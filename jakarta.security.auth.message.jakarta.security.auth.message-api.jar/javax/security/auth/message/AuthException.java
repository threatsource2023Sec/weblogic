package javax.security.auth.message;

import javax.security.auth.login.LoginException;

public class AuthException extends LoginException {
   private static final long serialVersionUID = -1156951780670243758L;

   public AuthException() {
   }

   public AuthException(String msg) {
      super(msg);
   }
}
