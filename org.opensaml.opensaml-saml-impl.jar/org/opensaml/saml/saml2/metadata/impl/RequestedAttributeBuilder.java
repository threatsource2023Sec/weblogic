package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;

public class RequestedAttributeBuilder extends AbstractSAMLObjectBuilder {
   public RequestedAttribute buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "RequestedAttribute", "md");
   }

   public RequestedAttribute buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RequestedAttributeImpl(namespaceURI, localName, namespacePrefix);
   }
}
