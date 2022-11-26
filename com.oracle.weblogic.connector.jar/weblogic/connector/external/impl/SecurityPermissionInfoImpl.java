package weblogic.connector.external.impl;

import weblogic.connector.external.SecurityPermissionInfo;
import weblogic.j2ee.descriptor.SecurityPermissionBean;

public class SecurityPermissionInfoImpl implements SecurityPermissionInfo {
   private SecurityPermissionBean securityPerm;

   public SecurityPermissionInfoImpl(SecurityPermissionBean securityPerm) {
      this.securityPerm = securityPerm;
   }

   public String getDescription() {
      String[] descriptions = this.securityPerm.getDescriptions();
      return descriptions[0];
   }

   public String[] getDescriptions() {
      return this.securityPerm.getDescriptions();
   }

   public String getSpec() {
      return this.securityPerm.getSecurityPermissionSpec();
   }
}
