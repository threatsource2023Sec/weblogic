package org.jboss.weld.bootstrap;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.ProcessProducerField;
import javax.enterprise.inject.spi.ProcessProducerMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeStore;
import org.jboss.weld.bean.AbstractBean;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.bean.DecoratorImpl;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.bean.InterceptorImpl;
import org.jboss.weld.bean.ManagedBean;
import org.jboss.weld.bean.NewBean;
import org.jboss.weld.bean.NewManagedBean;
import org.jboss.weld.bean.ProducerField;
import org.jboss.weld.bean.ProducerMethod;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.attributes.BeanAttributesFactory;
import org.jboss.weld.bean.attributes.ExternalBeanAttributesFactory;
import org.jboss.weld.bean.builtin.AbstractBuiltInBean;
import org.jboss.weld.bean.builtin.ExtensionBean;
import org.jboss.weld.bean.builtin.ee.EEResourceProducerField;
import org.jboss.weld.bean.builtin.ee.StaticEEResourceProducerField;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.events.ContainerLifecycleEvents;
import org.jboss.weld.bootstrap.events.ProcessBeanAttributesImpl;
import org.jboss.weld.event.ObserverFactory;
import org.jboss.weld.event.ObserverMethodImpl;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.EjbSupport;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.BeanMethods;
import org.jboss.weld.util.Observers;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Reflections;

public class AbstractBeanDeployer {
   private final BeanManagerImpl manager;
   private final ServiceRegistry services;
   private final BeanDeployerEnvironment environment;
   protected final ContainerLifecycleEvents containerLifecycleEvents;
   protected final ClassTransformer classTransformer;
   protected final SlimAnnotatedTypeStore slimAnnotatedTypeStore;
   protected final SpecializationAndEnablementRegistry specializationAndEnablementRegistry;
   protected final EjbSupport ejbSupport;

   public AbstractBeanDeployer(BeanManagerImpl manager, ServiceRegistry services, BeanDeployerEnvironment environment) {
      this.manager = manager;
      this.services = services;
      this.environment = environment;
      this.containerLifecycleEvents = (ContainerLifecycleEvents)manager.getServices().get(ContainerLifecycleEvents.class);
      this.classTransformer = (ClassTransformer)services.get(ClassTransformer.class);
      this.slimAnnotatedTypeStore = (SlimAnnotatedTypeStore)services.get(SlimAnnotatedTypeStore.class);
      this.specializationAndEnablementRegistry = (SpecializationAndEnablementRegistry)services.get(SpecializationAndEnablementRegistry.class);
      this.ejbSupport = (EjbSupport)manager.getServices().getRequired(EjbSupport.class);
   }

   protected BeanManagerImpl getManager() {
      return this.manager;
   }

   protected AbstractBeanDeployer deploySpecialized() {
      Iterator var1 = this.getEnvironment().getDecorators().iterator();

      while(var1.hasNext()) {
         DecoratorImpl bean = (DecoratorImpl)var1.next();
         bean.initialize(this.getEnvironment());
         this.containerLifecycleEvents.fireProcessBean(this.getManager(), bean);
         this.manager.addDecorator(bean);
         BootstrapLogger.LOG.foundDecorator(bean);
      }

      var1 = this.getEnvironment().getInterceptors().iterator();

      while(var1.hasNext()) {
         InterceptorImpl bean = (InterceptorImpl)var1.next();
         bean.initialize(this.getEnvironment());
         this.containerLifecycleEvents.fireProcessBean(this.getManager(), bean);
         this.manager.addInterceptor(bean);
         BootstrapLogger.LOG.foundInterceptor(bean);
      }

      return this;
   }

   protected AbstractBeanDeployer initializeBeans() {
      Iterator var1 = this.getEnvironment().getBeans().iterator();

      while(var1.hasNext()) {
         RIBean bean = (RIBean)var1.next();
         bean.initialize(this.getEnvironment());
      }

      return this;
   }

   protected AbstractBeanDeployer fireProcessBeanEvents() {
      Iterator var1 = this.getEnvironment().getBeans().iterator();

      while(var1.hasNext()) {
         RIBean bean = (RIBean)var1.next();
         if (!(bean instanceof NewBean)) {
            this.containerLifecycleEvents.fireProcessBean(this.getManager(), bean);
         }
      }

      return this;
   }

   protected void processInjectionTargetEvents(Iterable beans) {
      if (this.containerLifecycleEvents.isProcessInjectionTargetObserved()) {
         Iterator var2 = beans.iterator();

         while(var2.hasNext()) {
            AbstractBean bean = (AbstractBean)var2.next();
            if (!(bean instanceof NewBean) && bean instanceof AbstractClassBean) {
               this.containerLifecycleEvents.fireProcessInjectionTarget(this.getManager(), (AbstractClassBean)bean);
            }
         }

      }
   }

   protected void processProducerEvents(Iterable beans) {
      if (this.containerLifecycleEvents.isProcessProducerObserved()) {
         Iterator var2 = beans.iterator();

         while(var2.hasNext()) {
            AbstractBean bean = (AbstractBean)var2.next();
            if (!(bean instanceof NewBean) && bean instanceof AbstractProducerBean) {
               this.containerLifecycleEvents.fireProcessProducer(this.getManager(), (AbstractProducerBean)Reflections.cast(bean));
            }
         }

      }
   }

   protected AbstractBeanDeployer deployBeans() {
      this.manager.addBeans(this.getEnvironment().getBeans());
      return this;
   }

   protected AbstractBeanDeployer initializeObserverMethods() {
      Iterator var1 = this.getEnvironment().getObservers().iterator();

      while(var1.hasNext()) {
         ObserverInitializationContext observerInitializer = (ObserverInitializationContext)var1.next();
         if (Observers.isObserverMethodEnabled(observerInitializer.getObserver(), this.manager)) {
            observerInitializer.initialize();
         }
      }

      return this;
   }

   protected AbstractBeanDeployer deployObserverMethods() {
      Iterator var1 = this.getEnvironment().getObservers().iterator();

      while(var1.hasNext()) {
         ObserverInitializationContext observerInitializer = (ObserverInitializationContext)var1.next();
         if (Observers.isObserverMethodEnabled(observerInitializer.getObserver(), this.manager)) {
            BootstrapLogger.LOG.foundObserverMethod(observerInitializer.getObserver());
            ObserverMethod processedObserver = this.containerLifecycleEvents.fireProcessObserverMethod(this.manager, observerInitializer.getObserver());
            if (processedObserver != null) {
               this.manager.addObserver(processedObserver);
            }
         }
      }

      return this;
   }

   protected void createObserversProducersDisposers(AbstractClassBean bean) {
      if (bean instanceof ManagedBean || bean instanceof SessionBean) {
         this.createDisposalMethods(bean, bean.getEnhancedAnnotated());
         this.createProducerMethods(bean, bean.getEnhancedAnnotated());
         this.createProducerFields(bean, bean.getEnhancedAnnotated());
         if (this.manager.isBeanEnabled(bean)) {
            this.createObserverMethods(bean, bean.getEnhancedAnnotated());
         }
      }

   }

   protected DisposalMethod resolveDisposalMethod(BeanAttributes attributes, AbstractClassBean declaringBean) {
      Set disposalBeans = this.environment.resolveDisposalBeans(attributes.getTypes(), attributes.getQualifiers(), declaringBean);
      if (disposalBeans.size() == 1) {
         return (DisposalMethod)disposalBeans.iterator().next();
      } else if (disposalBeans.size() > 1) {
         throw BeanLogger.LOG.multipleDisposalMethods(this, WeldCollections.toMultiRowString(disposalBeans));
      } else {
         return null;
      }
   }

   protected void createProducerMethods(AbstractClassBean declaringBean, EnhancedAnnotatedType type) {
      Iterator var3 = BeanMethods.filterMethods(type.getDeclaredEnhancedMethods(Produces.class)).iterator();

      while(var3.hasNext()) {
         EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var3.next();
         this.createProducerMethod(declaringBean, method);
      }

   }

   protected void createDisposalMethods(AbstractClassBean declaringBean, EnhancedAnnotatedType annotatedClass) {
      Iterator var3 = BeanMethods.filterMethods(annotatedClass.getDeclaredEnhancedMethodsWithAnnotatedParameters(Disposes.class)).iterator();

      while(var3.hasNext()) {
         EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var3.next();
         DisposalMethod disposalBean = DisposalMethod.of(this.manager, method, declaringBean);
         this.getEnvironment().addDisposesMethod(disposalBean);
      }

   }

   protected void createProducerMethod(AbstractClassBean declaringBean, EnhancedAnnotatedMethod annotatedMethod) {
      BeanAttributes attributes = BeanAttributesFactory.forBean(annotatedMethod, this.getManager());
      DisposalMethod disposalMethod = this.resolveDisposalMethod(attributes, declaringBean);
      ProducerMethod bean = ProducerMethod.of(attributes, annotatedMethod, declaringBean, disposalMethod, this.manager, this.services);
      this.containerLifecycleEvents.preloadProcessBeanAttributes(bean.getType());
      this.containerLifecycleEvents.preloadProcessBean(ProcessProducerMethod.class, annotatedMethod.getBaseType(), bean.getBeanClass());
      this.containerLifecycleEvents.preloadProcessProducer(bean.getBeanClass(), annotatedMethod.getBaseType());
      this.getEnvironment().addProducerMethod(bean);
   }

   protected void createProducerField(AbstractClassBean declaringBean, EnhancedAnnotatedField field) {
      BeanAttributes attributes = BeanAttributesFactory.forBean(field, this.getManager());
      DisposalMethod disposalMethod = this.resolveDisposalMethod(attributes, declaringBean);
      Object bean;
      if (EEResourceProducerField.isEEResourceProducerField(this.manager, field)) {
         if (field.isStatic()) {
            bean = StaticEEResourceProducerField.of(attributes, field, declaringBean, disposalMethod, this.manager, this.services);
         } else {
            bean = EEResourceProducerField.of(attributes, field, declaringBean, disposalMethod, this.manager, this.services);
         }
      } else {
         bean = ProducerField.of(attributes, field, declaringBean, disposalMethod, this.manager, this.services);
      }

      this.containerLifecycleEvents.preloadProcessBeanAttributes(((ProducerField)bean).getType());
      this.containerLifecycleEvents.preloadProcessBean(ProcessProducerField.class, field.getBaseType(), ((ProducerField)bean).getBeanClass());
      this.containerLifecycleEvents.preloadProcessProducer(((ProducerField)bean).getBeanClass(), field.getBaseType());
      this.getEnvironment().addProducerField((ProducerField)bean);
   }

   protected void createProducerFields(AbstractClassBean declaringBean, EnhancedAnnotatedType annotatedClass) {
      Iterator var3 = annotatedClass.getDeclaredEnhancedFields(Produces.class).iterator();

      while(var3.hasNext()) {
         EnhancedAnnotatedField field = (EnhancedAnnotatedField)var3.next();
         this.createProducerField(declaringBean, field);
      }

   }

   protected void createObserverMethods(AbstractClassBean declaringBean, EnhancedAnnotatedType annotatedClass) {
      Iterator var3 = BeanMethods.getObserverMethods(annotatedClass).iterator();

      EnhancedAnnotatedMethod method;
      while(var3.hasNext()) {
         method = (EnhancedAnnotatedMethod)var3.next();
         this.createObserverMethod(declaringBean, method, false);
      }

      var3 = BeanMethods.getAsyncObserverMethods(annotatedClass).iterator();

      while(var3.hasNext()) {
         method = (EnhancedAnnotatedMethod)var3.next();
         this.createObserverMethod(declaringBean, method, true);
      }

   }

   protected void createObserverMethod(AbstractClassBean declaringBean, EnhancedAnnotatedMethod method, boolean isAsync) {
      ObserverMethodImpl observer = ObserverFactory.create(method, declaringBean, this.manager, isAsync);
      ObserverInitializationContext observerInitializer = ObserverInitializationContext.of(observer, method);
      this.containerLifecycleEvents.preloadProcessObserverMethod(observer.getObservedType(), declaringBean.getBeanClass());
      this.getEnvironment().addObserverMethod(observerInitializer);
   }

   protected ManagedBean createManagedBean(EnhancedAnnotatedType weldClass) {
      BeanAttributes attributes = BeanAttributesFactory.forBean(weldClass, this.getManager());
      ManagedBean bean = ManagedBean.of(attributes, weldClass, this.manager);
      this.getEnvironment().addManagedBean(bean);
      return bean;
   }

   protected void createNewManagedBean(Class clazz, Type type) {
      EnhancedAnnotatedType enhancedType = this.classTransformer.getEnhancedAnnotatedType(clazz, type, this.manager.getId());
      this.slimAnnotatedTypeStore.put(enhancedType.slim());
      this.getEnvironment().addManagedBean(NewManagedBean.of(BeanAttributesFactory.forNewManagedBean(enhancedType, this.manager), enhancedType, this.manager));
   }

   protected void createDecorator(EnhancedAnnotatedType weldClass) {
      BeanAttributes attributes = BeanAttributesFactory.forBean(weldClass, this.getManager());
      DecoratorImpl bean = DecoratorImpl.of(attributes, weldClass, this.manager);
      this.getEnvironment().addDecorator(bean);
   }

   protected void createInterceptor(EnhancedAnnotatedType weldClass) {
      BeanAttributes attributes = BeanAttributesFactory.forBean(weldClass, this.getManager());
      InterceptorImpl bean = InterceptorImpl.of(attributes, weldClass, this.manager);
      this.getEnvironment().addInterceptor(bean);
   }

   public BeanDeployerEnvironment getEnvironment() {
      return this.environment;
   }

   public void addBuiltInBean(AbstractBuiltInBean bean) {
      this.getEnvironment().addBuiltInBean(bean);
   }

   protected void addExtension(ExtensionBean bean) {
      this.getEnvironment().addExtension(bean);
   }

   protected boolean fireProcessBeanAttributes(AbstractBean bean) {
      if (!this.specializationAndEnablementRegistry.isCandidateForLifecycleEvent(bean)) {
         return false;
      } else {
         ProcessBeanAttributesImpl event = this.containerLifecycleEvents.fireProcessBeanAttributes(this.getManager(), bean, bean.getAnnotated(), bean.getType());
         if (event == null) {
            return false;
         } else if (event.isVeto()) {
            return true;
         } else {
            if (event.isDirty()) {
               bean.setAttributes(ExternalBeanAttributesFactory.of(event.getBeanAttributesInternal(), this.manager));
               bean.checkSpecialization();
            }

            if (event.isIgnoreFinalMethods()) {
               bean.setIgnoreFinalMethods();
            }

            return false;
         }
      }
   }
}
