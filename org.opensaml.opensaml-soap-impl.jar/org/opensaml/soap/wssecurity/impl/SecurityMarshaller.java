package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.Security;
import org.w3c.dom.Element;

public class SecurityMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Security security = (Security)xmlObject;
      XMLObjectSupport.marshallAttributeMap(security.getUnknownAttributes(), domElement);
   }
}
