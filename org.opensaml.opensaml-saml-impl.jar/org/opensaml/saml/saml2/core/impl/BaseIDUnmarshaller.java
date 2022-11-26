package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.BaseID;
import org.w3c.dom.Attr;

public abstract class BaseIDUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      BaseID baseID = (BaseID)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("NameQualifier")) {
            baseID.setNameQualifier(attribute.getValue());
         } else if (attribute.getLocalName().equals("SPNameQualifier")) {
            baseID.setSPNameQualifier(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
