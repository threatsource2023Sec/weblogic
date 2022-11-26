package com.bea.security.xacml.attr;

import com.bea.common.security.xacml.URI;
import com.bea.security.xacml.AttributeEvaluator;

public interface SubjectAttributeEvaluatableFactory extends AttributeEvaluatableFactory {
   AttributeEvaluator getEvaluatable(URI var1, URI var2, URI var3);

   AttributeEvaluator getEvaluatable(URI var1, URI var2, String var3, URI var4);
}
