package org.jboss.weld.bean.builtin;

import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.BeanManager;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.Arrays2;

public class BeanManagerBean extends AbstractBuiltInBean {
   private static final Set TYPES = Arrays2.asSet(Object.class, BeanManager.class);

   public BeanManagerBean(BeanManagerImpl manager) {
      super(new StringBeanIdentifier(BeanIdentifiers.forBuiltInBean(manager, BeanManager.class, (String)null)), manager, BeanManagerProxy.class);
   }

   public BeanManagerProxy create(CreationalContext creationalContext) {
      return new BeanManagerProxy(this.getBeanManager());
   }

   public Class getType() {
      return BeanManagerProxy.class;
   }

   public Set getTypes() {
      return TYPES;
   }

   public String toString() {
      return "Built-in Bean [javax.enterprise.inject.spi.BeanManager] with qualifiers [@Default]";
   }
}
