package com.bea.security.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.security.xacml.PolicyStoreException;
import java.util.Set;

public interface PolicyStore {
   int ACTIVE = 0;
   int IF_REFERENCED = 1;
   int INACTIVE = 2;

   Set readAllPolicies() throws DocumentParseException, PolicyStoreException, URISyntaxException;

   Set readAllPolicySets() throws DocumentParseException, PolicyStoreException, URISyntaxException;

   Policy readPolicy(URI var1, String var2) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   PolicySet readPolicySet(URI var1, String var2) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   boolean hasPolicy(URI var1, String var2) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   boolean hasPolicySet(URI var1, String var2) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   void addPolicy(Policy var1) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   void addPolicy(Policy var1, int var2) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   void addPolicySet(PolicySet var1) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   void addPolicySet(PolicySet var1, int var2) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   void setPolicy(Policy var1) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   void setPolicy(Policy var1, int var2) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   void setPolicySet(PolicySet var1, int var2) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   void setPolicySet(PolicySet var1) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   void setPolicyStatus(URI var1, String var2, int var3) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   int getPolicyStatus(URI var1, String var2) throws PolicyStoreException, URISyntaxException;

   void setPolicySetStatus(URI var1, String var2, int var3) throws PolicyStoreException, URISyntaxException;

   int getPolicySetStatus(URI var1, String var2) throws PolicyStoreException, URISyntaxException;

   boolean deletePolicy(URI var1, String var2) throws PolicyStoreException, URISyntaxException;

   boolean deletePolicySet(URI var1, String var2) throws PolicyStoreException, URISyntaxException;

   PolicyFinder getFinder();

   boolean isTransactionSupported();

   void commit() throws PolicyStoreException;

   void rollback() throws PolicyStoreException;

   boolean getAutoCommit() throws PolicyStoreException;

   void setAutoCommit(boolean var1) throws PolicyStoreException;

   void updatePolicies(Set var1) throws PolicyStoreException;
}
