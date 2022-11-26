package com.oracle.weblogic.diagnostics.expressions;

import java.lang.annotation.Annotation;
import javax.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;

@Service
public class EvaluatorFactoryImpl implements EvaluatorFactory {
   @Inject
   private ServiceLocator locator;

   public ExpressionEvaluator createEvaluator(Annotation... qualifiers) {
      return this.createEvaluator((String)null, Object.class, qualifiers);
   }

   public FixedExpressionEvaluator createEvaluator(String expression, Class expectedResultType, Annotation... qualifiers) {
      ExpressionEvaluatorImpl evaluator = new ExpressionEvaluatorImpl(expression, expectedResultType, qualifiers);
      this.locator.inject(evaluator);
      this.locator.postConstruct(evaluator);
      return evaluator;
   }

   public void destroyEvaluator(ExpressionEvaluator evaluator) {
      this.locator.preDestroy(evaluator);
   }
}
