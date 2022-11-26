package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPExpressionFactory;

public class NotExpression extends BaseLDAPExpression {
   private LDAPExpression inner;

   public NotExpression(LDAPExpression inner) {
      this.inner = inner;
   }

   public String getFilter(LDAPExpressionFactory fact, Object[] params) {
      return "(!" + this.inner.getFilter(fact, params) + ")";
   }
}
