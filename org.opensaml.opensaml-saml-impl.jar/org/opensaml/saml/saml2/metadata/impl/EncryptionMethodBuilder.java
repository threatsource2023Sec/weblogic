package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.EncryptionMethod;

public class EncryptionMethodBuilder extends AbstractSAMLObjectBuilder implements SAMLObjectBuilder {
   public EncryptionMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptionMethodImpl(namespaceURI, localName, namespacePrefix);
   }

   public EncryptionMethod buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "EncryptionMethod", "md");
   }
}
