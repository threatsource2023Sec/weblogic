package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdquery.AttributeQueryDescriptorType;

public class AttributeQueryDescriptorTypeBuilder extends AbstractSAMLObjectBuilder {
   public AttributeQueryDescriptorType buildObject() {
      return (AttributeQueryDescriptorType)this.buildObject("urn:oasis:names:tc:SAML:metadata:ext:query", "RoleDescriptor", "query", AttributeQueryDescriptorType.TYPE_NAME);
   }

   public AttributeQueryDescriptorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeQueryDescriptorTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
