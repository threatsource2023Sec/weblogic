package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.OrganizationName;

public class OrganizationNameBuilder extends AbstractSAMLObjectBuilder {
   public OrganizationName buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "OrganizationName", "md");
   }

   public OrganizationName buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new OrganizationNameImpl(namespaceURI, localName, namespacePrefix);
   }
}
