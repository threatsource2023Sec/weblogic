package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.Logo;

public class LogoBuilder extends AbstractSAMLObjectBuilder {
   public Logo buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "Logo", "mdui");
   }

   public Logo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new LogoImpl(namespaceURI, localName, namespacePrefix);
   }
}
