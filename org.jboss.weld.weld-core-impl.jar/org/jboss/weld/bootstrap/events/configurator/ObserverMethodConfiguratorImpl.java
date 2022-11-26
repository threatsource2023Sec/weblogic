package org.jboss.weld.bootstrap.events.configurator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Priority;
import javax.enterprise.event.ObserverException;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.EventContext;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.configurator.ObserverMethodConfigurator;
import org.jboss.weld.event.SyntheticObserverMethod;
import org.jboss.weld.logging.EventLogger;
import org.jboss.weld.resolution.CovariantTypes;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;

public class ObserverMethodConfiguratorImpl implements ObserverMethodConfigurator, Configurator {
   private Class beanClass;
   private Type observedType;
   private final Set observedQualifiers;
   private Reception reception;
   private TransactionPhase txPhase;
   private int priority;
   private boolean isAsync;
   private ObserverMethodConfigurator.EventConsumer notifyCallback;
   private final Extension extension;

   public ObserverMethodConfiguratorImpl(Extension extension) {
      this.reception = Reception.ALWAYS;
      this.txPhase = TransactionPhase.IN_PROGRESS;
      this.observedQualifiers = new HashSet();
      this.priority = 2500;
      this.extension = extension;
      this.beanClass = extension.getClass();
   }

   public ObserverMethodConfiguratorImpl(ObserverMethod observerMethod, Extension extension) {
      this(extension);
      this.read(observerMethod);
      this.notifyWith((e) -> {
         observerMethod.notify(e);
      });
   }

   public ObserverMethodConfigurator read(Method method) {
      Preconditions.checkArgumentNotNull(method);
      Set eventParameters = Configurators.getAnnotatedParameters(method, Observes.class, ObservesAsync.class);
      this.checkEventParams(eventParameters, method);
      Parameter eventParameter = (Parameter)eventParameters.iterator().next();
      Observes observesAnnotation = (Observes)eventParameter.getAnnotation(Observes.class);
      if (observesAnnotation != null) {
         this.reception(observesAnnotation.notifyObserver());
         this.transactionPhase(observesAnnotation.during());
      } else {
         this.reception(((ObservesAsync)eventParameter.getAnnotation(ObservesAsync.class)).notifyObserver());
      }

      Priority priority = (Priority)method.getAnnotation(Priority.class);
      if (priority != null) {
         this.priority(priority.value());
      }

      this.beanClass(eventParameter.getDeclaringExecutable().getDeclaringClass());
      this.observedType(eventParameter.getType());
      this.qualifiers(Configurators.getQualifiers((AnnotatedElement)eventParameter));
      return this;
   }

   public ObserverMethodConfigurator read(AnnotatedMethod method) {
      Preconditions.checkArgumentNotNull(method);
      Set eventParameters = (Set)method.getParameters().stream().filter((p) -> {
         return p.isAnnotationPresent(Observes.class) || p.isAnnotationPresent(ObservesAsync.class);
      }).collect(Collectors.toSet());
      this.checkEventParams(eventParameters, method.getJavaMember());
      AnnotatedParameter eventParameter = (AnnotatedParameter)eventParameters.iterator().next();
      Observes observesAnnotation = (Observes)eventParameter.getAnnotation(Observes.class);
      if (observesAnnotation != null) {
         this.reception(observesAnnotation.notifyObserver());
         this.transactionPhase(observesAnnotation.during());
      } else {
         this.reception(((ObservesAsync)eventParameter.getAnnotation(ObservesAsync.class)).notifyObserver());
      }

      Priority priority = (Priority)method.getAnnotation(Priority.class);
      if (priority != null) {
         this.priority(priority.value());
      }

      this.beanClass(eventParameter.getDeclaringCallable().getDeclaringType().getJavaClass());
      this.observedType(eventParameter.getBaseType());
      this.qualifiers(Configurators.getQualifiers((Annotated)eventParameter));
      return this;
   }

   public ObserverMethodConfigurator read(ObserverMethod observerMethod) {
      Preconditions.checkArgumentNotNull(observerMethod);
      this.beanClass(observerMethod.getBeanClass());
      this.observedType(observerMethod.getObservedType());
      this.qualifiers(observerMethod.getObservedQualifiers());
      this.reception(observerMethod.getReception());
      this.transactionPhase(observerMethod.getTransactionPhase());
      this.priority(observerMethod.getPriority());
      this.async(observerMethod.isAsync());
      return this;
   }

   public ObserverMethodConfigurator beanClass(Class beanClass) {
      this.beanClass = beanClass;
      return this;
   }

   public ObserverMethodConfigurator observedType(Type type) {
      Preconditions.checkArgumentNotNull(type);
      if (this.observedType != null && !CovariantTypes.isAssignableFrom(this.observedType, type)) {
         EventLogger.LOG.originalObservedTypeIsNotAssignableFrom(this.observedType, type, this.extension);
      }

      this.observedType = type;
      return this;
   }

   public ObserverMethodConfigurator addQualifier(Annotation qualifier) {
      Preconditions.checkArgumentNotNull(qualifier);
      this.observedQualifiers.add(qualifier);
      return this;
   }

   public ObserverMethodConfigurator addQualifiers(Annotation... qualifiers) {
      Preconditions.checkArgumentNotNull(qualifiers);
      Collections.addAll(this.observedQualifiers, qualifiers);
      return this;
   }

   public ObserverMethodConfigurator addQualifiers(Set qualifiers) {
      Preconditions.checkArgumentNotNull(qualifiers);
      this.observedQualifiers.addAll(qualifiers);
      return this;
   }

   public ObserverMethodConfigurator qualifiers(Annotation... qualifiers) {
      this.observedQualifiers.clear();
      this.addQualifiers(qualifiers);
      return this;
   }

   public ObserverMethodConfigurator qualifiers(Set qualifiers) {
      this.observedQualifiers.clear();
      this.addQualifiers(qualifiers);
      return this;
   }

   public ObserverMethodConfigurator reception(Reception reception) {
      Preconditions.checkArgumentNotNull(reception);
      this.reception = reception;
      return this;
   }

   public ObserverMethodConfigurator transactionPhase(TransactionPhase transactionPhase) {
      Preconditions.checkArgumentNotNull(transactionPhase);
      this.txPhase = transactionPhase;
      return this;
   }

   public ObserverMethodConfigurator priority(int priority) {
      this.priority = priority;
      return this;
   }

   public ObserverMethodConfigurator notifyWith(ObserverMethodConfigurator.EventConsumer callback) {
      Preconditions.checkArgumentNotNull(callback);
      this.notifyCallback = callback;
      return this;
   }

   public ObserverMethodConfigurator async(boolean async) {
      this.isAsync = async;
      return this;
   }

   public ObserverMethod complete() {
      return new ImmutableObserverMethod(this);
   }

   private void checkEventParams(Set eventParams, Method method) {
      if (eventParams.size() != 1) {
         EventLogger.LOG.noneOrMultipleEventParametersDeclared(method, Formats.formatAsStackTraceElement((Member)method));
      }

   }

   static class ImmutableObserverMethod implements SyntheticObserverMethod {
      private final Class beanClass;
      private final Type observedType;
      private final Set observedQualifiers;
      private final Reception reception;
      private final TransactionPhase txPhase;
      private final int priority;
      private final boolean isAsync;
      private final ObserverMethodConfigurator.EventConsumer notifyCallback;

      ImmutableObserverMethod(ObserverMethodConfiguratorImpl configurator) {
         if (configurator.notifyCallback == null) {
            throw EventLogger.LOG.notifyMethodNotImplemented(configurator);
         } else {
            this.beanClass = configurator.beanClass;
            this.observedType = configurator.observedType;
            this.observedQualifiers = ImmutableSet.copyOf(configurator.observedQualifiers);
            this.reception = configurator.reception;
            this.txPhase = configurator.txPhase;
            this.priority = configurator.priority;
            this.isAsync = configurator.isAsync;
            this.notifyCallback = configurator.notifyCallback;
         }
      }

      public int getPriority() {
         return this.priority;
      }

      public Class getBeanClass() {
         return this.beanClass;
      }

      public Type getObservedType() {
         return this.observedType;
      }

      public Set getObservedQualifiers() {
         return this.observedQualifiers;
      }

      public Reception getReception() {
         return this.reception;
      }

      public TransactionPhase getTransactionPhase() {
         return this.txPhase;
      }

      public void notify(EventContext eventContext) {
         try {
            this.notifyCallback.accept(eventContext);
         } catch (Exception var3) {
            throw new ObserverException(var3);
         }
      }

      public boolean isAsync() {
         return this.isAsync;
      }

      public boolean isEventMetadataRequired() {
         return true;
      }

      public String toString() {
         return "Configurator observer method [Bean class = " + this.getBeanClass() + ", type = " + this.getObservedType() + ", qualifiers =" + Formats.formatAnnotations((Iterable)this.getObservedQualifiers()) + ", priority =" + this.getPriority() + ", async =" + this.isAsync() + ", reception =" + this.getReception() + ", transaction phase =" + this.getTransactionPhase() + "]";
      }
   }
}
