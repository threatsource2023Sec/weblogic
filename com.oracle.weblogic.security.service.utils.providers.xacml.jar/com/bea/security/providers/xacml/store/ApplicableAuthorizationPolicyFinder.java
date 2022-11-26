package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyFinder;
import java.util.Set;

public interface ApplicableAuthorizationPolicyFinder extends PolicyFinder {
   Set findAuthorizationPolicy(EvaluationCtx var1) throws DocumentParseException, URISyntaxException, PolicyStoreException;
}
