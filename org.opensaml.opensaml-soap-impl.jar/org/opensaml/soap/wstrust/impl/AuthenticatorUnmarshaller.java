package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.Authenticator;
import org.opensaml.soap.wstrust.CombinedHash;

public class AuthenticatorUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Authenticator authenticator = (Authenticator)parentXMLObject;
      if (childXMLObject instanceof CombinedHash) {
         authenticator.setCombinedHash((CombinedHash)childXMLObject);
      } else {
         authenticator.getUnknownXMLObjects().add(childXMLObject);
      }

   }
}
