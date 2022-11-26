package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AttributeAuthorityDescriptor;

public class AttributeAuthorityDescriptorBuilder extends AbstractSAMLObjectBuilder {
   public AttributeAuthorityDescriptor buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeAuthorityDescriptor", "md");
   }

   public AttributeAuthorityDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeAuthorityDescriptorImpl(namespaceURI, localName, namespacePrefix);
   }
}
