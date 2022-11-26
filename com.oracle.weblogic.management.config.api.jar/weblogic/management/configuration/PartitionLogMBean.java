package weblogic.management.configuration;

import java.util.Properties;

public interface PartitionLogMBean extends ConfigurationMBean {
   Properties getPlatformLoggerLevels();

   void setPlatformLoggerLevels(Properties var1);

   String[] getEnabledServerDebugAttributes();

   void setEnabledServerDebugAttributes(String[] var1);

   boolean addEnabledServerDebugAttribute(String var1);

   boolean removeEnabledServerDebugAttribute(String var1);
}
