package weblogic.management.runtime;

import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;

public interface JMSRuntimeMBean extends RuntimeMBean, HealthFeedback {
   HealthState getHealthState();

   JMSConnectionRuntimeMBean[] getConnections();

   long getConnectionsCurrentCount();

   long getConnectionsHighCount();

   long getConnectionsTotalCount();

   JMSServerRuntimeMBean[] getJMSServers();

   long getJMSServersCurrentCount();

   long getJMSServersHighCount();

   long getJMSServersTotalCount();

   JMSPooledConnectionRuntimeMBean[] getJMSPooledConnections();
}
