package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.policy.PolicyCombinerParametersType;
import org.w3c.dom.Attr;

public class PolicyCombinerParametersTypeUnmarshaller extends CombinerParametersTypeUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("PolicyIdRef")) {
         PolicyCombinerParametersType policyCombinerParametersType = (PolicyCombinerParametersType)xmlObject;
         policyCombinerParametersType.setPolicyIdRef(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
