package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wssecurity.KeyIdentifier;
import org.w3c.dom.Attr;

public class KeyIdentifierUnmarshaller extends EncodedStringUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      KeyIdentifier keyIdentifier = (KeyIdentifier)xmlObject;
      if ("ValueType".equals(attribute.getLocalName())) {
         keyIdentifier.setValueType(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
