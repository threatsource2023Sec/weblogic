package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AttributeQuery;

public class AttributeQueryBuilder extends AbstractSAMLObjectBuilder {
   public AttributeQuery buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AttributeQuery", "saml2p");
   }

   public AttributeQuery buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeQueryImpl(namespaceURI, localName, namespacePrefix);
   }
}
