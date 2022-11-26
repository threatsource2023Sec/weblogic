package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.EncryptionProperties;
import org.w3c.dom.Element;

public class EncryptionPropertiesMarshaller extends AbstractXMLEncryptionMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      EncryptionProperties ep = (EncryptionProperties)xmlObject;
      if (ep.getID() != null) {
         domElement.setAttributeNS((String)null, "Id", ep.getID());
         domElement.setIdAttributeNS((String)null, "Id", true);
      }

   }
}
