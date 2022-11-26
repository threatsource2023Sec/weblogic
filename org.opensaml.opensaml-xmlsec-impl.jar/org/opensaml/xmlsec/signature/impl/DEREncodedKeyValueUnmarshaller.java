package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryUnmarshaller;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.w3c.dom.Attr;

public class DEREncodedKeyValueUnmarshaller extends XSBase64BinaryUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      DEREncodedKeyValue der = (DEREncodedKeyValue)xmlObject;
      if (attribute.getLocalName().equals("Id")) {
         der.setID(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
