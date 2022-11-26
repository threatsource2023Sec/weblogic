package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import java.util.Map;
import java.util.Set;

public interface RoleAssignmentPolicyRegistryProxy {
   Map findRoleAssignmentPolicy(EvaluationCtx var1) throws DocumentParseException, URISyntaxException, PolicyStoreException;

   AbstractPolicy find(IdReference var1) throws DocumentParseException, URISyntaxException, PolicyStoreException;

   Set getAllPolicies() throws DocumentParseException, URISyntaxException, PolicyStoreException;

   Set getAllPolicySets() throws DocumentParseException, URISyntaxException, PolicyStoreException;
}
