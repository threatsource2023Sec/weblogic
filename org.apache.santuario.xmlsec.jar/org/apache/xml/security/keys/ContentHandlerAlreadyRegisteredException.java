package org.apache.xml.security.keys;

import org.apache.xml.security.exceptions.XMLSecurityException;

public class ContentHandlerAlreadyRegisteredException extends XMLSecurityException {
   private static final long serialVersionUID = 1L;

   public ContentHandlerAlreadyRegisteredException() {
   }

   public ContentHandlerAlreadyRegisteredException(String msgID) {
      super(msgID);
   }

   public ContentHandlerAlreadyRegisteredException(String msgID, Object[] exArgs) {
      super(msgID, exArgs);
   }

   public ContentHandlerAlreadyRegisteredException(Exception originalException, String msgID) {
      super(originalException, msgID);
   }

   /** @deprecated */
   @Deprecated
   public ContentHandlerAlreadyRegisteredException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public ContentHandlerAlreadyRegisteredException(Exception originalException, String msgID, Object[] exArgs) {
      super(originalException, msgID, exArgs);
   }

   /** @deprecated */
   @Deprecated
   public ContentHandlerAlreadyRegisteredException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }
}
