package org.opensaml.security.x509.impl;

import java.util.Set;
import org.opensaml.security.x509.PKIXValidationOptions;

public class CertPathPKIXValidationOptions extends PKIXValidationOptions {
   private boolean forceRevocationEnabled = false;
   private boolean revocationEnabled = true;
   private boolean policyMappingInhibit = false;
   private boolean anyPolicyInhibit = false;
   private Set initialPolicies = null;

   public boolean isForceRevocationEnabled() {
      return this.forceRevocationEnabled;
   }

   public void setForceRevocationEnabled(boolean flag) {
      this.forceRevocationEnabled = flag;
   }

   public boolean isRevocationEnabled() {
      return this.revocationEnabled;
   }

   public void setRevocationEnabled(boolean flag) {
      this.revocationEnabled = flag;
   }

   public boolean isPolicyMappingInhibited() {
      return this.policyMappingInhibit;
   }

   public void setPolicyMappingInhibit(boolean flag) {
      this.policyMappingInhibit = flag;
   }

   public boolean isAnyPolicyInhibited() {
      return this.anyPolicyInhibit;
   }

   public void setAnyPolicyInhibit(boolean flag) {
      this.anyPolicyInhibit = flag;
   }

   public Set getInitialPolicies() {
      return this.initialPolicies;
   }

   public void setInitialPolicies(Set newPolicies) {
      this.initialPolicies = newPolicies;
   }
}
