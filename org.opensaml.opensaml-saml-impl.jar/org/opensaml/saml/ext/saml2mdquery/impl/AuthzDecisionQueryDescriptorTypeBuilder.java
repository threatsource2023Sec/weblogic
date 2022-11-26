package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdquery.AuthzDecisionQueryDescriptorType;

public class AuthzDecisionQueryDescriptorTypeBuilder extends AbstractSAMLObjectBuilder {
   public AuthzDecisionQueryDescriptorType buildObject() {
      return (AuthzDecisionQueryDescriptorType)this.buildObject("urn:oasis:names:tc:SAML:metadata:ext:query", "RoleDescriptor", "query", AuthzDecisionQueryDescriptorType.TYPE_NAME);
   }

   public AuthzDecisionQueryDescriptorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthzDecisionQueryDescriptorTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
