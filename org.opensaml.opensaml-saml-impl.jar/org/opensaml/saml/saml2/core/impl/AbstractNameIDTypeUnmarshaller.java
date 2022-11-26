package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.NameIDType;
import org.w3c.dom.Attr;

public abstract class AbstractNameIDTypeUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      NameIDType nameID = (NameIDType)samlObject;
      nameID.setValue(elementContent);
   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      NameIDType nameID = (NameIDType)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("NameQualifier")) {
            nameID.setNameQualifier(attribute.getValue());
         } else if (attribute.getLocalName().equals("SPNameQualifier")) {
            nameID.setSPNameQualifier(attribute.getValue());
         } else if (attribute.getLocalName().equals("Format")) {
            nameID.setFormat(attribute.getValue());
         } else if (attribute.getLocalName().equals("SPProvidedID")) {
            nameID.setSPProvidedID(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
