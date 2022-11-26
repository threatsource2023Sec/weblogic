package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceFederationParamsBean extends SettableBean {
   String NONE_TOPOLOGY = "none";
   String ACTIVE_ACTIVE_TOPOLOGY = "active-active";
   String ACTIVE_PASSIVE_TOPOLOGY = "active-passive";
   String PASSIVE_ACTIVE_TOPOLOGY = "passive-active";

   String getFederationTopology();

   void setFederationTopology(String var1);

   String[] getRemoteParticipantHosts();

   void addRemoteParticipantHost(String var1);

   void removeRemoteParticipantHost(String var1);

   void setRemoteParticipantHosts(String[] var1);

   String getRemoteCoherenceClusterName();

   void setRemoteCoherenceClusterName(String var1);

   int getRemoteCoherenceClusterListenPort();

   void setRemoteCoherenceClusterListenPort(int var1);
}
