package org.jboss.weld.contexts.beanstore;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public class ConcurrentHashMapBeanStore extends AbstractMapBackedBeanStore implements Serializable {
   private static final long serialVersionUID = 4770689245633688471L;
   protected Map delegate = new ConcurrentHashMap();
   private transient volatile LockStore lockStore;

   public Map delegate() {
      return this.delegate;
   }

   public String toString() {
      return "contextuals " + this.delegate;
   }

   public LockedBean lock(BeanIdentifier id) {
      LockStore lockStore = this.lockStore;
      if (lockStore == null) {
         synchronized(this) {
            lockStore = this.lockStore;
            if (lockStore == null) {
               this.lockStore = lockStore = new LockStore();
            }
         }
      }

      return lockStore.lock(id);
   }
}
