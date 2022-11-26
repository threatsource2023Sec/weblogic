package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.AttributeDesignator;
import org.opensaml.saml.saml1.core.AttributeQuery;
import org.w3c.dom.Attr;

public class AttributeQueryUnmarshaller extends SubjectQueryUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AttributeQuery attributeQuery = (AttributeQuery)parentSAMLObject;
      if (childSAMLObject instanceof AttributeDesignator) {
         attributeQuery.getAttributeDesignators().add((AttributeDesignator)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AttributeQuery attributeQuery = (AttributeQuery)samlObject;
      if (attribute.getLocalName().equals("Resource") && attribute.getNamespaceURI() == null) {
         attributeQuery.setResource(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
