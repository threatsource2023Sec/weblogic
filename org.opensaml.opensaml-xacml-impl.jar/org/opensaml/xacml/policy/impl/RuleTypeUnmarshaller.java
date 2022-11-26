package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ConditionType;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.RuleType;
import org.opensaml.xacml.policy.TargetType;
import org.w3c.dom.Attr;

public class RuleTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      RuleType ruleType = (RuleType)xmlObject;
      if (attribute.getLocalName().equals("Effect")) {
         ruleType.setEffect(EffectType.valueOf(StringSupport.trimOrNull(attribute.getValue())));
      } else if (attribute.getLocalName().equals("RuleId")) {
         ruleType.setRuleId(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RuleType ruleType = (RuleType)parentXMLObject;
      if (childXMLObject instanceof TargetType) {
         ruleType.setTarget((TargetType)childXMLObject);
      } else if (childXMLObject instanceof DescriptionType) {
         ruleType.setDescription((DescriptionType)childXMLObject);
      } else if (childXMLObject instanceof ConditionType) {
         ruleType.setCondition((ConditionType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
