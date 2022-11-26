package org.jboss.weld.bootstrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.NormalScope;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.Initialized.Literal;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Stereotype;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.Container;
import org.jboss.weld.ContainerState;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeStore;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeStoreImpl;
import org.jboss.weld.bean.DecoratorImpl;
import org.jboss.weld.bean.InterceptorImpl;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bean.builtin.AbstractBuiltInBean;
import org.jboss.weld.bean.builtin.BeanManagerBean;
import org.jboss.weld.bean.builtin.BeanManagerImplBean;
import org.jboss.weld.bean.builtin.ContextBean;
import org.jboss.weld.bean.proxy.ProtectionDomainCache;
import org.jboss.weld.bean.proxy.ProxyInstantiator;
import org.jboss.weld.bean.proxy.util.SimpleProxyServices;
import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.TypeDiscoveryConfiguration;
import org.jboss.weld.bootstrap.api.helpers.ServiceRegistries;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.enablement.GlobalEnablementBuilder;
import org.jboss.weld.bootstrap.events.AfterBeanDiscoveryImpl;
import org.jboss.weld.bootstrap.events.AfterDeploymentValidationImpl;
import org.jboss.weld.bootstrap.events.AfterTypeDiscoveryImpl;
import org.jboss.weld.bootstrap.events.BeforeBeanDiscoveryImpl;
import org.jboss.weld.bootstrap.events.ContainerLifecycleEventPreloader;
import org.jboss.weld.bootstrap.events.ContainerLifecycleEvents;
import org.jboss.weld.bootstrap.events.RequiredAnnotationDiscovery;
import org.jboss.weld.bootstrap.spi.CDI11Deployment;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.bootstrap.spi.helpers.MetadataImpl;
import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.context.ApplicationContext;
import org.jboss.weld.context.DependentContext;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.SingletonContext;
import org.jboss.weld.context.bound.BoundConversationContext;
import org.jboss.weld.context.bound.BoundLiteral;
import org.jboss.weld.context.bound.BoundRequestContext;
import org.jboss.weld.context.bound.BoundSessionContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.contexts.bound.BoundConversationContextImpl;
import org.jboss.weld.contexts.bound.BoundRequestContextImpl;
import org.jboss.weld.contexts.bound.BoundSessionContextImpl;
import org.jboss.weld.contexts.unbound.ApplicationContextImpl;
import org.jboss.weld.contexts.unbound.DependentContextImpl;
import org.jboss.weld.contexts.unbound.RequestContextImpl;
import org.jboss.weld.contexts.unbound.SingletonContextImpl;
import org.jboss.weld.event.ContextEvent;
import org.jboss.weld.event.CurrentEventMetadata;
import org.jboss.weld.event.DefaultObserverNotifierFactory;
import org.jboss.weld.event.GlobalObserverNotifierService;
import org.jboss.weld.executor.ExecutorServicesFactory;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.injection.ResourceInjectionFactory;
import org.jboss.weld.injection.producer.InjectionTargetService;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.logging.VersionLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.BeanManagerLookupService;
import org.jboss.weld.manager.api.ExecutorServices;
import org.jboss.weld.metadata.TypeStore;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.module.ObserverNotifierFactory;
import org.jboss.weld.module.WeldModules;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.resources.DefaultResourceLoader;
import org.jboss.weld.resources.MemberTransformer;
import org.jboss.weld.resources.ReflectionCache;
import org.jboss.weld.resources.ReflectionCacheFactory;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.resources.spi.ClassFileServices;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.security.NoopSecurityServices;
import org.jboss.weld.security.spi.SecurityServices;
import org.jboss.weld.serialization.BeanIdentifierIndex;
import org.jboss.weld.serialization.ContextualStoreImpl;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.serialization.spi.ProxyServices;
import org.jboss.weld.servlet.spi.HttpContextActivationFilter;
import org.jboss.weld.servlet.spi.helpers.AcceptingHttpContextActivationFilter;
import org.jboss.weld.transaction.spi.TransactionServices;
import org.jboss.weld.util.Bindings;
import org.jboss.weld.util.Permissions;
import org.jboss.weld.util.bytecode.ClassFileUtils;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.collections.Iterables;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class WeldStartup {
   private BeanManagerImpl deploymentManager;
   private BeanDeploymentArchiveMapping bdaMapping;
   private Collection contexts;
   private List extensions;
   private Environment environment;
   private Deployment deployment;
   private DeploymentVisitor deploymentVisitor;
   private final ServiceRegistry initialServices = new SimpleServiceRegistry();
   private String contextId;
   private final Tracker tracker = Trackers.create();

   public WeldRuntime startContainer(String contextId, Environment environment, Deployment deployment) {
      if (deployment == null) {
         throw BootstrapLogger.LOG.deploymentRequired();
      } else {
         this.tracker.start("bootstrap");
         this.tracker.start("startContainer");
         this.checkApiVersion();
         ServiceRegistry registry = deployment.getServices();
         (new AdditionalServiceLoader(deployment)).loadAdditionalServices(registry);
         if (!registry.contains(ResourceLoader.class)) {
            registry.add(ResourceLoader.class, DefaultResourceLoader.INSTANCE);
         }

         WeldConfiguration configuration = new WeldConfiguration(registry, deployment);
         registry.add(WeldConfiguration.class, configuration);
         String finalContextId = BeanDeployments.getFinalId(contextId, ((WeldConfiguration)registry.get(WeldConfiguration.class)).getStringProperty(ConfigurationKey.ROLLING_UPGRADES_ID_DELIMITER));
         this.contextId = finalContextId;
         this.deployment = deployment;
         this.environment = environment;
         if (this.extensions == null) {
            this.setExtensions(deployment.getExtensions());
         }

         this.extensions.add(MetadataImpl.from(new WeldExtension()));
         String vetoTypeRegex = configuration.getStringProperty(ConfigurationKey.VETO_TYPES_WITHOUT_BEAN_DEFINING_ANNOTATION);
         if (!vetoTypeRegex.isEmpty()) {
            this.extensions.add(MetadataImpl.from(new WeldVetoExtension(vetoTypeRegex)));
         }

         if (ConfigurationKey.UnusedBeans.isEnabled(configuration)) {
            this.extensions.add(MetadataImpl.from(new WeldUnusedMetadataExtension()));
         }

         this.tracker.start("initServices");
         this.setupInitialServices();
         registry.addAll(this.initialServices.entrySet());
         if (!registry.contains(ProxyServices.class)) {
            registry.add(ProxyServices.class, new SimpleProxyServices());
         }

         if (!((ProxyServices)registry.get(ProxyServices.class)).supportsClassDefining()) {
            ClassFileUtils.makeClassLoaderMethodsAccessible();
         }

         if (!registry.contains(SecurityServices.class)) {
            registry.add(SecurityServices.class, NoopSecurityServices.INSTANCE);
         }

         this.addImplementationServices(registry);
         this.tracker.end();
         verifyServices(registry, environment.getRequiredDeploymentServices(), contextId);
         if (!registry.contains(TransactionServices.class)) {
            BootstrapLogger.LOG.jtaUnavailable();
         }

         this.deploymentManager = BeanManagerImpl.newRootManager(finalContextId, "deployment", registry);
         Container.initialize(finalContextId, this.deploymentManager, ServiceRegistries.unmodifiableServiceRegistry(deployment.getServices()), environment);
         this.getContainer().setState(ContainerState.STARTING);
         this.tracker.start("builtinContexts");
         this.contexts = this.createContexts(registry);
         this.tracker.end();
         this.bdaMapping = new BeanDeploymentArchiveMapping();
         this.deploymentVisitor = new DeploymentVisitor(this.deploymentManager, environment, deployment, this.contexts, this.bdaMapping);
         if (deployment instanceof CDI11Deployment) {
            registry.add(BeanManagerLookupService.class, new BeanManagerLookupService((CDI11Deployment)deployment, this.bdaMapping.getBdaToBeanManagerMap()));
         } else {
            BootstrapLogger.LOG.legacyDeploymentMetadataProvided();
         }

         this.tracker.start("readDeploymentStructure");
         this.deploymentVisitor.visit();
         this.tracker.end();
         WeldRuntime weldRuntime = new WeldRuntime(finalContextId, this.deploymentManager, this.bdaMapping.getBdaToBeanManagerMap());
         this.tracker.end();
         return weldRuntime;
      }
   }

   private void checkApiVersion() {
      if (Bean.class.getInterfaces().length == 1) {
         throw BootstrapLogger.LOG.cdiApiVersionMismatch();
      }
   }

   private void setupInitialServices() {
      if (!this.initialServices.contains(TypeStore.class)) {
         TypeStore store = new TypeStore();
         SharedObjectCache cache = new SharedObjectCache();
         ReflectionCache reflectionCache = ReflectionCacheFactory.newInstance(store);
         ClassTransformer classTransformer = new ClassTransformer(store, cache, reflectionCache, this.contextId);
         this.initialServices.add(TypeStore.class, store);
         this.initialServices.add(SharedObjectCache.class, cache);
         this.initialServices.add(ReflectionCache.class, reflectionCache);
         this.initialServices.add(ClassTransformer.class, classTransformer);
      }
   }

   private void addImplementationServices(ServiceRegistry services) {
      WeldModules modules = new WeldModules();
      services.add(WeldModules.class, modules);
      WeldConfiguration configuration = (WeldConfiguration)services.get(WeldConfiguration.class);
      services.add(SlimAnnotatedTypeStore.class, new SlimAnnotatedTypeStoreImpl());
      if (services.get(ClassTransformer.class) == null) {
         throw new IllegalStateException(ClassTransformer.class.getSimpleName() + " not installed.");
      } else {
         services.add(MemberTransformer.class, new MemberTransformer((ClassTransformer)services.get(ClassTransformer.class)));
         services.add(MetaAnnotationStore.class, new MetaAnnotationStore((ClassTransformer)services.get(ClassTransformer.class)));
         BeanIdentifierIndex beanIdentifierIndex = null;
         if (configuration.getBooleanProperty(ConfigurationKey.BEAN_IDENTIFIER_INDEX_OPTIMIZATION)) {
            beanIdentifierIndex = new BeanIdentifierIndex();
            services.add(BeanIdentifierIndex.class, beanIdentifierIndex);
         }

         services.add(ContextualStore.class, new ContextualStoreImpl(this.contextId, beanIdentifierIndex));
         services.add(CurrentInjectionPoint.class, new CurrentInjectionPoint());
         services.add(CurrentEventMetadata.class, new CurrentEventMetadata());
         services.add(SpecializationAndEnablementRegistry.class, new SpecializationAndEnablementRegistry());
         services.add(MissingDependenciesRegistry.class, new MissingDependenciesRegistry());
         ExecutorServices executor = (ExecutorServices)services.get(ExecutorServices.class);
         if (executor == null) {
            executor = ExecutorServicesFactory.create(DefaultResourceLoader.INSTANCE, configuration);
            if (executor != null) {
               services.add(ExecutorServices.class, executor);
            }
         }

         services.add(RequiredAnnotationDiscovery.class, new RequiredAnnotationDiscovery((ReflectionCache)services.get(ReflectionCache.class)));
         services.add(GlobalEnablementBuilder.class, new GlobalEnablementBuilder());
         if (!services.contains(HttpContextActivationFilter.class)) {
            services.add(HttpContextActivationFilter.class, AcceptingHttpContextActivationFilter.INSTANCE);
         }

         services.add(ProtectionDomainCache.class, new ProtectionDomainCache());
         services.add(ProxyInstantiator.class, ProxyInstantiator.Factory.create(configuration));
         services.add(ObserverNotifierFactory.class, DefaultObserverNotifierFactory.INSTANCE);
         services.add(ResourceInjectionFactory.class, new ResourceInjectionFactory());
         modules.postServiceRegistration(this.contextId, services);
         Object validator;
         if (configuration.getBooleanProperty(ConfigurationKey.CONCURRENT_DEPLOYMENT) && services.contains(ExecutorServices.class)) {
            validator = new ConcurrentValidator(modules.getPluggableValidators(), executor, ConfigurationKey.UnusedBeans.isEnabled(configuration) ? new ConcurrentHashMap() : null);
         } else {
            validator = new Validator(modules.getPluggableValidators(), ConfigurationKey.UnusedBeans.isEnabled(configuration) ? new HashMap() : null);
         }

         services.add(Validator.class, (Service)validator);
         GlobalObserverNotifierService observerNotificationService = new GlobalObserverNotifierService(services, this.contextId);
         services.add(GlobalObserverNotifierService.class, observerNotificationService);
         ContainerLifecycleEventPreloader preloader = null;
         int preloaderThreadPoolSize = configuration.getIntegerProperty(ConfigurationKey.PRELOADER_THREAD_POOL_SIZE);
         if (preloaderThreadPoolSize > 0 && Permissions.hasPermission(Permissions.MODIFY_THREAD_GROUP)) {
            preloader = new ContainerLifecycleEventPreloader(preloaderThreadPoolSize, observerNotificationService.getGlobalLenientObserverNotifier());
         }

         services.add(ContainerLifecycleEvents.class, new ContainerLifecycleEvents(preloader, (RequiredAnnotationDiscovery)services.get(RequiredAnnotationDiscovery.class)));
         if (this.environment.isEEModulesAware()) {
            services.add(BeanDeploymentModules.class, new BeanDeploymentModules(this.contextId, services));
         }

      }
   }

   private void installFastProcessAnnotatedTypeResolver(ServiceRegistry services) {
      ClassFileServices classFileServices = (ClassFileServices)services.get(ClassFileServices.class);
      if (classFileServices != null) {
         GlobalObserverNotifierService observers = (GlobalObserverNotifierService)services.get(GlobalObserverNotifierService.class);

         try {
            FastProcessAnnotatedTypeResolver resolver = new FastProcessAnnotatedTypeResolver(observers.getAllObserverMethods());
            services.add(FastProcessAnnotatedTypeResolver.class, resolver);
         } catch (UnsupportedObserverMethodException var5) {
            BootstrapLogger.LOG.notUsingFastResolver(var5.getObserver());
            return;
         }
      }

   }

   public void startInitialization() {
      if (this.deploymentManager == null) {
         throw BootstrapLogger.LOG.managerNotInitialized();
      } else {
         this.tracker.start("startInitialization");
         Set physicalBeanDeploymentArchives = new HashSet(this.getBeanDeployments());
         ExtensionBeanDeployer extensionBeanDeployer = new ExtensionBeanDeployer(this.deploymentManager, this.deployment, this.bdaMapping, this.contexts);
         extensionBeanDeployer.addExtensions(this.extensions);
         extensionBeanDeployer.deployBeans();
         this.installFastProcessAnnotatedTypeResolver(this.deploymentManager.getServices());
         this.deploymentManager.addBean(new BeanManagerBean(this.deploymentManager));
         this.deploymentManager.addBean(new BeanManagerImplBean(this.deploymentManager));
         this.deploymentVisitor.visit();
         this.tracker.start("BeforeBeanDiscovery");
         BeforeBeanDiscoveryImpl.fire(this.deploymentManager, this.deployment, this.bdaMapping, this.contexts);
         this.tracker.end();
         Iterator var3 = physicalBeanDeploymentArchives.iterator();

         BeanDeployment beanDeployment;
         while(var3.hasNext()) {
            beanDeployment = (BeanDeployment)var3.next();
            beanDeployment.createClasses();
         }

         this.deploymentVisitor.visit();
         var3 = this.getBeanDeployments().iterator();

         while(var3.hasNext()) {
            beanDeployment = (BeanDeployment)var3.next();
            beanDeployment.createTypes();
         }

         this.tracker.start("AfterTypeDiscovery");
         AfterTypeDiscoveryImpl.fire(this.deploymentManager, this.deployment, this.bdaMapping, this.contexts);
         this.tracker.end();
         var3 = this.getBeanDeployments().iterator();

         while(var3.hasNext()) {
            beanDeployment = (BeanDeployment)var3.next();
            beanDeployment.createEnablement();
         }

         this.tracker.end();
      }
   }

   public void deployBeans() {
      this.tracker.start("deployBeans");
      Iterator var1 = this.getBeanDeployments().iterator();

      BeanDeployment beanDeployment;
      while(var1.hasNext()) {
         beanDeployment = (BeanDeployment)var1.next();
         beanDeployment.createBeans(this.environment);
      }

      var1 = this.getBeanDeployments().iterator();

      while(var1.hasNext()) {
         beanDeployment = (BeanDeployment)var1.next();
         beanDeployment.getBeanDeployer().processClassBeanAttributes();
         beanDeployment.getBeanDeployer().createProducersAndObservers();
      }

      var1 = this.getBeanDeployments().iterator();

      while(var1.hasNext()) {
         beanDeployment = (BeanDeployment)var1.next();
         beanDeployment.getBeanDeployer().processProducerAttributes();
         beanDeployment.getBeanDeployer().createNewBeans();
      }

      var1 = this.getBeanDeployments().iterator();

      while(var1.hasNext()) {
         beanDeployment = (BeanDeployment)var1.next();
         beanDeployment.deploySpecialized(this.environment);
      }

      var1 = this.getBeanDeployments().iterator();

      while(var1.hasNext()) {
         beanDeployment = (BeanDeployment)var1.next();
         beanDeployment.deployBeans(this.environment);
      }

      this.getContainer().setState(ContainerState.DISCOVERED);
      this.flushCaches();
      this.tracker.start("AfterBeanDiscovery");
      AfterBeanDiscoveryImpl.fire(this.deploymentManager, this.deployment, this.bdaMapping, this.contexts);
      this.tracker.end();
      this.flushCaches();
      if (((GlobalEnablementBuilder)this.deployment.getServices().getRequired(GlobalEnablementBuilder.class)).isDirty()) {
         var1 = this.getBeanDeployments().iterator();

         while(var1.hasNext()) {
            beanDeployment = (BeanDeployment)var1.next();
            beanDeployment.createEnablement();
         }
      }

      this.deploymentVisitor.visit();
      var1 = this.getBeanDeployments().iterator();

      while(var1.hasNext()) {
         beanDeployment = (BeanDeployment)var1.next();
         ((InjectionTargetService)beanDeployment.getBeanManager().getServices().get(InjectionTargetService.class)).initialize();
         beanDeployment.afterBeanDiscovery(this.environment);
      }

      this.getContainer().putBeanDeployments(this.bdaMapping);
      this.getContainer().setState(ContainerState.DEPLOYED);
      this.tracker.end();
   }

   public void validateBeans() {
      BootstrapLogger.LOG.validatingBeans();
      this.tracker.start("validateBeans");

      try {
         Iterator var1 = this.getBeanDeployments().iterator();

         while(var1.hasNext()) {
            BeanDeployment beanDeployment = (BeanDeployment)var1.next();
            BeanManagerImpl beanManager = beanDeployment.getBeanManager();
            beanManager.getBeanResolver().clear();
            ((Validator)this.deployment.getServices().get(Validator.class)).validateDeployment(beanManager, beanDeployment);
            ((InjectionTargetService)beanManager.getServices().get(InjectionTargetService.class)).validate();
         }
      } catch (Exception var4) {
         this.validationFailed(var4);
         throw var4;
      }

      this.getContainer().setState(ContainerState.VALIDATED);
      this.tracker.start("AfterDeploymentValidation");
      AfterDeploymentValidationImpl.fire(this.deploymentManager);
      this.tracker.end();
      this.tracker.end();
   }

   public void endInitialization() {
      this.tracker.start("endInitialization");
      BeanIdentifierIndex index = (BeanIdentifierIndex)this.deploymentManager.getServices().get(BeanIdentifierIndex.class);
      if (index != null) {
         index.build(this.getBeansForBeanIdentifierIndex());
      }

      this.flushCaches();
      this.deploymentManager.getServices().cleanupAfterBoot();
      this.deploymentManager.cleanupAfterBoot();
      Iterator var2 = this.getBeanDeployments().iterator();

      BeanDeployment beanDeployment;
      while(var2.hasNext()) {
         beanDeployment = (BeanDeployment)var2.next();
         BeanManagerImpl beanManager = beanDeployment.getBeanManager();
         beanManager.getInterceptorMetadataReader().cleanAfterBoot();
         beanManager.getServices().cleanupAfterBoot();
         beanManager.cleanupAfterBoot();
         Iterator var5 = beanManager.getBeans().iterator();

         while(var5.hasNext()) {
            Bean bean = (Bean)var5.next();
            if (bean instanceof RIBean) {
               RIBean riBean = (RIBean)bean;
               riBean.cleanupAfterBoot();
            }
         }

         var5 = beanManager.getDecorators().iterator();

         while(var5.hasNext()) {
            Decorator decorator = (Decorator)var5.next();
            if (decorator instanceof DecoratorImpl) {
               ((DecoratorImpl)Reflections.cast(decorator)).cleanupAfterBoot();
            }
         }

         var5 = beanManager.getInterceptors().iterator();

         while(var5.hasNext()) {
            Interceptor interceptor = (Interceptor)var5.next();
            if (interceptor instanceof InterceptorImpl) {
               ((InterceptorImpl)Reflections.cast(interceptor)).cleanupAfterBoot();
            }
         }
      }

      var2 = this.getBeanDeployments().iterator();

      while(var2.hasNext()) {
         beanDeployment = (BeanDeployment)var2.next();
         beanDeployment.getBeanDeployer().cleanup();
      }

      BeanDeploymentModules modules = (BeanDeploymentModules)this.deploymentManager.getServices().get(BeanDeploymentModules.class);
      if (modules != null) {
         modules.processBeanDeployments(this.getBeanDeployments());
         BootstrapLogger.LOG.debugv("EE modules: {0}", modules);
      }

      Iterator var9;
      if (ConfigurationKey.UnusedBeans.isEnabled((WeldConfiguration)this.deploymentManager.getServices().get(WeldConfiguration.class))) {
         this.deploymentManager.getBeanResolver().clear();
         var9 = this.getBeanDeployments().iterator();

         while(var9.hasNext()) {
            BeanDeployment beanDeployment = (BeanDeployment)var9.next();
            beanDeployment.getBeanManager().getBeanResolver().clear();
         }

         ((Validator)this.deploymentManager.getServices().get(Validator.class)).clearResolved();
         ((ClassTransformer)this.deploymentManager.getServices().get(ClassTransformer.class)).cleanupAfterBoot();
      }

      this.getContainer().setState(ContainerState.INITIALIZED);
      if (modules != null) {
         var9 = modules.iterator();

         while(var9.hasNext()) {
            BeanDeploymentModule module = (BeanDeploymentModule)var9.next();
            if (!module.isWebModule()) {
               module.fireEvent(Object.class, ContextEvent.APPLICATION_INITIALIZED, Literal.APPLICATION);
            }
         }
      }

      this.tracker.close();
   }

   private void flushCaches() {
      this.deploymentManager.getBeanResolver().clear();
      this.deploymentManager.getAccessibleLenientObserverNotifier().clear();
      this.deploymentManager.getGlobalStrictObserverNotifier().clear();
      this.deploymentManager.getGlobalLenientObserverNotifier().clear();
      this.deploymentManager.getDecoratorResolver().clear();
      this.deploymentManager.getInterceptorResolver().clear();
      this.deploymentManager.getNameBasedResolver().clear();
      Iterator var1 = this.getBeanDeployments().iterator();

      while(var1.hasNext()) {
         BeanDeployment beanDeployment = (BeanDeployment)var1.next();
         BeanManagerImpl beanManager = beanDeployment.getBeanManager();
         beanManager.getBeanResolver().clear();
         beanManager.getAccessibleLenientObserverNotifier().clear();
         beanManager.getDecoratorResolver().clear();
         beanManager.getInterceptorResolver().clear();
         beanManager.getNameBasedResolver().clear();
      }

   }

   private Collection getBeanDeployments() {
      return this.bdaMapping.getBeanDeployments();
   }

   private Container getContainer() {
      return Container.instance(this.contextId);
   }

   protected Collection createContexts(ServiceRegistry services) {
      List contexts = new ArrayList();
      BeanIdentifierIndex beanIdentifierIndex = (BeanIdentifierIndex)services.get(BeanIdentifierIndex.class);
      Set boundQualifires = ImmutableSet.builder().addAll((Iterable)Bindings.DEFAULT_QUALIFIERS).add(BoundLiteral.INSTANCE).build();
      Set unboundQualifiers = ImmutableSet.builder().addAll((Iterable)Bindings.DEFAULT_QUALIFIERS).add(UnboundLiteral.INSTANCE).build();
      contexts.add(new ContextHolder(new ApplicationContextImpl(this.contextId), ApplicationContext.class, unboundQualifiers));
      contexts.add(new ContextHolder(new SingletonContextImpl(this.contextId), SingletonContext.class, unboundQualifiers));
      contexts.add(new ContextHolder(new BoundSessionContextImpl(this.contextId, beanIdentifierIndex), BoundSessionContext.class, boundQualifires));
      contexts.add(new ContextHolder(new BoundConversationContextImpl(this.contextId, services), BoundConversationContext.class, boundQualifires));
      contexts.add(new ContextHolder(new BoundRequestContextImpl(this.contextId), BoundRequestContext.class, boundQualifires));
      contexts.add(new ContextHolder(new RequestContextImpl(this.contextId), RequestContext.class, unboundQualifiers));
      contexts.add(new ContextHolder(new DependentContextImpl((ContextualStore)services.get(ContextualStore.class)), DependentContext.class, unboundQualifiers));
      ((WeldModules)services.get(WeldModules.class)).postContextRegistration(this.contextId, services, contexts);
      Iterator var6 = contexts.iterator();

      while(var6.hasNext()) {
         ContextHolder context = (ContextHolder)var6.next();
         this.deploymentManager.addContext(context.getContext());
         this.deploymentManager.addBean(ContextBean.of(context, this.deploymentManager));
      }

      return contexts;
   }

   protected static void verifyServices(ServiceRegistry services, Set requiredServices, Object target) {
      Iterator var3 = requiredServices.iterator();

      Class serviceType;
      do {
         if (!var3.hasNext()) {
            return;
         }

         serviceType = (Class)var3.next();
      } while(services.contains(serviceType));

      throw BootstrapLogger.LOG.unspecifiedRequiredService(serviceType.getName(), target);
   }

   public TypeDiscoveryConfiguration startExtensions(Iterable extensions) {
      this.setExtensions(extensions);
      Set beanDefiningAnnotations = ImmutableSet.of(Dependent.class, RequestScoped.class, ConversationScoped.class, SessionScoped.class, ApplicationScoped.class, javax.interceptor.Interceptor.class, javax.decorator.Decorator.class, Model.class, NormalScope.class, Stereotype.class);
      return new TypeDiscoveryConfigurationImpl(beanDefiningAnnotations);
   }

   private Set getBeansForBeanIdentifierIndex() {
      Set beans = new HashSet();
      Iterator var2 = this.getBeanDeployments().iterator();

      label32:
      while(var2.hasNext()) {
         BeanDeployment beanDeployment = (BeanDeployment)var2.next();
         Iterator var4 = beanDeployment.getBeanManager().getBeans().iterator();

         while(true) {
            Bean bean;
            do {
               do {
                  if (!var4.hasNext()) {
                     continue label32;
                  }

                  bean = (Bean)var4.next();
               } while(bean instanceof AbstractBuiltInBean);
            } while(!bean.getScope().equals(SessionScoped.class) && !bean.getScope().equals(ConversationScoped.class));

            beans.add(bean);
         }
      }

      return beans;
   }

   private void setExtensions(Iterable extensions) {
      this.extensions = new ArrayList();
      Iterables.addAll(this.extensions, extensions);
   }

   BeanManagerImpl getDeploymentManager() {
      return this.deploymentManager;
   }

   BeanDeploymentArchiveMapping getBdaMapping() {
      return this.bdaMapping;
   }

   Collection getContexts() {
      return this.contexts;
   }

   Deployment getDeployment() {
      return this.deployment;
   }

   private void validationFailed(Exception failure) {
      Iterator var2 = this.getBeanDeployments().iterator();

      while(var2.hasNext()) {
         BeanDeployment beanDeployment = (BeanDeployment)var2.next();
         beanDeployment.getBeanManager().validationFailed(failure, this.environment);
      }

   }

   static {
      VersionLogger.LOG.version(Formats.version((Package)null));
   }
}
