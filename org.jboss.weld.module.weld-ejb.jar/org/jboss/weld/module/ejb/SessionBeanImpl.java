package org.jboss.weld.module.ejb;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.decorator.Decorator;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.interceptor.Interceptor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.enhanced.jlr.MethodSignatureImpl;
import org.jboss.weld.bean.AbstractBean;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.bean.interceptor.InterceptorBindingsAdapter;
import org.jboss.weld.bean.proxy.Marker;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.ejb.api.SessionObjectReference;
import org.jboss.weld.ejb.spi.BusinessInterfaceDescriptor;
import org.jboss.weld.ejb.spi.EjbServices;
import org.jboss.weld.injection.producer.Instantiator;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.BeanMethods;
import org.jboss.weld.util.reflection.Formats;

class SessionBeanImpl extends AbstractClassBean implements SessionBean {
   private final InternalEjbDescriptor ejbDescriptor;
   private Instantiator proxyInstantiator;

   public static SessionBean of(BeanAttributes attributes, InternalEjbDescriptor ejbDescriptor, BeanManagerImpl beanManager, EnhancedAnnotatedType type) {
      return new SessionBeanImpl(attributes, type, ejbDescriptor, new StringBeanIdentifier(SessionBeans.createIdentifier(type, ejbDescriptor)), beanManager);
   }

   SessionBeanImpl(BeanAttributes attributes, EnhancedAnnotatedType type, InternalEjbDescriptor ejbDescriptor, BeanIdentifier identifier, BeanManagerImpl manager) {
      super(attributes, type, identifier, manager);
      this.ejbDescriptor = ejbDescriptor;
      this.setProducer(this.beanManager.getLocalInjectionTargetFactory(type).createInjectionTarget(type, this, false));
   }

   public void internalInitialize(BeanDeployerEnvironment environment) {
      super.internalInitialize(environment);
      this.checkEJBTypeAllowed();
      this.checkConflictingRoles();
      this.checkObserverMethods();
      this.checkScopeAllowed();
   }

   protected void checkConflictingRoles() {
      if (this.getType().isAnnotationPresent(Interceptor.class)) {
         throw BeanLogger.LOG.ejbCannotBeInterceptor(this.getType());
      } else if (this.getType().isAnnotationPresent(Decorator.class)) {
         throw BeanLogger.LOG.ejbCannotBeDecorator(this.getType());
      }
   }

   protected void checkScopeAllowed() {
      if (this.ejbDescriptor.isStateless() && !this.isDependent()) {
         throw BeanLogger.LOG.scopeNotAllowedOnStatelessSessionBean(this.getScope(), this.getType());
      } else if (this.ejbDescriptor.isSingleton() && !this.isDependent() && !this.getScope().equals(ApplicationScoped.class)) {
         throw BeanLogger.LOG.scopeNotAllowedOnSingletonBean(this.getScope(), this.getType());
      }
   }

   protected void specialize() {
      Set specializedBeans = this.getSpecializedBeans();
      if (specializedBeans.isEmpty()) {
         throw BeanLogger.LOG.specializingEnterpriseBeanMustExtendAnEnterpriseBean(this);
      } else {
         Iterator var2 = specializedBeans.iterator();

         AbstractBean specializedBean;
         do {
            if (!var2.hasNext()) {
               return;
            }

            specializedBean = (AbstractBean)var2.next();
         } while(specializedBean instanceof SessionBean);

         throw BeanLogger.LOG.specializingEnterpriseBeanMustExtendAnEnterpriseBean(this);
      }
   }

   public Object create(CreationalContext creationalContext) {
      if (this.proxyInstantiator == null) {
         throw BeanLogger.LOG.initABDnotInvoked(this.annotatedType);
      } else {
         return this.proxyInstantiator.newInstance(creationalContext, this.beanManager);
      }
   }

   public void destroy(Object instance, CreationalContext creationalContext) {
      super.destroy(instance, creationalContext);
      if (instance == null) {
         throw BeanLogger.LOG.cannotDestroyNullBean(this);
      } else if (!(instance instanceof EnterpriseBeanInstance)) {
         throw BeanLogger.LOG.cannotDestroyEnterpriseBeanNotCreated(instance);
      } else {
         EnterpriseBeanInstance enterpriseBeanInstance = (EnterpriseBeanInstance)instance;
         enterpriseBeanInstance.destroy(Marker.INSTANCE, this, creationalContext);
         creationalContext.release();
      }
   }

   private void checkEJBTypeAllowed() {
      if (this.ejbDescriptor.isMessageDriven()) {
         throw BeanLogger.LOG.messageDrivenBeansCannotBeManaged(this);
      }
   }

   protected void checkType() {
      if (!this.isDependent() && this.getEnhancedAnnotated().isGeneric()) {
         throw BeanLogger.LOG.genericSessionBeanMustBeDependent(this);
      } else {
         boolean passivating = ((MetaAnnotationStore)this.beanManager.getServices().get(MetaAnnotationStore.class)).getScopeModel(this.getScope()).isPassivating();
         if (passivating && !this.isPassivationCapableBean()) {
            throw BeanLogger.LOG.passivatingBeanNeedsSerializableImpl(this);
         }
      }
   }

   public InternalEjbDescriptor getEjbDescriptor() {
      return this.ejbDescriptor;
   }

   public boolean isClientCanCallRemoveMethods() {
      return this.getEjbDescriptor().isStateful() && this.isDependent();
   }

   protected void checkObserverMethods() {
      Collection observerMethods = BeanMethods.getObserverMethods(this.getEnhancedAnnotated());
      Collection asyncObserverMethods = BeanMethods.getAsyncObserverMethods(this.getEnhancedAnnotated());
      this.checkObserverMethods(observerMethods);
      this.checkObserverMethods(asyncObserverMethods);
   }

   private void checkObserverMethods(Collection observerMethods) {
      if (!observerMethods.isEmpty()) {
         Set businessMethodSignatures = this.getLocalBusinessMethodSignatures();
         Set remoteBusinessMethodSignatures = this.getRemoteBusinessMethodSignatures();
         Iterator var4 = observerMethods.iterator();

         while(var4.hasNext()) {
            EnhancedAnnotatedMethod observerMethod = (EnhancedAnnotatedMethod)var4.next();
            boolean isLocalBusinessMethod = !remoteBusinessMethodSignatures.contains(observerMethod.getSignature()) && businessMethodSignatures.contains(observerMethod.getSignature());
            if (!isLocalBusinessMethod && !observerMethod.isStatic()) {
               throw BeanLogger.LOG.observerMethodMustBeStaticOrBusiness(observerMethod, Formats.formatAsStackTraceElement(observerMethod.getJavaMember()));
            }
         }
      }

   }

   public Set getLocalBusinessMethodSignatures() {
      Set businessMethodSignatures = new HashSet();
      Iterator var2 = this.ejbDescriptor.getLocalBusinessInterfaces().iterator();

      while(var2.hasNext()) {
         BusinessInterfaceDescriptor businessInterfaceDescriptor = (BusinessInterfaceDescriptor)var2.next();
         Method[] var4 = businessInterfaceDescriptor.getInterface().getMethods();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method m = var4[var6];
            businessMethodSignatures.add(new MethodSignatureImpl(m));
         }
      }

      return Collections.unmodifiableSet(businessMethodSignatures);
   }

   public Set getRemoteBusinessMethodSignatures() {
      Set businessMethodSignatures = new HashSet();
      Iterator var2 = this.ejbDescriptor.getRemoteBusinessInterfaces().iterator();

      while(var2.hasNext()) {
         BusinessInterfaceDescriptor businessInterfaceDescriptor = (BusinessInterfaceDescriptor)var2.next();
         Method[] var4 = businessInterfaceDescriptor.getInterface().getMethods();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method m = var4[var6];
            businessMethodSignatures.add(new MethodSignatureImpl(m));
         }
      }

      return Collections.unmodifiableSet(businessMethodSignatures);
   }

   public SessionObjectReference createReference() {
      SessionObjectReference var1;
      try {
         SessionBeanAwareInjectionPointBean.registerContextualInstance(this.getEjbDescriptor());
         var1 = ((EjbServices)this.beanManager.getServices().get(EjbServices.class)).resolveEjb(this.getEjbDescriptor().delegate());
      } finally {
         SessionBeanAwareInjectionPointBean.unregisterContextualInstance(this.getEjbDescriptor());
      }

      return var1;
   }

   protected boolean isInterceptionCandidate() {
      return true;
   }

   public String toString() {
      return "Session bean [" + this.getBeanClass() + " with qualifiers [" + Formats.formatAnnotations(this.getQualifiers()) + "]; local interfaces are [" + Formats.formatBusinessInterfaceDescriptors(this.getEjbDescriptor().getLocalBusinessInterfaces()) + "]";
   }

   public boolean isProxyable() {
      return true;
   }

   public boolean isPassivationCapableBean() {
      return this.ejbDescriptor.isPassivationCapable();
   }

   public boolean isPassivationCapableDependency() {
      return this.ejbDescriptor.isStateful() && this.isPassivationCapableBean() || this.ejbDescriptor.isSingleton() || this.ejbDescriptor.isStateless();
   }

   public void initializeAfterBeanDiscovery() {
      super.initializeAfterBeanDiscovery();
      this.proxyInstantiator = new SessionBeanProxyInstantiator(this.enhancedAnnotatedItem, this);
      this.registerInterceptors();
   }

   protected void registerInterceptors() {
      InterceptionModel model = (InterceptionModel)this.beanManager.getInterceptorModelRegistry().get(this.getAnnotated());
      if (model != null) {
         ((EjbServices)this.getBeanManager().getServices().get(EjbServices.class)).registerInterceptors(this.getEjbDescriptor().delegate(), new InterceptorBindingsAdapter(model));
      }

   }
}
