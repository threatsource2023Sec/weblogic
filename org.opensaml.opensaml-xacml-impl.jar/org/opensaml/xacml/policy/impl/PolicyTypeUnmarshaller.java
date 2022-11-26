package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.CombinerParametersType;
import org.opensaml.xacml.policy.DefaultsType;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xacml.policy.ObligationsType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.policy.RuleCombinerParametersType;
import org.opensaml.xacml.policy.RuleType;
import org.opensaml.xacml.policy.TargetType;
import org.opensaml.xacml.policy.VariableDefinitionType;
import org.w3c.dom.Attr;

public class PolicyTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      PolicyType policy = (PolicyType)xmlObject;
      if (attribute.getLocalName().equals("PolicyId")) {
         policy.setPolicyId(attribute.getValue());
      } else if (attribute.getLocalName().equals("Version")) {
         policy.setVersion(attribute.getValue());
      } else if (attribute.getLocalName().equals("RuleCombiningAlgId")) {
         policy.setRuleCombiningAlgoId(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      PolicyType policy = (PolicyType)parentXMLObject;
      if (childXMLObject instanceof DescriptionType) {
         policy.setDescription((DescriptionType)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(DefaultsType.POLICY_DEFAULTS_ELEMENT_NAME)) {
         policy.setPolicyDefaults((DefaultsType)childXMLObject);
      } else if (childXMLObject instanceof TargetType) {
         policy.setTarget((TargetType)childXMLObject);
      } else if (childXMLObject instanceof CombinerParametersType) {
         policy.getCombinerParameters().add((CombinerParametersType)childXMLObject);
      } else if (childXMLObject instanceof RuleCombinerParametersType) {
         policy.getRuleCombinerParameters().add((RuleCombinerParametersType)childXMLObject);
      } else if (childXMLObject instanceof VariableDefinitionType) {
         policy.getVariableDefinitions().add((VariableDefinitionType)childXMLObject);
      } else if (childXMLObject instanceof RuleType) {
         policy.getRules().add((RuleType)childXMLObject);
      } else if (childXMLObject instanceof ObligationsType) {
         policy.setObligations((ObligationsType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
