package weblogic.ejb.container.deployer.mbimpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.interfaces.NoSuchRoleException;
import weblogic.ejb.container.interfaces.SecurityRoleMapping;

public final class SecurityRoleMappingImpl implements SecurityRoleMapping {
   private final Map securityRoles = new HashMap();
   private Set externallyDefinedRoles = null;

   public final Collection getSecurityRoleNames() {
      return this.securityRoles.keySet();
   }

   public boolean hasRole(String roleName) {
      return this.securityRoles.get(roleName) != null;
   }

   public final Collection getSecurityRolePrincipalNames(String roleName) throws NoSuchRoleException {
      Collection prinNames = (Collection)this.securityRoles.get(roleName);
      if (prinNames == null) {
         throw new NoSuchRoleException(roleName + " is not a recognized role");
      } else {
         return prinNames;
      }
   }

   public void addRoleToPrincipalsMapping(String roleName, Collection principalNames) {
      this.securityRoles.put(roleName, principalNames);
   }

   public boolean isRoleMappedToPrincipals(String roleName) throws NoSuchRoleException {
      if (!this.securityRoles.containsKey(roleName)) {
         throw new NoSuchRoleException(roleName + " is not a recognized role");
      } else {
         Collection principalNames = (Collection)this.securityRoles.get(roleName);
         return principalNames != null && !principalNames.isEmpty();
      }
   }

   public void addExternallyDefinedRole(String roleName) {
      if (this.externallyDefinedRoles == null) {
         this.externallyDefinedRoles = new HashSet();
      }

      this.externallyDefinedRoles.add(roleName);
   }

   public boolean isExternallyDefinedRole(String roleName) throws NoSuchRoleException {
      if (!this.securityRoles.containsKey(roleName)) {
         throw new NoSuchRoleException(roleName + " is not a recognized role");
      } else {
         return this.externallyDefinedRoles == null ? false : this.externallyDefinedRoles.contains(roleName);
      }
   }

   public String toString() {
      StringBuilder result = new StringBuilder("SecurityRoleMapping:\n");
      Iterator var2 = this.securityRoles.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry e = (Map.Entry)var2.next();
         result.append("   Role:" + (String)e.getKey() + "\n");
         Iterator var4 = ((Collection)e.getValue()).iterator();

         while(var4.hasNext()) {
            String p = (String)var4.next();
            result.append("      " + p + "\n");
         }
      }

      return result.toString();
   }
}
