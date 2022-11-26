package weblogic.management.configuration;

import java.util.Properties;

public interface CommonLogMBean extends LogFileMBean {
   String STDOUT_STANDARD = "standard";
   String STDOUT_NOID = "noid";

   String getLoggerSeverity();

   void setLoggerSeverity(String var1);

   Properties getLoggerSeverityProperties();

   void setLoggerSeverityProperties(Properties var1);

   String getLogFileSeverity();

   void setLogFileSeverity(String var1);

   String getStdoutSeverity();

   void setStdoutSeverity(String var1);

   String getStdoutFormat();

   void setStdoutFormat(String var1);

   boolean isStdoutLogStack();

   void setStdoutLogStack(boolean var1);

   int getStacktraceDepth();

   void setStacktraceDepth(int var1);
}
