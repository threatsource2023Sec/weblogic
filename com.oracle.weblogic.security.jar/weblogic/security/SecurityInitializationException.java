package weblogic.security;

import weblogic.utils.NestedRuntimeException;

public class SecurityInitializationException extends NestedRuntimeException {
   public SecurityInitializationException() {
   }

   public SecurityInitializationException(String msg) {
      super(msg);
   }

   public SecurityInitializationException(Throwable nested) {
      super(nested);
   }

   public SecurityInitializationException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
