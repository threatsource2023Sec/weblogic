package weblogic.management.configuration;

public interface DataSourcePartitionMBean extends ConfigurationMBean {
   String getDefaultDatasource();

   void setDefaultDatasource(String var1);
}
