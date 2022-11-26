package org.apache.xml.security.transforms;

import org.apache.xml.security.exceptions.XMLSecurityException;

public class InvalidTransformException extends XMLSecurityException {
   private static final long serialVersionUID = 1L;

   public InvalidTransformException() {
   }

   public InvalidTransformException(String msgId) {
      super(msgId);
   }

   public InvalidTransformException(String msgId, Object[] exArgs) {
      super(msgId, exArgs);
   }

   public InvalidTransformException(Exception originalException, String msgId) {
      super(originalException, msgId);
   }

   /** @deprecated */
   @Deprecated
   public InvalidTransformException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public InvalidTransformException(Exception originalException, String msgId, Object[] exArgs) {
      super(originalException, msgId, exArgs);
   }

   /** @deprecated */
   @Deprecated
   public InvalidTransformException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }
}
