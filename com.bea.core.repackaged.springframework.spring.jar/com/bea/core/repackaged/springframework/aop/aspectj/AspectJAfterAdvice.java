package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.AfterAdvice;
import java.io.Serializable;
import java.lang.reflect.Method;

public class AspectJAfterAdvice extends AbstractAspectJAdvice implements MethodInterceptor, AfterAdvice, Serializable {
   public AspectJAfterAdvice(Method aspectJBeforeAdviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory aif) {
      super(aspectJBeforeAdviceMethod, pointcut, aif);
   }

   public Object invoke(MethodInvocation mi) throws Throwable {
      Object var2;
      try {
         var2 = mi.proceed();
      } finally {
         this.invokeAdviceMethod(this.getJoinPointMatch(), (Object)null, (Throwable)null);
      }

      return var2;
   }

   public boolean isBeforeAdvice() {
      return false;
   }

   public boolean isAfterAdvice() {
      return true;
   }
}
