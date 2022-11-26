package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Advice;

public class AdviceBuilder extends AbstractSAMLObjectBuilder {
   public Advice buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "Advice", "saml1");
   }

   public Advice buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AdviceImpl(namespaceURI, localName, namespacePrefix);
   }
}
