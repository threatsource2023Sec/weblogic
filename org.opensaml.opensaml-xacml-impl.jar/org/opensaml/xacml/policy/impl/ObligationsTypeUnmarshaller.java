package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ObligationType;
import org.opensaml.xacml.policy.ObligationsType;

public class ObligationsTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      ObligationsType obligations = (ObligationsType)parentObject;
      if (childObject instanceof ObligationType) {
         obligations.getObligations().add((ObligationType)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
