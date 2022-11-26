package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.Claims;
import org.w3c.dom.Element;

public class ClaimsMarshaller extends AbstractWSTrustObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Claims claims = (Claims)xmlObject;
      if (claims.getDialect() != null) {
         domElement.setAttributeNS((String)null, "Dialect", claims.getDialect());
      }

      XMLObjectSupport.marshallAttributeMap(claims.getUnknownAttributes(), domElement);
   }
}
