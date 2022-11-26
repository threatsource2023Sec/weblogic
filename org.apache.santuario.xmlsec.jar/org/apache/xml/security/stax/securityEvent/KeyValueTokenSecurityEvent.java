package org.apache.xml.security.stax.securityEvent;

import org.apache.xml.security.exceptions.XMLSecurityException;

public class KeyValueTokenSecurityEvent extends TokenSecurityEvent {
   public KeyValueTokenSecurityEvent() {
      super(SecurityEventConstants.KeyValueToken);
   }

   public KeyValueTokenType getKeyValueTokenType() {
      try {
         String algo = this.getSecurityToken().getPublicKey().getAlgorithm();
         return KeyValueTokenSecurityEvent.KeyValueTokenType.valueOf(algo);
      } catch (IllegalArgumentException var2) {
         return null;
      } catch (XMLSecurityException var3) {
         return null;
      }
   }

   public static enum KeyValueTokenType {
      RSA,
      DSA,
      EC;
   }
}
