package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Audience;

public class AudienceBuilder extends AbstractSAMLObjectBuilder {
   public Audience buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Audience", "saml2");
   }

   public Audience buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AudienceImpl(namespaceURI, localName, namespacePrefix);
   }
}
