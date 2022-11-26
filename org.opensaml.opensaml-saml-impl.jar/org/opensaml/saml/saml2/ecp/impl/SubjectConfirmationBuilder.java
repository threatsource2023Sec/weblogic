package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.ecp.SubjectConfirmation;

public class SubjectConfirmationBuilder extends AbstractSAMLObjectBuilder {
   public SubjectConfirmation buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "SubjectConfirmation", "ecp");
   }

   public SubjectConfirmation buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectConfirmationImpl(namespaceURI, localName, namespacePrefix);
   }
}
