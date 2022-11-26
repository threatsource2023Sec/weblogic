package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NameID;

public class NameIDBuilder extends AbstractSAMLObjectBuilder {
   public NameID buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "NameID", "saml2");
   }

   public NameID buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NameIDImpl(namespaceURI, localName, namespacePrefix);
   }
}
