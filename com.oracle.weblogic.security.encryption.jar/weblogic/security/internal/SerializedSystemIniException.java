package weblogic.security.internal;

import weblogic.utils.NestedRuntimeException;

class SerializedSystemIniException extends NestedRuntimeException {
   public SerializedSystemIniException() {
   }

   public SerializedSystemIniException(String msg) {
      super(msg);
   }

   public SerializedSystemIniException(Throwable nested) {
      super(nested);
   }

   public SerializedSystemIniException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
