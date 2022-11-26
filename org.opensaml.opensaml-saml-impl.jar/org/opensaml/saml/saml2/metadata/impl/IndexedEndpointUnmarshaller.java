package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;
import org.w3c.dom.Attr;

public class IndexedEndpointUnmarshaller extends EndpointUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      IndexedEndpoint iEndpoint = (IndexedEndpoint)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("index")) {
            iEndpoint.setIndex(Integer.valueOf(attribute.getValue()));
         } else if (attribute.getLocalName().equals("isDefault")) {
            iEndpoint.setIsDefault(XSBooleanValue.valueOf(attribute.getValue()));
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
