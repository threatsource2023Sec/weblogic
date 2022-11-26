package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.RespondWith;

public class RespondWithBuilder extends AbstractSAMLObjectBuilder {
   public RespondWith buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith", "saml1p");
   }

   public RespondWith buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RespondWithImpl(namespaceURI, localName, namespacePrefix);
   }
}
