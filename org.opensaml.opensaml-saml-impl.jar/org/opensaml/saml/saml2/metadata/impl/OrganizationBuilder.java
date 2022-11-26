package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.Organization;

public class OrganizationBuilder extends AbstractSAMLObjectBuilder {
   public Organization buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "Organization", "md");
   }

   public Organization buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new OrganizationImpl(namespaceURI, localName, namespacePrefix);
   }
}
