package org.jboss.weld.injection.producer;

import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.api.WeldInjectionTarget;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class BasicInjectionTarget extends AbstractProducer implements WeldInjectionTarget {
   protected final BeanManagerImpl beanManager;
   private final SlimAnnotatedType type;
   private final Set injectionPoints;
   private Instantiator instantiator;
   private final Injector injector;
   private final LifecycleCallbackInvoker invoker;

   public static BasicInjectionTarget create(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Injector injector, LifecycleCallbackInvoker invoker) {
      return new BasicInjectionTarget(type, bean, beanManager, injector, invoker);
   }

   public static BasicInjectionTarget createDefault(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Instantiator instantiator) {
      return new BasicInjectionTarget(type, bean, beanManager, instantiator);
   }

   public static BasicInjectionTarget createNonCdiInterceptor(EnhancedAnnotatedType type, BeanManagerImpl beanManager) {
      return new BasicInjectionTarget(type, (Bean)null, beanManager, DefaultInjector.of(type, (Bean)null, beanManager), NoopLifecycleCallbackInvoker.getInstance());
   }

   protected BasicInjectionTarget(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Injector injector, LifecycleCallbackInvoker invoker) {
      this(type, bean, beanManager, injector, invoker, (Instantiator)null);
   }

   protected BasicInjectionTarget(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Injector injector, LifecycleCallbackInvoker invoker, Instantiator instantiator) {
      this.beanManager = beanManager;
      this.type = type.slim();
      this.injector = injector;
      this.invoker = invoker;
      Set injectionPoints = new HashSet();
      this.checkType(type);
      this.injector.registerInjectionPoints(injectionPoints);
      if (instantiator != null) {
         this.instantiator = instantiator;
      } else {
         this.instantiator = this.initInstantiator(type, bean, beanManager, injectionPoints);
      }

      this.injectionPoints = ImmutableSet.copyOf(injectionPoints);
      this.checkDelegateInjectionPoints();
   }

   protected BasicInjectionTarget(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Instantiator instantiator) {
      this(type, bean, beanManager, DefaultInjector.of(type, bean, beanManager), DefaultLifecycleCallbackInvoker.of(type), instantiator);
   }

   protected void checkType(EnhancedAnnotatedType type) {
      if (!Reflections.isTopLevelOrStaticNestedClass(type.getJavaClass())) {
         throw BeanLogger.LOG.simpleBeanAsNonStaticInnerClassNotAllowed(type);
      }
   }

   public Object produce(CreationalContext ctx) {
      return this.instantiator.newInstance(ctx, this.beanManager);
   }

   public void inject(Object instance, CreationalContext ctx) {
      this.injector.inject(instance, ctx, this.beanManager, this.type, this);
   }

   public void postConstruct(Object instance) {
      this.invoker.postConstruct(instance, this.instantiator);
   }

   public void preDestroy(Object instance) {
      this.invoker.preDestroy(instance, this.instantiator);
   }

   public void dispose(Object instance) {
   }

   public Set getInjectionPoints() {
      return this.injectionPoints;
   }

   protected SlimAnnotatedType getType() {
      return this.type;
   }

   public BeanManagerImpl getBeanManager() {
      return this.beanManager;
   }

   public Instantiator getInstantiator() {
      return this.instantiator;
   }

   public void setInstantiator(Instantiator instantiator) {
      this.instantiator = instantiator;
   }

   public boolean hasInterceptors() {
      return this.instantiator.hasInterceptorSupport();
   }

   public boolean hasDecorators() {
      return this.instantiator.hasDecoratorSupport();
   }

   protected void initializeAfterBeanDiscovery(EnhancedAnnotatedType annotatedType) {
   }

   protected Instantiator initInstantiator(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Set injectionPoints) {
      DefaultInstantiator instantiator = new DefaultInstantiator(type, bean, beanManager);
      injectionPoints.addAll(instantiator.getParameterInjectionPoints());
      return instantiator;
   }

   public AnnotatedType getAnnotated() {
      return this.type;
   }

   public AnnotatedType getAnnotatedType() {
      return this.getAnnotated();
   }

   public Injector getInjector() {
      return this.injector;
   }

   public LifecycleCallbackInvoker getLifecycleCallbackInvoker() {
      return this.invoker;
   }

   public String toString() {
      StringBuilder result = new StringBuilder("InjectionTarget for ");
      if (this.getBean() == null) {
         result.append(this.getAnnotated());
      } else {
         result.append(this.getBean());
      }

      return result.toString();
   }

   public Bean getBean() {
      return null;
   }
}
