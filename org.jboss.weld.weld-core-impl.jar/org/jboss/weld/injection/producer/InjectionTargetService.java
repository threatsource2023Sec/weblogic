package org.jboss.weld.injection.producer;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Producer;
import org.jboss.weld.Container;
import org.jboss.weld.ContainerState;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bootstrap.Validator;
import org.jboss.weld.bootstrap.api.helpers.AbstractBootstrapService;
import org.jboss.weld.manager.BeanManagerImpl;

public class InjectionTargetService extends AbstractBootstrapService {
   private final Validator validator;
   private final Container container;
   private final BeanManagerImpl beanManager;
   private final Collection producersToValidate;
   private final Collection injectionTargetsToInitialize;

   public InjectionTargetService(BeanManagerImpl beanManager) {
      this.validator = (Validator)beanManager.getServices().get(Validator.class);
      this.producersToValidate = new ConcurrentLinkedQueue();
      this.injectionTargetsToInitialize = new ConcurrentLinkedQueue();
      this.container = Container.instance(beanManager);
      this.beanManager = beanManager;
   }

   public void validateProducer(Producer producer) {
      if (!this.container.getState().equals(ContainerState.VALIDATED) && !this.container.getState().equals(ContainerState.INITIALIZED)) {
         Iterator var2 = producer.getInjectionPoints().iterator();

         while(var2.hasNext()) {
            InjectionPoint ip = (InjectionPoint)var2.next();
            this.validator.validateInjectionPointForDefinitionErrors(ip, ip.getBean(), this.beanManager);
            this.validator.validateEventMetadataInjectionPoint(ip);
         }

         this.producersToValidate.add(producer);
      } else {
         this.validator.validateProducer(producer, this.beanManager);
      }

   }

   public void addInjectionTargetToBeInitialized(EnhancedAnnotatedType type, BasicInjectionTarget injectionTarget) {
      this.addInjectionTargetToBeInitialized(new InjectionTargetInitializationContext(type, injectionTarget));
   }

   public void addInjectionTargetToBeInitialized(InjectionTargetInitializationContext initializationContext) {
      if (!this.container.getState().equals(ContainerState.VALIDATED) && !this.container.getState().equals(ContainerState.INITIALIZED)) {
         this.injectionTargetsToInitialize.add(initializationContext);
      } else {
         initializationContext.initialize();
      }

   }

   public void initialize() {
      Iterator var1 = this.injectionTargetsToInitialize.iterator();

      while(var1.hasNext()) {
         InjectionTargetInitializationContext initializationContext = (InjectionTargetInitializationContext)var1.next();
         initializationContext.initialize();
      }

      this.injectionTargetsToInitialize.clear();
   }

   public void validate() {
      this.validator.validateProducers(this.producersToValidate, this.beanManager);
      this.producersToValidate.clear();
   }

   public void cleanupAfterBoot() {
      this.producersToValidate.clear();
      this.injectionTargetsToInitialize.clear();
   }
}
