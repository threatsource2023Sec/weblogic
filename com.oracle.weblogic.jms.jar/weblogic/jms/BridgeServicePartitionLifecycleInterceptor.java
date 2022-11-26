package weblogic.jms;

import java.security.AccessController;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.bridge.internal.BridgeDebug;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;

@Service
@Interceptor
@ContractsProvided({BridgeServicePartitionLifecycleInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(BridgeService.class)
public class BridgeServicePartitionLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ComponentInvocationContextManager CICM;

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("BridgeServicePartitionLifecycleInterceptor.startPartitionInAdmin(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.startPartition(methodInvocation, partitionName);
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("BridgeServicePartitionLifecycleInterceptor.resumePartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "resumePartition");
      methodInvocation.proceed();
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("BridgeServicePartitionLifecycleInterceptor.startPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.startBridgeService(partitionName);
      methodInvocation.proceed();
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("ENTER BridgeServicePartitionLifecycleInterceptor.suspendPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "suspendPartition");
      methodInvocation.proceed();
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("ENTER BridgeServicePartitionLifecycleInterceptor.forceSuspendPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceSuspendPartition");
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("ENTER BridgeServicePartitionLifecycleInterceptor.shutdownPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownPartition");
      methodInvocation.proceed();
      this.shutdownBridgeService(partitionName, false);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("ENTER BridgeServicePartitionLifecycleInterceptor.forceShutdownPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceShutdownPartition");
      methodInvocation.proceed();
      this.shutdownBridgeService(partitionName, true);
   }

   private void startBridgeService(String partitionName) throws Throwable {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("SETUP_BridgeServicePartitionLifecycleInterceptor.startBridgeService(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startBridgeService");
      BridgeService.getService();
      BridgeService.getPartitionBridgeService();
   }

   private void shutdownBridgeService(String partitionName, boolean force) throws ServiceFailureException {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("TEARDOWN_BridgeServicePartitionLifecycleInterceptor.shutdownBridgeService(" + partitionName + ", force=" + force + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownBridgeService");
      PartitionBridgeService service = BridgeService.removePartitionBridgeService(partitionName);
      if (service != null) {
         service.stop(force);
      }

   }

   private static void checkCIC(String partitionName, String method) throws ServiceFailureException {
      ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
      if (!cic.getPartitionName().equals(partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + cic + "], expected partition name " + partitionName + " on " + method + "() in BridgeServicePartitionLifecycleInterceptor");
      }
   }

   static {
      CICM = ComponentInvocationContextManager.getInstance(KERNEL_ID);
   }
}
