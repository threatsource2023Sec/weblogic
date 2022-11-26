package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.DynamicIntroductionAdvice;
import com.bea.core.repackaged.springframework.aop.IntroductionInterceptor;
import com.bea.core.repackaged.springframework.aop.ProxyMethodInvocation;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class DelegatingIntroductionInterceptor extends IntroductionInfoSupport implements IntroductionInterceptor {
   @Nullable
   private Object delegate;

   public DelegatingIntroductionInterceptor(Object delegate) {
      this.init(delegate);
   }

   protected DelegatingIntroductionInterceptor() {
      this.init(this);
   }

   private void init(Object delegate) {
      Assert.notNull(delegate, "Delegate must not be null");
      this.delegate = delegate;
      this.implementInterfacesOnObject(delegate);
      this.suppressInterface(IntroductionInterceptor.class);
      this.suppressInterface(DynamicIntroductionAdvice.class);
   }

   @Nullable
   public Object invoke(MethodInvocation mi) throws Throwable {
      if (this.isMethodOnIntroducedInterface(mi)) {
         Object retVal = AopUtils.invokeJoinpointUsingReflection(this.delegate, mi.getMethod(), mi.getArguments());
         if (retVal == this.delegate && mi instanceof ProxyMethodInvocation) {
            Object proxy = ((ProxyMethodInvocation)mi).getProxy();
            if (mi.getMethod().getReturnType().isInstance(proxy)) {
               retVal = proxy;
            }
         }

         return retVal;
      } else {
         return this.doProceed(mi);
      }
   }

   protected Object doProceed(MethodInvocation mi) throws Throwable {
      return mi.proceed();
   }
}
