package org.jboss.weld.module.web.el;

import javax.el.ELContext;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.LazyValueHolder;

public class WeldELResolver extends AbstractWeldELResolver {
   private final BeanManagerImpl beanManager;
   private final LazyValueHolder rootNamespace;

   public WeldELResolver(BeanManagerImpl manager) {
      this.beanManager = manager;
      this.rootNamespace = LazyValueHolder.forSupplier(() -> {
         return new Namespace(manager.getDynamicAccessibleNamespaces());
      });
   }

   protected BeanManagerImpl getManager(ELContext context) {
      return this.beanManager;
   }

   protected Namespace getRootNamespace() {
      return (Namespace)this.rootNamespace.get();
   }
}
