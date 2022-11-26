package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AssertionArtifact;

public class AssertionArtifactBuilder extends AbstractSAMLObjectBuilder {
   public AssertionArtifact buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "AssertionArtifact", "saml1p");
   }

   public AssertionArtifact buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AssertionArtifactImpl(namespaceURI, localName, namespacePrefix);
   }
}
