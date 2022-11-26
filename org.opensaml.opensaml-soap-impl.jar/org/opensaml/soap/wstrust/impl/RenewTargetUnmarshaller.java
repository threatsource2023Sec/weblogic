package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.RenewTarget;

public class RenewTargetUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RenewTarget rt = (RenewTarget)parentXMLObject;
      rt.setUnknownXMLObject(childXMLObject);
   }
}
