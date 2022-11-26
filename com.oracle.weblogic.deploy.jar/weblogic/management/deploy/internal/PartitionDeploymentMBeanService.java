package weblogic.management.deploy.internal;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.PartitionAppRuntimeStateRuntime;
import weblogic.management.PartitionDeployerRuntime;
import weblogic.management.PartitionDeploymentManager;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StringUtils;

@Service
@Named
public final class PartitionDeploymentMBeanService implements PartitionAppRuntimeStateRuntime, PartitionDeploymentManager, PartitionDeployerRuntime {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final Map deploymentManagers = new HashMap();
   private final Map deployerRuntimes = new HashMap();

   public AppRuntimeStateRuntimeMBean createAppRuntimeStateRuntimeMBean(RuntimeMBean parent, String partitionName, AuthenticatedSubject sub) throws ManagementException {
      if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         return null;
      } else {
         SecurityHelper.assertIfNotKernel(sub);
         if (StringUtils.isEmptyString(partitionName)) {
            partitionName = "DOMAIN";
         }

         return new AppRuntimeStateRuntimeMBeanImpl(parent, partitionName);
      }
   }

   public void destroyAppRuntimeStateRuntimeMBean(String partitionName, AuthenticatedSubject sub) throws ManagementException {
   }

   public DeploymentManagerMBean createDeploymentManagerMBean(RuntimeMBean parent, String partitionName, AuthenticatedSubject sub) throws ManagementException {
      if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         return null;
      } else {
         SecurityHelper.assertIfNotKernel(sub);
         if (StringUtils.isEmptyString(partitionName)) {
            partitionName = "DOMAIN";
         }

         synchronized(this.deploymentManagers) {
            DeploymentManagerImpl result = (DeploymentManagerImpl)this.deploymentManagers.get(partitionName);
            if (result != null) {
               return result;
            } else {
               result = new DeploymentManagerImpl(parent, partitionName);
               this.deploymentManagers.put(partitionName, result);
               return result;
            }
         }
      }
   }

   public void destroyDeploymentManagerMBean(String partitionName, AuthenticatedSubject sub) throws ManagementException {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         SecurityHelper.assertIfNotKernel(sub);
         if (partitionName != null && !"DOMAIN".equals(partitionName)) {
            synchronized(this.deploymentManagers) {
               DeploymentManagerImpl result = (DeploymentManagerImpl)this.deploymentManagers.get(partitionName);
               this.deploymentManagers.remove(partitionName);
               DeploymentManagerImpl.removeDeploymentManager(partitionName);
               if (result != null) {
                  result.unregister();
               }

            }
         } else {
            throw new IllegalArgumentException("The domain DeploymentManagerMBean cannot be destroyed");
         }
      }
   }

   public DeployerRuntimeMBean createDeployerRuntimeMBean(RuntimeMBean parent, String partitionName, AuthenticatedSubject sub) throws ManagementException {
      if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         return null;
      } else {
         SecurityHelper.assertIfNotKernel(sub);
         if (StringUtils.isEmptyString(partitionName)) {
            partitionName = "DOMAIN";
         }

         synchronized(this.deployerRuntimes) {
            DeployerRuntimeImpl result = (DeployerRuntimeImpl)this.deployerRuntimes.get(partitionName);
            if (result != null) {
               return result;
            } else {
               result = new DeployerRuntimeImpl(parent, partitionName);
               this.deployerRuntimes.put(partitionName, result);
               return result;
            }
         }
      }
   }

   public void destroyDeployerRuntimeMBean(String partitionName, AuthenticatedSubject sub) throws ManagementException {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         SecurityHelper.assertIfNotKernel(sub);
         if (partitionName != null && !"DOMAIN".equals(partitionName)) {
            synchronized(this.deployerRuntimes) {
               DeployerRuntimeImpl result = (DeployerRuntimeImpl)this.deployerRuntimes.get(partitionName);
               this.deployerRuntimes.remove(partitionName);
               if (result != null) {
                  result.unregister();
               }

            }
         } else {
            throw new IllegalArgumentException("The domain DeployerRuntimeMBean cannot be destroyed");
         }
      }
   }
}
