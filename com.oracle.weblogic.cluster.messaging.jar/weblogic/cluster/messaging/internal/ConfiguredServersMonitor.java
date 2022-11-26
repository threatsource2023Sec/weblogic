package weblogic.cluster.messaging.internal;

import java.util.SortedSet;

public interface ConfiguredServersMonitor {
   ServerConfigurationInformation getLocalServerConfiguration();

   SortedSet getConfiguredServers();

   ServerConfigurationInformation getConfiguration(String var1);
}
