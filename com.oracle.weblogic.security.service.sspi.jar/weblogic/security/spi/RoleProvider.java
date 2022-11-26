package weblogic.security.spi;

public interface RoleProvider extends SecurityProvider {
   RoleMapper getRoleMapper();
}
