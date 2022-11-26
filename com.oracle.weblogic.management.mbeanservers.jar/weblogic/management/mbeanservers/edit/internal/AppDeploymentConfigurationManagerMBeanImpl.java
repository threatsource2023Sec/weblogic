package weblogic.management.mbeanservers.edit.internal;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.Kernel;
import weblogic.management.DomainDir;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.jmx.modelmbean.WLSModelMBeanFactory;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.AppDeploymentConfigurationMBean;
import weblogic.management.mbeanservers.edit.AppDeploymentConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditAccessCallbackHandler;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.SessionHelperManagerRuntimeMBean;
import weblogic.management.runtime.SessionHelperRuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class AppDeploymentConfigurationManagerMBeanImpl extends ServiceImpl implements AppDeploymentConfigurationManagerMBean, EditAccessCallbackHandler {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXEdit");
   private final WLSModelMBeanContext context;
   private final ConfigurationManagerMBean configurationManager;
   private final Map appDeploymentConfigurations = new HashMap();
   private final DomainAccess domainAccess;
   private final SessionHelperManagerRuntimeMBean sessionHelperManager;
   private final String pName;
   private final boolean globalDomain;
   AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   AppDeploymentConfigurationManagerMBeanImpl(EditAccess editAccess, ConfigurationManagerMBean configurationManager, WLSModelMBeanContext context) {
      super("AppDeploymentConfigurationManager", AppDeploymentConfigurationManagerMBean.class.getName(), (Service)null);
      this.pName = editAccess.getPartitionName();
      this.globalDomain = "DOMAIN".equals(this.pName);
      this.configurationManager = configurationManager;
      this.context = context;
      editAccess.registerCallbackHandler(this);
      if (!Kernel.isServer()) {
         this.domainAccess = null;
         this.sessionHelperManager = null;
      } else {
         this.domainAccess = ManagementService.getDomainAccess(this.kernelId);
         if (this.domainAccess == null) {
            this.sessionHelperManager = null;
         } else {
            this.sessionHelperManager = this.domainAccess.getSessionHelperManager();
         }
      }
   }

   public AppDeploymentConfigurationMBean[] getDeploymentConfigurations() {
      try {
         DomainMBean domain = ((ConfigurationManagerMBeanImpl)this.configurationManager).getEditAccess().getDomainBeanWithoutLock();
         return (AppDeploymentConfigurationMBean[])this.appDeploymentConfigurations.values().toArray(new AppDeploymentConfigurationMBean[0]);
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public void releaseDeploymentConfiguration(String appName) {
      AppDeploymentConfigurationMBeanImpl mbean = (AppDeploymentConfigurationMBeanImpl)this.appDeploymentConfigurations.get(appName);
      if (mbean != null) {
         mbean.unregisterBeans();
         this.appDeploymentConfigurations.remove(appName);
         WLSModelMBeanFactory.unregisterWLSModelMBean(mbean, this.context);

         try {
            this.sessionHelperManager.releaseSessionHelper(appName);
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }
      }

   }

   public AppDeploymentConfigurationMBean loadDeploymentConfiguration(String appName) {
      return this.loadDeploymentConfiguration(appName, (String)null, false);
   }

   public AppDeploymentConfigurationMBean loadDeploymentConfiguration(String appName, boolean enableScaffolding) {
      return this.loadDeploymentConfiguration(appName, (String)null, enableScaffolding);
   }

   public AppDeploymentConfigurationMBean loadDeploymentConfiguration(String appName, String plan) {
      return this.loadDeploymentConfiguration(appName, plan, false);
   }

   public AppDeploymentConfigurationMBean loadDeploymentConfiguration(String appName, String plan, boolean enableScaffolding) {
      return this.internalLoadDeploymentConfiguration(appName, plan, enableScaffolding, this.globalDomain ? null : this.pName, (String)null);
   }

   public AppDeploymentConfigurationMBean loadDeploymentConfigurationForPartition(String appName, String plan, boolean enableScaffolding, String partitionName) {
      return this.internalLoadDeploymentConfiguration(appName, plan, enableScaffolding, partitionName, (String)null);
   }

   public AppDeploymentConfigurationMBean loadDeploymentConfiguration(String appName, String plan, boolean enableScaffolding, String partitionName, String resourceGroupName) {
      return this.loadDeploymentConfigurationForPartition(appName, plan, enableScaffolding, partitionName);
   }

   public AppDeploymentConfigurationMBean loadDeploymentConfigurationForResourceGroupTemplate(String appName, String plan, boolean enableScaffolding, String resourceGroupTemplateName) {
      return this.internalLoadDeploymentConfiguration(appName, plan, enableScaffolding, (String)null, resourceGroupTemplateName);
   }

   public AppDeploymentConfigurationMBean loadDeploymentConfiguration(String appName, String plan, boolean enableScaffolding, String resourceGroupTemplateName) {
      return this.loadDeploymentConfigurationForResourceGroupTemplate(appName, plan, enableScaffolding, resourceGroupTemplateName);
   }

   private AppDeploymentConfigurationMBean internalLoadDeploymentConfiguration(String appName, String plan, boolean enableScaffolding, String partitionName, String resourceGroupTemplateName) {
      if (appName != null && appName.length() != 0) {
         DomainMBean domain = null;
         AppDeploymentMBean appDeployment = null;
         AppInfo appInfo = null;

         try {
            domain = ((ConfigurationManagerMBeanImpl)this.configurationManager).getEditAccess().getDomainBeanWithoutLock();
            String resourceGroupName = null;
            if (resourceGroupTemplateName != null) {
               appDeployment = AppDeploymentHelper.lookupAppOrLibInResourceGroupTemplate(domain, resourceGroupTemplateName, appName, true);
            } else {
               resourceGroupName = AppDeploymentHelper.deriveResourceGroupNameForDeployedApp(appName, partitionName, domain);
               if (resourceGroupName != null) {
                  appDeployment = AppDeploymentHelper.lookupAppOrLibInResourceGroup(domain, resourceGroupName, partitionName, appName, true);
               } else if (appDeployment == null && partitionName == null) {
                  appDeployment = domain.lookupAppDeployment(appName);
               }
            }

            if (appDeployment == null) {
               throw new IllegalArgumentException("Application " + appName + " does not exist.");
            }

            if (plan == null || plan.length() == 0) {
               plan = appDeployment.getAbsolutePlanPath();
            }

            appInfo = new AppInfo(appName, appDeployment.getAbsoluteSourcePath(), plan, appDeployment.getLocalPlanPath(), appDeployment.getModuleType(), enableScaffolding, partitionName, resourceGroupName);
         } catch (Exception var10) {
            throw new RuntimeException(var10);
         }

         return this.getImpl(domain, appInfo);
      } else {
         throw new IllegalArgumentException("appName cannot be null");
      }
   }

   public AppDeploymentConfigurationMBean loadDeploymentConfiguration(ObjectName appObjectName, String plan) {
      String appName = appObjectName.getKeyProperty("Name");
      String appType = appObjectName.getKeyProperty("Type");
      String partitionName = appObjectName.getKeyProperty("Partition");
      String resourceGroupName = appObjectName.getKeyProperty("ResourceGroup");
      if (appType.equals("AppDeployment") && appName != null && partitionName != null && resourceGroupName != null) {
         return this.internalLoadDeploymentConfiguration(appName, plan, false, partitionName, (String)null);
      } else {
         throw new IllegalArgumentException("ObjectName " + appObjectName + " is incorrect.");
      }
   }

   public AppDeploymentConfigurationMBean editDeploymentConfiguration(String appName) {
      return this.editDeploymentConfiguration(appName, (String)null, false);
   }

   public AppDeploymentConfigurationMBean editDeploymentConfiguration(String appName, boolean enableScaffolding) {
      return this.editDeploymentConfiguration(appName, (String)null, enableScaffolding);
   }

   public AppDeploymentConfigurationMBean editDeploymentConfiguration(String appName, String plan) {
      return this.editDeploymentConfiguration(appName, plan, false);
   }

   public AppDeploymentConfigurationMBean editDeploymentConfiguration(String appName, String plan, boolean enableScaffolding) {
      return this.internalEditDeploymentConfiguration(appName, plan, enableScaffolding, this.globalDomain ? null : this.pName, (String)null);
   }

   public AppDeploymentConfigurationMBean editDeploymentConfigurationForPartition(String appName, String plan, boolean enableScaffolding, String partitionName) {
      return this.internalEditDeploymentConfiguration(appName, plan, enableScaffolding, partitionName, (String)null);
   }

   public AppDeploymentConfigurationMBean editDeploymentConfiguration(String appName, String plan, boolean enableScaffolding, String partitionName, String resourceGroupName) {
      return this.editDeploymentConfigurationForPartition(appName, plan, enableScaffolding, partitionName);
   }

   public AppDeploymentConfigurationMBean editDeploymentConfigurationForResourceGroupTemplate(String appName, String plan, boolean enableScaffolding, String resourceGroupTemplateName) {
      return this.internalEditDeploymentConfiguration(appName, plan, enableScaffolding, (String)null, resourceGroupTemplateName);
   }

   public AppDeploymentConfigurationMBean editDeploymentConfiguration(String appName, String plan, boolean enableScaffolding, String resourceGroupTemplateName) {
      return this.editDeploymentConfigurationForResourceGroupTemplate(appName, plan, enableScaffolding, resourceGroupTemplateName);
   }

   private AppDeploymentConfigurationMBean internalEditDeploymentConfiguration(String appName, String plan, boolean enableScaffolding, String partitionName, String resourceGroupTemplateName) {
      if (appName != null && appName.length() != 0) {
         DomainMBean domain = null;
         AppDeploymentMBean appDeployment = null;
         AppInfo appInfo = null;
         String resourceGroupName = null;

         try {
            if (!this.configurationManager.isEditor()) {
               throw new RuntimeException("Not edit lock owner");
            }

            domain = ((ConfigurationManagerMBeanImpl)this.configurationManager).getEditAccess().getDomainBeanWithoutLock();
            if (resourceGroupTemplateName != null) {
               appDeployment = AppDeploymentHelper.lookupAppOrLibInResourceGroupTemplate(domain, resourceGroupTemplateName, appName, true);
            } else {
               resourceGroupName = AppDeploymentHelper.deriveResourceGroupNameForDeployedApp(appName, partitionName, domain);
               if (resourceGroupName != null) {
                  appDeployment = AppDeploymentHelper.lookupAppOrLibInResourceGroup(domain, resourceGroupName, partitionName, appName, true);
               } else if (appDeployment == null && partitionName == null) {
                  appDeployment = domain.lookupAppDeployment(appName);
               }
            }

            if (appDeployment == null) {
               throw new IllegalArgumentException("Application " + appName + " does not exist.");
            }

            if (plan == null || plan.length() == 0) {
               plan = appDeployment.getAbsolutePlanPath();
               if (plan == null || plan.length() == 0) {
                  throw new IllegalArgumentException("plan cannot be null");
               }
            }

            appInfo = new AppInfo(appName, appDeployment.getAbsoluteSourcePath(), plan, appDeployment.getLocalPlanPath(), appDeployment.getModuleType(), enableScaffolding, partitionName, resourceGroupName);
         } catch (Exception var11) {
            throw new RuntimeException(var11);
         }

         return this.getImpl(domain, appInfo);
      } else {
         throw new IllegalArgumentException("appName cannot be null");
      }
   }

   public AppDeploymentConfigurationMBean editDeploymentConfiguration(String appName, String appLocation, String plan) {
      return this.editDeploymentConfiguration(appName, appLocation, plan, false);
   }

   public AppDeploymentConfigurationMBean editDeploymentConfiguration(String appName, String appLocation, String plan, boolean enableScaffolding) {
      if (appName != null && appName.length() != 0) {
         if (appLocation != null && appLocation.length() != 0) {
            if (plan != null && plan.length() != 0) {
               DomainMBean domain = null;
               AppInfo appInfo = null;

               try {
                  if (!this.configurationManager.isEditor()) {
                     throw new RuntimeException("Not edit lock owner");
                  }

                  domain = ((ConfigurationManagerMBeanImpl)this.configurationManager).getEditAccess().getDomainBeanWithoutLock();
                  AppDeploymentMBean appDeployment = domain.lookupAppDeployment(appName);
                  if (appDeployment == null) {
                     appDeployment = AppDeploymentHelper.lookupAppOrLibInResourceGroupOrTemplate(appName, domain);
                  }

                  if (appDeployment != null) {
                     throw new IllegalArgumentException("app is already deployed");
                  }

                  appInfo = new AppInfo(appName, appLocation, plan, (String)null, (String)null, enableScaffolding, (String)null, (String)null);
               } catch (Exception var8) {
                  throw new RuntimeException(var8);
               }

               return this.getImpl(domain, appInfo);
            } else {
               throw new IllegalArgumentException("plan cannot be null");
            }
         } else {
            throw new IllegalArgumentException("appLocation cannot be null");
         }
      } else {
         throw new IllegalArgumentException("appName cannot be null");
      }
   }

   public AppDeploymentConfigurationMBean editDeploymentConfiguration(ObjectName appObjectName, String plan) {
      String appName = appObjectName.getKeyProperty("Name");
      String appType = appObjectName.getKeyProperty("Type");
      String partitionName = appObjectName.getKeyProperty("Partition");
      String resourceGroupName = appObjectName.getKeyProperty("ResourceGroup");
      if (appType == "AppDeployment" && appName != null && partitionName != null && resourceGroupName != null) {
         return this.internalEditDeploymentConfiguration(appName, plan, false, partitionName, (String)null);
      } else {
         throw new IllegalArgumentException("ObjectName " + appObjectName + " is incorrect.");
      }
   }

   private AppDeploymentConfigurationMBean getImpl(DomainMBean domain, AppInfo appInfo) {
      AppDeploymentConfigurationMBeanImpl appDeploymentConfiguration = null;
      appDeploymentConfiguration = (AppDeploymentConfigurationMBeanImpl)this.appDeploymentConfigurations.get(appInfo.getName());
      if (appDeploymentConfiguration != null) {
         this.releaseDeploymentConfiguration(appInfo.getName());
      }

      try {
         SessionHelperRuntimeMBean sessionHelperMBean = this.initializeSessionHelper(appInfo, this.context);
         if (sessionHelperMBean != null) {
            appDeploymentConfiguration = new AppDeploymentConfigurationMBeanImpl(this.domainAccess, appInfo, domain, this.context, sessionHelperMBean);
            this.appDeploymentConfigurations.put(appInfo.getName(), appDeploymentConfiguration);
            WLSModelMBeanFactory.registerWLSModelMBean(appDeploymentConfiguration, new ObjectName(appDeploymentConfiguration.objectName), this.context);
         }
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }

      appDeploymentConfiguration = (AppDeploymentConfigurationMBeanImpl)this.appDeploymentConfigurations.get(appInfo.getName());
      return appDeploymentConfiguration;
   }

   private SessionHelperRuntimeMBean initializeSessionHelper(AppInfo appInfo, WLSModelMBeanContext context) {
      try {
         SessionHelperRuntimeMBean sessionHelperMBean = this.sessionHelperManager.getSessionHelper(appInfo, context);
         return sessionHelperMBean;
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   void addAppDeploymentConfiguration(String appName, AppDeploymentConfigurationMBeanImpl impl) {
      try {
         this.appDeploymentConfigurations.put(appName, impl);
         WLSModelMBeanFactory.registerWLSModelMBean(impl, new ObjectName(impl.objectName), this.context);
      } catch (Exception var4) {
      }

   }

   public void saveChanges() {
      Iterator var1 = this.appDeploymentConfigurations.values().iterator();

      while(var1.hasNext()) {
         AppDeploymentConfigurationMBeanImpl impl = (AppDeploymentConfigurationMBeanImpl)var1.next();
         impl.saveChanges();
      }

   }

   public void undoUnsavedChanges() {
      Iterator var1 = this.appDeploymentConfigurations.values().iterator();

      while(var1.hasNext()) {
         AppDeploymentConfigurationMBeanImpl impl = (AppDeploymentConfigurationMBeanImpl)var1.next();
         impl.undoUnsavedChanges();
      }

   }

   public void undoUnactivatedChanges() {
      Iterator var1 = this.appDeploymentConfigurations.values().iterator();

      while(var1.hasNext()) {
         AppDeploymentConfigurationMBeanImpl impl = (AppDeploymentConfigurationMBeanImpl)var1.next();
         impl.undoUnactivatedChanges();
      }

   }

   public void activateChanges() throws IOException {
      Iterator var1 = this.appDeploymentConfigurations.values().iterator();

      while(var1.hasNext()) {
         AppDeploymentConfigurationMBeanImpl impl = (AppDeploymentConfigurationMBeanImpl)var1.next();
         impl.activateChanges();
      }

   }

   public Iterator getChanges() {
      ArrayList changes = new ArrayList();
      Iterator var2 = this.appDeploymentConfigurations.values().iterator();

      while(true) {
         Iterator chIt;
         do {
            if (!var2.hasNext()) {
               return changes.iterator();
            }

            AppDeploymentConfigurationMBeanImpl impl = (AppDeploymentConfigurationMBeanImpl)var2.next();
            chIt = impl.getChanges();
         } while(chIt == null);

         while(chIt.hasNext()) {
            changes.add(chIt.next());
         }
      }
   }

   public Iterator getUnactivatedChanges() {
      ArrayList changes = new ArrayList();
      Iterator var2 = this.appDeploymentConfigurations.values().iterator();

      while(true) {
         Iterator chIt;
         do {
            if (!var2.hasNext()) {
               return changes.iterator();
            }

            AppDeploymentConfigurationMBeanImpl impl = (AppDeploymentConfigurationMBeanImpl)var2.next();
            chIt = impl.getUnactivatedChanges();
         } while(chIt == null);

         while(chIt.hasNext()) {
            changes.add(chIt.next());
         }
      }
   }

   public boolean isModified() {
      Iterator var1 = this.appDeploymentConfigurations.values().iterator();

      AppDeploymentConfigurationMBeanImpl impl;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         impl = (AppDeploymentConfigurationMBeanImpl)var1.next();
      } while(!impl.isModified());

      return true;
   }

   public void updateApplication() {
      Iterator var1 = this.appDeploymentConfigurations.values().iterator();

      while(var1.hasNext()) {
         AppDeploymentConfigurationMBeanImpl impl = (AppDeploymentConfigurationMBeanImpl)var1.next();
         impl.updateApplication();
      }

   }

   public AppDeploymentMBean[] getUnactivatedDeployments() {
      return this.internalGetUnactivatedDeployments(this.globalDomain ? null : this.pName);
   }

   public AppDeploymentMBean[] getUnactivatedDeployments(String partitionName) {
      return this.internalGetUnactivatedDeployments(partitionName);
   }

   private AppDeploymentMBean[] internalGetUnactivatedDeployments(String partitionName) {
      ArrayList beans = new ArrayList();
      String dir = DomainDir.getPendingDir() + File.separator + "deployments";
      File dirFile = new File(dir);
      if (dirFile.exists()) {
         try {
            DomainMBean domain = ((ConfigurationManagerMBeanImpl)this.configurationManager).getEditAccess().getDomainBeanWithoutLock();
            AppDeploymentMBean[] appDeployments;
            if (partitionName == null) {
               appDeployments = domain.getAppDeployments();
            } else {
               PartitionMBean partition = domain.lookupPartition(partitionName);
               appDeployments = AppDeploymentHelper.getAppsAndLibs(partition, true);
            }

            if (appDeployments != null) {
               for(int n = 0; n < appDeployments.length; ++n) {
                  String pendingDir = DomainDir.getPendingDeploymentsDir(appDeployments[n].getName());
                  File pendingPlanFile = new File(pendingDir + File.separator + "plan.xml");
                  if (pendingPlanFile.exists()) {
                     beans.add(appDeployments[n]);
                  }
               }
            }
         } catch (Exception var10) {
         }
      }

      return (AppDeploymentMBean[])beans.toArray(new AppDeploymentMBean[beans.size()]);
   }
}
