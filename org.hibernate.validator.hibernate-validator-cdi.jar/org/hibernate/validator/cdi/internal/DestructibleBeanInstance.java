package org.hibernate.validator.cdi.internal;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;

public class DestructibleBeanInstance {
   private final Object instance;
   private final InjectionTarget injectionTarget;

   public DestructibleBeanInstance(BeanManager beanManager, Class key) {
      this.injectionTarget = this.createInjectionTarget(beanManager, key);
      this.instance = createAndInjectBeans(beanManager, this.injectionTarget);
   }

   public DestructibleBeanInstance(BeanManager beanManager, Object instance) {
      this.injectionTarget = this.createInjectionTarget(beanManager, instance.getClass());
      injectBeans(beanManager, beanManager.createCreationalContext((Contextual)null), this.injectionTarget, instance);
      this.instance = instance;
   }

   public Object getInstance() {
      return this.instance;
   }

   public void destroy() {
      this.injectionTarget.preDestroy(this.instance);
      this.injectionTarget.dispose(this.instance);
   }

   private InjectionTarget createInjectionTarget(BeanManager beanManager, Class type) {
      AnnotatedType annotatedType = beanManager.createAnnotatedType(type);
      return beanManager.createInjectionTarget(annotatedType);
   }

   private static Object createAndInjectBeans(BeanManager beanManager, InjectionTarget injectionTarget) {
      CreationalContext creationalContext = beanManager.createCreationalContext((Contextual)null);
      Object instance = injectionTarget.produce(creationalContext);
      injectBeans(beanManager, creationalContext, injectionTarget, instance);
      return instance;
   }

   private static void injectBeans(BeanManager beanManager, CreationalContext creationalContext, InjectionTarget injectionTarget, Object instance) {
      injectionTarget.inject(instance, creationalContext);
      injectionTarget.postConstruct(instance);
   }
}
