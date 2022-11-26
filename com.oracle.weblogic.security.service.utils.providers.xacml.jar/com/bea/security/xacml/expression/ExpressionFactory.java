package com.bea.security.xacml.expression;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.ActionAttributeDesignator;
import com.bea.common.security.xacml.policy.Apply;
import com.bea.common.security.xacml.policy.AttributeSelector;
import com.bea.common.security.xacml.policy.EnvironmentAttributeDesignator;
import com.bea.common.security.xacml.policy.Function;
import com.bea.common.security.xacml.policy.ResourceAttributeDesignator;
import com.bea.common.security.xacml.policy.SubjectAttributeDesignator;
import com.bea.common.security.xacml.policy.VariableReference;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import java.util.Iterator;
import java.util.Map;

public interface ExpressionFactory {
   AttributeEvaluator createApply(Apply var1, Map var2, Configuration var3, Iterator var4) throws URISyntaxException, EvaluationPlanException;

   AttributeEvaluator createAttributeSelector(AttributeSelector var1, Configuration var2, Iterator var3) throws URISyntaxException, EvaluationPlanException;

   AttributeEvaluator createFunction(Function var1, Configuration var2, Iterator var3) throws URISyntaxException, EvaluationPlanException;

   AttributeEvaluator createVariableReference(VariableReference var1, Map var2, Configuration var3, Iterator var4) throws URISyntaxException, EvaluationPlanException;

   AttributeEvaluator createActionAttributeDesignator(ActionAttributeDesignator var1, Configuration var2, Iterator var3) throws URISyntaxException, EvaluationPlanException;

   AttributeEvaluator createResourceAttributeDesignator(ResourceAttributeDesignator var1, Configuration var2, Iterator var3) throws URISyntaxException, EvaluationPlanException;

   AttributeEvaluator createEnvironmentAttributeDesignator(EnvironmentAttributeDesignator var1, Configuration var2, Iterator var3) throws URISyntaxException, EvaluationPlanException;

   AttributeEvaluator createSubjectAttributeDesignator(SubjectAttributeDesignator var1, Configuration var2, Iterator var3) throws URISyntaxException, EvaluationPlanException;
}
