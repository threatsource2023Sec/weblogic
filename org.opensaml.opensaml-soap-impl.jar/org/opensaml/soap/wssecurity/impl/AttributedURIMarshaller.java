package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.AttributedURI;
import org.w3c.dom.Element;

public class AttributedURIMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedURI attributedURI = (AttributedURI)xmlObject;
      if (!Strings.isNullOrEmpty(attributedURI.getWSUId())) {
         XMLObjectSupport.marshallAttribute(AttributedURI.WSU_ID_ATTR_NAME, attributedURI.getWSUId(), domElement, true);
      }

      XMLObjectSupport.marshallAttributeMap(attributedURI.getUnknownAttributes(), domElement);
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedURI attributedURI = (AttributedURI)xmlObject;
      ElementSupport.appendTextContent(domElement, attributedURI.getValue());
   }
}
