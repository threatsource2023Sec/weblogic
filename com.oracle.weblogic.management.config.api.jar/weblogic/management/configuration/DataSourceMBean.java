package weblogic.management.configuration;

public interface DataSourceMBean extends DeploymentMBean {
   String RMIJDBC_SECURE = "Secure";
   String RMIJDBC_COMPATIBILITY = "Compatibility";

   DataSourceLogFileMBean getDataSourceLogFile();

   String getRmiJDBCSecurity();

   void setRmiJDBCSecurity(String var1);

   String getDefaultDatasource();

   void setDefaultDatasource(String var1);
}
