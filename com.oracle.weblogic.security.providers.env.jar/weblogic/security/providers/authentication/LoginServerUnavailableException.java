package weblogic.security.providers.authentication;

import weblogic.security.service.LoginServerNotAvailableException;

public class LoginServerUnavailableException extends LoginServerNotAvailableException {
   public LoginServerUnavailableException() {
   }

   public LoginServerUnavailableException(String message) {
      super(message);
   }
}
