package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.OneTimeUse;

public class OneTimeUseBuilder extends AbstractSAMLObjectBuilder {
   public OneTimeUse buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "OneTimeUse", "saml2");
   }

   public OneTimeUse buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new OneTimeUseImpl(namespaceURI, localName, namespacePrefix);
   }
}
