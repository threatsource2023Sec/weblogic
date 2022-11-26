package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.RequestSecurityTokenResponse;
import org.w3c.dom.Attr;

public class RequestSecurityTokenResponseUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      RequestSecurityTokenResponse rstr = (RequestSecurityTokenResponse)xmlObject;
      if ("Context".equals(attribute.getLocalName())) {
         rstr.setContext(attribute.getValue());
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(rstr.getUnknownAttributes(), attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RequestSecurityTokenResponse rstr = (RequestSecurityTokenResponse)parentXMLObject;
      rstr.getUnknownXMLObjects().add(childXMLObject);
   }
}
