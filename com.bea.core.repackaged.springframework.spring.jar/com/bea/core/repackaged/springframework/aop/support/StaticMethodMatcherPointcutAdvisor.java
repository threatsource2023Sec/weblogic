package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.PointcutAdvisor;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public abstract class StaticMethodMatcherPointcutAdvisor extends StaticMethodMatcherPointcut implements PointcutAdvisor, Ordered, Serializable {
   private Advice advice;
   private int order;

   public StaticMethodMatcherPointcutAdvisor() {
      this.advice = EMPTY_ADVICE;
      this.order = Integer.MAX_VALUE;
   }

   public StaticMethodMatcherPointcutAdvisor(Advice advice) {
      this.advice = EMPTY_ADVICE;
      this.order = Integer.MAX_VALUE;
      Assert.notNull(advice, (String)"Advice must not be null");
      this.advice = advice;
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public void setAdvice(Advice advice) {
      this.advice = advice;
   }

   public Advice getAdvice() {
      return this.advice;
   }

   public boolean isPerInstance() {
      return true;
   }

   public Pointcut getPointcut() {
      return this;
   }
}
