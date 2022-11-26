package weblogic.transaction.internal;

import java.security.AccessController;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.JTAPartitionMBean;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Interceptor
@ContractsProvided({JTAPartitionManagerInterceptor.class, MethodInterceptor.class})
public class JTAPartitionManagerInterceptor extends PartitionManagerInterceptorAdapter {
   private static final boolean isPartitionLifecycleOldmodel = false;
   static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   final RuntimeAccess runtimeAccess;

   public JTAPartitionManagerInterceptor() {
      this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      this.startJTAPartition(partitionName, methodInvocation);
      methodInvocation.proceed();
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      this.startJTAPartition(partitionName, methodInvocation);
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      methodInvocation.proceed();
      this.shutdownJTAPartition(partitionName);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
      this.shutdownJTAPartition(partitionName);
   }

   private void startJTAPartition(String partitionName, MethodInvocation methodInvocation) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "JTAPartitionManagerInterceptor partition start called: " + partitionName);
      }

      if (partitionName != null) {
         PartitionRuntimeMBean partitionRuntime = (PartitionRuntimeMBean)this.getPartitionRuntime(methodInvocation);
         JTAPartitionMBean jtaPartition = this.runtimeAccess.getDomain().lookupPartition(partitionRuntime.getName()).getJTAPartition();
         JTAPartitionService jtaPartitionService = JTAPartitionService.getOrCreateJTAPartitionService();
         jtaPartitionService.initializeJTAPartitionManager(jtaPartition, partitionRuntime);
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "JTAPartitionManagerInterceptor partition start: " + partitionName + " partitionRuntime:" + partitionRuntime + " jtaPartitionRuntime:" + jtaPartition);
         }

      }
   }

   private void shutdownJTAPartition(String partitionName) {
      if (TxDebug.JTA2PC.isDebugEnabled()) {
         TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "JTAPartitionManagerInterceptor partition shutdown called: " + partitionName);
      }

      if (partitionName != null) {
         JTAPartitionService jtaPartitionService = JTAPartitionService.getOrCreateJTAPartitionService();
         jtaPartitionService.deinitializeJTAPartitionManager(partitionName);
         if (TxDebug.JTA2PC.isDebugEnabled()) {
            TxDebug.txdebug(TxDebug.JTA2PC, (TransactionImpl)null, "JTAPartitionManagerInterceptor partition shutdown: " + partitionName);
         }

      }
   }
}
