package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.Timestamp;
import org.w3c.dom.Element;

public class TimestampMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Timestamp timestamp = (Timestamp)xmlObject;
      if (!Strings.isNullOrEmpty(timestamp.getWSUId())) {
         XMLObjectSupport.marshallAttribute(Timestamp.WSU_ID_ATTR_NAME, timestamp.getWSUId(), domElement, true);
      }

      XMLObjectSupport.marshallAttributeMap(timestamp.getUnknownAttributes(), domElement);
   }
}
