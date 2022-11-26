package com.bea.common.security.saml.registry;

import com.bea.common.security.store.data.BEASAMLAssertingParty;
import weblogic.management.security.ProviderMBean;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;
import weblogic.security.providers.saml.registry.SAMLAssertingParty;
import weblogic.security.spi.SecurityServices;

public class SAMLAssertingPartyRegistry extends SAMLPartnerRegistry {
   private static final String AP_REGISTRY_NAME = "SAMLAssertingPartyRegistry";
   private static final String AP_REGISTRY_AUDIT_EVENT_TYPE = "SAMLAssertingPartyRegistry";

   public SAMLAssertingPartyRegistry(ProviderMBean mbean, SecurityServices services) {
      super(mbean, services);
   }

   public String getRegistryName() {
      return "SAMLAssertingPartyRegistry";
   }

   protected String[] getEntryObjectClasses() {
      return SAMLAssertingPartyEntry.getLDAPObjectClasses();
   }

   protected String[] getEntryAttributes() {
      return SAMLAssertingPartyEntry.getLDAPAttributes();
   }

   protected String getBaseAuditEventType() {
      return "SAMLAssertingPartyRegistry";
   }

   protected String makeNewPartnerId(int num) {
      return SAMLAssertingPartyEntry.makeNewPartnerId(SAMLAssertingPartyEntry.getPartnerIdPrefix(), num);
   }

   protected SAMLPartnerEntry makeNewEntryInstance() {
      return new SAMLAssertingPartyEntry(this.log, this.legacyEncryptor);
   }

   protected SAMLPartnerEntry makeNewConfigInstance() {
      return new SAMLAssertingPartyConfig(this.log, this.legacyEncryptor);
   }

   public SAMLAssertingParty getAssertingParty(String partnerId) throws InvalidParameterException, NotFoundException {
      return (SAMLAssertingParty)this.getPartner(partnerId);
   }

   protected Class getBusinessObjectClass() {
      return BEASAMLAssertingParty.class;
   }

   protected String getPartnerIdFromBusinessObject(Object businessObject) {
      return ((BEASAMLAssertingParty)businessObject).getCn();
   }
}
