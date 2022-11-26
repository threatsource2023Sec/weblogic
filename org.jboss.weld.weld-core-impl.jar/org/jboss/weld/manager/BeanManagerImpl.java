package org.jboss.weld.manager;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.Initialized.Literal;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InjectionTargetFactory;
import javax.enterprise.inject.spi.InterceptionFactory;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.inject.spi.Prioritized;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProducerFactory;
import javax.enterprise.util.TypeLiteral;
import org.jboss.weld.Container;
import org.jboss.weld.annotated.AnnotatedTypeValidator;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMember;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.bean.ContextualInstance;
import org.jboss.weld.bean.NewBean;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.SyntheticBeanFactory;
import org.jboss.weld.bean.WeldBean;
import org.jboss.weld.bean.attributes.BeanAttributesFactory;
import org.jboss.weld.bean.builtin.AbstractBuiltInBean;
import org.jboss.weld.bean.builtin.ExtensionBean;
import org.jboss.weld.bean.builtin.InstanceImpl;
import org.jboss.weld.bean.proxy.ClientProxyProvider;
import org.jboss.weld.bean.proxy.DecorationHelper;
import org.jboss.weld.bootstrap.SpecializationAndEnablementRegistry;
import org.jboss.weld.bootstrap.Validator;
import org.jboss.weld.bootstrap.WeldUnusedMetadataExtension;
import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.enablement.ModuleEnablement;
import org.jboss.weld.bootstrap.events.ContainerLifecycleEvents;
import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.contexts.CreationalContextImpl;
import org.jboss.weld.contexts.PassivatingContextWrapper;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.event.ContainerLifecycleEventObserverMethod;
import org.jboss.weld.event.EventImpl;
import org.jboss.weld.event.EventMetadataImpl;
import org.jboss.weld.event.FastEvent;
import org.jboss.weld.event.GlobalObserverNotifierService;
import org.jboss.weld.event.ObserverMethodImpl;
import org.jboss.weld.event.ObserverNotifier;
import org.jboss.weld.events.WeldEvent;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.InjectionException;
import org.jboss.weld.inject.WeldInstance;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.injection.EmptyInjectionPoint;
import org.jboss.weld.injection.InterceptionFactoryImpl;
import org.jboss.weld.injection.ThreadLocalStack;
import org.jboss.weld.injection.attributes.FieldInjectionPointAttributes;
import org.jboss.weld.injection.attributes.InferringFieldInjectionPointAttributes;
import org.jboss.weld.injection.attributes.InferringParameterInjectionPointAttributes;
import org.jboss.weld.injection.attributes.ParameterInjectionPointAttributes;
import org.jboss.weld.injection.producer.WeldInjectionTargetBuilderImpl;
import org.jboss.weld.interceptor.reader.InterceptorMetadataReader;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.api.WeldInjectionTargetBuilder;
import org.jboss.weld.manager.api.WeldInjectionTargetFactory;
import org.jboss.weld.manager.api.WeldManager;
import org.jboss.weld.metadata.cache.InterceptorBindingModel;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.metadata.cache.ScopeModel;
import org.jboss.weld.metadata.cache.StereotypeModel;
import org.jboss.weld.module.EjbSupport;
import org.jboss.weld.module.ExpressionLanguageSupport;
import org.jboss.weld.module.ObserverNotifierFactory;
import org.jboss.weld.resolution.BeanTypeAssignabilityRules;
import org.jboss.weld.resolution.DecoratorResolvableBuilder;
import org.jboss.weld.resolution.InterceptorResolvable;
import org.jboss.weld.resolution.InterceptorResolvableBuilder;
import org.jboss.weld.resolution.NameBasedResolver;
import org.jboss.weld.resolution.QualifierInstance;
import org.jboss.weld.resolution.Resolvable;
import org.jboss.weld.resolution.ResolvableBuilder;
import org.jboss.weld.resolution.TypeSafeBeanResolver;
import org.jboss.weld.resolution.TypeSafeDecoratorResolver;
import org.jboss.weld.resolution.TypeSafeInterceptorResolver;
import org.jboss.weld.resolution.TypeSafeObserverResolver;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.resources.MemberTransformer;
import org.jboss.weld.serialization.ContextualStoreImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.Bindings;
import org.jboss.weld.util.ForwardingBeanManager;
import org.jboss.weld.util.InjectionPoints;
import org.jboss.weld.util.Interceptors;
import org.jboss.weld.util.LazyValueHolder;
import org.jboss.weld.util.Observers;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.collections.Iterables;
import org.jboss.weld.util.collections.SetMultimap;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Reflections;

public class BeanManagerImpl implements WeldManager, Serializable {
   private static final long serialVersionUID = 3021562879133838561L;
   private static final String CREATIONAL_CONTEXT = "creationalContext";
   private final transient ServiceRegistry services;
   private final transient Map contexts;
   private final transient ClientProxyProvider clientProxyProvider;
   private final transient Map enterpriseBeans;
   private transient volatile ModuleEnablement enabled;
   private final transient TypeSafeBeanResolver beanResolver;
   private final transient TypeSafeDecoratorResolver decoratorResolver;
   private final transient TypeSafeInterceptorResolver interceptorResolver;
   private final transient NameBasedResolver nameBasedResolver;
   private final transient ELResolver weldELResolver;
   private final transient ObserverNotifier accessibleLenientObserverNotifier;
   private final transient ObserverNotifier globalLenientObserverNotifier;
   private final transient ObserverNotifier globalStrictObserverNotifier;
   private final transient List enabledBeans;
   private final transient List sharedBeans;
   private final transient List specializedBeans;
   private final transient List decorators;
   private final transient List interceptors;
   private final transient List namespaces;
   private final transient List observers;
   private transient Set beanSet = Collections.synchronizedSet(new HashSet());
   private final transient Set managers;
   private final transient HashSet accessibleManagers;
   private final String id;
   private final String contextId;
   private final transient ConcurrentMap interceptorModelRegistry = new ConcurrentHashMap();
   private final transient InterceptorMetadataReader interceptorMetadataReader = new InterceptorMetadataReader(this);
   private final transient ContainerLifecycleEvents containerLifecycleEvents;
   private final transient SpecializationAndEnablementRegistry registry;
   private final transient CurrentInjectionPoint currentInjectionPoint;
   private final transient boolean clientProxyOptimization;
   private final transient List validationFailureCallbacks;
   private final transient LazyValueHolder requestInitializedEvent;
   private final transient LazyValueHolder requestBeforeDestroyedEvent;
   private final transient LazyValueHolder requestDestroyedEvent;

   public static BeanManagerImpl newRootManager(String contextId, String id, ServiceRegistry serviceRegistry) {
      Map contexts = new ConcurrentHashMap();
      return new BeanManagerImpl(serviceRegistry, new CopyOnWriteArrayList(), new CopyOnWriteArrayList(), new CopyOnWriteArrayList(), new CopyOnWriteArrayList(), new CopyOnWriteArrayList(), new CopyOnWriteArrayList(), new ConcurrentHashMap(), new ClientProxyProvider(contextId), contexts, ModuleEnablement.EMPTY_ENABLEMENT, id, new AtomicInteger(), new HashSet(), contextId);
   }

   public static BeanManagerImpl newManager(BeanManagerImpl rootManager, String id, ServiceRegistry services) {
      return new BeanManagerImpl(services, new CopyOnWriteArrayList(), new CopyOnWriteArrayList(), new CopyOnWriteArrayList(), new CopyOnWriteArrayList(), new CopyOnWriteArrayList(), new CopyOnWriteArrayList(), rootManager.getEnterpriseBeans(), rootManager.getClientProxyProvider(), rootManager.getContexts(), ModuleEnablement.EMPTY_ENABLEMENT, id, new AtomicInteger(), rootManager.managers, rootManager.contextId);
   }

   private BeanManagerImpl(ServiceRegistry serviceRegistry, List beans, List transitiveBeans, List decorators, List interceptors, List observers, List namespaces, Map enterpriseBeans, ClientProxyProvider clientProxyProvider, Map contexts, ModuleEnablement enabled, String id, AtomicInteger childIds, Set managers, String contextId) {
      this.services = serviceRegistry;
      this.enabledBeans = beans;
      this.sharedBeans = transitiveBeans;
      this.decorators = decorators;
      this.interceptors = interceptors;
      this.enterpriseBeans = enterpriseBeans;
      this.clientProxyProvider = clientProxyProvider;
      this.contexts = contexts;
      this.observers = observers;
      this.enabled = enabled;
      this.namespaces = namespaces;
      this.id = id;
      this.managers = managers;
      this.contextId = contextId;
      managers.add(this);
      this.accessibleManagers = new HashSet();
      BeanTransform beanTransform = new BeanTransform(this);
      this.beanResolver = new TypeSafeBeanResolver(this, this.createDynamicAccessibleIterable(beanTransform));
      this.decoratorResolver = new TypeSafeDecoratorResolver(this, this.createDynamicGlobalIterable(BeanManagerImpl::getDecorators));
      this.interceptorResolver = new TypeSafeInterceptorResolver(this, this.createDynamicGlobalIterable(BeanManagerImpl::getInterceptors));
      this.nameBasedResolver = new NameBasedResolver(this, this.createDynamicAccessibleIterable(beanTransform));
      this.weldELResolver = (ELResolver)this.services.getOptional(ExpressionLanguageSupport.class).map((el) -> {
         return el.createElResolver(this);
      }).orElse((Object)null);
      TypeSafeObserverResolver accessibleObserverResolver = new TypeSafeObserverResolver((MetaAnnotationStore)this.getServices().get(MetaAnnotationStore.class), this.createDynamicAccessibleIterable(BeanManagerImpl::getObservers), (WeldConfiguration)this.getServices().get(WeldConfiguration.class));
      this.accessibleLenientObserverNotifier = ((ObserverNotifierFactory)this.getServices().get(ObserverNotifierFactory.class)).create(contextId, accessibleObserverResolver, this.getServices(), false);
      GlobalObserverNotifierService globalObserverNotifierService = (GlobalObserverNotifierService)this.services.get(GlobalObserverNotifierService.class);
      this.globalLenientObserverNotifier = globalObserverNotifierService.getGlobalLenientObserverNotifier();
      this.globalStrictObserverNotifier = globalObserverNotifierService.getGlobalStrictObserverNotifier();
      globalObserverNotifierService.registerBeanManager(this);
      this.containerLifecycleEvents = (ContainerLifecycleEvents)serviceRegistry.get(ContainerLifecycleEvents.class);
      this.registry = (SpecializationAndEnablementRegistry)this.getServices().get(SpecializationAndEnablementRegistry.class);
      this.currentInjectionPoint = (CurrentInjectionPoint)this.getServices().get(CurrentInjectionPoint.class);
      this.clientProxyOptimization = ((WeldConfiguration)this.getServices().get(WeldConfiguration.class)).getBooleanProperty(ConfigurationKey.INJECTABLE_REFERENCE_OPTIMIZATION);
      this.requestInitializedEvent = LazyValueHolder.forSupplier(() -> {
         return FastEvent.of(Object.class, this, Literal.REQUEST);
      });
      this.requestBeforeDestroyedEvent = LazyValueHolder.forSupplier(() -> {
         return FastEvent.of(Object.class, this, javax.enterprise.context.BeforeDestroyed.Literal.REQUEST);
      });
      this.requestDestroyedEvent = LazyValueHolder.forSupplier(() -> {
         return FastEvent.of(Object.class, this, javax.enterprise.context.Destroyed.Literal.REQUEST);
      });
      this.validationFailureCallbacks = new CopyOnWriteArrayList();
      this.specializedBeans = new CopyOnWriteArrayList();
   }

   private Iterable createDynamicGlobalIterable(Function transform) {
      return Iterables.flatMap(this.managers, transform);
   }

   public String getContextId() {
      return this.contextId;
   }

   private Iterable createDynamicAccessibleIterable(Function transform) {
      return Iterables.concat(Iterables.flatMap(this.getAccessibleManagers(), transform), (Iterable)transform.apply(this));
   }

   public void addAccessibleBeanManager(BeanManagerImpl accessibleBeanManager) {
      this.accessibleManagers.add(accessibleBeanManager);
      this.beanResolver.clear();
      this.interceptorResolver.clear();
      this.decoratorResolver.clear();
      this.accessibleLenientObserverNotifier.clear();
   }

   public HashSet getAccessibleManagers() {
      return this.accessibleManagers;
   }

   public void addBean(Bean bean) {
      this.addBean(bean, this.enabledBeans, this.sharedBeans);
   }

   public void addBeans(Collection beans) {
      List beanList = new ArrayList(beans.size());
      List transitiveBeans = new ArrayList(beans.size());
      Iterator var4 = beans.iterator();

      while(var4.hasNext()) {
         Bean bean = (Bean)var4.next();
         this.addBean(bean, beanList, transitiveBeans);
      }

      this.enabledBeans.addAll(beanList);
      this.sharedBeans.addAll(transitiveBeans);
   }

   private boolean isConfiguratorBeanWithPriority(Bean bean) {
      return bean instanceof WeldBean && ((WeldBean)bean).getPriority() != null;
   }

   private void addBean(Bean bean, List beanList, List transitiveBeans) {
      if (this.beanSet.add(bean)) {
         if (bean.isAlternative() && !this.registry.isEnabledInAnyBeanDeployment(bean) && !(bean instanceof Prioritized) && !this.isConfiguratorBeanWithPriority(bean)) {
            BootstrapLogger.LOG.foundDisabledAlternative(bean);
         } else if (this.registry.isSpecializedInAnyBeanDeployment(bean)) {
            BootstrapLogger.LOG.foundSpecializedBean(bean);
         } else if (bean instanceof AbstractProducerBean && this.registry.isSpecializedInAnyBeanDeployment(((AbstractProducerBean)bean).getDeclaringBean())) {
            BootstrapLogger.LOG.foundProducerOfSpecializedBean(bean);
            this.specializedBeans.add(bean);
         } else {
            BootstrapLogger.LOG.foundBean(bean);
            beanList.add(bean);
            if (bean instanceof SessionBean) {
               SessionBean enterpriseBean = (SessionBean)bean;
               this.enterpriseBeans.put(enterpriseBean.getEjbDescriptor(), enterpriseBean);
            }

            if (bean instanceof PassivationCapable) {
               ((ContextualStore)this.getServices().get(ContextualStore.class)).putIfAbsent(bean);
            }

            this.registerBeanNamespace(bean);
            if (bean instanceof ExtensionBean || bean instanceof SessionBean || !(bean instanceof NewBean) && !(bean instanceof AbstractBuiltInBean)) {
               transitiveBeans.add(bean);
            }
         }
      }

   }

   public void addDecorator(Decorator bean) {
      this.decorators.add(bean);
      ((ContextualStore)this.getServices().get(ContextualStore.class)).putIfAbsent(bean);
      this.decoratorResolver.clear();
   }

   public Set resolveObserverMethods(Object event, Annotation... bindings) {
      return ImmutableSet.copyOf(this.globalStrictObserverNotifier.resolveObserverMethods(event.getClass(), (Annotation[])bindings).getAllObservers());
   }

   public void addInterceptor(Interceptor bean) {
      this.interceptors.add(bean);
      ((ContextualStore)this.getServices().get(ContextualStore.class)).putIfAbsent(bean);
      this.interceptorResolver.clear();
   }

   public ModuleEnablement getEnabled() {
      return this.enabled;
   }

   public void setEnabled(ModuleEnablement enabled) {
      this.enabled = enabled;
   }

   public boolean isBeanEnabled(Bean bean) {
      return Beans.isBeanEnabled(bean, this.getEnabled());
   }

   public Set getBeans(Type beanType, Annotation... qualifiers) {
      Resolvable resolvable = (new ResolvableBuilder(beanType, this)).addQualifiers(qualifiers).create();
      return (Set)this.beanResolver.resolve(resolvable, Reflections.isCacheable(qualifiers));
   }

   public Set getBeans(Type beanType, Set qualifiers) {
      return (Set)this.beanResolver.resolve((new ResolvableBuilder(beanType, this)).addQualifiers((Collection)qualifiers).create(), Reflections.isCacheable((Collection)qualifiers));
   }

   public Set getBeans(InjectionPoint injectionPoint) {
      boolean registerInjectionPoint = this.isRegisterableInjectionPoint(injectionPoint);
      ThreadLocalStack.ThreadLocalStackReference stack = this.currentInjectionPoint.pushConditionally(injectionPoint, registerInjectionPoint);

      Set var4;
      try {
         var4 = (Set)this.beanResolver.resolve((new ResolvableBuilder(injectionPoint, this)).create(), true);
      } finally {
         stack.pop();
      }

      return var4;
   }

   protected void registerBeanNamespace(Bean bean) {
      if (bean.getName() != null) {
         String[] parts = bean.getName().split("\\.");
         if (parts.length > 1) {
            for(int i = 0; i < parts.length - 1; ++i) {
               StringBuilder builder = new StringBuilder();

               for(int j = 0; j <= i; ++j) {
                  if (j > 0) {
                     builder.append('.');
                  }

                  builder.append(parts[j]);
               }

               this.namespaces.add(builder.toString());
            }
         }
      }

   }

   public Map getEnterpriseBeans() {
      return this.enterpriseBeans;
   }

   public List getBeans() {
      return WeldCollections.immutableListView(this.enabledBeans);
   }

   List getSharedBeans() {
      return WeldCollections.immutableListView(this.sharedBeans);
   }

   public List getDecorators() {
      return WeldCollections.immutableListView(this.decorators);
   }

   public List getInterceptors() {
      return WeldCollections.immutableListView(this.interceptors);
   }

   public Iterable getDynamicAccessibleBeans() {
      return this.createDynamicAccessibleIterable(new BeanTransform(this));
   }

   public Set getAccessibleBeans() {
      Set beans = new HashSet();
      beans.addAll(this.getBeans());
      Iterator var2 = this.getAccessibleManagers().iterator();

      while(var2.hasNext()) {
         BeanManagerImpl beanManager = (BeanManagerImpl)var2.next();
         beans.addAll(beanManager.getSharedBeans());
      }

      return beans;
   }

   public Iterable getDynamicAccessibleInterceptors() {
      return this.createDynamicAccessibleIterable(BeanManagerImpl::getInterceptors);
   }

   public Iterable getDynamicAccessibleDecorators() {
      return this.createDynamicAccessibleIterable(BeanManagerImpl::getDecorators);
   }

   public void addContext(Context context) {
      Class scope = context.getScope();
      if (this.isPassivatingScope(scope)) {
         context = PassivatingContextWrapper.wrap(context, (ContextualStore)this.services.get(ContextualStore.class));
      }

      List contextList = (List)this.contexts.get(scope);
      if (contextList == null) {
         contextList = new CopyOnWriteArrayList();
         this.contexts.put(scope, contextList);
      }

      ((List)contextList).add(context);
   }

   public void addObserver(ObserverMethod observer) {
      this.observers.add(observer);
   }

   public void fireEvent(Object event, Annotation... qualifiers) {
      Preconditions.checkArgumentNotNull(event, "event");
      Type eventType = Types.getCanonicalType(event.getClass());
      EventMetadata metadata = new EventMetadataImpl(eventType, (InjectionPoint)null, qualifiers);
      this.globalStrictObserverNotifier.fireEvent((Object)event, (EventMetadata)metadata, qualifiers);
   }

   public Context getContext(Class scopeType) {
      Context activeContext = this.internalGetContext(scopeType);
      if (activeContext == null) {
         throw BeanManagerLogger.LOG.contextNotActive(scopeType.getName());
      } else {
         return activeContext;
      }
   }

   public Context getUnwrappedContext(Class scopeType) {
      return PassivatingContextWrapper.unwrap(this.getContext(scopeType));
   }

   public boolean isContextActive(Class scopeType) {
      return this.internalGetContext(scopeType) != null;
   }

   private Context internalGetContext(Class scopeType) {
      Context activeContext = null;
      List ctx = (List)this.contexts.get(scopeType);
      if (ctx == null) {
         return null;
      } else {
         Iterator var4 = ctx.iterator();

         while(var4.hasNext()) {
            Context context = (Context)var4.next();
            if (context.isActive()) {
               if (activeContext != null) {
                  throw BeanManagerLogger.LOG.duplicateActiveContexts(scopeType.getName());
               }

               activeContext = context;
            }
         }

         return activeContext;
      }
   }

   public Object getReference(Bean bean, Type requestedType, CreationalContext creationalContext, boolean noProxy) {
      if (creationalContext instanceof CreationalContextImpl) {
         creationalContext = ((CreationalContextImpl)creationalContext).getCreationalContext(bean);
      }

      if (!noProxy && this.isProxyRequired(bean)) {
         if (creationalContext == null && ContextualInstance.getIfExists(bean, this) == null) {
            return null;
         } else {
            return requestedType == null ? this.clientProxyProvider.getClientProxy(bean) : this.clientProxyProvider.getClientProxy(bean, requestedType);
         }
      } else {
         return ContextualInstance.get((Bean)bean, this, (CreationalContext)creationalContext);
      }
   }

   private boolean isProxyRequired(Bean bean) {
      return bean instanceof RIBean ? ((RIBean)bean).isProxyRequired() : this.isNormalScope(bean.getScope());
   }

   public Object getReference(Bean bean, Type requestedType, CreationalContext creationalContext) {
      Preconditions.checkArgumentNotNull(bean, "bean");
      Preconditions.checkArgumentNotNull(requestedType, "requestedType");
      Preconditions.checkArgumentNotNull(creationalContext, "creationalContext");
      if (!BeanTypeAssignabilityRules.instance().matches(requestedType, bean.getTypes())) {
         throw BeanManagerLogger.LOG.specifiedTypeNotBeanType(requestedType, bean);
      } else {
         ThreadLocalStack.ThreadLocalStackReference stack = this.currentInjectionPoint.push(EmptyInjectionPoint.INSTANCE);

         Object var5;
         try {
            var5 = this.getReference(bean, requestedType, creationalContext, false);
         } finally {
            stack.pop();
         }

         return var5;
      }
   }

   /** @deprecated */
   @Deprecated
   public Object getReference(InjectionPoint injectionPoint, Bean resolvedBean, CreationalContext creationalContext) {
      return this.getInjectableReference(injectionPoint, resolvedBean, creationalContext);
   }

   public Object getInjectableReference(InjectionPoint injectionPoint, Bean resolvedBean, CreationalContext creationalContext) {
      Preconditions.checkArgumentNotNull(resolvedBean, "resolvedBean");
      Preconditions.checkArgumentNotNull(creationalContext, "creationalContext");
      boolean registerInjectionPoint = this.isRegisterableInjectionPoint(injectionPoint);
      boolean delegateInjectionPoint = injectionPoint != null && injectionPoint.isDelegate();
      ThreadLocalStack.ThreadLocalStackReference stack = this.currentInjectionPoint.pushConditionally(injectionPoint, registerInjectionPoint);

      try {
         Type requestedType = null;
         if (injectionPoint != null) {
            requestedType = injectionPoint.getType();
         }

         if (this.clientProxyOptimization && injectionPoint != null && injectionPoint.getBean() != null) {
            CreationalContextImpl weldCreationalContext = null;
            Bean bean = injectionPoint.getBean();
            if (!bean.equals(resolvedBean)) {
               if (creationalContext instanceof CreationalContextImpl) {
                  weldCreationalContext = (CreationalContextImpl)creationalContext;
               }

               if (weldCreationalContext != null && Dependent.class.equals(bean.getScope()) && this.isNormalScope(resolvedBean.getScope())) {
                  bean = this.findNormalScopedDependant(weldCreationalContext);
               }

               if (InjectionPoints.isInjectableReferenceLookupOptimizationAllowed(bean, resolvedBean)) {
                  Object existinInstance;
                  if (weldCreationalContext != null) {
                     Object incompleteInstance = weldCreationalContext.getIncompleteInstance(resolvedBean);
                     if (incompleteInstance != null) {
                        existinInstance = incompleteInstance;
                        return existinInstance;
                     }
                  }

                  Context context = this.internalGetContext(resolvedBean.getScope());
                  if (context != null) {
                     existinInstance = context.get((Contextual)Reflections.cast(resolvedBean));
                     if (existinInstance != null) {
                        Object var12 = existinInstance;
                        return var12;
                     }
                  }
               }
            }
         }

         Object var16 = this.getReference(resolvedBean, requestedType, creationalContext, delegateInjectionPoint);
         return var16;
      } finally {
         stack.pop();
      }
   }

   public Object getInjectableReference(InjectionPoint injectionPoint, CreationalContext creationalContext) {
      if (injectionPoint.isDelegate()) {
         return DecorationHelper.peek().getNextDelegate(injectionPoint, creationalContext);
      } else {
         Bean resolvedBean = this.getBean((new ResolvableBuilder(injectionPoint, this)).create());
         return this.getInjectableReference(injectionPoint, resolvedBean, creationalContext);
      }
   }

   public Bean getBean(Resolvable resolvable) {
      Bean bean = (Bean)Reflections.cast(this.resolve((Set)this.beanResolver.resolve(resolvable, true)));
      if (bean == null) {
         throw BeanManagerLogger.LOG.unresolvableElement(resolvable);
      } else {
         return bean;
      }
   }

   public Set getBeans(String name) {
      return this.nameBasedResolver.resolve(name);
   }

   public List resolveDecorators(Set types, Annotation... qualifiers) {
      this.checkResolveDecoratorsArguments(types);
      return (List)this.decoratorResolver.resolve((new DecoratorResolvableBuilder(this)).addTypes(types).addQualifiers(qualifiers).create(), Reflections.isCacheable(qualifiers));
   }

   public List resolveDecorators(Set types, Set qualifiers) {
      this.checkResolveDecoratorsArguments(types);
      return (List)this.decoratorResolver.resolve((new DecoratorResolvableBuilder(this)).addTypes(types).addQualifiers((Collection)qualifiers).create(), true);
   }

   private void checkResolveDecoratorsArguments(Set types) {
      if (types.isEmpty()) {
         throw BeanManagerLogger.LOG.noDecoratorTypes();
      }
   }

   public List resolveInterceptors(InterceptionType type, Annotation... interceptorBindings) {
      if (interceptorBindings.length == 0) {
         throw BeanManagerLogger.LOG.interceptorBindingsEmpty();
      } else {
         Annotation[] var3 = interceptorBindings;
         int var4 = interceptorBindings.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Annotation annotation = var3[var5];
            if (!this.isInterceptorBinding(annotation.annotationType())) {
               throw BeanManagerLogger.LOG.notInterceptorBindingType(annotation);
            }
         }

         Set flattenedInterceptorBindings = Interceptors.flattenInterceptorBindings((EnhancedAnnotatedType)null, this, Arrays.asList(interceptorBindings), true, true);
         return this.resolveInterceptors(type, (Collection)flattenedInterceptorBindings);
      }
   }

   public List resolveInterceptors(InterceptionType type, Collection interceptorBindings) {
      InterceptorResolvable interceptorResolvable = (new InterceptorResolvableBuilder(Object.class, this)).setInterceptionType(type).addQualifiers(interceptorBindings).create();
      return (List)this.interceptorResolver.resolve(interceptorResolvable, Reflections.isCacheable(interceptorBindings));
   }

   public TypeSafeBeanResolver getBeanResolver() {
      return this.beanResolver;
   }

   public TypeSafeDecoratorResolver getDecoratorResolver() {
      return this.decoratorResolver;
   }

   public TypeSafeInterceptorResolver getInterceptorResolver() {
      return this.interceptorResolver;
   }

   public NameBasedResolver getNameBasedResolver() {
      return this.nameBasedResolver;
   }

   public ObserverNotifier getAccessibleLenientObserverNotifier() {
      return this.accessibleLenientObserverNotifier;
   }

   public ObserverNotifier getGlobalLenientObserverNotifier() {
      return this.globalLenientObserverNotifier;
   }

   public ObserverNotifier getGlobalStrictObserverNotifier() {
      return this.globalStrictObserverNotifier;
   }

   public String toString() {
      return "Weld BeanManager for " + this.getId() + " [bean count=" + this.getBeans().size() + "]";
   }

   @SuppressFBWarnings({"EQ_CHECK_FOR_OPERAND_NOT_COMPATIBLE_WITH_THIS"})
   public boolean equals(Object obj) {
      if (obj instanceof ForwardingBeanManager) {
         ForwardingBeanManager proxy = (ForwardingBeanManager)obj;
         obj = proxy.delegate();
      }

      if (obj instanceof BeanManagerImpl) {
         BeanManagerImpl that = (BeanManagerImpl)obj;
         return this.getId().equals(that.getId());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.getId().hashCode();
   }

   public ServiceRegistry getServices() {
      return this.services;
   }

   protected Object readResolve() throws ObjectStreamException {
      return Container.instance(this.contextId).getBeanManager(this.id);
   }

   public ClientProxyProvider getClientProxyProvider() {
      return this.clientProxyProvider;
   }

   protected Map getContexts() {
      return this.contexts;
   }

   protected List getNamespaces() {
      return this.namespaces;
   }

   public Iterable getDynamicAccessibleNamespaces() {
      return this.createDynamicAccessibleIterable(BeanManagerImpl::getNamespaces);
   }

   public List getAccessibleNamespaces() {
      List namespaces = new ArrayList();
      namespaces.addAll(this.getNamespaces());
      Iterator var2 = this.getAccessibleManagers().iterator();

      while(var2.hasNext()) {
         BeanManagerImpl beanManagerImpl = (BeanManagerImpl)var2.next();
         namespaces.addAll(beanManagerImpl.getNamespaces());
      }

      return namespaces;
   }

   public String getId() {
      return this.id;
   }

   public List getObservers() {
      return this.observers;
   }

   public InjectionTarget createInjectionTarget(AnnotatedType type) {
      return this.getInjectionTargetFactory(type).createInjectionTarget((Bean)null);
   }

   public InjectionTarget createInjectionTarget(EjbDescriptor descriptor) {
      if (descriptor.isMessageDriven()) {
         AnnotatedType type = (AnnotatedType)Reflections.cast(this.createAnnotatedType(descriptor.getBeanClass()));
         return this.getLocalInjectionTargetFactory(type).createMessageDrivenInjectionTarget(descriptor);
      } else {
         InjectionTarget injectionTarget = this.getBean(descriptor).getProducer();
         return injectionTarget;
      }
   }

   public void validate(InjectionPoint ij) {
      try {
         ((Validator)this.getServices().get(Validator.class)).validateInjectionPoint(ij, this);
      } catch (DeploymentException var3) {
         throw new InjectionException(var3.getLocalizedMessage(), var3.getCause());
      }
   }

   public Set getInterceptorBindingDefinition(Class bindingType) {
      InterceptorBindingModel model = ((MetaAnnotationStore)this.getServices().get(MetaAnnotationStore.class)).getInterceptorBindingModel(bindingType);
      if (model.isValid()) {
         return model.getMetaAnnotations();
      } else {
         throw BeanManagerLogger.LOG.notInterceptorBindingType(bindingType);
      }
   }

   public Bean getPassivationCapableBean(String id) {
      return (Bean)((ContextualStore)this.getServices().get(ContextualStore.class)).getContextual(id);
   }

   public Bean getPassivationCapableBean(BeanIdentifier identifier) {
      return (Bean)((ContextualStore)this.getServices().get(ContextualStore.class)).getContextual(identifier);
   }

   public Set getStereotypeDefinition(Class stereotype) {
      StereotypeModel model = ((MetaAnnotationStore)this.getServices().get(MetaAnnotationStore.class)).getStereotype(stereotype);
      if (model.isValid()) {
         return model.getMetaAnnotations();
      } else {
         throw BeanManagerLogger.LOG.notStereotype(stereotype);
      }
   }

   public boolean isQualifier(Class annotationType) {
      return ((MetaAnnotationStore)this.getServices().get(MetaAnnotationStore.class)).getBindingTypeModel(annotationType).isValid();
   }

   public boolean isInterceptorBinding(Class annotationType) {
      return ((MetaAnnotationStore)this.getServices().get(MetaAnnotationStore.class)).getInterceptorBindingModel(annotationType).isValid();
   }

   public boolean isNormalScope(Class annotationType) {
      ScopeModel scope = ((MetaAnnotationStore)this.getServices().get(MetaAnnotationStore.class)).getScopeModel(annotationType);
      return scope.isValid() && scope.isNormal();
   }

   public boolean isPassivatingScope(Class annotationType) {
      ScopeModel scope = ((MetaAnnotationStore)this.getServices().get(MetaAnnotationStore.class)).getScopeModel(annotationType);
      return scope.isValid() && scope.isPassivating();
   }

   public boolean isScope(Class annotationType) {
      return ((MetaAnnotationStore)this.getServices().get(MetaAnnotationStore.class)).getScopeModel(annotationType).isValid();
   }

   public boolean isStereotype(Class annotationType) {
      return ((MetaAnnotationStore)this.getServices().get(MetaAnnotationStore.class)).getStereotype(annotationType).isValid();
   }

   public ELResolver getELResolver() {
      if (this.weldELResolver == null) {
         throw BootstrapLogger.LOG.unspecifiedRequiredService(ExpressionLanguageSupport.class, this.id);
      } else {
         return this.weldELResolver;
      }
   }

   public ExpressionFactory wrapExpressionFactory(ExpressionFactory expressionFactory) {
      return ((ExpressionLanguageSupport)this.services.getOptional(ExpressionLanguageSupport.class).orElseThrow(() -> {
         return BootstrapLogger.LOG.unspecifiedRequiredService(ExpressionLanguageSupport.class, this.id);
      })).wrapExpressionFactory(expressionFactory);
   }

   public WeldCreationalContext createCreationalContext(Contextual contextual) {
      return new CreationalContextImpl(contextual);
   }

   public AnnotatedType createAnnotatedType(Class type) {
      String bdaId = BeanManagerLookupService.lookupBeanManager(type, this).getId();
      return ((ClassTransformer)this.getServices().get(ClassTransformer.class)).getBackedAnnotatedType(type, bdaId);
   }

   public EnhancedAnnotatedType createEnhancedAnnotatedType(Class type) {
      String bdaId = BeanManagerLookupService.lookupBeanManager(type, this).getId();
      return ((ClassTransformer)this.getServices().get(ClassTransformer.class)).getEnhancedAnnotatedType(type, bdaId);
   }

   public AnnotatedType createAnnotatedType(Class type, String id) {
      return ((ClassTransformer)this.getServices().get(ClassTransformer.class)).getBackedAnnotatedType(type, BeanManagerLookupService.lookupBeanManager(type, this).getId(), id);
   }

   public void disposeAnnotatedType(Class type, String id) {
      ((ClassTransformer)this.getServices().get(ClassTransformer.class)).disposeBackedAnnotatedType(type, BeanManagerLookupService.lookupBeanManager(type, this).getId(), id);
   }

   public Bean resolve(Set beans) {
      if (beans != null && !beans.isEmpty()) {
         Set resolvedBeans = this.beanResolver.resolve(beans);
         if (resolvedBeans.size() == 1) {
            return (Bean)resolvedBeans.iterator().next();
         } else if (resolvedBeans.size() == 0) {
            return null;
         } else {
            throw BeanManagerLogger.LOG.ambiguousBeansForDependency(WeldCollections.toMultiRowString(beans));
         }
      } else {
         return null;
      }
   }

   public EjbDescriptor getEjbDescriptor(String beanName) {
      return ((EjbSupport)this.getServices().get(EjbSupport.class)).getEjbDescriptor(beanName);
   }

   public SessionBean getBean(EjbDescriptor descriptor) {
      return (SessionBean)Reflections.cast(this.getEnterpriseBeans().get(descriptor));
   }

   public void cleanup() {
      this.services.cleanup();
      this.accessibleManagers.clear();
      this.managers.clear();
      this.beanResolver.clear();
      this.enabledBeans.clear();
      this.clientProxyProvider.clear();
      this.contexts.clear();
      this.decoratorResolver.clear();
      this.decorators.clear();
      this.enterpriseBeans.clear();
      this.interceptorResolver.clear();
      this.interceptors.clear();
      this.nameBasedResolver.clear();
      this.namespaces.clear();
      this.accessibleLenientObserverNotifier.clear();
      this.observers.clear();
   }

   public void cleanupAfterBoot() {
      if (this.beanSet != null) {
         this.beanSet.clear();
         this.beanSet = null;
      }

      this.validationFailureCallbacks.clear();
      boolean isOptimizedCleanupAllowed = ((WeldConfiguration)this.getServices().get(WeldConfiguration.class)).getBooleanProperty(ConfigurationKey.ALLOW_OPTIMIZED_CLEANUP);
      if (!this.observers.isEmpty()) {
         this.observers.removeIf((o) -> {
            return o instanceof ContainerLifecycleEventObserverMethod && Observers.CONTAINER_LIFECYCLE_EVENT_TYPES.contains(Reflections.getRawType(o.getObservedType())) && !BeforeShutdown.class.equals(Reflections.getRawType(o.getObservedType())) && (isOptimizedCleanupAllowed || !ProcessInjectionPoint.class.equals(Reflections.getRawType(o.getObservedType())) && !ProcessInjectionTarget.class.equals(Reflections.getRawType(o.getObservedType())));
         });
      }

      if (isOptimizedCleanupAllowed) {
         this.removeUnusedBeans();
         if (!this.specializedBeans.isEmpty()) {
            ((ContextualStoreImpl)this.getServices().get(ContextualStore.class)).removeAll(this.specializedBeans);
            this.cleanupBeansAfterBoot(this.specializedBeans);
            this.specializedBeans.clear();
         }
      }

   }

   public ConcurrentMap getInterceptorModelRegistry() {
      return this.interceptorModelRegistry;
   }

   public InterceptorMetadataReader getInterceptorMetadataReader() {
      return this.interceptorMetadataReader;
   }

   public InjectionTarget fireProcessInjectionTarget(AnnotatedType annotatedType) {
      return this.fireProcessInjectionTarget(annotatedType, this.createInjectionTarget(annotatedType));
   }

   public InjectionTarget fireProcessInjectionTarget(AnnotatedType annotatedType, InjectionTarget injectionTarget) {
      return ((ContainerLifecycleEvents)this.services.get(ContainerLifecycleEvents.class)).fireProcessInjectionTarget(this, annotatedType, injectionTarget);
   }

   public Set extractInterceptorBindingsForQualifierInstance(Iterable annotations) {
      Set foundInterceptionBindingTypes = new HashSet();
      Iterator var3 = annotations.iterator();

      while(var3.hasNext()) {
         QualifierInstance annotation = (QualifierInstance)var3.next();
         if (this.isInterceptorBinding(annotation.getAnnotationClass())) {
            foundInterceptionBindingTypes.add(annotation);
         }
      }

      return foundInterceptionBindingTypes;
   }

   public Instance instance() {
      return this.getInstance(this.createCreationalContext((Contextual)null));
   }

   public WeldEvent event() {
      return EventImpl.of(BeanManagerImpl.EventInjectionPoint.INSTANCE, this);
   }

   public WeldInstance getInstance(CreationalContext ctx) {
      return (WeldInstance)Reflections.cast(InstanceImpl.of(BeanManagerImpl.InstanceInjectionPoint.INSTANCE, ctx, this));
   }

   public BeanAttributes createBeanAttributes(AnnotatedType type) {
      EnhancedAnnotatedType clazz = ((ClassTransformer)this.services.get(ClassTransformer.class)).getEnhancedAnnotatedType(type, this.getId());
      return ((EjbSupport)this.services.get(EjbSupport.class)).isEjb(type.getJavaClass()) ? ((EjbSupport)this.services.get(EjbSupport.class)).createSessionBeanAttributes(clazz, this) : BeanAttributesFactory.forBean(clazz, this);
   }

   public BeanAttributes createBeanAttributes(AnnotatedMember member) {
      return this.internalCreateBeanAttributes(member);
   }

   public BeanAttributes internalCreateBeanAttributes(AnnotatedMember member) {
      EnhancedAnnotatedMember weldMember = null;
      if (!(member instanceof AnnotatedField) && !(member instanceof AnnotatedMethod)) {
         throw BeanManagerLogger.LOG.cannotCreateBeanAttributesForIncorrectAnnotatedMember(member);
      } else {
         weldMember = ((MemberTransformer)this.services.get(MemberTransformer.class)).loadEnhancedMember(member, this.getId());
         return BeanAttributesFactory.forBean(weldMember, this);
      }
   }

   public Bean createBean(BeanAttributes attributes, Class beanClass, InjectionTargetFactory injectionTargetFactory) {
      return SyntheticBeanFactory.create(attributes, beanClass, injectionTargetFactory, this);
   }

   public Bean createBean(BeanAttributes attributes, Class beanClass, ProducerFactory producerFactory) {
      return SyntheticBeanFactory.create(attributes, beanClass, producerFactory, this);
   }

   public FieldInjectionPointAttributes createInjectionPoint(AnnotatedField field) {
      AnnotatedTypeValidator.validateAnnotatedMember(field);
      return (FieldInjectionPointAttributes)this.validateInjectionPoint(this.createFieldInjectionPoint(field));
   }

   private FieldInjectionPointAttributes createFieldInjectionPoint(AnnotatedField field) {
      EnhancedAnnotatedField enhancedField = (EnhancedAnnotatedField)((MemberTransformer)this.services.get(MemberTransformer.class)).loadEnhancedMember(field, this.getId());
      return InferringFieldInjectionPointAttributes.of(enhancedField, (Bean)null, field.getDeclaringType().getJavaClass(), this);
   }

   public ParameterInjectionPointAttributes createInjectionPoint(AnnotatedParameter parameter) {
      AnnotatedTypeValidator.validateAnnotatedParameter(parameter);
      EnhancedAnnotatedParameter enhancedParameter = ((MemberTransformer)this.services.get(MemberTransformer.class)).loadEnhancedParameter(parameter, this.getId());
      return (ParameterInjectionPointAttributes)this.validateInjectionPoint(InferringParameterInjectionPointAttributes.of(enhancedParameter, (Bean)null, parameter.getDeclaringCallable().getDeclaringType().getJavaClass(), this));
   }

   private InjectionPoint validateInjectionPoint(InjectionPoint injectionPoint) {
      try {
         ((Validator)this.services.get(Validator.class)).validateInjectionPointForDefinitionErrors(injectionPoint, (Bean)null, this);
         return injectionPoint;
      } catch (DefinitionException var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   public Extension getExtension(Class extensionClass) {
      Bean bean = null;
      Iterator var3 = this.getBeans(extensionClass, (Annotation[])()).iterator();

      while(var3.hasNext()) {
         Bean b = (Bean)var3.next();
         if (b.getBeanClass().equals(extensionClass)) {
            bean = b;
         }
      }

      if (bean == null) {
         throw BeanManagerLogger.LOG.noInstanceOfExtension(extensionClass);
      } else {
         return (Extension)extensionClass.cast(bean.create((CreationalContext)null));
      }
   }

   public InterceptionFactory createInterceptionFactory(CreationalContext ctx, Class clazz) {
      return InterceptionFactoryImpl.of(this, ctx, this.createAnnotatedType(clazz));
   }

   public Event getEvent() {
      return this.event();
   }

   private boolean isRegisterableInjectionPoint(InjectionPoint ip) {
      return ip != null && !ip.getType().equals(InjectionPoint.class) && !ip.isDelegate();
   }

   public ContainerLifecycleEvents getContainerLifecycleEvents() {
      return this.containerLifecycleEvents;
   }

   public boolean areQualifiersEquivalent(Annotation qualifier1, Annotation qualifier2) {
      return Bindings.areQualifiersEquivalent(qualifier1, qualifier2, (MetaAnnotationStore)this.services.get(MetaAnnotationStore.class));
   }

   public boolean areInterceptorBindingsEquivalent(Annotation interceptorBinding1, Annotation interceptorBinding2) {
      return Bindings.areInterceptorBindingsEquivalent(interceptorBinding1, interceptorBinding2, (MetaAnnotationStore)this.services.get(MetaAnnotationStore.class));
   }

   public int getQualifierHashCode(Annotation qualifier) {
      return Bindings.getQualifierHashCode(qualifier, (MetaAnnotationStore)this.services.get(MetaAnnotationStore.class));
   }

   public int getInterceptorBindingHashCode(Annotation interceptorBinding) {
      return Bindings.getInterceptorBindingHashCode(interceptorBinding, (MetaAnnotationStore)this.services.get(MetaAnnotationStore.class));
   }

   public InjectionTargetFactoryImpl getLocalInjectionTargetFactory(AnnotatedType type) {
      return new InjectionTargetFactoryImpl(type, this);
   }

   public WeldInjectionTargetFactory getInjectionTargetFactory(AnnotatedType type) {
      AnnotatedTypeValidator.validateAnnotatedType(type);
      BeanManagerImpl manager = BeanManagerLookupService.lookupBeanManager(type.getJavaClass(), this);
      return new InjectionTargetFactoryImpl(type, manager);
   }

   public FieldProducerFactory getProducerFactory(AnnotatedField field, Bean declaringBean) {
      BeanManagerImpl manager = BeanManagerLookupService.lookupBeanManager(field.getDeclaringType().getJavaClass(), this);
      return new FieldProducerFactory(field, declaringBean, manager);
   }

   public MethodProducerFactory getProducerFactory(AnnotatedMethod method, Bean declaringBean) {
      BeanManagerImpl manager = BeanManagerLookupService.lookupBeanManager(method.getDeclaringType().getJavaClass(), this);
      return new MethodProducerFactory(method, declaringBean, manager);
   }

   public WeldInjectionTargetBuilder createInjectionTargetBuilder(AnnotatedType type) {
      return new WeldInjectionTargetBuilderImpl(type, this);
   }

   public WeldInstance createInstance() {
      return this.getInstance(this.createCreationalContext((Contextual)null));
   }

   private Bean findNormalScopedDependant(CreationalContextImpl weldCreationalContext) {
      CreationalContextImpl parent = weldCreationalContext.getParentCreationalContext();
      if (parent != null && parent.getContextual() instanceof Bean) {
         Bean bean = (Bean)parent.getContextual();
         return this.isNormalScope(bean.getScope()) ? bean : this.findNormalScopedDependant(parent);
      } else {
         return null;
      }
   }

   public BeanManagerImpl unwrap() {
      return this;
   }

   public void fireRequestContextInitialized(Object payload) {
      ((FastEvent)this.requestInitializedEvent.get()).fire(payload);
   }

   public void fireRequestContextBeforeDestroyed(Object payload) {
      ((FastEvent)this.requestBeforeDestroyedEvent.get()).fire(payload);
   }

   public void fireRequestContextDestroyed(Object payload) {
      ((FastEvent)this.requestDestroyedEvent.get()).fire(payload);
   }

   public void addValidationFailureCallback(BiConsumer callback) {
      this.validationFailureCallbacks.add(callback);
   }

   public void validationFailed(Exception failure, Environment environment) {
      Iterator var3 = this.validationFailureCallbacks.iterator();

      while(var3.hasNext()) {
         BiConsumer callback = (BiConsumer)var3.next();

         try {
            callback.accept(failure, environment);
         } catch (Throwable var6) {
            BootstrapLogger.LOG.catchingDebug(var6);
         }
      }

   }

   public Collection getScopes() {
      return Collections.unmodifiableCollection(this.contexts.keySet());
   }

   private void removeUnusedBeans() {
      String excludeTypeProperty = ((WeldConfiguration)this.getServices().get(WeldConfiguration.class)).getStringProperty(ConfigurationKey.UNUSED_BEANS_EXCLUDE_TYPE);
      if (ConfigurationKey.UnusedBeans.isEnabled(excludeTypeProperty)) {
         String excludeAnnotationProperty = ((WeldConfiguration)this.getServices().get(WeldConfiguration.class)).getStringProperty(ConfigurationKey.UNUSED_BEANS_EXCLUDE_ANNOTATION);
         Pattern excludeAnnotation = excludeAnnotationProperty.isEmpty() ? null : Pattern.compile(excludeAnnotationProperty);
         Pattern excludeType = ConfigurationKey.UnusedBeans.excludeNone(excludeTypeProperty) ? null : Pattern.compile(excludeTypeProperty);
         Validator validator = (Validator)this.getServices().get(Validator.class);
         SetMultimap beanToDeclaredProducers = SetMultimap.newSetMultimap();
         SetMultimap beanToDeclaredObservers = SetMultimap.newSetMultimap();
         Iterator var8 = this.enabledBeans.iterator();

         while(var8.hasNext()) {
            Bean bean = (Bean)var8.next();
            if (bean instanceof AbstractProducerBean) {
               AbstractProducerBean producer = (AbstractProducerBean)bean;
               beanToDeclaredProducers.put(producer.getDeclaringBean(), producer);
            }
         }

         var8 = this.observers.iterator();

         while(var8.hasNext()) {
            ObserverMethod observerMethod = (ObserverMethod)var8.next();
            if (observerMethod instanceof ObserverMethodImpl) {
               ObserverMethodImpl observerMethodImpl = (ObserverMethodImpl)observerMethod;
               beanToDeclaredObservers.put(observerMethodImpl.getDeclaringBean(), observerMethod);
            }
         }

         Set removable = new HashSet();
         Set unusedProducers = new HashSet();
         WeldUnusedMetadataExtension metadataExtension = this.getUnusedMetadataExtension();
         Iterator var11 = this.enabledBeans.iterator();

         while(true) {
            Bean bean;
            do {
               while(true) {
                  do {
                     do {
                        do {
                           do {
                              do {
                                 do {
                                    do {
                                       do {
                                          do {
                                             do {
                                                if (!var11.hasNext()) {
                                                   if (!unusedProducers.isEmpty()) {
                                                      var11 = beanToDeclaredProducers.keySet().iterator();

                                                      while(var11.hasNext()) {
                                                         bean = (Bean)var11.next();
                                                         bean = Beans.unwrap(bean);
                                                         if (unusedProducers.containsAll(beanToDeclaredProducers.get(bean))) {
                                                            BootstrapLogger.LOG.dropUnusedBeanMetadata(bean);
                                                            removable.add(bean);
                                                         }
                                                      }
                                                   }

                                                   if (!removable.isEmpty()) {
                                                      this.enabledBeans.removeAll(removable);
                                                      this.sharedBeans.removeAll(removable);
                                                      this.beanResolver.clear();
                                                      this.cleanupBeansAfterBoot(removable);
                                                      ((ContextualStoreImpl)this.getServices().get(ContextualStore.class)).removeAll(removable);
                                                      ((ClassTransformer)this.getServices().get(ClassTransformer.class)).removeAll(removable);
                                                   }

                                                   return;
                                                }

                                                bean = (Bean)var11.next();
                                                bean = Beans.unwrap(bean);
                                             } while(bean instanceof AbstractBuiltInBean);
                                          } while(bean instanceof ExtensionBean);
                                       } while(bean instanceof Interceptor);
                                    } while(bean instanceof Decorator);
                                 } while(bean instanceof SessionBean);
                              } while(bean.getName() != null);
                           } while(excludeType != null && excludeType.matcher(bean.getBeanClass().getName()).matches());
                        } while(beanToDeclaredObservers.containsKey(bean));
                     } while(beanToDeclaredProducers.containsKey(bean));
                  } while(validator.isResolved(bean));

                  if (metadataExtension == null || !metadataExtension.isInjectedByEEComponent(bean, this) && !metadataExtension.isInstanceResolvedBean(bean, this)) {
                     break;
                  }
               }
            } while(excludeAnnotation != null && this.hasExcludeAnnotation(bean, excludeAnnotation));

            if (bean instanceof AbstractProducerBean) {
               unusedProducers.add(bean);
            }

            BootstrapLogger.LOG.dropUnusedBeanMetadata(bean);
            removable.add(bean);
         }
      }
   }

   private void cleanupBeansAfterBoot(Iterable beans) {
      Iterator var2 = beans.iterator();

      while(var2.hasNext()) {
         Bean bean = (Bean)var2.next();
         if (bean instanceof RIBean) {
            RIBean riBean = (RIBean)bean;
            riBean.cleanupAfterBoot();
         }
      }

   }

   private boolean hasExcludeAnnotation(Bean bean, Pattern excludeAnnotation) {
      if (bean instanceof AbstractClassBean) {
         return this.hasExcludeAnnotation((AbstractClassBean)bean, excludeAnnotation);
      } else {
         return bean instanceof AbstractProducerBean ? this.hasExcludeAnnotation(((AbstractProducerBean)bean).getDeclaringBean(), excludeAnnotation) : false;
      }
   }

   private boolean hasExcludeAnnotation(AbstractClassBean classBean, Pattern excludeAnnotation) {
      AnnotatedType annotatedType = classBean.getAnnotated();
      return this.hasExcludeAnnotation((Annotated)annotatedType, excludeAnnotation) || this.anyHasExcludeAnnotation(annotatedType.getMethods(), excludeAnnotation) || this.anyHasExcludeAnnotation(annotatedType.getFields(), excludeAnnotation) || this.anyHasExcludeAnnotation(annotatedType.getConstructors(), excludeAnnotation);
   }

   private boolean anyHasExcludeAnnotation(Set annotatedSet, Pattern excludeAnnotation) {
      if (annotatedSet.isEmpty()) {
         return false;
      } else {
         Iterator var3 = annotatedSet.iterator();

         Annotated annotated;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            annotated = (Annotated)var3.next();
         } while(!this.hasExcludeAnnotation(annotated, excludeAnnotation));

         return true;
      }
   }

   private boolean hasExcludeAnnotation(Annotated annotated, Pattern excludeAnnotation) {
      Iterator var3 = annotated.getAnnotations().iterator();

      Annotation annotation;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         annotation = (Annotation)var3.next();
      } while(!excludeAnnotation.matcher(annotation.annotationType().getName()).matches());

      return true;
   }

   private WeldUnusedMetadataExtension getUnusedMetadataExtension() {
      try {
         return (WeldUnusedMetadataExtension)this.getExtension(WeldUnusedMetadataExtension.class);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   private static class EventInjectionPoint implements InjectionPoint, Serializable {
      private static final long serialVersionUID = 8947703643709929295L;
      private static final InjectionPoint INSTANCE = new EventInjectionPoint();
      private transient Type type;

      public Type getType() {
         if (this.type == null) {
            this.type = (new TypeLiteral() {
            }).getType();
         }

         return this.type;
      }

      public Set getQualifiers() {
         return Collections.emptySet();
      }

      public Bean getBean() {
         return null;
      }

      public Member getMember() {
         return null;
      }

      public Annotated getAnnotated() {
         return null;
      }

      public boolean isDelegate() {
         return false;
      }

      public boolean isTransient() {
         return false;
      }
   }

   private static class InstanceInjectionPoint implements InjectionPoint, Serializable {
      private static final long serialVersionUID = -2952474261839554286L;
      private static final InjectionPoint INSTANCE = new InstanceInjectionPoint();
      private transient Type type;

      public Type getType() {
         if (this.type == null) {
            this.type = (new TypeLiteral() {
            }).getType();
         }

         return this.type;
      }

      public Set getQualifiers() {
         return Collections.emptySet();
      }

      public Bean getBean() {
         return null;
      }

      public Member getMember() {
         return null;
      }

      public Annotated getAnnotated() {
         return null;
      }

      public boolean isDelegate() {
         return false;
      }

      public boolean isTransient() {
         return false;
      }
   }
}
