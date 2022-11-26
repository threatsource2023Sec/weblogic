package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Encryption;

public class EncryptionBuilder extends AbstractWSTrustObjectBuilder {
   public Encryption buildObject() {
      return (Encryption)this.buildObject(Encryption.ELEMENT_NAME);
   }

   public Encryption buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptionImpl(namespaceURI, localName, namespacePrefix);
   }
}
