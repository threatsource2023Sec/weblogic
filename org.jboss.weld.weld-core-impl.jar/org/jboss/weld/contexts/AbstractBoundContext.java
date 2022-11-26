package org.jboss.weld.contexts;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jboss.weld.context.BoundContext;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.contexts.beanstore.BeanStore;
import org.jboss.weld.contexts.beanstore.BoundBeanStore;
import org.jboss.weld.contexts.beanstore.LockedBean;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public abstract class AbstractBoundContext extends AbstractManagedContext implements BoundContext {
   private final ThreadLocal beanStore = new ThreadLocal();

   public AbstractBoundContext(String contextId, boolean multithreaded) {
      super(contextId, multithreaded);
   }

   protected BoundBeanStore getBeanStore() {
      return (BoundBeanStore)this.beanStore.get();
   }

   protected void setBeanStore(BoundBeanStore beanStore) {
      if (beanStore == null) {
         this.beanStore.remove();
      } else {
         this.beanStore.set(beanStore);
      }

   }

   public void cleanup() {
      super.cleanup();
      this.beanStore.remove();
   }

   public void activate() {
      super.activate();
      this.getBeanStore().attach();
   }

   public void deactivate() {
      this.getBeanStore().detach();
      super.deactivate();
   }

   public boolean dissociate(Object storage) {
      if (this.getBeanStore() != null) {
         boolean var2;
         try {
            this.setBeanStore((BoundBeanStore)null);
            var2 = true;
         } finally {
            this.cleanup();
         }

         return var2;
      } else {
         return false;
      }
   }

   public Collection getAllContextualInstances() {
      Set result = new HashSet();
      BeanStore beanStore = this.getBeanStore();
      if (beanStore != null) {
         this.getBeanStore().iterator().forEachRemaining((beanId) -> {
            result.add(this.getBeanStore().get(beanId));
         });
      }

      return result;
   }

   public void clearAndSet(Collection setOfInstances) {
      BoundBeanStore boundBeanStore = this.getBeanStore();
      if (boundBeanStore != null) {
         boundBeanStore.clear();
         Iterator var3 = setOfInstances.iterator();

         while(var3.hasNext()) {
            ContextualInstance contextualInstance = (ContextualInstance)var3.next();
            BeanIdentifier id = this.getId(contextualInstance.getContextual());
            LockedBean lock = null;

            try {
               if (this.isMultithreaded()) {
                  lock = boundBeanStore.lock(id);
               }

               this.getBeanStore().put(this.getId(contextualInstance.getContextual()), contextualInstance);
            } finally {
               if (lock != null) {
                  lock.unlock();
               }

            }
         }
      }

   }
}
