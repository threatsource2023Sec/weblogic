package org.apache.xml.security.signature;

public class InvalidDigestValueException extends XMLSignatureException {
   private static final long serialVersionUID = 1L;

   public InvalidDigestValueException() {
   }

   public InvalidDigestValueException(String msgID) {
      super(msgID);
   }

   public InvalidDigestValueException(String msgID, Object[] exArgs) {
      super(msgID, exArgs);
   }

   public InvalidDigestValueException(Exception originalException, String msgID) {
      super(originalException, msgID);
   }

   /** @deprecated */
   @Deprecated
   public InvalidDigestValueException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public InvalidDigestValueException(Exception originalException, String msgID, Object[] exArgs) {
      super(originalException, msgID, exArgs);
   }

   /** @deprecated */
   @Deprecated
   public InvalidDigestValueException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }
}
