package org.opensaml.saml.ext.saml2mdreqinit.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdreqinit.RequestInitiator;

public class RequestInitiatorBuilder extends AbstractSAMLObjectBuilder {
   public RequestInitiator buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:profiles:SSO:request-init", "RequestInitiator", "init");
   }

   public RequestInitiator buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestInitiatorImpl(namespaceURI, localName, namespacePrefix);
   }
}
