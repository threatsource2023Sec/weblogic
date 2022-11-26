package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.SubjectLocality;

public class SubjectLocalityBuilder extends AbstractSAMLObjectBuilder {
   public SubjectLocality buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality", "saml1");
   }

   public SubjectLocality buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SubjectLocalityImpl(namespaceURI, localName, namespacePrefix);
   }
}
