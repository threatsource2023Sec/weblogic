package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.GivenName;

public class GivenNameBuilder extends AbstractSAMLObjectBuilder {
   public GivenName buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "GivenName", "md");
   }

   public GivenName buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new GivenNameImpl(namespaceURI, localName, namespacePrefix);
   }
}
