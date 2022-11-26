package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.ReferenceParameters;
import org.w3c.dom.Attr;

public class ReferenceParametersUnmarshaller extends AbstractWSAddressingObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      ReferenceParameters rp = (ReferenceParameters)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(rp.getUnknownAttributes(), attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ReferenceParameters rp = (ReferenceParameters)parentXMLObject;
      rp.getUnknownXMLObjects().add(childXMLObject);
   }
}
