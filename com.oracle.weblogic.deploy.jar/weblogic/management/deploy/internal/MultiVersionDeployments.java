package weblogic.management.deploy.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.internal.DeploymentOrder;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.deploy.internal.targetserver.OrderedDeployments;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.j2ee.descriptor.wl.AppDeploymentBean;
import weblogic.j2ee.descriptor.wl.DeploymentConfigOverridesBean;
import weblogic.j2ee.descriptor.wl.LibraryBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DeploymentConfigOverridesMBean;
import weblogic.management.configuration.DeploymentConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class MultiVersionDeployments implements MultiVersionConfiguration.DeploymentConfigOverridesBeanListener, PropertyChangeListener {
   private Map map = new HashMap();
   private final DomainMBean domain;
   private final MultiVersionConfiguration config;
   private int maxOldAppVersions;
   private static ContextualTrace t = ContextualTrace.get("MSID");
   private final TimerManager tm;
   private boolean cancelInflightWork = false;
   private int currentOldAppVersionCount = 0;
   private Object lock = new Object();
   private boolean blocked = false;

   MultiVersionDeployments(DomainMBean domain) {
      this.domain = domain;
      DeploymentConfigurationMBean dcm = domain.getDeploymentConfiguration();
      DeploymentConfigOverridesMBean mbean = dcm.getDeploymentConfigOverrides();
      this.maxOldAppVersions = mbean.getMaxOldAppVersions();
      this.config = new MultiVersionConfiguration(mbean.getDir(), mbean.getPollInterval(), this);
      this.tm = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.deploy.MSIDPoller", WorkManagerFactory.getInstance().getSystem());
      t.record("MVD", "Ctr", "MVC-Enabled?", this.config.isEnabled());
   }

   BasicDeploymentMBean processStaticDeployment(BasicDeploymentMBean mbean) {
      AppDeploymentBean appDeploymentBean = this.getMultiVersionConfig(mbean);
      if (appDeploymentBean != null) {
         t.record("MVD", "processStaticDeployment", "Being-cloned", mbean.getName());
         Data data = this.create((AppDeploymentMBean)mbean);
         mbean = this.init(data, appDeploymentBean);
         t.record("MVD", "processStaticDeployment", "Cloned", mbean.getName());
      }

      return mbean;
   }

   void processDynamicDeployment(DomainMBean proposedDomain) {
      DeploymentConfigOverridesBean bean = this.config.getRootBean();
      if (bean != null) {
         LibraryBean[] libraries = bean.getLibraries();
         int var6;
         if (libraries != null) {
            LibraryBean[] var4 = libraries;
            int var5 = libraries.length;

            for(var6 = 0; var6 < var5; ++var6) {
               LibraryBean lib = var4[var6];
               if (this.map.containsKey(lib.getName())) {
                  Data data = (Data)this.map.get(lib.getName());
                  data.setConfiguredAppDeploymentMBean(proposedDomain.lookupLibrary(lib.getName()));
                  AppDeploymentMBean newClone = data.createAppDeploymentMBean(proposedDomain);
                  data.setCurrentApp(newClone, data.getCurrentGeneratedVersion());
               }
            }
         }

         AppDeploymentBean[] apps = bean.getAppDeployments();
         if (apps != null) {
            AppDeploymentBean[] var12 = apps;
            var6 = apps.length;

            for(int var13 = 0; var13 < var6; ++var13) {
               AppDeploymentBean app = var12[var13];
               if (this.map.containsKey(app.getName())) {
                  Data data = (Data)this.map.get(app.getName());
                  data.setConfiguredAppDeploymentMBean(proposedDomain.lookupAppDeployment(app.getName()));
                  AppDeploymentMBean newClone = data.createAppDeploymentMBean(proposedDomain);
                  data.setCurrentApp(newClone, data.getCurrentGeneratedVersion());
               }
            }
         }
      }

   }

   public boolean isEnabled() {
      return this.config.isEnabled();
   }

   void startPolling() {
      this.config.startPolling();
      this.registerListener();
   }

   void stopPolling() {
      this.unregisterListener();
      this.config.stopPolling();
   }

   void teardown() {
      this.cancelInflightWork = true;
      this.config.teardown();
      this.map.clear();
   }

   public String getLatestAppId(String configuredAppId) {
      AppDeploymentMBean clonedMBean = this.getLatestAppDeploymentMBean(configuredAppId);
      return clonedMBean != null ? clonedMBean.getApplicationIdentifier() : null;
   }

   public AppDeploymentMBean getLatestAppDeploymentMBean(String configuredAppId) {
      return this.map.containsKey(configuredAppId) ? ((Data)this.map.get(configuredAppId)).getCurrentAppDeploymentMBean() : null;
   }

   public String getConfiguredAppId(String inferredAppId) {
      AppDeploymentMBean mbean = this.domain.lookupAppDeployment(inferredAppId);
      if (mbean == null) {
         mbean = this.domain.lookupLibrary(inferredAppId);
      }

      return mbean != null ? ((AppDeploymentMBean)mbean).getConfiguredApplicationIdentifier() : null;
   }

   private AppDeploymentBean getMultiVersionConfig(BasicDeploymentMBean configuredBean) {
      if (configuredBean instanceof AppDeploymentMBean) {
         AppDeploymentMBean appDepMBean = (AppDeploymentMBean)configuredBean;
         if (this.map.containsKey(appDepMBean.getApplicationIdentifier())) {
            throw new IllegalArgumentException("Configured deployment already exists: " + appDepMBean.getApplicationIdentifier());
         }

         if (!appDepMBean.isInternalApp()) {
            if (appDepMBean instanceof LibraryMBean) {
               return this.config.getMultiVersionLibrary(appDepMBean.getApplicationIdentifier());
            }

            return this.config.getMultiVersionApplication(appDepMBean.getApplicationIdentifier());
         }
      }

      return null;
   }

   private Data create(AppDeploymentMBean configuredBean) {
      Data data = new DataImpl(configuredBean);
      this.map.put(configuredBean.getApplicationIdentifier(), data);
      return data;
   }

   private BasicDeploymentMBean init(Data data, AppDeploymentBean appDeploymentBean) {
      AppDeploymentMBean mbean = data.createAppDeploymentMBean(appDeploymentBean);
      data.setCurrentApp(mbean, appDeploymentBean.getGeneratedVersion());
      return mbean;
   }

   public AppDeploymentMBean clone(AppDeploymentMBean bean, String newName, String newSource) {
      AbstractDescriptorBean parent = (AbstractDescriptorBean)bean.getParent();
      return this.clone((DomainMBean)parent, bean, newName, newSource);
   }

   public AppDeploymentMBean clone(DomainMBean domain, AppDeploymentMBean bean, String newName, String newSource) {
      try {
         AppDeploymentMBean clone = domain.createAppDeployment(newName, newSource);
         List excludeProps = new ArrayList(Arrays.asList("Name", "SourcePath"));
         clone = (AppDeploymentMBean)((AbstractDescriptorBean)bean)._copyProperties((AbstractDescriptorBean)clone, excludeProps);
         ((DescriptorImpl)bean.getDescriptor()).resolveReferences();
         return clone;
      } catch (Throwable var7) {
         throw new AssertionError(var7);
      }
   }

   public LibraryMBean clone(LibraryMBean bean, String newName, String newSource) {
      AbstractDescriptorBean parent = (AbstractDescriptorBean)bean.getParent();
      return this.clone((DomainMBean)parent, bean, newName, newSource);
   }

   public LibraryMBean clone(DomainMBean domain, LibraryMBean bean, String newName, String newSource) {
      try {
         LibraryMBean clone = domain.createLibrary(newName, newSource);
         List excludeProps = new ArrayList(Arrays.asList("Name", "SourcePath"));
         clone = (LibraryMBean)((AbstractDescriptorBean)bean)._copyProperties((AbstractDescriptorBean)clone, excludeProps);
         ((DescriptorImpl)bean.getDescriptor()).resolveReferences();
         return clone;
      } catch (Throwable var7) {
         throw new AssertionError(var7);
      }
   }

   public void onValidateRuntime(DeploymentConfigOverridesBean bean) {
      LibraryBean[] libraries = bean.getLibraries();
      if (libraries != null) {
         LibraryBean[] var3 = libraries;
         int var4 = libraries.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            LibraryBean lib = var3[var5];
            if (this.cancelInflightWork) {
               t.record("MVD", "onChange", "in-flight-work-cancelled", lib.getName(), lib.getGeneratedVersion());
               DeployerRuntimeExtendedLogger.cancelMSIDWork(lib.getName(), lib.getGeneratedVersion(), "onValidateRuntime");
               return;
            }

            if (this.map.containsKey(lib.getName())) {
               this.onAppDeployment(lib, false);
            }
         }
      }

      synchronized(this.lock) {
         this.currentOldAppVersionCount = 0;
      }

      AppDeploymentBean[] apps = bean.getAppDeployments();
      if (apps != null) {
         Collection sortedApps = this.sortAppsWithDeploymentOrder(apps);
         Iterator var11 = sortedApps.iterator();

         while(var11.hasNext()) {
            AppDeploymentBean app = (AppDeploymentBean)var11.next();
            if (this.cancelInflightWork) {
               t.record("MVD", "onChange", "in-flight-work-cancelled", app.getName(), app.getGeneratedVersion());
               DeployerRuntimeExtendedLogger.cancelMSIDWork(app.getName(), app.getGeneratedVersion(), "onValidateRuntime");
               return;
            }

            if (this.map.containsKey(app.getName())) {
               this.onAppDeployment(app, true);
            }
         }
      }

   }

   private void blockIfReachThreshold(String appName) {
      synchronized(this.lock) {
         if (this.currentOldAppVersionCount >= this.maxOldAppVersions) {
            this.blocked = true;
         }

         while(this.blocked) {
            try {
               DeployerRuntimeExtendedLogger.blockedMSIDAppProcessing(appName, this.currentOldAppVersionCount, this.maxOldAppVersions);
               this.lock.wait();
            } catch (InterruptedException var5) {
            }
         }

         ++this.currentOldAppVersionCount;
      }
   }

   private void unblockIfBelowThreshold() {
      synchronized(this.lock) {
         --this.currentOldAppVersionCount;
         if (this.blocked && this.currentOldAppVersionCount < this.maxOldAppVersions) {
            this.blocked = false;
            this.lock.notifyAll();
            DeployerRuntimeExtendedLogger.unblockedMSIDAppProcessing(this.currentOldAppVersionCount, this.maxOldAppVersions);
         }

      }
   }

   void changeMaxOldAppVersions(int newMaxOldAppVersions) {
      synchronized(this.lock) {
         if (newMaxOldAppVersions > this.maxOldAppVersions) {
            this.maxOldAppVersions = newMaxOldAppVersions;
            if (this.blocked && this.currentOldAppVersionCount < this.maxOldAppVersions) {
               this.blocked = false;
               this.lock.notifyAll();
               DeployerRuntimeExtendedLogger.unblockedMSIDAppProcessing(this.currentOldAppVersionCount, this.maxOldAppVersions);
            }
         } else {
            this.maxOldAppVersions = newMaxOldAppVersions;
         }

      }
   }

   private Collection sortAppsWithDeploymentOrder(AppDeploymentBean[] apps) {
      TreeMap sortedAppMap = new TreeMap(DeploymentOrder.COMPARATOR);
      AppDeploymentBean[] var3 = apps;
      int var4 = apps.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         AppDeploymentBean adb = var3[var5];
         Data data = (Data)this.map.get(adb.getName());
         if (data != null) {
            AppDeploymentMBean adm = data.getConfiguredAppDeploymentMBean();
            sortedAppMap.put(adm, adb);
         }
      }

      return sortedAppMap.values();
   }

   private void onAppDeployment(AppDeploymentBean appDeploymentBean, boolean isApplication) {
      Data data = (Data)this.map.get(appDeploymentBean.getName());
      if (TargetHelper.isTargetedLocaly(data.getConfiguredAppDeploymentMBean())) {
         this.onLocallyTargetedAppDeploymentChange(data, appDeploymentBean, isApplication);
      } else {
         this.onRemotelyTargetedAppDeploymentChange(data, appDeploymentBean);
      }

   }

   private void onLocallyTargetedAppDeploymentChange(Data data, AppDeploymentBean appDeploymentBean, boolean isApplication) {
      t.record("MVD", "onLocallyTargetedAppDeploymentChange", appDeploymentBean.getName(), appDeploymentBean.getGeneratedVersion(), isApplication);
      AppDeploymentMBean currentMBean = data.getCurrentAppDeploymentMBean();
      String currentGeneratedVersion = data.getCurrentGeneratedVersion();
      BasicDeployment currentDeployment = OrderedDeployments.getOrCreateBasicDeployment(currentMBean);
      if (currentGeneratedVersion.equals(appDeploymentBean.getGeneratedVersion())) {
         t.record("MVD", "onLocallyTargetedAppDeploymentChange", "Ignoring-Generated-Version-Unchanged", currentGeneratedVersion);
      } else {
         if (isApplication) {
            this.blockIfReachThreshold(appDeploymentBean.getName());
         }

         AppDeploymentMBean newClone = data.createAppDeploymentMBean(appDeploymentBean);
         BasicDeployment newDeployment = OrderedDeployments.getOrCreateBasicDeployment(newClone);
         if (this.deploy(data.getConfiguredAppDeploymentMBean().getApplicationIdentifier(), appDeploymentBean.getGeneratedVersion(), newClone.getSourcePath(), newDeployment)) {
            t.record("MVD", "onLocallyTargetedAppDeploymentChange", "Deployment-Success", newClone.getApplicationIdentifier(), newClone.getSourcePath());
            data.setCurrentApp(newClone, appDeploymentBean.getGeneratedVersion());
            if (isApplication) {
               this.scheduleRetirement(data.getConfiguredAppDeploymentMBean().getApplicationIdentifier(), currentGeneratedVersion, currentDeployment, currentMBean, this.computeRetireTimeout(appDeploymentBean, this.domain.getDeploymentConfiguration()));
            }
         } else {
            t.record("MVD", "onLocallyTargetedAppDeploymentChange", "Deployment-Failure", newClone.getApplicationIdentifier());
            if (newClone instanceof LibraryMBean) {
               ((DomainMBean)newClone.getParent()).destroyLibrary((LibraryMBean)newClone);
            } else {
               ((DomainMBean)newClone.getParent()).destroyAppDeployment(newClone);
               this.unblockIfBelowThreshold();
            }
         }

      }
   }

   private void onRemotelyTargetedAppDeploymentChange(Data data, AppDeploymentBean appDeploymentBean) {
      AppDeploymentMBean newClone = data.createAppDeploymentMBean(appDeploymentBean);
      data.setCurrentApp(newClone, appDeploymentBean.getGeneratedVersion());
   }

   private int computeRetireTimeout(AppDeploymentBean appDeploymentBean, DeploymentConfigurationMBean deploymentConfigurationMBean) {
      if (appDeploymentBean.isRetireTimeoutSet()) {
         try {
            int retireTimeout = Integer.parseInt(appDeploymentBean.getRetireTimeout());
            if (retireTimeout >= 0) {
               return retireTimeout;
            }
         } catch (NumberFormatException var4) {
         }

         DeployerRuntimeExtendedLogger.invalidRetireTimeout(appDeploymentBean.getName(), appDeploymentBean.getGeneratedVersion(), appDeploymentBean.getRetireTimeout(), deploymentConfigurationMBean.getDefaultMultiVersionAppRetireTimeout());
      }

      return deploymentConfigurationMBean.getDefaultMultiVersionAppRetireTimeout();
   }

   private boolean deploy(String configuredAppId, String newGeneratedVersion, String newVersionPath, BasicDeployment deployment) {
      t.record("MVD", "deploy", configuredAppId, newGeneratedVersion, newVersionPath);

      try {
         DeployerRuntimeExtendedLogger.initiatingDeployOfNewVersion(configuredAppId, newGeneratedVersion, newVersionPath);
         deployment.prepare();
         deployment.activateFromServerLifecycle();
         deployment.adminToProductionFromServerLifecycle();
         DeployerRuntimeExtendedLogger.completedDeployOfNewVersion(configuredAppId, newGeneratedVersion);
         return true;
      } catch (Exception var6) {
         DeployerRuntimeExtendedLogger.logFailedToDeployNewVersion(configuredAppId, newGeneratedVersion, newVersionPath, t.flush(), var6);
         return false;
      }
   }

   private void scheduleRetirement(String configuredAppId, String retiringGeneratedVersion, BasicDeployment deployment, AppDeploymentMBean mbean, int retireTimeout) {
      t.record("MVD", "scheduleRetirement", configuredAppId, retiringGeneratedVersion, mbean.getApplicationIdentifier());
      if (retireTimeout == 0) {
         (new RetireDeployment(configuredAppId, retiringGeneratedVersion, deployment, mbean)).timerExpired((Timer)null);
      } else {
         if (retireTimeout <= 0) {
            this.unblockIfBelowThreshold();
            throw new UnsupportedOperationException("Graceful retirement not supported");
         }

         DeployerRuntimeExtendedLogger.schedulingRetirement(configuredAppId, retiringGeneratedVersion, retireTimeout);
         this.tm.schedule(new RetireDeployment(configuredAppId, retiringGeneratedVersion, deployment, mbean), (long)retireTimeout * 1000L);
      }

   }

   private void registerListener() {
      this.domain.getDeploymentConfiguration().addPropertyChangeListener(this);
      this.domain.getDeploymentConfiguration().getDeploymentConfigOverrides().addPropertyChangeListener(this);
   }

   private void unregisterListener() {
      this.domain.getDeploymentConfiguration().getDeploymentConfigOverrides().removePropertyChangeListener(this);
      this.domain.getDeploymentConfiguration().removePropertyChangeListener(this);
   }

   public void propertyChange(PropertyChangeEvent evt) {
      if (evt.getPropertyName().equals("PollInterval")) {
         if (this.config.isEnabled()) {
            this.config.changePollInterval((Integer)evt.getNewValue());
         }
      } else if (evt.getPropertyName().equals("Dir")) {
         this.config.changeDir((String)evt.getNewValue());
      } else if (evt.getPropertyName().equals("MaxOldAppVersions") && this.config.isEnabled()) {
         this.changeMaxOldAppVersions((Integer)evt.getNewValue());
      }

   }

   private class DataImpl implements Data {
      private AppDeploymentMBean configuredAppDeploymentMBean;
      private AppDeploymentMBean currentAppDeploymentMBean;
      private String currentGeneratedVersion;

      private DataImpl(AppDeploymentMBean configuredAppDeploymentMBean) {
         this.configuredAppDeploymentMBean = configuredAppDeploymentMBean;
      }

      public AppDeploymentMBean getConfiguredAppDeploymentMBean() {
         return this.configuredAppDeploymentMBean;
      }

      public void setConfiguredAppDeploymentMBean(AppDeploymentMBean appDeploymentMBean) {
         this.configuredAppDeploymentMBean = appDeploymentMBean;
      }

      public AppDeploymentMBean createAppDeploymentMBean(DomainMBean domainMBean) {
         String inferredApplicationId = this.currentAppDeploymentMBean.getApplicationIdentifier();
         Object clonedMBean;
         if (this.currentAppDeploymentMBean instanceof LibraryMBean) {
            clonedMBean = domainMBean.lookupLibrary(inferredApplicationId);
            if (clonedMBean == null) {
               clonedMBean = MultiVersionDeployments.this.clone(domainMBean, (LibraryMBean)this.configuredAppDeploymentMBean, inferredApplicationId, this.currentAppDeploymentMBean.getSourcePath());
            }
         } else {
            clonedMBean = domainMBean.lookupAppDeployment(inferredApplicationId);
            if (clonedMBean == null) {
               clonedMBean = MultiVersionDeployments.this.clone(domainMBean, this.configuredAppDeploymentMBean, inferredApplicationId, this.currentAppDeploymentMBean.getSourcePath());
            }
         }

         MultiVersionDeployments.t.record("MVD", "createAppDeploymentMBean", this.configuredAppDeploymentMBean.getApplicationIdentifier(), ((AppDeploymentMBean)clonedMBean).getApplicationIdentifier());
         ((AppDeploymentMBean)clonedMBean).setConfiguredApplicationIdentifier(this.configuredAppDeploymentMBean.getApplicationIdentifier());
         ((AppDeploymentMBean)clonedMBean).setMultiVersionApp(true);
         this.configuredAppDeploymentMBean.setMultiVersionApp(true);
         return (AppDeploymentMBean)clonedMBean;
      }

      public AppDeploymentMBean createAppDeploymentMBean(AppDeploymentBean appDeploymentBean) {
         String inferredApplicationId = this.getInferredApplicationId(appDeploymentBean.getGeneratedVersion());
         Object clonedMBean;
         if (appDeploymentBean instanceof LibraryBean) {
            clonedMBean = MultiVersionDeployments.this.domain.lookupLibrary(inferredApplicationId);
            if (clonedMBean == null) {
               clonedMBean = MultiVersionDeployments.this.clone((LibraryMBean)this.configuredAppDeploymentMBean, inferredApplicationId, appDeploymentBean.getSourcePath());
            }
         } else {
            clonedMBean = MultiVersionDeployments.this.domain.lookupAppDeployment(inferredApplicationId);
            if (clonedMBean == null) {
               clonedMBean = MultiVersionDeployments.this.clone(this.configuredAppDeploymentMBean, inferredApplicationId, appDeploymentBean.getSourcePath());
            }
         }

         MultiVersionDeployments.t.record("MVD", "createAppDeploymentMBean", this.configuredAppDeploymentMBean.getApplicationIdentifier(), ((AppDeploymentMBean)clonedMBean).getApplicationIdentifier());
         ((AppDeploymentMBean)clonedMBean).setConfiguredApplicationIdentifier(this.configuredAppDeploymentMBean.getApplicationIdentifier());
         ((AppDeploymentMBean)clonedMBean).setMultiVersionApp(true);
         this.configuredAppDeploymentMBean.setMultiVersionApp(true);
         return (AppDeploymentMBean)clonedMBean;
      }

      public void setCurrentApp(AppDeploymentMBean appDeploymentMBean, String generatedVersion) {
         this.currentAppDeploymentMBean = appDeploymentMBean;
         this.currentGeneratedVersion = generatedVersion;
      }

      public AppDeploymentMBean getCurrentAppDeploymentMBean() {
         return this.currentAppDeploymentMBean;
      }

      public String getCurrentGeneratedVersion() {
         return this.currentGeneratedVersion;
      }

      private String getInferredApplicationId(String generatedVersion) {
         String configuredAppId = this.configuredAppDeploymentMBean.getApplicationIdentifier();
         String appName = ApplicationVersionUtils.getApplicationName(configuredAppId);
         String configuredVersion = ApplicationVersionUtils.getVersionId(configuredAppId);
         String inferredVersion;
         if (configuredVersion != null && configuredVersion.length() != 0) {
            inferredVersion = configuredVersion + "." + generatedVersion;
         } else {
            inferredVersion = generatedVersion;
         }

         return ApplicationVersionUtils.getApplicationId(appName, inferredVersion);
      }

      // $FF: synthetic method
      DataImpl(AppDeploymentMBean x1, Object x2) {
         this(x1);
      }
   }

   private interface Data {
      AppDeploymentMBean getConfiguredAppDeploymentMBean();

      void setConfiguredAppDeploymentMBean(AppDeploymentMBean var1);

      AppDeploymentMBean createAppDeploymentMBean(AppDeploymentBean var1);

      AppDeploymentMBean createAppDeploymentMBean(DomainMBean var1);

      void setCurrentApp(AppDeploymentMBean var1, String var2);

      AppDeploymentMBean getCurrentAppDeploymentMBean();

      String getCurrentGeneratedVersion();
   }

   class RetireDeployment implements TimerListener {
      private final String configuredAppId;
      private final String retiringGeneratedVersion;
      private final BasicDeployment deployment;
      private final AppDeploymentMBean retiringMBean;

      RetireDeployment(String configuredAppId, String retiringGeneratedVersion, BasicDeployment deployment, AppDeploymentMBean retiringMBean) {
         this.configuredAppId = configuredAppId;
         this.retiringGeneratedVersion = retiringGeneratedVersion;
         this.deployment = deployment;
         this.retiringMBean = retiringMBean;
      }

      public void timerExpired(Timer timer) {
         if (MultiVersionDeployments.this.cancelInflightWork) {
            MultiVersionDeployments.t.record("MVD", "timerExpired", "in-flight-retirement-cancelled", this.configuredAppId, this.retiringGeneratedVersion);
            DeployerRuntimeExtendedLogger.cancelMSIDWork(this.configuredAppId, this.retiringGeneratedVersion, "retirement");
         } else {
            try {
               DeployerRuntimeExtendedLogger.initiatingRetirementOfOldVersion(this.configuredAppId, this.retiringGeneratedVersion);
               this.deployment.productionToAdminFromServerLifecycle(false);
               this.deployment.deactivateFromServerLifecycle();
               this.deployment.unprepareFromServerLifecycle();
               this.deployment.retireFromServerLifecycle();
               this.deployment.remove();
               DeployerRuntimeExtendedLogger.completedRetirementOfOldVersion(this.configuredAppId, this.retiringGeneratedVersion);
            } catch (Exception var6) {
               DeployerRuntimeExtendedLogger.logExceptionInRetirementOfOldVersion(this.configuredAppId, this.retiringGeneratedVersion, MultiVersionDeployments.t.flush(), var6);
            } finally {
               ((DomainMBean)this.retiringMBean.getParent()).destroyAppDeployment(this.retiringMBean);
               MultiVersionDeployments.this.unblockIfBelowThreshold();
            }

         }
      }
   }
}
