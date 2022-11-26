package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.soap.wssecurity.BinarySecurityToken;
import org.w3c.dom.Element;

public class BinarySecurityTokenMarshaller extends EncodedStringMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      BinarySecurityToken token = (BinarySecurityToken)xmlObject;
      if (!Strings.isNullOrEmpty(token.getValueType())) {
         domElement.setAttributeNS((String)null, "EncodingType", token.getValueType());
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
