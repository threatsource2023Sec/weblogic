package weblogic.management.configuration;

public interface StuckThreadActionMBean extends ConfigurationMBean {
   String IGNORE_STUCK_THREADS = "ignore_stuck_threads";
   String SHUTDOWN_WORKMANAGER = "shutdown_work_manager";
   String APP_ADMIN_MODE = "application_admin_mode";
   String SERVER_FAILED = "server_failed";

   void setWorkManagerName(String var1);

   String getWorkManagerName();

   void setModuleName(String var1);

   String getModuleName();

   void setApplicationName(String var1);

   String getApplicationName();

   void setMaxStuckThreadsCount(int var1);

   int getMaxStuckThreadsCount();

   void setActionCode(String var1);

   String getActionCode();
}
