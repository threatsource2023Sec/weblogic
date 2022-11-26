package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPExpressionFactory;

public class ValueExpression extends BaseLDAPExpression {
   private Val value;

   public ValueExpression(Val value) {
      this.value = value;
   }

   public Val getValue() {
      return this.value;
   }

   public String getFilter(LDAPExpressionFactory fact, Object[] params) {
      return "";
   }
}
