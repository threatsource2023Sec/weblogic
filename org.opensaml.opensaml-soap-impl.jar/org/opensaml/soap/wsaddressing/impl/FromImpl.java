package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.From;

public class FromImpl extends EndpointReferenceTypeImpl implements From {
   public FromImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
