package com.bea.security.xacml.combinator;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.RuleCombinerEvaluator;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface RuleCombinerEvaluatorFactory {
   RuleCombinerEvaluator createCombiner(URI var1, List var2, List var3, List var4, Collection var5, Map var6, Configuration var7, Iterator var8) throws EvaluationPlanException, URISyntaxException;
}
