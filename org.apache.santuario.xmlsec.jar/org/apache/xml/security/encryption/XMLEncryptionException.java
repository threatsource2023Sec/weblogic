package org.apache.xml.security.encryption;

import org.apache.xml.security.exceptions.XMLSecurityException;

public class XMLEncryptionException extends XMLSecurityException {
   private static final long serialVersionUID = 1L;

   public XMLEncryptionException() {
   }

   public XMLEncryptionException(Exception ex) {
      super(ex);
   }

   public XMLEncryptionException(String msgID) {
      super(msgID);
   }

   public XMLEncryptionException(String msgID, Object... exArgs) {
      super(msgID, exArgs);
   }

   public XMLEncryptionException(Exception originalException, String msgID) {
      super(originalException, msgID);
   }

   /** @deprecated */
   @Deprecated
   public XMLEncryptionException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public XMLEncryptionException(Exception originalException, String msgID, Object[] exArgs) {
      super(originalException, msgID, exArgs);
   }

   /** @deprecated */
   @Deprecated
   public XMLEncryptionException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }
}
