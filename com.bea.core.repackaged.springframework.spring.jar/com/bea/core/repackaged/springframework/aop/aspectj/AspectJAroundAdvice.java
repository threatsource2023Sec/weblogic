package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.aspectj.lang.ProceedingJoinPoint;
import com.bea.core.repackaged.aspectj.weaver.tools.JoinPointMatch;
import com.bea.core.repackaged.springframework.aop.ProxyMethodInvocation;
import java.io.Serializable;
import java.lang.reflect.Method;

public class AspectJAroundAdvice extends AbstractAspectJAdvice implements MethodInterceptor, Serializable {
   public AspectJAroundAdvice(Method aspectJAroundAdviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory aif) {
      super(aspectJAroundAdviceMethod, pointcut, aif);
   }

   public boolean isBeforeAdvice() {
      return false;
   }

   public boolean isAfterAdvice() {
      return false;
   }

   protected boolean supportsProceedingJoinPoint() {
      return true;
   }

   public Object invoke(MethodInvocation mi) throws Throwable {
      if (!(mi instanceof ProxyMethodInvocation)) {
         throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
      } else {
         ProxyMethodInvocation pmi = (ProxyMethodInvocation)mi;
         ProceedingJoinPoint pjp = this.lazyGetProceedingJoinPoint(pmi);
         JoinPointMatch jpm = this.getJoinPointMatch(pmi);
         return this.invokeAdviceMethod(pjp, jpm, (Object)null, (Throwable)null);
      }
   }

   protected ProceedingJoinPoint lazyGetProceedingJoinPoint(ProxyMethodInvocation rmi) {
      return new MethodInvocationProceedingJoinPoint(rmi);
   }
}
