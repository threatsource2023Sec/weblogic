package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface RunAsRoleAssignmentMBean extends XMLElementMBean {
   String getRoleName();

   void setRoleName(String var1);

   String getRunAsPrincipalName();

   void setRunAsPrincipalName(String var1);
}
