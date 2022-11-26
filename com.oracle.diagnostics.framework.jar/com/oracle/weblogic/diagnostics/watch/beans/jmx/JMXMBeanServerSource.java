package com.oracle.weblogic.diagnostics.watch.beans.jmx;

import javax.management.MBeanServerConnection;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface JMXMBeanServerSource {
   String DIAGNOSTICS_JMX_PLATFORM_SOURCE = "platform";
   String DIAGNOSTICS_JMX_SERVER_SOURCE = "serverRuntime";
   String DIAGNOSTICS_JMX_DOMAIN_SOURCE = "domainRuntime";
   String DIAGNOSTICS_JMX_CLUSTER_SOURCE = "clusterRuntime";

   MBeanServerConnection getMBeanServerConnection();

   String getName();
}
