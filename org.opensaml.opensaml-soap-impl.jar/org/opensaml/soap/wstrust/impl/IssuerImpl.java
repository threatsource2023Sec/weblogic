package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wsaddressing.impl.EndpointReferenceTypeImpl;
import org.opensaml.soap.wstrust.Issuer;

public class IssuerImpl extends EndpointReferenceTypeImpl implements Issuer {
   public IssuerImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
