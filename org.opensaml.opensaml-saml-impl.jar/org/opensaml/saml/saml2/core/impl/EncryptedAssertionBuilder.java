package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.EncryptedAssertion;

public class EncryptedAssertionBuilder extends AbstractSAMLObjectBuilder {
   public EncryptedAssertion buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAssertion", "saml2");
   }

   public EncryptedAssertion buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptedAssertionImpl(namespaceURI, localName, namespacePrefix);
   }
}
