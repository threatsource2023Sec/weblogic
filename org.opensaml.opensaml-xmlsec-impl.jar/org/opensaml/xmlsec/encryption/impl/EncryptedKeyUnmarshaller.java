package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.CarriedKeyName;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.ReferenceList;
import org.w3c.dom.Attr;

public class EncryptedKeyUnmarshaller extends EncryptedTypeUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      EncryptedKey ek = (EncryptedKey)xmlObject;
      if (attribute.getLocalName().equals("Recipient")) {
         ek.setRecipient(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      EncryptedKey ek = (EncryptedKey)parentXMLObject;
      if (childXMLObject instanceof ReferenceList) {
         ek.setReferenceList((ReferenceList)childXMLObject);
      } else if (childXMLObject instanceof CarriedKeyName) {
         ek.setCarriedKeyName((CarriedKeyName)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
