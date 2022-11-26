package com.bea.security.xacml.combinator;

import com.bea.common.security.xacml.URI;
import com.bea.security.xacml.RuleCombinerEvaluator;
import java.util.List;

public interface SimpleRuleCombinerEvaluatorLibrary {
   RuleCombinerEvaluator createCombiner(URI var1, List var2);
}
