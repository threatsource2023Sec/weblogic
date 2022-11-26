package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Attribute;

public class AttributeBuilder extends AbstractSAMLObjectBuilder {
   public Attribute buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Attribute", "saml2");
   }

   public Attribute buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeImpl(namespaceURI, localName, namespacePrefix);
   }
}
