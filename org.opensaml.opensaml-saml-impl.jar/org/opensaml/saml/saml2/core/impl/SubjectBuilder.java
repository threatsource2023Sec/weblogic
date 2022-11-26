package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Subject;

public class SubjectBuilder extends AbstractSAMLObjectBuilder {
   public Subject buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Subject", "saml2");
   }

   public Subject buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectImpl(namespaceURI, localName, namespacePrefix);
   }
}
