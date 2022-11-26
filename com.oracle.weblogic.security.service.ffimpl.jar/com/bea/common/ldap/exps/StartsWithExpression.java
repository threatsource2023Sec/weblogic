package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPExpressionFactory;
import com.bea.common.ldap.escaping.EscapingFactory;

public class StartsWithExpression extends BaseLDAPExpression {
   private ExpPath left;
   private Const right;

   public StartsWithExpression(Val left, Val right) {
      if (left instanceof ExpPath) {
         this.left = (ExpPath)left;
         if (this.left.containsScopingKey()) {
            throw new IllegalArgumentException("May not test scoping primary key with starts with expression");
         } else if (right != null && !(right instanceof Const)) {
            throw new IllegalArgumentException("Operands to starts with expression must be field reference and constant value");
         } else {
            this.right = (Const)right;
         }
      } else {
         throw new IllegalArgumentException("Operands to starts with expression must be field reference and constant value");
      }
   }

   public String getFilter(LDAPExpressionFactory fact, Object[] params) {
      String l = this.left.getAttribute();
      Object o = this.right.getValue(params);
      if (o == null) {
         throw new IllegalArgumentException("Test constant must not be null");
      } else {
         String r = o.toString();
         EscapingFactory fac = this.getEscapingFactory(this.left.last());
         if (fac != null) {
            r = fac.getEscaping().escapeString(r);
         }

         return "(" + l + "=" + ldapFilterEscape(r) + "*)";
      }
   }
}
