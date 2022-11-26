package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.Security;
import org.w3c.dom.Attr;

public class SecurityUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Security security = (Security)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(security.getUnknownAttributes(), attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Security security = (Security)parentXMLObject;
      security.getUnknownXMLObjects().add(childXMLObject);
   }
}
