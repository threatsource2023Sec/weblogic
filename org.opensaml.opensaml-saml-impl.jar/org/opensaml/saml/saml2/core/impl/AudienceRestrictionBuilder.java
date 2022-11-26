package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AudienceRestriction;

public class AudienceRestrictionBuilder extends AbstractSAMLObjectBuilder {
   public AudienceRestriction buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceRestriction", "saml2");
   }

   public AudienceRestriction buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AudienceRestrictionImpl(namespaceURI, localName, namespacePrefix);
   }
}
