package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.ValidateTarget;

public class ValidateTargetUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ValidateTarget vt = (ValidateTarget)parentXMLObject;
      vt.setUnknownXMLObject(childXMLObject);
   }
}
