package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.CanonicalizationAlgorithm;

public class CanonicalizationAlgorithmBuilder extends AbstractWSTrustObjectBuilder {
   public CanonicalizationAlgorithm buildObject() {
      return (CanonicalizationAlgorithm)this.buildObject(CanonicalizationAlgorithm.ELEMENT_NAME);
   }

   public CanonicalizationAlgorithm buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CanonicalizationAlgorithmImpl(namespaceURI, localName, namespacePrefix);
   }
}
