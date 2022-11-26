package weblogic.management.configuration;

public interface DeploymentConfigOverridesMBean extends ConfigurationMBean {
   String getDir();

   void setDir(String var1);

   int getPollInterval();

   void setPollInterval(int var1);

   int getMaxOldAppVersions();

   void setMaxOldAppVersions(int var1);
}
