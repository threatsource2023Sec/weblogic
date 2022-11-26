package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.w3c.dom.Attr;

public class KeyInfoUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      KeyInfo keyInfo = (KeyInfo)xmlObject;
      if (attribute.getLocalName().equals("Id")) {
         keyInfo.setID(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      KeyInfo keyInfo = (KeyInfo)parentXMLObject;
      keyInfo.getXMLObjects().add(childXMLObject);
   }
}
