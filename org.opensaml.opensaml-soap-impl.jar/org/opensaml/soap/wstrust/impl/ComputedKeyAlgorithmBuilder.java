package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.ComputedKeyAlgorithm;

public class ComputedKeyAlgorithmBuilder extends AbstractWSTrustObjectBuilder {
   public ComputedKeyAlgorithm buildObject() {
      return (ComputedKeyAlgorithm)this.buildObject(ComputedKeyAlgorithm.ELEMENT_NAME);
   }

   public ComputedKeyAlgorithm buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ComputedKeyAlgorithmImpl(namespaceURI, localName, namespacePrefix);
   }
}
