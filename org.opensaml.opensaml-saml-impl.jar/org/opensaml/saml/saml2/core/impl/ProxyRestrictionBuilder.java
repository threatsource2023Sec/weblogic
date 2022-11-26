package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.ProxyRestriction;

public class ProxyRestrictionBuilder extends AbstractSAMLObjectBuilder {
   public ProxyRestriction buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "ProxyRestriction", "saml2");
   }

   public ProxyRestriction buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ProxyRestrictionImpl(namespaceURI, localName, namespacePrefix);
   }
}
