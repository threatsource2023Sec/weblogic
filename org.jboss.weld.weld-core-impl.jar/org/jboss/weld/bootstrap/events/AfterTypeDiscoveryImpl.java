package org.jboss.weld.bootstrap.events;

import java.util.Collection;
import java.util.List;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeContext;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeStore;
import org.jboss.weld.bootstrap.BeanDeployment;
import org.jboss.weld.bootstrap.BeanDeploymentArchiveMapping;
import org.jboss.weld.bootstrap.enablement.GlobalEnablementBuilder;
import org.jboss.weld.bootstrap.events.configurator.AnnotatedTypeConfiguratorImpl;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;

public class AfterTypeDiscoveryImpl extends AbstractAnnotatedTypeRegisteringEvent implements AfterTypeDiscovery {
   private final GlobalEnablementBuilder builder;
   private final ContainerLifecycleEvents events;
   private final ClassTransformer transformer;
   private final SlimAnnotatedTypeStore store;

   public static void fire(BeanManagerImpl beanManager, Deployment deployment, BeanDeploymentArchiveMapping bdaMapping, Collection contexts) {
      AfterTypeDiscoveryImpl event = new AfterTypeDiscoveryImpl(beanManager, bdaMapping, deployment, contexts);
      event.fire();
      event.finish();
   }

   protected AfterTypeDiscoveryImpl(BeanManagerImpl beanManager, BeanDeploymentArchiveMapping bdaMapping, Deployment deployment, Collection contexts) {
      super(beanManager, AfterTypeDiscovery.class, bdaMapping, deployment, contexts);
      this.builder = (GlobalEnablementBuilder)beanManager.getServices().get(GlobalEnablementBuilder.class);
      this.events = (ContainerLifecycleEvents)beanManager.getServices().get(ContainerLifecycleEvents.class);
      this.transformer = (ClassTransformer)beanManager.getServices().get(ClassTransformer.class);
      this.store = (SlimAnnotatedTypeStore)beanManager.getServices().get(SlimAnnotatedTypeStore.class);
   }

   public List getAlternatives() {
      this.checkWithinObserverNotification();
      return this.builder.getAlternativeList(this.getReceiver());
   }

   public List getInterceptors() {
      this.checkWithinObserverNotification();
      return this.builder.getInterceptorList(this.getReceiver());
   }

   public List getDecorators() {
      this.checkWithinObserverNotification();
      return this.builder.getDecoratorList(this.getReceiver());
   }

   public void addAnnotatedType(AnnotatedType type, String id) {
      this.checkWithinObserverNotification();
      this.addSyntheticAnnotatedType(type, id);
      BootstrapLogger.LOG.addAnnotatedTypeCalled(this.getReceiver(), type);
   }

   public AnnotatedTypeConfigurator addAnnotatedType(Class type, String id) {
      this.checkWithinObserverNotification();
      AnnotatedTypeConfiguratorImpl configurator = new AnnotatedTypeConfiguratorImpl(this.getBeanManager().createAnnotatedType(type));
      this.additionalAnnotatedTypes.add(new AbstractAnnotatedTypeRegisteringEvent.AnnotatedTypeRegistration(configurator, id));
      return configurator;
   }

   protected void storeSyntheticAnnotatedType(BeanDeployment deployment, AnnotatedType type, String id) {
      SlimAnnotatedType annotatedType = this.transformer.getUnbackedAnnotatedType(type, this.getBeanManager().getId(), id);
      Extension extension = this.getReceiver();
      SlimAnnotatedTypeContext annotatedTypeContext = SlimAnnotatedTypeContext.of(annotatedType, extension);
      ProcessAnnotatedTypeImpl event = this.events.fireProcessAnnotatedType(this.getBeanManager(), annotatedTypeContext);
      if (event == null) {
         deployment.getBeanDeployer().getEnvironment().addAnnotatedType(annotatedTypeContext);
         this.store.put(annotatedType);
      } else {
         if (event.isVeto()) {
            return;
         }

         SlimAnnotatedType annotatedType = event.getResultingAnnotatedType();
         deployment.getBeanDeployer().getEnvironment().addSyntheticAnnotatedType(annotatedType, extension);
         this.store.put(annotatedType);
      }

   }
}
