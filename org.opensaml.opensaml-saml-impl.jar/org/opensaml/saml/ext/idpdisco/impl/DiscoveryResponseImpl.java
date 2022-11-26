package org.opensaml.saml.ext.idpdisco.impl;

import org.opensaml.saml.ext.idpdisco.DiscoveryResponse;
import org.opensaml.saml.saml2.metadata.impl.IndexedEndpointImpl;

public class DiscoveryResponseImpl extends IndexedEndpointImpl implements DiscoveryResponse {
   protected DiscoveryResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
