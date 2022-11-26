package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface SecurityRoleRefMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getRoleName();

   void setRoleName(String var1);

   String getRoleLink();

   void setRoleLink(String var1);
}
