package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.DescriptionType;

public class DescriptionTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      DescriptionType description = (DescriptionType)xmlObject;
      description.setValue(elementContent);
   }
}
