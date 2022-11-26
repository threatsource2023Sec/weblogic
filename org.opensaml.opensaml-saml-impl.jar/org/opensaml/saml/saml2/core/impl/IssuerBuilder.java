package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Issuer;

public class IssuerBuilder extends AbstractSAMLObjectBuilder {
   public Issuer buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer", "saml2");
   }

   public Issuer buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new IssuerImpl(namespaceURI, localName, namespacePrefix);
   }
}
