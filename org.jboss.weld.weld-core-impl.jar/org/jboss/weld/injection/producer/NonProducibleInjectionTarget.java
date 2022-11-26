package org.jboss.weld.injection.producer;

import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class NonProducibleInjectionTarget extends BasicInjectionTarget {
   public static NonProducibleInjectionTarget create(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager) {
      return create(type, bean, (Injector)null, (LifecycleCallbackInvoker)null, beanManager);
   }

   public static NonProducibleInjectionTarget create(EnhancedAnnotatedType type, Bean bean, Injector injector, LifecycleCallbackInvoker invoker, BeanManagerImpl beanManager) {
      if (injector == null) {
         injector = DefaultInjector.of(type, bean, beanManager);
      }

      if (invoker == null) {
         invoker = DefaultLifecycleCallbackInvoker.of(type);
      }

      return new NonProducibleInjectionTarget(type, bean, beanManager, (Injector)injector, (LifecycleCallbackInvoker)invoker);
   }

   private NonProducibleInjectionTarget(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Injector injector, LifecycleCallbackInvoker invoker) {
      super(type, bean, beanManager, injector, invoker);
   }

   protected Instantiator initInstantiator(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Set injectionPoints) {
      return null;
   }

   public Object produce(CreationalContext ctx) {
      throw BeanLogger.LOG.injectionTargetCannotProduceInstance(this.getAnnotated().getJavaClass());
   }

   protected void checkType(EnhancedAnnotatedType type) {
   }

   public boolean hasInterceptors() {
      return false;
   }

   public boolean hasDecorators() {
      return false;
   }
}
