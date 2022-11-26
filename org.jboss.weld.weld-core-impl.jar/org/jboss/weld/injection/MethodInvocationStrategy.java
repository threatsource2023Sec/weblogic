package org.jboss.weld.injection;

import java.util.List;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.ObserverException;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.EventMetadata;
import org.jboss.weld.bean.builtin.BeanManagerProxy;
import org.jboss.weld.event.CurrentEventMetadata;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class MethodInvocationStrategy {
   private static final MethodInvocationStrategy DISPOSER_DEFAULT_STRATEGY = new DefaultMethodInvocationStrategy(IllegalArgumentException.class);
   private static final MethodInvocationStrategy DISPOSER_SIMPLE_STRATEGY = new SimpleMethodInvocationStrategy(IllegalArgumentException.class);
   private static final MethodInvocationStrategy OBSERVER_DEFAULT_STRATEGY = new DefaultMethodInvocationStrategy(ObserverException.class);
   private static final MethodInvocationStrategy OBSERVER_SIMPLE_STRATEGY = new SimpleMethodInvocationStrategy(ObserverException.class);
   private static final MethodInvocationStrategy OBSERVER_EVENT_PLUS_BEAN_MANAGER_STRATEGY = new SpecialParamPlusBeanManagerStrategy(ObserverException.class);
   protected final Class exceptionTypeToThrow;

   MethodInvocationStrategy(Class exceptionTypeToThrow) {
      this.exceptionTypeToThrow = exceptionTypeToThrow;
   }

   MethodInvocationStrategy() {
      this.exceptionTypeToThrow = null;
   }

   public static MethodInvocationStrategy forDisposer(MethodInjectionPoint method, BeanManagerImpl manager) {
      List parameters = method.getParameterInjectionPoints();
      return parameters.size() == 1 && ((ParameterInjectionPoint)parameters.get(0)).getAnnotated().isAnnotationPresent(Observes.class) ? DISPOSER_SIMPLE_STRATEGY : DISPOSER_DEFAULT_STRATEGY;
   }

   public static MethodInvocationStrategy forObserver(MethodInjectionPoint method, BeanManagerImpl manager) {
      List parameters = method.getParameterInjectionPoints();
      if (parameters.size() == 1 && ((ParameterInjectionPoint)parameters.get(0)).getAnnotated().isAnnotationPresent(Observes.class)) {
         return OBSERVER_SIMPLE_STRATEGY;
      } else {
         if (parameters.size() == 2 && ((ParameterInjectionPoint)parameters.get(0)).getAnnotated().isAnnotationPresent(Observes.class)) {
            if (BeanManager.class.equals(((ParameterInjectionPoint)parameters.get(1)).getType())) {
               return OBSERVER_EVENT_PLUS_BEAN_MANAGER_STRATEGY;
            }

            if (EventMetadata.class.equals(((ParameterInjectionPoint)parameters.get(1)).getType())) {
               return new EventPlusMetadataStrategy(manager);
            }
         }

         return OBSERVER_DEFAULT_STRATEGY;
      }
   }

   public abstract void invoke(Object var1, MethodInjectionPoint var2, Object var3, BeanManagerImpl var4, CreationalContext var5);

   private static class EventPlusMetadataStrategy extends MethodInvocationStrategy {
      private final CurrentEventMetadata metadata;

      private EventPlusMetadataStrategy(BeanManagerImpl manager) {
         this.metadata = (CurrentEventMetadata)manager.getServices().get(CurrentEventMetadata.class);
      }

      public void invoke(Object receiver, MethodInjectionPoint method, Object instance, BeanManagerImpl manager, CreationalContext creationalContext) {
         method.invoke(receiver, new Object[]{instance, this.metadata.peek()}, ObserverException.class);
      }

      // $FF: synthetic method
      EventPlusMetadataStrategy(BeanManagerImpl x0, Object x1) {
         this(x0);
      }
   }

   private static class SpecialParamPlusBeanManagerStrategy extends MethodInvocationStrategy {
      public SpecialParamPlusBeanManagerStrategy(Class exceptionTypeToThrow) {
         super(exceptionTypeToThrow);
      }

      public void invoke(Object receiver, MethodInjectionPoint method, Object instance, BeanManagerImpl manager, CreationalContext creationalContext) {
         method.invoke(receiver, new Object[]{instance, new BeanManagerProxy(manager)}, this.exceptionTypeToThrow);
      }
   }

   private static class SimpleMethodInvocationStrategy extends MethodInvocationStrategy {
      public SimpleMethodInvocationStrategy(Class exceptionTypeToThrow) {
         super(exceptionTypeToThrow);
      }

      public void invoke(Object receiver, MethodInjectionPoint method, Object instance, BeanManagerImpl manager, CreationalContext creationalContext) {
         method.invoke(receiver, instance, manager, creationalContext, this.exceptionTypeToThrow);
      }
   }

   private static class DefaultMethodInvocationStrategy extends MethodInvocationStrategy {
      public DefaultMethodInvocationStrategy(Class exceptionTypeToThrow) {
         super(exceptionTypeToThrow);
      }

      public void invoke(Object receiver, MethodInjectionPoint method, Object instance, BeanManagerImpl manager, CreationalContext creationalContext) {
         boolean release = creationalContext == null;
         if (release) {
            creationalContext = manager.createCreationalContext((Contextual)null);
         }

         try {
            method.invoke(receiver, instance, manager, (CreationalContext)creationalContext, this.exceptionTypeToThrow);
         } finally {
            if (release) {
               ((CreationalContext)creationalContext).release();
            }

         }

      }
   }
}
