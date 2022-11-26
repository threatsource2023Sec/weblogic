package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.RuleCombinerParametersType;
import org.w3c.dom.Element;

public class RuleCombinerParametersTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      RuleCombinerParametersType ruleCombinerParametersType = (RuleCombinerParametersType)xmlObject;
      if (!Strings.isNullOrEmpty(ruleCombinerParametersType.getRuleIdRef())) {
         domElement.setAttributeNS((String)null, "RuleIdRef", ruleCombinerParametersType.getRuleIdRef());
      }

   }
}
