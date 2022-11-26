package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPExpressionFactory;
import java.util.HashMap;
import java.util.Map;

public class AndExpression extends BaseLDAPExpression {
   private Map scopingExpressions;
   private LDAPExpression left;
   private LDAPExpression right;

   public AndExpression(LDAPExpression left, LDAPExpression right) {
      this.left = left;
      this.right = right;
      this.scopingExpressions = new HashMap();
      Map lse = left.getScopingExpressions();
      if (lse != null) {
         this.scopingExpressions.putAll(lse);
      }

      Map rse = right.getScopingExpressions();
      if (rse != null) {
         this.scopingExpressions.putAll(rse);
      }

   }

   public String getFilter(LDAPExpressionFactory fact, Object[] params) {
      String leftFilter = this.left.getFilter(fact, params);
      String rightFilter = this.right.getFilter(fact, params);
      if (leftFilter != null && leftFilter.length() != 0) {
         if (rightFilter != null && rightFilter.length() != 0) {
            if (this.left instanceof AndExpression) {
               leftFilter = leftFilter.substring(2, leftFilter.length() - 1);
            }

            if (this.right instanceof AndExpression) {
               rightFilter = rightFilter.substring(2, rightFilter.length() - 1);
            }

            return "(&" + leftFilter + rightFilter + ")";
         } else {
            return leftFilter;
         }
      } else {
         return rightFilter;
      }
   }

   public boolean containsScopingKey() {
      return this.left.containsScopingKey() || this.right.containsScopingKey();
   }

   public Map getScopingExpressions() {
      return this.scopingExpressions;
   }
}
