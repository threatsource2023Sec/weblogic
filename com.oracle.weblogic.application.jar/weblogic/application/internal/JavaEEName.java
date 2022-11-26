package weblogic.application.internal;

import java.io.File;
import weblogic.application.compiler.utils.ApplicationNameScanner;
import weblogic.application.compiler.utils.ApplicationNameScannerException;
import weblogic.application.utils.ManagementUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.management.utils.ApplicationNameUtils;

public enum JavaEEName {
   instance;

   public String confirmApplicationName(boolean isRedeployment, File appSourceFile, File altDescriptor, String tentativeName, String tentativeApplicationId, DomainMBean domain, String rgt, String rg, String partition) throws DeploymentException {
      if (!isRedeployment) {
         this.validateUniqueAppNameInDeploymentScope(tentativeName, domain, partition, rgt);
      }

      String versionId;
      if (tentativeName == null) {
         versionId = this.getApplicationNameFromAppSourceFile(appSourceFile, altDescriptor);
         tentativeName = versionId == null ? computeApplicationName(appSourceFile) : versionId;
      }

      versionId = this.getVersionId(tentativeName, tentativeApplicationId);
      return this.uniqueApplicationName(isRedeployment, appSourceFile.getAbsolutePath(), tentativeName, tentativeName, versionId, 1, domain, rgt, rg, partition);
   }

   private String getVersionId(String tentativeName, String tentativeApplicationId) {
      if (tentativeName == null) {
         return tentativeApplicationId;
      } else {
         return tentativeName.length() < tentativeApplicationId.length() ? tentativeApplicationId.substring(tentativeName.length()) : null;
      }
   }

   private String getApplicationNameFromAppSourceFile(File appSourceFile, File altDescriptor) throws DeploymentException {
      try {
         return ApplicationNameScanner.getApplicationNameFromDescriptor(appSourceFile, altDescriptor);
      } catch (ApplicationNameScannerException var4) {
         throw new DeploymentException("Unable to check the application name in the descriptor: " + appSourceFile, var4.getNested());
      }
   }

   public static String computeApplicationName(File descriptorFile) {
      return ApplicationNameUtils.computeApplicationName(descriptorFile);
   }

   protected String uniqueApplicationName(boolean isRedeployment, String absoluteAppSourcePath, String proposedName, String trialName, String versionId, int differentiator, DomainMBean domain) {
      return this.uniqueApplicationName(isRedeployment, absoluteAppSourcePath, proposedName, trialName, versionId, differentiator, domain, (String)null, (String)null, (String)null);
   }

   private String uniqueApplicationName(boolean isRedeployment, String absoluteAppSourcePath, String proposedName, String trialName, String versionId, int differentiator, DomainMBean domain, String rgt, String rg, String partition) {
      AppDeploymentMBean[] appMBeans = ManagementUtils.getAllAppsAndLibsForGivenScope(domain, rgt, rg, partition);
      if (appMBeans != null) {
         AppDeploymentMBean[] var12 = appMBeans;
         int var13 = appMBeans.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            AppDeploymentMBean appMBean = var12[var14];
            if (appMBean.getApplicationName().equals(trialName) && this.versionsMatch(versionId, appMBean.getVersionIdentifier()) && this.partitionMatch(partition, appMBean)) {
               if (isRedeployment) {
                  return trialName;
               }

               return this.uniqueApplicationName(isRedeployment, absoluteAppSourcePath, proposedName, proposedName + "-" + differentiator++, versionId, differentiator, domain, rgt, rg, partition);
            }
         }
      }

      return trialName;
   }

   private boolean versionsMatch(String versionId, String altVersionId) {
      return versionId != null && altVersionId != null && versionId.equals(altVersionId) || versionId == altVersionId;
   }

   private boolean partitionMatch(String partition, AppDeploymentMBean appMBean) {
      if (partition == null) {
         return appMBean.getPartitionName() == null;
      } else if (partition.equals(appMBean.getPartitionName())) {
         return true;
      } else {
         WebLogicMBean parent = appMBean.getParent();
         WebLogicMBean grandParent = parent.getParent();
         return parent instanceof ResourceGroupMBean && grandParent instanceof PartitionMBean && partition.equals(grandParent.getName());
      }
   }

   private void validateUniqueAppNameInDeploymentScope(String appName, DomainMBean domain, String partition, String rgt) throws DeploymentException {
      if (appName != null && appName.length() != 0) {
         if (rgt == null) {
            PartitionMBean partitionMBean = null;
            if (domain != null && partition != null) {
               partitionMBean = domain.lookupPartition(partition);
            }

            this.validateUniqueAppNameInPartitionOrGlobalDomain(appName, partitionMBean, domain, (String)null);
         }

      }
   }

   private void validateUniqueAppNameInPartitionOrGlobalDomain(String appName, PartitionMBean partitionMBean, DomainMBean domain, String rgt) throws DeploymentException {
      AppDeploymentMBean existingDeployment = null;
      if (partitionMBean != null) {
         existingDeployment = AppDeploymentHelper.lookupAppOrLib(appName, partitionMBean);
      } else if (domain != null) {
         if (rgt != null) {
            existingDeployment = AppDeploymentHelper.lookupAppOrLibInResourceGroupTemplate(domain, rgt, appName, false);
         } else {
            existingDeployment = AppDeploymentHelper.lookupAppOrLib(appName, domain);
         }
      }

      if (existingDeployment != null) {
         WebLogicMBean parent = existingDeployment.getParent();
         String existingType = parent == null ? "" : parent.getType();
         String existingDomain = parent == null ? "" : parent.getName();
         Loggable l = J2EELogger.logAppNameAlreadyExistsInDeploymentScopeLoggable(appName, existingType, existingDomain);
         l.log();
         throw new DeploymentException(l.getMessage());
      }
   }

   private boolean referencesResourceGroupTemplate(ResourceGroupMBean[] resourceGroupMBeans, String rgt) {
      if (rgt != null && resourceGroupMBeans != null) {
         ResourceGroupMBean[] var3 = resourceGroupMBeans;
         int var4 = resourceGroupMBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ResourceGroupMBean resourceGroupMBean = var3[var5];
            ResourceGroupTemplateMBean resourceGroupTemplateMBean = resourceGroupMBean.getResourceGroupTemplate();
            if (resourceGroupTemplateMBean != null && rgt.equals(resourceGroupTemplateMBean.getName())) {
               return true;
            }
         }
      }

      return false;
   }
}
