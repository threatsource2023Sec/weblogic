package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.SubjectLocality;

public class SubjectLocalityBuilder extends AbstractSAMLObjectBuilder {
   public SubjectLocality buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectLocality", "saml2");
   }

   public SubjectLocality buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectLocalityImpl(namespaceURI, localName, namespacePrefix);
   }
}
