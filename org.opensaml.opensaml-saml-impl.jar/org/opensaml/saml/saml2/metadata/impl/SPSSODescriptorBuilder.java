package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;

public class SPSSODescriptorBuilder extends AbstractSAMLObjectBuilder {
   public SPSSODescriptor buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "SPSSODescriptor", "md");
   }

   public SPSSODescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SPSSODescriptorImpl(namespaceURI, localName, namespacePrefix);
   }
}
