package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.IDPEntry;

public class IDPEntryBuilder extends AbstractSAMLObjectBuilder {
   public IDPEntry buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "IDPEntry", "saml2p");
   }

   public IDPEntry buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new IDPEntryImpl(namespaceURI, localName, namespacePrefix);
   }
}
