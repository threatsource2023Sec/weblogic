package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.RequestSecurityTokenResponse;
import org.opensaml.soap.wstrust.RequestSecurityTokenResponseCollection;
import org.w3c.dom.Attr;

public class RequestSecurityTokenResponseCollectionUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      RequestSecurityTokenResponseCollection rstrc = (RequestSecurityTokenResponseCollection)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(rstrc.getUnknownAttributes(), attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RequestSecurityTokenResponseCollection rstrc = (RequestSecurityTokenResponseCollection)parentXMLObject;
      if (childXMLObject instanceof RequestSecurityTokenResponse) {
         rstrc.getRequestSecurityTokenResponses().add((RequestSecurityTokenResponse)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
