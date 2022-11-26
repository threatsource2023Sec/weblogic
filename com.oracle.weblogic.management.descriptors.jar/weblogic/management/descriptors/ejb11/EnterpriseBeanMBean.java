package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface EnterpriseBeanMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getDisplayName();

   void setDisplayName(String var1);

   String getSmallIcon();

   void setSmallIcon(String var1);

   String getLargeIcon();

   void setLargeIcon(String var1);

   String getEJBName();

   void setEJBName(String var1);

   String getEJBClass();

   void setEJBClass(String var1);

   EnvEntryMBean[] getEnvEntries();

   void setEnvEntries(EnvEntryMBean[] var1);

   void addEnvEntry(EnvEntryMBean var1);

   void removeEnvEntry(EnvEntryMBean var1);

   EJBRefMBean[] getEJBRefs();

   void setEJBRefs(EJBRefMBean[] var1);

   void addEJBRef(EJBRefMBean var1);

   void removeEJBRef(EJBRefMBean var1);

   ResourceRefMBean[] getResourceRefs();

   void setResourceRefs(ResourceRefMBean[] var1);

   void addResourceRef(ResourceRefMBean var1);

   void removeResourceRef(ResourceRefMBean var1);
}
