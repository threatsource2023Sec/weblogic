package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.PDPDescriptor;

public class PDPDescriptorBuilder extends AbstractSAMLObjectBuilder {
   public PDPDescriptor buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "PDPDescriptor", "md");
   }

   public PDPDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PDPDescriptorImpl(namespaceURI, localName, namespacePrefix);
   }
}
