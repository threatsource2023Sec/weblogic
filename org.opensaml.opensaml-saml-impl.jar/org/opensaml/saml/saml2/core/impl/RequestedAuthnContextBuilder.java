package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;

public class RequestedAuthnContextBuilder extends AbstractSAMLObjectBuilder {
   public RequestedAuthnContext buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "RequestedAuthnContext", "saml2p");
   }

   public RequestedAuthnContext buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestedAuthnContextImpl(namespaceURI, localName, namespacePrefix);
   }
}
