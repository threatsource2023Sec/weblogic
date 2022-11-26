package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.InformationURL;

public class InformationURLBuilder extends AbstractSAMLObjectBuilder {
   public InformationURL buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "InformationURL", "mdui");
   }

   public InformationURL buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new InformationURLImpl(namespaceURI, localName, namespacePrefix);
   }
}
