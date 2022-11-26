package weblogic.management.partition.admin;

import java.security.AccessController;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.internal.PartitionFileSystemHelper;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.PartitionInterceptorServiceAPI;

@Service
@ServerServiceInterceptor(PartitionFileSystemService.class)
@Interceptor
@PartitionInterceptorServiceAPI
@ContractsProvided({PartitionFileSystemInterceptor.class, MethodInterceptor.class})
public class PartitionFileSystemInterceptor extends PartitionManagerInterceptorAdapter {
   static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   final RuntimeAccess runtimeAccess;

   public PartitionFileSystemInterceptor() {
      this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
   }

   public boolean shouldDoStart(MethodInvocation mi) {
      return true;
   }

   public void startPartitionInAdmin(MethodInvocation mi, String partitionName) throws Throwable {
      if (this.runtimeAccess.isAdminServer()) {
         PartitionMBean partition = this.getPartition(mi);
         PartitionFileSystemHelper.checkPartitionFileSystem(partition);
      }

      mi.proceed();
   }

   public void startPartition(MethodInvocation mi, String partitionName) throws Throwable {
      if (this.runtimeAccess.isAdminServer()) {
         PartitionMBean partition = this.getPartition(mi);
         PartitionFileSystemHelper.checkPartitionFileSystem(partition);
      }

      mi.proceed();
   }

   public void onPostDeletePartition(MethodInvocation mi, PartitionMBean partitionMBean) throws Throwable {
      mi.proceed();
      if (this.runtimeAccess.isAdminServer()) {
         PartitionFileSystemHelper.removePartitionFileSystem(partitionMBean);
      }

   }
}
