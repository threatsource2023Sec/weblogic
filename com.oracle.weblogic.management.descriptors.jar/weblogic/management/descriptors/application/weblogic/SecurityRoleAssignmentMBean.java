package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface SecurityRoleAssignmentMBean extends XMLElementMBean {
   String getRoleName();

   void setRoleName(String var1);

   String[] getPrincipalNames();

   void setPrincipalNames(String[] var1);

   void addPrincipalName(String var1);

   void removePrincipalName(String var1);
}
