package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.w3c.dom.Element;

public class EncryptionMethodMarshaller extends AbstractXMLEncryptionMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      EncryptionMethod em = (EncryptionMethod)xmlObject;
      if (em.getAlgorithm() != null) {
         domElement.setAttributeNS((String)null, "Algorithm", em.getAlgorithm());
      }

   }
}
