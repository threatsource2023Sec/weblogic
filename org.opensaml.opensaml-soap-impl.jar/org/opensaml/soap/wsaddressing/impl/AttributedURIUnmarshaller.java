package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.AttributedURI;
import org.w3c.dom.Attr;

public class AttributedURIUnmarshaller extends AbstractWSAddressingObjectUnmarshaller {
   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      if (elementContent != null) {
         AttributedURI attributedURI = (AttributedURI)xmlObject;
         attributedURI.setValue(elementContent);
      }

   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributedURI attributedURI = (AttributedURI)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(attributedURI.getUnknownAttributes(), attribute);
   }
}
