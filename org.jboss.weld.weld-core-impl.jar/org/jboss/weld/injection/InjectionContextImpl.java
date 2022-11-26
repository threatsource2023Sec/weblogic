package org.jboss.weld.injection;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.injection.spi.InjectionContext;
import org.jboss.weld.injection.spi.InjectionServices;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class InjectionContextImpl implements InjectionContext {
   private final BeanManagerImpl beanManager;
   private final InjectionTarget injectionTarget;
   private final AnnotatedType annotatedType;
   private final Object target;

   public InjectionContextImpl(BeanManagerImpl beanManager, InjectionTarget injectionTarget, AnnotatedType annotatedType, Object target) {
      this.beanManager = beanManager;
      this.injectionTarget = injectionTarget;
      this.annotatedType = annotatedType;
      this.target = target;
   }

   public void run() {
      InjectionServices injectionServices = (InjectionServices)this.beanManager.getServices().get(InjectionServices.class);
      if (injectionServices != null) {
         injectionServices.aroundInject(this);
      } else {
         this.proceed();
      }

   }

   public InjectionTarget getInjectionTarget() {
      return this.injectionTarget;
   }

   public AnnotatedType getAnnotatedType() {
      return this.annotatedType;
   }

   public Object getTarget() {
      return this.target;
   }
}
