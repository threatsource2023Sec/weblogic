package weblogic.j2ee.descriptor.wl;

public interface SecurityBean {
   /** @deprecated */
   @Deprecated
   String getRealmName();

   void setRealmName(String var1);

   ApplicationSecurityRoleAssignmentBean[] getSecurityRoleAssignments();

   ApplicationSecurityRoleAssignmentBean createSecurityRoleAssignment();

   void destroySecurityRoleAssignment(ApplicationSecurityRoleAssignmentBean var1);
}
