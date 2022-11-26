package org.jboss.weld.contexts.beanstore;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public class LockStore implements Serializable {
   private static final long serialVersionUID = -698649566870070414L;
   @SuppressFBWarnings({"SE_TRANSIENT_FIELD_NOT_RESTORED"})
   private transient volatile Map locks = new HashMap();

   public LockedBean lock(BeanIdentifier id) {
      ReferenceCountedLock refLock;
      synchronized(this) {
         if (this.locks == null) {
            this.locks = new HashMap();
         }

         refLock = (ReferenceCountedLock)this.locks.get(id);
         if (refLock != null) {
            ++refLock.count;
         } else {
            refLock = new ReferenceCountedLock(id);
            this.locks.put(id, refLock);
         }
      }

      refLock.lock.lock();
      return refLock;
   }

   private class ReferenceCountedLock implements LockedBean {
      private final BeanIdentifier key;
      int count;
      final ReentrantLock lock;

      private ReferenceCountedLock(BeanIdentifier key) {
         this.count = 1;
         this.lock = new ReentrantLock();
         this.key = key;
      }

      public void unlock() {
         synchronized(LockStore.this) {
            this.lock.unlock();
            --this.count;
            if (this.count == 0) {
               LockStore.this.locks.remove(this.key);
            }

         }
      }

      // $FF: synthetic method
      ReferenceCountedLock(BeanIdentifier x1, Object x2) {
         this(x1);
      }
   }
}
