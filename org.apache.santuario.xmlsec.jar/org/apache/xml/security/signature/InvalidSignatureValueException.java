package org.apache.xml.security.signature;

public class InvalidSignatureValueException extends XMLSignatureException {
   private static final long serialVersionUID = 1L;

   public InvalidSignatureValueException() {
   }

   public InvalidSignatureValueException(String msgID) {
      super(msgID);
   }

   public InvalidSignatureValueException(String msgID, Object[] exArgs) {
      super(msgID, exArgs);
   }

   public InvalidSignatureValueException(Exception originalException, String msgID) {
      super(originalException, msgID);
   }

   /** @deprecated */
   @Deprecated
   public InvalidSignatureValueException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public InvalidSignatureValueException(Exception originalException, String msgID, Object[] exArgs) {
      super(originalException, msgID, exArgs);
   }

   /** @deprecated */
   @Deprecated
   public InvalidSignatureValueException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }
}
