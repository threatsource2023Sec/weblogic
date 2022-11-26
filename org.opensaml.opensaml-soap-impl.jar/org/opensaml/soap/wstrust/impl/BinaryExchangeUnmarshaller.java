package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.impl.XSStringUnmarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.BinaryExchange;
import org.w3c.dom.Attr;

public class BinaryExchangeUnmarshaller extends XSStringUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      BinaryExchange binaryExchange = (BinaryExchange)xmlObject;
      String attrName = attribute.getLocalName();
      if ("ValueType".equals(attrName)) {
         binaryExchange.setValueType(attribute.getValue());
      } else if ("EncodingType".equals(attrName)) {
         binaryExchange.setEncodingType(attribute.getValue());
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(binaryExchange.getUnknownAttributes(), attribute);
      }

   }
}
