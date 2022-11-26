package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.IDPEntry;
import org.w3c.dom.Attr;

public class IDPEntryUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      IDPEntry entry = (IDPEntry)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("ProviderID")) {
            entry.setProviderID(attribute.getValue());
         } else if (attribute.getLocalName().equals("Name")) {
            entry.setName(attribute.getValue());
         } else if (attribute.getLocalName().equals("Loc")) {
            entry.setLoc(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
