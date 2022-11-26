package org.apache.xml.security.stax.ext;

import org.apache.xml.security.exceptions.XMLSecurityException;

public class XMLSecurityConfigurationException extends XMLSecurityException {
   private static final long serialVersionUID = -214211575738383300L;

   public XMLSecurityConfigurationException(Exception originalException) {
      super(originalException);
   }

   public XMLSecurityConfigurationException(String msgID) {
      super(msgID);
   }

   public XMLSecurityConfigurationException(String msgID, Object... exArgs) {
      super(msgID, exArgs);
   }

   public XMLSecurityConfigurationException(Exception originalException, String msgID) {
      super(originalException, msgID);
   }

   /** @deprecated */
   @Deprecated
   public XMLSecurityConfigurationException(String msgID, Exception originalException) {
      this(originalException, msgID);
   }
}
