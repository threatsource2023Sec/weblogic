package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Artifact;

public class ArtifactBuilder extends AbstractSAMLObjectBuilder {
   public Artifact buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Artifact", "saml2p");
   }

   public Artifact buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ArtifactImpl(namespaceURI, localName, namespacePrefix);
   }
}
