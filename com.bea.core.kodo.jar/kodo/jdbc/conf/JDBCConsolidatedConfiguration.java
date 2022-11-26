package kodo.jdbc.conf;

import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.lib.conf.Value;

public class JDBCConsolidatedConfiguration extends JDBCConfigurationImpl {
   public JDBCConsolidatedConfiguration() {
   }

   public JDBCConsolidatedConfiguration(boolean defaults) {
      super(defaults);
   }

   public String getProfiling() {
      return this.get("Profiling");
   }

   public void setProfiling(String conf) {
      this.set("Profiling", conf);
   }

   public String getJMX() {
      return this.get("JMX");
   }

   public void setJMX(String conf) {
      this.set("JMX", conf);
   }

   public String getExecutionContextNameProvider() {
      return this.get("ExecutionContextNameProvider");
   }

   public void setExecutionContextNameProvider(String conf) {
      this.set("ExecutionContextNameProvider", conf);
   }

   public String getLicenseKey() {
      return this.get("LicenseKey");
   }

   public void setLicenseKey(String conf) {
      this.set("LicenseKey", conf);
   }

   public String getPersistenceServer() {
      return this.get("PersistenceServer");
   }

   public void setPersistenceServer(String conf) {
      this.set("PersistenceServer", conf);
   }

   private String get(String key) {
      Value val = this.getValue(key);
      return val == null ? null : val.getString();
   }

   private void set(String key, String conf) {
      Value val = this.getValue(key);
      if (val != null) {
         val.setString(conf);
      }

   }
}
