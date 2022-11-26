package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.AttributeDesignator;
import org.w3c.dom.Attr;

public class AttributeDesignatorUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AttributeDesignator designator = (AttributeDesignator)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if ("AttributeName".equals(attribute.getLocalName())) {
            designator.setAttributeName(attribute.getValue());
         } else if ("AttributeNamespace".equals(attribute.getLocalName())) {
            designator.setAttributeNamespace(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
