package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.EncryptionAlgorithm;

public class EncryptionAlgorithmBuilder extends AbstractWSTrustObjectBuilder {
   public EncryptionAlgorithm buildObject() {
      return (EncryptionAlgorithm)this.buildObject(EncryptionAlgorithm.ELEMENT_NAME);
   }

   public EncryptionAlgorithm buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptionAlgorithmImpl(namespaceURI, localName, namespacePrefix);
   }
}
