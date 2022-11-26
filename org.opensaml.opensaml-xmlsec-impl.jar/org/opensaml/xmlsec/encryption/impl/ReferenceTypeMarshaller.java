package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.ReferenceType;
import org.w3c.dom.Element;

public class ReferenceTypeMarshaller extends AbstractXMLEncryptionMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      ReferenceType rt = (ReferenceType)xmlObject;
      if (rt.getURI() != null) {
         domElement.setAttributeNS((String)null, "URI", rt.getURI());
      } else {
         super.marshallAttributes(xmlObject, domElement);
      }

   }
}
