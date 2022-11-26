package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.StatusMessage;

public class StatusMessageBuilder extends AbstractSAMLObjectBuilder {
   public StatusMessage buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "StatusMessage", "saml2p");
   }

   public StatusMessage buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusMessageImpl(namespaceURI, localName, namespacePrefix);
   }
}
