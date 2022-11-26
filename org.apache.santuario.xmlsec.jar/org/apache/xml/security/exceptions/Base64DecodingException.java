package org.apache.xml.security.exceptions;

public class Base64DecodingException extends XMLSecurityException {
   private static final long serialVersionUID = 1L;

   public Base64DecodingException() {
   }

   public Base64DecodingException(String msgID) {
      super(msgID);
   }

   public Base64DecodingException(String msgID, Object[] exArgs) {
      super(msgID, exArgs);
   }

   public Base64DecodingException(Exception originalException, String msgID) {
      super(originalException, msgID);
   }

   /** @deprecated */
   @Deprecated
   public Base64DecodingException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public Base64DecodingException(Exception originalException, String msgID, Object[] exArgs) {
      super(originalException, msgID, exArgs);
   }

   /** @deprecated */
   @Deprecated
   public Base64DecodingException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }
}
