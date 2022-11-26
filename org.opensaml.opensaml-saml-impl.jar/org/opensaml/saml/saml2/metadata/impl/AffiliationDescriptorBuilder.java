package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;

public class AffiliationDescriptorBuilder extends AbstractSAMLObjectBuilder {
   public AffiliationDescriptor buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AffiliationDescriptor", "md");
   }

   public AffiliationDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AffiliationDescriptorImpl(namespaceURI, localName, namespacePrefix);
   }
}
