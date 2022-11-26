package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.InvalidXACMLPolicyException;
import com.bea.common.security.xacml.policy.Obligation;
import com.bea.common.security.xacml.policy.Obligations;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.PolicySetMember;
import java.util.List;

public interface PolicySetBuilder extends BuilderBase {
   PolicySetBuilder setPolicySetId(String var1) throws InvalidParameterException;

   PolicySetBuilder setVersion(String var1) throws InvalidParameterException;

   PolicySetBuilder setPolicyCombiningAlgorithm(CombiningAlgorithm var1) throws InvalidParameterException;

   PolicySetBuilder addPolicyIdRef(String var1) throws InvalidParameterException;

   PolicySetBuilder addPolicyIdRef(String var1, String var2, String var3, String var4) throws InvalidParameterException;

   PolicySetBuilder addPolicySetIdRef(String var1) throws InvalidParameterException;

   PolicySetBuilder addPolicySetIdRef(String var1, String var2, String var3, String var4) throws InvalidParameterException;

   PolicySetBuilder addPolicy(Policy var1);

   PolicySetBuilder addPolicySet(PolicySet var1);

   PolicySetBuilder addObligation(Obligation var1);

   PolicySetMember removePolicy(String var1) throws InvalidParameterException;

   PolicySetMember removePolicy(String var1, String var2) throws InvalidParameterException;

   PolicySetMember removePolicySet(String var1) throws InvalidParameterException;

   PolicySetMember removePolicySet(String var1, String var2) throws InvalidParameterException;

   List removeAll();

   Obligations removeObligations();

   String getResultAsXML() throws InvalidXACMLPolicyException;

   PolicySet getResult() throws InvalidXACMLPolicyException;
}
