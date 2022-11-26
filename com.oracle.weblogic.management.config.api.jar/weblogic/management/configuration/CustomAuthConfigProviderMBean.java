package weblogic.management.configuration;

import java.util.Properties;

public interface CustomAuthConfigProviderMBean extends AuthConfigProviderMBean {
   String getClassName();

   void setClassName(String var1);

   Properties getProperties();

   void setProperties(Properties var1);
}
