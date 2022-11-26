package org.jboss.weld.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.contexts.CreationalContextImpl;
import org.jboss.weld.injection.producer.BasicInjectionTarget;
import org.jboss.weld.interceptor.spi.metadata.InterceptorClassMetadata;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.Decorators;
import org.jboss.weld.util.Proxies;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class ManagedBean extends AbstractClassBean {
   private final boolean proxiable;
   private boolean passivationCapableBean;
   private boolean passivationCapableDependency;
   private boolean hasPostConstructCallback;

   public static ManagedBean of(BeanAttributes attributes, EnhancedAnnotatedType clazz, BeanManagerImpl beanManager) {
      return new ManagedBean(attributes, clazz, createId(attributes, clazz), beanManager);
   }

   private static BeanIdentifier createId(BeanAttributes attributes, EnhancedAnnotatedType clazz) {
      return (BeanIdentifier)(!Dependent.class.equals(attributes.getScope()) && !ApplicationScoped.class.equals(attributes.getScope()) ? new StringBeanIdentifier(BeanIdentifiers.forManagedBean(clazz)) : new ManagedBeanIdentifier((AnnotatedTypeIdentifier)clazz.slim().getIdentifier()));
   }

   protected ManagedBean(BeanAttributes attributes, EnhancedAnnotatedType type, BeanIdentifier identifier, BeanManagerImpl beanManager) {
      super(attributes, type, identifier, beanManager);
      this.proxiable = Proxies.isTypesProxyable((Iterable)this.getTypes(), beanManager.getServices());
      this.setProducer(beanManager.getLocalInjectionTargetFactory(this.getEnhancedAnnotated()).createInjectionTarget(this.getEnhancedAnnotated(), this, false));
   }

   public void internalInitialize(BeanDeployerEnvironment environment) {
      super.internalInitialize(environment);
      this.initPassivationCapable();
   }

   private void initPassivationCapable() {
      this.passivationCapableBean = this.getEnhancedAnnotated().isSerializable();
      this.passivationCapableDependency = this.isNormalScoped() || this.isDependent() && this.passivationCapableBean;
   }

   public void initializeAfterBeanDiscovery() {
      if (this.passivationCapableBean && this.hasDecorators() && !this.allDecoratorsArePassivationCapable()) {
         this.passivationCapableBean = false;
      }

      if (this.passivationCapableBean && this.hasInterceptors() && !this.allInterceptorsArePassivationCapable()) {
         this.passivationCapableBean = false;
      }

      super.initializeAfterBeanDiscovery();
   }

   private boolean allDecoratorsArePassivationCapable() {
      return this.getFirstNonPassivationCapableDecorator() == null;
   }

   private Decorator getFirstNonPassivationCapableDecorator() {
      Iterator var1 = this.getDecorators().iterator();

      Decorator decorator;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         decorator = (Decorator)var1.next();
      } while(Decorators.isPassivationCapable(decorator));

      return decorator;
   }

   private boolean allInterceptorsArePassivationCapable() {
      return this.getFirstNonPassivationCapableInterceptor() == null;
   }

   private InterceptorClassMetadata getFirstNonPassivationCapableInterceptor() {
      Iterator var1 = ((InterceptionModel)this.getBeanManager().getInterceptorModelRegistry().get(this.getAnnotated())).getAllInterceptors().iterator();

      InterceptorClassMetadata interceptorMetadata;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         interceptorMetadata = (InterceptorClassMetadata)var1.next();
      } while(Reflections.isSerializable(interceptorMetadata.getJavaClass()));

      return interceptorMetadata;
   }

   public Object create(CreationalContext creationalContext) {
      Object instance = this.getProducer().produce(creationalContext);
      this.getProducer().inject(instance, creationalContext);
      if (this.hasPostConstructCallback && !this.beanManager.isContextActive(RequestScoped.class)) {
         RequestContext context = this.getUnboundRequestContext();

         try {
            context.activate();
            this.beanManager.fireRequestContextInitialized(this.getId());
            this.getProducer().postConstruct(instance);
         } finally {
            this.beanManager.fireRequestContextBeforeDestroyed(this.getId());
            context.invalidate();
            context.deactivate();
            this.beanManager.fireRequestContextDestroyed(this.getId());
         }
      } else {
         this.getProducer().postConstruct(instance);
      }

      return instance;
   }

   public void destroy(Object instance, CreationalContext creationalContext) {
      super.destroy(instance, creationalContext);

      try {
         this.getProducer().preDestroy(instance);
         if (creationalContext instanceof CreationalContextImpl) {
            ((CreationalContextImpl)creationalContext).release(this, instance);
         } else {
            creationalContext.release();
         }
      } catch (Exception var4) {
         BeanLogger.LOG.errorDestroying(instance, this);
         BeanLogger.LOG.catchingDebug(var4);
      }

   }

   protected void checkType() {
      if (!this.isDependent() && this.getEnhancedAnnotated().isParameterizedType()) {
         throw BeanLogger.LOG.managedBeanWithParameterizedBeanClassMustBeDependent(this.type);
      } else {
         boolean passivating = this.beanManager.isPassivatingScope(this.getScope());
         if (passivating && !this.isPassivationCapableBean()) {
            if (!this.getEnhancedAnnotated().isSerializable()) {
               throw BeanLogger.LOG.passivatingBeanNeedsSerializableImpl(this);
            }

            if (this.hasDecorators() && !this.allDecoratorsArePassivationCapable()) {
               throw BeanLogger.LOG.passivatingBeanHasNonPassivationCapableDecorator(this, this.getFirstNonPassivationCapableDecorator());
            }

            if (this.hasInterceptors() && !this.allInterceptorsArePassivationCapable()) {
               throw BeanLogger.LOG.passivatingBeanHasNonPassivationCapableInterceptor(this, this.getFirstNonPassivationCapableInterceptor());
            }
         }

      }
   }

   protected void checkBeanImplementation() {
      super.checkBeanImplementation();
      if (this.isNormalScoped()) {
         Iterator var1 = this.getEnhancedAnnotated().getEnhancedFields().iterator();

         while(var1.hasNext()) {
            EnhancedAnnotatedField field = (EnhancedAnnotatedField)var1.next();
            if (field.isPublic() && !field.isStatic()) {
               throw BeanLogger.LOG.publicFieldOnNormalScopedBeanNotAllowed(field);
            }
         }
      }

   }

   protected void specialize() {
      Set specializedBeans = this.getSpecializedBeans();
      if (specializedBeans.isEmpty()) {
         throw BeanLogger.LOG.specializingBeanMustExtendABean(this);
      } else {
         Iterator var2 = specializedBeans.iterator();

         AbstractBean specializedBean;
         do {
            if (!var2.hasNext()) {
               return;
            }

            specializedBean = (AbstractBean)var2.next();
         } while(specializedBean instanceof ManagedBean);

         throw BeanLogger.LOG.specializingManagedBeanCanExtendOnlyManagedBeans(this, specializedBean);
      }
   }

   protected boolean isInterceptionCandidate() {
      return !(this instanceof InterceptorImpl) && !(this instanceof DecoratorImpl);
   }

   public String toString() {
      return "Managed Bean [" + this.getBeanClass().toString() + "] with qualifiers [" + Formats.formatAnnotations((Iterable)this.getQualifiers()) + "]";
   }

   public boolean isProxyable() {
      return this.proxiable;
   }

   public boolean isPassivationCapableBean() {
      return this.passivationCapableBean;
   }

   public boolean isPassivationCapableDependency() {
      return this.passivationCapableDependency;
   }

   private RequestContext getUnboundRequestContext() {
      Bean bean = this.beanManager.resolve(this.beanManager.getBeans(RequestContext.class, (Annotation[])(UnboundLiteral.INSTANCE)));
      CreationalContext ctx = this.beanManager.createCreationalContext(bean);
      return (RequestContext)this.beanManager.getReference((Bean)bean, (Type)RequestContext.class, ctx);
   }

   public void setProducer(InjectionTarget producer) {
      super.setProducer(producer);
      this.hasPostConstructCallback = this.initHasPostConstructCallback(producer);
   }

   private boolean initHasPostConstructCallback(InjectionTarget producer) {
      if (producer instanceof BasicInjectionTarget) {
         BasicInjectionTarget weldProducer = (BasicInjectionTarget)producer;
         InterceptionModel interceptors = this.getInterceptors();
         if ((interceptors == null || interceptors.getInterceptors(InterceptionType.POST_CONSTRUCT, (Method)null).isEmpty()) && !weldProducer.getLifecycleCallbackInvoker().hasPostConstructCallback()) {
            return false;
         }
      }

      return true;
   }
}
