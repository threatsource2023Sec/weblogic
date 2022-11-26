package weblogic.application.utils;

import java.security.AccessController;
import weblogic.management.DomainDir;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class ManagementUtils {
   private static final AuthenticatedSubject kernelIdentity = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static boolean isRuntimeAccessAvailable() {
      return ManagementService.isRuntimeAccessInitialized();
   }

   public static boolean isProductionModeEnabled() {
      return ManagementService.getRuntimeAccess(kernelIdentity).getDomain().isProductionModeEnabled();
   }

   public static ServerMBean getServerMBean() {
      SecurityServiceManager.checkKernelPermission();
      return ManagementService.getRuntimeAccess(kernelIdentity) != null ? ManagementService.getRuntimeAccess(kernelIdentity).getServer() : null;
   }

   public static DomainMBean getDomainMBean() {
      SecurityServiceManager.checkKernelPermission();
      return ManagementService.getRuntimeAccess(kernelIdentity).getDomain();
   }

   public static String getServerName() {
      return ManagementService.getRuntimeAccess(kernelIdentity).getServerName();
   }

   public static PartitionRuntimeMBean getPartitionRuntime(String parName) {
      if (parName != null && !"DOMAIN".equals(parName)) {
         SecurityServiceManager.checkKernelPermission();
         return ManagementService.getRuntimeAccess(kernelIdentity).getServerRuntime().lookupPartitionRuntime(parName);
      } else {
         return null;
      }
   }

   public static String getDomainRootDir() {
      return DomainDir.getRootDir();
   }

   public static AppDeploymentMBean getAppDeploymentMBeanByName(String appOrLibName, DomainMBean domain) {
      return AppDeploymentHelper.lookupAppOrLib(appOrLibName, domain);
   }

   public static void destroyAppDeploymentMBeanByName(AppDeploymentMBean mbean, DomainMBean domain) {
      AppDeploymentHelper.destroyAppOrLib(mbean, domain);
   }

   public static AppDeploymentMBean[] getAllAppsAndLibs(DomainMBean domain) {
      return AppDeploymentHelper.getAppsAndLibs(domain);
   }

   public static AppDeploymentMBean[] getAllAppsAndLibsForGivenScope(DomainMBean domain, String rgt, String rg, String partition) {
      return AppDeploymentHelper.getAppsAndLibsForGivenScope(domain, rgt, rg, partition, false);
   }

   public static ResourceGroupMBean getResourceGroupMBean(BasicDeploymentMBean appMBean) {
      return AppDeploymentHelper.getResourceGroupMBean(appMBean);
   }

   public static boolean isDeployedThroughResourceGroupTemplate(BasicDeploymentMBean appMBean) {
      return AppDeploymentHelper.isDeployedThroughRGT(appMBean);
   }
}
