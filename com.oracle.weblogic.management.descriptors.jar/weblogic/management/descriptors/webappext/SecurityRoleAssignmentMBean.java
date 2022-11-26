package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.webapp.SecurityRoleMBean;

public interface SecurityRoleAssignmentMBean extends WebElementMBean {
   void setRole(SecurityRoleMBean var1);

   SecurityRoleMBean getRole();

   void setPrincipalNames(String[] var1);

   String[] getPrincipalNames();

   void addPrincipalName(String var1);

   void removePrincipalName(String var1);

   boolean isExternallyDefined();

   void setExternallyDefined(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isGlobalRole();

   /** @deprecated */
   @Deprecated
   void setGlobalRole(boolean var1);
}
