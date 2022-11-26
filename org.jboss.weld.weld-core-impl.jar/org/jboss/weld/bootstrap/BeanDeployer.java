package org.jboss.weld.bootstrap;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.decorator.Decorator;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessManagedBean;
import javax.interceptor.Interceptor;
import org.jboss.weld.Container;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeContext;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeStore;
import org.jboss.weld.bean.AbstractBean;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.enablement.GlobalEnablementBuilder;
import org.jboss.weld.bootstrap.events.ProcessAnnotatedTypeImpl;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.spi.ClassFileServices;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.AnnotationApiAbstraction;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.collections.SetMultimap;
import org.jboss.weld.util.reflection.Reflections;

public class BeanDeployer extends AbstractBeanDeployer {
   private final ResourceLoader resourceLoader;
   private final SlimAnnotatedTypeStore annotatedTypeStore;
   private final GlobalEnablementBuilder globalEnablementBuilder;
   private final AnnotationApiAbstraction annotationApi;
   private final ClassFileServices classFileServices;

   public BeanDeployer(BeanManagerImpl manager, ServiceRegistry services) {
      this(manager, services, BeanDeployerEnvironmentFactory.newEnvironment(manager));
   }

   public BeanDeployer(BeanManagerImpl manager, ServiceRegistry services, BeanDeployerEnvironment environment) {
      super(manager, services, environment);
      this.resourceLoader = (ResourceLoader)manager.getServices().get(ResourceLoader.class);
      this.annotatedTypeStore = (SlimAnnotatedTypeStore)manager.getServices().get(SlimAnnotatedTypeStore.class);
      this.globalEnablementBuilder = (GlobalEnablementBuilder)manager.getServices().get(GlobalEnablementBuilder.class);
      this.annotationApi = (AnnotationApiAbstraction)manager.getServices().get(AnnotationApiAbstraction.class);
      this.classFileServices = (ClassFileServices)manager.getServices().get(ClassFileServices.class);
   }

   public BeanDeployer addClass(String className, AnnotatedTypeLoader loader) {
      this.addIfNotNull(loader.loadAnnotatedType(className, this.getManager().getId()));
      return this;
   }

   public BeanDeployer addClass(Class clazz, AnnotatedTypeLoader loader) {
      this.addIfNotNull(loader.loadAnnotatedType(clazz, this.getManager().getId()));
      return this;
   }

   private SlimAnnotatedTypeContext addIfNotNull(SlimAnnotatedTypeContext ctx) {
      if (ctx != null) {
         this.getEnvironment().addAnnotatedType(ctx);
      }

      return ctx;
   }

   private void processPriority(AnnotatedType type) {
      Object priority = type.getAnnotation(this.annotationApi.PRIORITY_ANNOTATION_CLASS);
      if (priority != null) {
         Integer value = this.annotationApi.getPriority(priority);
         if (value != null) {
            if (type.isAnnotationPresent(Interceptor.class)) {
               this.globalEnablementBuilder.addInterceptor(type.getJavaClass(), value);
            } else if (type.isAnnotationPresent(Decorator.class)) {
               this.globalEnablementBuilder.addDecorator(type.getJavaClass(), value);
            } else {
               this.globalEnablementBuilder.addAlternative(type.getJavaClass(), value);
            }
         }
      }

   }

   public BeanDeployer addSyntheticClass(AnnotatedType source, Extension extension, String suffix) {
      if (suffix == null) {
         suffix = AnnotatedTypes.createTypeId(source);
      }

      this.getEnvironment().addSyntheticAnnotatedType(this.classTransformer.getUnbackedAnnotatedType(source, this.getManager().getId(), suffix), extension);
      return this;
   }

   public BeanDeployer addClasses(Iterable classes) {
      AnnotatedTypeLoader loader = this.createAnnotatedTypeLoader();
      Iterator var3 = classes.iterator();

      while(var3.hasNext()) {
         String className = (String)var3.next();
         this.addClass(className, loader);
      }

      return this;
   }

   public BeanDeployer addLoadedClasses(Iterable classes) {
      AnnotatedTypeLoader loader = this.createAnnotatedTypeLoader();
      Iterator var3 = classes.iterator();

      while(var3.hasNext()) {
         Class clazz = (Class)var3.next();
         this.addClass(clazz, loader);
      }

      return this;
   }

   protected AnnotatedTypeLoader createAnnotatedTypeLoader() {
      if (this.classFileServices != null) {
         FastProcessAnnotatedTypeResolver resolver = (FastProcessAnnotatedTypeResolver)Container.instance(this.getManager()).deploymentManager().getServices().get(FastProcessAnnotatedTypeResolver.class);
         if (resolver != null) {
            return new FastAnnotatedTypeLoader(this.getManager(), this.classTransformer, this.classFileServices, this.containerLifecycleEvents, resolver);
         }
      }

      return new AnnotatedTypeLoader(this.getManager(), this.classTransformer, this.containerLifecycleEvents);
   }

   public void processAnnotatedTypes() {
      Set classesToBeAdded = new HashSet();
      Set classesToBeRemoved = new HashSet();
      Iterator var3 = this.getEnvironment().getAnnotatedTypes().iterator();

      while(var3.hasNext()) {
         SlimAnnotatedTypeContext annotatedTypeContext = (SlimAnnotatedTypeContext)var3.next();
         SlimAnnotatedType annotatedType = annotatedTypeContext.getAnnotatedType();
         ProcessAnnotatedTypeImpl event = this.containerLifecycleEvents.fireProcessAnnotatedType(this.getManager(), annotatedTypeContext);
         if (event != null) {
            if (event.isVeto()) {
               this.getEnvironment().vetoJavaClass(annotatedType.getJavaClass());
               classesToBeRemoved.add(annotatedTypeContext);
            } else {
               boolean dirty = event.isDirty();
               if (dirty) {
                  classesToBeRemoved.add(annotatedTypeContext);
                  classesToBeAdded.add(SlimAnnotatedTypeContext.of(event.getResultingAnnotatedType(), annotatedTypeContext.getExtension()));
               }

               this.processPriority(event.getResultingAnnotatedType());
            }
         } else {
            this.processPriority(annotatedType);
         }
      }

      this.getEnvironment().removeAnnotatedTypes(classesToBeRemoved);
      this.getEnvironment().addAnnotatedTypes(classesToBeAdded);
   }

   public void registerAnnotatedTypes() {
      Iterator var1 = this.getEnvironment().getAnnotatedTypes().iterator();

      while(var1.hasNext()) {
         SlimAnnotatedTypeContext ctx = (SlimAnnotatedTypeContext)var1.next();
         this.annotatedTypeStore.put(ctx.getAnnotatedType());
      }

   }

   public void createClassBeans() {
      SetMultimap otherWeldClasses = SetMultimap.newSetMultimap();
      Iterator var2 = this.getEnvironment().getAnnotatedTypes().iterator();

      while(var2.hasNext()) {
         SlimAnnotatedTypeContext ctx = (SlimAnnotatedTypeContext)var2.next();
         this.createClassBean(ctx.getAnnotatedType(), otherWeldClasses);
      }

      this.ejbSupport.createSessionBeans(this.getEnvironment(), otherWeldClasses, this.getManager());
   }

   protected void createClassBean(SlimAnnotatedType annotatedType, SetMultimap otherWeldClasses) {
      boolean managedBeanOrDecorator = !this.ejbSupport.isEjb(annotatedType.getJavaClass()) && Beans.isTypeManagedBeanOrDecoratorOrInterceptor(annotatedType);
      if (managedBeanOrDecorator) {
         this.containerLifecycleEvents.preloadProcessInjectionTarget(annotatedType.getJavaClass());
         this.containerLifecycleEvents.preloadProcessBeanAttributes(annotatedType.getJavaClass());
         EnhancedAnnotatedType weldClass = this.classTransformer.getEnhancedAnnotatedType(annotatedType);
         if (weldClass.isAnnotationPresent(Decorator.class)) {
            this.containerLifecycleEvents.preloadProcessBean(ProcessBean.class, annotatedType.getJavaClass());
            this.validateDecorator(weldClass);
            this.createDecorator(weldClass);
         } else if (weldClass.isAnnotationPresent(Interceptor.class)) {
            this.containerLifecycleEvents.preloadProcessBean(ProcessBean.class, annotatedType.getJavaClass());
            this.validateInterceptor(weldClass);
            this.createInterceptor(weldClass);
         } else if (!weldClass.isAbstract()) {
            this.containerLifecycleEvents.preloadProcessBean(ProcessManagedBean.class, annotatedType.getJavaClass());
            this.createManagedBean(weldClass);
         }
      } else {
         if (Beans.isDecoratorDeclaringInAppropriateConstructor((AnnotatedType)annotatedType)) {
            BootstrapLogger.LOG.decoratorWithNonCdiConstructor(annotatedType.getJavaClass().getName());
         }

         Class scopeClass = Beans.getBeanDefiningAnnotationScope(annotatedType);
         if (scopeClass != null && !Beans.hasSimpleCdiConstructor(annotatedType)) {
            BootstrapLogger.LOG.annotatedTypeNotRegisteredAsBeanDueToMissingAppropriateConstructor(annotatedType.getJavaClass().getName(), scopeClass.getSimpleName());
         }

         otherWeldClasses.put(annotatedType.getJavaClass(), annotatedType);
      }

   }

   public void processClassBeanAttributes() {
      this.preInitializeBeans(this.getEnvironment().getClassBeans());
      this.preInitializeBeans(this.getEnvironment().getDecorators());
      this.preInitializeBeans(this.getEnvironment().getInterceptors());
      this.processBeans(this.getEnvironment().getClassBeans());
      this.processBeans(this.getEnvironment().getDecorators());
      this.processBeans(this.getEnvironment().getInterceptors());
      this.searchForNewBeanDeclarations(this.getEnvironment().getClassBeans());
      this.searchForNewBeanDeclarations(this.getEnvironment().getDecorators());
      this.searchForNewBeanDeclarations(this.getEnvironment().getInterceptors());
   }

   private void preInitializeBeans(Iterable beans) {
      Iterator var2 = beans.iterator();

      while(var2.hasNext()) {
         AbstractBean bean = (AbstractBean)var2.next();
         bean.preInitialize();
      }

   }

   protected void processBeans(Iterable beans) {
      this.processInjectionTargetEvents(beans);
      this.processProducerEvents(beans);
      this.processBeanAttributes(beans);
   }

   protected void processBeanAttributes(Iterable beans) {
      if (this.containerLifecycleEvents.isProcessBeanAttributesObserved()) {
         if (beans.iterator().hasNext()) {
            Collection vetoedBeans = new HashSet();
            Collection previouslySpecializedBeans = new HashSet();
            Iterator var4 = beans.iterator();

            AbstractBean bean;
            while(var4.hasNext()) {
               bean = (AbstractBean)var4.next();
               boolean vetoed = this.fireProcessBeanAttributes(bean);
               if (vetoed) {
                  vetoedBeans.add(bean);
               }
            }

            for(var4 = vetoedBeans.iterator(); var4.hasNext(); this.getEnvironment().vetoBean(bean)) {
               bean = (AbstractBean)var4.next();
               if (bean.isSpecializing()) {
                  previouslySpecializedBeans.addAll(this.specializationAndEnablementRegistry.resolveSpecializedBeans(bean));
                  this.specializationAndEnablementRegistry.vetoSpecializingBean(bean);
               }
            }

            this.processBeans(previouslySpecializedBeans);
         }
      }
   }

   protected void searchForNewBeanDeclarations(Iterable beans) {
      Iterator var2 = beans.iterator();

      while(var2.hasNext()) {
         AbstractBean bean = (AbstractBean)var2.next();
         this.getEnvironment().addNewBeansFromInjectionPoints(bean);
      }

   }

   public void createProducersAndObservers() {
      Iterator var1 = this.getEnvironment().getClassBeans().iterator();

      while(var1.hasNext()) {
         AbstractClassBean bean = (AbstractClassBean)var1.next();
         this.createObserversProducersDisposers(bean);
      }

   }

   public void processProducerAttributes() {
      this.processBeans(this.getEnvironment().getProducerFields());
      this.searchForNewBeanDeclarations(this.getEnvironment().getProducerFields());
      this.preInitializeBeans(this.getEnvironment().getProducerMethodBeans());
      this.processBeans(this.getEnvironment().getProducerMethodBeans());
      this.searchForNewBeanDeclarations(this.getEnvironment().getProducerMethodBeans());
   }

   public void createNewBeans() {
      Iterator var1 = this.getEnvironment().getNewBeanTypes().iterator();

      while(var1.hasNext()) {
         Type type = (Type)var1.next();
         Class clazz = Reflections.getRawType(type);
         if (!this.ejbSupport.isEjb(clazz)) {
            this.createNewManagedBean(clazz, type);
         }
      }

      this.ejbSupport.createNewSessionBeans(this.getEnvironment(), this.getManager());
   }

   public void deploy() {
      this.initializeBeans();
      this.fireProcessBeanEvents();
      this.deployBeans();
      this.initializeObserverMethods();
      this.deployObserverMethods();
   }

   protected void validateInterceptor(EnhancedAnnotatedType weldClass) {
      if (weldClass.isAnnotationPresent(Decorator.class)) {
         throw BootstrapLogger.LOG.beanIsBothInterceptorAndDecorator(weldClass.getName());
      }
   }

   protected void validateDecorator(EnhancedAnnotatedType weldClass) {
      if (weldClass.isAnnotationPresent(Interceptor.class)) {
         throw BootstrapLogger.LOG.beanIsBothInterceptorAndDecorator(weldClass.getName());
      }
   }

   public void doAfterBeanDiscovery(List beanList) {
      Iterator var2 = beanList.iterator();

      while(var2.hasNext()) {
         Bean bean = (Bean)var2.next();
         if (bean instanceof RIBean) {
            ((RIBean)bean).initializeAfterBeanDiscovery();
         }
      }

   }

   public void registerCdiInterceptorsForMessageDrivenBeans() {
      this.ejbSupport.registerCdiInterceptorsForMessageDrivenBeans(this.getEnvironment(), this.getManager());
   }

   public ResourceLoader getResourceLoader() {
      return this.resourceLoader;
   }

   public void cleanup() {
      this.getEnvironment().cleanup();
   }
}
