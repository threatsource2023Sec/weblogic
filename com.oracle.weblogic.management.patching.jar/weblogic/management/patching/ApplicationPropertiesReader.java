package weblogic.management.patching;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;
import weblogic.management.utils.AppDeploymentHelper;

public class ApplicationPropertiesReader {
   public List readApplicationProperties(String applicationPropertiesPath, String target) throws JSONException, IOException, IllegalArgumentException, ManagementException {
      ArrayList properties = new ArrayList();
      this.assertString("applicationPropertiesFileName", applicationPropertiesPath);
      File jsonPropertiesFile = new File(applicationPropertiesPath);
      String propType = PatchingMessageTextFormatter.getInstance().getApplicationString();
      if (!jsonPropertiesFile.exists()) {
         PatchingDebugLogger.debug("Unable to find application properties file: " + applicationPropertiesPath);
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().propsFileDoesNotExist(propType, applicationPropertiesPath));
      } else if (!jsonPropertiesFile.canRead()) {
         PatchingDebugLogger.debug("Unable to read application properties file: " + applicationPropertiesPath);
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().cannotReadFile(propType, applicationPropertiesPath));
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Reading properties file: " + applicationPropertiesPath);
         }

         byte[] encoded = Files.readAllBytes(Paths.get(applicationPropertiesPath));
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Decoding bytes");
         }

         String jsonContent = new String(encoded, Charset.defaultCharset());
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Loading JSON object");
         }

         JSONObject readJson = new JSONObject(jsonContent);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Loaded JSON object: " + readJson);
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Extracting applications array");
         }

         JSONArray applicationsArray = readJson.getJSONArray("applications");
         if (PatchingDebugLogger.isDebugEnabled()) {
            if (applicationsArray == null) {
               PatchingDebugLogger.debug("ApplicationArray is null");
               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().cannotLoadAppsProps(applicationPropertiesPath));
            }

            PatchingDebugLogger.debug("Applications extracted. Length is: " + applicationsArray.length());
         }

         int i;
         if (applicationsArray != null && applicationsArray.length() > 0) {
            for(i = 0; i < applicationsArray.length(); ++i) {
               ApplicationProperties applicationProperties = new ApplicationProperties();
               JSONObject appObject = applicationsArray.getJSONObject(i);
               String appName = appObject.getString("applicationName");
               String patchedLoc = appObject.getString("patchedLocation");
               String backupLoc = appObject.getString("backupLocation");
               applicationProperties.setApplicationName(appName);
               applicationProperties.setPatchedLocation(patchedLoc);
               applicationProperties.setBackupLocation(backupLoc);
               if (appObject.has("partition") && appObject.has("resourceGroupTemplate")) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().cannotSupplyBothPartitionAndRGT(appName, applicationPropertiesPath));
               }

               String planLoc;
               if (appObject.has("resourceGroupTemplate")) {
                  planLoc = appObject.getString("resourceGroupTemplate");
                  boolean rgtExists = PartitionUtils.doesRGTExist(planLoc);
                  if (!rgtExists) {
                     throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().artifactNotFound(planLoc, "resourceGroupTemplate"));
                  }

                  if (PartitionUtils.isPartitionContext()) {
                     throw new ManagementException(PatchingMessageTextFormatter.getInstance().invalidArgument(PartitionUtils.getPartitionInfo(), planLoc));
                  }

                  applicationProperties.setResourceGroupTemplateName(planLoc);
               }

               if (appObject.has("partition")) {
                  planLoc = appObject.getString("partition");
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().artifactNotFound(planLoc, "partition"));
               }

               if (PartitionUtils.isPartitionContext()) {
                  applicationProperties.setPartitionFieldName(PartitionUtils.getPartitionInfo());
               }

               if (appObject.has("planLocation")) {
                  planLoc = appObject.getString("planLocation");
                  applicationProperties.setPlanLocation(planLoc);
               }

               if (appObject.has("removePlanOverride")) {
                  boolean removePlanOverride = appObject.getBoolean("removePlanOverride");
                  applicationProperties.setRemovePlanOverride(removePlanOverride);
               }

               properties.add(applicationProperties);
            }
         } else if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Application Array is empty");
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            for(i = 0; i < properties.size(); ++i) {
               PatchingDebugLogger.debug("Application Properties loaded for application: " + ((ApplicationProperties)properties.get(i)).getApplicationName());
            }
         }

         this.augmentApplicationProperties(properties, target);
         return properties;
      }
   }

   public void augmentApplicationProperties(List applicationPropertiesList, String targetString) throws ManagementException, IllegalArgumentException {
      if (applicationPropertiesList != null && applicationPropertiesList.size() != 0) {
         DomainMBean domain = PartitionUtils.getDomainMBean();
         ResourceGroupMBean[] resourceGroupMBeans = PartitionUtils.getResourceGroupMBeans();
         ResourceGroupTemplateMBean[] resourceGroupTemplateMBeans = PartitionUtils.getResourceGroupTemplateMBeans();
         Set targets = new HashSet(Arrays.asList(targetString.split(",")));
         TopologyInspector.TargetType targetType = TopologyInspector.calculateTargetType(targetString);
         Set partitionNamesProvided = (Set)applicationPropertiesList.stream().map((appProp) -> {
            return appProp.getPartitionFieldName();
         }).distinct().collect(Collectors.toSet());
         if (targetType == TopologyInspector.TargetType.PARTITION) {
            partitionNamesProvided.removeAll(targets);
            if (!partitionNamesProvided.isEmpty()) {
               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().partitionsNotTargeted((String)partitionNamesProvided.stream().collect(Collectors.joining(", "))));
            }
         }

         Iterator var9 = applicationPropertiesList.iterator();

         while(true) {
            while(var9.hasNext()) {
               ApplicationProperties applicationProperties = (ApplicationProperties)var9.next();
               String appName = applicationProperties.getApplicationName();
               String scope = this.getScopeForApp(applicationProperties);
               AppDeploymentMBean appMBean = null;
               if (PartitionUtils.doesRGTExist(scope)) {
                  ResourceGroupMBean[] var18 = resourceGroupMBeans;
                  int var19 = resourceGroupMBeans.length;

                  for(int var16 = 0; var16 < var19; ++var16) {
                     ResourceGroupMBean globalResourceGroupMBean = var18[var16];
                     if (globalResourceGroupMBean.getResourceGroupTemplate() != null && globalResourceGroupMBean.getResourceGroupTemplate().getName().equals(scope)) {
                        appMBean = AppDeploymentHelper.lookupAppOrLibInResourceGroup(domain, globalResourceGroupMBean.getName(), (String)null, appName, false);
                        if (appMBean == null || !this.isDeployedThroughRGT(appMBean, globalResourceGroupMBean.getResourceGroupTemplate())) {
                           throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().appsNotFoundInScope(appName, scope, "resourceGroupTemplate"));
                        }

                        if (PatchingDebugLogger.isDebugEnabled()) {
                           PatchingDebugLogger.debug("ApplicationProperties for " + appMBean.getName() + " examining global resourceGroup: " + globalResourceGroupMBean.getName());
                        }

                        applicationProperties.addGlobalResourceGroup(globalResourceGroupMBean.getName());
                     }
                  }

                  if (appMBean == null) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("ApplicationProperties couldn't find MBean for app: " + appName);
                     }

                     throw new ManagementException(PatchingMessageTextFormatter.getInstance().appsNotFoundInScope(appName, scope, "resource group template"));
                  }

                  this.setAppInfo(applicationProperties, appMBean);
               } else {
                  appMBean = AppDeploymentHelper.lookupAppOrLib(appName, domain);
                  if (appMBean == null) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("ApplicationProperties couldn't find MBean for app: " + appName);
                     }

                     throw new ManagementException(PatchingMessageTextFormatter.getInstance().appsNotFoundInScope(appName, scope, "global"));
                  }

                  ResourceGroupMBean appRG = AppDeploymentHelper.getResourceGroupMBean(appMBean);
                  if (appRG != null) {
                     ResourceGroupTemplateMBean appRGT = appRG.getResourceGroupTemplate();
                     if (appRGT != null && this.isDeployedThroughRGT(appMBean, appRGT)) {
                        throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidScope(scope, appRGT.getName(), appName));
                     }

                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("ApplicationProperties for " + appMBean.getName() + " examining global resource group: " + appRG.getName());
                     }

                     applicationProperties.addGlobalResourceGroup(appRG.getName());
                  }

                  this.setAppInfo(applicationProperties, appMBean);
               }
            }

            return;
         }
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("augmentApplicationProperties called with no properties to augment");
         }

      }
   }

   private boolean isDeployedThroughRGT(AppDeploymentMBean appMBean, ResourceGroupTemplateMBean rgtMBean) {
      BasicDeploymentMBean[] deps = rgtMBean.getBasicDeployments();
      boolean testRGTapp = AppDeploymentHelper.isDeployedThroughRGT(appMBean);
      PatchingDebugLogger.debug("app :" + appMBean.getName() + " isDeployedThroughRGT: " + testRGTapp);
      boolean found = false;

      for(int i = 0; i < deps.length; ++i) {
         if (appMBean.getName().equals(deps[i].getName())) {
            found = true;
            break;
         }
      }

      return found;
   }

   private String getScopeForApp(ApplicationProperties applicationProperties) {
      String scope;
      if (!applicationProperties.getPartitionFieldName().isEmpty()) {
         scope = applicationProperties.getPartitionFieldName();
      } else if (!applicationProperties.getResourceGroupTemplateName().isEmpty()) {
         scope = applicationProperties.getResourceGroupTemplateName();
      } else {
         scope = PartitionUtils.DOMAIN_SCOPE;
      }

      return scope;
   }

   private void setAppInfo(ApplicationProperties applicationProperties, AppDeploymentMBean appMBean) {
      applicationProperties.setCurrentLocation(appMBean.getAbsoluteSourcePath());
      String appName = appMBean.getName();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Source path for " + appName + " is: " + applicationProperties.getCurrentLocation());
      }

      String appStageMode = appMBean.getStagingMode();
      if (appStageMode != null && !appStageMode.equalsIgnoreCase("" + null)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Stage mode for " + appName + " is: " + appStageMode);
         }

         applicationProperties.setStageMode(appStageMode);
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Stage mode for " + appName + " is null, checking plan staging mode");
         }

         String planStagingMode = appMBean.getPlanStagingMode();
         if (planStagingMode != null && !planStagingMode.equalsIgnoreCase("" + null)) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Plan stage mode for " + appName + " is " + planStagingMode);
            }
         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Plan stage mode for " + appName + " is null, will use default server staging mode");
            }

            appStageMode = null;
         }
      }

      Path path = Paths.get(applicationProperties.getCurrentLocation());
      String stagingSubdirectory = appMBean.getApplicationIdentifier() + File.separator + path.getFileName();
      applicationProperties.setStagingSubdirectory(stagingSubdirectory);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Stage subdirectory is: " + stagingSubdirectory);
      }

      String targetStagingMode = null;
      TargetMBean[] targetMBeanArray = appMBean.getTargets();
      if (targetMBeanArray != null && targetMBeanArray.length > 0) {
         this.applyApplicationServerTargets(applicationProperties, targetMBeanArray);
      }

      if (applicationProperties.getServerNames().isEmpty() && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Unable to get targets for app: " + appName);
      }

      if (applicationProperties.getStageMode() == null) {
         applicationProperties.setStageMode(ApplicationProperties.StageMode.DEFAULT_STAGE);
      }

   }

   private void applyApplicationServerTargets(ApplicationProperties applicationProperties, TargetMBean[] targetMBeanArray) {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < targetMBeanArray.length; ++i) {
         sb.append(targetMBeanArray[i].getName());
         sb.append("(");
         sb.append(targetMBeanArray[i].getServerNames());
         sb.append(")");
         if (i + 1 < targetMBeanArray.length) {
            sb.append(",");
         }

         Set serverNameSet = targetMBeanArray[i].getServerNames();
         Iterator var6 = serverNameSet.iterator();

         while(var6.hasNext()) {
            String serverName = (String)var6.next();
            applicationProperties.addServerName(serverName);
         }
      }

      applicationProperties.setTargets(sb.toString());
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Setting target string to: " + applicationProperties.getTargets() + " for app: " + applicationProperties);
      }

   }

   private void augmentPropertiesFromPartition(ApplicationProperties applicationProperties, AppDeploymentMBean appMBean, PartitionMBean targetedPartition) {
      if (appMBean != null) {
         applicationProperties.addPartitionName(targetedPartition.getName());
         WebLogicMBean parent = appMBean.getParent();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ApplicationProperties for " + appMBean.getName() + " examining parent: " + parent.getName() + " with type: " + parent.getType());
         }

         if (parent instanceof ResourceGroupMBean) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("ApplicationProperties for " + appMBean.getName() + " examining RG and RGT mbeans: " + parent.getName());
            }

            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("ApplicationProperties for  " + appMBean.getName() + " setting ResourceGroupName: " + parent.getName() + " for Partition: " + appMBean.getPartitionName());
            }

            applicationProperties.addPartitionResourceGroup(appMBean.getPartitionName(), parent.getName());
         }

         TargetMBean[] targetMBeans = ((ResourceGroupMBean)parent).findEffectiveTargets();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Targets on RG is: " + (targetMBeans != null ? "" + targetMBeans.length : "null"));
         }

         if (targetMBeans != null && targetMBeans.length > 0) {
            this.applyApplicationServerTargets(applicationProperties, targetMBeans);
         } else if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Unable to get targets for app: " + applicationProperties.getApplicationName() + " on partition: " + appMBean.getPartitionName());
         }
      }

   }

   public void assertString(String paramName, String str) {
      if (str == null || str.length() == 0) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().undefinedParameter(paramName));
      }
   }

   protected String getStagingModeForServer(String serverName, ServerMBean[] serverMBeans) {
      String serverStagingMode = null;
      if (serverName != null && !serverName.isEmpty() && serverMBeans != null && serverMBeans.length >= 1) {
         ServerMBean[] var4 = serverMBeans;
         int var5 = serverMBeans.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ServerMBean serverMBean = var4[var6];
            if (serverName.equals(serverMBean.getName())) {
               serverStagingMode = serverMBean.getStagingMode();
               if (serverStagingMode == null || serverStagingMode.isEmpty() || serverStagingMode.equals("" + null)) {
                  serverStagingMode = null;
               }
               break;
            }
         }
      }

      return serverStagingMode;
   }
}
