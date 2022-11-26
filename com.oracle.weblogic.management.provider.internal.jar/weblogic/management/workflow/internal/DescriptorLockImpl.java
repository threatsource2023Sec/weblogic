package weblogic.management.workflow.internal;

import java.lang.ref.WeakReference;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.workflow.DescriptorLock;
import weblogic.management.workflow.DescriptorLockHandle;

@Service
public class DescriptorLockImpl implements DescriptorLock {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private final Object descriptorLockLock = new Object();
   private WeakReference outstandingLock;

   public DescriptorLockHandle lock(long waitTimeInMilliseconds) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Attempting to acquire Descriptor lock with wait time of " + waitTimeInMilliseconds);
      }

      if (waitTimeInMilliseconds < 0L) {
         waitTimeInMilliseconds = Long.MAX_VALUE;
      }

      synchronized(this.descriptorLockLock) {
         if (this.outstandingLock != null && this.outstandingLock.get() == null) {
            this.outstandingLock = null;
         }

         while(this.outstandingLock != null && waitTimeInMilliseconds > 0L) {
            long elapsedTime = System.currentTimeMillis();

            try {
               this.descriptorLockLock.wait(waitTimeInMilliseconds);
            } catch (InterruptedException var8) {
               throw new RuntimeException(var8);
            }

            elapsedTime = System.currentTimeMillis() - elapsedTime;
            waitTimeInMilliseconds -= elapsedTime;
         }

         if (this.outstandingLock != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Did not acquire Descriptor lock");
            }

            return null;
         } else {
            DescriptorLockHandleImpl retVal = new DescriptorLockHandleImpl(this);
            this.outstandingLock = new WeakReference(retVal);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Acquired Descriptor lock " + retVal);
            }

            return retVal;
         }
      }
   }

   private boolean unlock(DescriptorLockHandleImpl handle) {
      if (handle == null) {
         return false;
      } else {
         synchronized(this.descriptorLockLock) {
            if (this.outstandingLock == null) {
               return false;
            } else {
               DescriptorLockHandleImpl existingLock = (DescriptorLockHandleImpl)this.outstandingLock.get();
               if (existingLock == null) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Releasing Descriptor lock " + handle + " due to GC cleanup");
                  }

                  this.outstandingLock = null;
                  this.descriptorLockLock.notifyAll();
                  return false;
               } else if (existingLock.equals(handle)) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Releasing Descriptor lock " + handle);
                  }

                  this.outstandingLock = null;
                  this.descriptorLockLock.notifyAll();
                  return true;
               } else {
                  return false;
               }
            }
         }
      }
   }

   public String toString() {
      return "DescriptorLockImpl(" + System.identityHashCode(this) + ")";
   }

   private static final class DescriptorLockHandleImpl implements DescriptorLockHandle {
      private final DescriptorLockImpl parent;

      private DescriptorLockHandleImpl(DescriptorLockImpl parent) {
         this.parent = parent;
      }

      public boolean unlock() {
         return this.parent.unlock(this);
      }

      public void finalize() {
         this.unlock();
      }

      public String toString() {
         return "DescriptorLockHandleImpl(" + this.parent + "," + System.identityHashCode(this) + ")";
      }

      // $FF: synthetic method
      DescriptorLockHandleImpl(DescriptorLockImpl x0, Object x1) {
         this(x0);
      }
   }
}
