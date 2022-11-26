package weblogic.ejb.container.deployer.mbimpl;

import weblogic.ejb.container.interfaces.SecurityRoleReference;
import weblogic.j2ee.descriptor.SecurityRoleRefBean;

public final class SecurityRoleRefImpl implements SecurityRoleReference {
   private final String roleName;
   private final String referencedRole;

   public SecurityRoleRefImpl(SecurityRoleRefBean bean) {
      this.roleName = bean.getRoleName();
      this.referencedRole = bean.getRoleLink();
   }

   public String getRoleName() {
      return this.roleName;
   }

   public String getReferencedRole() {
      return this.referencedRole;
   }
}
