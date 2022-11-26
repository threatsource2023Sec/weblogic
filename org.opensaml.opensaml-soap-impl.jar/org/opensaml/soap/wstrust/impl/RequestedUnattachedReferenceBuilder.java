package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestedUnattachedReference;

public class RequestedUnattachedReferenceBuilder extends AbstractWSTrustObjectBuilder {
   public RequestedUnattachedReference buildObject() {
      return (RequestedUnattachedReference)this.buildObject(RequestedUnattachedReference.ELEMENT_NAME);
   }

   public RequestedUnattachedReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestedUnattachedReferenceImpl(namespaceURI, localName, namespacePrefix);
   }
}
