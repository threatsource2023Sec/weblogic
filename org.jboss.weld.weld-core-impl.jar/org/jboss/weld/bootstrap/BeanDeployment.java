package org.jboss.weld.bootstrap;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jboss.weld.bean.builtin.BeanManagerBean;
import org.jboss.weld.bean.builtin.BeanManagerImplBean;
import org.jboss.weld.bean.builtin.BeanMetadataBean;
import org.jboss.weld.bean.builtin.ContextBean;
import org.jboss.weld.bean.builtin.ConversationBean;
import org.jboss.weld.bean.builtin.DecoratedBeanMetadataBean;
import org.jboss.weld.bean.builtin.DecoratorMetadataBean;
import org.jboss.weld.bean.builtin.EventBean;
import org.jboss.weld.bean.builtin.EventMetadataBean;
import org.jboss.weld.bean.builtin.InjectionPointBean;
import org.jboss.weld.bean.builtin.InstanceBean;
import org.jboss.weld.bean.builtin.InterceptedBeanMetadataBean;
import org.jboss.weld.bean.builtin.InterceptionFactoryBean;
import org.jboss.weld.bean.builtin.InterceptorMetadataBean;
import org.jboss.weld.bean.builtin.RequestContextControllerBean;
import org.jboss.weld.bean.builtin.ee.PrincipalBean;
import org.jboss.weld.bean.proxy.InterceptionFactoryDataCache;
import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.enablement.GlobalEnablementBuilder;
import org.jboss.weld.bootstrap.enablement.ModuleEnablement;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.injection.producer.InjectionTargetService;
import org.jboss.weld.interceptor.builder.InterceptorsApiAbstraction;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.api.ExecutorServices;
import org.jboss.weld.metadata.FilterPredicate;
import org.jboss.weld.metadata.ScanningPredicate;
import org.jboss.weld.module.EjbSupport;
import org.jboss.weld.module.WeldModules;
import org.jboss.weld.persistence.PersistenceApiAbstraction;
import org.jboss.weld.resources.DefaultResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.security.NoopSecurityServices;
import org.jboss.weld.security.spi.SecurityServices;
import org.jboss.weld.util.AnnotationApiAbstraction;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.ws.WSApiAbstraction;

public class BeanDeployment {
   private final BeanDeploymentArchive beanDeploymentArchive;
   private final BeanManagerImpl beanManager;
   private final BeanDeployer beanDeployer;
   private final Collection contexts;

   public BeanDeployment(BeanDeploymentArchive beanDeploymentArchive, BeanManagerImpl deploymentManager, ServiceRegistry deploymentServices, Collection contexts) {
      this(beanDeploymentArchive, deploymentManager, deploymentServices, contexts, false);
   }

   public BeanDeployment(BeanDeploymentArchive beanDeploymentArchive, BeanManagerImpl deploymentManager, ServiceRegistry deploymentServices, Collection contexts, boolean additionalBeanArchive) {
      this.beanDeploymentArchive = beanDeploymentArchive;
      ServiceRegistry registry = beanDeploymentArchive.getServices();
      ResourceLoader resourceLoader = (ResourceLoader)registry.get(ResourceLoader.class);
      if (resourceLoader == null) {
         resourceLoader = DefaultResourceLoader.INSTANCE;
         registry.add(ResourceLoader.class, (Service)resourceLoader);
      }

      ServiceRegistry services = new SimpleServiceRegistry();
      services.addAll(deploymentServices.entrySet());
      services.addAll(registry.entrySet());
      services.add(PersistenceApiAbstraction.class, new PersistenceApiAbstraction((ResourceLoader)resourceLoader));
      services.add(WSApiAbstraction.class, new WSApiAbstraction((ResourceLoader)resourceLoader));
      services.add(InterceptorsApiAbstraction.class, new InterceptorsApiAbstraction((ResourceLoader)resourceLoader));
      services.add(AnnotationApiAbstraction.class, new AnnotationApiAbstraction((ResourceLoader)resourceLoader));
      this.beanManager = BeanManagerImpl.newManager(deploymentManager, BeanDeployments.getFinalId(beanDeploymentArchive.getId(), ((WeldConfiguration)services.get(WeldConfiguration.class)).getStringProperty(ConfigurationKey.ROLLING_UPGRADES_ID_DELIMITER)), services);
      services.add(InjectionTargetService.class, new InjectionTargetService(this.beanManager));
      services.add(InterceptionFactoryDataCache.class, new InterceptionFactoryDataCache(this.beanManager));
      ((WeldModules)services.get(WeldModules.class)).postBeanArchiveServiceRegistration(services, this.beanManager, beanDeploymentArchive);
      services.addIfAbsent(EjbSupport.class, EjbSupport.NOOP_IMPLEMENTATION);
      if (((WeldConfiguration)services.get(WeldConfiguration.class)).getBooleanProperty(ConfigurationKey.CONCURRENT_DEPLOYMENT) && services.contains(ExecutorServices.class)) {
         this.beanDeployer = new ConcurrentBeanDeployer(this.beanManager, deploymentServices);
      } else {
         this.beanDeployer = new BeanDeployer(this.beanManager, deploymentServices);
      }

      ((SpecializationAndEnablementRegistry)this.beanManager.getServices().get(SpecializationAndEnablementRegistry.class)).registerEnvironment(this.beanManager, this.beanDeployer.getEnvironment(), additionalBeanArchive);
      this.beanManager.addBean(new BeanManagerBean(this.beanManager));
      this.beanManager.addBean(new BeanManagerImplBean(this.beanManager));
      this.contexts = contexts;
   }

   public BeanManagerImpl getBeanManager() {
      return this.beanManager;
   }

   public BeanDeployer getBeanDeployer() {
      return this.beanDeployer;
   }

   public BeanDeploymentArchive getBeanDeploymentArchive() {
      return this.beanDeploymentArchive;
   }

   private Predicate createFilter() {
      if (this.getBeanDeploymentArchive().getBeansXml() != null && this.getBeanDeploymentArchive().getBeansXml().getScanning() != null) {
         Function filterToPredicateFunction = new Function() {
            final ResourceLoader resourceLoader;

            {
               this.resourceLoader = BeanDeployment.this.beanDeployer.getResourceLoader();
            }

            public Predicate apply(Metadata from) {
               return new FilterPredicate(from, this.resourceLoader);
            }
         };
         Object includeFilters;
         if (this.getBeanDeploymentArchive().getBeansXml().getScanning().getIncludes() != null) {
            includeFilters = this.getBeanDeploymentArchive().getBeansXml().getScanning().getIncludes();
         } else {
            includeFilters = Collections.emptyList();
         }

         Object excludeFilters;
         if (this.getBeanDeploymentArchive().getBeansXml().getScanning().getExcludes() != null) {
            excludeFilters = this.getBeanDeploymentArchive().getBeansXml().getScanning().getExcludes();
         } else {
            excludeFilters = Collections.emptyList();
         }

         Collection includes = (Collection)((Collection)includeFilters).stream().map(filterToPredicateFunction).collect(Collectors.toList());
         Collection excludes = (Collection)((Collection)excludeFilters).stream().map(filterToPredicateFunction).collect(Collectors.toList());
         return new ScanningPredicate(includes, excludes);
      } else {
         return null;
      }
   }

   public void createClasses() {
      Stream classNames = this.beanDeploymentArchive.getBeanClasses().stream();
      Collection loadedClasses = this.beanDeploymentArchive.getLoadedBeanClasses();
      if (!loadedClasses.isEmpty()) {
         Set preloadedClassNames = (Set)loadedClasses.stream().map((c) -> {
            return c.getName();
         }).collect(Collectors.toSet());
         classNames = classNames.filter((name) -> {
            return !preloadedClassNames.contains(name);
         });
      }

      Predicate filter = this.createFilter();
      if (filter != null) {
         classNames = classNames.filter(filter);
         loadedClasses = (Collection)loadedClasses.stream().filter((clazz) -> {
            return filter.test(clazz.getName());
         }).collect(Collectors.toSet());
      }

      this.beanDeployer.addLoadedClasses(loadedClasses);
      this.beanDeployer.addClasses((Iterable)classNames.collect(Collectors.toSet()));
   }

   public void createEnablement() {
      GlobalEnablementBuilder builder = (GlobalEnablementBuilder)this.beanManager.getServices().get(GlobalEnablementBuilder.class);
      ModuleEnablement enablement = builder.createModuleEnablement(this);
      this.beanManager.setEnabled(enablement);
      if (BootstrapLogger.LOG.isDebugEnabled()) {
         BootstrapLogger.LOG.enabledAlternatives(this.beanManager, WeldCollections.toMultiRowString(enablement.getAllAlternatives()));
         BootstrapLogger.LOG.enabledDecorators(this.beanManager, WeldCollections.toMultiRowString(enablement.getDecorators()));
         BootstrapLogger.LOG.enabledInterceptors(this.beanManager, WeldCollections.toMultiRowString(enablement.getInterceptors()));
      }

   }

   public void createTypes() {
      this.beanDeployer.processAnnotatedTypes();
      this.beanDeployer.registerAnnotatedTypes();
   }

   public void createBeans(Environment environment) {
      ((WeldModules)this.getBeanManager().getServices().get(WeldModules.class)).preBeanRegistration(this, environment);
      if (this.getBeanManager().getServices().get(EjbSupport.class) == EjbSupport.NOOP_IMPLEMENTATION) {
         this.beanDeployer.addBuiltInBean(new InjectionPointBean(this.beanManager));
      }

      this.beanDeployer.addBuiltInBean(new EventMetadataBean(this.beanManager));
      this.beanDeployer.addBuiltInBean(new EventBean(this.beanManager));
      this.beanDeployer.addBuiltInBean(new InstanceBean(this.beanManager));
      this.beanDeployer.addBuiltInBean(new ConversationBean(this.beanManager));
      this.beanDeployer.addBuiltInBean(new BeanMetadataBean(this.beanManager));
      this.beanDeployer.addBuiltInBean(new InterceptedBeanMetadataBean(this.beanManager));
      this.beanDeployer.addBuiltInBean(new DecoratedBeanMetadataBean(this.beanManager));
      this.beanDeployer.addBuiltInBean(new InterceptorMetadataBean(this.beanManager));
      this.beanDeployer.addBuiltInBean(new DecoratorMetadataBean(this.beanManager));
      this.beanDeployer.addBuiltInBean(new InterceptionFactoryBean(this.beanManager));
      if (this.beanManager.getServices().getRequired(SecurityServices.class) != NoopSecurityServices.INSTANCE) {
         this.beanDeployer.addBuiltInBean(new PrincipalBean(this.beanManager));
      }

      Iterator var2 = this.contexts.iterator();

      while(var2.hasNext()) {
         ContextHolder context = (ContextHolder)var2.next();
         this.beanDeployer.addBuiltInBean(ContextBean.of(context, this.beanManager));
      }

      this.beanDeployer.addBuiltInBean(new RequestContextControllerBean(this.beanManager));
      if (this.beanDeploymentArchive.getBeansXml() != null && this.beanDeploymentArchive.getBeansXml().isTrimmed()) {
         this.beanDeployer.getEnvironment().trim();
      }

      this.beanDeployer.createClassBeans();
   }

   public void deploySpecialized(Environment environment) {
      this.beanDeployer.deploySpecialized();
   }

   public void deployBeans(Environment environment) {
      this.beanDeployer.deploy();
   }

   public void afterBeanDiscovery(Environment environment) {
      this.beanDeployer.doAfterBeanDiscovery(this.beanManager.getBeans());
      this.beanDeployer.doAfterBeanDiscovery(this.beanManager.getDecorators());
      this.beanDeployer.doAfterBeanDiscovery(this.beanManager.getInterceptors());
      this.beanDeployer.registerCdiInterceptorsForMessageDrivenBeans();
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("BeanDeployment ");
      builder.append("[beanDeploymentArchiveId=");
      builder.append(this.beanDeploymentArchive.getId());
      if (!this.beanDeploymentArchive.getId().equals(this.beanManager.getId())) {
         builder.append(", beanManagerId=");
         builder.append(this.beanManager.getId());
      }

      builder.append("]");
      return builder.toString();
   }
}
