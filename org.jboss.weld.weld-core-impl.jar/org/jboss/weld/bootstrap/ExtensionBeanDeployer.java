package org.jboss.weld.bootstrap;

import java.lang.reflect.Member;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ObserverMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bean.builtin.ExtensionBean;
import org.jboss.weld.bootstrap.events.ContainerLifecycleEvents;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.event.ObserverFactory;
import org.jboss.weld.event.ObserverMethodImpl;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.logging.EventLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.resources.spi.ResourceLoadingException;
import org.jboss.weld.util.BeanMethods;
import org.jboss.weld.util.DeploymentStructures;
import org.jboss.weld.util.Observers;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class ExtensionBeanDeployer {
   private final BeanManagerImpl beanManager;
   private final Set extensions;
   private final Deployment deployment;
   private final BeanDeploymentArchiveMapping bdaMapping;
   private final Collection contexts;
   private final ContainerLifecycleEvents containerLifecycleEventObservers;
   private final MissingDependenciesRegistry missingDependenciesRegistry;

   public ExtensionBeanDeployer(BeanManagerImpl manager, Deployment deployment, BeanDeploymentArchiveMapping bdaMapping, Collection contexts) {
      this.beanManager = manager;
      this.extensions = new HashSet();
      this.deployment = deployment;
      this.bdaMapping = bdaMapping;
      this.contexts = contexts;
      this.containerLifecycleEventObservers = (ContainerLifecycleEvents)this.beanManager.getServices().get(ContainerLifecycleEvents.class);
      this.missingDependenciesRegistry = (MissingDependenciesRegistry)this.beanManager.getServices().get(MissingDependenciesRegistry.class);
   }

   public ExtensionBeanDeployer deployBeans() {
      ClassTransformer classTransformer = (ClassTransformer)this.beanManager.getServices().get(ClassTransformer.class);
      Iterator var2 = this.extensions.iterator();

      while(var2.hasNext()) {
         Metadata extension = (Metadata)var2.next();
         this.deployBean(extension, classTransformer);
      }

      return this;
   }

   private void deployBean(Metadata extension, ClassTransformer classTransformer) {
      BeanDeployment beanDeployment = DeploymentStructures.getOrCreateBeanDeployment(this.deployment, this.beanManager, this.bdaMapping, this.contexts, ((Extension)extension.getValue()).getClass());
      if (extension.getValue() instanceof SyntheticExtension) {
         SyntheticExtension synthetic = (SyntheticExtension)extension.getValue();
         synthetic.initialize(beanDeployment.getBeanManager());
         Iterator var10 = synthetic.getObservers().iterator();

         while(var10.hasNext()) {
            ObserverMethod observer = (ObserverMethod)var10.next();
            beanDeployment.getBeanManager().addObserver(observer);
            this.containerLifecycleEventObservers.processObserverMethod(observer);
         }

      } else {
         EnhancedAnnotatedType enchancedAnnotatedType = this.getEnhancedAnnotatedType(classTransformer, extension, beanDeployment);
         if (enchancedAnnotatedType != null) {
            ExtensionBean bean = new ExtensionBean(beanDeployment.getBeanManager(), enchancedAnnotatedType, extension);
            Set observerMethodInitializers = new HashSet();
            this.createObserverMethods(bean, beanDeployment.getBeanManager(), enchancedAnnotatedType, observerMethodInitializers);
            beanDeployment.getBeanManager().addBean(bean);
            beanDeployment.getBeanDeployer().addExtension(bean);
            Iterator var7 = observerMethodInitializers.iterator();

            while(var7.hasNext()) {
               ObserverInitializationContext observerMethodInitializer = (ObserverInitializationContext)var7.next();
               observerMethodInitializer.initialize();
               beanDeployment.getBeanManager().addObserver(observerMethodInitializer.getObserver());
               this.containerLifecycleEventObservers.processObserverMethod(observerMethodInitializer.getObserver());
            }

            BootstrapLogger.LOG.extensionBeanDeployed(bean);
         }

      }
   }

   private EnhancedAnnotatedType getEnhancedAnnotatedType(ClassTransformer classTransformer, Metadata extension, BeanDeployment beanDeployment) {
      Class clazz = ((Extension)extension.getValue()).getClass();

      try {
         return (EnhancedAnnotatedType)Reflections.cast(classTransformer.getEnhancedAnnotatedType(clazz, beanDeployment.getBeanDeploymentArchive().getId()));
      } catch (ResourceLoadingException var7) {
         String missingDependency = Formats.getNameOfMissingClassLoaderDependency(var7);
         BootstrapLogger.LOG.ignoringExtensionClassDueToLoadingError(clazz.getName(), missingDependency);
         BootstrapLogger.LOG.catchingDebug(var7);
         this.missingDependenciesRegistry.registerClassWithMissingDependency(clazz.getName(), missingDependency);
         return null;
      }
   }

   public void addExtensions(Iterable extensions) {
      Iterator var2 = extensions.iterator();

      while(var2.hasNext()) {
         Metadata extension = (Metadata)var2.next();
         this.addExtension(extension);
      }

   }

   public void addExtension(Metadata extension) {
      this.extensions.add(extension);
   }

   protected void createObserverMethods(RIBean declaringBean, BeanManagerImpl beanManager, EnhancedAnnotatedType annotatedClass, Set observerMethodInitializers) {
      Iterator var5 = BeanMethods.getObserverMethods(annotatedClass).iterator();

      EnhancedAnnotatedMethod method;
      while(var5.hasNext()) {
         method = (EnhancedAnnotatedMethod)var5.next();
         this.createObserverMethod(declaringBean, beanManager, method, observerMethodInitializers, false);
      }

      var5 = BeanMethods.getAsyncObserverMethods(annotatedClass).iterator();

      while(var5.hasNext()) {
         method = (EnhancedAnnotatedMethod)var5.next();
         this.createObserverMethod(declaringBean, beanManager, method, observerMethodInitializers, true);
      }

   }

   protected void createObserverMethod(RIBean declaringBean, BeanManagerImpl beanManager, EnhancedAnnotatedMethod method, Set observerMethodInitializers, boolean isAsync) {
      ObserverMethodImpl observer = ObserverFactory.create(method, declaringBean, beanManager, isAsync);
      ObserverInitializationContext observerMethodInitializer = ObserverInitializationContext.of(observer, method);
      if (Observers.isContainerLifecycleObserverMethod(observer)) {
         if (isAsync) {
            throw EventLogger.LOG.asyncContainerLifecycleEventObserver(observer, Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
         }

         if (method.isStatic()) {
            throw EventLogger.LOG.staticContainerLifecycleEventObserver(observer, Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
         }
      }

      observerMethodInitializers.add(observerMethodInitializer);
   }
}
