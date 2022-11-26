package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AttributeStatement;

public class AttributeStatementBuilder extends AbstractSAMLObjectBuilder {
   public AttributeStatement buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement", "saml1");
   }

   public AttributeStatement buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeStatementImpl(namespaceURI, localName, namespacePrefix);
   }
}
