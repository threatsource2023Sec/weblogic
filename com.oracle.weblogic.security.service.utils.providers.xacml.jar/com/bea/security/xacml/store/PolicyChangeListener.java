package com.bea.security.xacml.store;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;

public interface PolicyChangeListener {
   void addPolicy(Policy var1);

   void addPolicySet(PolicySet var1);

   void replacePolicy(Policy var1);

   void replacePolicySet(PolicySet var1);

   void deletePolicy(URI var1, String var2);

   void deletePolicySet(URI var1, String var2);

   void reload();

   boolean isTransactionSupported();

   void commit() throws Exception;

   void rollback() throws Exception;

   boolean getAutoCommit() throws Exception;

   void setAutoCommit(boolean var1) throws Exception;
}
