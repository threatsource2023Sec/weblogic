package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.StatusDetail;

public class StatusDetailBuilder extends AbstractSAMLObjectBuilder {
   public StatusDetail buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "StatusDetail", "saml2p");
   }

   public StatusDetail buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusDetailImpl(namespaceURI, localName, namespacePrefix);
   }
}
