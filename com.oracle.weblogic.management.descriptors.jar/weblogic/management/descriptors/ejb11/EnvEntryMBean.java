package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface EnvEntryMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getEnvEntryName();

   void setEnvEntryName(String var1);

   String getEnvEntryType();

   void setEnvEntryType(String var1);

   String getEnvEntryValue();

   void setEnvEntryValue(String var1);
}
