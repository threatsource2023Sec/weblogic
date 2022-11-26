package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.CombinerParametersType;
import org.opensaml.xacml.policy.DefaultsType;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xacml.policy.ObligationsType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.policy.RuleCombinerParametersType;
import org.opensaml.xacml.policy.RuleType;
import org.opensaml.xacml.policy.TargetType;
import org.opensaml.xacml.policy.VariableDefinitionType;

public class PolicyTypeImpl extends AbstractXACMLObject implements PolicyType {
   private DescriptionType description;
   private DefaultsType policyDefaults;
   private TargetType target;
   private IndexedXMLObjectChildrenList choiceGroup = new IndexedXMLObjectChildrenList(this);
   private ObligationsType obligations;
   private String policyId;
   private String version = "1.0";
   private String ruleCombiningAlgo;

   protected PolicyTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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

   public DefaultsType getPolicyDefaults() {
      return this.policyDefaults;
   }

   public String getPolicyId() {
      return this.policyId;
   }

   public List getRuleCombinerParameters() {
      return this.choiceGroup.subList(RuleCombinerParametersType.DEFAULT_ELEMENT_NAME);
   }

   public String getRuleCombiningAlgoId() {
      return this.ruleCombiningAlgo;
   }

   public List getRules() {
      return this.choiceGroup.subList(RuleType.DEFAULT_ELEMENT_NAME);
   }

   public TargetType getTarget() {
      return this.target;
   }

   public List getVariableDefinitions() {
      return this.choiceGroup.subList(VariableDefinitionType.DEFAULT_ELEMENT_NAME);
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

   public void setPolicyDefaults(DefaultsType defaults) {
      this.policyDefaults = (DefaultsType)this.prepareForAssignment(this.policyDefaults, defaults);
   }

   public void setPolicyId(String id) {
      this.policyId = this.prepareForAssignment(this.policyId, id);
   }

   public void setRuleCombiningAlgoId(String id) {
      this.ruleCombiningAlgo = this.prepareForAssignment(this.ruleCombiningAlgo, id);
   }

   public void setTarget(TargetType newTarget) {
      this.target = (TargetType)this.prepareForAssignment(this.target, newTarget);
   }

   public void setVersion(String newVersion) {
      this.version = this.prepareForAssignment(this.version, newVersion);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.description != null) {
         children.add(this.description);
      }

      if (this.policyDefaults != null) {
         children.add(this.policyDefaults);
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
}
