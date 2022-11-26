package weblogic.socket;

import java.security.AccessController;
import weblogic.kernel.Kernel;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLogger;
import weblogic.utils.concurrent.Semaphore;

public final class ServerThrottle {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final Semaphore lock;
   private volatile boolean enabled;
   private volatile int maxOpenSockets;

   public static ServerThrottle getServerThrottle() {
      return ServerThrottle.ServerThrottleMaker.SINGLETON;
   }

   private ServerThrottle() {
      this.enabled = false;
      this.maxOpenSockets = -1;
      int maxOpenSockets = Kernel.getConfig().getMaxOpenSockCount();
      this.enabled = maxOpenSockets > 0;
      this.lock = new Semaphore(maxOpenSockets);
   }

   public void changeMaxOpenSockets(int maxvalue) {
      this.enabled = maxvalue > 0;
      this.maxOpenSockets = maxvalue;
      this.lock.changePermits(maxvalue);
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean tryAcquirePermit() {
      return this.lock.tryAcquire();
   }

   public void acquireSocketPermit() {
      if (!this.lock.tryAcquire()) {
         ServerLogger.logMaxOpenSockets(this.maxOpenSockets, this.maxOpenSockets);
         RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
         rt.getServerRuntime().setHealthState(1, "Max Threshold Reached for Open Sockets.  MaxOpenSockCount can be tuned.");
         this.lock.acquire();
         ServerLogger.logAcceptingConnections();
         rt.getServerRuntime().setHealthState(0, "");
      }

   }

   public void decrementOpenSocketCount() {
      this.lock.release();
   }

   // $FF: synthetic method
   ServerThrottle(Object x0) {
      this();
   }

   private static final class ServerThrottleMaker {
      private static final ServerThrottle SINGLETON = new ServerThrottle();
   }
}
