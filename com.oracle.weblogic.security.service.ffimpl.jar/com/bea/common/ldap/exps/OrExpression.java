package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPExpressionFactory;

public class OrExpression extends BaseLDAPExpression {
   private LDAPExpression left;
   private LDAPExpression right;

   public OrExpression(LDAPExpression left, LDAPExpression right) {
      this.left = left;
      this.right = right;
   }

   public String getFilter(LDAPExpressionFactory fact, Object[] params) {
      String leftFilter = this.left.getFilter(fact, params);
      String rightFilter = this.right.getFilter(fact, params);
      if (leftFilter != null && leftFilter.length() != 0) {
         if (rightFilter != null && rightFilter.length() != 0) {
            if (this.left instanceof OrExpression) {
               leftFilter = leftFilter.substring(2, leftFilter.length() - 1);
            }

            if (this.right instanceof OrExpression) {
               rightFilter = rightFilter.substring(2, rightFilter.length() - 1);
            }

            return "(|" + leftFilter + rightFilter + ")";
         } else {
            return leftFilter;
         }
      } else {
         return rightFilter;
      }
   }
}
