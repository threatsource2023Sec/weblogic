package com.bea.security.xacml.expression;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.FunctionAttribute;
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
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.Constant;
import com.bea.security.xacml.policy.VariableContext;
import java.util.Iterator;
import java.util.Map;

public class StandardExpressionFactory implements ExpressionFactory {
   public AttributeEvaluator createApply(Apply apply, Map variables, Configuration config, Iterator otherFactories) throws URISyntaxException, EvaluationPlanException {
      return config.getFunctionRegistry().getEvaluator(apply, variables, config);
   }

   public AttributeEvaluator createAttributeSelector(AttributeSelector selector, Configuration config, Iterator otherFactories) throws URISyntaxException, EvaluationPlanException {
      return null;
   }

   public AttributeEvaluator createFunction(Function function, Configuration config, Iterator otherFactories) throws URISyntaxException, EvaluationPlanException {
      return new Constant(new FunctionAttribute(function.getFunctionId()));
   }

   public AttributeEvaluator createVariableReference(VariableReference reference, Map variables, Configuration config, Iterator otherFactories) throws URISyntaxException, EvaluationPlanException {
      final String id = reference.getId();
      final AttributeEvaluator var = variables != null ? (AttributeEvaluator)variables.get(id) : null;
      if (var == null) {
         throw new EvaluationPlanException("No variable defintion found for reference to: " + id);
      } else {
         return new AttributeEvaluator() {
            public AttributeValue evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
               VariableContext vc = context.getVariableContext();
               if (vc == null) {
                  throw new IndeterminateEvaluationException("Variable context not available");
               } else {
                  return vc.getVariable(id);
               }
            }

            public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
               VariableContext vc = context.getVariableContext();
               if (vc == null) {
                  throw new IndeterminateEvaluationException("Variable context not available");
               } else {
                  return vc.getVariableAsBag(id);
               }
            }

            public Type getType() throws URISyntaxException {
               return var.getType();
            }
         };
      }
   }

   public AttributeEvaluator createActionAttributeDesignator(ActionAttributeDesignator designator, Configuration config, Iterator otherFactories) throws URISyntaxException, EvaluationPlanException {
      return config.getActionAttributeDesignatorRegistry().getDesignator(designator, config);
   }

   public AttributeEvaluator createResourceAttributeDesignator(ResourceAttributeDesignator designator, Configuration config, Iterator otherFactories) throws URISyntaxException, EvaluationPlanException {
      return config.getResourceAttributeDesignatorRegistry().getDesignator(designator, config);
   }

   public AttributeEvaluator createEnvironmentAttributeDesignator(EnvironmentAttributeDesignator designator, Configuration config, Iterator otherFactories) throws URISyntaxException, EvaluationPlanException {
      return config.getEnvironmentAttributeDesignatorRegistry().getDesignator(designator, config);
   }

   public AttributeEvaluator createSubjectAttributeDesignator(SubjectAttributeDesignator designator, Configuration config, Iterator otherFactories) throws URISyntaxException, EvaluationPlanException {
      return config.getSubjectAttributeDesignatorRegistry().getDesignator(designator, config);
   }
}
