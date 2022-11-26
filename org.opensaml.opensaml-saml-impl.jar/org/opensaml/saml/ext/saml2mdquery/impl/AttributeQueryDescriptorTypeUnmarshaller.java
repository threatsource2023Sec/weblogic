package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.ext.saml2mdquery.AttributeQueryDescriptorType;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;

public class AttributeQueryDescriptorTypeUnmarshaller extends QueryDescriptorTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AttributeQueryDescriptorType descriptor = (AttributeQueryDescriptorType)parentSAMLObject;
      if (childSAMLObject instanceof AttributeConsumingService) {
         descriptor.getAttributeConsumingServices().add((AttributeConsumingService)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
