package org.jboss.weld.contexts;

import org.jboss.weld.contexts.beanstore.BeanStore;
import org.jboss.weld.contexts.beanstore.ConcurrentHashMapBeanStore;

public abstract class AbstractSharedContext extends AbstractContext {
   private final BeanStore beanStore = new ConcurrentHashMapBeanStore();

   protected AbstractSharedContext(String contextId) {
      super(contextId, true);
   }

   public BeanStore getBeanStore() {
      return this.beanStore;
   }

   public boolean isActive() {
      return true;
   }

   public void invalidate() {
      this.destroy();
   }

   protected void destroy() {
      super.destroy();
      this.cleanup();
   }

   public void cleanup() {
      super.cleanup();
      this.beanStore.clear();
   }

   public String toString() {
      String active = this.isActive() ? "Active " : "Inactive ";
      String beanStoreInfo = this.getBeanStore() == null ? "" : this.getBeanStore().toString();
      return active + "application context " + beanStoreInfo;
   }
}
