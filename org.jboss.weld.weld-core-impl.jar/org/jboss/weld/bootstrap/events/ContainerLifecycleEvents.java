package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessBeanAttributes;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessObserverMethod;
import javax.enterprise.inject.spi.ProcessProducer;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeContext;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.bean.ManagedBean;
import org.jboss.weld.bean.ProducerField;
import org.jboss.weld.bean.ProducerMethod;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bootstrap.api.helpers.AbstractBootstrapService;
import org.jboss.weld.event.ContainerLifecycleEventObserverMethod;
import org.jboss.weld.event.ExtensionObserverMethodImpl;
import org.jboss.weld.event.ObserverMethodImpl;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.injection.attributes.FieldInjectionPointAttributes;
import org.jboss.weld.injection.attributes.ParameterInjectionPointAttributes;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resolution.Resolvable;
import org.jboss.weld.util.reflection.Reflections;

public class ContainerLifecycleEvents extends AbstractBootstrapService {
   private boolean everythingObserved;
   private boolean processAnnotatedTypeObserved;
   private boolean processBeanObserved;
   private boolean processBeanAttributesObserved;
   private boolean processInjectionPointObserved;
   private boolean processInjectionTargetObserved;
   private boolean processProducerObserved;
   private boolean processObserverMethodObserved;
   private final RequiredAnnotationDiscovery discovery;
   private final ContainerLifecycleEventPreloader preloader;

   public ContainerLifecycleEvents(ContainerLifecycleEventPreloader preloader, RequiredAnnotationDiscovery discovery) {
      this.preloader = preloader;
      this.discovery = discovery;
   }

   public void processObserverMethod(ObserverMethod observer) {
      if (observer instanceof ContainerLifecycleEventObserverMethod) {
         this.processObserverMethodType(observer.getObservedType());
      }

   }

   protected void processObserverMethodType(Type observedType) {
      if (!this.everythingObserved) {
         Class rawType = Reflections.getRawType(observedType);
         if (Object.class.equals(rawType)) {
            this.everythingObserved = true;
            this.processAnnotatedTypeObserved = true;
            this.processBeanObserved = true;
            this.processBeanAttributesObserved = true;
            this.processInjectionPointObserved = true;
            this.processInjectionTargetObserved = true;
            this.processProducerObserved = true;
            this.processObserverMethodObserved = true;
         } else if (!this.processAnnotatedTypeObserved && ProcessAnnotatedType.class.isAssignableFrom(rawType)) {
            this.processAnnotatedTypeObserved = true;
         } else if (!this.processBeanObserved && ProcessBean.class.isAssignableFrom(rawType)) {
            this.processBeanObserved = true;
         } else if (!this.processBeanAttributesObserved && ProcessBeanAttributes.class.isAssignableFrom(rawType)) {
            this.processBeanAttributesObserved = true;
         } else if (!this.processObserverMethodObserved && ProcessObserverMethod.class.isAssignableFrom(rawType)) {
            this.processObserverMethodObserved = true;
         } else if (!this.processProducerObserved && ProcessProducer.class.equals(rawType)) {
            this.processProducerObserved = true;
         } else if (!this.processInjectionTargetObserved && ProcessInjectionTarget.class.equals(rawType)) {
            this.processInjectionTargetObserved = true;
         } else if (!this.processInjectionPointObserved && ProcessInjectionPoint.class.equals(rawType)) {
            this.processInjectionPointObserved = true;
         }

      }
   }

   public boolean isProcessAnnotatedTypeObserved() {
      return this.processAnnotatedTypeObserved;
   }

   public boolean isProcessBeanObserved() {
      return this.processBeanObserved;
   }

   public boolean isProcessBeanAttributesObserved() {
      return this.processBeanAttributesObserved;
   }

   public boolean isProcessObserverMethodObserved() {
      return this.processObserverMethodObserved;
   }

   public boolean isProcessProducerObserved() {
      return this.processProducerObserved;
   }

   public boolean isProcessInjectionTargetObserved() {
      return this.processInjectionTargetObserved;
   }

   public boolean isProcessInjectionPointObserved() {
      return this.processInjectionPointObserved;
   }

   public ProcessAnnotatedTypeImpl fireProcessAnnotatedType(BeanManagerImpl beanManager, SlimAnnotatedTypeContext annotatedTypeContext) {
      if (!this.isProcessAnnotatedTypeObserved()) {
         return null;
      } else {
         Set observers = annotatedTypeContext.getResolvedProcessAnnotatedTypeObservers();
         SlimAnnotatedType annotatedType = annotatedTypeContext.getAnnotatedType();
         if (observers != null && observers.isEmpty()) {
            BootstrapLogger.LOG.patSkipped(annotatedType);
            return null;
         } else {
            ProcessAnnotatedTypeImpl event = null;
            if (annotatedTypeContext.getExtension() == null) {
               event = new ProcessAnnotatedTypeImpl(beanManager, annotatedType);
            } else {
               event = new ProcessSyntheticAnnotatedTypeImpl(beanManager, annotatedTypeContext);
            }

            if (observers == null) {
               BootstrapLogger.LOG.patDefaultResolver(annotatedType);
               this.fireProcessAnnotatedType((ProcessAnnotatedTypeImpl)event, (BeanManagerImpl)beanManager);
            } else {
               BootstrapLogger.LOG.patFastResolver(annotatedType);
               this.fireProcessAnnotatedType((ProcessAnnotatedTypeImpl)event, annotatedTypeContext.getResolvedProcessAnnotatedTypeObservers(), beanManager);
            }

            return (ProcessAnnotatedTypeImpl)event;
         }
      }
   }

   private void fireProcessAnnotatedType(ProcessAnnotatedTypeImpl event, BeanManagerImpl beanManager) {
      Resolvable resolvable = ProcessAnnotatedTypeEventResolvable.of(event, this.discovery);

      try {
         beanManager.getGlobalLenientObserverNotifier().fireEvent(event, resolvable);
      } catch (Exception var5) {
         throw new DefinitionException(var5);
      }
   }

   private void fireProcessAnnotatedType(ProcessAnnotatedTypeImpl event, Set observers, BeanManagerImpl beanManager) {
      List errors = new LinkedList();
      Iterator var5 = observers.iterator();

      while(var5.hasNext()) {
         ContainerLifecycleEventObserverMethod observer = (ContainerLifecycleEventObserverMethod)var5.next();
         if (this.checkScopeInheritanceRules(event.getOriginalAnnotatedType(), observer, beanManager)) {
            try {
               observer.notify(event);
            } catch (Throwable var8) {
               errors.add(var8);
            }
         }
      }

      if (!errors.isEmpty()) {
         throw new DefinitionException(errors);
      }
   }

   private boolean checkScopeInheritanceRules(SlimAnnotatedType type, ContainerLifecycleEventObserverMethod observer, BeanManagerImpl beanManager) {
      Collection scopes;
      if (observer instanceof ExtensionObserverMethodImpl) {
         ExtensionObserverMethodImpl extensionObserver = (ExtensionObserverMethodImpl)observer;
         scopes = extensionObserver.getRequiredScopeAnnotations();
      } else {
         scopes = (Collection)observer.getRequiredAnnotations().stream().filter((a) -> {
            return beanManager.isScope(a);
         }).collect(Collectors.toSet());
      }

      if (!scopes.isEmpty() && scopes.size() == observer.getRequiredAnnotations().size()) {
         Iterator var7 = scopes.iterator();

         Class annotation;
         do {
            if (!var7.hasNext()) {
               return false;
            }

            annotation = (Class)var7.next();
         } while(!type.isAnnotationPresent(annotation));

         return true;
      } else {
         return true;
      }
   }

   public void fireProcessBean(BeanManagerImpl beanManager, Bean bean) {
      this.fireProcessBean(beanManager, bean, (Extension)null);
   }

   public void fireProcessBean(BeanManagerImpl beanManager, Bean bean, Extension extension) {
      if (this.isProcessBeanObserved()) {
         if (bean instanceof ManagedBean) {
            ProcessManagedBeanImpl.fire(beanManager, (ManagedBean)bean);
         } else if (bean instanceof SessionBean) {
            ProcessSessionBeanImpl.fire(beanManager, (SessionBean)Reflections.cast(bean));
         } else if (bean instanceof ProducerField) {
            ProcessProducerFieldImpl.fire(beanManager, (ProducerField)bean);
         } else if (bean instanceof ProducerMethod) {
            ProcessProducerMethodImpl.fire(beanManager, (ProducerMethod)bean);
         } else if (extension != null) {
            ProcessSynthethicBeanImpl.fire(beanManager, bean, extension);
         } else {
            ProcessBeanImpl.fire(beanManager, bean);
         }
      }

   }

   public ProcessBeanAttributesImpl fireProcessBeanAttributes(BeanManagerImpl beanManager, BeanAttributes attributes, Annotated annotated, Type type) {
      return this.isProcessBeanAttributesObserved() ? ProcessBeanAttributesImpl.fire(beanManager, attributes, annotated, type) : null;
   }

   public void fireProcessInjectionTarget(BeanManagerImpl beanManager, AbstractClassBean bean) {
      if (this.isProcessInjectionTargetObserved()) {
         AbstractProcessInjectionTarget.fire(beanManager, bean);
      }

   }

   public InjectionTarget fireProcessInjectionTarget(BeanManagerImpl beanManager, AnnotatedType annotatedType, InjectionTarget injectionTarget) {
      return this.isProcessInjectionTargetObserved() ? AbstractProcessInjectionTarget.fire(beanManager, annotatedType, injectionTarget) : injectionTarget;
   }

   public FieldInjectionPointAttributes fireProcessInjectionPoint(FieldInjectionPointAttributes attributes, Class declaringComponentClass, BeanManagerImpl manager) {
      return this.isProcessInjectionPointObserved() ? ProcessInjectionPointImpl.fire(attributes, declaringComponentClass, manager) : attributes;
   }

   public ParameterInjectionPointAttributes fireProcessInjectionPoint(ParameterInjectionPointAttributes injectionPointAttributes, Class declaringComponentClass, BeanManagerImpl manager) {
      return this.isProcessInjectionPointObserved() ? ProcessInjectionPointImpl.fire(injectionPointAttributes, declaringComponentClass, manager) : injectionPointAttributes;
   }

   public ObserverMethod fireProcessObserverMethod(BeanManagerImpl beanManager, ObserverMethodImpl observer) {
      return this.fireProcessObserverMethod(beanManager, observer.getMethod().getAnnotated(), observer, (Extension)null);
   }

   public ObserverMethod fireProcessObserverMethod(BeanManagerImpl beanManager, ObserverMethod observer, Extension extension) {
      return this.fireProcessObserverMethod(beanManager, (AnnotatedMethod)null, observer, extension);
   }

   private ObserverMethod fireProcessObserverMethod(BeanManagerImpl beanManager, AnnotatedMethod beanMethod, ObserverMethod observerMethod, Extension extension) {
      if (this.isProcessObserverMethodObserved()) {
         return extension != null ? ProcessSyntheticObserverMethodImpl.fire(beanManager, beanMethod, observerMethod, extension) : ProcessObserverMethodImpl.fire(beanManager, beanMethod, observerMethod);
      } else {
         return observerMethod;
      }
   }

   public void fireProcessProducer(BeanManagerImpl beanManager, AbstractProducerBean bean) {
      if (this.isProcessProducerObserved()) {
         ProcessProducerImpl.fire(beanManager, bean);
      }

   }

   public void preloadProcessAnnotatedType(Class type) {
      if (this.preloader != null && this.isProcessAnnotatedTypeObserved()) {
         this.preloader.preloadContainerLifecycleEvent(ProcessAnnotatedType.class, type);
      }

   }

   public void preloadProcessBean(Class eventRawType, Type... typeParameters) {
      if (this.preloader != null && this.isProcessBeanObserved()) {
         this.preloader.preloadContainerLifecycleEvent(ProcessAnnotatedType.class, typeParameters);
      }

   }

   public void preloadProcessBeanAttributes(Type type) {
      if (this.preloader != null && this.isProcessBeanAttributesObserved()) {
         this.preloader.preloadContainerLifecycleEvent(ProcessBeanAttributes.class, type);
      }

   }

   public void preloadProcessInjectionTarget(Class type) {
      if (this.preloader != null && this.isProcessInjectionTargetObserved()) {
         this.preloader.preloadContainerLifecycleEvent(ProcessInjectionTarget.class, type);
      }

   }

   public void preloadProcessObserverMethod(Type... typeParameters) {
      if (this.preloader != null && this.isProcessObserverMethodObserved()) {
         this.preloader.preloadContainerLifecycleEvent(ProcessObserverMethod.class, typeParameters);
      }

   }

   public void preloadProcessProducer(Type... typeParameters) {
      if (this.preloader != null && this.isProcessProducerObserved()) {
         this.preloader.preloadContainerLifecycleEvent(ProcessProducer.class, typeParameters);
      }

   }

   public void cleanupAfterBoot() {
      if (this.preloader != null) {
         this.preloader.shutdown();
      }

   }

   public boolean isPreloaderEnabled() {
      return this.preloader != null;
   }
}
