package com.bea.common.security.store.data;

public class BEASAMLAssertingPartyId extends BEASAMLPartnerId {
   public BEASAMLAssertingPartyId() {
   }

   public BEASAMLAssertingPartyId(String binding) {
      super(binding);
   }

   public BEASAMLAssertingPartyId(String domainName, String realmName, String registryName, String cn) {
      super(domainName, realmName, registryName, cn);
   }

   public BEASAMLAssertingPartyId(BEASAMLAssertingParty obj) {
      super((BEASAMLPartner)obj);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof BEASAMLAssertingPartyId) ? false : super.equals(other);
      }
   }
}
