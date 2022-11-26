package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.SignatureAlgorithm;

public class SignatureAlgorithmBuilder extends AbstractWSTrustObjectBuilder {
   public SignatureAlgorithm buildObject() {
      return (SignatureAlgorithm)this.buildObject(SignatureAlgorithm.ELEMENT_NAME);
   }

   public SignatureAlgorithm buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SignatureAlgorithmImpl(namespaceURI, localName, namespacePrefix);
   }
}
