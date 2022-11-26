package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.CombinerParametersType;
import org.opensaml.xacml.policy.DefaultsType;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xacml.policy.IdReferenceType;
import org.opensaml.xacml.policy.ObligationsType;
import org.opensaml.xacml.policy.PolicyCombinerParametersType;
import org.opensaml.xacml.policy.PolicySetCombinerParametersType;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.policy.TargetType;
import org.w3c.dom.Attr;

public class PolicySetTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      PolicySetType policySet = (PolicySetType)xmlObject;
      if (attribute.getLocalName().equals("PolicySetId")) {
         policySet.setPolicySetId(attribute.getValue());
      } else if (attribute.getLocalName().equals("Version")) {
         policySet.setVersion(attribute.getValue());
      } else if (attribute.getLocalName().equals("PolicyCombiningAlgId")) {
         policySet.setPolicyCombiningAlgoId(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      PolicySetType policySet = (PolicySetType)parentXMLObject;
      if (childXMLObject instanceof DescriptionType) {
         policySet.setDescription((DescriptionType)childXMLObject);
      } else if (childXMLObject instanceof DefaultsType) {
         policySet.setPolicySetDefaults((DefaultsType)childXMLObject);
      } else if (childXMLObject instanceof TargetType) {
         policySet.setTarget((TargetType)childXMLObject);
      } else if (childXMLObject instanceof PolicySetType) {
         policySet.getPolicySets().add((PolicySetType)childXMLObject);
      } else if (childXMLObject instanceof PolicyType) {
         policySet.getPolicies().add((PolicyType)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(IdReferenceType.POLICY_SET_ID_REFERENCE_ELEMENT_NAME)) {
         policySet.getPolicySetIdReferences().add((IdReferenceType)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(IdReferenceType.POLICY_ID_REFERENCE_ELEMENT_NAME)) {
         policySet.getPolicyIdReferences().add((IdReferenceType)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(CombinerParametersType.DEFAULT_ELEMENT_NAME)) {
         policySet.getCombinerParameters().add((CombinerParametersType)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(PolicyCombinerParametersType.DEFAULT_ELEMENT_NAME)) {
         policySet.getPolicyCombinerParameters().add((PolicyCombinerParametersType)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(PolicySetCombinerParametersType.DEFAULT_ELEMENT_NAME)) {
         policySet.getPolicySetCombinerParameters().add((PolicySetCombinerParametersType)childXMLObject);
      } else if (childXMLObject instanceof ObligationsType) {
         policySet.setObligations((ObligationsType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
