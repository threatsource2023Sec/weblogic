package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.UIInfo;

public class UIInfoBuilder extends AbstractSAMLObjectBuilder {
   public UIInfo buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "UIInfo", "mdui");
   }

   public UIInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new UIInfoImpl(namespaceURI, localName, namespacePrefix);
   }
}
