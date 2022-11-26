package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.EnvironmentType;
import org.opensaml.xacml.policy.EnvironmentsType;

public class EnvironmentsTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      EnvironmentsType environmentsType = (EnvironmentsType)parentXMLObject;
      if (childXMLObject instanceof EnvironmentType) {
         environmentsType.getEnvironments().add((EnvironmentType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
