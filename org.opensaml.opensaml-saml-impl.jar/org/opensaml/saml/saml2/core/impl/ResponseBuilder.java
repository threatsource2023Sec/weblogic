package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Response;

public class ResponseBuilder extends AbstractSAMLObjectBuilder {
   public Response buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Response", "saml2p");
   }

   public Response buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ResponseImpl(namespaceURI, localName, namespacePrefix);
   }
}
