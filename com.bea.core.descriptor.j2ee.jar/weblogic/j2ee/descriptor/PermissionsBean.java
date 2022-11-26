package weblogic.j2ee.descriptor;

public interface PermissionsBean {
   PermissionBean[] getPermissions();

   PermissionBean createPermission();

   void destroyPermission(PermissionBean var1);

   String getVersion();

   void setVersion(String var1);
}
