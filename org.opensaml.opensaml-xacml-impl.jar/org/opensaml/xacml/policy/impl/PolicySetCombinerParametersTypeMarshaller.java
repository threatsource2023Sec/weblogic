package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.policy.PolicySetCombinerParametersType;
import org.w3c.dom.Element;

public class PolicySetCombinerParametersTypeMarshaller extends CombinerParametersTypeMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      PolicySetCombinerParametersType policySetCombinerParametersType = (PolicySetCombinerParametersType)xmlObject;
      if (!Strings.isNullOrEmpty(policySetCombinerParametersType.getPolicySetIdRef())) {
         domElement.setAttributeNS((String)null, "PolicySetIdRef", policySetCombinerParametersType.getPolicySetIdRef());
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
