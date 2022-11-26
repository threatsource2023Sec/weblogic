package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyStore;

public interface PolicyStoreConfigurator {
   int AUTHORIZATION = 0;
   int ROLE_ASSIGNMENT = 1;

   PolicyStore newPolicyStore(int var1, AttributeRegistry var2) throws DocumentParseException, URISyntaxException, PolicyStoreException;
}
