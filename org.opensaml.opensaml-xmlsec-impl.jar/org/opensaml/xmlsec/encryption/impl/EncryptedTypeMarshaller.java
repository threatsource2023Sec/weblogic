package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.EncryptedType;
import org.w3c.dom.Element;

public abstract class EncryptedTypeMarshaller extends AbstractXMLEncryptionMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      EncryptedType et = (EncryptedType)xmlObject;
      if (et.getID() != null) {
         domElement.setAttributeNS((String)null, "Id", et.getID());
         domElement.setIdAttributeNS((String)null, "Id", true);
      }

      if (et.getType() != null) {
         domElement.setAttributeNS((String)null, "Type", et.getType());
      }

      if (et.getMimeType() != null) {
         domElement.setAttributeNS((String)null, "MimeType", et.getMimeType());
      }

      if (et.getEncoding() != null) {
         domElement.setAttributeNS((String)null, "Encoding", et.getEncoding());
      }

   }
}
