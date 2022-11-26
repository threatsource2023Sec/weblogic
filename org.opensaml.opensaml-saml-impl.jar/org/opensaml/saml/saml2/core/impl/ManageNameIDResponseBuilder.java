package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.ManageNameIDResponse;

public class ManageNameIDResponseBuilder extends AbstractSAMLObjectBuilder {
   public ManageNameIDResponse buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDResponse", "saml2p");
   }

   public ManageNameIDResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ManageNameIDResponseImpl(namespaceURI, localName, namespacePrefix);
   }
}
