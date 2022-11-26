package org.jboss.weld.contexts;

import org.jboss.weld.contexts.beanstore.BeanStore;

public abstract class AbstractUnboundContext extends AbstractManagedContext {
   private final ThreadLocal beanStore = new ThreadLocal();

   public AbstractUnboundContext(String contextId, boolean multithreaded) {
      super(contextId, multithreaded);
   }

   protected BeanStore getBeanStore() {
      return (BeanStore)this.beanStore.get();
   }

   protected void setBeanStore(BeanStore beanStore) {
      this.beanStore.set(beanStore);
   }

   protected void destroy() {
      super.destroy();
      this.beanStore.remove();
   }

   public void cleanup() {
      super.cleanup();
      this.beanStore.remove();
   }
}
