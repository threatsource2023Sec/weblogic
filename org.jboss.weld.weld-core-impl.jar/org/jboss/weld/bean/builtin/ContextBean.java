package org.jboss.weld.bean.builtin;

import java.util.Set;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.bootstrap.ContextHolder;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.HierarchyDiscovery;

public class ContextBean extends AbstractBuiltInBean {
   private final Context context;
   private final Set types;
   private final Set qualifiers;

   public static ContextBean of(ContextHolder context, BeanManagerImpl beanManager) {
      return new ContextBean(context, beanManager);
   }

   public ContextBean(ContextHolder contextHolder, BeanManagerImpl beanManager) {
      super(new StringBeanIdentifier(BeanIdentifiers.forBuiltInBean(beanManager, contextHolder.getType(), (String)null)), beanManager, contextHolder.getType());
      this.context = contextHolder.getContext();
      this.types = HierarchyDiscovery.forNormalizedType(contextHolder.getType()).getTypeClosure();
      this.qualifiers = contextHolder.getQualifiers();
   }

   public Set getTypes() {
      return this.types;
   }

   public Context create(CreationalContext creationalContext) {
      return this.context;
   }

   public Set getQualifiers() {
      return this.qualifiers;
   }
}
