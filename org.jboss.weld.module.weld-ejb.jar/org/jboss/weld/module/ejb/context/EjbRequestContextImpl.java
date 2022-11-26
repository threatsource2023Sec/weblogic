package org.jboss.weld.module.ejb.context;

import javax.enterprise.context.RequestScoped;
import javax.interceptor.InvocationContext;
import org.jboss.weld.context.ejb.EjbRequestContext;
import org.jboss.weld.contexts.AbstractBoundContext;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.contexts.beanstore.SimpleNamingScheme;
import org.jboss.weld.contexts.cache.RequestScopedCache;
import org.jboss.weld.module.ejb.context.beanstore.InvocationContextBeanStore;

public class EjbRequestContextImpl extends AbstractBoundContext implements EjbRequestContext {
   private final NamingScheme namingScheme = new SimpleNamingScheme(EjbRequestContext.class.getName());

   public EjbRequestContextImpl(String contextId) {
      super(contextId, false);
   }

   public Class getScope() {
      return RequestScoped.class;
   }

   public boolean associate(InvocationContext ctx) {
      if (this.getBeanStore() == null) {
         this.setBeanStore(new InvocationContextBeanStore(this.namingScheme, ctx));
         return true;
      } else {
         return false;
      }
   }

   public void activate() {
      super.activate();
      RequestScopedCache.beginRequest();
   }

   public void deactivate() {
      try {
         RequestScopedCache.endRequest();
      } finally {
         super.deactivate();
      }

   }
}
