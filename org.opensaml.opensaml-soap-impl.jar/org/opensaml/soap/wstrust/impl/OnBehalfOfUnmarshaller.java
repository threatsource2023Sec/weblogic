package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.OnBehalfOf;

public class OnBehalfOfUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      OnBehalfOf obo = (OnBehalfOf)parentXMLObject;
      obo.getUnknownXMLObjects().add(childXMLObject);
   }
}
