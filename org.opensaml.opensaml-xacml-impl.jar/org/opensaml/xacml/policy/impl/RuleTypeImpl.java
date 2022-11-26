package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ConditionType;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.RuleType;
import org.opensaml.xacml.policy.TargetType;

public class RuleTypeImpl extends AbstractXACMLObject implements RuleType {
   private ConditionType condition;
   private TargetType target;
   private DescriptionType description;
   private EffectType effectType;
   private String ruleId;

   protected RuleTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public ConditionType getCondition() {
      return this.condition;
   }

   public DescriptionType getDescription() {
      return this.description;
   }

   public EffectType getEffect() {
      return this.effectType;
   }

   public String getRuleId() {
      return this.ruleId;
   }

   public TargetType getTarget() {
      return this.target;
   }

   public void setCondition(ConditionType newCondition) {
      this.condition = (ConditionType)this.prepareForAssignment(this.condition, newCondition);
   }

   public void setDescription(DescriptionType newDescription) {
      this.description = (DescriptionType)this.prepareForAssignment(this.description, newDescription);
   }

   public void setEffect(EffectType type) {
      this.effectType = (EffectType)this.prepareForAssignment(this.effectType, type);
   }

   public void setRuleId(String id) {
      this.ruleId = this.prepareForAssignment(this.ruleId, id);
   }

   public void setTarget(TargetType newTarget) {
      this.target = (TargetType)this.prepareForAssignment(this.target, newTarget);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.description != null) {
         children.add(this.description);
      }

      if (this.target != null) {
         children.add(this.target);
      }

      if (this.condition != null) {
         children.add(this.condition);
      }

      return Collections.unmodifiableList(children);
   }
}
