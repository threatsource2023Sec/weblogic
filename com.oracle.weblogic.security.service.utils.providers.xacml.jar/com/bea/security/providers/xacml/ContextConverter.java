package com.bea.security.providers.xacml;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.attr.JavaObjectAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.IndeterminateEvaluationException;

public interface ContextConverter {
   JavaObjectAttribute getContextValue(String var1) throws IndeterminateEvaluationException;

   AttributeEvaluator getEvaluator(URI var1, URI var2, String var3);
}
