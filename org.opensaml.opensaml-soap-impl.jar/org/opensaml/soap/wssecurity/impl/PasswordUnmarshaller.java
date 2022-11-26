package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wssecurity.Password;
import org.w3c.dom.Attr;

public class PasswordUnmarshaller extends AttributedStringUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Password password = (Password)xmlObject;
      if ("Type".equals(attribute.getLocalName())) {
         password.setType(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
