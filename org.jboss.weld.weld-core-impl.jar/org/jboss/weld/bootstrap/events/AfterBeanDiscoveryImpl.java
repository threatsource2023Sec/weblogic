package org.jboss.weld.bootstrap.events;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.Context;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.DefinitionException;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.inject.spi.Prioritized;
import javax.enterprise.inject.spi.configurator.BeanConfigurator;
import javax.enterprise.inject.spi.configurator.ObserverMethodConfigurator;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeStore;
import org.jboss.weld.bean.CustomDecoratorWrapper;
import org.jboss.weld.bean.WeldBean;
import org.jboss.weld.bean.attributes.ExternalBeanAttributesFactory;
import org.jboss.weld.bootstrap.BeanDeploymentArchiveMapping;
import org.jboss.weld.bootstrap.BeanDeploymentFinder;
import org.jboss.weld.bootstrap.enablement.GlobalEnablementBuilder;
import org.jboss.weld.bootstrap.event.InterceptorConfigurator;
import org.jboss.weld.bootstrap.event.WeldAfterBeanDiscovery;
import org.jboss.weld.bootstrap.event.WeldBeanConfigurator;
import org.jboss.weld.bootstrap.events.configurator.BeanConfiguratorImpl;
import org.jboss.weld.bootstrap.events.configurator.ObserverMethodConfiguratorImpl;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.logging.InterceptorLogger;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Bindings;
import org.jboss.weld.util.Observers;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.reflection.Reflections;

public class AfterBeanDiscoveryImpl extends AbstractBeanDiscoveryEvent implements WeldAfterBeanDiscovery {
   private static final String TYPE_ARGUMENT_NAME = "type";
   private final SlimAnnotatedTypeStore slimAnnotatedTypeStore;
   private final ContainerLifecycleEvents containerLifecycleEvents;
   private final List additionalBeans;
   private final List additionalObservers;

   public static void fire(BeanManagerImpl beanManager, Deployment deployment, BeanDeploymentArchiveMapping bdaMapping, Collection contexts) {
      AfterBeanDiscoveryImpl event = new AfterBeanDiscoveryImpl(beanManager, deployment, bdaMapping, contexts);
      event.fire();
      event.finish();
   }

   private AfterBeanDiscoveryImpl(BeanManagerImpl beanManager, Deployment deployment, BeanDeploymentArchiveMapping bdaMapping, Collection contexts) {
      super(beanManager, WeldAfterBeanDiscovery.class, bdaMapping, deployment, contexts);
      this.slimAnnotatedTypeStore = (SlimAnnotatedTypeStore)beanManager.getServices().get(SlimAnnotatedTypeStore.class);
      this.containerLifecycleEvents = (ContainerLifecycleEvents)beanManager.getServices().get(ContainerLifecycleEvents.class);
      this.additionalBeans = new LinkedList();
      this.additionalObservers = new LinkedList();
   }

   public void addBean(Bean bean) {
      this.checkWithinObserverNotification();
      Preconditions.checkArgumentNotNull(bean, "bean");
      ExternalBeanAttributesFactory.validateBeanAttributes(bean, this.getBeanManager());
      this.validateBean(bean);
      this.additionalBeans.add(AfterBeanDiscoveryImpl.BeanRegistration.of(bean, this.getReceiver()));
      BootstrapLogger.LOG.addBeanCalled(this.getReceiver(), bean);
   }

   public WeldBeanConfigurator addBean() {
      this.checkWithinObserverNotification();
      BeanConfiguratorImpl configurator = new BeanConfiguratorImpl(this.getReceiver().getClass(), this.getBeanDeploymentFinder());
      this.additionalBeans.add(AfterBeanDiscoveryImpl.BeanRegistration.of(configurator, this.getReceiver()));
      return configurator;
   }

   public void addContext(Context context) {
      this.checkWithinObserverNotification();
      Preconditions.checkArgumentNotNull(context, "context");
      Class scope = context.getScope();
      if (scope == null) {
         throw ContextLogger.LOG.contextHasNullScope(context);
      } else {
         if (!this.getBeanManager().isScope(scope)) {
            MetadataLogger.LOG.contextGetScopeIsNotAScope(scope, context);
         }

         if (scope != ApplicationScoped.class && scope != Dependent.class) {
            this.getBeanManager().addContext(context);
            BootstrapLogger.LOG.addContext(this.getReceiver(), context);
         } else {
            throw ContextLogger.LOG.cannotRegisterContext(scope, context);
         }
      }
   }

   public void addObserverMethod(ObserverMethod observerMethod) {
      this.checkWithinObserverNotification();
      Preconditions.checkArgumentNotNull(observerMethod, "observerMethod");
      Observers.validateObserverMethod(observerMethod, this.getBeanManager(), (ObserverMethod)null);
      this.additionalObservers.add(AfterBeanDiscoveryImpl.ObserverRegistration.of(observerMethod, this.getReceiver()));
      BootstrapLogger.LOG.addObserverMethodCalled(this.getReceiver(), observerMethod);
   }

   public ObserverMethodConfigurator addObserverMethod() {
      this.checkWithinObserverNotification();
      ObserverMethodConfiguratorImpl configurator = new ObserverMethodConfiguratorImpl(this.getReceiver());
      this.additionalObservers.add(AfterBeanDiscoveryImpl.ObserverRegistration.of(configurator, this.getReceiver()));
      return configurator;
   }

   public AnnotatedType getAnnotatedType(Class type, String id) {
      this.checkWithinObserverNotification();
      Preconditions.checkArgumentNotNull(type, "type");
      return this.slimAnnotatedTypeStore.get(type, id);
   }

   public Iterable getAnnotatedTypes(Class type) {
      this.checkWithinObserverNotification();
      Preconditions.checkArgumentNotNull(type, "type");
      return (Iterable)Reflections.cast(this.slimAnnotatedTypeStore.get(type));
   }

   public InterceptorConfigurator addInterceptor() {
      InterceptorConfiguratorImpl configurator = new InterceptorConfiguratorImpl(this.getBeanManager());
      this.additionalBeans.add(AfterBeanDiscoveryImpl.BeanRegistration.of(configurator));
      return configurator;
   }

   private void finish() {
      try {
         GlobalEnablementBuilder globalEnablementBuilder = (GlobalEnablementBuilder)this.getBeanManager().getServices().get(GlobalEnablementBuilder.class);
         Iterator var2 = this.additionalBeans.iterator();

         while(var2.hasNext()) {
            BeanRegistration registration = (BeanRegistration)var2.next();
            this.processBeanRegistration(registration, globalEnablementBuilder);
         }

         var2 = this.additionalObservers.iterator();

         while(var2.hasNext()) {
            ObserverRegistration registration = (ObserverRegistration)var2.next();
            this.processObserverRegistration(registration);
         }

      } catch (Exception var4) {
         throw new DefinitionException(var4);
      }
   }

   private void processBeanRegistration(BeanRegistration registration, GlobalEnablementBuilder globalEnablementBuilder) {
      Bean bean = registration.initBean();
      BeanManagerImpl beanManager = registration.initBeanManager();
      if (beanManager == null) {
         beanManager = this.getOrCreateBeanDeployment(bean.getBeanClass()).getBeanManager();
      } else {
         ExternalBeanAttributesFactory.validateBeanAttributes(bean, this.getBeanManager());
         this.validateBean(bean);
      }

      Integer priority = bean instanceof Prioritized ? ((Prioritized)bean).getPriority() : null;
      if (priority == null && bean instanceof WeldBean) {
         priority = ((WeldBean)bean).getPriority();
      }

      if (bean instanceof Interceptor) {
         beanManager.addInterceptor((Interceptor)bean);
         if (priority != null) {
            globalEnablementBuilder.addInterceptor(bean.getBeanClass(), priority);
         }
      } else if (bean instanceof Decorator) {
         beanManager.addDecorator(CustomDecoratorWrapper.of((Decorator)bean, beanManager));
         if (priority != null) {
            globalEnablementBuilder.addDecorator(bean.getBeanClass(), priority);
         }
      } else {
         beanManager.addBean(bean);
         if (priority != null && bean.isAlternative()) {
            globalEnablementBuilder.addAlternative(bean.getBeanClass(), priority);
         }
      }

      this.containerLifecycleEvents.fireProcessBean(beanManager, bean, registration.extension);
   }

   private void validateBean(Bean bean) {
      if (bean.getBeanClass() == null) {
         throw BeanLogger.LOG.beanMethodReturnsNull("getBeanClass", bean);
      } else if (bean.getInjectionPoints() == null) {
         throw BeanLogger.LOG.beanMethodReturnsNull("getInjectionPoints", bean);
      } else {
         if (bean instanceof PassivationCapable) {
            PassivationCapable passivationCapable = (PassivationCapable)bean;
            if (passivationCapable.getId() == null) {
               throw BeanLogger.LOG.passivationCapableBeanHasNullId(bean);
            }
         }

         if (bean instanceof Interceptor) {
            this.validateInterceptor((Interceptor)bean);
         } else if (bean instanceof Decorator) {
            this.validateDecorator((Decorator)bean);
         }

      }
   }

   private void validateInterceptor(Interceptor interceptor) {
      Set bindings = interceptor.getInterceptorBindings();
      if (bindings == null) {
         throw InterceptorLogger.LOG.nullInterceptorBindings(interceptor);
      } else {
         Iterator var3 = bindings.iterator();

         Annotation annotation;
         do {
            if (!var3.hasNext()) {
               return;
            }

            annotation = (Annotation)var3.next();
         } while(this.getBeanManager().isInterceptorBinding(annotation.annotationType()));

         throw MetadataLogger.LOG.notAnInterceptorBinding(annotation, interceptor);
      }
   }

   private void validateDecorator(Decorator decorator) {
      Set qualifiers = decorator.getDelegateQualifiers();
      if (decorator.getDelegateType() == null) {
         throw BeanLogger.LOG.decoratorMethodReturnsNull("getDelegateType", decorator);
      } else {
         Bindings.validateQualifiers(qualifiers, this.getBeanManager(), decorator, "Decorator.getDelegateQualifiers");
         if (decorator.getDecoratedTypes() == null) {
            throw BeanLogger.LOG.decoratorMethodReturnsNull("getDecoratedTypes", decorator);
         }
      }
   }

   private void processObserverRegistration(ObserverRegistration registration) {
      ObserverMethod observerMethod = registration.initObserverMethod();
      Observers.validateObserverMethod(observerMethod, this.getBeanManager(), (ObserverMethod)null);
      BeanManagerImpl manager = this.getOrCreateBeanDeployment(observerMethod.getBeanClass()).getBeanManager();
      if (Observers.isObserverMethodEnabled(observerMethod, manager)) {
         ObserverMethod processedObserver = this.containerLifecycleEvents.fireProcessObserverMethod(manager, observerMethod, registration.extension);
         if (processedObserver != null) {
            manager.addObserver(processedObserver);
         }
      }

   }

   private BeanDeploymentFinder getBeanDeploymentFinder() {
      return new BeanDeploymentFinder(this.getBeanDeployments(), this.getDeployment(), this.getContexts(), this.getBeanManager());
   }

   private static class ObserverRegistration {
      private final ObserverMethod observerMethod;
      private final Extension extension;
      private final ObserverMethodConfiguratorImpl observerMethodConfigurator;

      static ObserverRegistration of(ObserverMethod observerMethod, Extension extension) {
         return new ObserverRegistration(observerMethod, (ObserverMethodConfiguratorImpl)null, extension);
      }

      static ObserverRegistration of(ObserverMethodConfiguratorImpl configurator, Extension extension) {
         return new ObserverRegistration((ObserverMethod)null, configurator, extension);
      }

      private ObserverRegistration(ObserverMethod observerMethod, ObserverMethodConfiguratorImpl observerMethodBuilder, Extension extension) {
         this.observerMethod = observerMethod;
         this.observerMethodConfigurator = observerMethodBuilder;
         this.extension = extension;
      }

      ObserverMethod initObserverMethod() {
         if (this.observerMethod != null) {
            return this.observerMethod;
         } else {
            try {
               return this.observerMethodConfigurator.complete();
            } catch (Exception var2) {
               throw BootstrapLogger.LOG.unableToProcessConfigurator(ObserverMethodConfigurator.class.getSimpleName(), this.extension, var2);
            }
         }
      }
   }

   private static class BeanRegistration {
      private final Bean bean;
      private final BeanConfiguratorImpl beanConfigurator;
      private final InterceptorConfiguratorImpl interceptorBuilder;
      private final Extension extension;

      static BeanRegistration of(Bean bean, Extension extension) {
         return new BeanRegistration(bean, (BeanConfiguratorImpl)null, (InterceptorConfiguratorImpl)null, extension);
      }

      static BeanRegistration of(BeanConfiguratorImpl configurator, Extension extension) {
         return new BeanRegistration((Bean)null, configurator, (InterceptorConfiguratorImpl)null, extension);
      }

      static BeanRegistration of(InterceptorConfiguratorImpl interceptorBuilder) {
         return new BeanRegistration((Bean)null, (BeanConfiguratorImpl)null, interceptorBuilder, (Extension)null);
      }

      BeanRegistration(Bean bean, BeanConfiguratorImpl beanConfigurator, InterceptorConfiguratorImpl interceptorBuilder, Extension extension) {
         this.bean = bean;
         this.beanConfigurator = beanConfigurator;
         this.interceptorBuilder = interceptorBuilder;
         this.extension = extension;
      }

      public Bean initBean() {
         if (this.bean != null) {
            return this.bean;
         } else if (this.beanConfigurator != null) {
            Bean bean;
            try {
               bean = this.beanConfigurator.complete();
            } catch (Exception var3) {
               throw BootstrapLogger.LOG.unableToProcessConfigurator(BeanConfigurator.class.getSimpleName(), this.extension, var3);
            }

            BootstrapLogger.LOG.addBeanCalled(this.extension, bean);
            return bean;
         } else {
            return this.interceptorBuilder.build();
         }
      }

      protected BeanManagerImpl initBeanManager() {
         if (this.bean != null) {
            return null;
         } else {
            return this.beanConfigurator != null ? this.beanConfigurator.getBeanManager() : this.interceptorBuilder.getBeanManager();
         }
      }
   }
}
