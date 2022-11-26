package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.TelephoneNumber;

public class TelephoneNumberBuilder extends AbstractSAMLObjectBuilder {
   public TelephoneNumber buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "TelephoneNumber", "md");
   }

   public TelephoneNumber buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new TelephoneNumberImpl(namespaceURI, localName, namespacePrefix);
   }
}
