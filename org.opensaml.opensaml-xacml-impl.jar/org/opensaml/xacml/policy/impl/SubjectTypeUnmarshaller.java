package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.SubjectMatchType;
import org.opensaml.xacml.policy.SubjectType;

public class SubjectTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      SubjectType subjectType = (SubjectType)parentXMLObject;
      if (childXMLObject instanceof SubjectMatchType) {
         subjectType.getSubjectMatches().add((SubjectMatchType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
