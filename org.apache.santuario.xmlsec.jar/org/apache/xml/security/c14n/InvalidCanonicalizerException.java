package org.apache.xml.security.c14n;

import org.apache.xml.security.exceptions.XMLSecurityException;

public class InvalidCanonicalizerException extends XMLSecurityException {
   private static final long serialVersionUID = 1L;

   public InvalidCanonicalizerException() {
   }

   public InvalidCanonicalizerException(String msgID) {
      super(msgID);
   }

   public InvalidCanonicalizerException(String msgID, Object[] exArgs) {
      super(msgID, exArgs);
   }

   public InvalidCanonicalizerException(Exception originalException, String msgID) {
      super(originalException, msgID);
   }

   /** @deprecated */
   @Deprecated
   public InvalidCanonicalizerException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public InvalidCanonicalizerException(Exception originalException, String msgID, Object[] exArgs) {
      super(originalException, msgID, exArgs);
   }

   /** @deprecated */
   @Deprecated
   public InvalidCanonicalizerException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }
}
