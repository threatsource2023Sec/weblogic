package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.NameIDFormat;

public class NameIDFormatBuilder extends AbstractSAMLObjectBuilder {
   public NameIDFormat buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "NameIDFormat", "md");
   }

   public NameIDFormat buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new NameIDFormatImpl(namespaceURI, localName, namespacePrefix);
   }
}
