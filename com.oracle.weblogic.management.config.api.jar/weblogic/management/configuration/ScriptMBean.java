package weblogic.management.configuration;

import java.util.Properties;

public interface ScriptMBean extends ConfigurationMBean {
   String getWorkingDirectory();

   void setWorkingDirectory(String var1);

   String getPathToScript();

   void setPathToScript(String var1);

   String[] getArguments();

   void setArguments(String[] var1);

   Properties getEnvironment();

   void setEnvironment(Properties var1);

   boolean isIgnoreFailures();

   void setIgnoreFailures(boolean var1);

   int getNumberOfRetriesAllowed();

   void setNumberOfRetriesAllowed(int var1);

   long getRetryDelayInMillis();

   void setRetryDelayInMillis(long var1);

   String getPathToErrorHandlerScript();

   void setPathToErrorHandlerScript(String var1);

   int getTimeoutInSeconds();

   void setTimeoutInSeconds(int var1);
}
