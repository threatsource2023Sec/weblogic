package org.apache.xml.security.c14n;

import org.apache.xml.security.exceptions.XMLSecurityException;

public class CanonicalizationException extends XMLSecurityException {
   private static final long serialVersionUID = 1L;

   public CanonicalizationException() {
   }

   public CanonicalizationException(Exception ex) {
      super(ex);
   }

   public CanonicalizationException(String msgID) {
      super(msgID);
   }

   public CanonicalizationException(String msgID, Object[] exArgs) {
      super(msgID, exArgs);
   }

   public CanonicalizationException(Exception originalException, String msgID) {
      super(originalException, msgID);
   }

   /** @deprecated */
   @Deprecated
   public CanonicalizationException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public CanonicalizationException(Exception originalException, String msgID, Object[] exArgs) {
      super(originalException, msgID, exArgs);
   }

   /** @deprecated */
   @Deprecated
   public CanonicalizationException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }
}
