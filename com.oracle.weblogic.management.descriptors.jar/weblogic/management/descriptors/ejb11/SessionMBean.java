package weblogic.management.descriptors.ejb11;

public interface SessionMBean extends EnterpriseBeanMBean {
   String getHome();

   void setHome(String var1);

   String getRemote();

   void setRemote(String var1);

   String getSessionType();

   void setSessionType(String var1);

   String getTransactionType();

   void setTransactionType(String var1);

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
