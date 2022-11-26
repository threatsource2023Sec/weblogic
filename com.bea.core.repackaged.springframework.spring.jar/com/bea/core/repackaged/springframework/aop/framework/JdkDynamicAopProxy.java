package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.AopInvocationException;
import com.bea.core.repackaged.springframework.aop.RawTargetAccess;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.core.DecoratingProxy;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

final class JdkDynamicAopProxy implements AopProxy, InvocationHandler, Serializable {
   private static final long serialVersionUID = 5531744639992436476L;
   private static final Log logger = LogFactory.getLog(JdkDynamicAopProxy.class);
   private final AdvisedSupport advised;
   private boolean equalsDefined;
   private boolean hashCodeDefined;

   public JdkDynamicAopProxy(AdvisedSupport config) throws AopConfigException {
      Assert.notNull(config, (String)"AdvisedSupport must not be null");
      if (config.getAdvisors().length == 0 && config.getTargetSource() == AdvisedSupport.EMPTY_TARGET_SOURCE) {
         throw new AopConfigException("No advisors and no TargetSource specified");
      } else {
         this.advised = config;
      }
   }

   public Object getProxy() {
      return this.getProxy(ClassUtils.getDefaultClassLoader());
   }

   public Object getProxy(@Nullable ClassLoader classLoader) {
      if (logger.isTraceEnabled()) {
         logger.trace("Creating JDK dynamic proxy: " + this.advised.getTargetSource());
      }

      Class[] proxiedInterfaces = AopProxyUtils.completeProxiedInterfaces(this.advised, true);
      this.findDefinedEqualsAndHashCodeMethods(proxiedInterfaces);
      return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);
   }

   private void findDefinedEqualsAndHashCodeMethods(Class[] proxiedInterfaces) {
      Class[] var2 = proxiedInterfaces;
      int var3 = proxiedInterfaces.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class proxiedInterface = var2[var4];
         Method[] methods = proxiedInterface.getDeclaredMethods();
         Method[] var7 = methods;
         int var8 = methods.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Method method = var7[var9];
            if (AopUtils.isEqualsMethod(method)) {
               this.equalsDefined = true;
            }

            if (AopUtils.isHashCodeMethod(method)) {
               this.hashCodeDefined = true;
            }

            if (this.equalsDefined && this.hashCodeDefined) {
               return;
            }
         }
      }

   }

   @Nullable
   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Object oldProxy = null;
      boolean setProxyContext = false;
      TargetSource targetSource = this.advised.targetSource;
      Object target = null;

      Object var12;
      try {
         if (!this.equalsDefined && AopUtils.isEqualsMethod(method)) {
            Boolean var18 = this.equals(args[0]);
            return var18;
         }

         if (!this.hashCodeDefined && AopUtils.isHashCodeMethod(method)) {
            Integer var17 = this.hashCode();
            return var17;
         }

         if (method.getDeclaringClass() == DecoratingProxy.class) {
            Class var16 = AopProxyUtils.ultimateTargetClass(this.advised);
            return var16;
         }

         Object retVal;
         if (!this.advised.opaque && method.getDeclaringClass().isInterface() && method.getDeclaringClass().isAssignableFrom(Advised.class)) {
            retVal = AopUtils.invokeJoinpointUsingReflection(this.advised, method, args);
            return retVal;
         }

         if (this.advised.exposeProxy) {
            oldProxy = AopContext.setCurrentProxy(proxy);
            setProxyContext = true;
         }

         target = targetSource.getTarget();
         Class targetClass = target != null ? target.getClass() : null;
         List chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
         if (chain.isEmpty()) {
            Object[] argsToUse = AopProxyUtils.adaptArgumentsIfNecessary(method, args);
            retVal = AopUtils.invokeJoinpointUsingReflection(target, method, argsToUse);
         } else {
            MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, chain);
            retVal = invocation.proceed();
         }

         Class returnType = method.getReturnType();
         if (retVal != null && retVal == target && returnType != Object.class && returnType.isInstance(proxy) && !RawTargetAccess.class.isAssignableFrom(method.getDeclaringClass())) {
            retVal = proxy;
         } else if (retVal == null && returnType != Void.TYPE && returnType.isPrimitive()) {
            throw new AopInvocationException("Null return value from advice does not match primitive return type for: " + method);
         }

         var12 = retVal;
      } finally {
         if (target != null && !targetSource.isStatic()) {
            targetSource.releaseTarget(target);
         }

         if (setProxyContext) {
            AopContext.setCurrentProxy(oldProxy);
         }

      }

      return var12;
   }

   public boolean equals(@Nullable Object other) {
      if (other == this) {
         return true;
      } else if (other == null) {
         return false;
      } else {
         JdkDynamicAopProxy otherProxy;
         if (other instanceof JdkDynamicAopProxy) {
            otherProxy = (JdkDynamicAopProxy)other;
         } else {
            if (!Proxy.isProxyClass(other.getClass())) {
               return false;
            }

            InvocationHandler ih = Proxy.getInvocationHandler(other);
            if (!(ih instanceof JdkDynamicAopProxy)) {
               return false;
            }

            otherProxy = (JdkDynamicAopProxy)ih;
         }

         return AopProxyUtils.equalsInProxy(this.advised, otherProxy.advised);
      }
   }

   public int hashCode() {
      return JdkDynamicAopProxy.class.hashCode() * 13 + this.advised.getTargetSource().hashCode();
   }
}
