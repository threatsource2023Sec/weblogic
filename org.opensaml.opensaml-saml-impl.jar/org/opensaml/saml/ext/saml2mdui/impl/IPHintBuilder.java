package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.IPHint;

public class IPHintBuilder extends AbstractSAMLObjectBuilder {
   public IPHint buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "IPHint", "mdui");
   }

   public IPHint buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new IPHintImpl(namespaceURI, localName, namespacePrefix);
   }
}
