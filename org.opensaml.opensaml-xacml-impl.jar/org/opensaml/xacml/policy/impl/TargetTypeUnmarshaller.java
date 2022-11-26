package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ActionsType;
import org.opensaml.xacml.policy.EnvironmentsType;
import org.opensaml.xacml.policy.ResourcesType;
import org.opensaml.xacml.policy.SubjectsType;
import org.opensaml.xacml.policy.TargetType;

public class TargetTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      TargetType targetType = (TargetType)parentXMLObject;
      if (childXMLObject.getElementQName().equals(ActionsType.DEFAULT_ELEMENT_NAME)) {
         targetType.setActions((ActionsType)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(EnvironmentsType.DEFAULT_ELEMENT_NAME)) {
         targetType.setEnvironments((EnvironmentsType)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(ResourcesType.DEFAULT_ELEMENT_NAME)) {
         targetType.setResources((ResourcesType)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(SubjectsType.DEFAULT_ELEMENT_NAME)) {
         targetType.setSubjects((SubjectsType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
