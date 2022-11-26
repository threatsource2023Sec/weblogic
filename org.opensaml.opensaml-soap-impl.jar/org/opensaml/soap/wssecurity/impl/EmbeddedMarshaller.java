package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.Embedded;
import org.w3c.dom.Element;

public class EmbeddedMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Embedded embedded = (Embedded)xmlObject;
      if (!Strings.isNullOrEmpty(embedded.getValueType())) {
         domElement.setAttributeNS((String)null, "ValueType", embedded.getValueType());
      }

      XMLObjectSupport.marshallAttributeMap(embedded.getUnknownAttributes(), domElement);
   }
}
