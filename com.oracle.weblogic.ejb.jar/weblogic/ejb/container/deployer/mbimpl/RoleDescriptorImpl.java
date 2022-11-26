package weblogic.ejb.container.deployer.mbimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import weblogic.ejb.container.interfaces.RoleDescriptor;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.wl.SecurityRoleAssignmentBean;

public final class RoleDescriptorImpl implements RoleDescriptor {
   private final String name;
   private final Collection securityPrincipals = new ArrayList();
   private boolean isExternallyDefined;
   private boolean anyAuthUserRoleDefined = false;

   public RoleDescriptorImpl(EjbDescriptorBean desc, String roleName) {
      this.name = roleName;
      SecurityRoleAssignmentBean[] var3 = desc.getWeblogicEjbJarBean().getSecurityRoleAssignments();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         SecurityRoleAssignmentBean sra = var3[var5];
         if (sra.getRoleName().equals(this.name)) {
            this.securityPrincipals.addAll(Arrays.asList(sra.getPrincipalNames()));
            this.isExternallyDefined = sra.getExternallyDefined() != null;
            this.anyAuthUserRoleDefined = "**".equals(sra.getRoleName());
         }
      }

   }

   public String getName() {
      return this.name;
   }

   public Collection getAllSecurityPrincipals() {
      return this.securityPrincipals;
   }

   public boolean isExternallyDefined() {
      return this.isExternallyDefined;
   }

   public boolean isAnyAuthUserRoleDefined() {
      return this.anyAuthUserRoleDefined;
   }
}
