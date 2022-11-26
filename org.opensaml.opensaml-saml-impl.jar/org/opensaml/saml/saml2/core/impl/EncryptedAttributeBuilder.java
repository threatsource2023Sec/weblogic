package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.EncryptedAttribute;

public class EncryptedAttributeBuilder extends AbstractSAMLObjectBuilder {
   public EncryptedAttribute buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAttribute", "saml2");
   }

   public EncryptedAttribute buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptedAttributeImpl(namespaceURI, localName, namespacePrefix);
   }
}
