package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.StatusDetail;

public class StatusDetailBuilder extends AbstractSAMLObjectBuilder {
   public StatusDetail buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "StatusDetail", "saml1p");
   }

   public StatusDetail buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusDetailImpl(namespaceURI, localName, namespacePrefix);
   }
}
