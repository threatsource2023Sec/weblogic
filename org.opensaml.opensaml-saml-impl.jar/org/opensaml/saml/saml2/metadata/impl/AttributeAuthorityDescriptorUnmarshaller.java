package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.AttributeProfile;
import org.opensaml.saml.saml2.metadata.AttributeService;
import org.opensaml.saml.saml2.metadata.NameIDFormat;

public class AttributeAuthorityDescriptorUnmarshaller extends RoleDescriptorUnmarshaller {
   protected void processChildElement(XMLObject parentElement, XMLObject childElement) throws UnmarshallingException {
      AttributeAuthorityDescriptor descriptor = (AttributeAuthorityDescriptor)parentElement;
      if (childElement instanceof AttributeService) {
         descriptor.getAttributeServices().add((AttributeService)childElement);
      } else if (childElement instanceof AssertionIDRequestService) {
         descriptor.getAssertionIDRequestServices().add((AssertionIDRequestService)childElement);
      } else if (childElement instanceof NameIDFormat) {
         descriptor.getNameIDFormats().add((NameIDFormat)childElement);
      } else if (childElement instanceof AttributeProfile) {
         descriptor.getAttributeProfiles().add((AttributeProfile)childElement);
      } else if (childElement instanceof Attribute) {
         descriptor.getAttributes().add((Attribute)childElement);
      } else {
         super.processChildElement(parentElement, childElement);
      }

   }
}
