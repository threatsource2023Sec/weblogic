package weblogic.security.providers.authentication;

import weblogic.utils.NestedRuntimeException;

public final class LDAPAtnDelegateException extends NestedRuntimeException {
   public LDAPAtnDelegateException() {
   }

   public LDAPAtnDelegateException(String msg) {
      super(msg);
   }

   public LDAPAtnDelegateException(Throwable nested) {
      super(nested);
   }

   public LDAPAtnDelegateException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
