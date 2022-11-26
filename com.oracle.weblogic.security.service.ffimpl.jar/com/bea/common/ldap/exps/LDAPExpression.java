package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPExpressionFactory;
import java.util.Map;
import org.apache.openjpa.kernel.exps.Expression;

public interface LDAPExpression extends Expression {
   String getFilter(LDAPExpressionFactory var1, Object[] var2);

   boolean containsScopingKey();

   Map getScopingExpressions();
}
