package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.SingleLogoutService;

public class SingleLogoutServiceBuilder extends AbstractSAMLObjectBuilder {
   public SingleLogoutService buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "SingleLogoutService", "md");
   }

   public SingleLogoutService buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SingleLogoutServiceImpl(namespaceURI, localName, namespacePrefix);
   }
}
