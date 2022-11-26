package weblogic.messaging.common;

import java.security.AccessController;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dispatcher.DispatcherPartitionContext;
import weblogic.kernel.KernelStatus;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JMSCICHelper {
   private static final ComponentInvocationContextManager CICM;
   private static final ManagedInvocationContext NOOP_SINGLETON;

   public static String getPartitionId() {
      if (CICM == null) {
         return null;
      } else {
         String partitionId = CICM.getCurrentComponentInvocationContext().getPartitionId();
         if (JMSDebug.JMSCICHelper.isDebugEnabled()) {
            JMSDebug.JMSCICHelper.debug("JMSCICHelper.getPartitionId:  return " + partitionId);
         }

         return partitionId;
      }
   }

   public static String getPartitionName() {
      if (CICM == null) {
         return null;
      } else {
         String partitionName = CICM.getCurrentComponentInvocationContext().getPartitionName();
         if (JMSDebug.JMSCICHelper.isDebugEnabled()) {
            JMSDebug.JMSCICHelper.debug("JMSCICHelper.getPartitionName:  return " + partitionName);
         }

         return partitionName;
      }
   }

   public static ComponentInvocationContext getCurrentCIC() {
      if (CICM == null) {
         return null;
      } else {
         ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
         if (JMSDebug.JMSCICHelper.isDebugEnabled()) {
            JMSDebug.JMSCICHelper.debug("JMSCICHelper.getCurrentCIC:  returning current cic");
         }

         return cic;
      }
   }

   public static ManagedInvocationContext pushJMSCIC(ComponentInvocationContext cic) {
      ManagedInvocationContext mic;
      if (cic != null && CICM != null && !cic.equals(CICM.getCurrentComponentInvocationContext())) {
         if (JMSDebug.JMSCICHelper.isDebugEnabled()) {
            JMSDebug.JMSCICHelper.debug("JMSCICHelper.pushJMSCIC:  push cic=" + cic.getPartitionName());
         }

         mic = CICM.setCurrentComponentInvocationContext(cic);
      } else {
         if (JMSDebug.JMSCICHelper.isDebugEnabled()) {
            JMSDebug.JMSCICHelper.debug("JMSCICHelper.pushJMSCIC:  cic parm same as currentCic (no-op)");
         }

         mic = NOOP_SINGLETON;
      }

      return (ManagedInvocationContext)(JMSDebug.JMSCICHelper.isDebugEnabled() ? new WrappedMIC(mic) : mic);
   }

   public static ManagedInvocationContext pushJMSCICByPartitionName(DispatcherPartitionContext dispatcherPartitionContext, String partitionName) {
      if (CICM != null && partitionName != null && dispatcherPartitionContext != null) {
         ComponentInvocationContext cic = dispatcherPartitionContext.getCIC(partitionName);
         if (JMSDebug.JMSCICHelper.isDebugEnabled()) {
            JMSDebug.JMSCICHelper.debug("JMSCICHelper.pushJMSCICByPartitionName:  delegate " + cic);
         }

         return pushJMSCIC(cic);
      } else {
         if (JMSDebug.JMSCICHelper.isDebugEnabled()) {
            JMSDebug.JMSCICHelper.debug("JMSCICHelper.pushJMSCICByPartitionName:  set to " + null);
         }

         ManagedInvocationContext mic = NOOP_SINGLETON;
         if (JMSDebug.JMSCICHelper.isDebugEnabled()) {
            JMSDebug.JMSCICHelper.debug("JMSCICHelper.pushJMSCICByPartitionName:  pushed partition " + partitionName);
            return new WrappedMIC(mic);
         } else {
            return mic;
         }
      }
   }

   static {
      if (KernelStatus.isServer()) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         CICM = ComponentInvocationContextManager.getInstance(kernelId);
      } else {
         CICM = null;
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
         if (JMSDebug.JMSCICHelper.isDebugEnabled()) {
            JMSDebug.JMSCICHelper.debug("CICHelper.closeMIC:  about to pop CIC");
         }

         this.mic.close();
         if (JMSDebug.JMSCICHelper.isDebugEnabled()) {
            JMSDebug.JMSCICHelper.debug("CICHelper.closeMIC:  popped CIC");
         }

      }
   }

   static class NOOPMIC implements ManagedInvocationContext {
      public void close() {
      }
   }
}
