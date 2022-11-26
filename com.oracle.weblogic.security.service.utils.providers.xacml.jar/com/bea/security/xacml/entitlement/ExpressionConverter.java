package com.bea.security.xacml.entitlement;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Expression;
import java.util.Set;

public interface ExpressionConverter {
   Expression convert(String var1, Set var2) throws DocumentParseException, SimplificationException, URISyntaxException;
}
