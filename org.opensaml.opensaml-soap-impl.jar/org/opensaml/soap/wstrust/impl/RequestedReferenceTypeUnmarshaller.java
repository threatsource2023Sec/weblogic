package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wssecurity.SecurityTokenReference;
import org.opensaml.soap.wstrust.RequestedReferenceType;

public class RequestedReferenceTypeUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RequestedReferenceType rrt = (RequestedReferenceType)parentXMLObject;
      if (childXMLObject instanceof SecurityTokenReference) {
         rrt.setSecurityTokenReference((SecurityTokenReference)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
