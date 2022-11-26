package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.AttributedUnsignedLong;
import org.w3c.dom.Attr;

public class AttributedUnsignedLongUnmarshaller extends AbstractWSAddressingObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributedUnsignedLong aul = (AttributedUnsignedLong)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(aul.getUnknownAttributes(), attribute);
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      AttributedUnsignedLong aul = (AttributedUnsignedLong)xmlObject;
      if (elementContent != null) {
         aul.setValue(Long.valueOf(elementContent.trim()));
      }

   }
}
