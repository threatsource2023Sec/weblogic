package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.PrivacyStatementURL;

public class PrivacyStatementURLBuilder extends AbstractSAMLObjectBuilder {
   public PrivacyStatementURL buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "PrivacyStatementURL", "mdui");
   }

   public PrivacyStatementURL buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PrivacyStatementURLImpl(namespaceURI, localName, namespacePrefix);
   }
}
