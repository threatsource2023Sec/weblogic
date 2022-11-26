package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.CipherData;
import org.opensaml.xmlsec.encryption.CipherReference;
import org.opensaml.xmlsec.encryption.CipherValue;

public class CipherDataUnmarshaller extends AbstractXMLEncryptionUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      CipherData cipherData = (CipherData)parentXMLObject;
      if (childXMLObject instanceof CipherValue) {
         cipherData.setCipherValue((CipherValue)childXMLObject);
      } else if (childXMLObject instanceof CipherReference) {
         cipherData.setCipherReference((CipherReference)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
