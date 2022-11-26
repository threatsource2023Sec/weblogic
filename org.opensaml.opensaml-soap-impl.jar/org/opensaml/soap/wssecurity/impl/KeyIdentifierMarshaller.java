package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.soap.wssecurity.KeyIdentifier;
import org.w3c.dom.Element;

public class KeyIdentifierMarshaller extends EncodedStringMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      KeyIdentifier keyIdentifier = (KeyIdentifier)xmlObject;
      if (!Strings.isNullOrEmpty(keyIdentifier.getValueType())) {
         domElement.setAttributeNS((String)null, "ValueType", keyIdentifier.getValueType());
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
