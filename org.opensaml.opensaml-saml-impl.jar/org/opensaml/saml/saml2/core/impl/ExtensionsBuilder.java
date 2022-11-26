package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Extensions;

public class ExtensionsBuilder extends AbstractSAMLObjectBuilder {
   public Extensions buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Extensions", "saml2p");
   }

   public Extensions buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ExtensionsImpl(namespaceURI, localName, namespacePrefix);
   }
}
