package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Status;

public class StatusBuilder extends AbstractSAMLObjectBuilder {
   public Status buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "Status", "saml1p");
   }

   public Status buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusImpl(namespaceURI, localName, namespacePrefix);
   }
}
