package com.bea.security.xacml.store;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.PolicySetIdReference;
import java.util.Collection;

public class PolicySetRecord extends Record {
   private PolicySetIdReference reference;

   public PolicySetRecord(URI identifier, String version, PolicyFinder finder) {
      this(identifier, version, finder, (Collection)null);
   }

   public PolicySetRecord(URI identifier, String version, PolicyFinder finder, Collection designatorMatches) {
      this(identifier, version, finder, (PolicySet)null, designatorMatches);
   }

   public PolicySetRecord(URI identifier, String version, PolicyFinder finder, PolicySet referent) {
      this(identifier, version, finder, referent, (Collection)null);
   }

   public PolicySetRecord(URI identifier, String version, PolicyFinder finder, PolicySet referent, Collection designatorMatches) {
      super(identifier, version, finder, referent, designatorMatches);
      this.reference = referent != null ? (PolicySetIdReference)referent.getReference() : new PolicySetIdReference(identifier, version);
   }

   public PolicySetIdReference getReference() {
      return this.reference;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof PolicySetRecord) ? false : super.equals(other);
      }
   }
}
