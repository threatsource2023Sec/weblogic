package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RequestedAttachedReference;

public class RequestedAttachedReferenceImpl extends RequestedReferenceTypeImpl implements RequestedAttachedReference {
   public RequestedAttachedReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
