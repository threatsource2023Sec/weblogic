package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface SNMPAgentRuntimeMBean extends RuntimeMBean {
   boolean isRunning();

   long getAttributeChangeTrapCount();

   long getMonitorTrapCount();

   long getCounterMonitorTrapCount();

   long getGaugeMonitorTrapCount();

   long getStringMonitorTrapCount();

   long getLogMessageTrapCount();

   long getServerStartTrapCount();

   long getServerStopTrapCount();

   int getUDPListenPort();

   int getMasterAgentXPort();

   String outputCustomMBeansMIBModule() throws ManagementException;

   int getFailedAuthenticationCount();

   int getFailedAuthorizationCount();

   int getFailedEncryptionCount();

   void invalidateLocalizedKeyCache(String var1);

   String getSNMPAgentName();
}
