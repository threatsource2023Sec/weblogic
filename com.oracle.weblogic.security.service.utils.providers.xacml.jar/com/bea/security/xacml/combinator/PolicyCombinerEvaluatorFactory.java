package com.bea.security.xacml.combinator;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.InvalidReferenceException;
import com.bea.security.xacml.PolicyEvaluator;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface PolicyCombinerEvaluatorFactory {
   PolicyEvaluator createCombiner(URI var1, List var2, List var3, List var4, List var5, Collection var6, Configuration var7, Iterator var8) throws InvalidReferenceException, EvaluationPlanException, URISyntaxException;
}
