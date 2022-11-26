package com.bea.security.xacml.expression;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.ActionAttributeDesignator;
import com.bea.common.security.xacml.policy.Apply;
import com.bea.common.security.xacml.policy.AttributeSelector;
import com.bea.common.security.xacml.policy.AttributeValue;
import com.bea.common.security.xacml.policy.EnvironmentAttributeDesignator;
import com.bea.common.security.xacml.policy.Expression;
import com.bea.common.security.xacml.policy.Function;
import com.bea.common.security.xacml.policy.ResourceAttributeDesignator;
import com.bea.common.security.xacml.policy.SubjectAttributeDesignator;
import com.bea.common.security.xacml.policy.VariableReference;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.MissingFactoryException;
import com.bea.security.xacml.attr.evaluator.Constant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ExpressionRegistry {
   private List factories = Collections.synchronizedList(new ArrayList());

   public ExpressionRegistry() throws URISyntaxException {
      this.register(new StandardExpressionFactory());
   }

   public void register(ExpressionFactory factory) {
      this.factories.add(factory);
   }

   public AttributeEvaluator createApply(Apply apply, Map variables, Configuration config) throws URISyntaxException, EvaluationPlanException {
      AttributeEvaluator ae = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for apply element: " + apply);
         }

         ExpressionFactory ff = (ExpressionFactory)facIt.next();
         ae = ff.createApply(apply, variables, config, this.factories.listIterator(facIt.nextIndex()));
      } while(ae == null);

      return ae;
   }

   public AttributeEvaluator createAttributeSelector(AttributeSelector selector, Configuration config) throws URISyntaxException, EvaluationPlanException {
      AttributeEvaluator ae = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for attribute selector: " + selector);
         }

         ExpressionFactory ff = (ExpressionFactory)facIt.next();
         ae = ff.createAttributeSelector(selector, config, this.factories.listIterator(facIt.nextIndex()));
      } while(ae == null);

      return ae;
   }

   public AttributeEvaluator createFunction(Function function, Configuration config) throws URISyntaxException, EvaluationPlanException {
      AttributeEvaluator ae = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for function: " + function);
         }

         ExpressionFactory ff = (ExpressionFactory)facIt.next();
         ae = ff.createFunction(function, config, this.factories.listIterator(facIt.nextIndex()));
      } while(ae == null);

      return ae;
   }

   public AttributeEvaluator createVariableReference(VariableReference reference, Map variables, Configuration config) throws URISyntaxException, EvaluationPlanException {
      AttributeEvaluator ae = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for variable reference: " + reference);
         }

         ExpressionFactory ff = (ExpressionFactory)facIt.next();
         ae = ff.createVariableReference(reference, variables, config, this.factories.listIterator(facIt.nextIndex()));
      } while(ae == null);

      return ae;
   }

   public AttributeEvaluator createActionAttributeDesignator(ActionAttributeDesignator designator, Configuration config) throws URISyntaxException, EvaluationPlanException {
      AttributeEvaluator ae = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for action attribute designator: " + designator);
         }

         ExpressionFactory ff = (ExpressionFactory)facIt.next();
         ae = ff.createActionAttributeDesignator(designator, config, this.factories.listIterator(facIt.nextIndex()));
      } while(ae == null);

      return ae;
   }

   public AttributeEvaluator createResourceAttributeDesignator(ResourceAttributeDesignator designator, Configuration config) throws URISyntaxException, EvaluationPlanException {
      AttributeEvaluator ae = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for resource attribute designator: " + designator);
         }

         ExpressionFactory ff = (ExpressionFactory)facIt.next();
         ae = ff.createResourceAttributeDesignator(designator, config, this.factories.listIterator(facIt.nextIndex()));
      } while(ae == null);

      return ae;
   }

   public AttributeEvaluator createEnvironmentAttributeDesignator(EnvironmentAttributeDesignator designator, Configuration config) throws URISyntaxException, EvaluationPlanException {
      AttributeEvaluator ae = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for environment attribute designator: " + designator);
         }

         ExpressionFactory ff = (ExpressionFactory)facIt.next();
         ae = ff.createEnvironmentAttributeDesignator(designator, config, this.factories.listIterator(facIt.nextIndex()));
      } while(ae == null);

      return ae;
   }

   public AttributeEvaluator createSubjectAttributeDesignator(SubjectAttributeDesignator designator, Configuration config) throws URISyntaxException, EvaluationPlanException {
      AttributeEvaluator ae = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for subject attribute designator: " + designator);
         }

         ExpressionFactory ff = (ExpressionFactory)facIt.next();
         ae = ff.createSubjectAttributeDesignator(designator, config, this.factories.listIterator(facIt.nextIndex()));
      } while(ae == null);

      return ae;
   }

   public AttributeEvaluator parse(Expression expression, Map variables, Configuration config) throws URISyntaxException, EvaluationPlanException {
      if (expression instanceof AttributeEvaluator) {
         return (AttributeEvaluator)expression;
      } else if (expression instanceof Apply) {
         return this.createApply((Apply)expression, variables, config);
      } else if (expression instanceof AttributeSelector) {
         return this.createAttributeSelector((AttributeSelector)expression, config);
      } else if (expression instanceof AttributeValue) {
         return new Constant(((AttributeValue)expression).getValue());
      } else if (expression instanceof Function) {
         return this.createFunction((Function)expression, config);
      } else if (expression instanceof VariableReference) {
         return this.createVariableReference((VariableReference)expression, variables, config);
      } else if (expression instanceof SubjectAttributeDesignator) {
         return this.createSubjectAttributeDesignator((SubjectAttributeDesignator)expression, config);
      } else if (expression instanceof ActionAttributeDesignator) {
         return this.createActionAttributeDesignator((ActionAttributeDesignator)expression, config);
      } else if (expression instanceof ResourceAttributeDesignator) {
         return this.createResourceAttributeDesignator((ResourceAttributeDesignator)expression, config);
      } else if (expression instanceof EnvironmentAttributeDesignator) {
         return this.createEnvironmentAttributeDesignator((EnvironmentAttributeDesignator)expression, config);
      } else {
         throw new EvaluationPlanException("Unknown expression type");
      }
   }
}
