package org.jboss.weld.contexts.unbound;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.bean.ManagedBean;
import org.jboss.weld.bean.builtin.AbstractBuiltInBean;
import org.jboss.weld.context.DependentContext;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.contexts.SerializableContextualInstanceImpl;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.exceptions.UnsupportedOperationException;
import org.jboss.weld.injection.producer.AbstractMemberProducer;
import org.jboss.weld.injection.producer.BasicInjectionTarget;
import org.jboss.weld.serialization.spi.ContextualStore;

public class DependentContextImpl implements DependentContext {
   private final ContextualStore contextualStore;

   public DependentContextImpl(ContextualStore contextualStore) {
      this.contextualStore = contextualStore;
   }

   public Object get(Contextual contextual, CreationalContext creationalContext) {
      if (!this.isActive()) {
         throw new ContextNotActiveException();
      } else if (creationalContext != null) {
         Object instance = contextual.create(creationalContext);
         if (creationalContext instanceof WeldCreationalContext) {
            this.addDependentInstance(instance, contextual, (WeldCreationalContext)creationalContext);
         }

         return instance;
      } else {
         return null;
      }
   }

   protected void addDependentInstance(Object instance, Contextual contextual, WeldCreationalContext creationalContext) {
      if (creationalContext.getDependentInstances().isEmpty()) {
         if (contextual instanceof ManagedBean && !this.isInterceptorOrDecorator(contextual)) {
            ManagedBean managedBean = (ManagedBean)contextual;
            if (managedBean.getProducer() instanceof BasicInjectionTarget) {
               BasicInjectionTarget injectionTarget = (BasicInjectionTarget)managedBean.getProducer();
               if (!injectionTarget.getLifecycleCallbackInvoker().hasPreDestroyMethods() && !injectionTarget.hasInterceptors()) {
                  return;
               }
            }
         }

         if (contextual instanceof AbstractProducerBean) {
            AbstractProducerBean producerBean = (AbstractProducerBean)contextual;
            if (producerBean.getProducer() instanceof AbstractMemberProducer) {
               AbstractMemberProducer producer = (AbstractMemberProducer)producerBean.getProducer();
               if (producer.getDisposalMethod() == null) {
                  return;
               }
            }
         }

         if (this.isOptimizableBuiltInBean(contextual)) {
            return;
         }
      }

      ContextualInstance beanInstance = new SerializableContextualInstanceImpl(contextual, instance, creationalContext, this.contextualStore);
      creationalContext.addDependentInstance(beanInstance);
   }

   private boolean isInterceptorOrDecorator(Contextual contextual) {
      return contextual instanceof Interceptor || contextual instanceof Decorator;
   }

   public Object get(Contextual contextual) {
      return this.get(contextual, (CreationalContext)null);
   }

   public boolean isActive() {
      return true;
   }

   public Class getScope() {
      return Dependent.class;
   }

   public void destroy(Contextual contextual) {
      throw new UnsupportedOperationException();
   }

   private boolean isOptimizableBuiltInBean(Contextual contextual) {
      if (contextual instanceof AbstractBuiltInBean) {
         AbstractBuiltInBean abstractBuiltInBean = (AbstractBuiltInBean)contextual;
         return abstractBuiltInBean.isDependentContextOptimizationAllowed();
      } else {
         return false;
      }
   }
}
