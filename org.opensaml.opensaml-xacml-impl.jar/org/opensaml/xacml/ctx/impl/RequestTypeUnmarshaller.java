package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.ActionType;
import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResourceType;
import org.opensaml.xacml.ctx.SubjectType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;

public class RequestTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RequestType request = (RequestType)parentXMLObject;
      if (childXMLObject instanceof ActionType) {
         request.setAction((ActionType)childXMLObject);
      } else if (childXMLObject instanceof EnvironmentType) {
         request.setEnvironment((EnvironmentType)childXMLObject);
      } else if (childXMLObject instanceof SubjectType) {
         request.getSubjects().add((SubjectType)childXMLObject);
      } else if (childXMLObject instanceof ResourceType) {
         request.getResources().add((ResourceType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
