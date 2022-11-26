package com.bea.core.repackaged.springframework.context.expression;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.BeanResolver;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.util.Assert;

public class BeanFactoryResolver implements BeanResolver {
   private final BeanFactory beanFactory;

   public BeanFactoryResolver(BeanFactory beanFactory) {
      Assert.notNull(beanFactory, (String)"BeanFactory must not be null");
      this.beanFactory = beanFactory;
   }

   public Object resolve(EvaluationContext context, String beanName) throws AccessException {
      try {
         return this.beanFactory.getBean(beanName);
      } catch (BeansException var4) {
         throw new AccessException("Could not resolve bean reference against BeanFactory", var4);
      }
   }
}
