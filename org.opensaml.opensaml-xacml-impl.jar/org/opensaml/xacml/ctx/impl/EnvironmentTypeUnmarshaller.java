package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;

public class EnvironmentTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      EnvironmentType environment = (EnvironmentType)parentObject;
      if (childObject instanceof AttributeType) {
         environment.getAttributes().add((AttributeType)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
