package weblogic.management.configuration;

import java.util.Properties;

public interface AuthModuleMBean extends ConfigurationMBean {
   String getClassName();

   void setClassName(String var1);

   String getModuleType();

   void setModuleType(String var1);

   Properties getProperties();

   void setProperties(Properties var1);
}
