package weblogic.security.providers.authentication;

import weblogic.utils.NestedRuntimeException;

public final class DBMSSQLAuthenticatorDelegateException extends NestedRuntimeException {
   public DBMSSQLAuthenticatorDelegateException() {
   }

   public DBMSSQLAuthenticatorDelegateException(String msg) {
      super(msg);
   }

   public DBMSSQLAuthenticatorDelegateException(Throwable nested) {
      super(nested);
   }

   public DBMSSQLAuthenticatorDelegateException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
