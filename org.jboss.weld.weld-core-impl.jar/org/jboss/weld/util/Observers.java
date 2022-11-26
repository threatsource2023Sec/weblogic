package org.jboss.weld.util;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Set;
import javax.enterprise.inject.Any.Literal;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.EventContext;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessBeanAttributes;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessManagedBean;
import javax.enterprise.inject.spi.ProcessObserverMethod;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.inject.spi.ProcessProducerField;
import javax.enterprise.inject.spi.ProcessProducerMethod;
import javax.enterprise.inject.spi.ProcessSessionBean;
import javax.enterprise.inject.spi.ProcessSyntheticAnnotatedType;
import javax.enterprise.inject.spi.ProcessSyntheticBean;
import javax.enterprise.inject.spi.ProcessSyntheticObserverMethod;
import org.jboss.weld.bootstrap.SpecializationAndEnablementRegistry;
import org.jboss.weld.bootstrap.event.WeldAfterBeanDiscovery;
import org.jboss.weld.event.ContainerLifecycleEventObserverMethod;
import org.jboss.weld.event.EventMetadataAwareObserverMethod;
import org.jboss.weld.event.ObserverMethodImpl;
import org.jboss.weld.event.SyntheticObserverMethod;
import org.jboss.weld.logging.EventLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.security.GetDeclaredMethodsAction;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class Observers {
   public static final Set CONTAINER_LIFECYCLE_EVENT_CANONICAL_SUPERTYPES = ImmutableSet.of(BeforeBeanDiscovery.class, AfterTypeDiscovery.class, AfterBeanDiscovery.class, AfterDeploymentValidation.class, BeforeShutdown.class, ProcessAnnotatedType.class, ProcessInjectionPoint.class, ProcessInjectionTarget.class, ProcessProducer.class, ProcessBeanAttributes.class, ProcessBean.class, ProcessObserverMethod.class);
   public static final Set CONTAINER_LIFECYCLE_EVENT_TYPES;
   private static final String NOTIFY_METHOD_NAME = "notify";

   private Observers() {
   }

   public static boolean isContainerLifecycleObserverMethod(ObserverMethod method) {
      if (CONTAINER_LIFECYCLE_EVENT_TYPES.contains(Reflections.getRawType(method.getObservedType()))) {
         return true;
      } else {
         if (Object.class.equals(method.getObservedType()) && method instanceof ContainerLifecycleEventObserverMethod) {
            if (method.getObservedQualifiers().isEmpty()) {
               return true;
            }

            if (method.getObservedQualifiers().size() == 1 && method.getObservedQualifiers().contains(Literal.INSTANCE)) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean isObserverMethodEnabled(ObserverMethod method, BeanManagerImpl manager) {
      if (method instanceof ObserverMethodImpl) {
         Bean declaringBean = ((ObserverMethodImpl)Reflections.cast(method)).getDeclaringBean();
         return ((SpecializationAndEnablementRegistry)manager.getServices().get(SpecializationAndEnablementRegistry.class)).isCandidateForLifecycleEvent(declaringBean);
      } else {
         return true;
      }
   }

   public static void validateObserverMethod(ObserverMethod observerMethod, BeanManager beanManager, ObserverMethod originalObserverMethod) {
      Set qualifiers = observerMethod.getObservedQualifiers();
      if (observerMethod.getBeanClass() == null) {
         throw EventLogger.LOG.observerMethodsMethodReturnsNull("getBeanClass", observerMethod);
      } else if (observerMethod.getObservedType() == null) {
         throw EventLogger.LOG.observerMethodsMethodReturnsNull("getObservedType", observerMethod);
      } else {
         Bindings.validateQualifiers(qualifiers, beanManager, observerMethod, "ObserverMethod.getObservedQualifiers");
         if (observerMethod.getReception() == null) {
            throw EventLogger.LOG.observerMethodsMethodReturnsNull("getReception", observerMethod);
         } else if (observerMethod.getTransactionPhase() == null) {
            throw EventLogger.LOG.observerMethodsMethodReturnsNull("getTransactionPhase", observerMethod);
         } else if (originalObserverMethod != null && !observerMethod.getBeanClass().equals(originalObserverMethod.getBeanClass())) {
            throw EventLogger.LOG.beanClassMismatch(originalObserverMethod, observerMethod);
         } else if (!(observerMethod instanceof SyntheticObserverMethod) && !hasNotifyOverriden(observerMethod.getClass(), observerMethod)) {
            throw EventLogger.LOG.notifyMethodNotImplemented(observerMethod);
         }
      }
   }

   public static boolean isEventMetadataRequired(ObserverMethod observer) {
      if (observer instanceof EventMetadataAwareObserverMethod) {
         EventMetadataAwareObserverMethod eventMetadataAware = (EventMetadataAwareObserverMethod)observer;
         return eventMetadataAware.isEventMetadataRequired();
      } else {
         return true;
      }
   }

   public static void notify(ObserverMethod observerMethod, Object event, EventMetadata metadata) {
      observerMethod.notify(new EventContextImpl(event, metadata));
   }

   private static boolean hasNotifyOverriden(Class clazz, ObserverMethod observerMethod) {
      if (clazz.isInterface()) {
         return false;
      } else {
         Method[] var2 = (Method[])AccessController.doPrivileged(new GetDeclaredMethodsAction(clazz));
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method method = var2[var4];
            if ("notify".equals(method.getName()) && method.getParameterTypes().length == 1) {
               return true;
            }
         }

         return clazz.getSuperclass() != null ? hasNotifyOverriden(clazz.getSuperclass(), observerMethod) : false;
      }
   }

   static {
      CONTAINER_LIFECYCLE_EVENT_TYPES = ImmutableSet.builder().addAll((Iterable)CONTAINER_LIFECYCLE_EVENT_CANONICAL_SUPERTYPES).addAll((Object[])(ProcessSyntheticAnnotatedType.class, ProcessSessionBean.class, ProcessManagedBean.class, ProcessProducerMethod.class, ProcessProducerField.class, ProcessSyntheticBean.class, ProcessSyntheticObserverMethod.class, WeldAfterBeanDiscovery.class)).build();
   }

   static class EventContextImpl implements EventContext {
      private final Object event;
      private final EventMetadata metadata;

      EventContextImpl(Object event, EventMetadata metadata) {
         this.event = event;
         this.metadata = metadata;
      }

      public Object getEvent() {
         return this.event;
      }

      public EventMetadata getMetadata() {
         return this.metadata;
      }
   }
}
