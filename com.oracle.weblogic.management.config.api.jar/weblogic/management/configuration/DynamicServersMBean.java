package weblogic.management.configuration;

public interface DynamicServersMBean extends ConfigurationMBean {
   int DEFAULT_LISTEN_PORT_RANGE_BASE = 7100;
   int DEFAULT_SSL_LISTEN_PORT_RANGE_BASE = 8100;
   int DEFAULT_NAP_LISTEN_PORT_RANGE_BASE = 9100;
   int DEFAULT_ADMINISTRATION_PORT_RANGE_BASE = 9002;

   ServerTemplateMBean getServerTemplate();

   void setServerTemplate(ServerTemplateMBean var1);

   /** @deprecated */
   @Deprecated
   void setMaximumDynamicServerCount(int var1);

   /** @deprecated */
   @Deprecated
   int getMaximumDynamicServerCount();

   void setCalculatedListenPorts(boolean var1);

   boolean isCalculatedListenPorts();

   void setCalculatedMachineNames(boolean var1);

   boolean isCalculatedMachineNames();

   void setMachineNameMatchExpression(String var1);

   String getMachineNameMatchExpression();

   void setMachineMatchExpression(String var1);

   String getMachineMatchExpression();

   void setMachineMatchType(String var1);

   String getMachineMatchType();

   void setServerNamePrefix(String var1);

   String getServerNamePrefix();

   int getDynamicClusterSize();

   void setDynamicClusterSize(int var1);

   int getMinDynamicClusterSize();

   void setMinDynamicClusterSize(int var1);

   int getMaxDynamicClusterSize();

   void setMaxDynamicClusterSize(int var1);

   int getDynamicClusterCooloffPeriodSeconds();

   void setDynamicClusterCooloffPeriodSeconds(int var1);

   int getDynamicClusterShutdownTimeoutSeconds();

   void setDynamicClusterShutdownTimeoutSeconds(int var1);

   boolean isIgnoreSessionsDuringShutdown();

   void setIgnoreSessionsDuringShutdown(boolean var1);

   boolean isWaitForAllSessionsDuringShutdown();

   void setWaitForAllSessionsDuringShutdown(boolean var1);

   String[] getDynamicServerNames();

   int getServerNameStartingIndex();

   void setServerNameStartingIndex(int var1);
}
