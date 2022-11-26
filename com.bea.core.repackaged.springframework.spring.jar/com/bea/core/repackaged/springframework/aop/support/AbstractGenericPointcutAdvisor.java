package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.aopalliance.aop.Advice;

public abstract class AbstractGenericPointcutAdvisor extends AbstractPointcutAdvisor {
   private Advice advice;

   public AbstractGenericPointcutAdvisor() {
      this.advice = EMPTY_ADVICE;
   }

   public void setAdvice(Advice advice) {
      this.advice = advice;
   }

   public Advice getAdvice() {
      return this.advice;
   }

   public String toString() {
      return this.getClass().getName() + ": advice [" + this.getAdvice() + "]";
   }
}
