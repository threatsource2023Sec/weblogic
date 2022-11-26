package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.StatusCodeType;
import org.opensaml.xacml.ctx.StatusDetailType;
import org.opensaml.xacml.ctx.StatusMessageType;
import org.opensaml.xacml.ctx.StatusType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;

public class StatusTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      StatusType status = (StatusType)parentObject;
      if (childObject instanceof StatusCodeType) {
         status.setStatusCode((StatusCodeType)childObject);
      } else if (childObject instanceof StatusMessageType) {
         status.setStatusMessage((StatusMessageType)childObject);
      } else if (childObject instanceof StatusDetailType) {
         status.setStatusDetail((StatusDetailType)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
