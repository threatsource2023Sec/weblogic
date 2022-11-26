package org.opensaml.saml.ext.samlpthrpty.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.samlpthrpty.RespondTo;

public class RespondToBuilder extends AbstractSAMLObjectBuilder {
   public RespondTo buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:protocol:ext:third-party", "RespondTo", "thrpty");
   }

   public RespondTo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RespondToImpl(namespaceURI, localName, namespacePrefix);
   }
}
