package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Status;

public class StatusBuilder extends AbstractSAMLObjectBuilder {
   public Status buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Status", "saml2p");
   }

   public Status buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusImpl(namespaceURI, localName, namespacePrefix);
   }
}
