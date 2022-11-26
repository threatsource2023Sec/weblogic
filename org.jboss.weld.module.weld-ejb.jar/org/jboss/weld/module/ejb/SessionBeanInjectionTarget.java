package org.jboss.weld.module.ejb;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler;
import org.jboss.weld.bean.proxy.ProxyObject;
import org.jboss.weld.injection.producer.AbstractInstantiator;
import org.jboss.weld.injection.producer.BeanInjectionTarget;
import org.jboss.weld.injection.producer.DefaultInjector;
import org.jboss.weld.injection.producer.DefaultInstantiator;
import org.jboss.weld.injection.producer.DefaultLifecycleCallbackInvoker;
import org.jboss.weld.injection.producer.Injector;
import org.jboss.weld.injection.producer.Instantiator;
import org.jboss.weld.injection.producer.InterceptionModelInitializer;
import org.jboss.weld.injection.producer.LifecycleCallbackInvoker;
import org.jboss.weld.injection.producer.SubclassDecoratorApplyingInstantiator;
import org.jboss.weld.injection.producer.SubclassedComponentInstantiator;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Types;

class SessionBeanInjectionTarget extends BeanInjectionTarget {
   private final SessionBean bean;

   public static SessionBeanInjectionTarget of(EnhancedAnnotatedType type, SessionBean bean, BeanManagerImpl beanManager) {
      LifecycleCallbackInvoker invoker = DefaultLifecycleCallbackInvoker.of(type);
      Object injector;
      if (!bean.getEjbDescriptor().isStateless() && !bean.getEjbDescriptor().isSingleton()) {
         injector = new DefaultInjector(type, bean, beanManager);
      } else {
         injector = new DynamicInjectionPointInjector(type, bean, beanManager);
      }

      return new SessionBeanInjectionTarget(type, bean, beanManager, (Injector)injector, invoker);
   }

   private SessionBeanInjectionTarget(EnhancedAnnotatedType type, SessionBean bean, BeanManagerImpl beanManager, Injector injector, LifecycleCallbackInvoker invoker) {
      super(type, bean, beanManager, injector, invoker);
      this.bean = bean;
   }

   public SessionBean getBean() {
      return this.bean;
   }

   protected Instantiator initInstantiator(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Set injectionPoints) {
      if (bean instanceof SessionBean) {
         EnhancedAnnotatedType implementationClass = SessionBeans.getEjbImplementationClass((SessionBean)bean);
         AbstractInstantiator instantiator = null;
         if (type.equals(implementationClass)) {
            instantiator = new DefaultInstantiator(type, bean, beanManager);
         } else {
            instantiator = SubclassedComponentInstantiator.forSubclassedEjb(type, implementationClass, bean, beanManager);
         }

         injectionPoints.addAll(((AbstractInstantiator)instantiator).getConstructorInjectionPoint().getParameterInjectionPoints());
         return (Instantiator)instantiator;
      } else {
         throw new IllegalArgumentException("Cannot create SessionBeanInjectionTarget for " + bean);
      }
   }

   public void initializeAfterBeanDiscovery(EnhancedAnnotatedType annotatedType) {
      this.initializeInterceptionModel(annotatedType);
      List decorators = this.beanManager.resolveDecorators(this.getBean().getTypes(), this.getBean().getQualifiers());
      if (!decorators.isEmpty()) {
         Instantiator instantiator = this.getInstantiator();
         EnhancedAnnotatedType implementationClass = SessionBeans.getEjbImplementationClass(this.getBean());
         Instantiator instantiator = SubclassedComponentInstantiator.forInterceptedDecoratedBean(implementationClass, this.getBean(), (AbstractInstantiator)instantiator, this.beanManager);
         Instantiator instantiator = new SubclassDecoratorApplyingInstantiator(this.getBeanManager().getContextId(), instantiator, this.getBean(), decorators, implementationClass.getJavaClass());
         this.setInstantiator(instantiator);
      }

      this.setupConstructorInterceptionInstantiator((InterceptionModel)this.beanManager.getInterceptorModelRegistry().get(this.getType()));
   }

   protected void buildInterceptionModel(EnhancedAnnotatedType annotatedType, AbstractInstantiator instantiator) {
      EnhancedAnnotatedConstructor constructor = annotatedType.getDeclaredEnhancedConstructor(instantiator.getConstructorInjectionPoint().getSignature());
      (new InterceptionModelInitializer(this.beanManager, annotatedType, constructor, this.getBean())).init();
   }

   public Object produce(CreationalContext ctx) {
      Object result = super.produce(ctx);
      if (result instanceof ProxyObject) {
         ProxyObject proxy = (ProxyObject)result;
         proxy.weld_setHandler(new SessionBeanViewMethodHandler(this.bean.getTypes(), (CombinedInterceptorAndDecoratorStackMethodHandler)proxy.weld_getHandler()));
      }

      return result;
   }

   public void inject(Object instance, CreationalContext ctx) {
      this.getInjector().inject(instance, ctx, this.beanManager, this.bean.getAnnotated(), this);
   }

   private static class SessionBeanViewMethodHandler extends CombinedInterceptorAndDecoratorStackMethodHandler {
      private static final long serialVersionUID = -8038819529432133787L;
      private final Set beanTypes;

      public SessionBeanViewMethodHandler(Set types, CombinedInterceptorAndDecoratorStackMethodHandler delegate) {
         this.beanTypes = Types.getRawTypes(types);
         this.setOuterDecorator(delegate.getOuterDecorator());
         this.setInterceptorMethodHandler(delegate.getInterceptorMethodHandler());
      }

      public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
         if (this.beanTypes.contains(thisMethod.getDeclaringClass())) {
            return super.invoke(self, thisMethod, proceed, args);
         } else {
            Method decoratedTypeMethod = this.getBeanTypeMethod(thisMethod);
            return super.invoke(self, decoratedTypeMethod, proceed, args);
         }
      }

      private Method getBeanTypeMethod(Method method) {
         Iterator var2 = this.beanTypes.iterator();

         while(var2.hasNext()) {
            Class c = (Class)var2.next();

            try {
               return c.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException var5) {
            }
         }

         return method;
      }
   }
}
