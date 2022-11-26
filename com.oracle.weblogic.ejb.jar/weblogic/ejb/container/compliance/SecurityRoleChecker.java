package weblogic.ejb.container.compliance;

import java.util.Iterator;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.SecurityRoleReference;
import weblogic.utils.ErrorCollectionException;

public final class SecurityRoleChecker extends BaseComplianceChecker {
   private final DeploymentInfo di;

   public SecurityRoleChecker(DeploymentInfo di) {
      this.di = di;
   }

   public void checkSecurityRoleRefLinks() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.di.getBeanInfos().iterator();

      while(var2.hasNext()) {
         BeanInfo binfo = (BeanInfo)var2.next();
         Iterator var4 = binfo.getAllSecurityRoleReferences().iterator();

         while(var4.hasNext()) {
            SecurityRoleReference roleRef = (SecurityRoleReference)var4.next();
            String link = roleRef.getReferencedRole();
            if (link == null) {
               Log.getInstance().logWarning(this.getFmt().NULL_SECURITY_ROLE_REF_LINK(binfo.getEJBName(), roleRef.getRoleName()));
            } else if (!this.di.getDeploymentRoles().hasRole(link)) {
               errors.add(new ComplianceException(this.getFmt().INVALID_SECURITY_ROLE_REF_LINK(binfo.getEJBName(), roleRef.getRoleName())));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }
}
