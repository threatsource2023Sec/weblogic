package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.EncryptedID;

public class EncryptedIDBuilder extends AbstractSAMLObjectBuilder {
   public EncryptedID buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedID", "saml2");
   }

   public EncryptedID buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptedIDImpl(namespaceURI, localName, namespacePrefix);
   }
}
