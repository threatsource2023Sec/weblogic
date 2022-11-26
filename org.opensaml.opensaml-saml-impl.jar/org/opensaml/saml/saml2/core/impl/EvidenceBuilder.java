package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Evidence;

public class EvidenceBuilder extends AbstractSAMLObjectBuilder {
   public Evidence buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Evidence", "saml2");
   }

   public Evidence buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EvidenceImpl(namespaceURI, localName, namespacePrefix);
   }
}
