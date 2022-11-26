package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.ActionType;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;

public class ActionTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      ActionType action = (ActionType)parentObject;
      if (childObject instanceof AttributeType) {
         action.getAttributes().add((AttributeType)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
