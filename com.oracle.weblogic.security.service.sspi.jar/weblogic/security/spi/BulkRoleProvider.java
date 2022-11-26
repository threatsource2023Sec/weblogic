package weblogic.security.spi;

public interface BulkRoleProvider extends RoleProvider {
   BulkRoleMapper getBulkRoleMapper();
}
