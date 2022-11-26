package weblogic.management.runtime;

public interface UnicastMessagingRuntimeMBean extends RuntimeMBean {
   int getRemoteGroupsDiscoveredCount();

   String getLocalGroupLeaderName();

   int getTotalGroupsCount();

   String[] getDiscoveredGroupLeaders();

   String getGroups();
}
