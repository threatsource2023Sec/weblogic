package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Request;

public class RequestBuilder extends AbstractSAMLObjectBuilder {
   public Request buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "Request", "saml1");
   }

   public Request buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestImpl(namespaceURI, localName, namespacePrefix);
   }
}
