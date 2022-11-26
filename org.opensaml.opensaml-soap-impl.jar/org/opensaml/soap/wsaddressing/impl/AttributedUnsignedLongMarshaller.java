package org.opensaml.soap.wsaddressing.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.AttributedUnsignedLong;
import org.w3c.dom.Element;

public class AttributedUnsignedLongMarshaller extends AbstractWSAddressingObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedUnsignedLong aul = (AttributedUnsignedLong)xmlObject;
      XMLObjectSupport.marshallAttributeMap(aul.getUnknownAttributes(), domElement);
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributedUnsignedLong aul = (AttributedUnsignedLong)xmlObject;
      if (aul.getValue() != null) {
         ElementSupport.appendTextContent(domElement, aul.getValue().toString());
      }

   }
}
