package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml1.core.AttributeQuery;
import org.w3c.dom.Element;

public class AttributeQueryMarshaller extends SubjectQueryMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      AttributeQuery attributeQuery = (AttributeQuery)samlElement;
      if (attributeQuery.getResource() != null) {
         domElement.setAttributeNS((String)null, "Resource", attributeQuery.getResource());
      }

   }
}
