package org.jboss.weld.contexts;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Iterator;
import javax.enterprise.context.spi.AlterableContext;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.Container;
import org.jboss.weld.bean.WrappedContextual;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.contexts.beanstore.BeanStore;
import org.jboss.weld.contexts.beanstore.LockedBean;
import org.jboss.weld.contexts.cache.RequestScopedCache;
import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.util.Beans;

public abstract class AbstractContext implements AlterableContext {
   private final boolean multithreaded;
   private final ServiceRegistry serviceRegistry;

   public AbstractContext(String contextId, boolean multithreaded) {
      this.multithreaded = multithreaded;
      this.serviceRegistry = Container.instance(contextId).services();
   }

   @SuppressFBWarnings(
      value = {"UL_UNRELEASED_LOCK"},
      justification = "False positive from FindBugs"
   )
   public Object get(Contextual contextual, CreationalContext creationalContext) {
      if (!this.isActive()) {
         throw new javax.enterprise.context.ContextNotActiveException();
      } else {
         this.checkContextInitialized();
         BeanStore beanStore = this.getBeanStore();
         if (beanStore == null) {
            return null;
         } else if (contextual == null) {
            throw ContextLogger.LOG.contextualIsNull();
         } else {
            BeanIdentifier id = this.getId(contextual);
            ContextualInstance beanInstance = beanStore.get(id);
            if (beanInstance != null) {
               return beanInstance.getInstance();
            } else if (creationalContext != null) {
               LockedBean lock = null;

               Object var8;
               try {
                  Object instance;
                  if (this.multithreaded) {
                     lock = beanStore.lock(id);
                     beanInstance = beanStore.get(id);
                     if (beanInstance != null) {
                        instance = beanInstance.getInstance();
                        return instance;
                     }
                  }

                  instance = contextual.create(creationalContext);
                  if (instance != null) {
                     ContextualInstance beanInstance = new SerializableContextualInstanceImpl(contextual, instance, creationalContext, (ContextualStore)this.serviceRegistry.get(ContextualStore.class));
                     beanStore.put(id, beanInstance);
                  }

                  var8 = instance;
               } finally {
                  if (lock != null) {
                     lock.unlock();
                  }

               }

               return var8;
            } else {
               return null;
            }
         }
      }
   }

   public Object get(Contextual contextual) {
      return this.get(contextual, (CreationalContext)null);
   }

   public void destroy(Contextual contextual) {
      if (!this.isActive()) {
         throw new javax.enterprise.context.ContextNotActiveException();
      } else {
         this.checkContextInitialized();
         if (contextual == null) {
            throw ContextLogger.LOG.contextualIsNull();
         } else {
            BeanStore beanStore = this.getBeanStore();
            if (beanStore == null) {
               throw ContextLogger.LOG.noBeanStoreAvailable(this);
            } else {
               BeanIdentifier id = this.getId(contextual);
               ContextualInstance beanInstance = beanStore.remove(id);
               if (beanInstance != null) {
                  RequestScopedCache.invalidate();
                  this.destroyContextualInstance(beanInstance);
               }

            }
         }
      }
   }

   private void destroyContextualInstance(ContextualInstance instance) {
      instance.getContextual().destroy(instance.getInstance(), instance.getCreationalContext());
      ContextLogger.LOG.contextualInstanceRemoved(instance, this);
   }

   protected void destroy() {
      ContextLogger.LOG.contextCleared(this);
      BeanStore beanStore = this.getBeanStore();
      if (beanStore == null) {
         throw ContextLogger.LOG.noBeanStoreAvailable(this);
      } else {
         Iterator var2 = beanStore.iterator();

         while(var2.hasNext()) {
            BeanIdentifier id = (BeanIdentifier)var2.next();
            this.destroyContextualInstance(beanStore.get(id));
         }

         beanStore.clear();
      }
   }

   protected abstract BeanStore getBeanStore();

   public void cleanup() {
      BeanStore beanStore = this.getBeanStore();
      if (beanStore != null) {
         try {
            beanStore.clear();
         } catch (Exception var3) {
            ContextLogger.LOG.unableToClearBeanStore(beanStore);
            ContextLogger.LOG.catchingDebug(var3);
         }
      }

   }

   protected static Contextual getContextual(String contextId, String id) {
      return ((ContextualStore)Container.instance(contextId).services().get(ContextualStore.class)).getContextual(id);
   }

   protected BeanIdentifier getId(Contextual contextual) {
      if (contextual instanceof WrappedContextual) {
         contextual = ((WrappedContextual)contextual).delegate();
      }

      return Beans.getIdentifier(contextual, this.serviceRegistry);
   }

   protected ServiceRegistry getServiceRegistry() {
      return this.serviceRegistry;
   }

   protected void checkContextInitialized() {
   }

   protected boolean isMultithreaded() {
      return this.multithreaded;
   }
}
