package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.w3c.dom.Attr;

public class SPSSODescriptorUnmarshaller extends SSODescriptorUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      SPSSODescriptor descriptor = (SPSSODescriptor)parentSAMLObject;
      if (childSAMLObject instanceof AssertionConsumerService) {
         descriptor.getAssertionConsumerServices().add((AssertionConsumerService)childSAMLObject);
      } else if (childSAMLObject instanceof AttributeConsumingService) {
         descriptor.getAttributeConsumingServices().add((AttributeConsumingService)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      SPSSODescriptor descriptor = (SPSSODescriptor)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("AuthnRequestsSigned")) {
            descriptor.setAuthnRequestsSigned(XSBooleanValue.valueOf(attribute.getValue()));
         } else if (attribute.getLocalName().equals("WantAssertionsSigned")) {
            descriptor.setWantAssertionsSigned(XSBooleanValue.valueOf(attribute.getValue()));
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
