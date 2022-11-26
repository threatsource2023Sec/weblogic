package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.springframework.aop.AfterAdvice;
import com.bea.core.repackaged.springframework.aop.AfterReturningAdvice;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.TypeUtils;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice implements AfterReturningAdvice, AfterAdvice, Serializable {
   public AspectJAfterReturningAdvice(Method aspectJBeforeAdviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory aif) {
      super(aspectJBeforeAdviceMethod, pointcut, aif);
   }

   public boolean isBeforeAdvice() {
      return false;
   }

   public boolean isAfterAdvice() {
      return true;
   }

   public void setReturningName(String name) {
      this.setReturningNameNoCheck(name);
   }

   public void afterReturning(@Nullable Object returnValue, Method method, Object[] args, @Nullable Object target) throws Throwable {
      if (this.shouldInvokeOnReturnValueOf(method, returnValue)) {
         this.invokeAdviceMethod(this.getJoinPointMatch(), returnValue, (Throwable)null);
      }

   }

   private boolean shouldInvokeOnReturnValueOf(Method method, @Nullable Object returnValue) {
      Class type = this.getDiscoveredReturningType();
      Type genericType = this.getDiscoveredReturningGenericType();
      return this.matchesReturnValue(type, method, returnValue) && (genericType == null || genericType == type || TypeUtils.isAssignable(genericType, method.getGenericReturnType()));
   }

   private boolean matchesReturnValue(Class type, Method method, @Nullable Object returnValue) {
      if (returnValue != null) {
         return ClassUtils.isAssignableValue(type, returnValue);
      } else {
         return Object.class == type && Void.TYPE == method.getReturnType() ? true : ClassUtils.isAssignable(type, method.getReturnType());
      }
   }
}
