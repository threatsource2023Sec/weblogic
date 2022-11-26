package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.EndpointReference;

public class EndpointReferenceBuilder extends AbstractWSAddressingObjectBuilder {
   public EndpointReference buildObject() {
      return (EndpointReference)this.buildObject(EndpointReference.ELEMENT_NAME);
   }

   public EndpointReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EndpointReferenceImpl(namespaceURI, localName, namespacePrefix);
   }
}
