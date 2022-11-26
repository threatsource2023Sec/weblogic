package weblogic.security.spi;

public interface DeployableRoleProviderV2 extends RoleProvider {
   DeployRoleHandle startDeployRoles(ApplicationInfo var1) throws DeployHandleCreationException;

   void deployRole(DeployRoleHandle var1, Resource var2, String var3, String[] var4) throws RoleCreationException;

   void endDeployRoles(DeployRoleHandle var1) throws RoleCreationException;

   void undeployAllRoles(DeployRoleHandle var1) throws RoleRemovalException;

   void deleteApplicationRoles(ApplicationInfo var1) throws RoleRemovalException;
}
