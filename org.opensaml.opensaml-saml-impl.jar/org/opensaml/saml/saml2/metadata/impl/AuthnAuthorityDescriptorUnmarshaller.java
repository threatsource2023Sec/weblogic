package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AuthnAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.AuthnQueryService;
import org.opensaml.saml.saml2.metadata.NameIDFormat;

public class AuthnAuthorityDescriptorUnmarshaller extends RoleDescriptorUnmarshaller {
   protected void processChildElement(XMLObject parentElement, XMLObject childElement) throws UnmarshallingException {
      AuthnAuthorityDescriptor descriptor = (AuthnAuthorityDescriptor)parentElement;
      if (childElement instanceof AuthnQueryService) {
         descriptor.getAuthnQueryServices().add((AuthnQueryService)childElement);
      } else if (childElement instanceof AssertionIDRequestService) {
         descriptor.getAssertionIDRequestServices().add((AssertionIDRequestService)childElement);
      } else if (childElement instanceof NameIDFormat) {
         descriptor.getNameIDFormats().add((NameIDFormat)childElement);
      } else {
         super.processChildElement(parentElement, childElement);
      }

   }
}
