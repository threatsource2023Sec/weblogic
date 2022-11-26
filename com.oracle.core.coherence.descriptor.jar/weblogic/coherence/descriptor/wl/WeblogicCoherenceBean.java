package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface WeblogicCoherenceBean extends SettableBean {
   String getName();

   void setName(String var1);

   CoherenceClusterParamsBean getCoherenceClusterParams();

   CoherenceLoggingParamsBean getCoherenceLoggingParams();

   CoherenceAddressProvidersBean getCoherenceAddressProviders();

   String getCustomClusterConfigurationFileName();

   void setCustomClusterConfigurationFileName(String var1);

   long getCustomClusterConfigurationFileLastUpdatedTimestamp();

   void setCustomClusterConfigurationFileLastUpdatedTimestamp(long var1);

   String getVersion();

   void setVersion(String var1);

   CoherencePersistenceParamsBean getCoherencePersistenceParams();

   CoherenceFederationParamsBean getCoherenceFederationParams();
}
