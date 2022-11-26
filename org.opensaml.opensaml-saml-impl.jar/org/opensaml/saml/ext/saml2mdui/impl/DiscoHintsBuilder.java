package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.DiscoHints;

public class DiscoHintsBuilder extends AbstractSAMLObjectBuilder {
   public DiscoHints buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "DiscoHints", "mdui");
   }

   public DiscoHints buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DiscoHintsImpl(namespaceURI, localName, namespacePrefix);
   }
}
