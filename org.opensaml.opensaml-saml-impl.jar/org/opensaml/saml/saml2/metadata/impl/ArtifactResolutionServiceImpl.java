package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;

public class ArtifactResolutionServiceImpl extends IndexedEndpointImpl implements ArtifactResolutionService {
   protected ArtifactResolutionServiceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
