package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.AttributeDesignator;
import org.w3c.dom.Element;

public class AttributeDesignatorMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      AttributeDesignator designator = (AttributeDesignator)samlElement;
      if (designator.getAttributeName() != null) {
         domElement.setAttributeNS((String)null, "AttributeName", designator.getAttributeName());
      }

      if (designator.getAttributeNamespace() != null) {
         domElement.setAttributeNS((String)null, "AttributeNamespace", designator.getAttributeNamespace());
      }

   }
}
