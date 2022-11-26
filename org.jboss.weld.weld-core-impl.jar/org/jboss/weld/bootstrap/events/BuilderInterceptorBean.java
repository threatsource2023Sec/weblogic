package org.jboss.weld.bootstrap.events;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.inject.spi.Prioritized;
import javax.interceptor.InvocationContext;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;

class BuilderInterceptorBean implements Interceptor, Prioritized, PassivationCapable {
   private final Set bindings;
   private static final Set types = ImmutableSet.of(BuilderInterceptorInstance.class, Object.class);
   private final int priority;
   private final InterceptionType interceptionType;
   private final Function interceptorFunction;
   private final BiFunction interceptorMetadataFunction;
   private final BeanManagerImpl beanManager;

   private BuilderInterceptorBean(Set interceptorBindings, InterceptionType type, int priority, BeanManagerImpl beanManager, Function interceptorFunction, BiFunction interceptorMetadataFunction) {
      this.interceptorFunction = interceptorFunction;
      this.interceptorMetadataFunction = interceptorMetadataFunction;
      this.priority = priority;
      this.interceptionType = type;
      this.bindings = ImmutableSet.copyOf(interceptorBindings);
      this.beanManager = beanManager;
   }

   public BuilderInterceptorBean(Set interceptorBindings, InterceptionType type, int priority, BeanManagerImpl beanManager, Function interceptorFunction) {
      this(interceptorBindings, type, priority, beanManager, interceptorFunction, (BiFunction)null);
   }

   public BuilderInterceptorBean(Set interceptorBindings, InterceptionType type, int priority, BeanManagerImpl beanManager, BiFunction interceptorFunction) {
      this(interceptorBindings, type, priority, beanManager, (Function)null, interceptorFunction);
   }

   public Set getInterceptorBindings() {
      return this.bindings;
   }

   public boolean intercepts(InterceptionType type) {
      return this.interceptionType.equals(type);
   }

   public Object intercept(InterceptionType type, BuilderInterceptorInstance builderInterceptorInstance, InvocationContext ctx) throws Exception {
      return this.interceptorMetadataFunction != null ? this.interceptorMetadataFunction.apply(ctx, builderInterceptorInstance.getInterceptedBean()) : this.interceptorFunction.apply(ctx);
   }

   public Class getBeanClass() {
      return BuilderInterceptorInstance.class;
   }

   public Set getInjectionPoints() {
      return Collections.emptySet();
   }

   public boolean isNullable() {
      return false;
   }

   public Set getTypes() {
      return types;
   }

   public Set getQualifiers() {
      return Collections.emptySet();
   }

   public Class getScope() {
      return Dependent.class;
   }

   public String getName() {
      return this.getBeanClass().toString() + this.interceptionType.name() + this.priority + Formats.formatAnnotations((Iterable)this.bindings);
   }

   public Set getStereotypes() {
      return Collections.emptySet();
   }

   public boolean isAlternative() {
      return false;
   }

   protected WeldCreationalContext getParentCreationalContext(CreationalContext ctx) {
      if (ctx instanceof WeldCreationalContext) {
         WeldCreationalContext parentContext = ((WeldCreationalContext)ctx).getParentCreationalContext();
         if (parentContext != null) {
            return parentContext;
         }
      }

      throw BeanLogger.LOG.unableToDetermineParentCreationalContext(ctx);
   }

   public int getPriority() {
      return this.priority;
   }

   public BuilderInterceptorInstance create(CreationalContext creationalContext) {
      if (this.interceptorMetadataFunction != null) {
         WeldCreationalContext interceptorContext = this.getParentCreationalContext(creationalContext);
         Contextual interceptedContextual = interceptorContext.getContextual();
         if (interceptedContextual instanceof Bean) {
            return new BuilderInterceptorInstance((Bean)interceptedContextual, this.beanManager.getContextId());
         } else {
            throw BeanLogger.LOG.cannotCreateContextualInstanceOfBuilderInterceptor(this);
         }
      } else {
         return new BuilderInterceptorInstance();
      }
   }

   public void destroy(BuilderInterceptorInstance instance, CreationalContext creationalContext) {
   }

   public String getId() {
      return this.getName();
   }
}
