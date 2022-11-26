package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.RequestedSecurityToken;

public class RequestedSecurityTokenUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RequestedSecurityToken reqToken = (RequestedSecurityToken)parentXMLObject;
      reqToken.setUnknownXMLObject(childXMLObject);
   }
}
