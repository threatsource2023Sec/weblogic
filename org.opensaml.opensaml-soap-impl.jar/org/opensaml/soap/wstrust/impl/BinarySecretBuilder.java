package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.BinarySecret;

public class BinarySecretBuilder extends AbstractWSTrustObjectBuilder {
   public BinarySecret buildObject() {
      return (BinarySecret)this.buildObject(BinarySecret.ELEMENT_NAME);
   }

   public BinarySecret buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new BinarySecretImpl(namespaceURI, localName, namespacePrefix);
   }
}
