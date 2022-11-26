package weblogic.management.configuration;

public interface SingletonServiceBaseMBean extends ConfigurationMBean {
   ServerMBean getHostingServer();

   void setHostingServer(ServerMBean var1);

   ServerMBean getUserPreferredServer();

   void setUserPreferredServer(ServerMBean var1);

   int getAdditionalMigrationAttempts();

   void setAdditionalMigrationAttempts(int var1);

   int getMillisToSleepBetweenAttempts();

   void setMillisToSleepBetweenAttempts(int var1);
}
