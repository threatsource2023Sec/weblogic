package org.jboss.weld.bean;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.manager.BeanManagerImpl;

public final class ContextualInstance {
   private ContextualInstance() {
   }

   public static Object get(Bean bean, BeanManagerImpl manager, CreationalContext ctx) {
      return getStrategy(bean).get(bean, manager, ctx);
   }

   public static Object getIfExists(Bean bean, BeanManagerImpl manager) {
      return getStrategy(bean).getIfExists(bean, manager);
   }

   public static Object get(RIBean bean, BeanManagerImpl manager, CreationalContext ctx) {
      return bean.getContextualInstanceStrategy().get(bean, manager, ctx);
   }

   public static Object getIfExists(RIBean bean, BeanManagerImpl manager) {
      return bean.getContextualInstanceStrategy().getIfExists(bean, manager);
   }

   private static ContextualInstanceStrategy getStrategy(Bean bean) {
      return bean instanceof RIBean ? ((RIBean)bean).getContextualInstanceStrategy() : ContextualInstanceStrategy.defaultStrategy();
   }
}
