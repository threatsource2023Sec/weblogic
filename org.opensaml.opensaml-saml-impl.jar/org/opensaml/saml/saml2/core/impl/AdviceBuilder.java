package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Advice;

public class AdviceBuilder extends AbstractSAMLObjectBuilder {
   public Advice buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Advice", "saml2");
   }

   public Advice buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AdviceImpl(namespaceURI, localName, namespacePrefix);
   }
}
