package org.jboss.weld.contexts.beanstore;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AttributeBeanStore implements BoundBeanStore {
   private final HashMapBeanStore beanStore;
   private final NamingScheme namingScheme;
   private final boolean attributeLazyFetchingEnabled;
   private boolean attached;

   public AttributeBeanStore(NamingScheme namingScheme, boolean attributeLazyFetchingEnabled) {
      this.namingScheme = namingScheme;
      this.beanStore = new HashMapBeanStore();
      this.attributeLazyFetchingEnabled = attributeLazyFetchingEnabled;
   }

   public boolean detach() {
      if (this.attached) {
         this.attached = false;
         ContextLogger.LOG.beanStoreDetached(this);
         return true;
      } else {
         return false;
      }
   }

   public boolean attach() {
      if (this.attached) {
         return false;
      } else {
         this.attached = true;
         if (this.isLocalBeanStoreSyncNeeded()) {
            if (!this.beanStore.delegate().isEmpty()) {
               Iterator var1 = this.beanStore.iterator();

               while(var1.hasNext()) {
                  BeanIdentifier id = (BeanIdentifier)var1.next();
                  ContextualInstance instance = this.beanStore.get(id);
                  String prefixedId = this.getNamingScheme().prefix(id);
                  ContextLogger.LOG.updatingStoreWithContextualUnderId(instance, id);
                  this.setAttribute(prefixedId, instance);
               }
            }

            if (!this.isAttributeLazyFetchingEnabled()) {
               this.fetchUninitializedAttributes();
            }
         }

         return true;
      }
   }

   public void fetchUninitializedAttributes() {
      Iterator var1 = this.getPrefixedAttributeNames().iterator();

      while(var1.hasNext()) {
         String prefixedId = (String)var1.next();
         BeanIdentifier id = this.getNamingScheme().deprefix(prefixedId);
         if (!this.beanStore.contains(id)) {
            ContextualInstance instance = (ContextualInstance)this.getAttribute(prefixedId);
            this.beanStore.put(id, instance);
            ContextLogger.LOG.addingDetachedContextualUnderId(instance, id);
         }
      }

   }

   public boolean isAttached() {
      return this.attached;
   }

   public ContextualInstance get(BeanIdentifier id) {
      ContextualInstance instance = this.beanStore.get(id);
      if (instance == null && this.isAttached() && this.isAttributeLazyFetchingEnabled()) {
         instance = (ContextualInstance)Reflections.cast(this.getAttribute(this.namingScheme.prefix(id)));
         if (instance != null) {
            this.beanStore.put(id, instance);
         }
      }

      ContextLogger.LOG.contextualInstanceFound(id, instance, this);
      return instance;
   }

   public void put(BeanIdentifier id, ContextualInstance instance) {
      this.beanStore.put(id, instance);
      if (this.isAttached()) {
         this.setAttribute(this.namingScheme.prefix(id), instance);
      }

      ContextLogger.LOG.contextualInstanceAdded(instance.getContextual(), id, this);
   }

   public ContextualInstance remove(BeanIdentifier id) {
      ContextualInstance instance = this.beanStore.remove(id);
      if (instance != null) {
         if (this.isAttached()) {
            this.removeAttribute(this.namingScheme.prefix(id));
         }

         ContextLogger.LOG.contextualInstanceRemoved(id, this);
      }

      return instance;
   }

   public void clear() {
      Iterator it = this.iterator();

      while(it.hasNext()) {
         BeanIdentifier id = (BeanIdentifier)it.next();
         if (this.isAttached()) {
            String prefixedId = this.namingScheme.prefix(id);
            this.removeAttribute(prefixedId);
         }

         it.remove();
         ContextLogger.LOG.contextualInstanceRemoved(id, this);
      }

      ContextLogger.LOG.contextCleared(this);
   }

   public boolean contains(BeanIdentifier id) {
      return this.get(id) != null;
   }

   protected NamingScheme getNamingScheme() {
      return this.namingScheme;
   }

   public Iterator iterator() {
      Iterator iterator;
      if (this.isAttributeLazyFetchingEnabled()) {
         Set identifiers = new HashSet();
         Iterator var3 = this.beanStore.iterator();

         while(var3.hasNext()) {
            BeanIdentifier id = (BeanIdentifier)var3.next();
            identifiers.add(id);
         }

         var3 = this.getPrefixedAttributeNames().iterator();

         while(var3.hasNext()) {
            String prefixedId = (String)var3.next();
            identifiers.add(this.getNamingScheme().deprefix(prefixedId));
         }

         iterator = identifiers.iterator();
      } else {
         iterator = this.beanStore.iterator();
      }

      return iterator;
   }

   protected abstract Object getAttribute(String var1);

   protected abstract void removeAttribute(String var1);

   protected abstract Iterator getAttributeNames();

   protected Collection getPrefixedAttributeNames() {
      return this.getNamingScheme().filterIds(this.getAttributeNames());
   }

   protected abstract void setAttribute(String var1, Object var2);

   public LockedBean lock(BeanIdentifier id) {
      LockStore lockStore = this.getLockStore();
      return lockStore == null ? null : lockStore.lock(id);
   }

   protected abstract LockStore getLockStore();

   protected boolean isLocalBeanStoreSyncNeeded() {
      return true;
   }

   public boolean isAttributeLazyFetchingEnabled() {
      return this.attributeLazyFetchingEnabled;
   }
}
