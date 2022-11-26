package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.SubjectType;
import org.opensaml.xacml.policy.SubjectsType;

public class SubjectsTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      SubjectsType subjectsType = (SubjectsType)parentXMLObject;
      if (childXMLObject instanceof SubjectType) {
         subjectsType.getSubjects().add((SubjectType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
