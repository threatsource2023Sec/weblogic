package weblogic.security.service;

import javax.security.auth.login.LoginException;

public class LoginServerNotAvailableException extends LoginException {
   public LoginServerNotAvailableException() {
   }

   public LoginServerNotAvailableException(String message) {
      super(message);
   }
}
