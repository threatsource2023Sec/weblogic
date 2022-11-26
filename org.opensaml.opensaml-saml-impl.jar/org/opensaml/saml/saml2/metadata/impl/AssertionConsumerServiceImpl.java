package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.saml2.metadata.AssertionConsumerService;

public class AssertionConsumerServiceImpl extends IndexedEndpointImpl implements AssertionConsumerService {
   protected AssertionConsumerServiceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
