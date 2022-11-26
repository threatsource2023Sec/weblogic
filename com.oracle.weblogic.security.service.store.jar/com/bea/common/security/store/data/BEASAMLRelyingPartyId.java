package com.bea.common.security.store.data;

public class BEASAMLRelyingPartyId extends BEASAMLPartnerId {
   public BEASAMLRelyingPartyId() {
   }

   public BEASAMLRelyingPartyId(String binding) {
      super(binding);
   }

   public BEASAMLRelyingPartyId(String domainName, String realmName, String registryName, String cn) {
      super(domainName, realmName, registryName, cn);
   }

   public BEASAMLRelyingPartyId(BEASAMLRelyingParty obj) {
      super((BEASAMLPartner)obj);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof BEASAMLRelyingPartyId) ? false : super.equals(other);
      }
   }
}
