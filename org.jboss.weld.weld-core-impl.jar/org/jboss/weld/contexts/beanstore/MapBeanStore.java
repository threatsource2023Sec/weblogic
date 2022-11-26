package org.jboss.weld.contexts.beanstore;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class MapBeanStore extends AttributeBeanStore {
   protected transient volatile LockStore lockStore;
   private final Map delegate;
   private final boolean safeIteration;

   public MapBeanStore(NamingScheme namingScheme, Map delegate) {
      this(namingScheme, delegate, false);
   }

   public MapBeanStore(NamingScheme namingScheme, Map delegate, boolean safeIteration) {
      super(namingScheme, false);
      this.delegate = delegate;
      this.safeIteration = safeIteration;
   }

   protected Object getAttribute(String prefixedId) {
      return this.delegate.get(prefixedId);
   }

   protected void removeAttribute(String prefixedId) {
      this.delegate.remove(prefixedId);
   }

   protected Iterator getAttributeNames() {
      if (this.safeIteration) {
         return (new HashSet(this.delegate.keySet())).iterator();
      } else {
         synchronized(this.delegate) {
            return (new HashSet(this.delegate.keySet())).iterator();
         }
      }
   }

   protected void setAttribute(String prefixedId, Object instance) {
      this.delegate.put(prefixedId, instance);
   }

   public LockStore getLockStore() {
      LockStore lockStore = this.lockStore;
      if (lockStore == null) {
         synchronized(this) {
            lockStore = this.lockStore;
            if (lockStore == null) {
               this.lockStore = lockStore = new LockStore();
            }
         }
      }

      return lockStore;
   }
}
