package org.opensaml.soap.wstrust.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSStringMarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.BinaryExchange;
import org.w3c.dom.Element;

public class BinaryExchangeMarshaller extends XSStringMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      BinaryExchange binaryExchange = (BinaryExchange)xmlObject;
      String valueType = StringSupport.trimOrNull(binaryExchange.getValueType());
      if (valueType != null) {
         domElement.setAttributeNS((String)null, "ValueType", valueType);
      }

      String encodingType = StringSupport.trimOrNull(binaryExchange.getEncodingType());
      if (encodingType != null) {
         domElement.setAttributeNS((String)null, "EncodingType", encodingType);
      }

      XMLObjectSupport.marshallAttributeMap(binaryExchange.getUnknownAttributes(), domElement);
   }
}
