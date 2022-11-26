package com.bea.common.security.store.data;

public class XACMLAuthorizationPolicyId extends XACMLEntryId {
   public XACMLAuthorizationPolicyId() {
   }

   public XACMLAuthorizationPolicyId(String binding) {
      super(binding);
   }

   public XACMLAuthorizationPolicyId(String domainName, String realmName, String typeName, String cn, String xacmlVersion) {
      super(domainName, realmName, typeName, cn, xacmlVersion);
   }

   public XACMLAuthorizationPolicyId(XACMLAuthorizationPolicy obj) {
      super((XACMLEntry)obj);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof XACMLAuthorizationPolicyId)) {
         return false;
      } else {
         XACMLAuthorizationPolicyId o = (XACMLAuthorizationPolicyId)other;
         return super.equals(other);
      }
   }
}
