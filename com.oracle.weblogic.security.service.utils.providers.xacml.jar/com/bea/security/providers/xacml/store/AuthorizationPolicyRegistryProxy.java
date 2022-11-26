package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import java.util.Set;

public interface AuthorizationPolicyRegistryProxy {
   Set findAuthorizationPolicy(EvaluationCtx var1) throws DocumentParseException, URISyntaxException, PolicyStoreException;

   AbstractPolicy find(IdReference var1) throws DocumentParseException, URISyntaxException, PolicyStoreException;

   Set getAllPolicies() throws URISyntaxException, DocumentParseException, PolicyStoreException;

   Set getAllPolicySets() throws URISyntaxException, DocumentParseException, PolicyStoreException;
}
