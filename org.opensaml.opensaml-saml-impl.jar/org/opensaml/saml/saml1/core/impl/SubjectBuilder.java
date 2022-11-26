package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Subject;

public class SubjectBuilder extends AbstractSAMLObjectBuilder {
   public Subject buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "Subject", "saml1");
   }

   public Subject buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectImpl(namespaceURI, localName, namespacePrefix);
   }
}
