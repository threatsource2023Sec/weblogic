package org.jboss.weld.module.web.context.http;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import org.jboss.weld.context.http.HttpRequestContext;
import org.jboss.weld.contexts.AbstractBoundContext;
import org.jboss.weld.contexts.beanstore.BoundBeanStore;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.contexts.beanstore.SimpleNamingScheme;
import org.jboss.weld.contexts.cache.RequestScopedCache;
import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.module.web.context.beanstore.http.RequestBeanStore;
import org.jboss.weld.util.collections.Iterables;
import org.jboss.weld.util.reflection.Reflections;

public class HttpRequestContextImpl extends AbstractBoundContext implements HttpRequestContext {
   private final NamingScheme namingScheme = new SimpleNamingScheme(HttpRequestContext.class.getName());

   public HttpRequestContextImpl(String contextId) {
      super(contextId, false);
   }

   public boolean associate(HttpServletRequest request) {
      BoundBeanStore beanStore = this.getBeanStore();
      if (beanStore != null) {
         ContextLogger.LOG.beanStoreLeakDuringAssociation(this.getClass().getName(), request);
         if (ContextLogger.LOG.isDebugEnabled()) {
            ContextLogger.LOG.beanStoreLeakAffectedBeanIdentifiers(this.getClass().getName(), Iterables.toMultiRowString(beanStore));
         }
      }

      BoundBeanStore beanStore = new RequestBeanStore(request, this.namingScheme);
      this.setBeanStore(beanStore);
      beanStore.attach();
      return true;
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

   public Class getScope() {
      return RequestScoped.class;
   }

   public HttpServletRequest getHttpServletRequest() {
      return this.getBeanStore() instanceof RequestBeanStore ? ((RequestBeanStore)Reflections.cast(this.getBeanStore())).getRequest() : null;
   }
}
