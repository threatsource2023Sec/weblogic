package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.w3c.dom.Attr;

public class NameIdentifierUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      NameIdentifier nameIdentifier = (NameIdentifier)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if ("Format".equals(attribute.getLocalName())) {
            nameIdentifier.setFormat(attribute.getValue());
         } else if ("NameQualifier".equals(attribute.getLocalName())) {
            nameIdentifier.setNameQualifier(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }

   protected void processElementContent(XMLObject samlObject, String elementContent) {
      NameIdentifier nameIdentifier = (NameIdentifier)samlObject;
      nameIdentifier.setValue(elementContent);
   }
}
