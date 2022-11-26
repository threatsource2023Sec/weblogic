package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface SecurityRoleRefMBean extends WebElementMBean {
   String getRoleName();

   void setRoleName(String var1);

   SecurityRoleMBean getRoleLink();

   void setRoleLink(SecurityRoleMBean var1);

   String getDescription();

   void setDescription(String var1);
}
