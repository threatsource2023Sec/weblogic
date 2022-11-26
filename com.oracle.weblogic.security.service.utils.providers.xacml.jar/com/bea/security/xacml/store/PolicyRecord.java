package com.bea.security.xacml.store;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicyIdReference;
import java.util.Collection;

public class PolicyRecord extends Record {
   private PolicyIdReference reference;

   public PolicyRecord(URI identifier, String version, PolicyFinder finder) {
      this(identifier, version, finder, (Collection)null);
   }

   public PolicyRecord(URI identifier, String version, PolicyFinder finder, Collection designatorMatches) {
      this(identifier, version, finder, (Policy)null, designatorMatches);
   }

   public PolicyRecord(URI identifier, String version, PolicyFinder finder, Policy referent) {
      this(identifier, version, finder, referent, (Collection)null);
   }

   public PolicyRecord(URI identifier, String version, PolicyFinder finder, Policy referent, Collection designatorMatches) {
      super(identifier, version, finder, referent, designatorMatches);
      this.reference = referent != null ? (PolicyIdReference)referent.getReference() : new PolicyIdReference(identifier, version);
   }

   public PolicyIdReference getReference() {
      return this.reference;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof PolicyRecord) ? false : super.equals(other);
      }
   }
}
