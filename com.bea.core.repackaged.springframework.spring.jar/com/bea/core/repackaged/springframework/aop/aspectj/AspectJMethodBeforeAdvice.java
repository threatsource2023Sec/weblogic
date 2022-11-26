package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.springframework.aop.MethodBeforeAdvice;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;
import java.lang.reflect.Method;

public class AspectJMethodBeforeAdvice extends AbstractAspectJAdvice implements MethodBeforeAdvice, Serializable {
   public AspectJMethodBeforeAdvice(Method aspectJBeforeAdviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory aif) {
      super(aspectJBeforeAdviceMethod, pointcut, aif);
   }

   public void before(Method method, Object[] args, @Nullable Object target) throws Throwable {
      this.invokeAdviceMethod(this.getJoinPointMatch(), (Object)null, (Throwable)null);
   }

   public boolean isBeforeAdvice() {
      return true;
   }

   public boolean isAfterAdvice() {
      return false;
   }
}
