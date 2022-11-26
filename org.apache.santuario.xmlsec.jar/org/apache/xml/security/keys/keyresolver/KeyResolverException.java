package org.apache.xml.security.keys.keyresolver;

import org.apache.xml.security.exceptions.XMLSecurityException;

public class KeyResolverException extends XMLSecurityException {
   private static final long serialVersionUID = 1L;

   public KeyResolverException() {
   }

   public KeyResolverException(Exception ex) {
      super(ex);
   }

   public KeyResolverException(String msgID) {
      super(msgID);
   }

   public KeyResolverException(String msgID, Object[] exArgs) {
      super(msgID, exArgs);
   }

   public KeyResolverException(Exception originalException, String msgID) {
      super(originalException, msgID);
   }

   /** @deprecated */
   @Deprecated
   public KeyResolverException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }

   public KeyResolverException(Exception originalException, String msgID, Object[] exArgs) {
      super(originalException, msgID, exArgs);
   }

   /** @deprecated */
   @Deprecated
   public KeyResolverException(String msgID, Object[] exArgs, Exception originalException) {
      this(originalException, msgID, exArgs);
   }
}
