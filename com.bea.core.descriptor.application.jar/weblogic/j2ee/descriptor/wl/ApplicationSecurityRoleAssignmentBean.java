package weblogic.j2ee.descriptor.wl;

import weblogic.j2ee.descriptor.EmptyBean;

public interface ApplicationSecurityRoleAssignmentBean {
   String getRoleName();

   void setRoleName(String var1);

   String[] getPrincipalNames();

   void addPrincipalName(String var1);

   void removePrincipalName(String var1);

   void setPrincipalNames(String[] var1);

   EmptyBean getExternallyDefined();

   EmptyBean createExternallyDefined();

   void destroyExternallyDefined(EmptyBean var1);
}
