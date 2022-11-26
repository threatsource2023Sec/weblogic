package weblogic.management.configuration;

import java.util.Properties;

public interface InterceptorMBean extends ConfigurationMBean {
   String getInterceptorTypeName();

   void setInterceptorTypeName(String var1);

   String getInterceptedTargetKey();

   void setInterceptedTargetKey(String var1);

   String[] getInterceptedOperationNames();

   void setInterceptedOperationNames(String[] var1);

   int getPriority();

   void setPriority(int var1);

   String[] getDependsOn();

   void setDependsOn(String[] var1);

   Properties getProperties();

   void setProperties(Properties var1);
}
