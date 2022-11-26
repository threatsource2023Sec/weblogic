package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.ResourceContentType;
import org.opensaml.xacml.ctx.ResourceType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;

public class ResourceTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      ResourceType resource = (ResourceType)parentObject;
      if (childObject instanceof ResourceContentType) {
         resource.setResourceContent((ResourceContentType)childObject);
      } else if (childObject instanceof AttributeType) {
         resource.getAttributes().add((AttributeType)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
