package weblogic.security.service;

public class NotAuthorizedRuntimeException extends SecurityServiceRuntimeException {
   public NotAuthorizedRuntimeException(String s) {
      super(s);
   }
}
