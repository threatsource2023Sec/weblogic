package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.InvalidXACMLPolicyException;
import com.bea.common.security.xacml.policy.Condition;
import com.bea.common.security.xacml.policy.Rule;

public interface RuleBuilder extends BuilderBase {
   RuleBuilder setRuleId(String var1) throws InvalidParameterException;

   RuleBuilder setEffectPermit(boolean var1);

   RuleBuilder setCondition(Expression var1) throws InvalidParameterException;

   Condition removeCondition();

   Rule getResult() throws InvalidXACMLPolicyException;
}
