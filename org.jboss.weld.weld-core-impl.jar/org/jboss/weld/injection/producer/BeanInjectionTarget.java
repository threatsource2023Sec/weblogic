package org.jboss.weld.injection.producer;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.CustomDecoratorWrapper;
import org.jboss.weld.bean.DecoratorImpl;
import org.jboss.weld.bean.proxy.ProxyInstantiator;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.reflection.Formats;

public class BeanInjectionTarget extends BasicInjectionTarget {
   private final Bean bean;

   public static BeanInjectionTarget createDefault(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager) {
      return new BeanInjectionTarget(type, bean, beanManager);
   }

   public static BeanInjectionTarget forCdiInterceptor(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl manager) {
      return new BeanInjectionTarget(type, bean, manager, ResourceInjector.of(type, bean, manager), NoopLifecycleCallbackInvoker.getInstance());
   }

   public BeanInjectionTarget(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Injector injector, LifecycleCallbackInvoker invoker) {
      super(type, bean, beanManager, injector, invoker);
      this.bean = bean;
   }

   public BeanInjectionTarget(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager) {
      this(type, bean, beanManager, ResourceInjector.of(type, bean, beanManager), DefaultLifecycleCallbackInvoker.of(type));
   }

   public void dispose(Object instance) {
   }

   protected boolean isInterceptor() {
      return this.getBean() instanceof Interceptor || this.getType().isAnnotationPresent(javax.interceptor.Interceptor.class);
   }

   protected boolean isDecorator() {
      return this.getBean() instanceof Decorator || this.getType().isAnnotationPresent(javax.decorator.Decorator.class);
   }

   protected boolean isInterceptionCandidate() {
      return !this.isInterceptor() && !this.isDecorator() && !Modifier.isAbstract(this.getType().getJavaClass().getModifiers());
   }

   protected void initializeInterceptionModel(EnhancedAnnotatedType annotatedType) {
      AbstractInstantiator instantiator = (AbstractInstantiator)this.getInstantiator();
      if (instantiator.getConstructorInjectionPoint() != null) {
         if (this.isInterceptionCandidate() && !this.beanManager.getInterceptorModelRegistry().containsKey(this.getType())) {
            this.buildInterceptionModel(annotatedType, instantiator);
         }

      }
   }

   protected void buildInterceptionModel(EnhancedAnnotatedType annotatedType, AbstractInstantiator instantiator) {
      (new InterceptionModelInitializer(this.beanManager, annotatedType, annotatedType.getDeclaredEnhancedConstructor(instantiator.getConstructorInjectionPoint().getSignature()), this.getBean())).init();
   }

   public void initializeAfterBeanDiscovery(EnhancedAnnotatedType annotatedType) {
      this.initializeInterceptionModel(annotatedType);
      InterceptionModel interceptionModel = null;
      if (this.isInterceptionCandidate()) {
         interceptionModel = (InterceptionModel)this.beanManager.getInterceptorModelRegistry().get(this.getType());
      }

      boolean hasNonConstructorInterceptors = interceptionModel != null && (interceptionModel.hasExternalNonConstructorInterceptors() || interceptionModel.hasTargetClassInterceptors());
      List decorators = null;
      if (this.getBean() != null && this.isInterceptionCandidate()) {
         decorators = this.beanManager.resolveDecorators(this.getBean().getTypes(), this.getBean().getQualifiers());
      }

      boolean hasDecorators = decorators != null && !decorators.isEmpty();
      if (hasDecorators) {
         this.checkDecoratedMethods(annotatedType, decorators);
      }

      if (hasNonConstructorInterceptors || hasDecorators) {
         if (!(this.getInstantiator() instanceof DefaultInstantiator)) {
            throw new IllegalStateException("Unexpected instantiator " + this.getInstantiator());
         }

         DefaultInstantiator delegate = (DefaultInstantiator)this.getInstantiator();
         this.setInstantiator(SubclassedComponentInstantiator.forInterceptedDecoratedBean(annotatedType, this.getBean(), delegate, this.beanManager));
         if (hasDecorators) {
            this.setInstantiator(new SubclassDecoratorApplyingInstantiator(this.getBeanManager().getContextId(), this.getInstantiator(), this.getBean(), decorators));
         }

         if (hasNonConstructorInterceptors) {
            this.setInstantiator(new InterceptorApplyingInstantiator(this.getInstantiator(), interceptionModel, this.getType()));
         }
      }

      if (this.isInterceptionCandidate()) {
         this.setupConstructorInterceptionInstantiator(interceptionModel);
      }

   }

   protected void setupConstructorInterceptionInstantiator(InterceptionModel interceptionModel) {
      if (interceptionModel != null && interceptionModel.hasExternalConstructorInterceptors()) {
         this.setInstantiator(new ConstructorInterceptionInstantiator(this.getInstantiator(), interceptionModel, this.getType()));
      }

   }

   protected void checkNoArgsConstructor(EnhancedAnnotatedType type) {
      EnhancedAnnotatedConstructor constructor = type.getNoArgsEnhancedConstructor();
      if (constructor == null) {
         if (((ProxyInstantiator)this.beanManager.getServices().get(ProxyInstantiator.class)).isUsingConstructor()) {
            throw BeanLogger.LOG.decoratedHasNoNoargsConstructor(this);
         }
      } else if (constructor.isPrivate()) {
         throw BeanLogger.LOG.decoratedNoargsConstructorIsPrivate(this, Formats.formatAsStackTraceElement((Member)constructor.getJavaMember()));
      }
   }

   protected void checkDecoratedMethods(EnhancedAnnotatedType type, List decorators) {
      if (type.isFinal()) {
         throw BeanLogger.LOG.finalBeanClassWithDecoratorsNotAllowed(this);
      } else {
         this.checkNoArgsConstructor(type);
         Iterator var3 = decorators.iterator();

         while(var3.hasNext()) {
            Decorator decorator = (Decorator)var3.next();
            EnhancedAnnotatedType decoratorClass;
            if (decorator instanceof DecoratorImpl) {
               DecoratorImpl decoratorBean = (DecoratorImpl)decorator;
               decoratorClass = ((ClassTransformer)decoratorBean.getBeanManager().getServices().get(ClassTransformer.class)).getEnhancedAnnotatedType(decoratorBean.getAnnotated());
            } else {
               if (!(decorator instanceof CustomDecoratorWrapper)) {
                  throw BeanLogger.LOG.nonContainerDecorator(decorator);
               }

               decoratorClass = ((CustomDecoratorWrapper)decorator).getEnhancedAnnotated();
            }

            Iterator var9 = decoratorClass.getEnhancedMethods().iterator();

            while(var9.hasNext()) {
               EnhancedAnnotatedMethod decoratorMethod = (EnhancedAnnotatedMethod)var9.next();
               EnhancedAnnotatedMethod method = type.getEnhancedMethod(decoratorMethod.getSignature());
               if (method != null && !method.isStatic() && !method.isPrivate() && method.isFinal()) {
                  throw BeanLogger.LOG.finalBeanClassWithInterceptorsNotAllowed(this);
               }
            }
         }

      }
   }

   public Object produce(CreationalContext ctx) {
      Object instance = super.produce(ctx);
      if (this.bean != null && !this.bean.getScope().equals(Dependent.class) && !this.getInstantiator().hasDecoratorSupport()) {
         ctx.push(instance);
      }

      return instance;
   }

   public Bean getBean() {
      return this.bean;
   }
}
