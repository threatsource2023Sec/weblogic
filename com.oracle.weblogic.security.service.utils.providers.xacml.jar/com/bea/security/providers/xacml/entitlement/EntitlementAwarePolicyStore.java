package com.bea.security.providers.xacml.entitlement;

import com.bea.security.xacml.store.PolicyStore;

public interface EntitlementAwarePolicyStore extends PolicyStore {
   int ENTITLEMENT = 3;
}
