package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPExpressionFactory;

public class EmptyExpression extends BaseLDAPExpression {
   public String getFilter(LDAPExpressionFactory fact, Object[] params) {
      return "";
   }
}
