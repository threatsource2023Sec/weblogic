package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.AttributedString;
import org.w3c.dom.Element;

public class AttributedStringMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedString attributedString = (AttributedString)xmlObject;
      if (!Strings.isNullOrEmpty(attributedString.getWSUId())) {
         XMLObjectSupport.marshallAttribute(AttributedString.WSU_ID_ATTR_NAME, attributedString.getWSUId(), domElement, true);
      }

      XMLObjectSupport.marshallAttributeMap(attributedString.getUnknownAttributes(), domElement);
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedString attributedString = (AttributedString)xmlObject;
      ElementSupport.appendTextContent(domElement, attributedString.getValue());
   }
}
