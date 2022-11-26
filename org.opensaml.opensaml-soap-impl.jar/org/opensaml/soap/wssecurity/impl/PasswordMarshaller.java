package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.soap.wssecurity.Password;
import org.w3c.dom.Element;

public class PasswordMarshaller extends AttributedStringMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Password password = (Password)xmlObject;
      if (!Strings.isNullOrEmpty(password.getType())) {
         domElement.setAttributeNS((String)null, "Type", password.getType());
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
