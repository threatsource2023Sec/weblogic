package com.oracle.pitchfork.intercept;

import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.oracle.pitchfork.inject.DeploymentUnitMetadata;
import com.oracle.pitchfork.inject.Jsr250Metadata;
import com.oracle.pitchfork.interfaces.SystemInterceptor;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import com.oracle.pitchfork.util.reflect.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InterceptorMetadata extends Jsr250Metadata implements InterceptorMetadataI {
   private final List aroundInvokeMethods;
   private final List aroundTimeoutMethods;
   private final Method matchingMethod;
   private final Map lifecycleEventListenerMethods;
   private boolean classInterceptor;
   private boolean defaultInterceptor;
   private boolean constructorInterceptor;
   private final boolean isSystemInterceptor;
   private ConcurrentHashMap externallyCreatedInterceptors;
   private Method scheduledTimeoutMethod;

   private InterceptorMetadata(String name, Class componentClass, List aroundInvokeMethods, List aroundTimeoutMethods, Method matchingMethod) {
      super(new DeploymentUnitMetadata(), name, componentClass);
      this.lifecycleEventListenerMethods = new HashMap();
      this.externallyCreatedInterceptors = new ConcurrentHashMap();
      this.aroundInvokeMethods = aroundInvokeMethods;
      this.aroundTimeoutMethods = aroundTimeoutMethods;
      this.matchingMethod = matchingMethod;
      this.isSystemInterceptor = SystemInterceptor.class.isAssignableFrom(componentClass);
      if (aroundTimeoutMethods != null) {
         this.makeAccessible(aroundInvokeMethods);
      }

      if (aroundTimeoutMethods != null) {
         this.makeAccessible(aroundTimeoutMethods);
      }

   }

   public InterceptorMetadata(Class componentClass, List aroundInvokeMethods, List aroundTimeoutMethods, Method matchingMethod) {
      this(componentClass.getName(), componentClass, aroundInvokeMethods, aroundTimeoutMethods, matchingMethod);
   }

   public InterceptorMetadata(Class componentClass, List aroundInvokeMethods, List aroundTimeoutMethods) {
      this(componentClass.getName(), componentClass, aroundInvokeMethods, aroundTimeoutMethods, (Method)null);
   }

   private void makeAccessible(List methods) {
      Iterator var2 = methods.iterator();

      while(var2.hasNext()) {
         Method m = (Method)var2.next();
         ReflectionUtils.makeAccessible(m);
      }

   }

   public void registerLifecycleEventListenerMethod(LifecycleEvent le, Method m) {
      if (m != null) {
         ReflectionUtils.makeAccessible(m);
         List lifecycleListeners = (List)this.lifecycleEventListenerMethods.get(le);
         if (lifecycleListeners == null) {
            lifecycleListeners = new ArrayList();
         }

         ((List)lifecycleListeners).add(m);
         this.lifecycleEventListenerMethods.put(le, lifecycleListeners);
         this.log.info("Interceptor callback listener: Registered " + m + " on " + this.getComponentClass().getName() + " for " + le);
      }

   }

   public List getLifecycleEventListenerMethod(LifecycleEvent le) {
      return (List)this.lifecycleEventListenerMethods.get(le);
   }

   public List getAroundInvokeMethods() {
      return this.aroundInvokeMethods;
   }

   public List getAroundTimeoutMethods() {
      return this.aroundTimeoutMethods;
   }

   public Method getMatchingMethod() {
      return this.matchingMethod;
   }

   public boolean isClassInterceptor() {
      return this.classInterceptor;
   }

   public void setClassInterceptor(boolean classInterceptor) {
      this.classInterceptor = classInterceptor;
   }

   public boolean isDefaultInterceptor() {
      return this.defaultInterceptor;
   }

   public void setDefaultInterceptor(boolean defaultInterceptor) {
      this.defaultInterceptor = defaultInterceptor;
   }

   public boolean isConstructorInterceptor() {
      return this.constructorInterceptor;
   }

   public void setConstructorInterceptor(boolean constructorInterceptor) {
      this.constructorInterceptor = constructorInterceptor;
   }

   public void setComponentContext(ApplicationContext appCtx, BeanDefinitionRegistry bdr) {
      super.setComponentContext(appCtx, bdr);
      this.registerBeanDefinition(bdr);
   }

   private void registerBeanDefinition(BeanDefinitionRegistry bdr) {
      bdr.registerBeanDefinition(this.getComponentName(), this.getBeanDefinition());
   }

   public boolean isSystemInterceptor() {
      return this.isSystemInterceptor;
   }

   public void setExternallyCreatedInterceptor(Object beanTarget, Object interceptorInstance) {
      this.externallyCreatedInterceptors.put(beanTarget, interceptorInstance);
   }

   public Object getAndClearExternallyCreatedInterceptor(Object beanTarget) {
      return this.externallyCreatedInterceptors.remove(beanTarget);
   }

   public Method getScheduledTimeoutMethod() {
      return this.scheduledTimeoutMethod;
   }

   public void setScheduledTimeoutMethod(Method scheduledTimeoutMethod) {
      this.scheduledTimeoutMethod = scheduledTimeoutMethod;
   }
}
