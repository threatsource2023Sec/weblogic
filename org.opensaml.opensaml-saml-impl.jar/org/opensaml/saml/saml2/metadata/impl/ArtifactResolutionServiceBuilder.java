package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;

public class ArtifactResolutionServiceBuilder extends AbstractSAMLObjectBuilder {
   public ArtifactResolutionService buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "ArtifactResolutionService", "md");
   }

   public ArtifactResolutionService buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ArtifactResolutionServiceImpl(namespaceURI, localName, namespacePrefix);
   }
}
