package org.jboss.weld.manager;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bootstrap.events.configurator.AnnotatedTypeConfiguratorImpl;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.injection.producer.BasicInjectionTarget;
import org.jboss.weld.injection.producer.BeanInjectionTarget;
import org.jboss.weld.injection.producer.DecoratorInjectionTarget;
import org.jboss.weld.injection.producer.InjectionTargetService;
import org.jboss.weld.injection.producer.NonProducibleInjectionTarget;
import org.jboss.weld.injection.spi.InjectionServices;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.manager.api.WeldInjectionTarget;
import org.jboss.weld.manager.api.WeldInjectionTargetFactory;
import org.jboss.weld.module.EjbSupport;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.InjectionTargets;

public class InjectionTargetFactoryImpl implements WeldInjectionTargetFactory {
   private final BeanManagerImpl manager;
   private final EnhancedAnnotatedType originalAnnotatedType;
   private final InjectionTargetService injectionTargetService;
   private final InjectionServices injectionServices;
   private volatile EnhancedAnnotatedType annotatedType;
   private volatile AnnotatedTypeConfiguratorImpl configurator;

   protected InjectionTargetFactoryImpl(AnnotatedType type, BeanManagerImpl manager) {
      this.manager = manager;
      this.originalAnnotatedType = ((ClassTransformer)manager.getServices().get(ClassTransformer.class)).getEnhancedAnnotatedType(type, manager.getId());
      this.injectionTargetService = (InjectionTargetService)manager.getServices().get(InjectionTargetService.class);
      this.injectionServices = (InjectionServices)manager.getServices().get(InjectionServices.class);
   }

   public WeldInjectionTarget createInjectionTarget(Bean bean) {
      return this.createInjectionTarget(bean, false);
   }

   public WeldInjectionTarget createInterceptorInjectionTarget() {
      return this.createInjectionTarget((Bean)null, true);
   }

   private WeldInjectionTarget createInjectionTarget(Bean bean, boolean interceptor) {
      try {
         this.initAnnotatedType();
         return (WeldInjectionTarget)this.validate(this.createInjectionTarget(this.annotatedType, bean, interceptor));
      } catch (Throwable var4) {
         throw new IllegalArgumentException(var4);
      }
   }

   public BasicInjectionTarget createInjectionTarget(EnhancedAnnotatedType type, Bean bean, boolean interceptor) {
      BasicInjectionTarget injectionTarget = this.chooseInjectionTarget(type, bean, interceptor);
      this.initialize(injectionTarget);
      this.postProcess(injectionTarget);
      return injectionTarget;
   }

   public synchronized AnnotatedTypeConfigurator configure() {
      if (this.annotatedType != null) {
         BeanManagerLogger.LOG.unableToConfigureInjectionTargetFactory(this.annotatedType);
         throw new IllegalStateException();
      } else {
         if (this.configurator == null) {
            this.configurator = new AnnotatedTypeConfiguratorImpl(this.originalAnnotatedType);
         }

         return this.configurator;
      }
   }

   private synchronized void initAnnotatedType() {
      if (this.annotatedType == null) {
         if (this.configurator != null) {
            AnnotatedType configuredType = this.configurator.complete();
            this.annotatedType = ((ClassTransformer)this.manager.getServices().get(ClassTransformer.class)).getEnhancedAnnotatedType(configuredType, this.manager.getId());
         } else {
            this.annotatedType = this.originalAnnotatedType;
         }

      }
   }

   private BasicInjectionTarget chooseInjectionTarget(EnhancedAnnotatedType type, Bean bean, boolean interceptor) {
      if (!(bean instanceof Decorator) && !type.isAnnotationPresent(javax.decorator.Decorator.class)) {
         NonProducibleInjectionTarget nonProducible = InjectionTargets.createNonProducibleInjectionTarget(type, bean, this.manager);
         if (nonProducible != null) {
            return nonProducible;
         } else if (bean instanceof SessionBean) {
            return ((EjbSupport)this.manager.getServices().get(EjbSupport.class)).createSessionBeanInjectionTarget(type, (SessionBean)bean, this.manager);
         } else if (!(bean instanceof Interceptor) && !type.isAnnotationPresent(javax.interceptor.Interceptor.class)) {
            return (BasicInjectionTarget)(interceptor ? BasicInjectionTarget.createNonCdiInterceptor(type, this.manager) : BeanInjectionTarget.createDefault(type, bean, this.manager));
         } else {
            return BeanInjectionTarget.forCdiInterceptor(type, bean, this.manager);
         }
      } else {
         return new DecoratorInjectionTarget(type, bean, this.manager);
      }
   }

   protected InjectionTarget createMessageDrivenInjectionTarget(EjbDescriptor descriptor) {
      return this.prepareInjectionTarget(((EjbSupport)this.manager.getServices().get(EjbSupport.class)).createMessageDrivenInjectionTarget(this.originalAnnotatedType, descriptor, this.manager));
   }

   private BasicInjectionTarget initialize(BasicInjectionTarget injectionTarget) {
      this.injectionTargetService.addInjectionTargetToBeInitialized(this.originalAnnotatedType, injectionTarget);
      return injectionTarget;
   }

   private InjectionTarget validate(InjectionTarget injectionTarget) {
      this.injectionTargetService.validateProducer(injectionTarget);
      return injectionTarget;
   }

   private void postProcess(InjectionTarget injectionTarget) {
      if (this.injectionServices != null) {
         this.injectionServices.registerInjectionTarget(injectionTarget, this.originalAnnotatedType.slim());
      }

   }

   private BasicInjectionTarget prepareInjectionTarget(BasicInjectionTarget injectionTarget) {
      try {
         this.postProcess(this.initialize((BasicInjectionTarget)this.validate(injectionTarget)));
         return injectionTarget;
      } catch (Throwable var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   public WeldInjectionTarget createNonProducibleInjectionTarget() {
      return this.prepareInjectionTarget(NonProducibleInjectionTarget.create(this.originalAnnotatedType, (Bean)null, this.manager));
   }
}
