package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.CombinerParameterType;
import org.opensaml.xacml.policy.CombinerParametersType;

public class CombinerParametersTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      CombinerParametersType combinerParametersType = (CombinerParametersType)parentXMLObject;
      if (childXMLObject instanceof CombinerParameterType) {
         combinerParametersType.getCombinerParameters().add((CombinerParameterType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
