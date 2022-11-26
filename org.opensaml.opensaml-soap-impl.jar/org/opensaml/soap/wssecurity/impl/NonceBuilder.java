package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Nonce;

public class NonceBuilder extends AbstractWSSecurityObjectBuilder {
   public Nonce buildObject() {
      return (Nonce)this.buildObject(Nonce.ELEMENT_NAME);
   }

   public Nonce buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NonceImpl(namespaceURI, localName, namespacePrefix);
   }
}
