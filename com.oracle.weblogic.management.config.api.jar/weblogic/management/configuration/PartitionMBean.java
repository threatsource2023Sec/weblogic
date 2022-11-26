package weblogic.management.configuration;

import weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBean;
import weblogic.management.security.RealmMBean;

public interface PartitionMBean extends ConfigurationPropertiesMBean {
   String getName();

   ResourceGroupMBean[] getResourceGroups();

   ResourceGroupMBean lookupResourceGroup(String var1);

   ResourceGroupMBean createResourceGroup(String var1);

   void destroyResourceGroup(ResourceGroupMBean var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   JDBCSystemResourceOverrideMBean[] getJDBCSystemResourceOverrides();

   JDBCSystemResourceOverrideMBean lookupJDBCSystemResourceOverride(String var1);

   JDBCSystemResourceOverrideMBean createJDBCSystemResourceOverride(String var1);

   void destroyJDBCSystemResourceOverride(JDBCSystemResourceOverrideMBean var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   MailSessionOverrideMBean[] getMailSessionOverrides();

   MailSessionOverrideMBean lookupMailSessionOverride(String var1);

   MailSessionOverrideMBean createMailSessionOverride(String var1);

   void destroyMailSessionOverride(MailSessionOverrideMBean var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   ForeignJNDIProviderOverrideMBean[] getForeignJNDIProviderOverrides();

   ForeignJNDIProviderOverrideMBean lookupForeignJNDIProviderOverride(String var1);

   ForeignJNDIProviderOverrideMBean createForeignJNDIProviderOverride(String var1);

   void destroyForeignJNDIProviderOverride(ForeignJNDIProviderOverrideMBean var1);

   TargetMBean[] findEffectiveTargets();

   String[] findEffectiveServerNames();

   TargetMBean[] findEffectiveAdminTargets();

   ResourceGroupMBean[] findAdminResourceGroupsTargeted(String var1);

   @ExportCustomizeableValues(
      saveDefault = false
   )
   TargetMBean[] getDefaultTargets();

   void setDefaultTargets(TargetMBean[] var1);

   void addDefaultTarget(TargetMBean var1);

   void removeDefaultTarget(TargetMBean var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   TargetMBean[] getAvailableTargets();

   TargetMBean lookupAvailableTarget(String var1);

   void addAvailableTarget(TargetMBean var1);

   void removeAvailableTarget(TargetMBean var1);

   void setAvailableTargets(TargetMBean[] var1);

   SelfTuningMBean getSelfTuning();

   @ExportCustomizeableValues(
      saveDefault = true
   )
   RealmMBean getRealm();

   void setRealm(RealmMBean var1);

   String getPartitionID();

   void setPartitionID(String var1);

   PartitionLogMBean getPartitionLog();

   @ExportCustomizeableValues(
      saveDefault = false
   )
   String getPrimaryIdentityDomain();

   void setPrimaryIdentityDomain(String var1);

   int getMaxConcurrentNewThreads();

   void setMaxConcurrentNewThreads(int var1);

   int getMaxConcurrentLongRunningRequests();

   void setMaxConcurrentLongRunningRequests(int var1);

   ManagedExecutorServiceTemplateMBean[] getManagedExecutorServiceTemplates();

   ManagedExecutorServiceTemplateMBean createManagedExecutorServiceTemplate(String var1);

   void destroyManagedExecutorServiceTemplate(ManagedExecutorServiceTemplateMBean var1);

   ManagedExecutorServiceTemplateMBean lookupManagedExecutorServiceTemplate(String var1);

   ManagedScheduledExecutorServiceTemplateMBean[] getManagedScheduledExecutorServiceTemplates();

   ManagedScheduledExecutorServiceTemplateMBean createManagedScheduledExecutorServiceTemplate(String var1);

   void destroyManagedScheduledExecutorServiceTemplate(ManagedScheduledExecutorServiceTemplateMBean var1);

   ManagedScheduledExecutorServiceTemplateMBean lookupManagedScheduledExecutorServiceTemplate(String var1);

   ManagedThreadFactoryTemplateMBean[] getManagedThreadFactoryTemplates();

   ManagedThreadFactoryTemplateMBean createManagedThreadFactoryTemplate(String var1);

   void destroyManagedThreadFactoryTemplate(ManagedThreadFactoryTemplateMBean var1);

   ManagedThreadFactoryTemplateMBean lookupManagedThreadFactoryTemplate(String var1);

   JTAPartitionMBean getJTAPartition();

   void setDataSourceForJobScheduler(JDBCSystemResourceMBean var1);

   JDBCSystemResourceMBean getDataSourceForJobScheduler();

   String getJobSchedulerTableName();

   void setJobSchedulerTableName(String var1);

   ResourceManagerMBean getResourceManager();

   ResourceManagerMBean createResourceManager(String var1);

   void destroyResourceManager(ResourceManagerMBean var1);

   ResourceManagerMBean getResourceManagerRef();

   void setResourceManagerRef(ResourceManagerMBean var1);

   void destroyResourceManagerRef(ResourceManagerMBean var1);

   DataSourcePartitionMBean getDataSourcePartition();

   @ExportCustomizeableValues(
      saveDefault = true
   )
   CoherencePartitionCacheConfigMBean[] getCoherencePartitionCacheConfigs();

   CoherencePartitionCacheConfigMBean lookupCoherencePartitionCacheConfig(String var1);

   CoherencePartitionCacheConfigMBean createCoherencePartitionCacheConfig(String var1);

   void destroyCoherencePartitionCacheConfig(CoherencePartitionCacheConfigMBean var1);

   PartitionFileSystemMBean getSystemFileSystem();

   PartitionUserFileSystemMBean getUserFileSystem();

   @ExportCustomizeableValues(
      saveDefault = true
   )
   String getResourceDeploymentPlanPath();

   void setResourceDeploymentPlanPath(String var1);

   byte[] getResourceDeploymentPlan();

   byte[] getResourceDeploymentPlanExternalDescriptors();

   ResourceDeploymentPlanBean getResourceDeploymentPlanDescriptor();

   @ExportCustomizeableValues(
      saveDefault = true
   )
   JMSSystemResourceOverrideMBean[] getJMSSystemResourceOverrides();

   JMSSystemResourceOverrideMBean lookupJMSSystemResourceOverride(String var1);

   JMSSystemResourceOverrideMBean createJMSSystemResourceOverride(String var1);

   void destroyJMSSystemResourceOverride(JMSSystemResourceOverrideMBean var1);

   AppDeploymentMBean[] getInternalAppDeployments();

   AppDeploymentMBean lookupInternalAppDeployment(String var1);

   void destroyInternalAppDeployment(AppDeploymentMBean var1);

   AppDeploymentMBean createInternalAppDeployment(String var1, String var2) throws IllegalArgumentException;

   void setInternalAppDeployments(AppDeploymentMBean[] var1);

   LibraryMBean[] getInternalLibraries();

   LibraryMBean lookupInternalLibrary(String var1);

   LibraryMBean createInternalLibrary(String var1, String var2) throws IllegalArgumentException;

   void setInternalLibraries(LibraryMBean[] var1);

   PartitionWorkManagerMBean createPartitionWorkManager(String var1);

   PartitionWorkManagerMBean getPartitionWorkManager();

   void destroyPartitionWorkManager(PartitionWorkManagerMBean var1);

   void setPartitionWorkManagerRef(PartitionWorkManagerMBean var1);

   PartitionWorkManagerMBean getPartitionWorkManagerRef();

   void destroyPartitionWorkManagerRef(PartitionWorkManagerMBean var1);

   String getBatchJobsDataSourceJndiName();

   void setBatchJobsDataSourceJndiName(String var1);

   String getBatchJobsExecutorServiceName();

   void setBatchJobsExecutorServiceName(String var1);

   boolean isParallelDeployApplications();

   void setParallelDeployApplications(boolean var1);

   boolean isParallelDeployApplicationModules();

   void setParallelDeployApplicationModules(boolean var1);

   String getUploadDirectoryName();

   void setUploadDirectoryName(String var1);

   void setPartitionLifeCycleTimeoutVal(int var1);

   int getPartitionLifeCycleTimeoutVal();

   void setGracefulShutdownTimeout(int var1);

   void setStartupTimeout(int var1);

   int getStartupTimeout();

   int getGracefulShutdownTimeout();

   boolean isIgnoreSessionsDuringShutdown();

   void setIgnoreSessionsDuringShutdown(boolean var1);

   int getRCMHistoricalDataBufferLimit();

   void setRCMHistoricalDataBufferLimit(int var1);

   WebServiceMBean getWebService();

   boolean isEagerTrackingOfResourceMetricsEnabled();

   void setEagerTrackingOfResourceMetricsEnabled(boolean var1);

   AdminVirtualTargetMBean getAdminVirtualTarget();

   AdminVirtualTargetMBean createAdminVirtualTarget(String var1);

   void destroyAdminVirtualTarget(AdminVirtualTargetMBean var1);

   TargetMBean[] findEffectiveAvailableTargets();

   TargetMBean lookupEffectiveAvailableTarget(String var1);
}
