package com.bea.security.xacml.rule;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Rule;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.RuleEvaluator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public interface RuleEvaluatorFactory {
   RuleEvaluator createRule(Rule var1, Collection var2, Map var3, Configuration var4, Iterator var5) throws EvaluationPlanException, URISyntaxException;
}
