package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.RequestSecurityTokenResponseCollection;
import org.w3c.dom.Element;

public class RequestSecurityTokenResponseCollectionMarshaller extends AbstractWSTrustObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      RequestSecurityTokenResponseCollection rstrc = (RequestSecurityTokenResponseCollection)xmlObject;
      XMLObjectSupport.marshallAttributeMap(rstrc.getUnknownAttributes(), domElement);
   }
}
