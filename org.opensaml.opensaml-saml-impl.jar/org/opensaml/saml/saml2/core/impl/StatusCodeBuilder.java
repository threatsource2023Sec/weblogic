package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.StatusCode;

public class StatusCodeBuilder extends AbstractSAMLObjectBuilder {
   public StatusCode buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "StatusCode", "saml2p");
   }

   public StatusCode buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusCodeImpl(namespaceURI, localName, namespacePrefix);
   }
}
