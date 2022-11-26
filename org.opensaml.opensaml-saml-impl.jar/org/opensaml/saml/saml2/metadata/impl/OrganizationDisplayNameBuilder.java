package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.OrganizationDisplayName;

public class OrganizationDisplayNameBuilder extends AbstractSAMLObjectBuilder {
   public OrganizationDisplayName buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "OrganizationDisplayName", "md");
   }

   public OrganizationDisplayName buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new OrganizationDisplayNameImpl(namespaceURI, localName, namespacePrefix);
   }
}
