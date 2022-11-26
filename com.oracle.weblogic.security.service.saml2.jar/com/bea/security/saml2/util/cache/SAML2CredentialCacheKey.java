package com.bea.security.saml2.util.cache;

import com.bea.common.security.utils.HashCodeUtil;
import java.util.Map;

public class SAML2CredentialCacheKey {
   private Object initiator = null;
   private String credType = null;
   private Map contextMap = null;
   private int hashCode = 0;

   private SAML2CredentialCacheKey() {
   }

   public SAML2CredentialCacheKey(Object initiator, String credType, Map contextMap) {
      this.initiator = initiator;
      this.credType = credType;
      this.contextMap = contextMap;
      this.construct();
   }

   public int hashCode() {
      return this.hashCode;
   }

   public boolean equals(Object other) {
      if (other != null && other instanceof SAML2CredentialCacheKey) {
         if (this == other) {
            return true;
         }

         SAML2CredentialCacheKey o = (SAML2CredentialCacheKey)other;
         if (this.initiator.equals(o.initiator) && this.credType.equals(o.credType) && this.contextMap.equals(o.contextMap)) {
            return true;
         }
      }

      return false;
   }

   private void constructHashCode() {
      int hash = 23;
      hash = HashCodeUtil.hash(hash, this.initiator);
      hash = HashCodeUtil.hash(hash, this.credType);
      hash = HashCodeUtil.hash(hash, this.contextMap);
      this.hashCode = hash;
   }

   private void construct() {
      this.constructHashCode();
   }
}
