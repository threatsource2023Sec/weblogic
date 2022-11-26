package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface AuthConstraintMBean extends WebElementMBean {
   SecurityRoleMBean[] getRoles();

   void setRoles(SecurityRoleMBean[] var1);

   void addRole(SecurityRoleMBean var1);

   void removeRole(SecurityRoleMBean var1);

   String getDescription();

   void setDescription(String var1);
}
