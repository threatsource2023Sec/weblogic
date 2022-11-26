package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.RequestedProofToken;

public class RequestedProofTokenUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RequestedProofToken rpt = (RequestedProofToken)parentXMLObject;
      rpt.setUnknownXMLObject(childXMLObject);
   }
}
