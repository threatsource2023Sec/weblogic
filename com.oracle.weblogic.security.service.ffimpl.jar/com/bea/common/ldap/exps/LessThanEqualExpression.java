package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPExpressionFactory;
import com.bea.common.ldap.escaping.EscapingFactory;

public class LessThanEqualExpression extends BaseLDAPExpression {
   private ExpPath left;
   private Const right;
   private boolean reversed = false;

   public LessThanEqualExpression(Val left, Val right) {
      if (left instanceof ExpPath) {
         this.left = (ExpPath)left;
         if (this.left.containsScopingKey()) {
            throw new IllegalArgumentException("May not test scoping primary key with less than or equal expression");
         }

         if (!(right instanceof Const)) {
            throw new IllegalArgumentException("Operands to less than or equal expression must be field reference and constant value");
         }

         this.right = (Const)right;
      } else {
         if (!(right instanceof ExpPath)) {
            throw new IllegalArgumentException("Operands to less than or equal expression must be field reference and constant value");
         }

         this.left = (ExpPath)right;
         if (this.left.containsScopingKey()) {
            throw new IllegalArgumentException("May not test scoping primary key with less than or equal expression");
         }

         if (!(left instanceof Const)) {
            throw new IllegalArgumentException("Operands to less than or equal expression must be field reference and constant value");
         }

         this.right = (Const)left;
         this.reversed = true;
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

         String filter = "(" + l + "<=" + ldapFilterEscape(r) + ")";
         return this.reversed ? "(!" + filter + ")" : filter;
      }
   }
}
