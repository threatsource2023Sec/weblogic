package org.opensaml.soap.wspolicy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wspolicy.OperatorContentType;

public class OperatorContentTypeUnmarshaller extends AbstractWSPolicyObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      OperatorContentType oct = (OperatorContentType)parentXMLObject;
      oct.getXMLObjects().add(childXMLObject);
   }
}
