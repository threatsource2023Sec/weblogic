package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestedUnattachedReference;

public class RequestedUnattachedReferenceImpl extends RequestedReferenceTypeImpl implements RequestedUnattachedReference {
   public RequestedUnattachedReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
