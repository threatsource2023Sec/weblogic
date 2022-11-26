package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wssecurity.BinarySecurityToken;
import org.w3c.dom.Attr;

public class BinarySecurityTokenUnmarshaller extends EncodedStringUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      BinarySecurityToken token = (BinarySecurityToken)xmlObject;
      if ("ValueType".equals(attribute.getLocalName())) {
         token.setValueType(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
