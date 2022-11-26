package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPExpressionFactory;
import com.bea.common.ldap.escaping.EscapingFactory;
import java.util.HashMap;
import java.util.Map;

public class EqualExpression extends BaseLDAPExpression {
   private ExpPath left;
   private Const right;

   public EqualExpression(Val left, Val right) {
      if (left instanceof ExpPath) {
         this.left = (ExpPath)left;
         if (!(right instanceof Const)) {
            throw new IllegalArgumentException("Operands to equals expression must be field reference and constant value");
         }

         this.right = (Const)right;
      } else {
         if (!(right instanceof ExpPath)) {
            throw new IllegalArgumentException("Operands to equals expression must be field reference and constant value");
         }

         this.left = (ExpPath)right;
         if (!(left instanceof Const)) {
            throw new IllegalArgumentException("Operands to equals expression must be field reference and constant value");
         }

         this.right = (Const)left;
      }

   }

   public String getFilter(LDAPExpressionFactory fact, Object[] params) {
      if (this.left.containsScopingKey()) {
         return "";
      } else {
         String l = this.left.getAttribute();
         String r = "*";
         Object o = this.right.getValue(params);
         if (o != null) {
            r = o.toString();
            EscapingFactory fac = this.getEscapingFactory(this.left.last());
            if (fac != null) {
               r = fac.getEscaping().escapeString(r);
            }

            r = ldapFilterEscape(r);
         }

         return "(" + l + "=" + r + ")";
      }
   }

   public Map getScopingExpressions() {
      if (this.left.containsScopingKey()) {
         Map scopeMap = new HashMap();
         scopeMap.put(this.left.last(), this.right);
         return scopeMap;
      } else {
         return super.getScopingExpressions();
      }
   }

   public boolean containsScopingKey() {
      return this.left.containsScopingKey();
   }
}
