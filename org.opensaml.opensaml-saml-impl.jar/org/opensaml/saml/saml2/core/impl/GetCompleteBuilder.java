package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.GetComplete;

public class GetCompleteBuilder extends AbstractSAMLObjectBuilder {
   public GetComplete buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "GetComplete", "saml2p");
   }

   public GetComplete buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new GetCompleteImpl(namespaceURI, localName, namespacePrefix);
   }
}
