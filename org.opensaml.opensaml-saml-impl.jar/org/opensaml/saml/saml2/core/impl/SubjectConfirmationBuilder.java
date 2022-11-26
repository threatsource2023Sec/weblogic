package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.SubjectConfirmation;

public class SubjectConfirmationBuilder extends AbstractSAMLObjectBuilder {
   public SubjectConfirmation buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmation", "saml2");
   }

   public SubjectConfirmation buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectConfirmationImpl(namespaceURI, localName, namespacePrefix);
   }
}
