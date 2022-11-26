package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.soap.wssecurity.EncodedString;
import org.w3c.dom.Element;

public class EncodedStringMarshaller extends AttributedStringMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      EncodedString encodedString = (EncodedString)xmlObject;
      if (!Strings.isNullOrEmpty(encodedString.getEncodingType())) {
         domElement.setAttributeNS((String)null, "EncodingType", encodedString.getEncodingType());
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
