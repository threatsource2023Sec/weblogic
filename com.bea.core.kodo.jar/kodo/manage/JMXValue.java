package kodo.manage;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.PluginValue;

public class JMXValue extends PluginValue {
   public static final String KEY = "JMX";

   public JMXValue() {
      super("JMX", true);
      String[] aliases = new String[]{"none", "kodo.manage.ManagementConfigurationNone", "local", "kodo.manage.ManagementConfigurationManagement", "gui", "kodo.manage.ManagementConfigurationManagementLocal", "jmx2", "kodo.manage.ManagementConfigurationManagementJMX2Remote", "mx4j1", "kodo.manage.ManagementConfigurationManagementMX4J1Remote", "wl81", "kodo.manage.ManagementConfigurationManagementWL81"};
      this.setAliases(aliases);
      this.setDefault(aliases[0]);
      this.setString(aliases[0]);
      this.setScope(this.getClass());
   }

   public static JMXValue getInstance(OpenJPAConfiguration conf) {
      return (JMXValue)conf.getValue("JMX");
   }

   public static ManagementConfiguration getManagementConfiguration(OpenJPAConfiguration conf) {
      JMXValue value = getInstance(conf);
      if (value.get() == null) {
         value.instantiate(ManagementConfiguration.class, conf);
      }

      return (ManagementConfiguration)value.get();
   }
}
