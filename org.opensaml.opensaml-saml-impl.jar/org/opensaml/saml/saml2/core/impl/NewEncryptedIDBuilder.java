package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NewEncryptedID;

public class NewEncryptedIDBuilder extends AbstractSAMLObjectBuilder {
   public NewEncryptedID buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "NewEncryptedID", "saml2p");
   }

   public NewEncryptedID buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NewEncryptedIDImpl(namespaceURI, localName, namespacePrefix);
   }
}
