package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.CipherData;
import org.opensaml.xmlsec.encryption.EncryptedType;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.EncryptionProperties;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.w3c.dom.Attr;

public abstract class EncryptedTypeUnmarshaller extends AbstractXMLEncryptionUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      EncryptedType et = (EncryptedType)parentXMLObject;
      if (childXMLObject instanceof EncryptionMethod) {
         et.setEncryptionMethod((EncryptionMethod)childXMLObject);
      } else if (childXMLObject instanceof KeyInfo) {
         et.setKeyInfo((KeyInfo)childXMLObject);
      } else if (childXMLObject instanceof CipherData) {
         et.setCipherData((CipherData)childXMLObject);
      } else if (childXMLObject instanceof EncryptionProperties) {
         et.setEncryptionProperties((EncryptionProperties)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      EncryptedType et = (EncryptedType)xmlObject;
      if (attribute.getLocalName().equals("Id")) {
         et.setID(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else if (attribute.getLocalName().equals("Type")) {
         et.setType(attribute.getValue());
      } else if (attribute.getLocalName().equals("MimeType")) {
         et.setMimeType(attribute.getValue());
      } else if (attribute.getLocalName().equals("Encoding")) {
         et.setEncoding(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
