package org.jboss.weld.bean.builtin;

import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.api.WeldManager;
import org.jboss.weld.util.collections.Arrays2;

public class BeanManagerImplBean extends AbstractBuiltInBean {
   private static final Set TYPES = Arrays2.asSet(Object.class, BeanManagerImpl.class, WeldManager.class);

   public BeanManagerImplBean(BeanManagerImpl manager) {
      super(new StringBeanIdentifier(BeanIdentifiers.forBuiltInBean(manager, BeanManagerImpl.class, (String)null)), manager, BeanManagerImpl.class);
   }

   public BeanManagerImpl create(CreationalContext creationalContext) {
      return this.getBeanManager();
   }

   public Class getType() {
      return BeanManagerImpl.class;
   }

   public Set getTypes() {
      return TYPES;
   }

   public String toString() {
      return "Built-in Bean [org.jboss.weld.manager.BeanManagerImpl] with qualifiers [@Default]";
   }
}
