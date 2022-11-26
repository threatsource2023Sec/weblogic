package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NewID;

public class NewIDBuilder extends AbstractSAMLObjectBuilder {
   public NewID buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "NewID", "saml2p");
   }

   public NewID buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NewIDImpl(namespaceURI, localName, namespacePrefix);
   }
}
