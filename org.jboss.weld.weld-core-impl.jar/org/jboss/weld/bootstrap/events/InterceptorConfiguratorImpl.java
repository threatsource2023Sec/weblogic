package org.jboss.weld.bootstrap.events;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.bootstrap.BeanDeploymentFinder;
import org.jboss.weld.bootstrap.event.InterceptorConfigurator;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class InterceptorConfiguratorImpl implements InterceptorConfigurator {
   private int priority;
   private Set bindings;
   private InterceptionType type;
   private Function interceptorFunction;
   private BiFunction interceptorBiFunction;
   private BeanManagerImpl beanManager;
   private BeanDeploymentFinder beanDeploymentFinder;

   public InterceptorConfiguratorImpl() {
      this((BeanManagerImpl)null);
   }

   public InterceptorConfiguratorImpl(BeanManagerImpl beanManager) {
      this.priority = 2000;
      this.bindings = new HashSet();
      this.beanManager = beanManager;
   }

   public InterceptorConfigurator intercept(InterceptionType interceptionType, Function interceptorFunction) {
      this.type = interceptionType;
      this.interceptorFunction = interceptorFunction;
      this.interceptorBiFunction = null;
      return this;
   }

   public InterceptorConfigurator interceptWithMetadata(InterceptionType interceptionType, BiFunction interceptorFunction) {
      this.type = interceptionType;
      this.interceptorBiFunction = interceptorFunction;
      this.interceptorFunction = null;
      return this;
   }

   public InterceptorConfigurator addBinding(Annotation binding) {
      Collections.addAll(this.bindings, new Annotation[]{binding});
      return this;
   }

   public InterceptorConfigurator addBindings(Annotation... bindings) {
      Collections.addAll(this.bindings, bindings);
      return this;
   }

   public InterceptorConfigurator addBindings(Set bindings) {
      this.bindings.addAll(bindings);
      return this;
   }

   public InterceptorConfigurator bindings(Annotation... bindings) {
      this.bindings.clear();
      Collections.addAll(this.bindings, bindings);
      return this;
   }

   public InterceptorConfigurator priority(int priority) {
      this.priority = priority;
      return this;
   }

   public Interceptor build() {
      if (this.type == null) {
         throw BeanLogger.LOG.noInterceptionType(this);
      } else if (this.interceptorFunction == null && this.interceptorBiFunction == null) {
         throw BeanLogger.LOG.noInterceptionFunction(this);
      } else {
         if (this.beanDeploymentFinder != null) {
            this.beanManager = this.beanDeploymentFinder.getOrCreateBeanDeployment(BuilderInterceptorInstance.class).getBeanManager();
         }

         BuilderInterceptorBean interceptor;
         if (this.interceptorBiFunction != null) {
            interceptor = new BuilderInterceptorBean(this.bindings, this.type, this.priority, this.beanManager, this.interceptorBiFunction);
         } else {
            interceptor = new BuilderInterceptorBean(this.bindings, this.type, this.priority, this.beanManager, this.interceptorFunction);
         }

         return interceptor;
      }
   }

   public BeanManagerImpl getBeanManager() {
      return this.beanManager;
   }

   public void setBeanDeploymentFinder(BeanDeploymentFinder beanDeploymentFinder) {
      this.beanDeploymentFinder = beanDeploymentFinder;
   }
}
