package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.PolicyType;
import org.w3c.dom.Element;

public class PolicyTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      PolicyType policy = (PolicyType)xmlObject;
      if (!Strings.isNullOrEmpty(policy.getPolicyId())) {
         domElement.setAttributeNS((String)null, "PolicyId", policy.getPolicyId());
      }

      if (!Strings.isNullOrEmpty(policy.getVersion())) {
         domElement.setAttributeNS((String)null, "Version", policy.getVersion());
      }

      if (!Strings.isNullOrEmpty(policy.getRuleCombiningAlgoId())) {
         domElement.setAttributeNS((String)null, "RuleCombiningAlgId", policy.getRuleCombiningAlgoId());
      }

   }
}
