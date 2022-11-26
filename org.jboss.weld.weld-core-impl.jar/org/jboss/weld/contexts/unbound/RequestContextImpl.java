package org.jboss.weld.contexts.unbound;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.contexts.AbstractUnboundContext;
import org.jboss.weld.contexts.beanstore.BeanStore;
import org.jboss.weld.contexts.beanstore.HashMapBeanStore;

public class RequestContextImpl extends AbstractUnboundContext implements RequestContext {
   public RequestContextImpl(String contextId) {
      super(contextId, false);
   }

   public Class getScope() {
      return RequestScoped.class;
   }

   public void activate() {
      this.setBeanStore(new HashMapBeanStore());
      super.activate();
   }

   public void deactivate() {
      super.deactivate();
      this.setBeanStore((BeanStore)null);
      this.cleanup();
   }

   public Collection getAllContextualInstances() {
      Set result = new HashSet();
      this.getBeanStore().iterator().forEachRemaining((beanId) -> {
         result.add(this.getBeanStore().get(beanId));
      });
      return result;
   }

   public void clearAndSet(Collection setOfInstances) {
      this.getBeanStore().clear();
      Iterator var2 = setOfInstances.iterator();

      while(var2.hasNext()) {
         ContextualInstance contextualInstance = (ContextualInstance)var2.next();
         this.getBeanStore().put(this.getId(contextualInstance.getContextual()), contextualInstance);
      }

   }
}
