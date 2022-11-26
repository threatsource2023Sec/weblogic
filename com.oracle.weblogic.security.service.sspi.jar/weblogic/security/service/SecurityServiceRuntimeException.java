package weblogic.security.service;

import weblogic.utils.NestedRuntimeException;

public class SecurityServiceRuntimeException extends NestedRuntimeException {
   public SecurityServiceRuntimeException() {
   }

   public SecurityServiceRuntimeException(String msg) {
      super(msg);
   }

   public SecurityServiceRuntimeException(Throwable nested) {
      super(nested);
   }

   public SecurityServiceRuntimeException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
