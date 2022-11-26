package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.ecp.RequestAuthenticated;

public class RequestAuthenticatedBuilder extends AbstractSAMLObjectBuilder {
   public RequestAuthenticated buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RequestAuthenticated", "ecp");
   }

   public RequestAuthenticated buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestAuthenticatedImpl(namespaceURI, localName, namespacePrefix);
   }
}
