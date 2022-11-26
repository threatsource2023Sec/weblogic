package com.bea.logging;

import java.util.Properties;

public interface LoggingServiceConfig {
   String getLoggerSeverity();

   void setLoggerSeverity(String var1);

   LogFileConfig getLogFileConfig();

   void setLogFileConfig(LogFileConfig var1);

   StdoutConfig getStdoutConfig();

   void setStdoutConfig(StdoutConfig var1);

   Properties getLoggerSeverities();

   void setLoggerSeverities(Properties var1);
}
