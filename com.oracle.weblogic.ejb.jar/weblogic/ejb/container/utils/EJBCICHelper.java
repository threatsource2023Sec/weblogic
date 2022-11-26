package weblogic.ejb.container.utils;

import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.kernel.KernelStatus;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class EJBCICHelper {
   private static final ComponentInvocationContextManager CICM;
   protected static final DebugLogger debugLogger;
   private static final ManagedInvocationContext NOOP_SINGLETON;

   public static ManagedInvocationContext pushEJBCIC(ComponentInvocationContext cic) {
      ManagedInvocationContext mic = NOOP_SINGLETON;
      if (cic != null && CICM != null) {
         if (debugLogger.isDebugEnabled()) {
            debug("EJBCICHelper.pushEJBCIC:  push cic=" + cic.getPartitionName());
         }

         mic = CICM.setCurrentComponentInvocationContext(cic);
      } else {
         mic = NOOP_SINGLETON;
      }

      return (ManagedInvocationContext)(debugLogger.isDebugEnabled() ? new WrappedMIC(mic) : mic);
   }

   private static void debug(String s) {
      debugLogger.debug("[EJBCICHelper] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
      if (KernelStatus.isServer()) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         CICM = ComponentInvocationContextManager.getInstance(kernelId);
      } else {
         CICM = ComponentInvocationContextManager.getInstance();
      }

      NOOP_SINGLETON = new NOOPMIC();
   }

   static class WrappedMIC implements ManagedInvocationContext {
      private ManagedInvocationContext mic;

      WrappedMIC(ManagedInvocationContext m) {
         this.mic = m;
      }

      public boolean isNOOP() {
         return this.mic instanceof NOOPMIC;
      }

      public void close() {
         if (EJBCICHelper.debugLogger.isDebugEnabled()) {
            EJBCICHelper.debug("CICHelper.closeMIC:  about to pop CIC");
         }

         this.mic.close();
         if (EJBCICHelper.debugLogger.isDebugEnabled()) {
            EJBCICHelper.debug("CICHelper.closeMIC:  popped CIC");
         }

      }
   }

   static class NOOPMIC implements ManagedInvocationContext {
      public void close() {
      }
   }
}
