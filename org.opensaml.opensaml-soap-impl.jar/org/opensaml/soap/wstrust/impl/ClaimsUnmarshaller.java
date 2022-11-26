package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.Claims;
import org.w3c.dom.Attr;

public class ClaimsUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      Claims claims = (Claims)xmlObject;
      if ("Dialect".equals(attribute.getLocalName())) {
         claims.setDialect(attribute.getValue());
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(claims.getUnknownAttributes(), attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Claims claims = (Claims)parentXMLObject;
      claims.getUnknownXMLObjects().add(childXMLObject);
   }
}
