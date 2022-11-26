package org.jboss.weld.contexts.bound;

import java.util.Map;
import javax.enterprise.context.RequestScoped;
import org.jboss.weld.context.bound.BoundRequestContext;
import org.jboss.weld.contexts.AbstractBoundContext;
import org.jboss.weld.contexts.beanstore.MapBeanStore;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.contexts.beanstore.SimpleNamingScheme;
import org.jboss.weld.contexts.cache.RequestScopedCache;

public class BoundRequestContextImpl extends AbstractBoundContext implements BoundRequestContext {
   private final NamingScheme namingScheme = new SimpleNamingScheme(BoundRequestContext.class.getName());

   public BoundRequestContextImpl(String contextId) {
      super(contextId, false);
   }

   public Class getScope() {
      return RequestScoped.class;
   }

   public boolean associate(Map storage) {
      if (this.getBeanStore() == null) {
         this.setBeanStore(new MapBeanStore(this.namingScheme, storage, true));
         this.getBeanStore().attach();
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

   public void invalidate() {
      super.invalidate();
      this.getBeanStore().detach();
   }
}
