package com.bea.security.xacml.policy;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.PolicyEvaluator;
import java.util.Collection;
import java.util.Iterator;

public interface PolicyEvaluatorFactory {
   PolicyEvaluator createPolicy(Policy var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;

   PolicyEvaluator createPolicySet(PolicySet var1, Collection var2, Configuration var3, Iterator var4) throws EvaluationPlanException, URISyntaxException;
}
