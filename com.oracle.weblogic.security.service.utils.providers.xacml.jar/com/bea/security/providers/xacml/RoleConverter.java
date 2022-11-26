package com.bea.security.providers.xacml;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.IndeterminateEvaluationException;

public interface RoleConverter {
   boolean isInRole(StringAttribute var1) throws IndeterminateEvaluationException;

   Bag getRoleAttributes() throws IndeterminateEvaluationException;

   AttributeEvaluator getEvaluator(URI var1, URI var2, String var3, URI var4);
}
