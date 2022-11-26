package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.FaultTo;

public class FaultToImpl extends EndpointReferenceTypeImpl implements FaultTo {
   public FaultToImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
