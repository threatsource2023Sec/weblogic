package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.support.AbstractGenericPointcutAdvisor;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class AspectJExpressionPointcutAdvisor extends AbstractGenericPointcutAdvisor implements BeanFactoryAware {
   private final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

   public void setExpression(@Nullable String expression) {
      this.pointcut.setExpression(expression);
   }

   @Nullable
   public String getExpression() {
      return this.pointcut.getExpression();
   }

   public void setLocation(@Nullable String location) {
      this.pointcut.setLocation(location);
   }

   @Nullable
   public String getLocation() {
      return this.pointcut.getLocation();
   }

   public void setParameterNames(String... names) {
      this.pointcut.setParameterNames(names);
   }

   public void setParameterTypes(Class... types) {
      this.pointcut.setParameterTypes(types);
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.pointcut.setBeanFactory(beanFactory);
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }
}
