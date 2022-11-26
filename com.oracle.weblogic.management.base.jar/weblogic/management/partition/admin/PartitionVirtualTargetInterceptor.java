package weblogic.management.partition.admin;

import java.lang.annotation.Annotation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.server.GlobalServiceLocator;

@Rank(2147483645)
@Service
@Interceptor
@ContractsProvided({PartitionVirtualTargetInterceptor.class, MethodInterceptor.class})
public class PartitionVirtualTargetInterceptor extends PartitionManagerInterceptorAdapter {
   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      this.cacheWorkingVirtualTargetReferences(partitionName);
      methodInvocation.proceed();
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      this.cacheWorkingVirtualTargetReferences(partitionName);
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      methodInvocation.proceed();
      this.cleanupVirtualTargetReferences(partitionName);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
      this.cleanupVirtualTargetReferences(partitionName);
   }

   private void cacheWorkingVirtualTargetReferences(String partitionName) {
      WorkingVirtualTargetManager workingVirtualTargetManager = (WorkingVirtualTargetManager)GlobalServiceLocator.getServiceLocator().getService(WorkingVirtualTargetManager.class, new Annotation[0]);
      ((WorkingVirtualTargetManagerImpl)workingVirtualTargetManager).initialize(partitionName);
   }

   private void cleanupVirtualTargetReferences(String partitionName) {
      WorkingVirtualTargetManager workingVirtualTargetManager = (WorkingVirtualTargetManager)GlobalServiceLocator.getServiceLocator().getService(WorkingVirtualTargetManager.class, new Annotation[0]);
      ((WorkingVirtualTargetManagerImpl)workingVirtualTargetManager).cleanup(partitionName);
   }
}
