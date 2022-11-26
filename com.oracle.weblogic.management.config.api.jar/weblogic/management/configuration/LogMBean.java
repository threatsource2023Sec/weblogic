package weblogic.management.configuration;

import java.util.Properties;

public interface LogMBean extends CommonLogMBean {
   int PROD_MODE_MEMORY_BUFFER_SIZE = 500;
   int DEV_MODE_MEMORY_BUFFER_SIZE = 10;

   LogFilterMBean getLogFileFilter();

   void setLogFileFilter(LogFilterMBean var1);

   LogFilterMBean getStdoutFilter();

   void setStdoutFilter(LogFilterMBean var1);

   String getDomainLogBroadcastSeverity();

   void setDomainLogBroadcastSeverity(String var1);

   LogFilterMBean getDomainLogBroadcastFilter();

   void setDomainLogBroadcastFilter(LogFilterMBean var1);

   /** @deprecated */
   @Deprecated
   String getMemoryBufferSeverity();

   /** @deprecated */
   @Deprecated
   void setMemoryBufferSeverity(String var1);

   /** @deprecated */
   @Deprecated
   LogFilterMBean getMemoryBufferFilter();

   /** @deprecated */
   @Deprecated
   void setMemoryBufferFilter(LogFilterMBean var1);

   /** @deprecated */
   @Deprecated
   int getMemoryBufferSize();

   /** @deprecated */
   @Deprecated
   void setMemoryBufferSize(int var1);

   /** @deprecated */
   @Deprecated
   boolean isLog4jLoggingEnabled();

   /** @deprecated */
   @Deprecated
   void setLog4jLoggingEnabled(boolean var1);

   boolean isRedirectStdoutToServerLogEnabled();

   void setRedirectStdoutToServerLogEnabled(boolean var1);

   boolean isRedirectStderrToServerLogEnabled();

   void setRedirectStderrToServerLogEnabled(boolean var1);

   int getDomainLogBroadcasterBufferSize();

   void setDomainLogBroadcasterBufferSize(int var1);

   /** @deprecated */
   @Deprecated
   boolean isServerLoggingBridgeUseParentLoggersEnabled();

   /** @deprecated */
   @Deprecated
   void setServerLoggingBridgeUseParentLoggersEnabled(boolean var1);

   Properties getPlatformLoggerLevels();

   void setPlatformLoggerLevels(Properties var1);

   boolean isServerLoggingBridgeAtRootLoggerEnabled();

   void setServerLoggingBridgeAtRootLoggerEnabled(boolean var1);

   boolean isLogMonitoringEnabled();

   void setLogMonitoringEnabled(boolean var1);

   int getLogMonitoringIntervalSecs();

   void setLogMonitoringIntervalSecs(int var1);

   int getLogMonitoringThrottleThreshold();

   void setLogMonitoringThrottleThreshold(int var1);

   int getLogMonitoringThrottleMessageLength();

   void setLogMonitoringThrottleMessageLength(int var1);

   int getLogMonitoringMaxThrottleMessageSignatureCount();

   void setLogMonitoringMaxThrottleMessageSignatureCount(int var1);
}
