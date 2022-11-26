package weblogic.management.mbeanservers.partition;

import java.security.AccessController;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.mbeanservers.domainruntime.internal.DomainRuntimeServerService;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Interceptor
@ContractsProvided({PartitionMbsLifecycleInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(DomainRuntimeServerService.class)
public class PartitionMbsLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   @Inject
   private PartitionedDomainRuntimeMbsManager pmm;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      this.startPartition(methodInvocation);
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      this.startPartition(methodInvocation);
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      this.shutdownPartition(methodInvocation);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      this.shutdownPartition(methodInvocation);
   }

   private void startPartition(MethodInvocation methodInvocation) throws Throwable {
      PartitionRuntimeMBean pr = (PartitionRuntimeMBean)this.getPartitionRuntime(methodInvocation);
      this.pmm.registerInJndi(pr.getName(), this.pmm.getJndiName(), ManagementService.getDomainRuntimeMBeanServer(kernelId));
      methodInvocation.proceed();
   }

   private void shutdownPartition(MethodInvocation methodInvocation) throws Throwable {
      methodInvocation.proceed();
      PartitionRuntimeMBean pr = (PartitionRuntimeMBean)this.getPartitionRuntime(methodInvocation);
      this.pmm.unregisterFromJndi(pr.getName(), this.pmm.getJndiName());
   }
}
