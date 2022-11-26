package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.KeyExchangeToken;

public class KeyExchangeTokenUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      KeyExchangeToken ket = (KeyExchangeToken)parentXMLObject;
      ket.getUnknownXMLObjects().add(childXMLObject);
   }
}
