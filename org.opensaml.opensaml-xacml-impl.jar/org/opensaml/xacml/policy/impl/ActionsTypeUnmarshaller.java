package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ActionType;
import org.opensaml.xacml.policy.ActionsType;

public class ActionsTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ActionsType actionType = (ActionsType)parentXMLObject;
      if (childXMLObject instanceof ActionType) {
         actionType.getActions().add((ActionType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
