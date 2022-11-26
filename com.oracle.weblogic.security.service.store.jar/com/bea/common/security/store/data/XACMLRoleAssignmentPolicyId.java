package com.bea.common.security.store.data;

public class XACMLRoleAssignmentPolicyId extends XACMLEntryId {
   public XACMLRoleAssignmentPolicyId() {
   }

   public XACMLRoleAssignmentPolicyId(String binding) {
      super(binding);
   }

   public XACMLRoleAssignmentPolicyId(String domainName, String realmName, String typeName, String cn, String xacmlVersion) {
      super(domainName, realmName, typeName, cn, xacmlVersion);
   }

   public XACMLRoleAssignmentPolicyId(XACMLRoleAssignmentPolicy obj) {
      super((XACMLEntry)obj);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof XACMLRoleAssignmentPolicyId) ? false : super.equals(other);
      }
   }
}
