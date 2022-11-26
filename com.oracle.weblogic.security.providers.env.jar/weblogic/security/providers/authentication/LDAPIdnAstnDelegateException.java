package weblogic.security.providers.authentication;

import weblogic.utils.NestedRuntimeException;

public final class LDAPIdnAstnDelegateException extends NestedRuntimeException {
   public LDAPIdnAstnDelegateException() {
   }

   public LDAPIdnAstnDelegateException(String msg) {
      super(msg);
   }

   public LDAPIdnAstnDelegateException(Throwable nested) {
      super(nested);
   }

   public LDAPIdnAstnDelegateException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
