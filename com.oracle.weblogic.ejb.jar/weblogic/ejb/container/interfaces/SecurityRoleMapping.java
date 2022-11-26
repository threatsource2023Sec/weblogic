package weblogic.ejb.container.interfaces;

import java.util.Collection;

public interface SecurityRoleMapping {
   Collection getSecurityRoleNames();

   boolean hasRole(String var1);

   Collection getSecurityRolePrincipalNames(String var1) throws NoSuchRoleException;

   void addRoleToPrincipalsMapping(String var1, Collection var2);

   boolean isRoleMappedToPrincipals(String var1) throws NoSuchRoleException;

   void addExternallyDefinedRole(String var1);

   boolean isExternallyDefinedRole(String var1) throws NoSuchRoleException;
}
