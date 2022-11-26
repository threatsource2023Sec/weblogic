package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.DynamicIntroductionAdvice;
import com.bea.core.repackaged.springframework.aop.IntroductionInterceptor;
import com.bea.core.repackaged.springframework.aop.ProxyMethodInvocation;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.util.Map;
import java.util.WeakHashMap;

public class DelegatePerTargetObjectIntroductionInterceptor extends IntroductionInfoSupport implements IntroductionInterceptor {
   private final Map delegateMap = new WeakHashMap();
   private Class defaultImplType;
   private Class interfaceType;

   public DelegatePerTargetObjectIntroductionInterceptor(Class defaultImplType, Class interfaceType) {
      this.defaultImplType = defaultImplType;
      this.interfaceType = interfaceType;
      Object delegate = this.createNewDelegate();
      this.implementInterfacesOnObject(delegate);
      this.suppressInterface(IntroductionInterceptor.class);
      this.suppressInterface(DynamicIntroductionAdvice.class);
   }

   @Nullable
   public Object invoke(MethodInvocation mi) throws Throwable {
      if (this.isMethodOnIntroducedInterface(mi)) {
         Object delegate = this.getIntroductionDelegateFor(mi.getThis());
         Object retVal = AopUtils.invokeJoinpointUsingReflection(delegate, mi.getMethod(), mi.getArguments());
         if (retVal == delegate && mi instanceof ProxyMethodInvocation) {
            retVal = ((ProxyMethodInvocation)mi).getProxy();
         }

         return retVal;
      } else {
         return this.doProceed(mi);
      }
   }

   protected Object doProceed(MethodInvocation mi) throws Throwable {
      return mi.proceed();
   }

   private Object getIntroductionDelegateFor(Object targetObject) {
      synchronized(this.delegateMap) {
         if (this.delegateMap.containsKey(targetObject)) {
            return this.delegateMap.get(targetObject);
         } else {
            Object delegate = this.createNewDelegate();
            this.delegateMap.put(targetObject, delegate);
            return delegate;
         }
      }
   }

   private Object createNewDelegate() {
      try {
         return ReflectionUtils.accessibleConstructor(this.defaultImplType).newInstance();
      } catch (Throwable var2) {
         throw new IllegalArgumentException("Cannot create default implementation for '" + this.interfaceType.getName() + "' mixin (" + this.defaultImplType.getName() + "): " + var2);
      }
   }
}
