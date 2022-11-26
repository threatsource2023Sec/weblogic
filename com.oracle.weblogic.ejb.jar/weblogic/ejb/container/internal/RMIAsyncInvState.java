package weblogic.ejb.container.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.kernel.ResettableThreadLocal;
import weblogic.rmi.internal.FutureResultID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityServiceManager;

public final class RMIAsyncInvState {
   private static final DebugLogger DEBUG_LOGGER;
   private final Map mayInterruptIfRunningFlags = new ConcurrentHashMap();
   private final ResettableThreadLocal rmiInvId = new ResettableThreadLocal();

   public synchronized void setFutureResultID(FutureResultID id, AuthenticatedSubject kernelId) {
      if (!SecurityServiceManager.isKernelIdentity(kernelId)) {
         throw new AssertionError("Internal method, should *not* to be invoked by application code");
      } else {
         this.rmiInvId.set(id);
         if (id != null && this.mayInterruptIfRunningFlags.put(id, new AtomicBoolean(false)) != null) {
            throw new AssertionError("Should not happen");
         }
      }
   }

   public synchronized boolean updateCancelFlag(FutureResultID id, boolean mayInterruptIfRunning) {
      AtomicBoolean flag = (AtomicBoolean)this.mayInterruptIfRunningFlags.get(id);
      if (flag != null) {
         if (mayInterruptIfRunning) {
            flag.set(true);
         }
      } else if (DEBUG_LOGGER.isDebugEnabled()) {
         this.debug("Received cancel for id : " + id + " - IGNORING as execution seems to have completed.");
      }

      return false;
   }

   Object retrieveRMIInvId() {
      Object o = this.rmiInvId.get();
      if (o != null) {
         this.rmiInvId.set((Object)null);
      }

      return o;
   }

   AtomicBoolean getMayInterruptIfRunningFlag(Object id) {
      return (AtomicBoolean)this.mayInterruptIfRunningFlags.get(id);
   }

   AtomicBoolean finishedExecuting(Object id) {
      return (AtomicBoolean)this.mayInterruptIfRunningFlags.remove(id);
   }

   private void debug(String s) {
      DEBUG_LOGGER.debug("[RMIAsyncInvState] " + s);
   }

   static {
      DEBUG_LOGGER = EJBDebugService.invokeLogger;
   }
}
