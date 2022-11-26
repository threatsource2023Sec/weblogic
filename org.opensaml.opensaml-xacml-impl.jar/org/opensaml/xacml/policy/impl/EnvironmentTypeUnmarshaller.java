package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.EnvironmentMatchType;
import org.opensaml.xacml.policy.EnvironmentType;

public class EnvironmentTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      EnvironmentType environmentType = (EnvironmentType)parentXMLObject;
      if (childXMLObject instanceof EnvironmentMatchType) {
         environmentType.getEnvrionmentMatches().add((EnvironmentMatchType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
