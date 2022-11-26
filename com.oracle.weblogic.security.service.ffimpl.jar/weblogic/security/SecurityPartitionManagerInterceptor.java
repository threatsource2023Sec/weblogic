package weblogic.security;

import java.security.AccessController;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.utils.PartitionUtils;
import weblogic.utils.annotation.Secure;

@Service
@Interceptor
@ContractsProvided({SecurityPartitionManagerInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(PreSecurityService.class)
@Secure
public class SecurityPartitionManagerInterceptor extends PartitionManagerInterceptorAdapter {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      this.startPartitionRealm(partitionName);
      methodInvocation.proceed();
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      this.startPartitionRealm(partitionName);
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      methodInvocation.proceed();
      this.shutdownPartitionRealm(partitionName);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
      this.shutdownPartitionRealm(partitionName);
   }

   private void startPartitionRealm(String partitionName) {
      if (partitionName != null) {
         String realmName = PartitionUtils.getRealmName(partitionName);
         SecurityServiceManager.initializeRealm(kernelId, realmName);
      }
   }

   private void shutdownPartitionRealm(String partitionName) {
      if (partitionName != null) {
         String realmName = PartitionUtils.getRealmName(partitionName);
         if (realmName != null) {
            boolean shutdown = true;
            PartitionRuntimeMBean[] partitionRuntimes = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getPartitionRuntimes();
            if (partitionRuntimes != null && partitionRuntimes.length > 0) {
               PartitionRuntimeMBean[] var5 = partitionRuntimes;
               int var6 = partitionRuntimes.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  PartitionRuntimeMBean partition = var5[var7];
                  String pName = partition.getName();
                  if (pName != null && !pName.equals(partitionName)) {
                     String rName = PartitionUtils.getRealmName(pName);
                     if (rName != null && rName.equals(realmName)) {
                        shutdown = false;
                        break;
                     }
                  }
               }
            }

            if (shutdown) {
               SecurityServiceManager ssm = PreSecurityService.getSingleton().getSecurityServiceManager(kernelId);
               ssm.shutdownRealm(kernelId, realmName);
            }
         }

      }
   }
}
