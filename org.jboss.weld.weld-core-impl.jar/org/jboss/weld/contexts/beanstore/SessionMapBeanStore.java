package org.jboss.weld.contexts.beanstore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.reflection.Reflections;

public class SessionMapBeanStore extends MapBeanStore {
   private static final String LOCK_STORE_KEY = "org.jboss.weld.context.beanstore.LockStore";

   public SessionMapBeanStore(NamingScheme namingScheme, Map delegate) {
      super(namingScheme, delegate, delegate instanceof ConcurrentHashMap);
   }

   public ContextualInstance get(BeanIdentifier id) {
      ContextualInstance instance = super.get(id);
      if (instance == null && this.isAttached()) {
         String prefixedId = this.getNamingScheme().prefix(id);
         instance = (ContextualInstance)Reflections.cast(this.getAttribute(prefixedId));
      }

      return instance;
   }

   public LockStore getLockStore() {
      LockStore lockStore = this.lockStore;
      if (lockStore == null) {
         lockStore = (LockStore)this.getAttribute("org.jboss.weld.context.beanstore.LockStore");
         if (lockStore == null) {
            Class var2 = SessionMapBeanStore.class;
            synchronized(SessionMapBeanStore.class) {
               lockStore = (LockStore)this.getAttribute("org.jboss.weld.context.beanstore.LockStore");
               if (lockStore == null) {
                  lockStore = new LockStore();
                  this.setAttribute("org.jboss.weld.context.beanstore.LockStore", lockStore);
               }
            }
         }

         this.lockStore = lockStore;
      }

      return lockStore;
   }
}
