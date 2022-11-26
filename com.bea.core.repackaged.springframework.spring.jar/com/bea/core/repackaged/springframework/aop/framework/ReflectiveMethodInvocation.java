package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.ProxyMethodInvocation;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectiveMethodInvocation implements ProxyMethodInvocation, Cloneable {
   protected final Object proxy;
   @Nullable
   protected final Object target;
   protected final Method method;
   protected Object[] arguments;
   @Nullable
   private final Class targetClass;
   @Nullable
   private Map userAttributes;
   protected final List interceptorsAndDynamicMethodMatchers;
   private int currentInterceptorIndex = -1;

   protected ReflectiveMethodInvocation(Object proxy, @Nullable Object target, Method method, @Nullable Object[] arguments, @Nullable Class targetClass, List interceptorsAndDynamicMethodMatchers) {
      this.proxy = proxy;
      this.target = target;
      this.targetClass = targetClass;
      this.method = BridgeMethodResolver.findBridgedMethod(method);
      this.arguments = AopProxyUtils.adaptArgumentsIfNecessary(method, arguments);
      this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
   }

   public final Object getProxy() {
      return this.proxy;
   }

   @Nullable
   public final Object getThis() {
      return this.target;
   }

   public final AccessibleObject getStaticPart() {
      return this.method;
   }

   public final Method getMethod() {
      return this.method;
   }

   public final Object[] getArguments() {
      return this.arguments;
   }

   public void setArguments(Object... arguments) {
      this.arguments = arguments;
   }

   @Nullable
   public Object proceed() throws Throwable {
      if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
         return this.invokeJoinpoint();
      } else {
         Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
         if (interceptorOrInterceptionAdvice instanceof InterceptorAndDynamicMethodMatcher) {
            InterceptorAndDynamicMethodMatcher dm = (InterceptorAndDynamicMethodMatcher)interceptorOrInterceptionAdvice;
            Class targetClass = this.targetClass != null ? this.targetClass : this.method.getDeclaringClass();
            return dm.methodMatcher.matches(this.method, targetClass, this.arguments) ? dm.interceptor.invoke(this) : this.proceed();
         } else {
            return ((MethodInterceptor)interceptorOrInterceptionAdvice).invoke(this);
         }
      }
   }

   @Nullable
   protected Object invokeJoinpoint() throws Throwable {
      return AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
   }

   public MethodInvocation invocableClone() {
      Object[] cloneArguments = this.arguments;
      if (this.arguments.length > 0) {
         cloneArguments = new Object[this.arguments.length];
         System.arraycopy(this.arguments, 0, cloneArguments, 0, this.arguments.length);
      }

      return this.invocableClone(cloneArguments);
   }

   public MethodInvocation invocableClone(Object... arguments) {
      if (this.userAttributes == null) {
         this.userAttributes = new HashMap();
      }

      try {
         ReflectiveMethodInvocation clone = (ReflectiveMethodInvocation)this.clone();
         clone.arguments = arguments;
         return clone;
      } catch (CloneNotSupportedException var3) {
         throw new IllegalStateException("Should be able to clone object of type [" + this.getClass() + "]: " + var3);
      }
   }

   public void setUserAttribute(String key, @Nullable Object value) {
      if (value != null) {
         if (this.userAttributes == null) {
            this.userAttributes = new HashMap();
         }

         this.userAttributes.put(key, value);
      } else if (this.userAttributes != null) {
         this.userAttributes.remove(key);
      }

   }

   @Nullable
   public Object getUserAttribute(String key) {
      return this.userAttributes != null ? this.userAttributes.get(key) : null;
   }

   public Map getUserAttributes() {
      if (this.userAttributes == null) {
         this.userAttributes = new HashMap();
      }

      return this.userAttributes;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("ReflectiveMethodInvocation: ");
      sb.append(this.method).append("; ");
      if (this.target == null) {
         sb.append("target is null");
      } else {
         sb.append("target is of class [").append(this.target.getClass().getName()).append(']');
      }

      return sb.toString();
   }
}
