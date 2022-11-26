package weblogic.security.internal.encryption;

import weblogic.utils.NestedRuntimeException;

public class EncryptionServiceException extends NestedRuntimeException {
   public EncryptionServiceException() {
   }

   public EncryptionServiceException(String msg) {
      super(msg);
   }

   public EncryptionServiceException(Throwable nested) {
      super(nested);
   }

   public EncryptionServiceException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
