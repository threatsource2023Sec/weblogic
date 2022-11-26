package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Conditions;

public class ConditionsBuilder extends AbstractSAMLObjectBuilder {
   public Conditions buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions", "saml1");
   }

   public Conditions buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ConditionsImpl(namespaceURI, localName, namespacePrefix);
   }
}
