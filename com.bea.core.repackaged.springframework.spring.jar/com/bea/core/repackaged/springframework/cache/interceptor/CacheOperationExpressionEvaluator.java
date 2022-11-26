package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.context.expression.AnnotatedElementKey;
import com.bea.core.repackaged.springframework.context.expression.BeanFactoryResolver;
import com.bea.core.repackaged.springframework.context.expression.CachedExpressionEvaluator;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class CacheOperationExpressionEvaluator extends CachedExpressionEvaluator {
   public static final Object NO_RESULT = new Object();
   public static final Object RESULT_UNAVAILABLE = new Object();
   public static final String RESULT_VARIABLE = "result";
   private final Map keyCache = new ConcurrentHashMap(64);
   private final Map conditionCache = new ConcurrentHashMap(64);
   private final Map unlessCache = new ConcurrentHashMap(64);

   public EvaluationContext createEvaluationContext(Collection caches, Method method, Object[] args, Object target, Class targetClass, Method targetMethod, @Nullable Object result, @Nullable BeanFactory beanFactory) {
      CacheExpressionRootObject rootObject = new CacheExpressionRootObject(caches, method, args, target, targetClass);
      CacheEvaluationContext evaluationContext = new CacheEvaluationContext(rootObject, targetMethod, args, this.getParameterNameDiscoverer());
      if (result == RESULT_UNAVAILABLE) {
         evaluationContext.addUnavailableVariable("result");
      } else if (result != NO_RESULT) {
         evaluationContext.setVariable("result", result);
      }

      if (beanFactory != null) {
         evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
      }

      return evaluationContext;
   }

   @Nullable
   public Object key(String keyExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
      return this.getExpression(this.keyCache, methodKey, keyExpression).getValue(evalContext);
   }

   public boolean condition(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
      return Boolean.TRUE.equals(this.getExpression(this.conditionCache, methodKey, conditionExpression).getValue(evalContext, Boolean.class));
   }

   public boolean unless(String unlessExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
      return Boolean.TRUE.equals(this.getExpression(this.unlessCache, methodKey, unlessExpression).getValue(evalContext, Boolean.class));
   }

   void clear() {
      this.keyCache.clear();
      this.conditionCache.clear();
      this.unlessCache.clear();
   }
}
