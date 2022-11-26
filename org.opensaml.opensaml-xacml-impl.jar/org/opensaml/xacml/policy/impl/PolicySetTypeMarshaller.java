package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.PolicySetType;
import org.w3c.dom.Element;

public class PolicySetTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      PolicySetType policySet = (PolicySetType)xmlObject;
      if (!Strings.isNullOrEmpty(policySet.getPolicySetId())) {
         domElement.setAttributeNS((String)null, "PolicySetId", policySet.getPolicySetId());
      }

      if (!Strings.isNullOrEmpty(policySet.getVersion())) {
         domElement.setAttributeNS((String)null, "Version", policySet.getVersion());
      }

      if (!Strings.isNullOrEmpty(policySet.getPolicyCombiningAlgoId())) {
         domElement.setAttributeNS((String)null, "PolicyCombiningAlgId", policySet.getPolicyCombiningAlgoId());
      }

   }
}
