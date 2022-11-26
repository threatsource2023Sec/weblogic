package org.jboss.weld.manager;

import java.util.function.Function;

final class BeanTransform implements Function {
   private final BeanManagerImpl declaringBeanManager;

   public BeanTransform(BeanManagerImpl declaringBeanManager) {
      this.declaringBeanManager = declaringBeanManager;
   }

   public Iterable apply(BeanManagerImpl beanManager) {
      return beanManager.equals(this.declaringBeanManager) ? beanManager.getBeans() : beanManager.getSharedBeans();
   }
}
