package com.bea.security.xacml.function;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Apply;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.InvalidArgumentsException;
import java.util.Iterator;
import java.util.Map;

public interface FunctionFactory {
   AttributeEvaluator createFunction(Apply var1, Map var2, Configuration var3, Iterator var4) throws InvalidArgumentsException, EvaluationPlanException, URISyntaxException;
}
