package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.DelegateTo;

public class DelegateToUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      DelegateTo delegateTo = (DelegateTo)parentXMLObject;
      delegateTo.setUnknownXMLObject(childXMLObject);
   }
}
