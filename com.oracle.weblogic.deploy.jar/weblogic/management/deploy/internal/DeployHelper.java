package weblogic.management.deploy.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.config.DescriptorParser;
import weblogic.deploy.internal.DeployerTextFormatter;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DeployHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static void validateOptions(Properties properties, HashSet recognizedOptions, String operation) throws IllegalArgumentException {
      DeployerTextFormatter cat = new DeployerTextFormatter();
      Set badOpts = ((Hashtable)properties.clone()).keySet();
      badOpts.removeAll(recognizedOptions);
      if (badOpts.size() > 0) {
         throw new IllegalArgumentException(cat.errorOptionNotAllowed(badOpts.toString(), operation));
      }
   }

   public static DeploymentData propertiesToDeploymentData(String[] targets, String plan, Properties properties) throws IllegalArgumentException {
      DeployerTextFormatter cat = new DeployerTextFormatter();
      DeploymentData deploymentData = new DeploymentData();
      DeploymentOptions deploymentOptions = new DeploymentOptions();
      ArrayList globalTargets = new ArrayList();
      String[] subTargets;
      if (properties != null) {
         String val = null;
         val = properties.getProperty("adminMode");
         if (val != null) {
            deploymentOptions.setAdminMode(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("altDD");
         if (val != null) {
            deploymentOptions.setAltDD(val);
         }

         val = properties.getProperty("altWlsDD");
         if (val != null) {
            deploymentOptions.setAltWlsDD(val);
         }

         val = properties.getProperty("appVersion");
         if (val != null) {
            deploymentOptions.setArchiveVersion(val);
         }

         val = properties.getProperty("clusterDeploymentTimeout");
         int timeout;
         if (val != null) {
            timeout = Integer.parseInt(val);
            deploymentOptions.setClusterDeploymentTimeout(timeout);
         }

         val = properties.getProperty("defaultSubmoduleTargets");
         if (val != null) {
            deploymentOptions.setDefaultSubmoduleTargets(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("deploymentOrder");
         if (val != null) {
            deploymentOptions.setDeploymentOrder(Integer.getInteger(val));
         }

         val = properties.getProperty("deploymentPrincipalName");
         if (val != null) {
            deploymentOptions.setDeploymentPrincipalName(val);
         }

         val = properties.getProperty("forceUndeployTimeout");
         if (val != null) {
            long timeout = Long.parseLong(val);
            deploymentOptions.setForceUndeployTimeout(timeout);
         }

         val = properties.getProperty("gracefulIgnoreSessions");
         if (val != null) {
            deploymentOptions.setGracefulIgnoreSessions(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("gracefulProductionToAdmin");
         if (val != null) {
            deploymentOptions.setGracefulProductionToAdmin(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("noVersion");
         if (val != null) {
            deploymentOptions.setNoVersion(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("planVersion");
         if (val != null) {
            deploymentOptions.setPlanVersion(val);
         }

         val = properties.getProperty("library");
         if (val != null) {
            deploymentOptions.setLibrary(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("libSpecVer");
         if (val != null) {
            deploymentOptions.setLibSpecVersion(val);
         }

         val = properties.getProperty("libImplVer");
         if (val != null) {
            deploymentOptions.setLibImplVersion(val);
         }

         val = properties.getProperty("retireGracefully");
         if (val != null) {
            deploymentOptions.setRetireGracefully(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("retireTimeout");
         if (val != null) {
            timeout = Integer.parseInt(val);
            deploymentOptions.setRetireTime(timeout);
         }

         val = properties.getProperty("rmiGracePeriod");
         if (val != null) {
            timeout = Integer.parseInt(val);
            deploymentOptions.setRMIGracePeriodSecs(timeout);
         }

         val = properties.getProperty("securityModel");
         if (val != null) {
            deploymentOptions.setSecurityModel(val);
         }

         val = properties.getProperty("securityValidationEnabled");
         if (val != null) {
            deploymentOptions.setSecurityValidationEnabled(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("stageMode");
         if (val != null) {
            deploymentOptions.setStageMode(val);
         }

         val = properties.getProperty("startTask");
         if (val != null) {
            deploymentOptions.setStartTask(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("timeout");
         if (val != null) {
            timeout = Integer.parseInt(val);
            deploymentOptions.setTimeout((long)timeout);
         }

         val = properties.getProperty("useNonExclusiveLock");
         if (val != null) {
            deploymentOptions.setUseNonexclusiveLock(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("versionIdentifier");
         if (val != null) {
            deploymentOptions.setVersionIdentifier(val);
         }

         val = properties.getProperty("subModuleTargets");
         if (val != null) {
            subTargets = val.split(",");

            for(int i = 0; i < subTargets.length; ++i) {
               String[] tl = subTargets[i].split("@");
               if (tl.length == 2) {
                  deploymentData.addSubModuleTarget((String)null, tl[0], new String[]{tl[1]});
               } else {
                  if (tl.length != 3) {
                     throw new IllegalArgumentException(cat.invalidTargetSyntax(subTargets[i]));
                  }

                  deploymentData.addSubModuleTarget(tl[1], tl[0], new String[]{tl[2]});
               }
            }

            deploymentOptions.setDefaultSubmoduleTargets(false);
         }

         val = properties.getProperty("cacheInAppDirectory");
         if (val != null) {
            deploymentOptions.setCacheInAppDirectory(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("specifiedTargetsOnly");
         if (val != null) {
            deploymentOptions.setSpecifiedTargetsOnly(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("partition");
         if (val != null) {
            deploymentOptions.setPartition(val);
         }

         val = properties.getProperty("editSession");
         if (val != null) {
            deploymentOptions.setEditSessionName(val);
         }

         val = properties.getProperty("resourceGroup");
         if (val != null) {
            deploymentOptions.setResourceGroup(val);
         }

         val = properties.getProperty("resourceGroupTemplate");
         if (val != null) {
            deploymentOptions.setResourceGroupTemplate(val);
         }

         val = properties.getProperty("removePlanOverride");
         if (val != null) {
            deploymentOptions.setRemovePlanOverride(Boolean.parseBoolean(val));
         }

         val = properties.getProperty("appShutdownOnStop");
         if (val != null) {
            deploymentOptions.setAppShutdownOnStop(Boolean.parseBoolean(val));
         }

         deploymentData.setDeploymentOptions(deploymentOptions);
      }

      if (targets != null) {
         for(int n = 0; n < targets.length; ++n) {
            if (targets[n] != null) {
               if (targets[n].contains("@")) {
                  subTargets = targets[n].split("@");
                  if (subTargets == null || subTargets.length != 2) {
                     throw new IllegalArgumentException(cat.invalidTargetSyntax(targets[n]));
                  }

                  deploymentData.addModuleTarget(subTargets[0].trim(), subTargets[1].trim());
               } else {
                  globalTargets.add(targets[n]);
               }
            }
         }
      }

      deploymentData.addGlobalTargets((String[])globalTargets.toArray(new String[0]));
      if (plan != null) {
         deploymentData.setDeploymentPlan(plan);

         try {
            InputStream planInputStream = new FileInputStream(plan);
            DeploymentPlanBean deploymentPlanBean = DescriptorParser.parseDeploymentPlan(planInputStream);
            if (deploymentPlanBean != null) {
               String configRoot = deploymentPlanBean.getConfigRoot();
               if (configRoot != null) {
                  File configRootFile = new File(configRoot);
                  if (configRootFile.exists() && configRootFile.isDirectory()) {
                     deploymentData.setConfigDirectory(configRoot);
                  }
               }
            }
         } catch (Throwable var11) {
            RuntimeException rtEx = ExceptionTranslator.translateException(var11);
            throw rtEx;
         }
      }

      return deploymentData;
   }

   public static String[] getAllServerTargets() {
      String[] targets = null;
      DomainRuntimeServiceMBean domainRuntimeService = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
      DomainMBean domain = domainRuntimeService.getDomainConfiguration();
      ServerMBean[] servers = domain.getServers();
      if (servers != null) {
         targets = new String[servers.length];

         for(int n = 0; n < servers.length; ++n) {
            targets[n] = servers[n].getName();
         }
      }

      return targets;
   }
}
