package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.KeyWrapAlgorithm;

public class KeyWrapAlgorithmBuilder extends AbstractWSTrustObjectBuilder {
   public KeyWrapAlgorithm buildObject() {
      return (KeyWrapAlgorithm)this.buildObject(KeyWrapAlgorithm.ELEMENT_NAME);
   }

   public KeyWrapAlgorithm buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeyWrapAlgorithmImpl(namespaceURI, localName, namespacePrefix);
   }
}
