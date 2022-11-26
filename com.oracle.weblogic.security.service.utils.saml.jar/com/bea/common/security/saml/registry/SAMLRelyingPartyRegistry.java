package com.bea.common.security.saml.registry;

import com.bea.common.security.store.data.BEASAMLRelyingParty;
import weblogic.management.security.ProviderMBean;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;
import weblogic.security.providers.saml.registry.SAMLRelyingParty;
import weblogic.security.spi.SecurityServices;

public class SAMLRelyingPartyRegistry extends SAMLPartnerRegistry {
   private static final String RP_REGISTRY_NAME = "SAMLRelyingPartyRegistry";
   private static final String RP_REGISTRY_AUDIT_EVENT_TYPE = "SAMLRelyingPartyRegistry";

   public SAMLRelyingPartyRegistry(ProviderMBean mbean, SecurityServices services) {
      super(mbean, services);
   }

   public String getRegistryName() {
      return "SAMLRelyingPartyRegistry";
   }

   protected String[] getEntryObjectClasses() {
      return SAMLRelyingPartyEntry.getLDAPObjectClasses();
   }

   protected String[] getEntryAttributes() {
      return SAMLRelyingPartyEntry.getLDAPAttributes();
   }

   protected String getBaseAuditEventType() {
      return "SAMLRelyingPartyRegistry";
   }

   protected String makeNewPartnerId(int num) {
      return SAMLRelyingPartyEntry.makeNewPartnerId(SAMLRelyingPartyEntry.getPartnerIdPrefix(), num);
   }

   protected SAMLPartnerEntry makeNewEntryInstance() {
      return new SAMLRelyingPartyEntry(this.log, this.legacyEncryptor);
   }

   protected SAMLPartnerEntry makeNewConfigInstance() {
      return new SAMLRelyingPartyConfig(this.log, this.legacyEncryptor);
   }

   public SAMLRelyingParty getRelyingParty(String partnerId) throws InvalidParameterException, NotFoundException {
      return (SAMLRelyingParty)this.getPartner(partnerId);
   }

   protected Class getBusinessObjectClass() {
      return BEASAMLRelyingParty.class;
   }

   protected String getPartnerIdFromBusinessObject(Object businessObject) {
      return ((BEASAMLRelyingParty)businessObject).getCn();
   }
}
