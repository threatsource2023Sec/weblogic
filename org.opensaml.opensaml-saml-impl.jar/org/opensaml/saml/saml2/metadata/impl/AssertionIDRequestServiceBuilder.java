package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;

public class AssertionIDRequestServiceBuilder extends AbstractSAMLObjectBuilder {
   public AssertionIDRequestService buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AssertionIDRequestService", "md");
   }

   public AssertionIDRequestService buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AssertionIDRequestServiceImpl(namespaceURI, localName, namespacePrefix);
   }
}
