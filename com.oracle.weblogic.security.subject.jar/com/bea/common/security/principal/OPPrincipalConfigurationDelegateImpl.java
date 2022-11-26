package com.bea.common.security.principal;

import weblogic.security.principal.PrincipalConfigurationDelegate;

public class OPPrincipalConfigurationDelegateImpl extends PrincipalConfigurationDelegate {
   private boolean equalsCaseInsensitive = false;
   private boolean equalsCompareDnAndGuid = false;

   public OPPrincipalConfigurationDelegateImpl() {
      this.equalsCaseInsensitive = Boolean.valueOf(System.getProperty("PrincipalEqualsCaseInsensitive", "false"));
      this.equalsCompareDnAndGuid = Boolean.valueOf(System.getProperty("PrincipalEqualsCompareDnAndGuid", "false"));
   }

   public boolean isEqualsCaseInsensitive() {
      return this.equalsCaseInsensitive;
   }

   public boolean isEqualsCompareDnAndGuid() {
      return this.equalsCompareDnAndGuid;
   }
}
