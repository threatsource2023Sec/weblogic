package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.Company;

public class CompanyBuilder extends AbstractSAMLObjectBuilder {
   public Company buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "Company", "md");
   }

   public Company buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CompanyImpl(namespaceURI, localName, namespacePrefix);
   }
}
