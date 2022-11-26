package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.AopInvocationException;
import com.bea.core.repackaged.springframework.aop.PointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.RawTargetAccess;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.cglib.core.ClassGenerator;
import com.bea.core.repackaged.springframework.cglib.core.CodeGenerationException;
import com.bea.core.repackaged.springframework.cglib.core.SpringNamingPolicy;
import com.bea.core.repackaged.springframework.cglib.proxy.Callback;
import com.bea.core.repackaged.springframework.cglib.proxy.CallbackFilter;
import com.bea.core.repackaged.springframework.cglib.proxy.Dispatcher;
import com.bea.core.repackaged.springframework.cglib.proxy.Enhancer;
import com.bea.core.repackaged.springframework.cglib.proxy.Factory;
import com.bea.core.repackaged.springframework.cglib.proxy.MethodInterceptor;
import com.bea.core.repackaged.springframework.cglib.proxy.MethodProxy;
import com.bea.core.repackaged.springframework.cglib.proxy.NoOp;
import com.bea.core.repackaged.springframework.cglib.transform.impl.UndeclaredThrowableStrategy;
import com.bea.core.repackaged.springframework.core.SmartClassLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

class CglibAopProxy implements AopProxy, Serializable {
   private static final int AOP_PROXY = 0;
   private static final int INVOKE_TARGET = 1;
   private static final int NO_OVERRIDE = 2;
   private static final int DISPATCH_TARGET = 3;
   private static final int DISPATCH_ADVISED = 4;
   private static final int INVOKE_EQUALS = 5;
   private static final int INVOKE_HASHCODE = 6;
   protected static final Log logger = LogFactory.getLog(CglibAopProxy.class);
   private static final Map validatedClasses = new WeakHashMap();
   protected final AdvisedSupport advised;
   @Nullable
   protected Object[] constructorArgs;
   @Nullable
   protected Class[] constructorArgTypes;
   private final transient AdvisedDispatcher advisedDispatcher;
   private transient Map fixedInterceptorMap = Collections.emptyMap();
   private transient int fixedInterceptorOffset;

   public CglibAopProxy(AdvisedSupport config) throws AopConfigException {
      Assert.notNull(config, (String)"AdvisedSupport must not be null");
      if (config.getAdvisors().length == 0 && config.getTargetSource() == AdvisedSupport.EMPTY_TARGET_SOURCE) {
         throw new AopConfigException("No advisors and no TargetSource specified");
      } else {
         this.advised = config;
         this.advisedDispatcher = new AdvisedDispatcher(this.advised);
      }
   }

   public void setConstructorArguments(@Nullable Object[] constructorArgs, @Nullable Class[] constructorArgTypes) {
      if (constructorArgs != null && constructorArgTypes != null) {
         if (constructorArgs.length != constructorArgTypes.length) {
            throw new IllegalArgumentException("Number of 'constructorArgs' (" + constructorArgs.length + ") must match number of 'constructorArgTypes' (" + constructorArgTypes.length + ")");
         } else {
            this.constructorArgs = constructorArgs;
            this.constructorArgTypes = constructorArgTypes;
         }
      } else {
         throw new IllegalArgumentException("Both 'constructorArgs' and 'constructorArgTypes' need to be specified");
      }
   }

   public Object getProxy() {
      return this.getProxy((ClassLoader)null);
   }

   public Object getProxy(@Nullable ClassLoader classLoader) {
      if (logger.isTraceEnabled()) {
         logger.trace("Creating CGLIB proxy: " + this.advised.getTargetSource());
      }

      try {
         Class rootClass = this.advised.getTargetClass();
         Assert.state(rootClass != null, "Target class must be available for creating a CGLIB proxy");
         Class proxySuperClass = rootClass;
         int x;
         if (ClassUtils.isCglibProxyClass(rootClass)) {
            proxySuperClass = rootClass.getSuperclass();
            Class[] additionalInterfaces = rootClass.getInterfaces();
            Class[] var5 = additionalInterfaces;
            int var6 = additionalInterfaces.length;

            for(x = 0; x < var6; ++x) {
               Class additionalInterface = var5[x];
               this.advised.addInterface(additionalInterface);
            }
         }

         this.validateClassIfNecessary(proxySuperClass, classLoader);
         Enhancer enhancer = this.createEnhancer();
         if (classLoader != null) {
            enhancer.setClassLoader(classLoader);
            if (classLoader instanceof SmartClassLoader && ((SmartClassLoader)classLoader).isClassReloadable(proxySuperClass)) {
               enhancer.setUseCache(false);
            }
         }

         enhancer.setSuperclass(proxySuperClass);
         enhancer.setInterfaces(AopProxyUtils.completeProxiedInterfaces(this.advised));
         enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
         enhancer.setStrategy(new ClassLoaderAwareUndeclaredThrowableStrategy(classLoader));
         Callback[] callbacks = this.getCallbacks(rootClass);
         Class[] types = new Class[callbacks.length];

         for(x = 0; x < types.length; ++x) {
            types[x] = callbacks[x].getClass();
         }

         enhancer.setCallbackFilter(new ProxyCallbackFilter(this.advised.getConfigurationOnlyCopy(), this.fixedInterceptorMap, this.fixedInterceptorOffset));
         enhancer.setCallbackTypes(types);
         return this.createProxyClassAndInstance(enhancer, callbacks);
      } catch (IllegalArgumentException | CodeGenerationException var9) {
         throw new AopConfigException("Could not generate CGLIB subclass of " + this.advised.getTargetClass() + ": Common causes of this problem include using a final class or a non-visible class", var9);
      } catch (Throwable var10) {
         throw new AopConfigException("Unexpected AOP exception", var10);
      }
   }

   protected Object createProxyClassAndInstance(Enhancer enhancer, Callback[] callbacks) {
      enhancer.setInterceptDuringConstruction(false);
      enhancer.setCallbacks(callbacks);
      return this.constructorArgs != null && this.constructorArgTypes != null ? enhancer.create(this.constructorArgTypes, this.constructorArgs) : enhancer.create();
   }

   protected Enhancer createEnhancer() {
      return new Enhancer();
   }

   private void validateClassIfNecessary(Class proxySuperClass, @Nullable ClassLoader proxyClassLoader) {
      if (logger.isWarnEnabled()) {
         synchronized(validatedClasses) {
            if (!validatedClasses.containsKey(proxySuperClass)) {
               this.doValidateClass(proxySuperClass, proxyClassLoader, ClassUtils.getAllInterfacesForClassAsSet(proxySuperClass));
               validatedClasses.put(proxySuperClass, Boolean.TRUE);
            }
         }
      }

   }

   private void doValidateClass(Class proxySuperClass, @Nullable ClassLoader proxyClassLoader, Set ifcs) {
      if (proxySuperClass != Object.class) {
         Method[] methods = proxySuperClass.getDeclaredMethods();
         Method[] var5 = methods;
         int var6 = methods.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Method method = var5[var7];
            int mod = method.getModifiers();
            if (!Modifier.isStatic(mod) && !Modifier.isPrivate(mod)) {
               if (Modifier.isFinal(mod)) {
                  if (implementsInterface(method, ifcs)) {
                     logger.info("Unable to proxy interface-implementing method [" + method + "] because it is marked as final: Consider using interface-based JDK proxies instead!");
                  }

                  logger.debug("Final method [" + method + "] cannot get proxied via CGLIB: Calls to this method will NOT be routed to the target instance and might lead to NPEs against uninitialized fields in the proxy instance.");
               } else if (!Modifier.isPublic(mod) && !Modifier.isProtected(mod) && proxyClassLoader != null && proxySuperClass.getClassLoader() != proxyClassLoader) {
                  logger.debug("Method [" + method + "] is package-visible across different ClassLoaders and cannot get proxied via CGLIB: Declare this method as public or protected if you need to support invocations through the proxy.");
               }
            }
         }

         this.doValidateClass(proxySuperClass.getSuperclass(), proxyClassLoader, ifcs);
      }

   }

   private Callback[] getCallbacks(Class rootClass) throws Exception {
      boolean exposeProxy = this.advised.isExposeProxy();
      boolean isFrozen = this.advised.isFrozen();
      boolean isStatic = this.advised.getTargetSource().isStatic();
      Callback aopInterceptor = new DynamicAdvisedInterceptor(this.advised);
      Object targetInterceptor;
      if (exposeProxy) {
         targetInterceptor = isStatic ? new StaticUnadvisedExposedInterceptor(this.advised.getTargetSource().getTarget()) : new DynamicUnadvisedExposedInterceptor(this.advised.getTargetSource());
      } else {
         targetInterceptor = isStatic ? new StaticUnadvisedInterceptor(this.advised.getTargetSource().getTarget()) : new DynamicUnadvisedInterceptor(this.advised.getTargetSource());
      }

      Callback targetDispatcher = isStatic ? new StaticDispatcher(this.advised.getTargetSource().getTarget()) : new SerializableNoOp();
      Callback[] mainCallbacks = new Callback[]{aopInterceptor, (Callback)targetInterceptor, new SerializableNoOp(), (Callback)targetDispatcher, this.advisedDispatcher, new EqualsInterceptor(this.advised), new HashCodeInterceptor(this.advised)};
      Callback[] callbacks;
      if (isStatic && isFrozen) {
         Method[] methods = rootClass.getMethods();
         Callback[] fixedCallbacks = new Callback[methods.length];
         this.fixedInterceptorMap = new HashMap(methods.length);

         for(int x = 0; x < methods.length; ++x) {
            Method method = methods[x];
            List chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, rootClass);
            fixedCallbacks[x] = new FixedChainStaticTargetInterceptor(chain, this.advised.getTargetSource().getTarget(), this.advised.getTargetClass());
            this.fixedInterceptorMap.put(methods.toString(), x);
         }

         callbacks = new Callback[mainCallbacks.length + fixedCallbacks.length];
         System.arraycopy(mainCallbacks, 0, callbacks, 0, mainCallbacks.length);
         System.arraycopy(fixedCallbacks, 0, callbacks, mainCallbacks.length, fixedCallbacks.length);
         this.fixedInterceptorOffset = mainCallbacks.length;
      } else {
         callbacks = mainCallbacks;
      }

      return callbacks;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof CglibAopProxy && AopProxyUtils.equalsInProxy(this.advised, ((CglibAopProxy)other).advised);
   }

   public int hashCode() {
      return CglibAopProxy.class.hashCode() * 13 + this.advised.getTargetSource().hashCode();
   }

   private static boolean implementsInterface(Method method, Set ifcs) {
      Iterator var2 = ifcs.iterator();

      Class ifc;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         ifc = (Class)var2.next();
      } while(!ClassUtils.hasMethod(ifc, method.getName(), method.getParameterTypes()));

      return true;
   }

   @Nullable
   private static Object processReturnType(Object proxy, @Nullable Object target, Method method, @Nullable Object returnValue) {
      if (returnValue != null && returnValue == target && !RawTargetAccess.class.isAssignableFrom(method.getDeclaringClass())) {
         returnValue = proxy;
      }

      Class returnType = method.getReturnType();
      if (returnValue == null && returnType != Void.TYPE && returnType.isPrimitive()) {
         throw new AopInvocationException("Null return value from advice does not match primitive return type for: " + method);
      } else {
         return returnValue;
      }
   }

   private static class ClassLoaderAwareUndeclaredThrowableStrategy extends UndeclaredThrowableStrategy {
      @Nullable
      private final ClassLoader classLoader;

      public ClassLoaderAwareUndeclaredThrowableStrategy(@Nullable ClassLoader classLoader) {
         super(UndeclaredThrowableException.class);
         this.classLoader = classLoader;
      }

      public byte[] generate(ClassGenerator cg) throws Exception {
         if (this.classLoader == null) {
            return super.generate(cg);
         } else {
            Thread currentThread = Thread.currentThread();

            ClassLoader threadContextClassLoader;
            try {
               threadContextClassLoader = currentThread.getContextClassLoader();
            } catch (Throwable var9) {
               return super.generate(cg);
            }

            boolean overrideClassLoader = !this.classLoader.equals(threadContextClassLoader);
            if (overrideClassLoader) {
               currentThread.setContextClassLoader(this.classLoader);
            }

            byte[] var5;
            try {
               var5 = super.generate(cg);
            } finally {
               if (overrideClassLoader) {
                  currentThread.setContextClassLoader(threadContextClassLoader);
               }

            }

            return var5;
         }
      }
   }

   private static class ProxyCallbackFilter implements CallbackFilter {
      private final AdvisedSupport advised;
      private final Map fixedInterceptorMap;
      private final int fixedInterceptorOffset;

      public ProxyCallbackFilter(AdvisedSupport advised, Map fixedInterceptorMap, int fixedInterceptorOffset) {
         this.advised = advised;
         this.fixedInterceptorMap = fixedInterceptorMap;
         this.fixedInterceptorOffset = fixedInterceptorOffset;
      }

      public int accept(Method method) {
         if (AopUtils.isFinalizeMethod(method)) {
            CglibAopProxy.logger.trace("Found finalize() method - using NO_OVERRIDE");
            return 2;
         } else if (!this.advised.isOpaque() && method.getDeclaringClass().isInterface() && method.getDeclaringClass().isAssignableFrom(Advised.class)) {
            if (CglibAopProxy.logger.isTraceEnabled()) {
               CglibAopProxy.logger.trace("Method is declared on Advised interface: " + method);
            }

            return 4;
         } else if (AopUtils.isEqualsMethod(method)) {
            if (CglibAopProxy.logger.isTraceEnabled()) {
               CglibAopProxy.logger.trace("Found 'equals' method: " + method);
            }

            return 5;
         } else if (AopUtils.isHashCodeMethod(method)) {
            if (CglibAopProxy.logger.isTraceEnabled()) {
               CglibAopProxy.logger.trace("Found 'hashCode' method: " + method);
            }

            return 6;
         } else {
            Class targetClass = this.advised.getTargetClass();
            List chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
            boolean haveAdvice = !chain.isEmpty();
            boolean exposeProxy = this.advised.isExposeProxy();
            boolean isStatic = this.advised.getTargetSource().isStatic();
            boolean isFrozen = this.advised.isFrozen();
            if (!haveAdvice && isFrozen) {
               if (!exposeProxy && isStatic) {
                  Class returnType = method.getReturnType();
                  if (targetClass != null && returnType.isAssignableFrom(targetClass)) {
                     if (CglibAopProxy.logger.isTraceEnabled()) {
                        CglibAopProxy.logger.trace("Method return type is assignable from target type and may therefore return 'this' - using INVOKE_TARGET: " + method);
                     }

                     return 1;
                  } else {
                     if (CglibAopProxy.logger.isTraceEnabled()) {
                        CglibAopProxy.logger.trace("Method return type ensures 'this' cannot be returned - using DISPATCH_TARGET: " + method);
                     }

                     return 3;
                  }
               } else {
                  return 1;
               }
            } else if (exposeProxy) {
               if (CglibAopProxy.logger.isTraceEnabled()) {
                  CglibAopProxy.logger.trace("Must expose proxy on advised method: " + method);
               }

               return 0;
            } else {
               String key = method.toString();
               if (isStatic && isFrozen && this.fixedInterceptorMap.containsKey(key)) {
                  if (CglibAopProxy.logger.isTraceEnabled()) {
                     CglibAopProxy.logger.trace("Method has advice and optimizations are enabled: " + method);
                  }

                  int index = (Integer)this.fixedInterceptorMap.get(key);
                  return index + this.fixedInterceptorOffset;
               } else {
                  if (CglibAopProxy.logger.isTraceEnabled()) {
                     CglibAopProxy.logger.trace("Unable to apply any optimizations to advised method: " + method);
                  }

                  return 0;
               }
            }
         }
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof ProxyCallbackFilter)) {
            return false;
         } else {
            ProxyCallbackFilter otherCallbackFilter = (ProxyCallbackFilter)other;
            AdvisedSupport otherAdvised = otherCallbackFilter.advised;
            if (this.advised.isFrozen() != otherAdvised.isFrozen()) {
               return false;
            } else if (this.advised.isExposeProxy() != otherAdvised.isExposeProxy()) {
               return false;
            } else if (this.advised.getTargetSource().isStatic() != otherAdvised.getTargetSource().isStatic()) {
               return false;
            } else if (!AopProxyUtils.equalsProxiedInterfaces(this.advised, otherAdvised)) {
               return false;
            } else {
               Advisor[] thisAdvisors = this.advised.getAdvisors();
               Advisor[] thatAdvisors = otherAdvised.getAdvisors();
               if (thisAdvisors.length != thatAdvisors.length) {
                  return false;
               } else {
                  for(int i = 0; i < thisAdvisors.length; ++i) {
                     Advisor thisAdvisor = thisAdvisors[i];
                     Advisor thatAdvisor = thatAdvisors[i];
                     if (!this.equalsAdviceClasses(thisAdvisor, thatAdvisor)) {
                        return false;
                     }

                     if (!this.equalsPointcuts(thisAdvisor, thatAdvisor)) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      }

      private boolean equalsAdviceClasses(Advisor a, Advisor b) {
         return a.getAdvice().getClass() == b.getAdvice().getClass();
      }

      private boolean equalsPointcuts(Advisor a, Advisor b) {
         return !(a instanceof PointcutAdvisor) || b instanceof PointcutAdvisor && ObjectUtils.nullSafeEquals(((PointcutAdvisor)a).getPointcut(), ((PointcutAdvisor)b).getPointcut());
      }

      public int hashCode() {
         int hashCode = 0;
         Advisor[] advisors = this.advised.getAdvisors();
         Advisor[] var3 = advisors;
         int var4 = advisors.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Advisor advisor = var3[var5];
            Advice advice = advisor.getAdvice();
            hashCode = 13 * hashCode + advice.getClass().hashCode();
         }

         hashCode = 13 * hashCode + (this.advised.isFrozen() ? 1 : 0);
         hashCode = 13 * hashCode + (this.advised.isExposeProxy() ? 1 : 0);
         hashCode = 13 * hashCode + (this.advised.isOptimize() ? 1 : 0);
         hashCode = 13 * hashCode + (this.advised.isOpaque() ? 1 : 0);
         return hashCode;
      }
   }

   private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
      @Nullable
      private final MethodProxy methodProxy;

      public CglibMethodInvocation(Object proxy, @Nullable Object target, Method method, Object[] arguments, @Nullable Class targetClass, List interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {
         super(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
         this.methodProxy = Modifier.isPublic(method.getModifiers()) && method.getDeclaringClass() != Object.class && !AopUtils.isEqualsMethod(method) && !AopUtils.isHashCodeMethod(method) && !AopUtils.isToStringMethod(method) ? methodProxy : null;
      }

      protected Object invokeJoinpoint() throws Throwable {
         return this.methodProxy != null ? this.methodProxy.invoke(this.target, this.arguments) : super.invokeJoinpoint();
      }
   }

   private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {
      private final AdvisedSupport advised;

      public DynamicAdvisedInterceptor(AdvisedSupport advised) {
         this.advised = advised;
      }

      @Nullable
      public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
         Object oldProxy = null;
         boolean setProxyContext = false;
         Object target = null;
         TargetSource targetSource = this.advised.getTargetSource();

         Object var16;
         try {
            if (this.advised.exposeProxy) {
               oldProxy = AopContext.setCurrentProxy(proxy);
               setProxyContext = true;
            }

            target = targetSource.getTarget();
            Class targetClass = target != null ? target.getClass() : null;
            List chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
            Object retVal;
            if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
               Object[] argsToUse = AopProxyUtils.adaptArgumentsIfNecessary(method, args);
               retVal = methodProxy.invoke(target, argsToUse);
            } else {
               retVal = (new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy)).proceed();
            }

            retVal = CglibAopProxy.processReturnType(proxy, target, method, retVal);
            var16 = retVal;
         } finally {
            if (target != null && !targetSource.isStatic()) {
               targetSource.releaseTarget(target);
            }

            if (setProxyContext) {
               AopContext.setCurrentProxy(oldProxy);
            }

         }

         return var16;
      }

      public boolean equals(Object other) {
         return this == other || other instanceof DynamicAdvisedInterceptor && this.advised.equals(((DynamicAdvisedInterceptor)other).advised);
      }

      public int hashCode() {
         return this.advised.hashCode();
      }
   }

   private static class FixedChainStaticTargetInterceptor implements MethodInterceptor, Serializable {
      private final List adviceChain;
      @Nullable
      private final Object target;
      @Nullable
      private final Class targetClass;

      public FixedChainStaticTargetInterceptor(List adviceChain, @Nullable Object target, @Nullable Class targetClass) {
         this.adviceChain = adviceChain;
         this.target = target;
         this.targetClass = targetClass;
      }

      @Nullable
      public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
         MethodInvocation invocation = new CglibMethodInvocation(proxy, this.target, method, args, this.targetClass, this.adviceChain, methodProxy);
         Object retVal = invocation.proceed();
         retVal = CglibAopProxy.processReturnType(proxy, this.target, method, retVal);
         return retVal;
      }
   }

   private static class HashCodeInterceptor implements MethodInterceptor, Serializable {
      private final AdvisedSupport advised;

      public HashCodeInterceptor(AdvisedSupport advised) {
         this.advised = advised;
      }

      public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) {
         return CglibAopProxy.class.hashCode() * 13 + this.advised.getTargetSource().hashCode();
      }
   }

   private static class EqualsInterceptor implements MethodInterceptor, Serializable {
      private final AdvisedSupport advised;

      public EqualsInterceptor(AdvisedSupport advised) {
         this.advised = advised;
      }

      public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) {
         Object other = args[0];
         if (proxy == other) {
            return true;
         } else if (other instanceof Factory) {
            Callback callback = ((Factory)other).getCallback(5);
            if (!(callback instanceof EqualsInterceptor)) {
               return false;
            } else {
               AdvisedSupport otherAdvised = ((EqualsInterceptor)callback).advised;
               return AopProxyUtils.equalsInProxy(this.advised, otherAdvised);
            }
         } else {
            return false;
         }
      }
   }

   private static class AdvisedDispatcher implements Dispatcher, Serializable {
      private final AdvisedSupport advised;

      public AdvisedDispatcher(AdvisedSupport advised) {
         this.advised = advised;
      }

      public Object loadObject() throws Exception {
         return this.advised;
      }
   }

   private static class StaticDispatcher implements Dispatcher, Serializable {
      @Nullable
      private Object target;

      public StaticDispatcher(@Nullable Object target) {
         this.target = target;
      }

      @Nullable
      public Object loadObject() {
         return this.target;
      }
   }

   private static class DynamicUnadvisedExposedInterceptor implements MethodInterceptor, Serializable {
      private final TargetSource targetSource;

      public DynamicUnadvisedExposedInterceptor(TargetSource targetSource) {
         this.targetSource = targetSource;
      }

      @Nullable
      public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
         Object oldProxy = null;
         Object target = this.targetSource.getTarget();

         Object var8;
         try {
            oldProxy = AopContext.setCurrentProxy(proxy);
            Object retVal = methodProxy.invoke(target, args);
            var8 = CglibAopProxy.processReturnType(proxy, target, method, retVal);
         } finally {
            AopContext.setCurrentProxy(oldProxy);
            if (target != null) {
               this.targetSource.releaseTarget(target);
            }

         }

         return var8;
      }
   }

   private static class DynamicUnadvisedInterceptor implements MethodInterceptor, Serializable {
      private final TargetSource targetSource;

      public DynamicUnadvisedInterceptor(TargetSource targetSource) {
         this.targetSource = targetSource;
      }

      @Nullable
      public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
         Object target = this.targetSource.getTarget();

         Object var7;
         try {
            Object retVal = methodProxy.invoke(target, args);
            var7 = CglibAopProxy.processReturnType(proxy, target, method, retVal);
         } finally {
            if (target != null) {
               this.targetSource.releaseTarget(target);
            }

         }

         return var7;
      }
   }

   private static class StaticUnadvisedExposedInterceptor implements MethodInterceptor, Serializable {
      @Nullable
      private final Object target;

      public StaticUnadvisedExposedInterceptor(@Nullable Object target) {
         this.target = target;
      }

      @Nullable
      public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
         Object oldProxy = null;

         Object var7;
         try {
            oldProxy = AopContext.setCurrentProxy(proxy);
            Object retVal = methodProxy.invoke(this.target, args);
            var7 = CglibAopProxy.processReturnType(proxy, this.target, method, retVal);
         } finally {
            AopContext.setCurrentProxy(oldProxy);
         }

         return var7;
      }
   }

   private static class StaticUnadvisedInterceptor implements MethodInterceptor, Serializable {
      @Nullable
      private final Object target;

      public StaticUnadvisedInterceptor(@Nullable Object target) {
         this.target = target;
      }

      @Nullable
      public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
         Object retVal = methodProxy.invoke(this.target, args);
         return CglibAopProxy.processReturnType(proxy, this.target, method, retVal);
      }
   }

   public static class SerializableNoOp implements NoOp, Serializable {
   }
}
