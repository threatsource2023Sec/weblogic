package org.jboss.weld.module.web.context.beanstore.http;

import java.util.Collections;
import java.util.Iterator;
import javax.servlet.http.HttpSession;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.contexts.beanstore.AttributeBeanStore;
import org.jboss.weld.contexts.beanstore.LockStore;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.collections.EnumerationIterator;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractSessionBeanStore extends AttributeBeanStore {
   private static final String SESSION_KEY = "org.jboss.weld.context.beanstore.http.LockStore";
   private transient volatile LockStore lockStore;
   private static final ThreadLocal CURRENT_LOCK_STORE = new ThreadLocal();
   private final boolean resetHttpSessionAttributeOnBeanAccess;

   protected abstract HttpSession getSession(boolean var1);

   public AbstractSessionBeanStore(NamingScheme namingScheme, boolean attributeLazyFetchingEnabled, ServiceRegistry serviceRegistry) {
      super(namingScheme, attributeLazyFetchingEnabled);
      this.resetHttpSessionAttributeOnBeanAccess = ((WeldConfiguration)serviceRegistry.get(WeldConfiguration.class)).getBooleanProperty(ConfigurationKey.RESET_HTTP_SESSION_ATTR_ON_BEAN_ACCESS);
   }

   protected Iterator getAttributeNames() {
      HttpSession session = this.getSession(false);
      return (Iterator)(session == null ? Collections.emptyIterator() : new EnumerationIterator(session.getAttributeNames()));
   }

   protected void removeAttribute(String key) {
      HttpSession session = this.getSession(false);
      if (session != null) {
         session.removeAttribute(key);
         ContextLogger.LOG.removedKeyFromSession(key, this.getSession(false).getId());
      } else {
         ContextLogger.LOG.unableToRemoveKeyFromSession(key);
      }

   }

   protected void setAttribute(String key, Object instance) {
      HttpSession session = this.getSession(true);
      if (session != null) {
         session.setAttribute(key, instance);
         ContextLogger.LOG.addedKeyToSession(key, this.getSession(false).getId());
      } else {
         ContextLogger.LOG.unableToAddKeyToSession(key);
      }

   }

   public ContextualInstance get(BeanIdentifier id) {
      ContextualInstance instance = super.get(id);
      if (instance == null && this.isAttached()) {
         String prefixedId = this.getNamingScheme().prefix(id);
         instance = (ContextualInstance)Reflections.cast(this.getAttribute(prefixedId));
      }

      if (this.resetHttpSessionAttributeOnBeanAccess && instance != null) {
         this.put(id, instance);
      }

      return instance;
   }

   protected Object getAttribute(String prefixedId) {
      HttpSession session = this.getSession(false);
      return session != null ? session.getAttribute(prefixedId) : null;
   }

   protected LockStore getLockStore() {
      LockStore lockStore = this.lockStore;
      if (lockStore == null) {
         lockStore = (LockStore)CURRENT_LOCK_STORE.get();
         if (lockStore != null) {
            return lockStore;
         }

         HttpSession session = this.getSession(false);
         if (session == null) {
            lockStore = new LockStore();
            CURRENT_LOCK_STORE.set(lockStore);

            try {
               session = this.getSession(true);
            } finally {
               CURRENT_LOCK_STORE.remove();
            }
         }

         lockStore = (LockStore)session.getAttribute("org.jboss.weld.context.beanstore.http.LockStore");
         if (lockStore == null) {
            Class var3 = AbstractSessionBeanStore.class;
            synchronized(AbstractSessionBeanStore.class) {
               lockStore = (LockStore)session.getAttribute("org.jboss.weld.context.beanstore.http.LockStore");
               if (lockStore == null) {
                  lockStore = new LockStore();
                  session.setAttribute("org.jboss.weld.context.beanstore.http.LockStore", lockStore);
               }
            }
         }

         this.lockStore = lockStore;
      }

      return lockStore;
   }
}
