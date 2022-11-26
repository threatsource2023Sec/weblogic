package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestedAttachedReference;

public class RequestedAttachedReferenceBuilder extends AbstractWSTrustObjectBuilder {
   public RequestedAttachedReference buildObject() {
      return (RequestedAttachedReference)this.buildObject(RequestedAttachedReference.ELEMENT_NAME);
   }

   public RequestedAttachedReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestedAttachedReferenceImpl(namespaceURI, localName, namespacePrefix);
   }
}
