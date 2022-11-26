package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface SecurityMBean extends XMLElementMBean {
   String getRealmName();

   void setRealmName(String var1);

   SecurityRoleAssignmentMBean[] getRoleAssignments();

   void setRoleAssignments(SecurityRoleAssignmentMBean[] var1);

   void addRoleAssignment(SecurityRoleAssignmentMBean var1);

   void removeRoleAssignment(SecurityRoleAssignmentMBean var1);
}
