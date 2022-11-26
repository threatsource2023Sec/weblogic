package com.bea.common.security.saml.registry;

import weblogic.management.security.ProviderMBean;
import weblogic.security.providers.utils.CertRegLDAPDelegate;
import weblogic.security.spi.SecurityServices;

public final class SAMLIdentityAsserterLDAPDelegate extends CertRegLDAPDelegate {
   public SAMLIdentityAsserterLDAPDelegate(ProviderMBean mbean, SecurityServices services) {
      super(mbean, services);
   }

   protected String getRegistryDNName() {
      return "SAMLCertReg";
   }

   protected String getBaseAuditEventType() {
      return "SAMLCertReg";
   }

   protected String getDebugLogName() {
      return "SecuritySAMLLib";
   }
}
