package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.Code;
import org.opensaml.soap.wstrust.Reason;
import org.opensaml.soap.wstrust.Status;

public class StatusUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Status status = (Status)parentXMLObject;
      if (childXMLObject instanceof Code) {
         status.setCode((Code)childXMLObject);
      } else if (childXMLObject instanceof Reason) {
         status.setReason((Reason)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
