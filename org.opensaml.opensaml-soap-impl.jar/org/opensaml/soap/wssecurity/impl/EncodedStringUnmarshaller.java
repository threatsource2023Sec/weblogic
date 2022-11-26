package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wssecurity.EncodedString;
import org.w3c.dom.Attr;

public class EncodedStringUnmarshaller extends AttributedStringUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      EncodedString encodedString = (EncodedString)xmlObject;
      if ("EncodingType".equals(attribute.getLocalName())) {
         encodedString.setEncodingType(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
