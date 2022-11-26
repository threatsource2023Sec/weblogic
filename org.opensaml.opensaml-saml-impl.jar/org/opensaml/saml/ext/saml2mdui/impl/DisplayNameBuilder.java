package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.DisplayName;

public class DisplayNameBuilder extends AbstractSAMLObjectBuilder {
   public DisplayName buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "DisplayName", "mdui");
   }

   public DisplayName buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DisplayNameImpl(namespaceURI, localName, namespacePrefix);
   }
}
