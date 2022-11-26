package com.bea.security.xacml.combinator;

import com.bea.security.xacml.PolicyEvaluator;
import java.util.List;

public interface SimplePolicyCombinerEvaluatorFactory {
   PolicyEvaluator createCombiner(List var1);
}
