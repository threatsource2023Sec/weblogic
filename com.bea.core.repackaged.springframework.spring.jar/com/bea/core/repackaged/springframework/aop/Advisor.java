package com.bea.core.repackaged.springframework.aop;

import com.bea.core.repackaged.aopalliance.aop.Advice;

public interface Advisor {
   Advice EMPTY_ADVICE = new Advice() {
   };

   Advice getAdvice();

   boolean isPerInstance();
}
