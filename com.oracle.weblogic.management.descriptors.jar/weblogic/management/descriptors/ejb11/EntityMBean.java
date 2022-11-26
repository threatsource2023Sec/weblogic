package weblogic.management.descriptors.ejb11;

public interface EntityMBean extends EnterpriseBeanMBean {
   String getHome();

   void setHome(String var1);

   String getRemote();

   void setRemote(String var1);

   void setPersistenceType(String var1);

   String getPersistenceType();

   String getPrimKeyClass();

   void setPrimKeyClass(String var1);

   boolean isReentrant();

   void setReentrant(boolean var1);

   CMPFieldMBean[] getCMPFields();

   void setCMPFields(CMPFieldMBean[] var1);

   void addCMPField(CMPFieldMBean var1);

   void removeCMPField(CMPFieldMBean var1);

   String getPrimkeyField();

   void setPrimkeyField(String var1);

   EnvEntryMBean[] getEnvEntries();

   void setEnvEntries(EnvEntryMBean[] var1);

   void addEnvEntry(EnvEntryMBean var1);

   void removeEnvEntry(EnvEntryMBean var1);

   EJBRefMBean[] getEJBRefs();

   void setEJBRefs(EJBRefMBean[] var1);

   void addEJBRef(EJBRefMBean var1);

   void removeEJBRef(EJBRefMBean var1);

   SecurityRoleRefMBean[] getSecurityRoleRefs();

   void setSecurityRoleRefs(SecurityRoleRefMBean[] var1);

   void addSecurityRoleRef(SecurityRoleRefMBean var1);

   void removeSecurityRoleRef(SecurityRoleRefMBean var1);

   ResourceRefMBean[] getResourceRefs();

   void setResourceRefs(ResourceRefMBean[] var1);

   void addResourceRef(ResourceRefMBean var1);

   void removeResourceRef(ResourceRefMBean var1);
}
