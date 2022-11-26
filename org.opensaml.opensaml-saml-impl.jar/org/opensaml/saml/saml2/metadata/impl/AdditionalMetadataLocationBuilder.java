package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AdditionalMetadataLocation;

public class AdditionalMetadataLocationBuilder extends AbstractSAMLObjectBuilder {
   public AdditionalMetadataLocation buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AdditionalMetadataLocation", "md");
   }

   public AdditionalMetadataLocation buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AdditionalMetadataLocationImpl(namespaceURI, localName, namespacePrefix);
   }
}
