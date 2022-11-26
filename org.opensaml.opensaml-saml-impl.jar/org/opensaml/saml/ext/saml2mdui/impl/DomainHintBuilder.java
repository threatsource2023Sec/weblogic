package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.DomainHint;

public class DomainHintBuilder extends AbstractSAMLObjectBuilder {
   public DomainHint buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "DomainHint", "mdui");
   }

   public DomainHint buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DomainHintImpl(namespaceURI, localName, namespacePrefix);
   }
}
