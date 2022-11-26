package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.context.ApplicationEvent;
import com.bea.core.repackaged.springframework.context.expression.AnnotatedElementKey;
import com.bea.core.repackaged.springframework.context.expression.BeanFactoryResolver;
import com.bea.core.repackaged.springframework.context.expression.CachedExpressionEvaluator;
import com.bea.core.repackaged.springframework.context.expression.MethodBasedEvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class EventExpressionEvaluator extends CachedExpressionEvaluator {
   private final Map conditionCache = new ConcurrentHashMap(64);

   public boolean condition(String conditionExpression, ApplicationEvent event, Method targetMethod, AnnotatedElementKey methodKey, Object[] args, @Nullable BeanFactory beanFactory) {
      EventExpressionRootObject root = new EventExpressionRootObject(event, args);
      MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(root, targetMethod, args, this.getParameterNameDiscoverer());
      if (beanFactory != null) {
         evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
      }

      return Boolean.TRUE.equals(this.getExpression(this.conditionCache, methodKey, conditionExpression).getValue((EvaluationContext)evaluationContext, (Class)Boolean.class));
   }
}
