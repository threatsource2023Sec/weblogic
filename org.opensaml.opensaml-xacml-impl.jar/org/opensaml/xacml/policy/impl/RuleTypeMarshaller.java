package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.RuleType;
import org.w3c.dom.Element;

public class RuleTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      RuleType ruleType = (RuleType)xmlObject;
      if (!Strings.isNullOrEmpty(ruleType.getRuleId())) {
         domElement.setAttributeNS((String)null, "RuleId", ruleType.getRuleId());
      }

      if (!Strings.isNullOrEmpty(ruleType.getEffect().toString())) {
         if (ruleType.getEffect().equals(EffectType.Deny)) {
            domElement.setAttributeNS((String)null, "Effect", EffectType.Deny.toString());
         } else {
            domElement.setAttributeNS((String)null, "Effect", EffectType.Permit.toString());
         }
      }

   }
}
