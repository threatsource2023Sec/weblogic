package weblogic.management.configuration.util;

import org.aopalliance.intercept.MethodInvocation;

@PartitionManagerPartitionAPI
@PartitionManagerResourceGroupAPI
@Setup
@Teardown
public class ResourceGroupManagerInterceptorAdapter extends PartitionManagerInterceptorAdapter {
   public void startResourceGroup(MethodInvocation methodInvocation, String partitionName, String ResourceGroup) throws Throwable {
      methodInvocation.proceed();
   }

   public void startResourceGroupInAdmin(MethodInvocation methodInvocation, String partitionName, String ResourceGroup) throws Throwable {
      methodInvocation.proceed();
   }

   public void suspendResourceGroup(MethodInvocation methodInvocation, String partitionName, String ResourceGroup, int timeout, boolean ignoreSessions) throws Throwable {
      methodInvocation.proceed();
   }

   public void forceSuspendResourceGroup(MethodInvocation methodInvocation, String partitionName, String ResourceGroup) throws Throwable {
      methodInvocation.proceed();
   }

   public void resumeResourceGroup(MethodInvocation methodInvocation, String partitionName, String ResourceGroup) throws Throwable {
      methodInvocation.proceed();
   }

   public void shutdownResourceGroup(MethodInvocation methodInvocation, String partitionName, String ResourceGroup, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      methodInvocation.proceed();
   }

   public void forceShutdownResourceGroup(MethodInvocation methodInvocation, String partitionName, String ResourceGroup) throws Throwable {
      methodInvocation.proceed();
   }
}
