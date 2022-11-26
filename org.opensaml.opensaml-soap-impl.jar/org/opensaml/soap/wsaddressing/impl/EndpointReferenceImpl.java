package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.EndpointReference;

public class EndpointReferenceImpl extends EndpointReferenceTypeImpl implements EndpointReference {
   public EndpointReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
