package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
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

public class PolicySetTypeImpl extends AbstractXACMLObject implements PolicySetType {
   private DescriptionType description;
   private DefaultsType policySetDefaults;
   private TargetType target;
   private IndexedXMLObjectChildrenList choiceGroup = new IndexedXMLObjectChildrenList(this);
   private ObligationsType obligations;
   private String policySetId;
   private String version = "1.0";
   private String combiningAlgo;

   protected PolicySetTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getCombinerParameters() {
      return this.choiceGroup.subList(CombinerParametersType.DEFAULT_ELEMENT_NAME);
   }

   public DescriptionType getDescription() {
      return this.description;
   }

   public ObligationsType getObligations() {
      return this.obligations;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.description != null) {
         children.add(this.description);
      }

      if (this.policySetDefaults != null) {
         children.add(this.policySetDefaults);
      }

      children.add(this.target);
      if (!this.choiceGroup.isEmpty()) {
         children.addAll(this.choiceGroup);
      }

      if (this.obligations != null) {
         children.add(this.obligations);
      }

      return children;
   }

   public List getPolicies() {
      return this.choiceGroup.subList(PolicyType.DEFAULT_ELEMENT_NAME);
   }

   public List getPolicyCombinerParameters() {
      return this.choiceGroup.subList(PolicyCombinerParametersType.DEFAULT_ELEMENT_NAME);
   }

   public String getPolicyCombiningAlgoId() {
      return this.combiningAlgo;
   }

   public List getPolicyIdReferences() {
      return this.choiceGroup.subList(IdReferenceType.POLICY_ID_REFERENCE_ELEMENT_NAME);
   }

   public List getPolicySetCombinerParameters() {
      return this.choiceGroup.subList(PolicySetCombinerParametersType.DEFAULT_ELEMENT_NAME);
   }

   public DefaultsType getPolicySetDefaults() {
      return this.policySetDefaults;
   }

   public String getPolicySetId() {
      return this.policySetId;
   }

   public List getPolicySetIdReferences() {
      return this.choiceGroup.subList(IdReferenceType.POLICY_SET_ID_REFERENCE_ELEMENT_NAME);
   }

   public List getPolicySets() {
      return this.choiceGroup.subList(PolicySetType.DEFAULT_ELEMENT_NAME);
   }

   public TargetType getTarget() {
      return this.target;
   }

   public String getVersion() {
      return this.version;
   }

   public void setDescription(DescriptionType newDescription) {
      this.description = (DescriptionType)this.prepareForAssignment(this.description, newDescription);
   }

   public void setObligations(ObligationsType newObligations) {
      this.obligations = (ObligationsType)this.prepareForAssignment(this.obligations, newObligations);
   }

   public void setPolicyCombiningAlgoId(String id) {
      this.combiningAlgo = this.prepareForAssignment(this.combiningAlgo, id);
   }

   public void setPolicySetDefaults(DefaultsType defaults) {
      this.policySetDefaults = (DefaultsType)this.prepareForAssignment(this.policySetDefaults, defaults);
   }

   public void setPolicySetId(String id) {
      this.policySetId = this.prepareForAssignment(this.policySetId, id);
   }

   public void setTarget(TargetType newTarget) {
      this.target = (TargetType)this.prepareForAssignment(this.target, newTarget);
   }

   public void setVersion(String newVersion) {
      this.version = this.prepareForAssignment(this.version, newVersion);
   }

   public IndexedXMLObjectChildrenList getPolicyChoiceGroup() {
      return this.choiceGroup;
   }
}
