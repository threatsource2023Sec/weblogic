package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestedProofToken;

public class RequestedProofTokenBuilder extends AbstractWSTrustObjectBuilder {
   public RequestedProofToken buildObject() {
      return (RequestedProofToken)this.buildObject(RequestedProofToken.ELEMENT_NAME);
   }

   public RequestedProofToken buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestedProofTokenImpl(namespaceURI, localName, namespacePrefix);
   }
}
