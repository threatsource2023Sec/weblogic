package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AttributeProfile;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.NameIDMappingService;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;
import org.w3c.dom.Attr;

public class IDPSSODescriptorUnmarshaller extends SSODescriptorUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      IDPSSODescriptor descriptor = (IDPSSODescriptor)parentObject;
      if (childObject instanceof SingleSignOnService) {
         descriptor.getSingleSignOnServices().add((SingleSignOnService)childObject);
      } else if (childObject instanceof NameIDMappingService) {
         descriptor.getNameIDMappingServices().add((NameIDMappingService)childObject);
      } else if (childObject instanceof AssertionIDRequestService) {
         descriptor.getAssertionIDRequestServices().add((AssertionIDRequestService)childObject);
      } else if (childObject instanceof AttributeProfile) {
         descriptor.getAttributeProfiles().add((AttributeProfile)childObject);
      } else if (childObject instanceof Attribute) {
         descriptor.getAttributes().add((Attribute)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      IDPSSODescriptor descriptor = (IDPSSODescriptor)samlObject;
      if (attribute.getLocalName().equals("WantAuthnRequestsSigned") && attribute.getNamespaceURI() == null) {
         descriptor.setWantAuthnRequestsSigned(XSBooleanValue.valueOf(attribute.getValue()));
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
