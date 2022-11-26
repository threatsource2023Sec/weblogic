package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.AttributedDateTime;
import org.w3c.dom.Element;

public class AttributedDateTimeMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedDateTime dateTime = (AttributedDateTime)xmlObject;
      if (!Strings.isNullOrEmpty(dateTime.getWSUId())) {
         XMLObjectSupport.marshallAttribute(AttributedDateTime.WSU_ID_ATTR_NAME, dateTime.getWSUId(), domElement, true);
      }

      XMLObjectSupport.marshallAttributeMap(dateTime.getUnknownAttributes(), domElement);
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedDateTime dateTime = (AttributedDateTime)xmlObject;
      ElementSupport.appendTextContent(domElement, dateTime.getValue());
   }
}
