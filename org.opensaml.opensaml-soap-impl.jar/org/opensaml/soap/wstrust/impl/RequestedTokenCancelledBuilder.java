package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestedTokenCancelled;

public class RequestedTokenCancelledBuilder extends AbstractWSTrustObjectBuilder {
   public RequestedTokenCancelled buildObject() {
      return (RequestedTokenCancelled)this.buildObject(RequestedTokenCancelled.ELEMENT_NAME);
   }

   public RequestedTokenCancelled buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestedTokenCancelledImpl(namespaceURI, localName, namespacePrefix);
   }
}
