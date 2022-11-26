package com.bea.security.providers.xacml.store.file;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;

interface CachedPolicyFinder {
   void loadEntry(Index.Entry var1) throws DocumentParseException, PolicyStoreException, URISyntaxException;

   void reset();

   Object findPolicy(EvaluationCtx var1) throws DocumentParseException, URISyntaxException, PolicyStoreException;
}
