package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import java.util.List;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractProcessInjectionTarget extends AbstractDefinitionContainerEvent {
   protected final AnnotatedType annotatedType;

   protected static void fire(BeanManagerImpl beanManager, AbstractClassBean bean) {
      if (beanManager.isBeanEnabled(bean)) {
         (new ProcessBeanInjectionTarget(beanManager, bean) {
         }).fire();
      }

   }

   protected static InjectionTarget fire(BeanManagerImpl beanManager, AnnotatedType annotatedType, InjectionTarget injectionTarget) {
      ProcessSimpleInjectionTarget processSimpleInjectionTarget = new ProcessSimpleInjectionTarget(beanManager, annotatedType, injectionTarget) {
      };
      processSimpleInjectionTarget.fire();
      return processSimpleInjectionTarget.getInjectionTargetInternal();
   }

   protected AbstractProcessInjectionTarget(BeanManagerImpl beanManager, AnnotatedType annotatedType) {
      super(beanManager, ProcessInjectionTarget.class, new Type[]{annotatedType.getBaseType()});
      this.annotatedType = annotatedType;
   }

   public List getDefinitionErrors() {
      return super.getErrors();
   }

   public AnnotatedType getAnnotatedType() {
      this.checkWithinObserverNotification();
      return this.annotatedType;
   }
}
