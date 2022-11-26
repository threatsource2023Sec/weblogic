package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AttributeProfile;

public class AttributeProfileBuilder extends AbstractSAMLObjectBuilder {
   public AttributeProfile buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeProfile", "md");
   }

   public AttributeProfile buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeProfileImpl(namespaceURI, localName, namespacePrefix);
   }
}
