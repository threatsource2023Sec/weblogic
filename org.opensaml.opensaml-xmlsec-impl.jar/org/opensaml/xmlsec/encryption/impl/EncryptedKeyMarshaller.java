package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.w3c.dom.Element;

public class EncryptedKeyMarshaller extends EncryptedTypeMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      EncryptedKey ek = (EncryptedKey)xmlObject;
      if (ek.getRecipient() != null) {
         domElement.setAttributeNS((String)null, "Recipient", ek.getRecipient());
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
