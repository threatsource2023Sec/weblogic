package weblogic.management.descriptors.ejb20;

public interface MethodPermissionMBean extends weblogic.management.descriptors.ejb11.MethodPermissionMBean {
   String[] getRoleNames();

   void setRoleNames(String[] var1);

   void addRoleName(String var1);

   void removeRoleName(String var1);

   boolean isUnchecked();

   void setUnchecked(boolean var1);
}
