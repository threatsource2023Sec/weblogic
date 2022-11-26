package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;

public class IDPSSODescriptorBuilder extends AbstractSAMLObjectBuilder {
   public IDPSSODescriptor buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "IDPSSODescriptor", "md");
   }

   public IDPSSODescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new IDPSSODescriptorImpl(namespaceURI, localName, namespacePrefix);
   }
}
