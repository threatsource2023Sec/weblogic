package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.ArtifactResponse;

public class ArtifactResponseBuilder extends AbstractSAMLObjectBuilder {
   public ArtifactResponse buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResponse", "saml2p");
   }

   public ArtifactResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ArtifactResponseImpl(namespaceURI, localName, namespacePrefix);
   }
}
