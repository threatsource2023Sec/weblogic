package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.ReferenceType;
import org.w3c.dom.Attr;

public class ReferenceTypeUnmarshaller extends AbstractXMLEncryptionUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      ReferenceType rt = (ReferenceType)xmlObject;
      if (attribute.getLocalName().equals("URI")) {
         rt.setURI(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ReferenceType rt = (ReferenceType)parentXMLObject;
      rt.getUnknownXMLObjects().add(childXMLObject);
   }
}
