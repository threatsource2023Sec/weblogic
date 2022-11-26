package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.TransformationParameters;
import org.w3c.dom.Attr;

public class TransformationParametersUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      TransformationParameters tp = (TransformationParameters)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(tp.getUnknownAttributes(), attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      TransformationParameters tp = (TransformationParameters)parentXMLObject;
      tp.getUnknownXMLObjects().add(childXMLObject);
   }
}
