package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.RequestSecurityToken;
import org.opensaml.soap.wstrust.RequestSecurityTokenCollection;

public class RequestSecurityTokenCollectionUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RequestSecurityTokenCollection rstc = (RequestSecurityTokenCollection)parentXMLObject;
      if (childXMLObject instanceof RequestSecurityToken) {
         rstc.getRequestSecurityTokens().add((RequestSecurityToken)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
