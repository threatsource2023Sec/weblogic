package org.apache.xml.security.transforms;

import org.apache.xml.security.exceptions.XMLSecurityException;

public class TransformationException extends XMLSecurityException {
   private static final long serialVersionUID = 1L;

   public TransformationException() {
   }

   public TransformationException(Exception ex) {
      super(ex);
   }

   public TransformationException(String msgID) {
      super(msgID);
   }

   public TransformationException(String msgID, Object[] exArgs) {
      super(msgID, exArgs);
   }

   public TransformationException(Exception originalException, String msgID) {
      super(originalException, msgID);
   }

   /** @deprecated */
   @Deprecated
   public TransformationException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public TransformationException(Exception originalException, String msgID, Object[] exArgs) {
      super(originalException, msgID, exArgs);
   }

   /** @deprecated */
   @Deprecated
   public TransformationException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }
}
