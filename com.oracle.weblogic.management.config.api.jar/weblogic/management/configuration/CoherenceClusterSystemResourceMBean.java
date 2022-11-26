package weblogic.management.configuration;

import weblogic.coherence.descriptor.wl.WeblogicCoherenceBean;
import weblogic.management.ManagementException;

public interface CoherenceClusterSystemResourceMBean extends SystemResourceMBean {
   String getName();

   String getDescriptorFileName();

   String getCustomClusterConfigurationFileName();

   boolean isUsingCustomClusterConfigurationFile();

   void importCustomClusterConfigurationFile(String var1) throws ManagementException;

   WeblogicCoherenceBean getCoherenceClusterResource();

   void setUsingCustomClusterConfigurationFile(boolean var1) throws ManagementException;

   CoherenceCacheConfigMBean[] getCoherenceCacheConfigs();

   CoherenceCacheConfigMBean createCoherenceCacheConfig(String var1);

   void destroyCoherenceCacheConfig(CoherenceCacheConfigMBean var1);

   CoherenceCacheConfigMBean lookupCoherenceCacheConfig(String var1);

   String getReportGroupFile();

   void setReportGroupFile(String var1);

   TargetMBean[] getTargets();

   String[] getClusterHosts();

   long getCustomConfigFileLastUpdatedTime();

   String getPersistenceDefaultMode();

   String getPersistenceActiveDirectory();

   String getPersistenceSnapshotDirectory();

   String getPersistenceTrashDirectory();

   String getFederationTopology();

   String[] getFederationRemoteParticipantHosts();

   String getFederationRemoteClusterName();

   int getFederationRemoteClusterListenPort();
}
