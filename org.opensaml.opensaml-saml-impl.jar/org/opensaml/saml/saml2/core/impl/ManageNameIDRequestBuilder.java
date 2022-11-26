package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.ManageNameIDRequest;

public class ManageNameIDRequestBuilder extends AbstractSAMLObjectBuilder {
   public ManageNameIDRequest buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDRequest", "saml2p");
   }

   public ManageNameIDRequest buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ManageNameIDRequestImpl(namespaceURI, localName, namespacePrefix);
   }
}
