package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.ext.saml2mdquery.ActionNamespace;
import org.opensaml.saml.ext.saml2mdquery.AuthzDecisionQueryDescriptorType;

public class AuthzDecisionQueryDescriptorTypeUnmarshaller extends QueryDescriptorTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AuthzDecisionQueryDescriptorType descriptor = (AuthzDecisionQueryDescriptorType)parentSAMLObject;
      if (childSAMLObject instanceof ActionNamespace) {
         descriptor.getActionNamespaces().add((ActionNamespace)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
