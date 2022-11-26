package com.bea.security.xacml.combinator;

import com.bea.common.security.xacml.URI;
import com.bea.security.xacml.PolicyEvaluator;
import java.util.List;

public interface SimplePolicyCombinerEvaluatorLibrary {
   PolicyEvaluator createCombiner(URI var1, List var2);
}
