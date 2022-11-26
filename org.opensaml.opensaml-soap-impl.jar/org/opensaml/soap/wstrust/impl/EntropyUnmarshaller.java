package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.Claims;
import org.w3c.dom.Attr;

public class EntropyUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Claims claims = (Claims)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(claims.getUnknownAttributes(), attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Claims claims = (Claims)parentXMLObject;
      claims.getUnknownXMLObjects().add(childXMLObject);
   }
}
