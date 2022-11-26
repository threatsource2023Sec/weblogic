package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.AfterAdvice;
import java.io.Serializable;
import java.lang.reflect.Method;

public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice implements MethodInterceptor, AfterAdvice, Serializable {
   public AspectJAfterThrowingAdvice(Method aspectJBeforeAdviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory aif) {
      super(aspectJBeforeAdviceMethod, pointcut, aif);
   }

   public boolean isBeforeAdvice() {
      return false;
   }

   public boolean isAfterAdvice() {
      return true;
   }

   public void setThrowingName(String name) {
      this.setThrowingNameNoCheck(name);
   }

   public Object invoke(MethodInvocation mi) throws Throwable {
      try {
         return mi.proceed();
      } catch (Throwable var3) {
         if (this.shouldInvokeOnThrowing(var3)) {
            this.invokeAdviceMethod(this.getJoinPointMatch(), (Object)null, var3);
         }

         throw var3;
      }
   }

   private boolean shouldInvokeOnThrowing(Throwable ex) {
      return this.getDiscoveredThrowingType().isAssignableFrom(ex.getClass());
   }
}
