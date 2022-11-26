package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;

public class SingleSignOnServiceBuilder extends AbstractSAMLObjectBuilder {
   public SingleSignOnService buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "SingleSignOnService", "md");
   }

   public SingleSignOnService buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SingleSignOnServiceImpl(namespaceURI, localName, namespacePrefix);
   }
}
