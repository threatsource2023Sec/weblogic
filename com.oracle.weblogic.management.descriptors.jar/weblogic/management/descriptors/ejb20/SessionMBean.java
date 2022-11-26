package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.ejb11.EJBRefMBean;
import weblogic.management.descriptors.ejb11.EnvEntryMBean;
import weblogic.management.descriptors.ejb11.SecurityRoleRefMBean;

public interface SessionMBean extends weblogic.management.descriptors.ejb11.SessionMBean {
   String getHome();

   void setHome(String var1);

   String getRemote();

   void setRemote(String var1);

   String getLocalHome();

   void setLocalHome(String var1);

   String getLocal();

   void setLocal(String var1);

   String getEJBClass();

   void setEJBClass(String var1);

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

   EJBLocalRefMBean[] getEJBLocalRefs();

   void setEJBLocalRefs(EJBLocalRefMBean[] var1);

   void addEJBLocalRef(EJBLocalRefMBean var1);

   void removeEJBLocalRef(EJBLocalRefMBean var1);

   SecurityRoleRefMBean[] getSecurityRoleRefs();

   void setSecurityRoleRefs(SecurityRoleRefMBean[] var1);

   void addSecurityRoleRef(SecurityRoleRefMBean var1);

   void removeSecurityRoleRef(SecurityRoleRefMBean var1);

   SecurityIdentityMBean getSecurityIdentity();

   void setSecurityIdentity(SecurityIdentityMBean var1);

   weblogic.management.descriptors.ejb11.ResourceRefMBean[] getResourceRefs();

   void setResourceRefs(weblogic.management.descriptors.ejb11.ResourceRefMBean[] var1);

   void addResourceRef(weblogic.management.descriptors.ejb11.ResourceRefMBean var1);

   void removeResourceRef(weblogic.management.descriptors.ejb11.ResourceRefMBean var1);

   ResourceEnvRefMBean[] getResourceEnvRefs();

   void setResourceEnvRefs(ResourceEnvRefMBean[] var1);

   void addResourceEnvRef(ResourceEnvRefMBean var1);

   void removeResourceEnvRef(ResourceEnvRefMBean var1);
}
