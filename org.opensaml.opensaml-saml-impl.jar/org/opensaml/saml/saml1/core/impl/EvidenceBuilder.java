package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Evidence;

public class EvidenceBuilder extends AbstractSAMLObjectBuilder {
   public Evidence buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "Evidence", "saml1");
   }

   public Evidence buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EvidenceImpl(namespaceURI, localName, namespacePrefix);
   }
}
