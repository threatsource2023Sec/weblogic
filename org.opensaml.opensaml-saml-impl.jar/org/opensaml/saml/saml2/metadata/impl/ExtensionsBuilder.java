package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.Extensions;

public class ExtensionsBuilder extends AbstractSAMLObjectBuilder {
   public Extensions buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "Extensions", "md");
   }

   public Extensions buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ExtensionsImpl(namespaceURI, localName, namespacePrefix);
   }
}
