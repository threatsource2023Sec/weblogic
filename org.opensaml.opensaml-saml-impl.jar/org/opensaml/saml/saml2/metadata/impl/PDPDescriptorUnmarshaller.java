package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AuthzService;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.PDPDescriptor;

public class PDPDescriptorUnmarshaller extends RoleDescriptorUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      PDPDescriptor descriptor = (PDPDescriptor)parentSAMLObject;
      if (childSAMLObject instanceof AuthzService) {
         descriptor.getAuthzServices().add((AuthzService)childSAMLObject);
      } else if (childSAMLObject instanceof AssertionIDRequestService) {
         descriptor.getAssertionIDRequestServices().add((AssertionIDRequestService)childSAMLObject);
      } else if (childSAMLObject instanceof NameIDFormat) {
         descriptor.getNameIDFormats().add((NameIDFormat)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
