package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.InvalidXACMLPolicyException;
import com.bea.common.security.xacml.policy.Obligation;
import com.bea.common.security.xacml.policy.Obligations;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.Rule;
import java.util.List;

public interface PolicyBuilder extends BuilderBase {
   PolicyBuilder setPolicyId(String var1) throws InvalidParameterException;

   PolicyBuilder setVersion(String var1) throws InvalidParameterException;

   PolicyBuilder addRule(Rule var1);

   PolicyBuilder setRuleCombiningAlgorithm(CombiningAlgorithm var1) throws InvalidParameterException;

   PolicyBuilder addObligation(Obligation var1);

   Rule removeRule(String var1);

   List removeAllRules();

   Obligations removeObligations();

   String getResultAsXML() throws InvalidXACMLPolicyException;

   Policy getResult() throws InvalidXACMLPolicyException;
}
