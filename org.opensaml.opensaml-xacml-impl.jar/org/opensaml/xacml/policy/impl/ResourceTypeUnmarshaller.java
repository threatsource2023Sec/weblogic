package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ResourceMatchType;
import org.opensaml.xacml.policy.ResourceType;

public class ResourceTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ResourceType resourceType = (ResourceType)parentXMLObject;
      if (childXMLObject instanceof ResourceMatchType) {
         resourceType.getResourceMatches().add((ResourceMatchType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
