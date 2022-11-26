package weblogic.management.utils;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;

public final class AppDeploymentHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger deploymentLogger = DebugLogger.getDebugLogger("DebugDeployment");

   private AppDeploymentHelper() {
   }

   public static AppDeploymentMBean[] getAppsAndLibs(DomainMBean domain) {
      return getAppsAndLibs(domain, false);
   }

   public static AppDeploymentMBean[] getAppsAndLibs(DomainMBean domain, boolean appOnly) {
      ArrayList appsAndLibs = new ArrayList();
      AppDeploymentMBean[] apps = domain.getInternalAppDeployments();
      if (apps != null) {
         appsAndLibs.addAll(Arrays.asList(apps));
      }

      apps = domain.getAppDeployments();
      if (apps != null) {
         appsAndLibs.addAll(Arrays.asList(apps));
      }

      if (!appOnly) {
         AppDeploymentMBean[] apps = domain.getInternalLibraries();
         if (apps != null) {
            appsAndLibs.addAll(Arrays.asList(apps));
         }

         apps = domain.getLibraries();
         if (apps != null) {
            appsAndLibs.addAll(Arrays.asList(apps));
         }
      }

      return (AppDeploymentMBean[])((AppDeploymentMBean[])appsAndLibs.toArray(new AppDeploymentMBean[appsAndLibs.size()]));
   }

   public static AppDeploymentMBean[] getAppsAndLibs(PartitionMBean pBean) {
      return getAppsAndLibs(pBean, false);
   }

   public static AppDeploymentMBean[] getAppsAndLibs(PartitionMBean pBean, boolean appOnly) {
      return new AppDeploymentMBean[0];
   }

   public static AppDeploymentMBean[] getAppsAndLibsForResourceGroupTemplate(DomainMBean domain, String rgt, boolean appOnly) {
      if (rgt != null) {
         ResourceGroupTemplateMBean rgtm = domain.lookupResourceGroupTemplate(rgt);
         if (rgtm != null) {
            return getAppsAndLibs(rgtm, appOnly);
         }
      }

      return null;
   }

   public static AppDeploymentMBean[] getAppsAndLibs(ResourceGroupTemplateMBean rgtm, boolean appOnly) {
      ArrayList appsAndLibs = new ArrayList();
      if (rgtm == null) {
         return new AppDeploymentMBean[0];
      } else {
         AppDeploymentMBean[] apps = rgtm.getAppDeployments();
         if (apps != null) {
            appsAndLibs.addAll(Arrays.asList(apps));
         }

         if (!appOnly) {
            AppDeploymentMBean[] apps = rgtm.getLibraries();
            if (apps != null) {
               appsAndLibs.addAll(Arrays.asList(apps));
            }
         }

         return (AppDeploymentMBean[])((AppDeploymentMBean[])appsAndLibs.toArray(new AppDeploymentMBean[appsAndLibs.size()]));
      }
   }

   public static AppDeploymentMBean[] getAppsAndLibsForResourceGroup(DomainMBean domain, String rg, String partition, boolean appOnly) {
      if (rg != null) {
         ResourceGroupMBean rgm = null;
         if (partition != null) {
            PartitionMBean pm = domain.lookupPartition(partition);
            if (pm != null) {
               rgm = pm.lookupResourceGroup(rg);
            }
         } else {
            rgm = domain.lookupResourceGroup(rg);
         }

         if (rgm != null) {
            return getAppsAndLibs(rgm, appOnly);
         }
      }

      return null;
   }

   public static AppDeploymentMBean[] getAppsAndLibs(ResourceGroupMBean rgm, boolean appOnly) {
      ArrayList appsAndLibs = new ArrayList();
      if (rgm == null) {
         return new AppDeploymentMBean[0];
      } else {
         AppDeploymentMBean[] apps = rgm.getAppDeployments();
         if (apps != null) {
            appsAndLibs.addAll(Arrays.asList(apps));
         }

         if (!appOnly) {
            AppDeploymentMBean[] apps = rgm.getLibraries();
            if (apps != null) {
               appsAndLibs.addAll(Arrays.asList(apps));
            }
         }

         return (AppDeploymentMBean[])((AppDeploymentMBean[])appsAndLibs.toArray(new AppDeploymentMBean[appsAndLibs.size()]));
      }
   }

   public static AppDeploymentMBean lookupAppOrLibInResourceGroupTemplate(DomainMBean domain, String rgt, String name, boolean appOnly) {
      if (rgt != null) {
         ResourceGroupTemplateMBean rgtm = domain.lookupResourceGroupTemplate(rgt);
         if (rgtm != null) {
            AppDeploymentMBean app = rgtm.lookupAppDeployment(name);
            if (app == null && !appOnly) {
               app = rgtm.lookupLibrary(name);
            }

            return (AppDeploymentMBean)app;
         }
      }

      return null;
   }

   public static AppDeploymentMBean lookupAppOrLibInReferencedResourceGroupTemplate(DomainMBean domain, AppDeploymentMBean deployment, String name, boolean appOnly) {
      if (deployment != null) {
         WebLogicMBean parent = deployment.getParent();
         if (parent instanceof ResourceGroupMBean) {
            ResourceGroupTemplateMBean rgtm = ((ResourceGroupMBean)parent).getResourceGroupTemplate();
            if (rgtm != null) {
               AppDeploymentMBean app = rgtm.lookupAppDeployment(name);
               if (app == null && !appOnly) {
                  app = rgtm.lookupLibrary(name);
               }

               if (app != null) {
                  return (AppDeploymentMBean)app;
               }
            }
         }
      }

      return deployment;
   }

   public static AppDeploymentMBean lookupAppOrLibInResourceGroup(DomainMBean domain, String rg, String partition, String name, boolean appOnly) {
      ResourceGroupMBean rgm = null;
      if (rg != null) {
         if (partition != null) {
            PartitionMBean pm = domain.lookupPartition(partition);
            if (pm != null) {
               rgm = pm.lookupResourceGroup(rg);
            }
         } else {
            rgm = domain.lookupResourceGroup(rg);
         }

         if (rgm != null) {
            AppDeploymentMBean app = rgm.lookupAppDeployment(name);
            if (app == null && !appOnly) {
               app = rgm.lookupLibrary(name);
            }

            return (AppDeploymentMBean)app;
         }
      }

      return null;
   }

   public static AppDeploymentMBean[] getAppsAndLibsForGivenScope(DomainMBean domain, String rgt, String rg, String partition, boolean appOnly) {
      if (partition != null) {
         if (rg == null) {
            PartitionMBean pm = domain.lookupPartition(partition);
            return getAppsAndLibs(pm, appOnly);
         } else {
            return getAppsAndLibsForResourceGroup(domain, rg, partition, appOnly);
         }
      } else if (rgt != null) {
         return getAppsAndLibsForResourceGroupTemplate(domain, rgt, appOnly);
      } else {
         return rg != null ? getAppsAndLibsForResourceGroup(domain, rg, (String)null, appOnly) : getAppsAndLibs(domain, appOnly);
      }
   }

   public static AppDeploymentMBean lookupAppOrLibInGivenScope(String name, String rgt, String rg, String partition, DomainMBean domain) {
      AppDeploymentMBean appDeployment = null;
      if (partition != null) {
         if (rg == null) {
            PartitionMBean pm = domain.lookupPartition(partition);
            return lookupAppOrLib(name, pm, false);
         } else {
            return lookupAppOrLibInResourceGroup(domain, rg, partition, name, false);
         }
      } else if (rgt != null) {
         return lookupAppOrLibInResourceGroupTemplate(domain, rgt, name, false);
      } else {
         return rg != null ? lookupAppOrLibInResourceGroup(domain, rg, (String)null, name, false) : lookupAppOrLib(name, domain);
      }
   }

   public static AppDeploymentMBean lookupAppOrLib(String name, DomainMBean domain) {
      AppDeploymentMBean app = domain.lookupAppDeployment(name);
      if (app != null) {
         return app;
      } else {
         AppDeploymentMBean app = domain.lookupLibrary(name);
         if (app != null) {
            return app;
         } else {
            app = domain.lookupInternalAppDeployment(name);
            if (app != null) {
               return app;
            } else {
               app = domain.lookupInternalLibrary(name);
               return app != null ? app : app;
            }
         }
      }
   }

   public static AppDeploymentMBean lookupAppOrLib(String name, PartitionMBean pBean) {
      return lookupAppOrLib(name, pBean, false);
   }

   public static AppDeploymentMBean lookupAppOrLib(String name, PartitionMBean pBean, boolean appOnly) {
      return null;
   }

   public static void destroyAppOrLib(AppDeploymentMBean mbean, DomainMBean domain) {
      if (!mbean.isInternalApp()) {
         String appName = mbean.getName();
         ApplicationMBean application = domain.lookupApplication(appName);
         if (application != null) {
            domain.destroyApplication(application);
         }

         Object parent = mbean.getParent();
         if (parent instanceof DomainMBean) {
            if (mbean instanceof LibraryMBean) {
               domain.destroyLibrary((LibraryMBean)mbean);
            } else {
               domain.destroyAppDeployment(mbean);
            }
         }

         if (!isRuntimeTree(domain)) {
            if (parent instanceof ResourceGroupMBean) {
               if (mbean instanceof LibraryMBean) {
                  ((ResourceGroupMBean)parent).destroyLibrary((LibraryMBean)mbean);
               } else {
                  ((ResourceGroupMBean)parent).destroyAppDeployment(mbean);
               }
            } else if (parent instanceof ResourceGroupTemplateMBean) {
               if (mbean instanceof LibraryMBean) {
                  ((ResourceGroupTemplateMBean)parent).destroyLibrary((LibraryMBean)mbean);
               } else {
                  ((ResourceGroupTemplateMBean)parent).destroyAppDeployment(mbean);
               }

               String rgt = ((ResourceGroupTemplateMBean)parent).getName();
               AppDeploymentMBean deployment = lookupAppOrLibInResourceGroupOrTemplate(appName, domain);
               destroyAppOrLibRGTconfigOverride(deployment, rgt);
            }
         }

      }
   }

   private static void destroyAppOrLibRGTconfigOverride(AppDeploymentMBean deployment, String rgt) {
      if (deployment != null && isDeploymentRGTConfigOverride(deployment, rgt)) {
         Object rgMBean = deployment.getParent();
         if (rgMBean instanceof ResourceGroupMBean) {
            if (deployment instanceof LibraryMBean) {
               ((ResourceGroupMBean)rgMBean).destroyLibrary((LibraryMBean)deployment);
            } else {
               ((ResourceGroupMBean)rgMBean).destroyAppDeployment(deployment);
            }
         }
      }

   }

   public static BasicDeploymentMBean lookupBasicDeployment(String name, DomainMBean domain) {
      if (name == null) {
         return null;
      } else if (domain == null) {
         return null;
      } else {
         BasicDeploymentMBean[] deps = domain.getBasicDeployments();

         for(int i = 0; i < deps.length; ++i) {
            if (name.equals(deps[i].getName())) {
               return deps[i];
            }
         }

         return null;
      }
   }

   private static BasicDeploymentMBean lookupBasicDeployment(String name, ResourceGroupTemplateMBean rgt) {
      if (deploymentLogger.isDebugEnabled()) {
         deploymentLogger.debug("lookupBasicDeployment in resource group or template: " + rgt.getName() + " for basic deployment:" + name);
      }

      if (name == null) {
         return null;
      } else if (rgt == null) {
         return null;
      } else {
         ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
         BasicDeploymentMBean[] deps = rgt.getBasicDeployments();

         for(int i = 0; i < deps.length; ++i) {
            if (deploymentLogger.isDebugEnabled()) {
               deploymentLogger.debug("Check against basic deployment: " + deps[i].getName());
            }

            if (name.equals(avus.getApplicationName(deps[i].getName()))) {
               return deps[i];
            }
         }

         return null;
      }
   }

   private static DeploymentMBean lookupDeployment(String name, ResourceGroupMBean rg) {
      if (deploymentLogger.isDebugEnabled()) {
         deploymentLogger.debug("lookupDeployment in resource group: " + rg.getName() + " for deployment:" + name);
      }

      if (name == null) {
         return null;
      } else if (rg == null) {
         return null;
      } else {
         DeploymentMBean[] deps = rg.getDeployments();

         for(int i = 0; i < deps.length; ++i) {
            if (name.equals(deps[i].getName())) {
               return deps[i];
            }
         }

         return null;
      }
   }

   public static BasicDeploymentMBean lookupBasicDeployment(String name, long requestId) {
      BasicDeploymentMBean mbean = null;
      AdminServerDeploymentManagerServiceGenerator generator = (AdminServerDeploymentManagerServiceGenerator)LocatorUtilities.getService(AdminServerDeploymentManagerServiceGenerator.class);
      AdminServerDeploymentManagerService deploymentManager = generator.createAdminServerDeploymentManager(kernelId);
      DomainMBean editable = deploymentManager.getEditableDomainMBean(requestId);
      if (editable != null) {
         mbean = lookupBasicDeployment(name, editable);
      }

      if (mbean == null) {
         mbean = lookupBasicDeployment(name, ManagementService.getRuntimeAccess(kernelId).getDomain());
      }

      return mbean;
   }

   public static AppDeploymentMBean lookupAppDeployment(String name, long requestId) {
      AppDeploymentMBean mbean = null;
      AdminServerDeploymentManagerServiceGenerator generator = (AdminServerDeploymentManagerServiceGenerator)LocatorUtilities.getService(AdminServerDeploymentManagerServiceGenerator.class);
      AdminServerDeploymentManagerService deploymentManager = generator.createAdminServerDeploymentManager(kernelId);
      DomainMBean editable = deploymentManager.getEditableDomainMBean(requestId);
      if (editable != null) {
         mbean = lookupAppOrLib(name, editable);
         if (mbean == null) {
            mbean = lookupAppOrLibInResourceGroupOrTemplate(name, editable);
         }
      }

      if (mbean == null) {
         mbean = lookupAppOrLib(name, ManagementService.getRuntimeAccess(kernelId).getDomain());
      }

      return mbean;
   }

   private static boolean isRuntimeTree(DomainMBean domain) {
      if (!ManagementService.isRuntimeAccessInitialized()) {
         return false;
      } else {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         if (runtimeAccess == null) {
            return false;
         } else {
            DomainMBean runtimeDomain = runtimeAccess.getDomain();
            return runtimeDomain == domain;
         }
      }
   }

   public static AppDeploymentMBean lookupAppOrLibInResourceGroupOrTemplate(String name, DomainMBean domain) {
      return null;
   }

   public static boolean isDeployedThroughRGT(BasicDeploymentMBean basicDepMBean) {
      ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
      ResourceGroupMBean rgm = getResourceGroupMBean(basicDepMBean);
      if (rgm == null) {
         return false;
      } else {
         ResourceGroupTemplateMBean rgtm = rgm.getResourceGroupTemplate();
         if (rgtm == null) {
            return false;
         } else {
            String depName = basicDepMBean.getName();
            String appName = avus.getApplicationName(depName);
            String versionId = avus.getVersionId(depName);
            String nonPartitionName = avus.getApplicationId(appName, versionId);
            if (deploymentLogger.isDebugEnabled()) {
               deploymentLogger.debug("dep name: " + depName + " appName: " + appName + " versionId: " + versionId + " nonPartitionName: " + nonPartitionName);
            }

            BasicDeploymentMBean bdm = lookupBasicDeployment(appName, rgtm);
            return bdm != null;
         }
      }
   }

   public static ResourceGroupMBean getResourceGroupMBean(BasicDeploymentMBean basicDepMBean) {
      if (deploymentLogger.isDebugEnabled() && basicDepMBean != null) {
         deploymentLogger.debug("getResourceGroupMBean for :" + basicDepMBean.getName());
      }

      BasicDeploymentMBean bdm = getOriginalMBean(basicDepMBean);
      if (deploymentLogger.isDebugEnabled()) {
         deploymentLogger.debug("original mbean :" + bdm);
      }

      if (bdm != null && bdm.getParent() instanceof ResourceGroupMBean) {
         if (deploymentLogger.isDebugEnabled()) {
            deploymentLogger.debug("parent resource group mbean :" + bdm.getParent());
         }

         return (ResourceGroupMBean)bdm.getParent();
      } else {
         return null;
      }
   }

   public static BasicDeploymentMBean getOriginalMBean(BasicDeploymentMBean basicDepMBean) {
      return null;
   }

   public static BasicDeploymentMBean lookupBasicDeploymentInResourceGroups(ResourceGroupMBean[] resourceGroups, String appName) {
      return null;
   }

   public static ResourceGroupMBean getResourceGroupMBean(DeploymentMBean depMBean) {
      return null;
   }

   public static DeploymentMBean getOriginalMBean(DeploymentMBean depMBean) {
      return null;
   }

   public static DeploymentMBean lookupDeploymentInResourceGroups(ResourceGroupMBean[] resourceGroups, String appName) {
      return null;
   }

   public static String deriveResourceGroupNameForDeployedApp(String appName, String partition, DomainMBean domain) {
      return null;
   }

   public static boolean isDeploymentRGTConfigOverride(AppDeploymentMBean deployment, String rgt) {
      return false;
   }
}
