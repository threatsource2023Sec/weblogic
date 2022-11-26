package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.Keywords;

public class KeywordsBuilder extends AbstractSAMLObjectBuilder {
   public Keywords buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "Keywords", "mdui");
   }

   public Keywords buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeywordsImpl(namespaceURI, localName, namespacePrefix);
   }
}
