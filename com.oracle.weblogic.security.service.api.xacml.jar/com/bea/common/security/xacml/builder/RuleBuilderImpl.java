package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.InvalidXACMLPolicyException;
import com.bea.common.security.xacml.PolicyUtils;
import com.bea.common.security.xacml.XACMLException;
import com.bea.common.security.xacml.policy.Condition;
import com.bea.common.security.xacml.policy.Rule;

class RuleBuilderImpl extends AbstractBuilderBase implements RuleBuilder {
   private String id;
   private boolean isEffectPermit = false;
   private Condition condition;

   RuleBuilderImpl() {
   }

   RuleBuilderImpl(Rule rule) throws XACMLException {
      if (rule == null) {
         throw new InvalidParameterException("The rule should not be null.");
      } else {
         super.init(rule.getTarget().getSubjects(), rule.getTarget().getResources(), rule.getTarget().getActions(), rule.getTarget().getEnvironments(), rule.getDescription());
         this.id = rule.getRuleId();
         this.isEffectPermit = rule.isEffectPermit();
         this.condition = rule.getCondition();
      }
   }

   RuleBuilderImpl(String ruleId, boolean isEffectPermit) throws XACMLException {
      if (ruleId != null && ruleId.trim().length() != 0) {
         this.id = ruleId;
         this.isEffectPermit = isEffectPermit;
      } else {
         throw new InvalidParameterException("The rule id should not be null or empty.");
      }
   }

   public Rule getResult() throws InvalidXACMLPolicyException {
      if (this.id != null && this.id.trim().length() != 0) {
         Rule rule = new Rule(this.id, this.isEffectPermit, this.getTarget(), this.condition, this.description);

         try {
            PolicyUtils.checkXACMLSchema(rule.toString());
            return rule;
         } catch (XACMLException var3) {
            throw new InvalidXACMLPolicyException(var3);
         }
      } else {
         throw new InvalidXACMLPolicyException("The rule id should not be null or empty.");
      }
   }

   public RuleBuilder setRuleId(String id) throws InvalidParameterException {
      if (id != null && id.trim().length() != 0) {
         this.id = id;
         return this;
      } else {
         throw new InvalidParameterException("The rule id should not be null or empty");
      }
   }

   public RuleBuilder setEffectPermit(boolean isEffectPermit) {
      this.isEffectPermit = isEffectPermit;
      return this;
   }

   public RuleBuilder setCondition(Expression expression) throws InvalidParameterException {
      if (expression == null) {
         throw new InvalidParameterException("The expression should not be null.");
      } else {
         this.condition = new Condition(expression.toXACML());
         return this;
      }
   }

   public Condition removeCondition() {
      Condition c = this.condition;
      this.condition = null;
      return c;
   }

   protected RuleBuilder getInstance() {
      return this;
   }
}
