package weblogic.management.partition.admin;

import java.security.AccessController;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.PartitionInterceptorServiceAPI;

@Service
@ServerServiceInterceptor(PartitionMatchMapSingletonService.class)
@Interceptor
@PartitionInterceptorServiceAPI
@ContractsProvided({PartitionMatchMapSingletonInterceptor.class, MethodInterceptor.class})
public class PartitionMatchMapSingletonInterceptor extends PartitionManagerInterceptorAdapter {
   static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   final RuntimeAccess runtimeAccess;

   public PartitionMatchMapSingletonInterceptor() {
      this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
   }

   public void startPartitionInAdmin(MethodInvocation mi, String partitionName) throws Throwable {
      PartitionMatchMapSingleton.getInstance();
      PartitionMatchMapSingleton.createMatchMap(this.runtimeAccess.getDomain());
      mi.proceed();
   }

   public void startPartition(MethodInvocation mi, String partitionName) throws Throwable {
      PartitionMatchMapSingleton.getInstance();
      PartitionMatchMapSingleton.createMatchMap(this.runtimeAccess.getDomain());
      mi.proceed();
   }
}
