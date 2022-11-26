package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.PointcutAdvisor;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class AspectJPointcutAdvisor implements PointcutAdvisor, Ordered {
   private final AbstractAspectJAdvice advice;
   private final Pointcut pointcut;
   @Nullable
   private Integer order;

   public AspectJPointcutAdvisor(AbstractAspectJAdvice advice) {
      Assert.notNull(advice, (String)"Advice must not be null");
      this.advice = advice;
      this.pointcut = advice.buildSafePointcut();
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order != null ? this.order : this.advice.getOrder();
   }

   public boolean isPerInstance() {
      return true;
   }

   public Advice getAdvice() {
      return this.advice;
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }

   public String getAspectName() {
      return this.advice.getAspectName();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AspectJPointcutAdvisor)) {
         return false;
      } else {
         AspectJPointcutAdvisor otherAdvisor = (AspectJPointcutAdvisor)other;
         return this.advice.equals(otherAdvisor.advice);
      }
   }

   public int hashCode() {
      return AspectJPointcutAdvisor.class.hashCode() * 29 + this.advice.hashCode();
   }
}
