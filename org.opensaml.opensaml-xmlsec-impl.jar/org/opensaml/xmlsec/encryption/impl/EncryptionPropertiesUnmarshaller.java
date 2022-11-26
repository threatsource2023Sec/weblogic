package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.EncryptionProperties;
import org.opensaml.xmlsec.encryption.EncryptionProperty;
import org.w3c.dom.Attr;

public class EncryptionPropertiesUnmarshaller extends AbstractXMLEncryptionUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      EncryptionProperties ep = (EncryptionProperties)xmlObject;
      if (attribute.getLocalName().equals("Id")) {
         ep.setID(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      EncryptionProperties ep = (EncryptionProperties)parentXMLObject;
      if (childXMLObject instanceof EncryptionProperty) {
         ep.getEncryptionProperties().add((EncryptionProperty)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
