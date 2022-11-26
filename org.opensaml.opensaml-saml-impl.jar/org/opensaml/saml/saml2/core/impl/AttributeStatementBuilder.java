package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AttributeStatement;

public class AttributeStatementBuilder extends AbstractSAMLObjectBuilder {
   public AttributeStatement buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeStatement", "saml2");
   }

   public AttributeStatement buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeStatementImpl(namespaceURI, localName, namespacePrefix);
   }
}
