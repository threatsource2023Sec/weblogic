package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdquery.AuthnQueryDescriptorType;

public class AuthnQueryDescriptorTypeBuilder extends AbstractSAMLObjectBuilder {
   public AuthnQueryDescriptorType buildObject() {
      return (AuthnQueryDescriptorType)this.buildObject("urn:oasis:names:tc:SAML:metadata:ext:query", "RoleDescriptor", "query", AuthnQueryDescriptorType.TYPE_NAME);
   }

   public AuthnQueryDescriptorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthnQueryDescriptorTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
