package com.bea.common.security.saml.registry;

import java.util.List;
import java.util.Set;
import weblogic.management.security.ProviderMBean;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.providers.utils.BusinessObjectListerManager;
import weblogic.security.providers.utils.CertRegLDAPDelegate;
import weblogic.security.spi.SecurityServices;

public final class SAMLCertRegLDAPDelegate extends CertRegLDAPDelegate {
   public SAMLCertRegLDAPDelegate(ProviderMBean mbean, SecurityServices services) {
      super(mbean, services);
   }

   protected String getRegistryDNName() {
      return "SAMLCertificateRegistry";
   }

   protected String getBaseAuditEventType() {
      return "SAMLCertificateRegistry";
   }

   protected String getDebugLogName() {
      return "SecuritySAMLLib";
   }

   protected String listCertificates(BusinessObjectListerManager listerManager, String wildcard, int maxToReturn) throws InvalidParameterException, InvalidCursorException {
      return this.listAliases(listerManager, wildcard, maxToReturn);
   }

   protected String resolveCertificateAliasConflict(String alias, Set reservedAliases) throws InvalidParameterException {
      if (!this.aliasExists(alias)) {
         return alias;
      } else {
         String newAlias = null;
         int i = 0;

         do {
            do {
               ++i;
               newAlias = alias + "_" + i;
            } while(reservedAliases.contains(newAlias));
         } while(this.aliasExists(newAlias));

         return newAlias;
      }
   }

   protected List searchCertificatesByAliasFilter(String aliasWildCard) {
      return this.searchCertificatesByAliasFilter(aliasWildCard, 0);
   }
}
