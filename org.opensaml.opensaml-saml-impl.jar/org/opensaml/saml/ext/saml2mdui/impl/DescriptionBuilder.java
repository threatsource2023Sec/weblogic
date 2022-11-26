package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.Description;

public class DescriptionBuilder extends AbstractSAMLObjectBuilder {
   public Description buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "Description", "mdui");
   }

   public Description buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DescriptionImpl(namespaceURI, localName, namespacePrefix);
   }
}
