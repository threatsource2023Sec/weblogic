package com.bea.security.providers.xacml;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.attr.AnyURIAttribute;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.IndeterminateEvaluationException;

public interface SubjectConverter {
   boolean isUserAnonymous() throws IndeterminateEvaluationException;

   boolean isUser(StringAttribute var1) throws IndeterminateEvaluationException;

   boolean isMemberOf(StringAttribute var1) throws IndeterminateEvaluationException;

   boolean isIddName(AnyURIAttribute var1) throws IndeterminateEvaluationException;

   Bag getUserNameAttributes() throws IndeterminateEvaluationException;

   Bag getGroupAttributes() throws IndeterminateEvaluationException;

   Bag getIddNameAttributes() throws IndeterminateEvaluationException;

   boolean isIDCSAppRole(AnyURIAttribute var1) throws IndeterminateEvaluationException;

   Bag getIDCSAppRoleAttributes() throws IndeterminateEvaluationException;

   AttributeEvaluator getEvaluator(URI var1, URI var2, String var3, URI var4);
}
