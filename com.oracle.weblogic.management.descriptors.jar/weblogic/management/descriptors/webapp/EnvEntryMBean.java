package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface EnvEntryMBean extends WebElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getEnvEntryName();

   void setEnvEntryName(String var1);

   String getEnvEntryValue();

   void setEnvEntryValue(String var1);

   String getEnvEntryType();

   void setEnvEntryType(String var1);
}
